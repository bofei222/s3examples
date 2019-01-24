package com.bf.io;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.*;
import org.omg.CORBA.IntHolder;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author bofei
 * @Date 2019/1/8 12:49
 * @Description
 */
public class ToS3 implements StorageFile {
    // 读和写全局配置
    private StorageConfig storageConfig;

    // 读或者写标识
    private String rw = null;
    // 对象key
    String s3Key = null;
    // s3Client
    AmazonS3 s3Client = null;

    //以下是写所需要的全局变量
    // 分段上传 段数标识
    private Integer i = 1;
    // 创建一个列表保存所有分传的 PartETag, 在分段完成后会用到
    List<PartETag> partETags = new ArrayList<>();
    // 第一步，初始化，声明下面将有一个 Multipart Upload
    InitiateMultipartUploadRequest initRequest = null;
    InitiateMultipartUploadResult initResponse = null;

    // 要上传的分片大小  最小值
    private int minPartSize = 500 * 1024 * 1024;
    // 缓冲区的大小
    private int buffSize = 1000 * 1024 * 1024;
//    private int buffSize = Integer.MAX_VALUE - 2;
    private byte[] buff = new byte[buffSize];
    private Integer pos = 0;


    // 以下是读所需要的全局变量

    public ToS3(StorageConfig storageConfig) {
        this.storageConfig = storageConfig;
    }

    @Override
    public boolean open(String id, String flag) {
        rw = flag;
        System.out.println("open");
        String hash = MyUtil.hash(id);
        s3Key = storageConfig.getDirPath() + "/" + hash + "/" + id;
        System.out.println("s3key " + s3Key);
        s3Client = AmazonS3ClientBuilder.standard().withRegion(Regions.CN_NORTHWEST_1).build();
        if ("w".equals(flag)) {
            // 第一步，初始化，声明下面将有一个 Multipart Upload
            initRequest = new InitiateMultipartUploadRequest(storageConfig.getBucketName(),
                    s3Key);
            initResponse = s3Client.initiateMultipartUpload(initRequest);
        } else {
            return true;
        }
        return true;
    }

    GetObjectRequest request = null;
    S3ObjectInputStream is = null;
    S3Object object = null;
//    S3Object fullObject = null;
    @Override
    public boolean read(byte[] data, long off, long size, IntHolder length) {
//        System.out.println("read");
        boolean flag = true;
        if (data.length < (size - off)) {
            flag = false;
            return flag;
        }

        try {

            long range = off + size - 1;
            // range的起始坐标超过文件长度 会报错：InvalidRange 后面待改进 返回-1
//            request = new GetObjectRequest(storageConfig.getBucketName(), s3Key);
//            fullObject = s3Client.getObject(new GetObjectRequest(storageConfig.getBucketName(), s3Key));
//            if (fullObject.getObjectMetadata().getContentLength() < off + 1) {
//                length.value = -1;
//                System.out.println("off 超出range范围");
//                flag = false;
//                return flag;
//            }

            request = new GetObjectRequest(storageConfig.getBucketName(), s3Key)
                    .withRange(off, range);
//            System.out.println("Downloading an object");
            object = s3Client.getObject(request);

            is = object.getObjectContent();
            BufferedInputStream bis = new BufferedInputStream(is);
//            long contentLength = object.getObjectMetadata().getContentLength();
            int read = bis.read(data);
            length.value = read;
//            System.out.println(read);
            // .....................流 用过一次就没了
//            S3Util.displayTextInputStream(objectContent);
        } catch (AmazonServiceException e) {
            // The call was transmitted successfully, but Amazon S3 couldn't process
            // it, so it returned an error response.
            e.printStackTrace();
            flag = false;
        } catch (SdkClientException e) {
            // Amazon S3 couldn't be contacted for a response, or the client
            // couldn't parse the response from Amazon S3.
            e.printStackTrace();
            flag = false;
        } catch (IOException e) {
            e.printStackTrace();
            flag = false;
        }
        return flag;
//         finally {
//            // To ensure that the network connection doesn't remain open, close any open input streams.
//            return flag;
//        }
    }

