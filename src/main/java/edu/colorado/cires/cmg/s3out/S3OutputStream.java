package edu.colorado.cires.cmg.s3out;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import software.amazon.awssdk.services.s3.model.CompletedPart;

/**
 * An {@link OutputStream} that uses a multipart upload to upload a file to a S3 bucket.
 */
public class S3OutputStream extends OutputStream {

  private static final Logger LOGGER = LoggerFactory.getLogger(S3OutputStream.class);
  private static final int MiB = 1024 * 1024;
  private static final int MIN_PART_SIZE_MIB = 5;

  /**
   * Creates a new builder for a S3OutputStream.
   *
   * @return a new builder for a S3OutputStream
   */
  public static Builder builder() {
    return new Builder();
  }

  /**
   * Builds a {@link S3OutputStream}.
   */
  public static class Builder {

    private S3ClientMultipartUpload s3;
    private String bucket;
    private String key;
    private MultipartUploadRequest uploadRequest;
    private int partSizeMib = MIN_PART_SIZE_MIB;
    private boolean autoComplete = true;
    private int uploadQueueSize = 1;

    private Builder() {

    }

    /**
     * Sets the {@link S3ClientMultipartUpload} object for the {@link S3OutputStream}. Required.
     *
     * @param s3 the {@link S3ClientMultipartUpload}
     * @return this Builder
     */
    public Builder s3(S3ClientMultipartUpload s3) {
      this.s3 = s3;
      return this;
    }

    /**
     * Sets the bucket name for the {@link S3OutputStream}. Required.
     *
     * @param bucket the bucket name
     * @return this Builder
     * @deprecated use {@link #uploadRequest(MultipartUploadRequest)}
     */
    @Deprecated
    public Builder bucket(String bucket) {
      this.bucket = bucket;
      return this;
    }

    /**
     * Sets the key where the file will be uploaded to in the bucket. Required.
     *
     * @param key the key where the file will be uploaded to in the bucket
     * @return this Builder
     * @deprecated use {@link #uploadRequest(MultipartUploadRequest)}
     */
    @Deprecated
    public Builder key(String key) {
      this.key = key;
      return this;
    }


    /**
     * Sets upload request with bucket info and object metadata
     *
     * @param uploadRequest object
     * @return this Builder
     */
    public Builder uploadRequest(MultipartUploadRequest uploadRequest) {
      this.uploadRequest = uploadRequest;
      return this;
    }

    /**
     * Sets the part size to use when uploading in MiB. Must be at least 5. Default value: 5
     *
     * @param partSizeMib the part size to use when uploading in MiB
     * @return this Builder
     */
    public Builder partSizeMib(int partSizeMib) {
      this.partSizeMib = partSizeMib;
      return this;
    }

    /**
     * When a multipart file upload is completed, AWS S3 must be notified. Autocompletion is a convenience feature that allows a S3OutputStream to
     * work like a normal {@link OutputStream}.  The main use case for this is where your code generates a S3OutputStream that must be passed to
     * another library as a {@link OutputStream} that you do not control that is responsible for closing it. With autocompletion enabled, the AWS
     * completion notification will always happen when the OutputStream is closed. This is fine, unless an exception occurs and close() is called in a
     * finally block or try-with-resources (as should always be done).  In this scenario, the upload will be completed even if there was an error,
     * rather than aborting the upload.  To ensure compatibility with java.io.OutputStream, autocompletion is enabled by default.
     *
     * If you have control over the code closing the S3OutputStream it is best to disable autocompletion as follows:
     * <pre>
     *     try (
     *         InputStream inputStream = Files.newInputStream(source);
     *         S3OutputStream s3OutputStream = S3OutputStream.builder()
     *             .s3(s3)
     *             .uploadRequest(MultipartUploadRequest.builder().bucket(bucketName).key(key).build())
     *             .autoComplete(false)
     *             .build();
     *     ) {
     *       IOUtils.copy(inputStream, outputStream);
     *       s3OutputStream.done();
     *     }
     * </pre>
     *
     * Note the call to s3OutputStream.done(). This should be called after all data has been uploaded, before calling close or the end of the
     * try-with-resources block. This signals that the upload was successful and when the S3OutputStream is closed, the completion signal will be
     * sent. If done() is not called before close() and error was assumed to have occurred and an abort signal will be send in close() instead.
     *
     * @param autoComplete true to enable autocompletion
     * @return this Builder
     */
    public Builder autoComplete(boolean autoComplete) {
      this.autoComplete = autoComplete;
      return this;
    }

    /**
     * A S3OutputStream uses a queue to allow multipart uploads to S3 to happen while additional buffers are being filled concurrently. The
     * uploadQueueSize defines the number of parts to be queued before blocking population of additional parts.  The default value is 1. Specifying a
     * higher value may improve upload speed at the expense of more heap usage. Using a value higher than one should be tested to see if any
     * performance gains are achieved for your situation.
     *
     * @param uploadQueueSize the max number of buffers in the queue before blocking
     * @return this Builder
     */
    public Builder uploadQueueSize(int uploadQueueSize) {
      this.uploadQueueSize = uploadQueueSize;
      return this;
    }

    /**
     * Builds a new {@link S3OutputStream}
     *
     * @return a new {@link S3OutputStream}
     */
    public S3OutputStream build() {
      if (partSizeMib < MIN_PART_SIZE_MIB) {
        throw new IllegalArgumentException("Part size MiB must be at least " + MIN_PART_SIZE_MIB);
      }
      MultipartUploadRequest request;
      if (uploadRequest != null) {
        request = uploadRequest;
      } else {
        request = MultipartUploadRequest.builder().bucket(bucket).key(key).build();
      }
      return new S3OutputStream(s3, request, partSizeMib * MiB, autoComplete, uploadQueueSize);
    }
  }

