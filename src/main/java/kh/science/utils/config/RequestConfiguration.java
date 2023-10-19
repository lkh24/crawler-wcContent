package kh.science.utils.config;

/**
 * @author ：Lkh
 * @date ：Created in 2023/10/19 9:43 AM
 *
 * 获取微信公众号的请求参数配置
 * 这里根据自己登录的微信公众号，获取自己的参数然后自行配置（注意微信公众号的过期时间为三天）
 */
public class RequestConfiguration {
    /**
     * 需要获取到的文章的内容
     */
    public static final String URL = "https://mp.weixin.qq.com/cgi-bin/appmsg";
    public static final String FAKEID = "MjM5NTQxNzg0MQ==";

    public static final String TOKEN = "";

    public static final String COOKIE ="";

    /**
     * 为保证获取的内容不乱码，需要添加的头部信息
     */
    public static final String HTML_HEAD = "<head>\n" +
            "    <meta charset=\"UTF-8\">\n" +
            "</head>\n" +
            "<style >\n" +
            "    div {\n" +
            "        visibility: visible !important;\n" +
            "    }\n" +
            "    img {\n" +
            "        max-width: 100% !important;\n" +
            "    }\n" +
            "</style>";

    /**
     * 获取的页数（我要获取的页数共98页）
     */
    public static final int PAGE = 98;
}
