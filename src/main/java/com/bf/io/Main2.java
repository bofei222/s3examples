package com.bf.io;

import org.apache.commons.lang3.RandomStringUtils;
import org.omg.CORBA.IntHolder;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @author bofei
 * @date 2019/1/10 13:01
 */
public class Main2 {

    // 读
    public static void main(String[] args) throws IOException {
        byte[] b = new byte[36700160];
//        byte[] b = null;
        StorageConfig sc = new StorageConfig("s3.properties");
        sc.init();
        StorageFile toS3 = new ToS3(sc);
//        Size: 35.00 MB ( bytes)
        boolean open = toS3.open("bofei的一个15+15+5MB文件", "r");
        IntHolder holder = new IntHolder();
        OutputStream os = new FileOutputStream("c:/test/bb");
        toS3.read(b, 0, b.length, holder);
//        System.out.println(new String(b));
        os.write(b);
        os.close();
//        long l = 0;
//        while (holder.value != -1) {
//            toS3.read(b, l, b.length, holder);
//            l += holder.value;
//            os.write(b);
////            System.out.println("b-----------" + new String(b));
//            System.out.println("length-----------" + holder.value);
//        }

//os.flush();




    }
    // 写
    public static void main2(String[] args) {
        int p1 = 15 * 1024 * 1024; //分段大小在 5MB - 5GB 之间，只有最后一个分段才允许小于 5MB，不可避免的
        int p2 = 15 * 1024 * 1024; //分段大小在 5MB - 5GB 之间，只有最后一个分段才允许小于 5MB，不可避免的
        int p3 = 5 * 1024 * 1024; //分段大小在 5MB - 5GB 之间，只有最后一个分段才允许小于 5MB，不可避免的
        byte[] b1 = RandomStringUtils.randomAlphabetic(p1).getBytes(); //填充一个 5MB 的字符串
        byte[] b2 = RandomStringUtils.randomAlphabetic(p2).getBytes(); //填充一个 5MB 的字符串
        byte[] b3 = RandomStringUtils.randomAlphabetic(p3).getBytes(); //填充一个 5MB 的字符串
        byte[] b4 = "abcdefg".getBytes();

        StorageConfig sc = new StorageConfig("C:\\test\\s3.properties");
        sc.init();
        StorageFile toS3 = new ToS3(sc);
        boolean open = toS3.open("bofei的一个15+15+5MB文件", "w");
        IntHolder holder = new IntHolder();
        toS3.write(b1, 0, b1.length, holder);
        toS3.write(b2, 0, b2.length, holder);
        toS3.write(b3, 0, b3.length, holder);
//        toS3.write(b4, 0, b4.length, holder);
        toS3.close();

    }
}
