package edu.colorado.cires.cmg.s3out;

import java.time.Instant;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import software.amazon.awssdk.services.s3.model.CreateMultipartUploadRequest;

/**
 * Implementation of {@link ObjectMetadataCustomizer} that allows setting most
 * AWS SDK object metadata through a builder.
 */
public class ObjectMetadata implements ObjectMetadataCustomizer {

  private final String acl;
  private final String cacheControl;
  private final String contentDisposition;
  private final String contentEncoding;
  private final String contentLanguage;
  private final String contentType;
  private final Instant expires;
  private final String grantFullControl;
  private final String grantRead;
  private final String grantReadACP;
  private final String grantWriteACP;
  private final Map<String, String> metadata;
  private final String serverSideEncryption;
  private final String storageClass;
  private final String websiteRedirectLocation;
  private final String sseCustomerAlgorithm;
  private final String sseCustomerKey;
  private final String sseCustomerKeyMD5;
  private final String ssekmsKeyId;
  private final String ssekmsEncryptionContext;
  private final Boolean bucketKeyEnabled;
  private final String requestPayer;
  private final String tagging;
  private final String objectLockMode;
  private final Instant objectLockRetainUntilDate;
  private final String objectLockLegalHoldStatus;
  private final String expectedBucketOwner;
  private final String checksumAlgorithm;

  /**
   * Returns a new builder for an {@link ObjectMetadata}
   * @return a new builder for an {@link ObjectMetadata}
   */
  public static Builder builder() {
    return new Builder();
  }

  private ObjectMetadata(
      String acl,
      String cacheControl,
      String contentDisposition,
      String contentEncoding,
      String contentLanguage,
      String contentType,
      Instant expires,
      String grantFullControl,
      String grantRead,
      String grantReadACP,
      String grantWriteACP,
      Map<String, String> metadata,
      String serverSideEncryption,
      String storageClass,
      String websiteRedirectLocation,
      String sseCustomerAlgorithm,
      String sseCustomerKey,
      String sseCustomerKeyMD5,
      String ssekmsKeyId,
      String ssekmsEncryptionContext,
      Boolean bucketKeyEnabled,
      String requestPayer,
      String tagging,
      String objectLockMode,
      Instant objectLockRetainUntilDate,
      String objectLockLegalHoldStatus,
      String expectedBucketOwner,
      String checksumAlgorithm) {
    this.acl = acl;
    this.cacheControl = cacheControl;
    this.contentDisposition = contentDisposition;
    this.contentEncoding = contentEncoding;
    this.contentLanguage = contentLanguage;
    this.contentType = contentType;
    this.expires = expires;
    this.grantFullControl = grantFullControl;
    this.grantRead = grantRead;
    this.grantReadACP = grantReadACP;
    this.grantWriteACP = grantWriteACP;
    this.metadata = metadata;
    this.serverSideEncryption = serverSideEncryption;
    this.storageClass = storageClass;
    this.websiteRedirectLocation = websiteRedirectLocation;
    this.sseCustomerAlgorithm = sseCustomerAlgorithm;
    this.sseCustomerKey = sseCustomerKey;
    this.sseCustomerKeyMD5 = sseCustomerKeyMD5;
    this.ssekmsKeyId = ssekmsKeyId;
    this.ssekmsEncryptionContext = ssekmsEncryptionContext;
    this.bucketKeyEnabled = bucketKeyEnabled;
    this.requestPayer = requestPayer;
    this.tagging = tagging;
    this.objectLockMode = objectLockMode;
    this.objectLockRetainUntilDate = objectLockRetainUntilDate;
    this.objectLockLegalHoldStatus = objectLockLegalHoldStatus;
    this.expectedBucketOwner = expectedBucketOwner;
    this.checksumAlgorithm = checksumAlgorithm;
  }

  /**
   * Gets the canned ACL to apply to the object.
   *
   * @return the canned ACL to apply to the object.
   */
  public String getAcl() {
    return acl;
  }

  /**
   * Gets the caching behavior along the request/reply chain.
   *
   * @return the caching behavior along the request/reply chain.
   */
  public String getCacheControl() {
    return cacheControl;
  }

