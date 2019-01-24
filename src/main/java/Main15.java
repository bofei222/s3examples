import org.omg.CORBA.LongHolder;

import javax.sound.midi.Soundbank;
import java.io.File;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Date;

/**
 * @Author bofei
 * @Date 2019/1/15 15:01
 * @Description
 */
class A {
    Long h = 2000L;
}
public class Main15 {
    private Long id = 2000L;

    public static void main(String[] args) {
        Main15 main15 = new Main15();
        System.out.println(main15.id);
        main15.change(main15.id);
        System.out.println(main15.id);
        System.out.println("-----------");

        A a = new A();
        System.out.println(a.h);
        main15.change(a.h);
        System.out.println(a.h);

        System.out.println("---------LongHolder.value");
        LongHolder lh = new LongHolder(2000);
        System.out.println(lh.value);
        main15.change(lh.value);
        System.out.println(lh.value);

        System.out.println("---------LongHolder");
        LongHolder lh2 = new LongHolder(2000);
        System.out.println(lh2.value);
        main15.changeLH(lh2);
        System.out.println(lh2.value);

        System.out.println("----------A");
        System.out.println(a.h);
        main15.changeA(a);
        System.out.println(a.h);

    }

    public void change(Long id) {
        id = 4000L;
//        System.out.println(id);
//        LongHolder

    }
    public void changeA(A a) {
        a.h = 4000L;
    }
    public void changeLH(LongHolder lh) {
        lh.value = 4000L;
    }



    public static void main3(String[] args) {
        test();
    }

    static int i = 0;

    public static void test() {
        test();
        System.out.println(++i);
        return;

    }

    public static void test2() {
        return;
    }

    public static void main2(String[] args) {
//        Integer i = 0;
//        for (int i1 = 0; i1 < 100; i1++) {
//            System.out.println(i++);
//        }
//
//        // 切割空串""   得到一个length=1的数组 arr[0]=空串""
//        String[] split = "".split(",");
//        System.out.println(split.length);
        //空指针错误
//        Arrays.asList(null);

        System.out.println("-----------");

        File file = new File("C:test/123.txt");
        if (!file.exists()) {

        }
        long length = file.length();
        System.out.println(length);
        long lastModified = file.lastModified();
        boolean delete = file.delete();
        System.out.println(delete);
        File file1 = new File(file, "123");

        System.out.println(new Date(lastModified));


    }
}
