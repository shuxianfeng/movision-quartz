package com.movision.utils.oss;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import com.aliyun.oss.model.BucketInfo;
import com.movision.utils.file.FileUtil;
import com.movision.utils.propertiesLoader.PropertiesLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.aliyun.oss.ClientConfiguration;
import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSException;
import com.movision.constants.MsgCodeConstant;
import com.movision.exception.BusinessException;
import com.movision.utils.ApplicationPropertiesUtils;
import com.movision.utils.FileUtils;

import javax.imageio.ImageIO;

/**
 * 阿里云OSS
 *
 * @author zhuangyuhao
 * @since 16/6/21.
 */
@Service
public class AliOSSClient {
    private static final Logger log = LoggerFactory.getLogger(AliOSSClient.class);

    // endpoint是访问OSS的域名。如果您已经在OSS的控制台上 创建了Bucket，请在控制台上查看域名。
    // 如果您还没有创建Bucket，endpoint选择请参看文档中心的“开发人员指南 > 基本概念 > 访问域名”，
    // 链接地址是：https://help.aliyun.com/document_detail/oss/user_guide/oss_concept/endpoint.html?spm=5176.docoss/user_guide/endpoint_region
    // endpoint的格式形如“http://oss-cn-hangzhou.aliyuncs.com/”，注意http://后不带bucket名称，
    // 比如“http://bucket-name.oss-cn-hangzhou.aliyuncs.com”，是错误的endpoint，请去掉其中的“bucket-name”。
    private static String endpoint = "http://oss-cn-shenzhen.aliyuncs.com";// "http://oss-cn-hangzhou.aliyuncs.com";

    // accessKeyId和accessKeySecret是OSS的访问密钥，您可以在控制台上创建和查看，
    // 创建和查看访问密钥的链接地址是：https://ak-console.aliyun.com/#/。
    // 注意：accessKeyId和accessKeySecret前后都没有空格，从控制台复制时请检查并去除多余的空格。
    private static String accessKeyId = "LTAITI0FOKgMZQty";
    private static String accessKeySecret = "5x12H1yASg31OlPanhTCg0z0PzNr3t";

    // Bucket用来管理所存储Object的存储空间，详细描述请参看“开发人员指南 > 基本概念 > OSS基本概念介绍”。
    // Bucket命名规范如下：只能包括小写字母，数字和短横线（-），必须以小写字母或者数字开头，长度必须在3-63字节之间。
    private static String bucketName = "doc-movision";

    // Object是OSS存储数据的基本单元，称为OSS的对象，也被称为OSS的文件。详细描述请参看“开发人员指南 > 基本概念 >
    // OSS基本概念介绍”。
    // Object命名规范如下：使用UTF-8编码，长度必须在1-1023字节之间，不能以“/”或者“\”字符开头。
    private static String firstKey = "my-first-key";

    /**
     * 初始化client
     *
     * @return
     */
    private OSSClient init() {
        // 创建ClientConfiguration实例，按照您的需要修改默认参数
        ClientConfiguration conf = new ClientConfiguration();
        // 开启支持CNAME选项
        conf.setSupportCname(true);
        // 创建OSSClient实例
        OSSClient ossClient = null;

        try {
            ossClient =  new OSSClient(endpoint, accessKeyId, accessKeySecret, conf);
            // 判断Bucket是否存在。详细请参看“SDK手册 > Java-SDK > 管理Bucket”。
            // 链接地址是：https://help.aliyun.com/document_detail/oss/sdk/java-sdk/manage_bucket.html?spm=5176.docoss/sdk/java-sdk/init
            if (ossClient.doesBucketExist(bucketName)) {
                log.debug("您已经创建Bucket：" + bucketName + "。");
            } else {
                log.debug("您的Bucket不存在，创建Bucket：" + bucketName + "。");
                // 创建Bucket。详细请参看“SDK手册 > Java-SDK > 管理Bucket”。
                // 链接地址是：https://help.aliyun.com/document_detail/oss/sdk/java-sdk/manage_bucket.html?spm=5176.docoss/sdk/java-sdk/init
                ossClient.createBucket(bucketName);
            }

            // 查看Bucket信息。详细请参看“SDK手册 > Java-SDK > 管理Bucket”。
            // 链接地址是：https://help.aliyun.com/document_detail/oss/sdk/java-sdk/manage_bucket.html?spm=5176.docoss/sdk/java-sdk/init
            BucketInfo info = ossClient.getBucketInfo(bucketName);
            log.debug("Bucket " + bucketName + "的信息如下：");
            log.debug("\t数据中心：" + info.getBucket().getLocation());
            log.debug("\t创建时间：" + info.getBucket().getCreationDate());
            log.debug("\t用户标志：" + info.getBucket().getOwner());

        } catch (ClientException ce) {
            ce.printStackTrace();
            log.error(ce.getErrorCode() + ":" + ce.getErrorMessage());
        }
        return ossClient;
    }