  /**
   * Gets presentational information for the object.
   *
   * @return presentational information for the object
   */
  public String getContentDisposition() {
    return contentDisposition;
  }

  /**
   * Gets content encodings have been applied to the object and thus what decoding mechanisms must be applied to obtain the media-type
   * referenced by the Content-Type header field.
   *
   * @return content encodings have been applied to the object and thus what decoding mechanisms must be applied to obtain the media-type
   *    referenced by the Content-Type header field.
   */
  public String getContentEncoding() {
    return contentEncoding;
  }

  /**
   * Gets the language the content is in.
   *
   * @return the language the content is in
   */
  public String getContentLanguage() {
    return contentLanguage;
  }

  /**
   * Gets the MIME type describing the format of the object data.
   *
   * @return the MIME type describing the format of the object data
   */
  public String getContentType() {
    return contentType;
  }

  /**
   * Gets the date and time at which the object is no longer cacheable.
   *
   * @return the date and time at which the object is no longer cacheable
   */
  public Instant getExpires() {
    return expires;
  }

  /**
   * Gets the grantee READ, READ_ACP, and WRITE_ACP permissions on the object.
   *
   * @return the grantee READ, READ_ACP, and WRITE_ACP permissions on the object.
   */
  public String getGrantFullControl() {
    return grantFullControl;
  }

  /**
   * Gets grantee state to read the object data and its metadata.
   *
   * @return grantee state to read the object data and its metadata.
   */
  public String getGrantRead() {
    return grantRead;
  }

  /**
   * Gets grantee state to read the object ACL.
   *
   * @return Gets grantee state to read the object ACL
   */
  public String getGrantReadACP() {
    return grantReadACP;
  }

  /**
   * Gets grantee state to write the ACL for the applicable object.
   *
   * @return grantee state to write the ACL for the applicable object
   */
  public String getGrantWriteACP() {
    return grantWriteACP;
  }

  /**
   * Gets an unmodifiable map of metadata to store with the object in S3.
   * @return an unmodifiable map of metadata to store with the object in S3
   */
  public Map<String, String> getMetadata() {
    return metadata;
  }

  /**
   * Gets the server-side encryption algorithm used when storing this object in Amazon S3 (for example, AES256, aws:kms).
   *
   * @return the serve-side encryption algorithm used wrhen storing this object in Amazon S3 (for example, AES256, aws:kms)
   */
  public String getServerSideEncryption() {
    return serverSideEncryption;
  }

  /**
   * Gets the Storage Class to store newly created objects.
   * @return the Storage Class to store newly created objects
   */
  public String getStorageClass() {
    return storageClass;
  }

  /**
   * Gets the redirects requests for this object.
   *
   * @return the redirects requests for this object.
   */
  public String getWebsiteRedirectLocation() {
    return websiteRedirectLocation;
  }

  /**
   * Gets the algorithm to use to when encrypting the object (for example, AES256).
   *
   * @return the algorithm to use to when encrypting the object (for example, AES256).
   */
  public String getSseCustomerAlgorithm() {
    return sseCustomerAlgorithm;
  }

  /**
   * Gets the customer-provided encryption key for Amazon S3 to use in encrypting data. This value is used to store the object and then it is
   * discarded; Amazon S3 does not store the encryption key. The key must be appropriate for use with the algorithm specified in the
   * x-amz-server-side-encryption-customer-algorithm header.
   *
   * @return the customer-provided encryption key for Amazon S3 to use in encrypting data. This value is used to store the object and then it is
   *    discarded; Amazon S3 does not store the encryption key. The key must be appropriate for use with the algorithm specified in the
   *    x-amz-server-side-encryption-customer-algorithm header.
   */
  public String getSseCustomerKey() {
    return sseCustomerKey;
  }

  /**
   * Gets the 128-bit MD5 digest of the encryption key according to RFC 1321. Amazon S3 uses this header for a message integrity check to
   * ensure that the encryption key was transmitted without error.
   *
   * @return the 128-bit MD5 digest of the encryption key according to RFC 1321. Amazon S3 uses this header for a message integrity check to
   *    ensure that the encryption key was transmitted without error.
   */
  public String getSseCustomerKeyMD5() {
    return sseCustomerKeyMD5;
  }

