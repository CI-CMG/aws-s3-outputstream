package edu.colorado.cires.cmg.s3out;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class DefaultContentTypeResolverTest {

  @Test
  public void testMapped() throws Exception {
    DefaultContentTypeResolver resolver = new DefaultContentTypeResolver();
    assertEquals("image/png", resolver.resolveContentType("foo/bar/cats.png").get());
  }

  @Test
  public void testNotFound() throws Exception {
    DefaultContentTypeResolver resolver = new DefaultContentTypeResolver();
    assertFalse(resolver.resolveContentType("foo/bar/cats.dog").isPresent());
  }

  @Test
  public void testNoExt() throws Exception {
    DefaultContentTypeResolver resolver = new DefaultContentTypeResolver();
    assertFalse(resolver.resolveContentType("foo/bar/cats").isPresent());
  }
}