package com.bf.io;

import org.omg.CORBA.IntHolder;

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
    public boolean read(byte[] data, long off, long size, IntHolder length) {
        return false;
    }

    @Override
    public boolean write(byte[] data, long off, long size, IntHolder length) {
        return false;
    }

    @Override
    public boolean close() {
        return false;
    }
}
