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


    public ToFS(StorageConfig storageConfig) {
        this.storageConfig = storageConfig;
    }

    @Override
    public boolean open(String id, String flag) {
        if ("w".equals(flag)) {
            String hash = MyUtil.hash(id);
            File dir = new File(storageConfig.getDirPath() + File.separator + hash);
            dir.mkdirs();
            file = new File(dir + File.separator + id);
            if (!file.exists()) {
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return true;
        } else {
            file = new File(storageConfig.getFilePath());
            return true;
        }
    }

    @Override
    public boolean read(byte[] data, long off, long size, IntHolder length) {
        RandomAccessFile raf = null;
        try {
            raf = new RandomAccessFile(file, "rw");
            raf.seek(off);
            int length1 = (int)file.length();
            byte[] bytes = null;
            if (size > (length1 - off)) {
                bytes = new byte[(int)(length1 - off)];
            } else {
                bytes = new byte[(int)size];
            }
            raf.read(bytes);
//            System.out.println(new String(bytes));
            length.value = bytes.length;

            System.arraycopy(bytes, 0, data, 0, bytes.length);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if (raf != null) {
                    raf.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return true;
        }
    }

    @Override
    public boolean write(byte[] data, long off, long size, IntHolder length) {
        try {
            RandomAccessFile raf = new RandomAccessFile(file, "rw");
            byte[] bytes = new byte[(int)size];
            System.arraycopy(data, 0, bytes, 0, bytes.length);

            raf.seek(off);
            raf.write(bytes);
            length.value = bytes.length;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public boolean close() {
        return true;
    }
}
