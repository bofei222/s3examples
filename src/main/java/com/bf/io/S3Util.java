package com.bf.io;

import aws.example.s3.GetObject;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ResponseHeaderOverrides;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.util.IOUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class S3Util {

    public static void getObject(byte[] datas, int off, int size, Integer length) {
        String clientRegion = "ap-northeast-1";
        String bucketName = "com.bf";
        String key = "456.txt";

        S3Object object = null;
        try {
            AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                    .withRegion(clientRegion)
//                    .withCredentials(new ProfileCredentialsProvider())
                    .build();

            GetObjectRequest request = null;
            if (off == -1) {
                request = new GetObjectRequest(bucketName, key);
            } else {
                int end = off + size;
                request = new GetObjectRequest(bucketName, key)
                        .withRange(3, end);
            }
            // Get an object and print its contents.
            System.out.println("Downloading an object");
            object = s3Client.getObject(request);
            System.out.println("Content-Type: " + object.getObjectMetadata().getContentType());
            System.out.println("Content: ");
            S3ObjectInputStream objectContent = object.getObjectContent();
            datas = IOUtils.toByteArray(objectContent);
            length = datas.length;
        } catch (AmazonServiceException e) {
            // The call was transmitted successfully, but Amazon S3 couldn't process
            // it, so it returned an error response.
            e.printStackTrace();
        } catch (SdkClientException e) {
            // Amazon S3 couldn't be contacted for a response, or the client
            // couldn't parse the response from Amazon S3.
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // To ensure that the network connection doesn't remain open, close any open input streams.
            try {
                if (object != null) {
                    object.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