  private final S3ClientMultipartUpload s3;
  private final String bucket;
  private final String key;
  private final String checksumAlgorithm;
  private final int maxBufferSize;
  private final String uploadId;
  private final List<CompletedPart> completedParts = new ArrayList<>();
  private final BlockingQueue<UploadConsumerBuffer> uploadQueue;
  private final Thread consumer;

  private ByteBuffer buffer;
  private boolean complete;
  private boolean closed;


  S3OutputStream(S3ClientMultipartUpload s3, MultipartUploadRequest uploadRequest, int maxBufferSize, boolean autoComplete,
      int queueSize) {
    this.uploadQueue = new LinkedBlockingDeque<>(queueSize);
    this.s3 = s3;
    this.bucket = uploadRequest.getBucket();
    this.key = uploadRequest.getKey();
    this.checksumAlgorithm = uploadRequest.getChecksumAlgorithm();
    this.maxBufferSize = maxBufferSize;
    complete = autoComplete;
    uploadId = s3.createMultipartUpload(uploadRequest);
    newBuffer();
    consumer = new Thread(new UploadConsumer());
    consumer.start();
  }

  private void newBuffer() {
    buffer = ByteBuffer.allocate(maxBufferSize);
  }

  private void uploadPart() {
    if (buffer.position() > 0) {
      buffer.flip();
      try {
        uploadQueue.put(new UploadConsumerBuffer(buffer, false));
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
        throw new IllegalStateException("Upload thread was interrupted", e);
      }
    }
  }

  private class UploadConsumer implements Runnable {

    @Override
    public void run() {
      try {
        while (true) {
          UploadConsumerBuffer buffer = uploadQueue.take();
          if (buffer.isPoison()) {
            return;
          }
          synchronized (completedParts) {
            int partNumber = completedParts.size() + 1;
            UploadPartParams.Builder builder = UploadPartParams.builder()
                    .bucket(bucket)
                    .key(key)
                    .uploadId(uploadId)
                    .partNumber(partNumber)
                    .buffer(buffer.getBuffer())
                    .checksumAlgorithm(checksumAlgorithm);
            completedParts.add(s3.uploadPart(builder.build()));
          }
        }
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
      }
    }
  }

  private static class UploadConsumerBuffer {

    private final ByteBuffer buffer;
    private final boolean poison;

    private UploadConsumerBuffer(ByteBuffer buffer, boolean poison) {
      this.buffer = buffer;
      this.poison = poison;
    }

    public ByteBuffer getBuffer() {
      return buffer;
    }

    public boolean isPoison() {
      return poison;
    }
  }

  private void cycleBuffer() {
    uploadPart();
    newBuffer();
  }

  private void complete() {
    synchronized (completedParts) {
      s3.completeMultipartUpload(bucket, key, uploadId, completedParts);
    }
  }

  private void abort() {
    try {
      s3.abortMultipartUpload(bucket, key, uploadId);
    } catch (Exception e) {
      LOGGER.warn("An error occurred aborting multipart upload: " + bucket + ":" + key, e);
    }
  }

  /**
   * If autocomplete is disabled, marks the upload as successful.
   *
   * @see Builder#autoComplete(boolean)
   */
  public void done() {
    complete = true;
  }

  @Override
  public void write(byte[] b, int off, int len) throws IOException {
    if (b == null) {
      throw new NullPointerException();
    } else if ((off < 0) || (off > b.length) || (len < 0) ||
        ((off + len) > b.length) || ((off + len) < 0)) {
      throw new IndexOutOfBoundsException();
    } else if (len == 0) {
      return;
    }
    if (buffer.remaining() >= len) {
      buffer.put(b, off, len);
      if (!buffer.hasRemaining()) {
        cycleBuffer();
      }
    } else {
      for (byte[] chunk : chunkBytes(b, off, len)) {
        buffer.put(chunk);
        if (!buffer.hasRemaining()) {
          cycleBuffer();
        }
      }
    }
  }

  private List<byte[]> chunkBytes(byte[] b, int initialOffset, int len) {
    List<byte[]> chunks = new ArrayList<>();
    final int bEnd = initialOffset + len;
    int start = initialOffset;
    int end = Math.min(bEnd, start + buffer.remaining());
    byte[] chunk = new byte[end - start];
    System.arraycopy(b, start, chunk, 0, chunk.length);
    chunks.add(chunk);

    while (end < bEnd) {
      start = end;
      end = Math.min(bEnd, start + maxBufferSize);
      chunk = new byte[end - start];
      System.arraycopy(b, start, chunk, 0, chunk.length);
      chunks.add(chunk);
    }

    return chunks;
  }

  @Override
  public void write(int b) throws IOException {
    if (buffer.hasRemaining()) {
      buffer.put((byte) b);
    } else {
      cycleBuffer();
      buffer.put((byte) b);
    }
  }

  @Override
  public void close() throws IOException {
    if (!closed) {
      closed = true;
      if (complete) {
        uploadPart();
        try {
          uploadQueue.put(new UploadConsumerBuffer(null, true));
          consumer.join();
        } catch (InterruptedException e) {
          Thread.currentThread().interrupt();
        }
        complete();
      } else {
        try {
          uploadQueue.put(new UploadConsumerBuffer(null, true));
          consumer.join();
        } catch (InterruptedException e) {
          Thread.currentThread().interrupt();
        }
        abort();
      }
    }
  }
}
