package edu.colorado.cires.cmg.s3out;

import java.util.Optional;

/**
 * Resolves the MIME type for a file to be uploaded.
 */
public interface ContentTypeResolver {

  /**
   * Resolves the MIME type for a file to be uploaded.
   *
   * @param key the S3 key for the uploaded file
   * @return a populated {@link Optional} with the MIME type or empty if not defined
   */
  Optional<String> resolveContentType(String key);

}
