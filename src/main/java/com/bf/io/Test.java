package com.bf.io;

import java.io.*;
import java.util.Arrays;

public class Test {

    public static void main(String[] args) {
        // 源文件与目标文件
        File sourceFile = new File("D:/", "test.txt");
        File targetFile = new File("E:/", "test.txt");
        // 输入输出流
        FileInputStream fis = null;
        FileOutputStream fos = null;
        // 数据缓冲区
        byte[] buf = new byte[0];

        try {
            fis = new FileInputStream(sourceFile);
            fos = new FileOutputStream(targetFile);
            // 数据读写
            int k = 0;
            while (k != -1) {
                k = fis.read(buf);
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
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}
