package kh.science.utils.config;

import lombok.Data;
import org.springframework.context.annotation.Configuration;

/**
 * @author ：Lkh
 * @date ：Created in 2023/8/28 12:00 PM
 */
@Data
@Configuration
public class OssClientConfiguration {
    /**
     * 公网地址
     */
    String endpoint = "https://oss-cn-beijing.aliyuncs.com";

    /**
     * AccessKey
     */
    String accessKeyId = "LTAI5t81UM4avZFMgMczJah4";

    /**
     * KeySecret
     */
    String accessKeySecret = "1h98uFnEICgGBFpSzgfVPxyPwfjbbK";

    /**
     * bucket名
     */
    String bucketName = "oss-xmls";

}
