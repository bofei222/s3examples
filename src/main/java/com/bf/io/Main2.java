package com.bf.io;

import org.apache.commons.lang3.RandomStringUtils;
import org.omg.CORBA.IntHolder;

/**
 * @author bofei
 * @date 2019/1/10 13:01
 */
public class Main2 {
    public static void main(String[] args) {
        int minPartSize = 5 * 1024 * 1024; //分段大小在 5MB - 5GB 之间，只有最后一个分段才允许小于 5MB，不可避免的
        byte[] bytes = RandomStringUtils.randomAlphabetic(minPartSize).getBytes(); //填充一个 5MB 的字符串

        StorageConfig sc = new StorageConfig("s3");
        sc.init();
        StorageFile toS3 = new ToS3(sc);
        boolean open = toS3.open("zxcvbnm", "w");
        IntHolder holder = new IntHolder();
        toS3.write(bytes, 0, bytes.length, holder);
        toS3.write(bytes, 0, bytes.length, holder);
        toS3.close();

    }
}
