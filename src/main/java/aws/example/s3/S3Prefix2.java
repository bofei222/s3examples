package aws.example.s3;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ListObjectsRequest;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.S3ObjectSummary;

import java.util.LinkedList;
import java.util.List;

/**
 * @Author bofei
 * @Date 2019/2/22 10:11
 * @Description
 */
public class S3Prefix2 {
    public static void main(String[] args) {
        String bucket = "com.bf2";
        AmazonS3 s3 = AmazonS3ClientBuilder.standard().withRegion("cn-northwest-1").build();

        String prefix = "bofei-test/";
        for (String key : getObjectsListFromS3(s3, bucket, prefix)) {
            System.out.println(key);
        }
    }
    public static List<String> getObjectsListFromS3(AmazonS3 s3, String bucket, String prefix) {
        final String delimiter = "/";
        if (!prefix.endsWith(delimiter)) {
            prefix = prefix + delimiter;
        }

        List<String> paths = new LinkedList<>();
        ListObjectsRequest request = new ListObjectsRequest().withBucketName(bucket).withPrefix(prefix);

        ObjectListing result;
        do {
            result = s3.listObjects(request);

            for (S3ObjectSummary summary : result.getObjectSummaries()) {
                // Make sure we are not adding a 'folder'
                if (!summary.getKey().endsWith(delimiter)) {
                    paths.add(summary.getKey());
                }
            }
            request.setMarker(result.getMarker());
        }
        while (result.isTruncated());

        return paths;
    }
}
