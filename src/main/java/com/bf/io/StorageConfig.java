package com.bf.io;

import java.util.ResourceBundle;

/**
 * @author bofei
 * @date 2019/1/9 11:03
 */
public class StorageConfig {
    private String propertiesName;

    private String filePath;
    private String dirPath;

    private String clientRegion;
    private String bucketName;
    private String objectKey;

    public StorageConfig(String propertiesName) {
        this.propertiesName = propertiesName;
    }


    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getDirPath() {
        return dirPath;
    }

    public void setDirPath(String dirPath) {
        this.dirPath = dirPath;
    }

    public String getClientRegion() {
        return clientRegion;
    }

    public void setClientRegion(String clientRegion) {
        this.clientRegion = clientRegion;
    }

    public String getBucketName() {
        return bucketName;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    public String getObjectKey() {
        return objectKey;
    }

    public void setObjectKey(String objectKey) {
        this.objectKey = objectKey;
    }

    @Override
    public String toString() {
        return "StorageConfig{" +
                "propertiesName='" + propertiesName + '\'' +
                ", filePath='" + filePath + '\'' +
                ", dirPath='" + dirPath + '\'' +
                ", clientRegion='" + clientRegion + '\'' +
                ", bucketName='" + bucketName + '\'' +
                ", objectKey='" + objectKey + '\'' +
                '}';
    }

    public Boolean init() {
        ResourceBundle rb = ResourceBundle.getBundle(propertiesName.trim());
        if (rb.containsKey("filePath")) filePath = rb.getString("filePath");
        if (rb.containsKey("dirPath")) dirPath = rb.getString("dirPath");

        if (rb.containsKey("clientRegion")) clientRegion = rb.getString("clientRegion");
        if (rb.containsKey("bucketName")) bucketName = rb.getString("bucketName");
        if (rb.containsKey("objectKey")) objectKey = rb.getString("objectKey");
        return true;
    }


}
