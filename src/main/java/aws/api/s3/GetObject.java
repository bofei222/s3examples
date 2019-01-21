package aws.api.s3;

// Copyright 2018 Amazon.com, Inc. or its affiliates. All Rights Reserved.
// SPDX-License-Identifier: MIT-0 (For details, see https://github.com/awsdocs/amazon-s3-developer-guide/blob/master/LICENSE-SAMPLECODE.)

import java.io.*;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ResponseHeaderOverrides;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;

public class GetObject {

    public static void main(String[] args) throws IOException {
//        String clientRegion = "ap-northeast-1";
        String bucketName = "com.bf2";
        String key = "yun0116/0441/cc795aa0-eeb9-4913-b6ae-6599833ece92";

        S3Object fullObject = null, objectPortion = null, headerOverrideObject = null;
        try {
            AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                    .withRegion(Regions.CN_NORTHWEST_1)
//                    .withCredentials(new ProfileCredentialsProvider())
                    .build();

            // Get an object and print its contents.
//            System.out.println("Downloading an object");
//            fullObject = s3Client.getObject(new GetObjectRequest(bucketName, key));
//            System.out.println("Content-Type: " + fullObject.getObjectMetadata().getContentType());
//            System.out.println("Content: ");
//            displayTextInputStream(fullObject.getObjectContent());

            // Get a range of bytes from an object and print the bytes.
            GetObjectRequest rangeObjectRequest = new GetObjectRequest(bucketName, key)
                    .withRange(0,33258916);
            objectPortion = s3Client.getObject(rangeObjectRequest);
            long contentLength = objectPortion.getObjectMetadata().getContentLength();
            System.out.println(contentLength);
            System.out.println("Printing bytes retrieved.");
            S3ObjectInputStream is = objectPortion.getObjectContent();
            BufferedInputStream bis = new BufferedInputStream(is);
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream("c:/test/bb"));
            byte[] b = new byte[1024 * 1024];
            int l = 0;
            int count = 0;
            while ((l = bis.read(b)) != -1) {
//                System.out.println(++count);
                System.out.println(l);
                bos.write(b);

            }
            bos.flush();


//            displayTextInputStream(objectPortion.getObjectContent());

            // Get an entire object, overriding the specified response headers, and print the object's content.
//            ResponseHeaderOverrides headerOverrides = new ResponseHeaderOverrides()
//                    .withCacheControl("No-cache")
//                    .withContentDisposition("attachment; filename=example.txt");
//            GetObjectRequest getObjectRequestHeaderOverride = new GetObjectRequest(bucketName, key)
//                    .withResponseHeaders(headerOverrides);
//            headerOverrideObject = s3Client.getObject(getObjectRequestHeaderOverride);
//            displayTextInputStream(headerOverrideObject.getObjectContent());
        }
        catch(AmazonServiceException e) {
            // The call was transmitted successfully, but Amazon S3 couldn't process
            // it, so it returned an error response.
            e.printStackTrace();
        }
        catch(SdkClientException e) {
            // Amazon S3 couldn't be contacted for a response, or the client
            // couldn't parse the response from Amazon S3.
            e.printStackTrace();
        }
        finally {
            // To ensure that the network connection doesn't remain open, close any open input streams.
            if(fullObject != null) {
                fullObject.close();
            }
            if(objectPortion != null) {
                objectPortion.close();
            }
            if(headerOverrideObject != null) {
                headerOverrideObject.close();
            }
        }
    }

    private static void displayTextInputStream(InputStream input) throws IOException {
        // Read the text input stream one line at a time and display each line.
        BufferedReader reader = new BufferedReader(new InputStreamReader(input));

        String line = null;
        while ((line = reader.readLine()) != null) {
            System.out.println(line);
        }
        System.out.println();
    }
}