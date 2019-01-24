package com.bf.io;


import org.apache.commons.lang3.RandomStringUtils;
import org.omg.CORBA.IntHolder;

/**
 * @author bofei
 * @date 2019/1/9 10:35
 */
public class Main {

    // fileAttr
    public static void main(String[] args) {
        StorageConfig storageConfig = new StorageConfig("C:\\test\\fs.properties");
        storageConfig.init();
        FSFileAttr attr = new FSFileAttr(storageConfig);
        boolean zxcvbnm = attr.open("zxcvbnm");
        System.out.println(attr.getLength());
        System.out.println(attr.getLastModified());

    }
    // delete
    public static void main7(String[] args) {
        StorageConfig storageConfig = new StorageConfig("C:\\test\\fs.properties");
        storageConfig.init();
        ToFS toFS = new ToFS(storageConfig);
        // 删除不用open
        toFS.delete("zxcvbnm");
    }
    // read
    // 读指定偏移量off、期望长度的数据size的，实际长度length的数据
    public static void main2(String[] args) {
        StorageConfig storageConfig = new StorageConfig("fs");
        storageConfig.init();
        ToFS toFS = new ToFS(storageConfig);

        toFS.open("zxcvbnm", "r");
        byte[] b = new byte[100];
        IntHolder length = new IntHolder();

        boolean read = toFS.read(b, 2, 10, length);
        System.out.println(length.value);
        System.out.println(read);
        System.out.println(new String(b));
        System.out.println(b.length);
    }

    // write
    public static void main11(String[] args) {
        String s = "abcdef";
        String s2 = "123456";
        String s3 = "987";
        int minPartSize = 5 * 1024 * 1024;
        byte[] b = s.getBytes();
        byte[] b2 = s2.getBytes();
        byte[] b3 = s3.getBytes();
        byte[] bytes2 = RandomStringUtils.randomAlphabetic(minPartSize).getBytes(); //填充一个 5MB 的字符串

        StorageConfig storageConfig = new StorageConfig("C:\\test\\fs.properties");
        storageConfig.init();
        System.out.println(storageConfig);
        ToFS toFS = new ToFS(storageConfig);
        boolean open = toFS.open("zxcvbnm", "w");
        IntHolder length = new IntHolder();

        toFS.write(b, 1, b.length, length);
//        System.out.println(length.value);
        toFS.write(b2, 3, b2.length, length);
        toFS.write(b3, 6, b3.length, length);

//        Thread thread = new Thread(new MyWork(toFS, 0));
//        Thread thread1 = new Thread(new MyWork(toFS, 5242883));
//        thread.start();
//        thread1.start();
//        System.out.println(write);
        System.out.println(length.value);

    }
}

class MyWork implements Runnable {
    private StorageFile storageFile;
    private Integer off;


    public MyWork(StorageFile storageFile, Integer off) {
        this.storageFile = storageFile;
        this.off = off;
    }

    @Override
    public void run() {
        byte[] bytes = "123".getBytes();
        IntHolder intHolder = new IntHolder();
        storageFile.write(bytes, off, bytes.length, intHolder);
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}