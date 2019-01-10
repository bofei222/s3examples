package com.bf.io;

import com.amazonaws.SdkClientException;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.*;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.omg.CORBA.IntHolder;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Author bofei
 * @Date 2019/1/8 12:49
 * @Description
 */
public class ToS3 implements StorageFile {
    private StorageConfig storageConfig;

    String s3Key = null;
    private Integer i = 1;

    AmazonS3 s3Client = null;

    // 创建一个列表保存所有分传的 PartETag, 在分段完成后会用到
    List<PartETag> partETags = new ArrayList<>();
    // 第一步，初始化，声明下面将有一个 Multipart Upload
    InitiateMultipartUploadRequest initRequest = null;
    InitiateMultipartUploadResult initResponse = null;


    private List<Byte> buff = new ArrayList<>();
    private int minPartSize = 500 * 1024 * 1024;

    public ToS3(StorageConfig storageConfig) {
        this.storageConfig = storageConfig;
    }


    @Override
    public boolean open(String id, String flag) {
        System.out.println("open" +i);
        String hash = MyUtil.hash(id);
        s3Key = storageConfig.getDirPath() + File.separator + hash + File.separator + id;

        s3Client = AmazonS3ClientBuilder.standard().withRegion(Regions.CN_NORTHWEST_1).build();
        if ("w".equals(flag)) {
            // 第一步，初始化，声明下面将有一个 Multipart Upload
            initRequest = new InitiateMultipartUploadRequest(storageConfig.getBucketName(),
                    s3Key);
            initResponse = s3Client.initiateMultipartUpload(initRequest);
        } else {
            return true;
        }
        return true;
    }

    @Override
    public boolean read(byte[] data, long off, long size, IntHolder length) {

        return false;
    }

    @Override
    public boolean write(byte[] data, long off, long size, IntHolder length) {
        if (data.length < minPartSize) {
            Byte[] byteObject = ArrayUtils.toObject(data);
            List<Byte> list = new ArrayList<Byte>(Arrays.asList(byteObject));
            buff.addAll(list);
        }


        System.out.println("write" + i);
        Boolean flag = true;
        try {
            UploadPartRequest uploadRequest = new UploadPartRequest()
                    .withBucketName(storageConfig.getBucketName())
                    .withKey(s3Key)
                    .withUploadId(initResponse.getUploadId())
                    .withPartNumber(i++)
                    .withInputStream(new ByteArrayInputStream(data))
                    .withPartSize(data.length);

            // 第二步，上传分段，并把当前段的 PartETag 放到列表中
            partETags.add(s3Client.uploadPart(uploadRequest).getPartETag());
        } catch (SdkClientException e) {
            e.printStackTrace();
            flag = false;
        } finally {
            return flag;
        }
    }

    @Override
    public boolean close() {
        System.out.println("close");
        boolean flag = true;
        try {
            // 第三步，完成上传
            CompleteMultipartUploadRequest compRequest = new CompleteMultipartUploadRequest(storageConfig.getBucketName(), s3Key,
                    initResponse.getUploadId(), partETags);
            s3Client.completeMultipartUpload(compRequest);
            return false;
        } catch (SdkClientException e) {
            e.printStackTrace();
            flag = false;
            s3Client.abortMultipartUpload(new AbortMultipartUploadRequest(storageConfig.getBucketName(),
                    s3Key, initResponse.getUploadId()));
            System.out.println("Failed to upload, " + e.getMessage());
        } finally {
            return flag;
        }
    }
}
