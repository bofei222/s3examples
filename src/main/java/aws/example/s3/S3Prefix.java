package aws.example.s3;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ListObjectsV2Request;
import com.amazonaws.services.s3.model.ListObjectsV2Result;

import java.util.Arrays;

/**
 * @Author bofei
 * @Date 2019/2/22 10:07
 * @Description
 */
public class S3Prefix {

    public static AmazonS3 s3 = AmazonS3ClientBuilder.standard().withRegion(Regions.CN_NORTHWEST_1).build();

    public static void main(String[] args) {
        Arrays.asList("shipin/", "bofei-test/").forEach(S3Prefix::listPrefix);
    }

    public static void listPrefix(String prefix) {
        System.out.println("Listing prefix '" + prefix + "'");
        final ListObjectsV2Result result = s3.listObjectsV2(new ListObjectsV2Request()
                .withPrefix(prefix)
                .withBucketName("com.bf2")
                .withDelimiter("/"));

        System.out.println("\tCommon prefixes");
        result.getCommonPrefixes().forEach(p -> System.out.println("\t\t" + p));

        System.out.println("\tKeys");
        result.getObjectSummaries().forEach(s -> System.out.println("\t\t" + s.getKey()));
    }
}