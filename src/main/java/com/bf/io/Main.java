package com.bf.io;


import org.apache.commons.lang3.RandomStringUtils;
import org.omg.CORBA.IntHolder;

/**
 * @author bofei
 * @date 2019/1/9 10:35
 */
public class Main {
    // read
    // 读指定偏移量off、期望长度的数据size的，实际长度length的数据
    public static void main2(String[] args) {
        StorageConfig storageConfig = new StorageConfig("fs");
        storageConfig.init();
        ToFS toFS = new ToFS(storageConfig);

        toFS.open("0", "r");
        byte[] b = new byte[100];
        IntHolder length = new IntHolder();

        boolean read = toFS.read(b, 2, 1, length);
        System.out.println(length.value);
        System.out.println(new String(b));
    }

    // write
    public static void main(String[] args) {
        String s = "123456";
        int minPartSize = 10 * 1024 * 1024;
        byte[] bytes = s.getBytes();
        byte[] bytes2 = RandomStringUtils.randomAlphabetic(minPartSize).getBytes(); //填充一个 5MB 的字符串

        StorageConfig storageConfig = new StorageConfig("fs");
        storageConfig.init();
        ToFS toFS = new ToFS(storageConfig);
        boolean open = toFS.open("zxcvbnm", "w");
        IntHolder length = new IntHolder();
        toFS.write(bytes, 2, 3, length);
        System.out.println(length.value);

    }
}
