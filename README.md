# aws-s3-outputstream

The aws-s3-outputstream project allows for multipart uploads to an AWS S3 bucket through a java.io.OutputStream.

Additional project information, javadocs, and test coverage is located at https://ci-cmg.github.io/project-documentation/aws-s3-outputstream/

## Adding To Your Project

Add the following dependency to your Maven pom.xml

```xml
    <dependency>
      <groupId>io.github.ci-cmg</groupId>
      <artifactId>aws-s3-outputstream</artifactId>
      <version>1.0.0</version>
    </dependency>
```

## Usage

The minimal way to create a S3OutputStream is as follows:
```java
OutputStream out = S3OutputStream.builder().s3(s3).bucket(bucketName).key(key).build();
```

Where in the above example s3 is an instance of S3ClientMultipartUpload (described below), bucketName is the name of
a S3 bucket, and key is the key that will be uploaded to in the bucket.

Here is how to create a S3OutputStream with all the available options:
```java
OutputStream out = S3OutputStream.builder()
    .s3(s3)
    .bucket(bucketName)
    .key(key)
    .partSizeMib(partSizeMib)
    .uploadQueueSize(queueSize)
    .autoComplete(true)
    .build();
```

### S3ClientMultipartUpload
s3 is an instance of S3ClientMultipartUpload.  The S3ClientMultipartUpload is a wrapper
around the S3Client from the AWS SDK v2.  This allows for calls to the S3Client to 
be mocked for testing.  Two implementations are provided:

1. AwsS3ClientMultipartUpload - This uses the S3Client to make calls using the AWS SDK.
2. FileMockS3ClientMultipartUpload - This reads and writes from the local file system. This should only be used for testing.

An instance of AwsS3ClientMultipartUpload can be created as follows:
```java
S3ClientMultipartUpload s3 = AwsS3ClientMultipartUpload.builder()
    .s3(s3)
    .contentTypeResolver(contentTypeResolver)
    .build();
```

The contentTypeResolver resolves the MIME type for files uploaded to S3. A default
implementation is provided if not specified in the AwsS3ClientMultipartUpload builder.
An instance of NoContentTypeResolver can be provided if MIME types should not be used.

### Upload Performance
A S3OutputStream uploads a file in parts. partSizeMib represents the size of the parts to 
upload in MiB.  This value must be at least 5, which is the default.

A S3OutputStream uses a queue to allow multipart uploads to S3 to happen while additional
buffers are being filled concurrently. The uploadQueueSize defines the number of parts
to be queued before blocking population of additional parts.  The default value is 1.
Specifying a higher value may improve upload speed at the expense of more heap usage.
Using a value higher than one should be tested to see if any performance gains are achieved
for your situation.

### Auto Completion
When a multipart file upload is completed, AWS S3 must be notified. Autocompletion is a
convenience feature that allows a S3OutputStream to work like a normal java.io.OutputStream.  The
main use case for this is where your code generates a S3OutputStream that must be 
passed to another library as a java.io.OutputStream that you do not control that is responsible for closing 
it. With autocompletion enabled, the AWS completion notification will always happen
when the OutputStream is closed. This is fine, unless an exception occurs and close()
is called in a finally block or try-with-resources (as should always be done).  In this
scenario, the upload will be completed even if there was an error, rather than aborting
the upload.  To ensure compatibility with java.io.OutputStream, autocompletion is enabled
by default.

If you have control over the code closing the S3OutputStream it is best to disable autocompletion 
as follows:
```java
    try (
        InputStream inputStream = Files.newInputStream(source);
        S3OutputStream s3OutputStream = S3OutputStream.builder()
            .s3(s3)
            .bucket(bucket)
            .key(key)
            .autoComplete(false)
            .build();
    ) {
      IOUtils.copy(inputStream, outputStream);
      s3OutputStream.done();
    }
```

Note the call to s3OutputStream.done(). This should be called after all data has been uploaded, before
calling close or the end of the try-with-resources block. This signals that the upload was successful
and when the S3OutputStream is closed, the completion signal will be sent. If done() is not called
before close() and error was assumed to have occurred and an abort signal will be send in close() 
instead.







