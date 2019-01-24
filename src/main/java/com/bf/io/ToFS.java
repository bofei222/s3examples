package com.bf.io;


import org.omg.CORBA.IntHolder;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

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
        File dir = new File(storageConfig.getDirPath() + "/" + hash);
        file = new File(dir + "/" + id);
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
        boolean flag = true;
        if (size > data.length) {
            flag = false;
            return flag;
        }
        try {
            raf = new RandomAccessFile(file, "rw");
            // 文件长度，字节数
            long fileLength = file.length();
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
            RandomAccessFile raf = null;
            try {
                raf = new RandomAccessFile(file, "rw");
                // 文件长度，字节数
                // 无视off参数，直接在文件末尾追加
//                off = file.length();
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
        } catch (Exception e) {
            e.printStackTrace();
            flag = false;
        } finally {
            return flag;
        }
    }

    @Override
    public boolean delete(String id) {
        try {
            String hash = MyUtil.hash(id);
            File dir = new File(storageConfig.getDirPath() + "/" + hash);
            file = new File(dir + "/" + id);
            boolean delete = file.delete();
            return delete;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean close() {
        return true;
    }
}
