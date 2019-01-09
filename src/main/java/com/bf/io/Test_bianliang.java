package com.bf.io;

import org.omg.CORBA.IntHolder;

/**
 * @Author bofei
 * @Date 2019/1/9 13:57
 * @Description
 */
public class Test_bianliang {



    public static void swap(IntHolder x, IntHolder y){
//        int temp = x.value;
        x.value = x.value * 3;
//        x.value = y.value;
//        y.value = temp;
    }
    public static void main(String[] args) {
        IntHolder x = new IntHolder(10);
        IntHolder y = new IntHolder(20);
        System.out.println(x.value + "---" + y.value);
        swap(x, y);
        System.out.println(x.value + "---" + y.value);
    }

}
