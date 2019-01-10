package com.bf.io;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.RandomStringUtils;

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
    // arr、list的addAll()
    public static void main(String[] args) {
        String[] first = {"a", "b", "c"};
        String[] second = {"1", "2", "3"};
        String[] both = (String[])ArrayUtils.addAll(first, second);
        System.out.println(Arrays.toString(both));

        byte[] b = new byte[5];
//        byte[] b2 = RandomStringUtils.randomAlphabetic(4 * 1024 * 1024).getBytes();
//        byte[] bytes = ArrayUtils.addAll(b, b2);

//        System.out.println(new String(bytes));
//        System.out.println(bytes.length);

        byte b3 = 127;

        System.out.println(Arrays.toString(b));
        Byte[] byteObject = ArrayUtils.toObject(b);
        List<Byte> list = new ArrayList<Byte>(Arrays.asList(byteObject));
        System.out.println(list.size());
        List l = new ArrayList();
        l.add("1");
        List l2 = new ArrayList();
        l2.add("2");
        System.out.println(l.size());
        l2.addAll(l);
        Object[] objects = l2.toArray();
        System.out.println(l2.toString());
        System.out.println("-------------------------------------");
        List<Byte> al = new ArrayList<Byte>();
        al.add((byte)1);
        al.add((byte)20);
        al.add((byte)30);
        al.add((byte)40);

//        Integer[] arr = new Integer[al.size() - 1];
//        System.out.println(arr.length);
        Byte[] bytes = new Byte[al.size()];
         bytes = al.toArray(bytes);
//        System.out.println(Arrays.toString(integers));
//        System.out.println(Arrays.toString(arr));
//        System.out.println(arr.length);
        byte[] b4 = ArrayUtils.toPrimitive(bytes);
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

    public static void main6(String[] args) throws FileNotFoundException {

        File file = new File("c:/test2/bofei/1.txt");
//        file.mkdirs();
//        File file2 = new File(file + "/1.txt");
//        if (!file2.exists()) {
//            try {
//                file2.createNewFile();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//        new FileInputStream(file2);
//
//        try {
//            file.createNewFile();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
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
