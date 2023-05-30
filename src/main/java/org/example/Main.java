package org.example;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {
        S3Bucket s3Bucket = new S3Bucket();
        s3Bucket.extractKeys();
        s3Bucket.credentialGenerator();
        s3Bucket.createBucket("alen-assignment1b00930528");
    }
}