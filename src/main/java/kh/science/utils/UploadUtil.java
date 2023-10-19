package kh.science.utils;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.PutObjectRequest;
import com.aliyun.oss.model.PutObjectResult;
import kh.science.utils.config.OssClientConfiguration;
import org.jsoup.internal.StringUtil;
import org.springframework.util.StringUtils;

import java.io.InputStream;
import java.net.URL;
import java.util.Date;

/**
 * @author ：Lkh
 * @date ：Created in 2023/8/28 8:21 PM
 */
public class UploadUtil {

    OssClientConfiguration ossClientConfiguration = new OssClientConfiguration();
    // 创建OSSClient实例
    OSS ossClient = new OSSClientBuilder().build(ossClientConfiguration.getEndpoint(), ossClientConfiguration.getAccessKeyId(), ossClientConfiguration.getAccessKeySecret());

    public String uploadFile(String url) {
        if (StringUtil.isBlank(url)) {
            return "";
        }
        String objectName = getObjectName(url);
        String fileUrl = "";
        // 创建OSSClient实例
        try {
            URL imageUrl = new URL(url);
            InputStream inputStream = imageUrl.openStream();
            // 创建PutObjectRequest对象
            PutObjectRequest putObjectRequest = new PutObjectRequest(ossClientConfiguration.getBucketName(), objectName, inputStream);

            // 上传文件
            PutObjectResult result = ossClient.putObject(putObjectRequest);

            // 获取上传后的文件访问地址
            fileUrl = ossClient.generatePresignedUrl(ossClientConfiguration.getBucketName(), objectName, new Date(System.currentTimeMillis() + (3600 * 1000))).toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fileUrl;
    }

    /**
     * 获取重新想拉取的图片名
     *
     * @param url 图片路径
     * @return 唯一图片名
     */
    private static String getObjectName(String url) {
        if (StringUtils.isEmpty(url)) {
            return "";
        }
//        排除不是url的情况
        if (!url.contains("http")) {
            return "";
        }
        // 获取最后一个/前面的内容
//        url地址https://mmbiz.qpic.cn/sz_mmbiz_jpg/uJepfQdDoJs9VcYUzyiaaqbpctP15QCdNm9DOfBZibjIoy0tnB7iazczC3NbhEo4lFB6eoydyHqQGnzbWm3lrOWicQ/640?wx_fmt=jpeg
        String[] segments = url.split("/");
        String segmentBeforeJpgName = segments[segments.length - 2];
        return segmentBeforeJpgName + ".jpeg";
    }

}
