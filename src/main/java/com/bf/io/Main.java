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

        toFS.open("zxcvbnm", "r");
        byte[] b = new byte[100];
        IntHolder length = new IntHolder();

        boolean read = toFS.read(b, 2, 10, length);
        System.out.println(length.value);
        System.out.println(read);
        System.out.println(new String(b));
    }

    // write
    public static void main(String[] args) {
        String s = "123";
        int minPartSize = 5 * 1024 * 1024;
        byte[] bytes2 = s.getBytes();
        byte[] bytes = RandomStringUtils.randomAlphabetic(minPartSize).getBytes(); //填充一个 5MB 的字符串

        StorageConfig storageConfig = new StorageConfig("fs");
        storageConfig.init();
        ToFS toFS = new ToFS(storageConfig);
        boolean open = toFS.open("zxcvbnm", "w");
        IntHolder length = new IntHolder();


        boolean write = toFS.write(bytes, 3, bytes.length, length);
        Thread thread = new Thread(new MyWork(toFS, 0));
        Thread thread1 = new Thread(new MyWork(toFS, 5242883));
        thread.start();
        thread1.start();
        System.out.println(write);
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