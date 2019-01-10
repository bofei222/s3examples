package com.bf.io;

import java.util.ResourceBundle;

/**
 * @author bofei
 * @date 2019/1/9 11:03
 */
public class StorageConfig {
    private String propertiesName;

    private String dirPath;

    private String clientRegion;
    private String bucketName;


    public StorageConfig(String propertiesName) {
        this.propertiesName = propertiesName;
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

    @Override
    public String toString() {
        return "StorageConfig{" +
                "propertiesName='" + propertiesName + '\'' +
                ", dirPath='" + dirPath + '\'' +
                ", clientRegion='" + clientRegion + '\'' +
                ", bucketName='" + bucketName + '\'' +
                '}';
    }

    public Boolean init() {
        ResourceBundle rb = ResourceBundle.getBundle(propertiesName.trim());
        if (rb.containsKey("dirPath")) dirPath = rb.getString("dirPath");

        if (rb.containsKey("clientRegion")) clientRegion = rb.getString("clientRegion");
        if (rb.containsKey("bucketName")) bucketName = rb.getString("bucketName");
        return true;
    }


}
