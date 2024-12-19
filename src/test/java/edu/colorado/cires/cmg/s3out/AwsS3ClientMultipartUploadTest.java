package edu.colorado.cires.cmg.s3out;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.nio.ByteBuffer;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.ChecksumAlgorithm;
import software.amazon.awssdk.services.s3.model.CompletedPart;
import software.amazon.awssdk.services.s3.model.CreateMultipartUploadRequest;
import software.amazon.awssdk.services.s3.model.CreateMultipartUploadResponse;
import software.amazon.awssdk.services.s3.model.UploadPartRequest;
import software.amazon.awssdk.services.s3.model.UploadPartResponse;

public class AwsS3ClientMultipartUploadTest {

  @Test
  public void testObjectMetadata() throws Exception {
    S3Client s3Client = mock(S3Client.class);
    ContentTypeResolver contentTypeResolver = mock(ContentTypeResolver.class);
    CreateMultipartUploadResponse createMultipartUploadResponse = CreateMultipartUploadResponse.builder().uploadId("myUploadId").build();

    when(contentTypeResolver.resolveContentType(any())).thenReturn(Optional.of("defaultContentType"));
    when(s3Client.createMultipartUpload(any(CreateMultipartUploadRequest.class))).thenReturn(createMultipartUploadResponse);

    AwsS3ClientMultipartUpload awsS3ClientMultipartUpload = AwsS3ClientMultipartUpload.builder()
        .s3(s3Client)
        .contentTypeResolver(contentTypeResolver)
        .build();
    String result = awsS3ClientMultipartUpload.createMultipartUpload(
        MultipartUploadRequest.builder()
            .bucket("myBucket")
            .key("myKey")
            .objectMetadata(ObjectMetadata.builder()
                .acl("myAcl")
                .contentType("myContentType")
                .build())
            .build());

    assertEquals("myUploadId", result);

    ArgumentCaptor<CreateMultipartUploadRequest> argument = ArgumentCaptor.forClass(CreateMultipartUploadRequest.class);
    verify(s3Client).createMultipartUpload(argument.capture());
    assertEquals("myAcl", argument.getValue().aclAsString());
    assertEquals("myContentType", argument.getValue().contentType());

  }

  @Test
  public void testObjectMetadataDefaultContentType() throws Exception {
    S3Client s3Client = mock(S3Client.class);
    ContentTypeResolver contentTypeResolver = mock(ContentTypeResolver.class);
    CreateMultipartUploadResponse createMultipartUploadResponse = CreateMultipartUploadResponse.builder().uploadId("myUploadId").build();

    when(contentTypeResolver.resolveContentType(any())).thenReturn(Optional.of("defaultContentType"));
    when(s3Client.createMultipartUpload(any(CreateMultipartUploadRequest.class))).thenReturn(createMultipartUploadResponse);

    AwsS3ClientMultipartUpload awsS3ClientMultipartUpload = AwsS3ClientMultipartUpload.builder()
        .s3(s3Client)
        .contentTypeResolver(contentTypeResolver)
        .build();
    String result = awsS3ClientMultipartUpload.createMultipartUpload(
        MultipartUploadRequest.builder()
            .bucket("myBucket")
            .key("myKey")
            .objectMetadata(ObjectMetadata.builder()
                .acl("myAcl")
                .build())
            .build());

    assertEquals("myUploadId", result);

    ArgumentCaptor<CreateMultipartUploadRequest> argument = ArgumentCaptor.forClass(CreateMultipartUploadRequest.class);
    verify(s3Client).createMultipartUpload(argument.capture());
    assertEquals("myAcl", argument.getValue().aclAsString());
    assertEquals("defaultContentType", argument.getValue().contentType());

  }

  @Test
  public void testUploadPartParams() throws Exception {
    S3Client s3Client = mock(S3Client.class);
    ContentTypeResolver contentTypeResolver = mock(ContentTypeResolver.class);
    UploadPartResponse uploadPartResponse = UploadPartResponse.builder()
        .checksumCRC32("myChecksumCRC32")
        .checksumCRC32C("myChecksumCRC32C")
        .checksumSHA1("myChecksumSHA1")
        .checksumSHA256("myChecksumSHA256")
        .eTag("myEtag")
        .build();

    when(s3Client.uploadPart(any(UploadPartRequest.class), any(RequestBody.class))).thenReturn(uploadPartResponse);

    AwsS3ClientMultipartUpload awsS3ClientMultipartUpload = AwsS3ClientMultipartUpload.builder()
        .s3(s3Client)
        .contentTypeResolver(contentTypeResolver)
        .build();

    UploadPartParams params = UploadPartParams.builder()
        .bucket("myBucket")
        .key("myKey")
        .uploadId("myUploadId")
        .partNumber(5)
        .buffer(ByteBuffer.allocate(256))
        .checksumAlgorithm("SHA1")
        .build();

    CompletedPart completedPart = awsS3ClientMultipartUpload.uploadPart(params);

    ArgumentCaptor<UploadPartRequest> uploadPartRequestArg = ArgumentCaptor.forClass(UploadPartRequest.class);
    ArgumentCaptor<RequestBody> requestBodyArg = ArgumentCaptor.forClass(RequestBody.class);
    verify(s3Client).uploadPart(uploadPartRequestArg.capture(), requestBodyArg.capture());
    UploadPartRequest uploadPartRequest = uploadPartRequestArg.getValue();
    RequestBody requestBody = requestBodyArg.getValue();

    assertEquals("myBucket", uploadPartRequest.bucket());
    assertEquals("myKey", uploadPartRequest.key());
    assertEquals(ChecksumAlgorithm.SHA1, uploadPartRequest.checksumAlgorithm());
    assertEquals("myUploadId", uploadPartRequest.uploadId());
    assertEquals(5, uploadPartRequest.partNumber());

    assertEquals(256, requestBody.contentLength());

    assertEquals(5, completedPart.partNumber());
    assertEquals("myChecksumCRC32", completedPart.checksumCRC32());
    assertEquals("myChecksumCRC32C", completedPart.checksumCRC32C());
    assertEquals("myChecksumSHA1", completedPart.checksumSHA1());
    assertEquals("myChecksumSHA256", completedPart.checksumSHA256());
    assertEquals("myEtag", completedPart.eTag());
  }
}