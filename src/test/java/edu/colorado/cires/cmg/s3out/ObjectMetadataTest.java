package edu.colorado.cires.cmg.s3out;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;
import software.amazon.awssdk.services.s3.model.CreateMultipartUploadRequest;

public class ObjectMetadataTest {

  @Test
  public void testBuilder() throws Exception {
    Map<String, String> metadata = new HashMap<>();
    metadata.put("a", "b");

    final String acl = "acl";
    final String cacheControl = "cacheControl";
    final String contentDisposition = "contentDisposition";
    final String contentEncoding = "contentEncoding";
    final String contentLanguage = "contentLanguage";
    final String contentType = "contentType";
    final Instant expires = Instant.now();
    final String grantFullControl = "grantFullControl";
    final String grantRead = "grantRead";
    final String grantReadACP = "grantReadACP";
    final String grantWriteACP = "grantWriteACP";
    final String serverSideEncryption = "serverSideEncryption";
    final String storageClass = "storageClass";
    final String websiteRedirectLocation = "websiteRedirectLocation";
    final String sseCustomerAlgorithm = "sseCustomerAlgorithm";
    final String sseCustomerKey = "sseCustomerKey";
    final String sseCustomerKeyMD5 = "sseCustomerKeyMD5";
    final String ssekmsKeyId = "ssekmsKeyId";
    final String ssekmsEncryptionContext = "ssekmsEncryptionContext";
    final Boolean bucketKeyEnabled = true;
    final String requestPayer = "requestPayer";
    final String tagging = "tagging";
    final String objectLockMode = "objectLockMode";
    final Instant objectLockRetainUntilDate = expires.plus(5, ChronoUnit.DAYS);
    final String objectLockLegalHoldStatus = "objectLockLegalHoldStatus";
    final String expectedBucketOwner = "expectedBucketOwner";
    final String checksumAlgorithm = "checksumAlgorithm";

    ObjectMetadata objectMetadata = ObjectMetadata.builder()
        .metadata(metadata)
        .metadata("foo", "bar")
        .acl(acl)
        .cacheControl(cacheControl)
        .contentDisposition(contentDisposition)
        .contentEncoding(contentEncoding)
        .contentLanguage(contentLanguage)
        .contentType(contentType)
        .expires(expires)
        .grantFullControl(grantFullControl)
        .grantRead(grantRead)
        .grantReadACP(grantReadACP)
        .grantWriteACP(grantWriteACP)
        .serverSideEncryption(serverSideEncryption)
        .storageClass(storageClass)
        .websiteRedirectLocation(websiteRedirectLocation)
        .sseCustomerAlgorithm(sseCustomerAlgorithm)
        .sseCustomerKey(sseCustomerKey)
        .sseCustomerKeyMD5(sseCustomerKeyMD5)
        .ssekmsKeyId(ssekmsKeyId)
        .ssekmsEncryptionContext(ssekmsEncryptionContext)
        .bucketKeyEnabled(bucketKeyEnabled)
        .requestPayer(requestPayer)
        .tagging(tagging)
        .objectLockMode(objectLockMode)
        .objectLockRetainUntilDate(objectLockRetainUntilDate)
        .objectLockLegalHoldStatus(objectLockLegalHoldStatus)
        .expectedBucketOwner(expectedBucketOwner)
        .checksumAlgorithm(checksumAlgorithm)
        .build();

    metadata.put("foo", "bar");

    assertEquals(metadata, objectMetadata.getMetadata());
    assertEquals(acl, objectMetadata.getAcl());
    assertEquals(cacheControl, objectMetadata.getCacheControl());
    assertEquals(contentDisposition, objectMetadata.getContentDisposition());
    assertEquals(contentEncoding, objectMetadata.getContentEncoding());
    assertEquals(contentLanguage, objectMetadata.getContentLanguage());
    assertEquals(contentType, objectMetadata.getContentType());
    assertEquals(expires, objectMetadata.getExpires());
    assertEquals(grantFullControl, objectMetadata.getGrantFullControl());
    assertEquals(grantRead, objectMetadata.getGrantRead());
    assertEquals(grantReadACP, objectMetadata.getGrantReadACP());
    assertEquals(grantWriteACP, objectMetadata.getGrantWriteACP());
    assertEquals(serverSideEncryption, objectMetadata.getServerSideEncryption());
    assertEquals(storageClass, objectMetadata.getStorageClass());
    assertEquals(websiteRedirectLocation, objectMetadata.getWebsiteRedirectLocation());
    assertEquals(sseCustomerAlgorithm, objectMetadata.getSseCustomerAlgorithm());
    assertEquals(sseCustomerKey, objectMetadata.getSseCustomerKey());
    assertEquals(sseCustomerKeyMD5, objectMetadata.getSseCustomerKeyMD5());
    assertEquals(ssekmsKeyId, objectMetadata.getSsekmsKeyId());
    assertEquals(ssekmsEncryptionContext, objectMetadata.getSsekmsEncryptionContext());
    assertEquals(bucketKeyEnabled, objectMetadata.getBucketKeyEnabled());
    assertEquals(requestPayer, objectMetadata.getRequestPayer());
    assertEquals(tagging, objectMetadata.getTagging());
    assertEquals(objectLockMode, objectMetadata.getObjectLockMode());
    assertEquals(objectLockRetainUntilDate, objectMetadata.getObjectLockRetainUntilDate());
    assertEquals(objectLockLegalHoldStatus, objectMetadata.getObjectLockLegalHoldStatus());
    assertEquals(expectedBucketOwner, objectMetadata.getExpectedBucketOwner());
    assertEquals(checksumAlgorithm, objectMetadata.getChecksumAlgorithm());

    CreateMultipartUploadRequest.Builder builder = mock(CreateMultipartUploadRequest.Builder.class);

    objectMetadata.apply(builder);
    verify(builder, times(1)).metadata(metadata);
    verify(builder, times(1)).acl(acl);
    verify(builder, times(1)).cacheControl(cacheControl);
    verify(builder, times(1)).contentDisposition(contentDisposition);
    verify(builder, times(1)).contentEncoding(contentEncoding);
    verify(builder, times(1)).contentLanguage(contentLanguage);
    verify(builder, times(1)).contentType(contentType);
    verify(builder, times(1)).expires(expires);
    verify(builder, times(1)).grantFullControl(grantFullControl);
    verify(builder, times(1)).grantRead(grantRead);
    verify(builder, times(1)).grantReadACP(grantReadACP);
    verify(builder, times(1)).grantWriteACP(grantWriteACP);
    verify(builder, times(1)).serverSideEncryption(serverSideEncryption);
    verify(builder, times(1)).storageClass(storageClass);
    verify(builder, times(1)).websiteRedirectLocation(websiteRedirectLocation);
    verify(builder, times(1)).sseCustomerAlgorithm(sseCustomerAlgorithm);
    verify(builder, times(1)).sseCustomerKey(sseCustomerKey);
    verify(builder, times(1)).sseCustomerKeyMD5(sseCustomerKeyMD5);
    verify(builder, times(1)).ssekmsKeyId(ssekmsKeyId);
    verify(builder, times(1)).ssekmsEncryptionContext(ssekmsEncryptionContext);
    verify(builder, times(1)).bucketKeyEnabled(bucketKeyEnabled);
    verify(builder, times(1)).requestPayer(requestPayer);
    verify(builder, times(1)).tagging(tagging);
    verify(builder, times(1)).objectLockMode(objectLockMode);
    verify(builder, times(1)).objectLockRetainUntilDate(objectLockRetainUntilDate);
    verify(builder, times(1)).objectLockLegalHoldStatus(objectLockLegalHoldStatus);
    verify(builder, times(1)).expectedBucketOwner(expectedBucketOwner);
    verify(builder, times(1)).checksumAlgorithm(checksumAlgorithm);
  }

