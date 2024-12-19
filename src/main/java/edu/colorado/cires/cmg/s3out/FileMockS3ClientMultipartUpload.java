package edu.colorado.cires.cmg.s3out;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import software.amazon.awssdk.services.s3.model.CompletedPart;
import software.amazon.awssdk.utils.BinaryUtils;

/**
 * A mock implementation of a {@link S3ClientMultipartUpload} that uses the local filesystem.
 * ONLY to be used for testing.
 */
public class FileMockS3ClientMultipartUpload implements S3ClientMultipartUpload {

  /**
   * A Builder that builds a {@link FileMockS3ClientMultipartUpload}
   *
   * @return the Builder
   */
  public static Builder builder() {
    return new Builder();
  }

  /**
   * A Builder that builds a {@link FileMockS3ClientMultipartUpload}
   */
  public static class Builder {
    private Path mockBucketDir;

    private Builder() {

    }

    /**
     * Sets a directory that will contain directories representing buckets for testing.
     *
     * @param mockBucketDir a directory that will contain directories representing buckets for testing
     * @return this Builder
     */
    public Builder mockBucketDir(Path mockBucketDir) {
      this.mockBucketDir = mockBucketDir;
      return this;
    }

    /**
     * Builds a new {@link FileMockS3ClientMultipartUpload}
     *
     * @return a new {@link FileMockS3ClientMultipartUpload}
     */
    public FileMockS3ClientMultipartUpload build() {
      return new FileMockS3ClientMultipartUpload(mockBucketDir);
    }
  }

  private final Map<String, MultipartUploadState> uploadStateMap = Collections.synchronizedMap(new HashMap<>());
  private final Path mockBucketDir;

  private FileMockS3ClientMultipartUpload(Path mockBucketDir) {
    this.mockBucketDir = Objects.requireNonNull(mockBucketDir);
  }

  @Override
  public String createMultipartUpload(String bucket, String key) {
    return createMultipartUpload(MultipartUploadRequest.builder().bucket(bucket).key(key).build());
  }

  @Override
  public String createMultipartUpload(MultipartUploadRequest multipartUploadRequest) {
    MultipartUploadState multipartUploadState = new MultipartUploadState(
        multipartUploadRequest.getBucket(),
        multipartUploadRequest.getKey());
    uploadStateMap.put(multipartUploadState.getId(), multipartUploadState);
    return multipartUploadState.getId();
  }

  @Override
  public CompletedPart uploadPart(String bucket, String key, String uploadId, int partNumber, ByteBuffer buffer) {
    return uploadPart(UploadPartParams.builder()
            .bucket(bucket)
            .key(key)
            .uploadId(uploadId)
            .partNumber(partNumber)
            .buffer(buffer)
            .build());
  }

  @Override
  public CompletedPart uploadPart(UploadPartParams uploadPartParams) {
    MultipartUploadState multipartUploadState = uploadStateMap.get(uploadPartParams.getUploadId());
    if (!multipartUploadState.getBucket().equals(uploadPartParams.getBucket())) {
      throw new IllegalStateException("Incorrect bucket: " + uploadPartParams.getBucket() + " : " + multipartUploadState.getBucket());
    }
    if (!multipartUploadState.getKey().equals(uploadPartParams.getKey())) {
      throw new IllegalStateException("Incorrect key: " + uploadPartParams.getKey() + " : " + multipartUploadState.getKey());
    }
    if (uploadPartParams.getPartNumber() != multipartUploadState.getParts().size() + 1) {
      throw new IllegalStateException("Incorrect part number: " + uploadPartParams.getPartNumber() + " : " + (multipartUploadState.getParts().size() + 1));
    }
    multipartUploadState.getParts().add(uploadPartParams.getBuffer());
    return CompletedPart.builder().build();
  }

  @Override
  public void completeMultipartUpload(String bucket, String key, String uploadId, Collection<CompletedPart> completedParts) {
    MultipartUploadState multipartUploadState = uploadStateMap.remove(uploadId);
    if (!multipartUploadState.getBucket().equals(bucket)) {
      throw new IllegalStateException("Incorrect bucket: " + bucket + " : " + multipartUploadState.getBucket());
    }
    if (!multipartUploadState.getKey().equals(key)) {
      throw new IllegalStateException("Incorrect key: " + key + " : " + multipartUploadState.getKey());
    }
    Path path = mockBucketDir.resolve(bucket).resolve(key);
    Path parent = path.getParent();
    if (parent != null) {
      try {
        Files.createDirectories(parent);
      } catch (IOException e) {
        throw new IllegalStateException("Unable to create directory: " + parent);
      }
    }

    try (OutputStream outputStream = new BufferedOutputStream(Files.newOutputStream(path))) {
      for (ByteBuffer buffer : multipartUploadState.getParts()) {
        outputStream.write(BinaryUtils.copyRemainingBytesFrom(buffer));
      }
    } catch (IOException e) {
      throw new IllegalStateException("Unable to write to file", e);
    }
  }

  @Override
  public void abortMultipartUpload(String bucket, String key, String uploadId) {
    MultipartUploadState multipartUploadState = uploadStateMap.remove(uploadId);
    if (!multipartUploadState.getBucket().equals(bucket)) {
      throw new IllegalStateException("Incorrect bucket: " + bucket + " : " + multipartUploadState.getBucket());
    }
    if (!multipartUploadState.getKey().equals(key)) {
      throw new IllegalStateException("Incorrect key: " + key + " : " + multipartUploadState.getKey());
    }
  }

  /**
   * Returns a {@link Map} containing a mock representation of pending multipart uploads.
   *
   * @return a {@link Map} containing a mock representation of pending multipart uploads
   */
  public Map<String, MultipartUploadState> getUploadStateMap() {
    return uploadStateMap;
  }

  private static class MultipartUploadState {

    private final List<ByteBuffer> parts = new ArrayList<>();
    private final String id = UUID.randomUUID().toString();
    private final String bucket;
    private final String key;

    private MultipartUploadState(String bucket, String key) {
      this.bucket = bucket;
      this.key = key;
    }

    public List<ByteBuffer> getParts() {
      return parts;
    }

    public String getId() {
      return id;
    }

    public String getBucket() {
      return bucket;
    }

    public String getKey() {
      return key;
    }
  }
}
