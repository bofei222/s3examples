package com.bf.io;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;

/**
 * @author bofei
 * @date 2019/1/19 14:49
 */
public class S3FileAttr {
    private long length = 0;
    private long lastModified = 0;

    // 对象key
    String s3Key = null;
    // s3Client
    AmazonS3 s3Client = null;

    private StorageConfig storageConfig;

    public S3FileAttr(StorageConfig storageConfig) {
        this.storageConfig = storageConfig;
    }

    //    private
    public boolean open(String id) {
        try {
            String hash = MyUtil.hash(id);
            s3Key = storageConfig.getDirPath() + "/" + hash + "/" + id;
            s3Client = AmazonS3ClientBuilder.standard().withRegion(Regions.CN_NORTHWEST_1).build();
            boolean exist = s3Client.doesObjectExist(storageConfig.getBucketName(), s3Key);

            if (!exist) {
                length = -1;
                lastModified = -1;
                return false;
            } else {
                ObjectMetadata metadata = s3Client.getObjectMetadata(storageConfig.getBucketName(), s3Key);
                length = metadata.getContentLength();
                lastModified = metadata.getLastModified().getTime();
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public long getLength() {
        return length;
    }

    public long getLastModified() {
        return lastModified;
    }

    public boolean close() {
        return true;
    }
}
