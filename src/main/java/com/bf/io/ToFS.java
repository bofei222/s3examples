package com.bf.io;


import org.omg.CORBA.IntHolder;

import java.io.*;

/**
 * @Author bofei
 * @Date 2019/1/8 12:46
 * @Description
 */
public class ToFS implements StorageFile{

    private StorageConfig storageConfig;
    private File file;
    RandomAccessFile raf = null;

    public ToFS(StorageConfig storageConfig) {
        this.storageConfig = storageConfig;
    }

    @Override
    public boolean open(String id, String flag) {
        String hash = MyUtil.hash(id);
        File dir = new File(storageConfig.getDirPath() + File.separator + hash);
        file = new File(dir + File.separator + id);
        if ("w".equals(flag)) {
            dir.mkdirs();
            if (!file.exists()) {
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return true;
        } else {
            return true;
        }
    }

    @Override
    public boolean read(byte[] data, long off, long size, IntHolder length) {
        if (size > data.length) {
            return false;
        }
        boolean flag = true;
        try {
            raf = new RandomAccessFile(file, "rw");
            raf.seek(off);

            int read = raf.read(data, 0, (int) size);
            length.value = read;
        } catch (IOException e) {
            e.printStackTrace();
            flag = false;
        }finally {
            try {
                if (raf != null) {
                    raf.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return flag;
        }
    }

    @Override
    public boolean write(byte[] data, long off, long size, IntHolder length) {
        if (size > data.length) {
            return false;
        }
        boolean flag = true;
        try {
            raf = new RandomAccessFile(file, "rw");
            raf.seek(off);
            raf.write(data, 0, (int)size);
            length.value = (int)size;
        } catch (IOException e) {
            e.printStackTrace();
            flag = false;
        } finally {
            try {
                if (raf != null) {
                    raf.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return flag;
        }
    }

    @Override
    public boolean close() {
        return true;
    }
}
