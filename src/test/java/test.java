import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
/**
 * @author ：Lkh
 * @date ：Created in 2023/10/4 11:33 AM
 *     获取微信公众号列表
 */
public class test {
    public static void main(String[] args) {
        String url = "https://mp.weixin.qq.com/mp/profile_ext?action=home&__biz=MjM5NzcwNzU5MQ==&scene=124#wechat_redirect";
        String html = getHtml(url);
        System.out.println(html);
    }

    public static String getHtml(String url1) {
        String html = null;
        try {
            // 目标URL
            String url = "http://mp.weixin.qq.com/s?__biz=MjM5NzcwNzU5MQ==&mid=2662633911&idx=2&sn=efdae85aeb27eabb6cb8a5d403edc32d&chksm=bd95db9f8ae252892970797c94fe6b83bcd87cda5f3019d9cf38b9c529b37dc837afa82ae585&scene=27#wechat_redirect";

            // 创建URL对象
            URL obj = new URL(url);

            // 打开连接
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            // 设置请求方法为GET
            con.setRequestMethod("GET");

            // 添加请求头（模拟浏览器请求）
            con.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) " +
                    "AppleWebKit/537.36 (KHTML, like Gecko) Chrome/71.0.3578.98 Safari/537.36");

            // 获取响应代码
            int responseCode = con.getResponseCode();
            System.out.println("Response Code: " + responseCode);

            // 读取响应内容
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                System.out.println(inputLine);
            }
            in.close();

            // 打印响应内容
            //System.out.println(response.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return html;
    }

}
