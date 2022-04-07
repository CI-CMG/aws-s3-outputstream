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
  private final ObjectMetadata objectMetadata;

  private MultipartUploadRequest(String bucket, String key, ObjectMetadata objectMetadata) {
    this.bucket = bucket;
    this.key = key;
    this.objectMetadata = objectMetadata;
  }

  public String getBucket() {
    return bucket;
  }

  public String getKey() {
    return key;
  }

  public Optional<ObjectMetadata> getObjectMetadata() {
    return Optional.ofNullable(objectMetadata);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    MultipartUploadRequest that = (MultipartUploadRequest) o;
    return Objects.equals(bucket, that.bucket) && Objects.equals(key, that.key) && Objects.equals(objectMetadata,
        that.objectMetadata);
  }

  @Override
  public int hashCode() {
    return Objects.hash(bucket, key, objectMetadata);
  }

  @Override
  public String toString() {
    return "MultipartUploadConfiguration{" +
        "bucket='" + bucket + '\'' +
        ", key='" + key + '\'' +
        ", objectMetadata=" + objectMetadata +
        '}';
  }

  /**
   * Builds a {@link MultipartUploadRequest}.
   */
  public static class Builder {

    private String bucket;
    private String key;
    private ObjectMetadata objectMetadata;

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
     * Sets the metadata that will be applied to a file.
     *
     * @param objectMetadata the file metadata
     * @return this Builder
     */
    public Builder objectMetadata(ObjectMetadata objectMetadata) {
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
          objectMetadata);
    }
  }
}
