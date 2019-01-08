package com.bf.io;

/**
 * @Author bofei
 * @Date 2019/1/8 12:49
 * @Description
 */
public class ToS3 implements StorageFile {
    private StorageFile storageFile;

    public ToS3(StorageFile storageFile) {
        this.storageFile = storageFile;
    }

    @Override
    public boolean open(String id, String flag) {
        return false;
    }

    @Override
    public boolean read(byte[] data, int off, int size, Integer length) {
        return false;
    }

    @Override
    public boolean write(byte[] data, int off, int size, Integer length) {
        return false;
    }

    @Override
    public boolean close() {
        return false;
    }
}
