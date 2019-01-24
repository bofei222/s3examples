package com.bf.io;

import java.io.File;

/**
 * @author bofei
 * @date 2019/1/19 14:48
 */
public class FSFileAttr {
    private long length = 0;
    private long lastModified = 0;

    private File file = null;

    private StorageConfig storageConfig;

    public FSFileAttr(StorageConfig storageConfig) {
        this.storageConfig = storageConfig;
    }

    //    private
    public boolean open(String id) {
        try {
            String hash = MyUtil.hash(id);
            File dir = new File(storageConfig.getDirPath() + "/" + hash);
            file = new File(dir + "/" + id);
            if (!file.exists()) {
                length = -1;
                lastModified = -1;
                return false;
            } else {
                length = file.length();
                lastModified = file.lastModified();
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