  /**
   * Gets the ID of the symmetric customer managed key to use for object encryption. All GET and PUT requests for an object protected by Amazon
   * Web Services KMS will fail if not made via SSL or using SigV4.
   *
   * @return the ID of the symmetric customer managed key to use for object encryption. All GET and PUT requests for an object protected by Amazon
   *    Web Services KMS will fail if not made via SSL or using SigV4.
   */
  public String getSsekmsKeyId() {
    return ssekmsKeyId;
  }

  /**
   * Gets the Amazon Web Services KMS Encryption Context to use for object encryption. The value of this header is a base64-encoded UTF-8
   * string holding JSON with the encryption context key-value pairs.
   *
   * @return the Amazon Web Services KMS Encryption Context to use for object encryption. The value of this header is a base64-encoded UTF-8
   *    string holding JSON with the encryption context key-value pairs.
   */
  public String getSsekmsEncryptionContext() {
    return ssekmsEncryptionContext;
  }

  /**
   * Gets whether Amazon S3 should use an S3 Bucket Key for object encryption with server-side encryption using AWS KMS (SSE-KMS). Setting this
   * header to true causes Amazon S3 to use an S3 Bucket Key for object encryption with SSE-KMS.
   *
   * @return whether Amazon S3 should use an S3 Bucket Key for object encryption with server-side encryption using AWS KMS (SSE-KMS). Setting this
   *    header to true causes Amazon S3 to use an S3 Bucket Key for object encryption with SSE-KMS.
   */
  public Boolean getBucketKeyEnabled() {
    return bucketKeyEnabled;
  }

  /**
   * Gets the value of the RequestPayer property for this object.
   *
   * @return the value of the RequestPayer property for this object.
   */
  public String getRequestPayer() {
    return requestPayer;
  }

  /**
   * Gets the tag-set for the object. The tag-set must be encoded as URL Query parameters.
   *
   * @return the tag-set for the object. The tag-set must be encoded as URL Query parameters.
   */
  public String getTagging() {
    return tagging;
  }

  /**
   * Gets the Object Lock mode that you want to apply to the uploaded object.
   *
   * @return the Object Lock mode that you want to apply to the uploaded object.
   */
  public String getObjectLockMode() {
    return objectLockMode;
  }

  /**
   * Gets the date and time when you want the Object Lock to expire.
   *
   * @return the date and time when you want the Object Lock to expire.
   */
  public Instant getObjectLockRetainUntilDate() {
    return objectLockRetainUntilDate;
  }

  /**
   * Gets whether you want to apply a legal hold to the uploaded object.
   *
   * @return whether you want to apply a legal hold to the uploaded object.
   */
  public String getObjectLockLegalHoldStatus() {
    return objectLockLegalHoldStatus;
  }

  /**
   * Gets the account ID of the expected bucket owner. If the bucket is owned by a different account, the request fails with the HTTP status code 403
   * Forbidden (access denied).
   *
   * @return the account ID of the expected bucket owner. If the bucket is owned by a different account, the request fails with the HTTP status code 403
   *    Forbidden (access denied).
   */
  public String getExpectedBucketOwner() {
    return expectedBucketOwner;
  }

  /**
   * Get the algorithm you want Amazon S3 to use to create the checksum for the object.
   *
   * @return the algorithm you want Amazon S3 to use to create the checksum for the object
   */
  public String getChecksumAlgorithm() {
    return checksumAlgorithm;
  }

