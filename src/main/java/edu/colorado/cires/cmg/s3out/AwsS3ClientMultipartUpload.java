package edu.colorado.cires.cmg.s3out;

import java.nio.ByteBuffer;
import java.util.Collection;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.AbortMultipartUploadRequest;
import software.amazon.awssdk.services.s3.model.CompleteMultipartUploadRequest;
import software.amazon.awssdk.services.s3.model.CompletedMultipartUpload;
import software.amazon.awssdk.services.s3.model.CompletedPart;
import software.amazon.awssdk.services.s3.model.CreateMultipartUploadRequest;
import software.amazon.awssdk.services.s3.model.UploadPartRequest;

/**
 * A {@link S3ClientMultipartUpload} that uses a {@link S3Client} to make calls to the AWS S3 SDK.
 */
public class AwsS3ClientMultipartUpload implements S3ClientMultipartUpload {

  /**
   * Creates a new {@link Builder} to build a S3ClientMultipartUpload
   *
   * @return a new Builder
   */
  public static Builder builder() {
    return new Builder();
  }

  /**
   * Builds a {@link AwsS3ClientMultipartUpload}
   */
  public static class Builder {
    private S3Client s3;
    private ContentTypeResolver contentTypeResolver = new DefaultContentTypeResolver();

    private Builder() {

    }

    /**
     * Sets the {@link S3Client}.
     * Required.
     *
     * @param s3 the {@link S3Client}
     * @return this Builder
     */
    public Builder s3(S3Client s3) {
      this.s3 = s3;
      return this;
    }

    /**
     * Sets the {@link ContentTypeResolver}
     * Default: {@link DefaultContentTypeResolver}
     *
     * @param contentTypeResolver the {@link ContentTypeResolver}
     * @return this Builder
     */
    public Builder contentTypeResolver(ContentTypeResolver contentTypeResolver) {
      this.contentTypeResolver = contentTypeResolver;
      return this;
    }

    /**
     * Builds a new {@link AwsS3ClientMultipartUpload}
     *
     * @return a new {@link AwsS3ClientMultipartUpload}
     */
    public AwsS3ClientMultipartUpload build() {
      return new AwsS3ClientMultipartUpload(s3, contentTypeResolver);
    }
  }

  private final S3Client s3;
  private final ContentTypeResolver contentTypeResolver;

  private AwsS3ClientMultipartUpload(S3Client s3, ContentTypeResolver contentTypeResolver) {
    this.s3 = s3;
    this.contentTypeResolver = contentTypeResolver;
  }

  @Override
  public String createMultipartUpload(String bucket, String key, ObjectMetadata objectMetadata) {

    CreateMultipartUploadRequest.Builder builder = CreateMultipartUploadRequest.builder()
            .bucket(bucket)
            .key(key);

    if (objectMetadata != null) {
      objectMetadata.apply(builder);
    }

    contentTypeResolver.resolveContentType(key).ifPresent(builder::contentType);

    return s3.createMultipartUpload(builder.build()).uploadId();
  }

  @Override
  public CompletedPart uploadPart(String bucket, String key, String uploadId, int partNumber, ByteBuffer buffer) {
    UploadPartRequest uploadPartRequest = UploadPartRequest.builder()
        .bucket(bucket)
        .key(key)
        .uploadId(uploadId)
        .partNumber(partNumber).build();

    String etag = s3.uploadPart(uploadPartRequest, RequestBody.fromRemainingByteBuffer(buffer)).eTag();

    return CompletedPart.builder().partNumber(partNumber).eTag(etag).build();
  }

  @Override
  public void completeMultipartUpload(String bucket, String key, String uploadId, Collection<CompletedPart> completedParts) {
    CompletedMultipartUpload completedMultipartUpload = CompletedMultipartUpload.builder()
        .parts(completedParts)
        .build();

    CompleteMultipartUploadRequest completeMultipartUploadRequest =
        CompleteMultipartUploadRequest.builder()
            .bucket(bucket)
            .key(key)
            .uploadId(uploadId)
            .multipartUpload(completedMultipartUpload)
            .build();

    s3.completeMultipartUpload(completeMultipartUploadRequest);
  }

  @Override
  public void abortMultipartUpload(String bucket, String key, String uploadId) {
    s3.abortMultipartUpload(AbortMultipartUploadRequest.builder()
        .bucket(bucket)
        .key(key)
        .uploadId(uploadId)
        .build());
  }
}
