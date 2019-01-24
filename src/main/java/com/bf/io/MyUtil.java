package com.bf.io;

/**
 * @Author bofei
 * @Date 2019/1/8 12:57
 * @Description
 */
public class MyUtil {
    public static void main(String[] args) {
        String s = "cc795aa0-eeb9-4913-b6ae-6599833ece92";
        hash(s);
    }

    public static String hash(String s) {
        char[] chars = s.toCharArray();
        int sum = 0;
        for (int i : chars) {
            sum += i;
        }

//        System.out.println(sum);

        String b = String.format("%04d", (sum % 1024) + 1);
//        System.out.println("" + b);
        return b;
    }
}
