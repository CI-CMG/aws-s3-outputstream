package edu.colorado.cires.cmg.s3out;

import java.util.Objects;
import java.util.Optional;

public class MultipartUploadRequest {

  /**
   * Creates a new builder for a MultipartUploadRequest.
   *
   * @return a new builder for a MultipartUploadRequest
   */
  public static Builder builder() {
    return new Builder();
  }

  private final String bucket;
  private final String key;
  private final String checksumAlgorithm;
  private final ObjectMetadataCustomizer objectMetadata;

  private MultipartUploadRequest(String bucket, String key, String checksumAlgorithm, ObjectMetadataCustomizer objectMetadata) {
    this.bucket = bucket;
    this.key = key;
    this.checksumAlgorithm = checksumAlgorithm;
    this.objectMetadata = objectMetadata;
  }

  /**
   * Returns the bucket name.
   * Never null.
   * @return the bucket name
   */
  public String getBucket() {
    return bucket;
  }

  /**
   * Returns the bucket key
   * Never null.
   * @return the bucket key
   */
  public String getKey() {
    return key;
  }

  /**
   * Returns the checksum algorithm.
   * Never null.
   * @return the checksum algorithm
   */
  public String getChecksumAlgorithm() {
    return checksumAlgorithm;
  }

  /**
   * Returns and {@link Optional} containing a {@link ObjectMetadataCustomizer} if available or an empty {@link Optional} otherwise.
   * @return an {@link Optional}lly wrapped {@link ObjectMetadataCustomizer}
   */
  public Optional<ObjectMetadataCustomizer> getObjectMetadata() {
    return Optional.ofNullable(objectMetadata);
  }


  /**
   * Builds a {@link MultipartUploadRequest}.
   */
  public static class Builder {

    private String bucket;
    private String key;
    private String checksumAlgorithm;
    private ObjectMetadataCustomizer objectMetadata;

    private Builder() {

    }

    /**
     * Sets the bucket name for the {@link MultipartUploadRequest}. Required.
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
     * Sets the checksum algorithm for the {@link MultipartUploadRequest}. Required.
     *
     * @param checksumAlgorithm the checksum algorithm
     * @return this Builder
     */
    public Builder checksumAlgorithm(String checksumAlgorithm) {
      this.checksumAlgorithm = checksumAlgorithm;
      return this;
    }

    /**
     * Sets the metadata that will be applied to a file.
     *
     * @param objectMetadata the file metadata
     * @return this Builder
     */
    public Builder objectMetadata(ObjectMetadataCustomizer objectMetadata) {
      this.objectMetadata = objectMetadata;
      return this;
    }

    /**
     * Builds a new {@link MultipartUploadRequest}
     *
     * @return a new {@link MultipartUploadRequest}
     */
    public MultipartUploadRequest build() {
      return new MultipartUploadRequest(
          Objects.requireNonNull(bucket, "bucket is required"),
          Objects.requireNonNull(key, "key is required"),
          checksumAlgorithm,
          objectMetadata);
    }
  }
}
