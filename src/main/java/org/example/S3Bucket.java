package org.example;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.CreateBucketRequest;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class S3Bucket {
    public static String SECRET_KEY = "";
    public static String ACCESS_KEY = "";
    public static AwsCredentials CREDENTIALS = null;


    /**
     * This function extracts the access and secret keys of AWS Sandbox
     * */
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
     * Creates the credential object */
    public void credentialGenerator(){
        CREDENTIALS = AwsBasicCredentials.create(ACCESS_KEY,SECRET_KEY);
    }

    /**
     * Creating the bucket
     * */
    public String createBucket(String bucketName){
        S3Client s3Client = S3Client.builder().region(Region.US_EAST_1).credentialsProvider(() -> CREDENTIALS).build();
        CreateBucketRequest bucketRequest = CreateBucketRequest.builder().bucket(bucketName).build();
        s3Client.createBucket(bucketRequest);
        return "Bucket "+ bucketName + " has been created!";
    }

}