  @Override
  public void apply(CreateMultipartUploadRequest.Builder builder) {
    if (acl != null) {
      builder.acl(acl);
    }
    if (cacheControl != null) {
      builder.cacheControl(cacheControl);
    }
    if (contentDisposition != null) {
      builder.contentDisposition(contentDisposition);
    }
    if (contentEncoding != null) {
      builder.contentEncoding(contentEncoding);
    }
    if (contentLanguage != null) {
      builder.contentLanguage(contentLanguage);
    }
    if (contentType != null) {
      builder.contentType(contentType);
    }
    if (expires != null) {
      builder.expires(expires);
    }
    if (grantFullControl != null) {
      builder.grantFullControl(grantFullControl);
    }
    if (grantRead != null) {
      builder.grantRead(grantRead);
    }
    if (grantReadACP != null) {
      builder.grantReadACP(grantReadACP);
    }
    if (grantWriteACP != null) {
      builder.grantWriteACP(grantWriteACP);
    }
    if (!metadata.isEmpty()) {
      builder.metadata(metadata);
    }
    if (serverSideEncryption != null) {
      builder.serverSideEncryption(serverSideEncryption);
    }
    if (storageClass != null) {
      builder.storageClass(storageClass);
    }
    if (websiteRedirectLocation != null) {
      builder.websiteRedirectLocation(websiteRedirectLocation);
    }
    if (sseCustomerAlgorithm != null) {
      builder.sseCustomerAlgorithm(sseCustomerAlgorithm);
    }
    if (sseCustomerKey != null) {
      builder.sseCustomerKey(sseCustomerKey);
    }
    if (sseCustomerKeyMD5 != null) {
      builder.sseCustomerKeyMD5(sseCustomerKeyMD5);
    }
    if (ssekmsKeyId != null) {
      builder.ssekmsKeyId(ssekmsKeyId);
    }
    if (ssekmsEncryptionContext != null) {
      builder.ssekmsEncryptionContext(ssekmsEncryptionContext);
    }
    if (bucketKeyEnabled != null) {
      builder.bucketKeyEnabled(bucketKeyEnabled);
    }
    if (requestPayer != null) {
      builder.requestPayer(requestPayer);
    }
    if (tagging != null) {
      builder.tagging(tagging);
    }
    if (objectLockMode != null) {
      builder.objectLockMode(objectLockMode);
    }
    if (objectLockRetainUntilDate != null) {
      builder.objectLockRetainUntilDate(objectLockRetainUntilDate);
    }
    if (objectLockLegalHoldStatus != null) {
      builder.objectLockLegalHoldStatus(objectLockLegalHoldStatus);
    }
    if (expectedBucketOwner != null) {
      builder.expectedBucketOwner(expectedBucketOwner);
    }
    if (checksumAlgorithm != null) {
      builder.checksumAlgorithm(checksumAlgorithm);
    }
  }

  /**
   * Builder for an {@link ObjectMetadata}
   */
  public static class Builder {

    private Map<String, String> metadata = new HashMap<>();
    private String acl;
    private String cacheControl;
    private String contentDisposition;
    private String contentEncoding;
    private String contentLanguage;
    private String contentType;
    private Instant expires;
    private String grantFullControl;
    private String grantRead;
    private String grantReadACP;
    private String grantWriteACP;
    private String serverSideEncryption;
    private String storageClass;
    private String websiteRedirectLocation;
    private String sseCustomerAlgorithm;
    private String sseCustomerKey;
    private String sseCustomerKeyMD5;
    private String ssekmsKeyId;
    private String ssekmsEncryptionContext;
    private Boolean bucketKeyEnabled;
    private String requestPayer;
    private String tagging;
    private String objectLockMode;
    private Instant objectLockRetainUntilDate;
    private String objectLockLegalHoldStatus;
    private String expectedBucketOwner;
    private String checksumAlgorithm;

    /**
     * Sets the canned ACL to apply to the object.
     *
     * @param acl The canned ACL to apply to the object
     * @return this Builder
     */
    public Builder acl(String acl) {
      this.acl = acl;
      return this;
    }

    /**
     * Specifies caching behavior along the request/reply chain.
     *
     * @param cacheControl Specifies caching behavior along the request/reply chain.
     * @return this Builder
     */
    public Builder cacheControl(String cacheControl) {
      this.cacheControl = cacheControl;
      return this;
    }

    /**
     * Specifies presentational information for the object.
     *
     * @param contentDisposition Specifies presentational information for the object.
     * @return this Builder
     */
    public Builder contentDisposition(String contentDisposition) {
      this.contentDisposition = contentDisposition;
      return this;
    }

