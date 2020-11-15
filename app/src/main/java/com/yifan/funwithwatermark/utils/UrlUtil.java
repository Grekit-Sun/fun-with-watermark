package com.yifan.funwithwatermark.utils;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @Author sun
 * @Date 2020-11-16
 * Description:
 */
public class UrlUtil {

    /**
     * url校验
     * @param webUrl
     * @return
     */
    public static boolean urlCheck1  (String webUrl) {
        URL url;
        try {
            url = new URL(webUrl);
            url.openStream();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * url校验
     * @param webUrl
     * @return
     */
    public static boolean urlCheck(String webUrl) {
        try {
            // 设置此类是否应该自动执行 HTTP重定向（响应代码为 3xx 的请求）。
            HttpURLConnection.setFollowRedirects(false);
            // 到URL所引用的远程对象的连接
            HttpURLConnection conn = (HttpURLConnection) new URL(webUrl).openConnection();
            // 设置URL请求的方法，GET POST HEAD OPTIONS PUT DELETE TRACE
            // 以上方法之一是合法的，具体取决于协议的限制。
            conn.setRequestMethod("HEAD");
            // 从HTTP响应消息获取状态码
            return (conn.getResponseCode() == HttpURLConnection.HTTP_OK);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


}
