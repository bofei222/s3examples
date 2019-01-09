package com.bf.io;

import javax.sound.midi.Soundbank;

/**
 * @Author bofei
 * @Date 2019/1/8 12:57
 * @Description
 */
public class MyUtil {
    public static void main(String[] args) {
        String s = "as,4-3<5";
        hash(s);
    }

    public static String hash(String s) {
        char[] chars = s.toCharArray();
        int sum = 0;
        for (int i : chars) {
            sum += i;
        }
//        System.out.println(sum);
        String b = String.format("%04d", sum);
//        System.out.println(b);
        return b;
    }
}