  @Test
  public void testBuilderNull() throws Exception {

    ObjectMetadata objectMetadata = ObjectMetadata.builder().build();

    assertEquals(new HashMap<>(), objectMetadata.getMetadata());
    assertNull(objectMetadata.getAcl());
    assertNull(objectMetadata.getCacheControl());
    assertNull(objectMetadata.getContentDisposition());
    assertNull(objectMetadata.getContentEncoding());
    assertNull(objectMetadata.getContentLanguage());
    assertNull(objectMetadata.getContentType());
    assertNull(objectMetadata.getExpires());
    assertNull(objectMetadata.getGrantFullControl());
    assertNull(objectMetadata.getGrantRead());
    assertNull(objectMetadata.getGrantReadACP());
    assertNull(objectMetadata.getGrantWriteACP());
    assertNull(objectMetadata.getServerSideEncryption());
    assertNull(objectMetadata.getStorageClass());
    assertNull(objectMetadata.getWebsiteRedirectLocation());
    assertNull(objectMetadata.getSseCustomerAlgorithm());
    assertNull(objectMetadata.getSseCustomerKey());
    assertNull(objectMetadata.getSseCustomerKeyMD5());
    assertNull(objectMetadata.getSsekmsKeyId());
    assertNull(objectMetadata.getSsekmsEncryptionContext());
    assertNull(objectMetadata.getBucketKeyEnabled());
    assertNull(objectMetadata.getRequestPayer());
    assertNull(objectMetadata.getTagging());
    assertNull(objectMetadata.getObjectLockMode());
    assertNull(objectMetadata.getObjectLockRetainUntilDate());
    assertNull(objectMetadata.getObjectLockLegalHoldStatus());
    assertNull(objectMetadata.getExpectedBucketOwner());
    assertNull(objectMetadata.getChecksumAlgorithm());

    CreateMultipartUploadRequest.Builder builder = mock(CreateMultipartUploadRequest.Builder.class);

    objectMetadata.apply(builder);
    verify(builder, times(0)).metadata(any());
    verify(builder, times(0)).acl(any(String.class));
    verify(builder, times(0)).cacheControl(any());
    verify(builder, times(0)).contentDisposition(any());
    verify(builder, times(0)).contentEncoding(any());
    verify(builder, times(0)).contentLanguage(any());
    verify(builder, times(0)).contentType(any());
    verify(builder, times(0)).expires(any());
    verify(builder, times(0)).grantFullControl(any());
    verify(builder, times(0)).grantRead(any());
    verify(builder, times(0)).grantReadACP(any());
    verify(builder, times(0)).grantWriteACP(any());
    verify(builder, times(0)).serverSideEncryption(any(String.class));
    verify(builder, times(0)).storageClass(any(String.class));
    verify(builder, times(0)).websiteRedirectLocation(any());
    verify(builder, times(0)).sseCustomerAlgorithm(any());
    verify(builder, times(0)).sseCustomerKey(any());
    verify(builder, times(0)).sseCustomerKeyMD5(any());
    verify(builder, times(0)).ssekmsKeyId(any());
    verify(builder, times(0)).ssekmsEncryptionContext(any());
    verify(builder, times(0)).bucketKeyEnabled(any());
    verify(builder, times(0)).requestPayer(any(String.class));
    verify(builder, times(0)).tagging(any(String.class));
    verify(builder, times(0)).objectLockMode(any(String.class));
    verify(builder, times(0)).objectLockRetainUntilDate(any(Instant.class));
    verify(builder, times(0)).objectLockLegalHoldStatus(any(String.class));
    verify(builder, times(0)).expectedBucketOwner(any(String.class));
    verify(builder, times(0)).checksumAlgorithm(any(String.class));
  }
}