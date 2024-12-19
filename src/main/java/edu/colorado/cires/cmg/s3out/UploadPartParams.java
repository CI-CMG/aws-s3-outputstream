package edu.colorado.cires.cmg.s3out;

import java.nio.ByteBuffer;
import java.util.Objects;

public class UploadPartParams {

  /**
   * Creates a new builder for a UploadPartParams.
   *
   * @return a new builder for a UploadPartParams
   */
  public static Builder builder() {
    return new Builder();
  }

  private final String bucket;
  private final String key;
  private final String uploadId;
  private final int partNumber;
  private final ByteBuffer buffer;
  private final String checksumAlgorithm;

  public UploadPartParams(String bucket, String key, String uploadId, int partNumber, ByteBuffer buffer, String checksumAlgorithm) {
    this.bucket = bucket;
    this.key = key;
    this.uploadId = uploadId;
    this.partNumber = partNumber;
    this.buffer = buffer;
    this.checksumAlgorithm = checksumAlgorithm;
  }

  /**
   * Returns the bucket name.
   * Never null.
   *
   * @return the bucket name
   */
  public String getBucket() {
    return bucket;
  }

  /**
   * Returns the bucket key
   * Never null.
   *
   * @return the bucket key
   */
  public String getKey() {
    return key;
  }

  /**
   * Returns the upload id
   * Never null.
   *
   * @return the upload id
   */
  public String getUploadId() {
    return uploadId;
  }

  /**
   * Returns the part number
   * Never null.
   *
   * @return the part number
   */
  public int getPartNumber() {
    return partNumber;
  }

  /**
   * Returns the buffer
   * Never null.
   *
   * @return the buffer
   */
  public ByteBuffer getBuffer() {
    return buffer;
  }

  /**
   * Returns the checksumAlgorithm;
   *
   * @return the checksumAlgorithm
   */
  public String getChecksumAlgorithm() {
    return checksumAlgorithm;
  }


  /**
   * Builds a {@link UploadPartParams}.
   */
  public static class Builder {

    private String bucket;
    private String key;
    private String uploadId;
    private int partNumber;
    private ByteBuffer buffer;
    private String checksumAlgorithm;

    private Builder() {

    }

    /**
     * Sets the bucket name for the {@link UploadPartParams}. Required.
     *
     * @param bucket the bucket name
     * @return this Builder
     */
    public Builder bucket(String bucket) {
      this.bucket = bucket;
      return this;
    }

    /**
     * Sets the key where the file will be uploaded to in the bucket. Required.
     *
     * @param key the key where the file will be uploaded to in the bucket
     * @return this Builder
     */
    public Builder key(String key) {
      this.key = key;
      return this;
    }

    /**
     * Sets the upload id. Required.
     *
     * @param uploadId the upload id
     * @return this Builder
     */
    public Builder uploadId(String uploadId) {
      this.uploadId = uploadId;
      return this;
    }

    /**
     * Sets the part number. Required.
     *
     * @param partNumber the part number
     * @return this Builder
     */
    public Builder partNumber(int partNumber) {
      this.partNumber = partNumber;
      return this;
    }

    /**
     * Sets the buffer. Required.
     *
     * @param buffer the buffer
     * @return this Builder
     */
    public Builder buffer(ByteBuffer buffer) {
      this.buffer = buffer;
      return this;
    }

    /**
     * Sets the checksumAlgorithm that will be applied.
     *
     * @param checksumAlgorithm the file metadata
     * @return this Builder
     */
    public Builder checksumAlgorithm(String checksumAlgorithm) {
      this.checksumAlgorithm = checksumAlgorithm;
      return this;
    }

    /**
     * Builds a new {@link UploadPartParams}
     *
     * @return a new {@link UploadPartParams}
     */
    public UploadPartParams build() {
      return new UploadPartParams(
              Objects.requireNonNull(bucket, "bucket is required"),
              Objects.requireNonNull(key, "key is required"),
              Objects.requireNonNull(uploadId, "uploadId is required"),
              Objects.requireNonNull(partNumber, "partNumber is required"),
              Objects.requireNonNull(buffer, "buffer is required"),
              checksumAlgorithm);
    }
  }
}