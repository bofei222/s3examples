package com.bf.io;

import org.apache.commons.lang3.ArrayUtils;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @Author bofei
 * @Date 2019/1/8 9:41
 * @Description
 */
public class Test2 {
    public static void main2(String[] args) throws IOException {


        String str = "!!hello everybody!";
        byte[] words = str.getBytes();
        OutputStream fos = null;
        fos = new FileOutputStream("e:/test.txt");//此文件输出流对象fos就和目标数据源(c:/test.txt)联系起来了。
        fos.write(words, 0, 5);//利用write方法将数据写入到文件中去
        System.out.println("文件已更新!");
    }
    // Byte[] byte[] List<Byte>
    public static void main3(String[] args) throws IOException {
        String str = "789";
        byte[] words = str.getBytes();

        RandomAccessFile raf = new RandomAccessFile(new File("C:\\test\\bofei\\test.txt"), "rw");
        raf.seek(2);
//        raf.write(words);
        byte[] data = new byte[10];
        raf.read(data);
        System.out.println(new String(data));
        System.out.println(data.length);

        Byte[] byteObject = ArrayUtils.toObject(data);
        List<Byte> list = new ArrayList<Byte>(Arrays.asList(byteObject));
        System.out.println(list.size());
        boolean a = list.add(new Byte("1"));
        System.out.println(list.size());
    }

    public static void main(String[] args) throws FileNotFoundException {

        File file = new File("f:/test");
        file.mkdirs();
        File file2 = new File(file + "/1.txt");
        if (!file2.exists()) {
            try {
                file2.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        new FileInputStream(file2);

        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main4(String[] args) {
        try {
            SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
            System.err.println(sdf2.format(new Date()));
            // File f = new File("C:\\errorJmsMessage"+sdf2.format(new
            // Date())+".txt");
            // 创建文件夹
            File tf = new File("D:\\临时文件");
            if (!tf.exists()) {
                tf.mkdir();
            }
            File f = new File("D:\\临时文件\\errorJmsMessage"
                    + sdf2.format(new Date()) + ".txt");
            if (!f.exists()) {
                f.createNewFile();
                System.err.println(f + "已创建！");
            }
            String str;
            byte[] s;
            RandomAccessFile rf = new RandomAccessFile(f, "rw");

            long count = rf.length();

            rf.seek(count);

            // 如要换行，用 \r\n 次序不要乱

            for (int i = 0; i < 4; i++) {
                str = "这是第   " + i + "行文本示例\r\n";
                s = str.getBytes();
                rf.write(s);
            }
            rf.close();

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("读写出错");

        }
    }
}
