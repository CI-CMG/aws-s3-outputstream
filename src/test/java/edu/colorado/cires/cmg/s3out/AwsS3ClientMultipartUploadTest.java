package edu.colorado.cires.cmg.s3out;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.CreateMultipartUploadRequest;
import software.amazon.awssdk.services.s3.model.CreateMultipartUploadResponse;

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
}