    /**
     * Specifies what content encodings have been applied to the object and thus what decoding mechanisms must be applied to obtain the media-type
     * referenced by the Content-Type header field.
     *
     * @param contentEncoding Specifies what content encodings have been applied to the object and thus what decoding mechanisms must be applied to
     * obtain the media-type referenced by the Content-Type header field.
     * @return this Builder
     */
    public Builder contentEncoding(String contentEncoding) {
      this.contentEncoding = contentEncoding;
      return this;
    }

    /**
     * The language the content is in.
     *
     * @param contentLanguage The language the content is in.
     * @return this Builder
     */
    public Builder contentLanguage(String contentLanguage) {
      this.contentLanguage = contentLanguage;
      return this;
    }

    /**
     * A standard MIME type describing the format of the object data. Note: Setting this will override any value set by a {@link
     * ContentTypeResolver}.
     *
     * @param contentType A standard MIME type describing the format of the object data.
     * @return this Builder
     */
    public Builder contentType(String contentType) {
      this.contentType = contentType;
      return this;
    }

    /**
     * The date and time at which the object is no longer cacheable.
     *
     * @param expires The date and time at which the object is no longer cacheable.
     * @return this Builder
     */
    public Builder expires(Instant expires) {
      this.expires = expires;
      return this;
    }

    /**
     * Gives the grantee READ, READ_ACP, and WRITE_ACP permissions on the object.
     *
     * @param grantFullControl Gives the grantee READ, READ_ACP, and WRITE_ACP permissions on the object.
     * @return this Builder
     */
    public Builder grantFullControl(String grantFullControl) {
      this.grantFullControl = grantFullControl;
      return this;
    }

    /**
     * Allows grantee to read the object data and its metadata.
     *
     * @param grantRead Allows grantee to read the object data and its metadata.
     * @return this Builder
     */
    public Builder grantRead(String grantRead) {
      this.grantRead = grantRead;
      return this;
    }

    /**
     * Allows grantee to read the object ACL.
     *
     * @param grantReadACP Allows grantee to read the object ACL.
     * @return this Builder
     */
    public Builder grantReadACP(String grantReadACP) {
      this.grantReadACP = grantReadACP;
      return this;
    }

    /**
     * Allows grantee to write the ACL for the applicable object.
     *
     * @param grantWriteACP Allows grantee to write the ACL for the applicable object.
     * @return this Builder
     */
    public Builder grantWriteACP(String grantWriteACP) {
      this.grantWriteACP = grantWriteACP;
      return this;
    }

    /**
     * A map of metadata to store with the object in S3. Note: setting this clears any values set by {@link #metadata(String, String)}
     *
     * @param metadata A map of metadata to store with the object in S3.
     * @return this Builder
     */
    public Builder metadata(Map<String, String> metadata) {
      if (metadata == null) {
        metadata = new HashMap<>();
      }
      this.metadata = new HashMap<>(metadata);
      return this;
    }

    /**
     * Adds to the map of metadata to store with the object in S3.
     *
     * @param key the metadata key
     * @param value the metadata value
     * @return this Builder
     */
    public Builder metadata(String key, String value) {
      this.metadata.put(key, value);
      return this;
    }

    /**
     * The server-side encryption algorithm used when storing this object in Amazon S3 (for example, AES256, aws:kms).
     *
     * @param serverSideEncryption The server-side encryption algorithm used when storing this object in Amazon S3 (for example, AES256, aws:kms).
     * @return this Builder
     */
    public Builder serverSideEncryption(String serverSideEncryption) {
      this.serverSideEncryption = serverSideEncryption;
      return this;
    }

    /**
     * By default, Amazon S3 uses the STANDARD Storage Class to store newly created objects. The STANDARD storage class provides high durability and
     * high availability. Depending on performance needs, you can specify a different Storage Class.
     *
     * @param storageClass By default, Amazon S3 uses the STANDARD Storage Class to store newly created objects. The STANDARD storage class provides
     * high durability and high availability. Depending on performance needs, you can specify a different Storage Class.
     * @return this Builder
     */
    public Builder storageClass(String storageClass) {
      this.storageClass = storageClass;
      return this;
    }

