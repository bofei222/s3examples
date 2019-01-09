package com.bf.io;

import java.util.Arrays;

/**
 * @Author bofei
 * @Date 2019/1/9 14:05
 * @Description
 */
public class CallByValue {
    private static int x=10;
    private static int[] arr = {1, 2, 3};

    public static void updateValue(int value){
        value = 3 * value;
    }

    public static void updateArr(int[] arr) {

        int[] brr = {4, 5, 6};
//        arr = brr;
        arr[0] =4;
        arr[1] = 5;
        arr[2] = 6;
        System.out.println(Arrays.toString(arr));

    }
    public static void main(String[] args) {
        System.out.println("调用前x的值："+x);
        updateValue(x);
        System.out.println("调用后x的值："+x);

        System.out.println("调用前arr的值："+Arrays.toString(arr));
        updateArr(arr);
        System.out.println("调用后arr的值："+Arrays.toString(arr));
    }


}
