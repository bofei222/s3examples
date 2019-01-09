package com.bf.io;

import org.omg.CORBA.IntHolder;

/**
 * @Author bofei
 * @Date 2019/1/8 12:49
 * @Description
 */
public interface StorageFile {
    boolean open(String id, String flag);

    boolean read(byte[] data, long off, long size, IntHolder length);

    boolean write(byte[] data, long off, long size, IntHolder length);

    boolean close();
}
