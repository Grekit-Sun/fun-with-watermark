package com.yifan.funwithwatermark.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author sun
 * @Date 2020-11-16
 * Description:
 */
public class AlgorithmUtil {

    /**
     * 字符串筛选
     * @param str
     * @return url
     */
    public static String urlTransform(String str){
        String url = null;
        if (str == null || str.length() <= 0) {
            return null;
        }
        String regex = "(http)s?://v.douyin.com/\\w+/";
        Pattern compile = Pattern.compile(regex);
        Matcher matcher = compile.matcher(str);
        if (matcher.find()) {
            url = matcher.group();
        }
        return url;
    }
}
