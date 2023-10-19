package kh.science;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import kh.science.entity.AppMsgItem;
import kh.science.entity.Content;
import kh.science.entity.LikeItem;
import kh.science.entity.UseSchoolItem;
import kh.science.mapper.ContentMapper;
import kh.science.utils.UploadUtil;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static kh.science.utils.config.RequestConfiguration.*;

/**
 * @author ?Lkh
 * @date ?Created in 2023/8/22 7:53 PM
 */

public class GetUseSchoolContent {

    public static void main(String[] args) throws IOException {
        kyAccounts();
//        kyWebsite();
    }

    /**
     * 获取官网信息，其他官网类似
     */
    private static void kyWebsite() throws IOException {
        ContentMapper contentMapper = new ContentMapper();
        // 页面 URL
        List<String> urlList = Lists.newArrayList();
        OkHttpClient client = new OkHttpClient();
        for (int i = 1; i <= PAGE; i++) {
            String contentUrl = "https://www.sxcast.edu.cn/xwzx/zonghexinwen-" + i + ".html";
            urlList.add(contentUrl);
        }
        System.out.println("======================================");
        urlList.forEach(t -> {
            try {
                Document doc = Jsoup.connect(t).get(); // 获取页面内容
                Elements liElements = doc.select(".li-tuwen li"); // 选择包含信息的li元素

                ArrayList<Content> contentList = Lists.newArrayList();
                for (Element li : liElements) {

                    Element a = li.selectFirst("a"); // 获取a标签元素
                    String href = a.attr("href"); // 获取href属性值
                    String title = a.attr("title"); // 获取title属性值

                    Element img = a.selectFirst("img"); // 获取img标签元素
                    String imgSrc = img.attr("src"); // 获取img src属性值

                    Element span = a.selectFirst("span"); // 获取时间标签元素

                    System.out.println("Href: " + href);
                    System.out.println("Title: " + title);
                    System.out.println("Img Src: " + imgSrc);
                    System.out.println("span: " + span.text());
                    System.out.println("view: " + getView(client, href));

                    // 解析HTML
                    Document htmlDoc = Jsoup.connect(href).get();
                    Elements articleElement = htmlDoc.select(".content");
//                        如果要取出那些标签就用这个
                    String articleContent = "";
                    for (Element element : articleElement) {
                        /*Elements select1 = element.select(".w," +
                                ".x-nav," +
                                ".page-breadcrumb," +
                                ".sider-box," +
                                ".footer," +
                                ".header");
                        select1.forEach(Node::remove);*/
                        articleContent = element.outerHtml(); // 获取标签本身的HTML内容
                        System.out.println(articleContent);
                    }
                    //入库
                    Content content = Content.builder()
                            .href(href)
                            .title(title)
                            .img(imgSrc)
                            .time(span.text())
                            .view(getView(client, href))
                            .content(articleContent)
                            .id(getId(href))
                            .build();
                    contentList.add(content);
                    System.out.println();
                }
                contentMapper.batchInsertMapper(contentList);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        System.out.println(contentMapper.findListMapper());
    }
    @NotNull
    private static Integer getView(OkHttpClient client, String href) throws IOException {
        String number = getId(href);
        String viewUrl = "https://www.sxcast.edu.cn/index.php?s=api&c=module&siteid=1&app=news&m=hits&id=" + number;

        HttpUrl.Builder urlBuilder = HttpUrl.parse(viewUrl).newBuilder();
        Request request = setBuildRequest(urlBuilder);
        Response response = client.newCall(request).execute();

        String body = response.body().string();
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonData = body.substring(body.indexOf("{"), body.lastIndexOf("}") + 1);

        Integer msg = objectMapper.readValue(jsonData, LikeItem.class).getMsg();
        return msg;
    }

    @NotNull
    private static String getId(String href) {
        String[] parts = href.split("/");
        String lastPart = parts[parts.length - 1];

        // 提取数字部分
        String number = lastPart.replaceAll("\\D+", "");
        return number;
    }

    /**
     * 获取公众号
     */
    private static void kyAccounts() {
        OkHttpClient client = new OkHttpClient();
        ArrayList<AppMsgItem> msgList = Lists.newArrayList();
//        这里设置页数，如果要全拿需要设置间隔时间
        for (int i = 1; i < 2; i++) {
            HttpUrl.Builder urlBuilder = setBuildHeader(i);
            Request request = setBuildRequest(urlBuilder);
            try {
                Response response = client.newCall(request).execute();
                String body = response.body().string();
                ObjectMapper objectMapper = new ObjectMapper();
                UseSchoolItem appMsg = objectMapper.readValue(body, UseSchoolItem.class);
                msgList.addAll(appMsg.getApp_msg_list());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        String getDiv = "div#js_content";
        List<String> urlList = msgList.stream()
                .sorted(Comparator.comparingLong(AppMsgItem::getCreate_time))
                .map(AppMsgItem::getLink)
                .map(m->pageProcessing(m, getDiv, "a.wx_tap_link.js_wx_tap_highlight.weui-wa-hotarea"))
                .collect(Collectors.toList());
        System.out.println(urlList);

    }

    /**
     * 页面内标签处理
     * @param url 传入需要处理公众号url
     * @param getDiv 需要获取的div
     * @param remDiv 需要删除的div
     * @return
     */
    private static String pageProcessing(String url, String getDiv, String remDiv) {
        UploadUtil uploadUtil = new UploadUtil();
        try {
            Document doc = Jsoup.connect(url).get();
            Element articleDiv = doc.selectFirst(getDiv);
            Elements excludeElements = null;
            if (articleDiv != null) {
                excludeElements = articleDiv.select(remDiv);
                excludeElements.remove();
            }

//          修改图片链接
            //  SVG 标签元素
            Elements svgElements = doc.select("svg");
            forEachElementsUrl(uploadUtil, svgElements);

            //  style 标签元素
            Elements sectionElements = doc.select("section[style]");
            forEachElementsUrl(uploadUtil, sectionElements);

            // 处理 <img> 元素
            Elements imgElements = null;
            if (articleDiv != null) {
                imgElements = articleDiv.select("img, [data-src]");
                for (Element imgElement : imgElements) {
                    String originalDataUrl = imgElement.attr("data-src");
                    String newDataUrl = uploadUtil.uploadFile(originalDataUrl);
                    imgElement.attr("src", newDataUrl);
                    imgElement.removeAttr("data-src");
                }
            }

//          向拉取的内容中添加h1标签
            Element h1Element = doc.selectFirst("h1.rich_media_title");

            // 获取 <div id="js_article" class="rich_media"> 标签内部内容并且拼接h1标签
            return HTML_HEAD+(h1Element != null ? h1Element.outerHtml() : null)+(articleDiv != null ? articleDiv.outerHtml() : null);
        } catch (IOException | NullPointerException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 遍历每个 SVG 元素，替换其中的 background-image URL
     *
     * @param uploadUtil 图片处理工具实例
     * @param svgElements 标签内容
     */
    private static void forEachElementsUrl(UploadUtil uploadUtil, Elements svgElements) {
        for (Element svgElement : svgElements) {
            // 获取 style 属性值
            String style = svgElement.attr("style");

            if (style.equals("margin-bottom:unset;")) {
                svgElement.removeAttr("style");
            }
            // 解析 style 属性值为键值对形式
            Map<String, String> styleMap = null;
            try {
                styleMap = Arrays.stream(style.split(";"))
                        .map(s -> s.split(":", 2))
                        .collect(Collectors.toMap(
                                s -> s[0].trim(),
                                s -> s[1].trim()
                        ));
            } catch (Exception e) {
                e.printStackTrace();
            }

            // 获取 background-image 的 URL 值
            String backgroundImageUrl = null;
            if (styleMap != null) {
                backgroundImageUrl = styleMap.get("background-image");
            }

            if (backgroundImageUrl != null) {
                // 去掉 url() 字符串
                backgroundImageUrl = backgroundImageUrl.replaceAll("url\\((.*?)\\)", "$1");
                // 将 &quot; 转义回 "
                String urlCon = backgroundImageUrl.replace("&quot;", "\"");

                // 调用 OSS 上传文件并获取新的 URL 地址
                String uploadedUrl = uploadUtil.uploadFile(urlCon.replaceAll("\"", "").trim());

                // 更新 styleMap 中的 background-image URL 值
                styleMap.put("background-image", uploadedUrl);

                // 重新构建 style 属性值，拼接 URL 地址
                StringBuilder sb = new StringBuilder();
                for (Map.Entry<String, String> entry : styleMap.entrySet()) {
                    if (entry.getKey().equals("background-image")) {
                        sb.append(entry.getKey()).append(": url(&quot;").append(uploadedUrl).append("&quot;)");
                    } else {
                        sb.append(entry.getKey()).append(": ").append(entry.getValue());
                    }
                    sb.append("; ");
                }
                String newStyle = sb.toString().trim();

                // 更新 SVG 元素的 style 属性
                svgElement.attr("style", newStyle);
            }
        }
    }

    @NotNull
    private static HttpUrl.Builder setBuildHeader(int i) {
        HttpUrl.Builder urlBuilder = HttpUrl.parse(URL).newBuilder();
        urlBuilder.addQueryParameter("action", "list_ex");
        urlBuilder.addQueryParameter("begin", String.valueOf(i * 5));
        urlBuilder.addQueryParameter("count", "5");
        urlBuilder.addQueryParameter("fakeid", FAKEID);
        urlBuilder.addQueryParameter("type", "9");
        urlBuilder.addQueryParameter("query", "");
        urlBuilder.addQueryParameter("token", TOKEN);
        urlBuilder.addQueryParameter("lang", "zh_CN");
        urlBuilder.addQueryParameter("f", "json");
        urlBuilder.addQueryParameter("ajax", "1");
        return urlBuilder;
    }

    @NotNull
    private static Request setBuildRequest(HttpUrl.Builder urlBuilder) {
        Request request = new Request.Builder()
                .url(urlBuilder.build())
                .addHeader("Cookie", COOKIE)
                .addHeader("User-Agent", "PostmanRuntime/7.32.3")
                .addHeader("Accept", "*/*")
//                    .addHeader("Accept-Encoding","gzip, deflate, br")
                .addHeader("Connection", "keep-alive")
                .addHeader("Postman-Token", "<calculated when request is sent>")
                .addHeader("Cache-Control", "no-cache")
                .addHeader("Content-Type", "application/json; charset=UTF-8")
                .build();
        return request;
    }
}

