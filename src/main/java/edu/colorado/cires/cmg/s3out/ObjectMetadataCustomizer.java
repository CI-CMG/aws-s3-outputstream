package edu.colorado.cires.cmg.s3out;

import software.amazon.awssdk.services.s3.model.CreateMultipartUploadRequest.Builder;

/**
 * Allows for customization of the S3 object metadata when uploading
 */
public interface ObjectMetadataCustomizer {

  /**
   * Applies any customizations to the upload request.
   * @param builder the {@link Builder}
   */
  void apply(Builder builder);
}