    /**
     * If the bucket is configured as a website, redirects requests for this object to another object in the same bucket or to an external URL. Amazon
     * S3 stores the value of this header in the object metadata.
     *
     * @param websiteRedirectLocation If the bucket is configured as a website, redirects requests for this object to another object in the same
     * bucket or to an external URL. Amazon S3 stores the value of this header in the object metadata.
     * @return this Builder
     */
    public Builder websiteRedirectLocation(String websiteRedirectLocation) {
      this.websiteRedirectLocation = websiteRedirectLocation;
      return this;
    }

    /**
     * Specifies the algorithm to use to when encrypting the object (for example, AES256).
     *
     * @param sseCustomerAlgorithm Specifies the algorithm to use to when encrypting the object (for example, AES256).
     * @return this Builder
     */
    public Builder sseCustomerAlgorithm(String sseCustomerAlgorithm) {
      this.sseCustomerAlgorithm = sseCustomerAlgorithm;
      return this;
    }

    /**
     * Specifies the customer-provided encryption key for Amazon S3 to use in encrypting data. This value is used to store the object and then it is
     * discarded; Amazon S3 does not store the encryption key. The key must be appropriate for use with the algorithm specified in the
     * x-amz-server-side-encryption-customer-algorithm header.
     *
     * @param sseCustomerKey Specifies the customer-provided encryption key for Amazon S3 to use in encrypting data. This value is used to store the
     * object and then it is discarded; Amazon S3 does not store the encryption key. The key must be appropriate for use with the algorithm specified
     * in the x-amz-server-side-encryption-customer-algorithm header.
     * @return this Builder
     */
    public Builder sseCustomerKey(String sseCustomerKey) {
      this.sseCustomerKey = sseCustomerKey;
      return this;
    }

    /**
     * Specifies the 128-bit MD5 digest of the encryption key according to RFC 1321. Amazon S3 uses this header for a message integrity check to
     * ensure that the encryption key was transmitted without error.
     *
     * @param sseCustomerKeyMD5 Specifies the 128-bit MD5 digest of the encryption key according to RFC 1321. Amazon S3 uses this header for a message
     * integrity check to ensure that the encryption key was transmitted without error.
     * @return this Builder
     */
    public Builder sseCustomerKeyMD5(String sseCustomerKeyMD5) {
      this.sseCustomerKeyMD5 = sseCustomerKeyMD5;
      return this;
    }

    /**
     * Specifies the ID of the symmetric customer managed key to use for object encryption. All GET and PUT requests for an object protected by Amazon
     * Web Services KMS will fail if not made via SSL or using SigV4.
     *
     * @param ssekmsKeyId Specifies the ID of the symmetric customer managed key to use for object encryption. All GET and PUT requests for an object
     * protected by Amazon Web Services KMS will fail if not made via SSL or using SigV4.
     * @return this Builder
     */
    public Builder ssekmsKeyId(String ssekmsKeyId) {
      this.ssekmsKeyId = ssekmsKeyId;
      return this;
    }

    /**
     * Specifies the Amazon Web Services KMS Encryption Context to use for object encryption. The value of this header is a base64-encoded UTF-8
     * string holding JSON with the encryption context key-value pairs.
     *
     * @param ssekmsEncryptionContext Specifies the Amazon Web Services KMS Encryption Context to use for object encryption. The value of this header
     * is a base64-encoded UTF-8 string holding JSON with the encryption context key-value pairs.
     * @return this Builder
     */
    public Builder ssekmsEncryptionContext(String ssekmsEncryptionContext) {
      this.ssekmsEncryptionContext = ssekmsEncryptionContext;
      return this;
    }

