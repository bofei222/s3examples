package com.bf.io;

import javax.sound.midi.Soundbank;

/**
 * @Author bofei
 * @Date 2019/1/8 12:57
 * @Description
 */
public class MyUtil {
    public static void main(String[] args) {
        String s = "bofei的一个9M+9M文件";
        hash(s);
    }

    public static String hash(String s) {
        char[] chars = s.toCharArray();
        int sum = 0;
        for (int i : chars) {
            sum += i;
        }

        System.out.println(sum);

        String b = String.format("%04d", (sum % 1024) + 1);
        System.out.println(b);
        return b;
    }
}
