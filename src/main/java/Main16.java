import com.sun.javafx.event.CompositeEventTarget;

/**
 * @Author bofei
 * @Date 2019/1/20 18:16
 * @Description
 */
public class Main16 {
    public static void main(String[] args) {
        test();
    }

    public static void test() {
        for (int i = 0; i < 100; i++) {
            System.out.println(i);
//            return;
        }
        System.out.println("0.0");
    }
}
