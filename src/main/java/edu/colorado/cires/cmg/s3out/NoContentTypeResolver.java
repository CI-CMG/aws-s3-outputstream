package edu.colorado.cires.cmg.s3out;

import java.util.Optional;

/**
 * A {@link ContentTypeResolver} that never resolves the MIME type.
 */
public class NoContentTypeResolver implements ContentTypeResolver{

  @Override
  public Optional<String> resolveContentType(String key) {
    return Optional.empty();
  }
}
