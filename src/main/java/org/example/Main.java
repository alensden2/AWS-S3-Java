package org.example;

public class Main {
    public static void main(String[] args) {
        S3Bucket s3Bucket = new S3Bucket();
        s3Bucket.extractKeys();
        s3Bucket.credentialGenerator();
        s3Bucket.createBucket("alen-assignment1b00930528");
        s3Bucket.uploadHtml("alen-assignment1b00930528");
       s3Bucket.setS3ToPublic("alen-assignment1b00930528");
    }
}