    /**
     * Specifies whether Amazon S3 should use an S3 Bucket Key for object encryption with server-side encryption using AWS KMS (SSE-KMS). Setting this
     * header to true causes Amazon S3 to use an S3 Bucket Key for object encryption with SSE-KMS.
     *
     * @param bucketKeyEnabled Specifies whether Amazon S3 should use an S3 Bucket Key for object encryption with server-side encryption using AWS KMS
     * (SSE-KMS). Setting this header to true causes Amazon S3 to use an S3 Bucket Key for object encryption with SSE-KMS.
     * @return this Builder
     */
    public Builder bucketKeyEnabled(Boolean bucketKeyEnabled) {
      this.bucketKeyEnabled = bucketKeyEnabled;
      return this;
    }

    /**
     * Sets the value of the RequestPayer property for this object.
     *
     * @param requestPayer The new value for the RequestPayer property for this object.
     * @return this Builder
     */
    public Builder requestPayer(String requestPayer) {
      this.requestPayer = requestPayer;
      return this;
    }

    /**
     * The tag-set for the object. The tag-set must be encoded as URL Query parameters.
     *
     * @param tagging The tag-set for the object. The tag-set must be encoded as URL Query parameters.
     * @return this Builder
     */
    public Builder tagging(String tagging) {
      this.tagging = tagging;
      return this;
    }

    /**
     * Specifies the Object Lock mode that you want to apply to the uploaded object.
     *
     * @param objectLockMode Specifies the Object Lock mode that you want to apply to the uploaded object.
     * @return this Builder
     */
    public Builder objectLockMode(String objectLockMode) {
      this.objectLockMode = objectLockMode;
      return this;
    }

    /**
     * Specifies the date and time when you want the Object Lock to expire.
     *
     * @param objectLockRetainUntilDate Specifies the date and time when you want the Object Lock to expire.
     * @return this Builder
     */
    public Builder objectLockRetainUntilDate(Instant objectLockRetainUntilDate) {
      this.objectLockRetainUntilDate = objectLockRetainUntilDate;
      return this;
    }

    /**
     * Specifies whether you want to apply a legal hold to the uploaded object.
     *
     * @param objectLockLegalHoldStatus Specifies whether you want to apply a legal hold to the uploaded object.
     * @return this Builder
     */
    public Builder objectLockLegalHoldStatus(String objectLockLegalHoldStatus) {
      this.objectLockLegalHoldStatus = objectLockLegalHoldStatus;
      return this;
    }

    /**
     * The account ID of the expected bucket owner. If the bucket is owned by a different account, the request fails with the HTTP status code 403
     * Forbidden (access denied).
     *
     * @param expectedBucketOwner The account ID of the expected bucket owner. If the bucket is owned by a different account, the request fails with
     * the HTTP status code 403 Forbidden (access denied).
     * @return this Builder
     */
    public Builder expectedBucketOwner(String expectedBucketOwner) {
      this.expectedBucketOwner = expectedBucketOwner;
      return this;
    }

    /**
     * Indicates the algorithm you want Amazon S3 to use to create the checksum for the object.
     *
     * @param checksumAlgorithm Indicates the algorithm you want Amazon S3 to use to create the checksum for the object.
     * @return this Builder
     * @deprecated Use {@link MultipartUploadRequest.Builder#checksumAlgorithm(String)} instead
     */
    @Deprecated
    public Builder checksumAlgorithm(String checksumAlgorithm) {
      this.checksumAlgorithm = checksumAlgorithm;
      return this;
    }

    /**
     * Builds a new {@link ObjectMetadata} object
     *
     * @return a new {@link ObjectMetadata} object
     */
    public ObjectMetadata build() {
      return new ObjectMetadata(
          acl,
          cacheControl,
          contentDisposition,
          contentEncoding,
          contentLanguage,
          contentType,
          expires,
          grantFullControl,
          grantRead,
          grantReadACP,
          grantWriteACP,
          Collections.unmodifiableMap(metadata),
          serverSideEncryption,
          storageClass,
          websiteRedirectLocation,
          sseCustomerAlgorithm,
          sseCustomerKey,
          sseCustomerKeyMD5,
          ssekmsKeyId,
          ssekmsEncryptionContext,
          bucketKeyEnabled,
          requestPayer,
          tagging,
          objectLockMode,
          objectLockRetainUntilDate,
          objectLockLegalHoldStatus,
          expectedBucketOwner,
          checksumAlgorithm);
    }
  }
}
