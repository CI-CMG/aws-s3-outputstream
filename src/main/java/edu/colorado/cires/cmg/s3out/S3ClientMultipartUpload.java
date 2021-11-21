package edu.colorado.cires.cmg.s3out;

import java.nio.ByteBuffer;
import java.util.Collection;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.CompletedPart;

/**
 * Acts as a wrapper around a {@link S3Client}, which allows for implementations that could
 * allow for testing or custom behavior.
 */
public interface S3ClientMultipartUpload {

  /**
   * Creates a default S3ClientMultipartUpload that should work for most scenarios.
   *
   * @param s3 the {@link S3Client} to access a S3 bucket
   * @return a default implementation of S3ClientMultipartUpload
   */
  static S3ClientMultipartUpload createDefault(S3Client s3) {
    return AwsS3ClientMultipartUpload.builder().s3(s3).build();
  }

  /**
   * Initiates a multipart upload to a S3 bucket.
   *
   * @param bucket the bucket name
   * @param key the key where a file will be uploaded to in the bucket
   * @return a upload ID for the pending upload
   */
  String createMultipartUpload(String bucket, String key);

  /**
   * Uploads a part of a multipart upload.
   *
   * @param bucket the bucket name
   * @param key the key where a file will be uploaded to in the bucket
   * @param uploadId the upload ID for the initiated upload
   * @param partNumber the incrementing number for this part in the upload
   * @param buffer a {@link ByteBuffer} containing the data to be uploaded in this part
   * @return a {@link CompletedPart} response object from the completed part upload
   */
  CompletedPart uploadPart(String bucket, String key, String uploadId, int partNumber, ByteBuffer buffer);

  /**
   * Triggers completion of the multipart upload.
   *
   * @param bucket the bucket name
   * @param key the key where a file will be uploaded to in the bucket
   * @param uploadId the upload ID for the initiated upload
   * @param completedParts a collection of {@link CompletedPart} for all the parts uploaded
   */
  void completeMultipartUpload(String bucket, String key, String uploadId, Collection<CompletedPart> completedParts);

  /**
   * Signals an abortion of a multipart upload.
   *
   * @param bucket the bucket name
   * @param key the key where a file will be uploaded to in the bucket
   * @param uploadId the upload ID for the initiated upload
   */
  void abortMultipartUpload(String bucket, String key, String uploadId);
}
