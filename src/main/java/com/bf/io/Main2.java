package com.bf.io;

import org.apache.commons.lang3.RandomStringUtils;
import org.omg.CORBA.IntHolder;

import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

/**
 * @author bofei
 * @date 2019/1/10 13:01
 */
public class Main2 {


    // 读
    public static void main(String[] args) throws IOException {
//        byte[] b = null;
        StorageConfig sc = new StorageConfig("c:/test/s3.properties");
        sc.init();
        StorageFile toS3 = new ToS3(sc);

        boolean open = toS3.open("bofei的一个15+15+5MB文件", "r");
//        boolean open = toS3.open("cc795aa0-eeb9-4913-b6ae-6599833ece92", "r");
        IntHolder holder = new IntHolder();

        FileOutputStream fos = null;
        try {
             fos = new FileOutputStream("C:\\test\\_aa");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        int i = 0;
        byte[] b = new byte[1024 * 1024];
        Long l = 0L;
        Long l2 = 36700160L;

//        while (holder.value != -1) {
//            toS3.read(b, l, b.length, holder);
////            l += holder.value;
//            System.out.println(l + "------------" + holder.value);
//            fos.write(b, 0, holder.value);
//        }
        while (l < l2) {
            System.out.println("==========" + ++i);
            toS3.read(b, l, b.length, holder);
            l += holder.value;
            System.out.println(l + "------------" + holder.value);
            fos.write(b, 0, holder.value);
        }
        toS3.close();
        fos.close();
//        System.out.println("length-----------" + holder.value);



    }
    // 写
    public static void main3(String[] args) throws IOException {
        int p1 = 15 * 1024 * 1024; //分段大小在 5MB - 5GB 之间，只有最后一个分段才允许小于 5MB，不可避免的
        int p2 = 15* 1024 * 1024; //分段大小在 5MB - 5GB 之间，只有最后一个分段才允许小于 5MB，不可避免的
        int p3 = 5 * 1024 * 1024; //分段大小在 5MB - 5GB 之间，只有最后一个分段才允许小于 5MB，不可避免的
        byte[] b1 = RandomStringUtils.randomAlphabetic(p1).getBytes(); //填充一个 5MB 的字符串
        byte[] b2 = RandomStringUtils.randomAlphabetic(p2).getBytes(); //填充一个 5MB 的字符串
        byte[] b3 = RandomStringUtils.randomAlphabetic(p3).getBytes(); //填充一个 5MB 的字符串
        byte[] b4 = "abcdefg".getBytes();
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream("c:/test/aaa", true));
        bos.write(b1);
        bos.write(b2);
        bos.write(b3);

        StorageConfig sc = new StorageConfig("C:\\test\\s3.properties");
        sc.init();
        StorageFile toS3 = new ToS3(sc);
        boolean open = toS3.open("bofei的一个15+15+5MB文件", "w");
        IntHolder holder = new IntHolder();
        toS3.write(b1, 0, b1.length, holder);
        toS3.write(b2, b1.length, b2.length, holder);
        toS3.write(b3, b2.length, b3.length, holder);
//        toS3.write(b4, 0, b4.length, holder);
        toS3.close();

    }

    // file attr
    public static void main2(String[] args) {
        StorageConfig sc = new StorageConfig("C:\\test\\s3.properties");
        sc.init();
        S3FileAttr s3 = new S3FileAttr(sc);
        boolean test = s3.open("test");
        System.out.println(new Date(s3.getLastModified()));
        System.out.println(s3.getLength());
    }
    // 删
    public static void main7(String[] args) {
        StorageConfig sc = new StorageConfig("C:\\test\\s3.properties");
        sc.init();
        StorageFile toS3 = new ToS3(sc);
        boolean test = toS3.delete("test");
        System.out.println(test);
    }
}
