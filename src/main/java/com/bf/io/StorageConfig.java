package com.bf.io;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

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
        try {
            Properties config = new Properties();
            config.load(new FileInputStream(propertiesName));

            if (config.containsKey("dirPath")) dirPath = config.getProperty("dirPath");

            if (config.containsKey("clientRegion")) clientRegion = config.getProperty("clientRegion");
            if (config.containsKey("bucketName")) bucketName = config.getProperty("bucketName");
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

    }


}
