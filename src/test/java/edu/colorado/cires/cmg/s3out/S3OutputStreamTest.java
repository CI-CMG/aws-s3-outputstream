package edu.colorado.cires.cmg.s3out;

import static org.junit.jupiter.api.Assertions.*;

import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class S3OutputStreamTest {

  private static final Path MOCK_BUCKETS_DIR = Paths.get("target/mock-buckets");
  private static final String BUCKET = "my-test-bucket";
  private static final Path BUCKET_DIR = MOCK_BUCKETS_DIR.resolve(BUCKET);

  @BeforeEach
  public void setup() throws Exception {
    FileUtils.deleteQuietly(BUCKET_DIR.toFile());
    Files.createDirectories(BUCKET_DIR);
  }

  @ParameterizedTest
  @CsvSource({
      "test.txt,100,1,src/test/resources/test.txt,100",
      "test.txt,100,1,src/test/resources/test.txt,1",
      "foo/bar/test.txt,100,1,src/test/resources/test-exact-buffer.txt,100",
      "test.txt,49,1,src/test/resources/test.txt,25",
      "test.txt,1,3,src/test/resources/test.txt,2",
   })
  public void testAutoComplete(String key, int maxBufferSize, int queueSize, String source, int copyBufferSize) throws Exception {
    boolean autoComplete = true;
    Path sourcePath = Paths.get(source);

    FileMockS3ClientMultipartUpload s3 = FileMockS3ClientMultipartUpload.builder().mockBucketDir(MOCK_BUCKETS_DIR).build();

    try (
        InputStream inputStream = Files.newInputStream(sourcePath);
        OutputStream outputStream = new S3OutputStream(s3, BUCKET, key, maxBufferSize, autoComplete, queueSize);
    ) {
      if (copyBufferSize == 1){
        long count;
        int n;
        byte[] buffer = new byte[1];
        for(count = 0L; -1 != (n = inputStream.read(buffer)); count += n) {
          outputStream.write(buffer[0]);
        }
      } else {
        IOUtils.copy(inputStream, outputStream, copyBufferSize);
      }
    }

    String expected = new String(Files.readAllBytes(sourcePath), StandardCharsets.UTF_8);
    String actual = new String(Files.readAllBytes(BUCKET_DIR.resolve(key)), StandardCharsets.UTF_8);

    assertEquals(expected, actual);
    assertEquals(0, s3.getUploadStateMap().size());
  }

  @ParameterizedTest
  @CsvSource({
      "test.txt,1,src/test/resources/test.txt,100",
      "test.txt,1,src/test/resources/test.txt,1",
      "foo/bar/test.txt,1,src/test/resources/test-exact-buffer.txt,100",
      "test.txt,1,src/test/resources/test.txt,25",
      "test.txt,3,src/test/resources/test.txt,2",
  })
  public void testBuilder(String key, int queueSize, String source, int copyBufferSize) throws Exception {
    boolean autoComplete = true;
    Path sourcePath = Paths.get(source);

    FileMockS3ClientMultipartUpload s3 = FileMockS3ClientMultipartUpload.builder().mockBucketDir(MOCK_BUCKETS_DIR).build();

    try (
        InputStream inputStream = Files.newInputStream(sourcePath);
        OutputStream outputStream = S3OutputStream.builder().s3(s3).bucket(BUCKET).key(key).autoComplete(true).partSizeMib(5).uploadQueueSize(queueSize).build();
    ) {
        IOUtils.copy(inputStream, outputStream, copyBufferSize);
    }

    String expected = new String(Files.readAllBytes(sourcePath), StandardCharsets.UTF_8);
    String actual = new String(Files.readAllBytes(BUCKET_DIR.resolve(key)), StandardCharsets.UTF_8);

    assertEquals(expected, actual);
    assertEquals(0, s3.getUploadStateMap().size());
  }

  @Test
  public void testDone() throws Exception {
    String key = "test.txt";
    int maxBufferSize = 100;
    boolean autoComplete = false;
    int queueSize = 1;
    Path source = Paths.get("src/test/resources/test.txt");

    FileMockS3ClientMultipartUpload s3 = FileMockS3ClientMultipartUpload.builder().mockBucketDir(MOCK_BUCKETS_DIR).build();

    try (
        InputStream inputStream = Files.newInputStream(source);
        S3OutputStream outputStream = new S3OutputStream(s3, BUCKET, key, maxBufferSize, autoComplete, queueSize);
    ) {
      IOUtils.copy(inputStream, outputStream);
      outputStream.done();
    }

    String expected = new String(Files.readAllBytes(source), StandardCharsets.UTF_8);
    String actual = new String(Files.readAllBytes(BUCKET_DIR.resolve(key)), StandardCharsets.UTF_8);

    assertEquals(expected, actual);
    assertEquals(0, s3.getUploadStateMap().size());
  }

  @Test
  public void testDoneAbort() throws Exception {
    String key = "test.txt";
    int maxBufferSize = 100;
    boolean autoComplete = false;
    int queueSize = 1;
    Path source = Paths.get("src/test/resources/test.txt");

    FileMockS3ClientMultipartUpload s3 = FileMockS3ClientMultipartUpload.builder().mockBucketDir(MOCK_BUCKETS_DIR).build();

    RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
      try (
          InputStream inputStream = Files.newInputStream(source);
          S3OutputStream outputStream = new S3OutputStream(s3, BUCKET, key, maxBufferSize, autoComplete, queueSize);
      ) {
        IOUtils.copy(inputStream, outputStream);
        throw new RuntimeException("test error");
      }
    });

    assertEquals("test error", thrown.getMessage());

    assertEquals(0, s3.getUploadStateMap().size());

  }

}