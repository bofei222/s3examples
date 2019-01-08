package com.bf.io;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.*;
import org.apache.commons.lang3.RandomStringUtils;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author bofei
 * @Date 2019/1/8 18:37
 * @Description
 */
public class S3Test {
    public static void main(String[] args) {
        String bucket = "com.bf2";
        String s3Key = "100.txt";


        AmazonS3 s3Client = AmazonS3ClientBuilder.standard().withRegion(Regions.CN_NORTHWEST_1).build();

        // 创建一个列表保存所有分传的 PartETag, 在分段完成后会用到
        List<PartETag> partETags = new ArrayList<>();

        // 第一步，初始化，声明下面将有一个 Multipart Upload
        InitiateMultipartUploadRequest initRequest = new InitiateMultipartUploadRequest(bucket, s3Key);
        InitiateMultipartUploadResult initResponse = s3Client.initiateMultipartUpload(initRequest);

        int minPartSize = 10 * 1024 * 1024; //分段大小在 5MB - 5GB 之间，只有最后一个分段才允许小于 5MB，不可避免的

        try {
            for (int i = 1; i < 1002; i++) {
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
}
