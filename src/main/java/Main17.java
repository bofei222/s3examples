
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.builder.ToStringExclude;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * @Author bofei
 * @Date 2019/1/20 19:41
 * @Description
 */
public class Main17 {

    @Test
    public void givenUsingApache_whenGeneratingRandomAlphabeticString_thenCorrect() {
        String generatedString = RandomStringUtils.randomAlphabetic(10);

        System.out.println(generatedString);
    }
    @Test
    public void givenUsingApache_whenGeneratingRandomStringBounded_thenCorrect() {

        int length = 10;
        boolean useLetters = true;
        boolean useNumbers = false;
        String generatedString = RandomStringUtils.random(length, useLetters, useNumbers);

        System.out.println(generatedString);
    }
//    public static void main2(String[] args) throws IOException {
//        RandomAccessFile raf = new RandomAccessFile("C:\\test\\bofei\\123.txt", "rw");
//        raf.seek(10);
//        byte[] b = new byte[2];
//        int read = raf.read(b, 0, 2);
//        System.out.println(read);
//        System.out.println();
//        System.out.println();
//    }
}
