package com.example.utils.demo.util.alioss;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.*;
import com.example.utils.demo.util.CommonUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Slf4j
public class AliOssUtil {

    private static String endpoint = "http://oss-cn-beijing.aliyuncs.com";
    private static String accessKeyId = "******";
    private static String accessKeySecret = "******";
    private static String bucketName = "******";

    /**
     * 上传文件 - 普通
     * @return
     */
    public static String uploadFileToOSS(File file, String suffix) {
        String fileName = initObjectName(suffix);
        PutObjectResult result = null;
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        try {
            // 判断Bucket是否存在
            if (ossClient.doesBucketExist(bucketName)) {
                log.info("您已经创建Bucket：{}。", bucketName);
            } else {
                log.info("您的Bucket不存在，创建Bucket：{}。", bucketName);
                ossClient.createBucket(bucketName);
            }
            // 文件存储入OSS，Object的名称为fileKey。
            result =ossClient.putObject(bucketName, fileName, file);
            log.info("上传文件Object：{},存入OSS成功。", fileName);
            return fileName;
        } catch (OSSException oe) {
            log.error("上传文件到OSS出现异常。{}{}", oe.getMessage(), oe);
        } catch (ClientException ce) {
            log.error("上传文件到OSS出现异常。{}{}", ce.getMessage(), ce);
        } catch (Exception e) {
            log.error("上传文件到OSS出现异常。{}{}", e.getMessage(), e);
        } finally {
            //关闭OSSClient
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
        return null;
    }

    /**
     * 上传文件 - 迁移数据
     * @return
     */
    public static String transformUploadFileToOSS(File file, String fileName) {
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        try {
            // 文件存储入OSS，Object的名称为fileKey。
            ossClient.putObject(bucketName, fileName, file);
            log.info("上传文件Object：{},存入OSS成功。", fileName);
            return fileName;
        } catch (OSSException oe) {
            log.error("上传文件到OSS出现异常。{}{}", oe.getMessage(), oe);
        } catch (ClientException ce) {
            log.error("上传文件到OSS出现异常。{}{}", ce.getMessage(), ce);
        } catch (Exception e) {
            log.error("上传文件到OSS出现异常。{}{}", e.getMessage(), e);
        } finally {
            //关闭OSSClient
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
        return null;
    }

    /**
     * 上传文件 - 分片异步
     * @return
     */
    public static String fragAsynUploadFileToOSS(File file, String suffix) {
        OSS ossClient = null;
        try {
            //初始化文件名
            String fileName = initObjectName(suffix);
            //创建ossClient实例
            ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
            // 创建InitiateMultipartUploadRequest对象。
            InitiateMultipartUploadRequest request = new InitiateMultipartUploadRequest(bucketName, fileName);
            //初始化分片
            InitiateMultipartUploadResult upResult = ossClient.initiateMultipartUpload(request);
            // 返回uploadId，它是分片上传事件的唯一标识，您可以根据这个ID来发起相关的操作，如取消分片上传、查询分片上传等。
            String uploadId = upResult.getUploadId();

            // partETags是PartETag的集合。PartETag由分片的ETag和分片号组成。
            List<PartETag> partETags = new ArrayList<>();
            // 计算文件有多少个分片。
            final long partSize = 1 * 1024 * 1024L; //1MB
            long fileLength = file.length();
            int partCount = (int) (fileLength / partSize);
            if (fileLength % partSize != 0) {
                partCount++;
            }

            //遍历分片上传
            List<UploadPartRequest> requests = new ArrayList<>();
            for (int i = 0; i < partCount; i++) {
                long startPos = i * partSize;
                long curPartSize = (i + 1 == partCount) ? (fileLength - startPos) : partSize;
                InputStream inputStream = new FileInputStream(file);
                //跳过已上传的分片
                inputStream.skip(startPos);
                UploadPartRequest uploadPartRequest = new UploadPartRequest();
                uploadPartRequest.setBucketName(bucketName);
                uploadPartRequest.setKey(fileName);
                uploadPartRequest.setUploadId(uploadId);
                uploadPartRequest.setInputStream(inputStream);
                // 设置分片大小。除了最后一个分片没有大小限制，其他的分片最小为100KB。
                uploadPartRequest.setPartSize(curPartSize);
                // 设置分片号。每一个上传的分片都有一个分片号，取值范围是1~10000，如果超出这个范围，OSS将返回InvalidArgument的错误码。
                uploadPartRequest.setPartNumber(i + 1);

                requests.add(uploadPartRequest);
            }

            //异步上传
            final OSS client = ossClient;
            List<CompletableFuture<PartETag>> futures = new ArrayList<>();
            requests.forEach(partition -> {
                CompletableFuture<PartETag> future = CompletableFuture.supplyAsync(() -> {
                    // 每个分片不需要按顺序上传，甚至可以在不同客户端上传，OSS会按照分片号排序组成完整的文件。
                    UploadPartResult uploadPartResult = client.uploadPart(partition);
                    return uploadPartResult.getPartETag();
                });
                futures.add(future);
            });

            futures.forEach(f -> {
                PartETag join = f.join();
                // 每次上传分片之后，OSS的返回结果会包含一个PartETag。PartETag将被保存到partETags中。
                partETags.add(join);
            });

            // 创建CompleteMultipartUploadRequest对象。
            // 在执行完成分片上传操作时，需要提供所有有效的partETags。OSS收到提交的partETags后，会逐一验证每个分片的有效性。当所有的数据分片验证通过后，OSS将把这些分片组合成一个完整的文件。
            CompleteMultipartUploadRequest completeMultipartUploadRequest =
                    new CompleteMultipartUploadRequest(bucketName, fileName, uploadId, partETags);

            // 如果需要在完成文件上传的同时设置文件访问权限，请参考以下示例代码。
            // completeMultipartUploadRequest.setObjectACL(CannedAccessControlList.PublicRead);

            // 完成上传。
            ossClient.completeMultipartUpload(completeMultipartUploadRequest);
            log.info("分片异步上传文件Object：{},存入OSS成功。", fileName);
            return fileName;
        } catch (OSSException oe) {
            log.error("上传文件到OSS出现异常。{}{}", oe.getMessage(), oe);
        } catch (ClientException ce) {
            log.error("上传文件到OSS出现异常。{}{}", ce.getMessage(), ce);
        } catch (Exception e) {
            log.error("上传文件到OSS出现异常。{}{}", e.getMessage(), e);
        } finally {
            //关闭OSSClient
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
        return null;
    }

    /**
     * 下载文件为File用于读取
     * @return
     */
    public static File downloadFileFromOSS(String pathName, String objectName) {
        File file = null;
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        try {
            file = new File(pathName+objectName.substring(objectName.lastIndexOf("/") + 1));
            // 下载OSS文件到本地文件。如果指定的本地文件存在会覆盖，不存在则新建。
            ossClient.getObject(new GetObjectRequest(bucketName, objectName), file);
            log.info("下载文件fileName：{},成功。", objectName);
            return file;
        } catch (OSSException oe) {
            log.error("从OSS上下载文件出现异常。{}{}", oe.getMessage(), oe);
        } catch (ClientException ce) {
            log.error("从OSS上下载文件出现异常。{}{}", ce.getMessage(), ce);
        } catch (Exception e) {
            log.error("从OSS上下载文件出现异常。{}{}", e.getMessage(), e);
        } finally {
            //关闭OSSClient
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
        return null;
    }

    /**
     * 删除文件-单个
     * @return
     */
    public static String removeFileFromOSS(String pathName) {
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        try {
            // 删除Object。
            ossClient.deleteObject(bucketName, pathName);
            log.info("删除单个文件：{}成功。", pathName);
            return pathName;
        } catch (OSSException oe) {
            log.error("从OSS上删除单个文件出现异常。{}{}", oe.getMessage(), oe);
        } catch (ClientException ce) {
            log.error("从OSS上删除单个文件出现异常。{}{}", ce.getMessage(), ce);
        } catch (Exception e) {
            log.error("从OSS上删除单个文件出现异常。{}{}", e.getMessage(), e);
        } finally {
            //关闭OSSClient
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
        return null;
    }

    /**
     * 删除文件-多个
     * @return
     */
    public static List<String> removeBatchFileFromOSS(List<String> pathNameList) {
        List<String> deletedObjects = null;
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        try {
            DeleteObjectsResult deleteObjectsResult = ossClient.deleteObjects(new DeleteObjectsRequest(bucketName).withKeys(pathNameList));
            deletedObjects = deleteObjectsResult.getDeletedObjects();
            log.info("删除多个文件成功，文件名为：{}。", deletedObjects.toString());
            return deletedObjects;
        } catch (OSSException oe) {
            log.error("从OSS上批量删除文件出现异常。{}{}", oe.getMessage(), oe);
        } catch (ClientException ce) {
            log.error("从OSS上批量删除文件出现异常。{}{}", ce.getMessage(), ce);
        } catch (Exception e) {
            log.error("从OSS上批量删除文件出现异常。{}{}", e.getMessage(), e);
        } finally {
            //关闭OSSClient
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
        return null;
    }

    /**
     * 查看文件
     * @return
     */
    public static List<String> listFileFromOSS() {
        List<String> fileNameList = new ArrayList<>();
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        try {
            // 查看Bucket中的Object。
            ObjectListing objectListing = ossClient.listObjects(bucketName);
            List<OSSObjectSummary> objectSummary = objectListing.getObjectSummaries();
            for (OSSObjectSummary object : objectSummary) {
                fileNameList.add(object.getKey());
            }
            return fileNameList;
        } catch (OSSException oe) {
            log.error("从OSS查看文件列表出现异常。{}{}", oe.getMessage(), oe);
        } catch (ClientException ce) {
            log.error("从OSS查看文件列表出现异常。{}{}", ce.getMessage(), ce);
        } catch (Exception e) {
            log.error("从OSS查看文件列表出现异常。{}{}", e.getMessage(), e);
        } finally {
            //关闭OSSClient
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
        return null;
    }

    /**
     * 初始化文件名
     * @return
     */
    private static String initObjectName(String suffix) {
        // 打散目录
        String[] directory = CommonUtil.getDateFolder();
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(directory[0]);
        stringBuffer.append("/");
        stringBuffer.append(directory[1]);
        stringBuffer.append("/");
        stringBuffer.append(directory[2]);
        stringBuffer.append("/");
        stringBuffer.append(UUID.randomUUID().toString().replace("-", ""));
        stringBuffer.append(".");
        stringBuffer.append(suffix);
        return stringBuffer.toString();
    }

    public static void main(String[] args) {
        List<String> fileList = AliOssUtil.listFileFromOSS();
        System.out.println(fileList.toString());
    }
}