    // 1。说白了，buff大于500M，上传是在write,
    //    buff小于500M 就close,write中还没上传，所以上传在close中
    // 2。上传就刷新buff，重置pos
    // size是准的 length=0无用的
    @Override
    public boolean write(byte[] data, long off, long size, IntHolder length) {
        boolean flag = true;
        try {
            if (data.length < minPartSize) { // data小于500M,缓存
                //            System.out.println("1");
                // 缓存到buff中
                System.arraycopy(data, 0, buff, pos, (int)size);
                pos += (int)size;
                if (pos > minPartSize) { // 已缓存的数据 大于500M，就上传到S3
                    //                System.out.println("3");
                    byte[] b = new byte[pos];
                    // 把缓存中的数据 从0到pos复制到新数组
                    System.arraycopy(buff, 0, b, 0, pos);
                    // 刷新buff pos
//                    buff = new byte[buffSize];
                    pos = 0;
                    upload(b);
                    return true;
                } else { // 已缓存的数据 还没到500M就先存着不动
                    //                System.out.println("2");
                }
            } else { // 大于500M就上传s3，
                if (pos != 0) { // 如果pos不等于0，说明buff中有不到500M的缓存数据，就把这次的和之前的一起传到s3
                    //                System.out.println("4");
                    System.arraycopy(data, 0, buff, pos, (int)size);
                    pos += (int)size;
                    byte[] b = new byte[pos];
                    System.arraycopy(buff, 0, b, 0, pos);
                    // 可不可以不定buff的大小，通过返回值的方式，返回多大就是多大，带改进
//                    buff = new byte[buffSize];
                    pos = 0;
                    upload(b);
                } else { // 如果buff中没有数据，就直接把这次的传到s3
                    //                System.out.println("5");
                    upload(data);
                }
            }
            return flag;
        } catch (Exception e) {
            e.printStackTrace();
            return flag;
        } finally {
            return flag;
        }
    }


    @Override
    public boolean close() {
        boolean flag = true;
        try {
            if ("w".equals(rw)) {
                System.out.println("write close");
                if (pos != 0) { // buff有数据就close，说明是一个文件，在close时上传，然后在合并；不然直接合并
//                    System.out.println("6");
                    byte[] b = new byte[pos];
                    System.arraycopy(buff, 0, b, 0, pos);
//                    buff = new byte[buffSize];
                    pos = 0;
                    upload(b);
                }
                System.out.println("7");
                // 第三步，完成上传，合并
                CompleteMultipartUploadRequest compRequest = new CompleteMultipartUploadRequest(storageConfig.getBucketName(), s3Key,
                        initResponse.getUploadId(), partETags);
                s3Client.completeMultipartUpload(compRequest);
            } else {
                try {
                    if (object != null) {
                        System.out.println("read close");
                        object.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return flag;
        } catch (SdkClientException e) {
            e.printStackTrace();
            flag = false;
        } finally {
            return flag;
        }
    }

    // 实际上传动作
    public void upload(byte[] data) {
        System.out.println("upload " + i);
        UploadPartRequest uploadRequest = new UploadPartRequest()
                .withBucketName(storageConfig.getBucketName())
                .withKey(s3Key)
                .withUploadId(initResponse.getUploadId())
                .withPartNumber(i++)
                .withInputStream(new ByteArrayInputStream(data))
                .withPartSize(data.length);

        // 第二步，上传分段，并把当前段的 PartETag 放到列表中
        partETags.add(s3Client.uploadPart(uploadRequest).getPartETag());
    }

    // 因为调用删除之前不调用open所以 delete里要左open中前面的操作
    @Override
    public boolean delete(String id) {
        try {
            String hash = MyUtil.hash(id);
            s3Key = storageConfig.getDirPath() + "/" + hash + "/" + id;

            s3Client = AmazonS3ClientBuilder.standard().withRegion(Regions.CN_NORTHWEST_1).build();
            s3Client.deleteObject(storageConfig.getBucketName(), s3Key);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
