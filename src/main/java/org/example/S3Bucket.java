package org.example;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.CreateBucketRequest;
import software.amazon.awssdk.services.s3.model.PutBucketPolicyRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class S3Bucket {
    public static String SECRET_KEY = "";
    public static String ACCESS_KEY = "";
    public static AwsCredentials CREDENTIALS = null;
    public static S3Client S3ClientGLOBAL = null;

    /**
     * This function extracts the access and secret keys of AWS Sandbox
     */
    public void extractKeys() {
        Properties properties = new Properties();
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream("/home/cynos/DalhousieUniv/serverless/assignment/Assignment1/src/main/java/org/example/app.properties");
            properties.load(fileInputStream);
            String SK = properties.getProperty("SecretKey");
            String AK = properties.getProperty("AccessKey");
            /* Assigning SK AK to resp keys */
            SECRET_KEY = SK;
            ACCESS_KEY = AK;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Creates the credential object static
     */
    public void credentialGenerator() {
        CREDENTIALS = AwsBasicCredentials.create(ACCESS_KEY, SECRET_KEY);
    }

    /**
     * Creating the bucket
     * reference [1]
     */
    public String createBucket(String bucketName) {
        // creating the s3Client
        S3Client s3Client = S3Client.builder().region(Region.US_EAST_1).credentialsProvider(() -> CREDENTIALS).build();
        CreateBucketRequest bucketRequest = CreateBucketRequest.builder().bucket(bucketName).build();
        // creates the bucket
        s3Client.createBucket(bucketRequest);
        S3ClientGLOBAL = s3Client;
        return "Bucket " + bucketName + " has been created!";
    }

    /**
     * Uploading the HTML File
     * reference [2]
     * */
    public void uploadHtml(String bucketName) {
        // places the index.html in the bucket
        PutObjectRequest putObjectRequest = PutObjectRequest.builder().bucket(bucketName).key("index.html").build();
        S3ClientGLOBAL.putObject(putObjectRequest, new File("/home/cynos/DalhousieUniv/serverless/assignment/Assignment1/src/main/java/org/example/index.html").toPath());
    }

    /**
     * Setting the bucket policy to public
     * */
    public void setS3ToPublic(String bucketName) {
        S3ClientGLOBAL.putBucketPolicy(PutBucketPolicyRequest.builder().bucket(bucketName).policy("{\"Version\":\"2012-10-17\",\"Statement\":[{\"Sid\":\"PublicReadGetObject\",\"Effect\":\"Allow\",\"Principal\":\"*\",\"Action\":\"s3:GetObject\",\"Resource\":\"arn:aws:s3:::" + bucketName + "/*\"}]}").build());
    }
}


/**
 * References for the code -
 * [1] https://www.codejava.net/aws/create-bucket-examples - Setting S3client and creating the bucket
 * [2] https://docs.aws.amazon.com/AmazonS3/latest/userguide/example_s3_PutObject_section.html - put the html in a bucket
 * */