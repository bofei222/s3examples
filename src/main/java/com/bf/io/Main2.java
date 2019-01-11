package com.bf.io;

import org.apache.commons.lang3.RandomStringUtils;
import org.omg.CORBA.IntHolder;

/**
 * @author bofei
 * @date 2019/1/10 13:01
 */
public class Main2 {
    public static void main(String[] args) {
        int p1 = 5 * 1024 * 1024; //分段大小在 5MB - 5GB 之间，只有最后一个分段才允许小于 5MB，不可避免的
        int p2 = 3 * 1024 * 1024; //分段大小在 5MB - 5GB 之间，只有最后一个分段才允许小于 5MB，不可避免的
        int p3 = 3 * 1024 * 1024; //分段大小在 5MB - 5GB 之间，只有最后一个分段才允许小于 5MB，不可避免的
        byte[] b1 = RandomStringUtils.randomAlphabetic(p1).getBytes(); //填充一个 5MB 的字符串
        byte[] b2 = RandomStringUtils.randomAlphabetic(p2).getBytes(); //填充一个 5MB 的字符串
        byte[] b3 = RandomStringUtils.randomAlphabetic(p3).getBytes(); //填充一个 5MB 的字符串
        byte[] b4 = "abcdefg".getBytes();

        StorageConfig sc = new StorageConfig("s3");
        sc.init();
        StorageFile toS3 = new ToS3(sc);
        boolean open = toS3.open("bofei的一个test文件", "w");
        IntHolder holder = new IntHolder();
//        toS3.write(b1, 0, b1.length, holder);
//        toS3.write(b2, 0, b2.length, holder);
//        toS3.write(b3, 0, b3.length, holder);
        toS3.write(b4, 0, b4.length, holder);
        toS3.close();

    }
}
