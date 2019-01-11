package com.bf.io;

import aws.example.s3.GetObject;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.*;
import com.amazonaws.util.IOUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.omg.CORBA.IntHolder;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class S3Util {
    private Integer count = 1;
    public static void main2(String[] args) {
        String bucket = "com.bf2";
        String s3Key = "2个5M.txt";


        AmazonS3 s3Client = AmazonS3ClientBuilder.standard().withRegion(Regions.CN_NORTHWEST_1).build();

        // 创建一个列表保存所有分传的 PartETag, 在分段完成后会用到
        List<PartETag> partETags = new ArrayList<>();

        // 第一步，初始化，声明下面将有一个 Multipart Upload
        InitiateMultipartUploadRequest initRequest = new InitiateMultipartUploadRequest(bucket, s3Key);
        InitiateMultipartUploadResult initResponse = s3Client.initiateMultipartUpload(initRequest);

        int minPartSize = 5 * 1024 * 1024; //分段大小在 5MB - 5GB 之间，只有最后一个分段才允许小于 5MB，不可避免的

        try {
            for (int i = 1; i < 3; i++) {
                System.out.println("第" + i + "段");
                byte[] bytes = RandomStringUtils.randomAlphabetic(minPartSize).getBytes(); //填充一个 5MB 的字符串

                UploadPartRequest uploadRequest = new UploadPartRequest()
                        .withBucketName(bucket)
                        .withKey(s3Key)
                        .withUploadId(initResponse.getUploadId())
                        .withPartNumber(i)
                        .withInputStream(new ByteArrayInputStream(bytes))
                        .withPartSize(bytes.length);

                // 第二步，上传分段，并把当前段的 PartETag 放到列表中
                partETags.add(s3Client.uploadPart(uploadRequest).getPartETag());
            }

            // 第三步，完成上传
            CompleteMultipartUploadRequest compRequest = new CompleteMultipartUploadRequest(bucket, s3Key,
                    initResponse.getUploadId(), partETags);

            s3Client.completeMultipartUpload(compRequest);
        } catch (Exception e) {
            s3Client.abortMultipartUpload(new AbortMultipartUploadRequest(bucket, s3Key, initResponse.getUploadId()));
            System.out.println("Failed to upload, " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        byte[] b = new byte[1024];
        IntHolder intHolder = new IntHolder();
        getObject(b, 1, 3, intHolder.value);



    }


    public static void getObject(byte[] datas, int off, int size, Integer length) {
//        String clientRegion = "ap-northeast-1";
        String bucketName = "com.bf2";
        String key = "test\\117488\\bofei的一个test文件";

        S3Object object = null;
        try {
            AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                    .withRegion(Regions.CN_NORTHWEST_1)
//                    .withCredentials(new ProfileCredentialsProvider())
                    .build();

            GetObjectRequest request = null;
            if (off == -1) {
                request = new GetObjectRequest(bucketName, key);
            } else {
                int end = off + size;
                request = new GetObjectRequest(bucketName, key)
                        .withRange(off, end);
            }
            // Get an object and print its contents.
            System.out.println("Downloading an object");
            object = s3Client.getObject(request);
            System.out.println("Content-Type: " + object.getObjectMetadata().getContentType());
            System.out.println("Content: ");
            S3ObjectInputStream objectContent = object.getObjectContent();
            displayTextInputStream(objectContent);
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

    public static void displayTextInputStream(InputStream input) throws IOException {
        // Read the text input stream one line at a time and display each line.
        BufferedReader reader = new BufferedReader(new InputStreamReader(input));
        String line = null;
        while ((line = reader.readLine()) != null) {
            System.out.println(line);
        }
        System.out.println();
    }
}