    /**
     * 上传文件流
     * 
     * @param filePath
     * @param parDiv
     * @param chann
     * @return
     */
    public Map<String, String> uploadFileStream(String filePath, String parDiv, String chann) {
        Map<String, String> result = new HashMap<>();

        log.info("阿里云OSS上传Started");
        OSSClient ossClient = init();

        try {
            // 上传文件流
            String domain;
            File file = new File(filePath);
            long size = file.length();
            InputStream in = new FileInputStream(file);
            // InputStream in = file.getInputStream();
            String fileName = file.getName();
            String fileKey;
            String fileName2 = FileUtils.renameFile(fileName);
            if (chann != null) {
                fileKey = "upload/" + chann + "/img/" + parDiv + "/" + fileName2;
            } else {
                fileKey = "upload/" + fileName2;
            }

            String data = "";
            bucketName = ApplicationPropertiesUtils.getValue("img.bucket");
            domain = ApplicationPropertiesUtils.getValue("img.domain");
            data = "//" + domain + "/" + fileKey;
            String maxSize = ApplicationPropertiesUtils.getValue("uploadPicMaxPostSize");
            if (size > Long.valueOf(maxSize)) {
                throw new BusinessException(MsgCodeConstant.SYSTEM_ERROR, "文件大小超过最大限制");
            }
            ossClient.putObject(bucketName, fileKey, in);

            log.debug("Object：" + fileKey + "存入OSS成功。");
            result.put("status", "success");
            result.put("data", data);

        } catch (OSSException oe) {
            oe.printStackTrace();
            result.put("status", "fail");
            log.error("AliOSSClient.uploadFileStream()",oe);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            result.put("status", "fail");
            log.error("AliOSSClient.uploadFileStream()",e);
            return result;
        } finally {
            ossClient.shutdown();
        }

        log.info("阿里云OSS上传Completed");

        return result;
    }

    /**
     * 上传本地文件
     *
     * @param file  本地文件名
     * @param type  文件类型 IMG:图片 | FILE:其他文件
     * @param chann 频道
     * @return
     */
    public Map<String, Object> uploadLocalFile(File file, String type, String chann) {
        Map<String, Object> result = new HashMap<>();

        log.info("阿里云OSS上传Started");
        OSSClient ossClient = init();

        try {
            // 文件存储入OSS，Object的名称为fileKey。详细请参看“SDK手册 > Java-SDK > 上传文件”。
            // 链接地址是：https://help.aliyun.com/document_detail/oss/sdk/java-sdk/upload_object.html?spm=5176.docoss/user_guide/upload_object
            String fileKey;
            String domain;
            String fileName = FileUtil.renameFile(file).getName();
            if (chann != null) {
                fileKey = "upload/" + chann + "/" + type + "/" + fileName;
            } else {
                fileKey = "upload/" + fileName;
            }

            String data = "";
            if (type.equals("img")) {
                //图片
                bucketName = PropertiesLoader.getValue("img.bucket");
                domain = PropertiesLoader.getValue("ali.domain");
                data = domain + "/" + fileKey;
                //返回图片的宽高
                InputStream is = new FileInputStream(file);
                BufferedImage src = ImageIO.read(is);
                result.put("width", src.getWidth());
                result.put("height", src.getHeight());

            } else if (type.equals("doc")) {
                //文档
                bucketName = PropertiesLoader.getValue("file.bucket");
                data = fileName;
            }

            ossClient.putObject(bucketName, fileKey, file);
            log.debug("Object：" + fileKey + "存入OSS成功。");
            log.info("【上传Alioss的返回值】：" + result.toString());
            result.put("status", "success");

            result.put("url", data);

        } catch (OSSException oe) {
            oe.printStackTrace();
            log.error(oe.getErrorCode() + ":" + oe.getErrorMessage());
            result.put("status", "fail");
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            result.put("status", "fail");
            return result;
        } finally {
            ossClient.shutdown();
        }

        log.info("阿里云OSS上传Completed");

        return result;
    }

    // test
    public static void main(String[] args) {
        AliOSSClient client = new AliOSSClient();
        String fileName = "/Users/zhuangyuhao/Downloads/1111.jpg";
        File file = new File(fileName);
        // Map<String, String> result = client.uploadLocalFile(file, "doc",
        // null);
        // System.out.println(result);
        // String name = file.getName();
        // String a = FileUtil.renameFile(name);
        // System.out.println(a);
        // Map<String, Object> map = client.downloadStream(fileName, "doc",
        // "job");
        // System.out.println(map);
    }

}
