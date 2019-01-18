import java.util.Arrays;

/**
 * @Author bofei
 * @Date 2019/1/15 15:01
 * @Description
 */
public class Main15 {
    public static void main(String[] args) {
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
