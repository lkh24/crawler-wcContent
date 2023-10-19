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
    String endpoint = "";

    /**
     * AccessKey
     */
    String accessKeyId = "";

    /**
     * KeySecret
     */
    String accessKeySecret = "";

    /**
     * bucket名
     */
    String bucketName = "";

}
