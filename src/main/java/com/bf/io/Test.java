package com.bf.io;

import java.io.*;
import java.nio.channels.FileChannel;
import java.util.Arrays;

public class Test {

    public static void main2(String[] args) throws IOException {
        long start = System.currentTimeMillis();

        FileChannel in = new FileInputStream("d:/CentOS-6.5-i386-bin-DVD1.rar").getChannel(),
                    out = new FileOutputStream("e:/CentOS-6.5-i386-bin-DVD1.rar").getChannel();
        in.transferTo(0, in.size(), out);
        System.out.println(System.currentTimeMillis() - start);
    }
    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        // 源文件与目标文件
        File sourceFile = new File("D:/", "CentOS-6.5-i386-bin-DVD1.rar");
        File targetFile = new File("E:/", "CentOS-6.5-i386-bin-DVD1.rar");
        // 输入输出流
        FileInputStream fis = null;
        FileOutputStream fos = null;
        // 数据缓冲区
        byte[] buf = new byte[Integer.MAX_VALUE -2];
//        byte[] buf2 = new byte[1024000000];

        try {
            fis = new FileInputStream(sourceFile);
            fos = new FileOutputStream(targetFile);
            // 数据读写
            int k;
            while ((k = fis.read(buf)) != -1) {

                System.out.println("write data..." + k);
                fos.write(buf);
            }
        } catch (FileNotFoundException e) {
            System.out.println("指定文件不存在");
        } catch (IOException e) {
            // TODO: handle exception
        } finally {
            try {
                // 关闭输入输出流
                if (fis != null)
                    fis.close();

                if (fos != null)
                    fos.close();
                System.out.println(System.currentTimeMillis() - start);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}

