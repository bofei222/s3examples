package com.bf.io;

import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.apache.commons.configuration2.ex.ConfigurationException;

import java.io.File;
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
        try {
            Configurations configs = new Configurations();
            Configuration config = configs.properties(new File(propertiesName));

            if (config.containsKey("dirPath")) dirPath = config.getString("dirPath");

            if (config.containsKey("clientRegion")) clientRegion = config.getString("clientRegion");
            if (config.containsKey("bucketName")) bucketName = config.getString("bucketName");
            return true;
        } catch (ConfigurationException e) {
            e.printStackTrace();
            return false;
        }
    }


}
