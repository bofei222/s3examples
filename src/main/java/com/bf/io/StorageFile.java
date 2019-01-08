package com.bf.io;

/**
 * @Author bofei
 * @Date 2019/1/8 12:49
 * @Description
 */
public interface StorageFile {
    boolean open(String id, String flag);

    boolean read(byte[] data, int off, int size, Integer length);

    boolean write(byte[] data, int off, int size, Integer length);

    boolean close();

}
