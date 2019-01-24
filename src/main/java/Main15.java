
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;

/**
 * @Author bofei
 * @Date 2019/1/15 15:01
 * @Description
 */
public class Main15 {
    public static void main(String[] args) throws IOException {
        byte[] b = new byte[1];
        FileInputStream fis = new FileInputStream("c:/test/bofei/test.txt");

        int l = 0;
        while ((l = fis.read(b)) != -1) { // 判断为-1 之后就不进入循环体，没必要写出b中的内容
            System.out.println(l);
            System.out.println(new String(b));
        }
    }
    public static void main2(String[] args) {
        Integer i = 0;
        for (int i1 = 0; i1 < 100; i1++) {
            System.out.println(i++);
        }

        // 切割空串""   得到一个length=1的数组 arr[0]=空串""
        String[] split = "".split(",");
        System.out.println(split.length);
        //空指针错误
        Arrays.asList(null);


    }
}
