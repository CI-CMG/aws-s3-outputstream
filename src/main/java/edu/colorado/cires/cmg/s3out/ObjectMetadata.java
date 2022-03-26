package edu.colorado.cires.cmg.s3out;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

import software.amazon.awssdk.services.s3.model.CreateMultipartUploadRequest;

public class ObjectMetadata {
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

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private final Map<String, String> metadata = new HashMap<>();
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

        public Builder acl(String acl) {
            this.acl = acl;
            return this;
        }

        public Builder cacheControl(String cacheControl) {
            this.cacheControl = cacheControl;
            return this;
        }

        public Builder contentDisposition(String contentDisposition) {
            this.contentDisposition = contentDisposition;
            return this;
        }

        public Builder contentEncoding(String contentEncoding) {
            this.contentEncoding = contentEncoding;
            return this;
        }

        public Builder contentLanguage(String contentLanguage) {
            this.contentLanguage = contentLanguage;
            return this;
        }

        public Builder contentType(String contentType) {
            this.contentType = contentType;
            return this;
        }

        public Builder expires(Instant expires) {
            this.expires = expires;
            return this;
        }

        public Builder grantFullControl(String grantFullControl) {
            this.grantFullControl = grantFullControl;
            return this;
        }

        public Builder grantRead(String grantRead) {
            this.grantRead = grantRead;
            return this;
        }

        public Builder grantReadACP(String grantReadACP) {
            this.grantReadACP = grantReadACP;
            return this;
        }

        public Builder grantWriteACP(String grantWriteACP) {
            this.grantWriteACP = grantWriteACP;
            return this;
        }

        public Builder metadata(String key, String value) {
            this.metadata.put(key, value);
            return this;
        }

        public Builder serverSideEncryption(String serverSideEncryption) {
            this.serverSideEncryption = serverSideEncryption;
            return this;
        }

        public Builder storageClass(String storageClass) {
            this.storageClass = storageClass;
            return this;
        }

        public Builder websiteRedirectLocation(String websiteRedirectLocation) {
            this.websiteRedirectLocation = websiteRedirectLocation;
            return this;
        }

        public Builder sseCustomerAlgorithm(String sseCustomerAlgorithm) {
            this.sseCustomerAlgorithm = sseCustomerAlgorithm;
            return this;
        }

        public Builder sseCustomerKey(String sseCustomerKey) {
            this.sseCustomerKey = sseCustomerKey;
            return this;
        }

        public Builder sseCustomerKeyMD5(String sseCustomerKeyMD5) {
            this.sseCustomerKeyMD5 = sseCustomerKeyMD5;
            return this;
        }

        public Builder ssekmsKeyId(String ssekmsKeyId) {
            this.ssekmsKeyId = ssekmsKeyId;
            return this;
        }

        public Builder ssekmsEncryptionContext(String ssekmsEncryptionContext) {
            this.ssekmsEncryptionContext = ssekmsEncryptionContext;
            return this;
        }

        public Builder bucketKeyEnabled(Boolean bucketKeyEnabled) {
            this.bucketKeyEnabled = bucketKeyEnabled;
            return this;
        }

        public Builder requestPayer(String requestPayer) {
            this.requestPayer = requestPayer;
            return this;
        }

        public Builder tagging(String tagging) {
            this.tagging = tagging;
            return this;
        }

        public Builder objectLockMode(String objectLockMode) {
            this.objectLockMode = objectLockMode;
            return this;
        }

        public Builder objectLockRetainUntilDate(Instant objectLockRetainUntilDate) {
            this.objectLockRetainUntilDate = objectLockRetainUntilDate;
            return this;
        }

        public Builder objectLockLegalHoldStatus(String objectLockLegalHoldStatus) {
            this.objectLockLegalHoldStatus = objectLockLegalHoldStatus;
            return this;
        }

        public Builder expectedBucketOwner(String expectedBucketOwner) {
            this.expectedBucketOwner = expectedBucketOwner;
            return this;
        }

        public Builder checksumAlgorithm(String checksumAlgorithm) {
            this.checksumAlgorithm = checksumAlgorithm;
            return this;
        }

        public ObjectMetadata build() {
            return new ObjectMetadata(acl, cacheControl, contentDisposition, contentEncoding, contentLanguage, contentType, expires,
                    grantFullControl, grantRead, grantReadACP, grantWriteACP, metadata, serverSideEncryption, storageClass,
                    websiteRedirectLocation, sseCustomerAlgorithm, sseCustomerKey, sseCustomerKeyMD5, ssekmsKeyId,
                    ssekmsEncryptionContext, bucketKeyEnabled, requestPayer, tagging, objectLockMode, objectLockRetainUntilDate,
                    objectLockLegalHoldStatus, expectedBucketOwner, checksumAlgorithm);
        }
    }

    ObjectMetadata(String acl, String cacheControl, String contentDisposition, String contentEncoding,
            String contentLanguage, String contentType, Instant expires, String grantFullControl, String grantRead,
            String grantReadACP, String grantWriteACP, Map<String, String> metadata, String serverSideEncryption,
            String storageClass, String websiteRedirectLocation, String sseCustomerAlgorithm, String sseCustomerKey,
            String sseCustomerKeyMD5, String ssekmsKeyId, String ssekmsEncryptionContext, Boolean bucketKeyEnabled,
            String requestPayer, String tagging, String objectLockMode, Instant objectLockRetainUntilDate,
            String objectLockLegalHoldStatus, String expectedBucketOwner, String checksumAlgorithm) {
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

    void apply(CreateMultipartUploadRequest.Builder builder) {
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
        if (metadata != null) {
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
}
