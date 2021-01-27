package com.yifan.funwithwatermark.demo;

/**
 * @Description:
 * @Author: ZhengXiang Sun
 * @Data: 2021-01-07
 */
public class AndroidJni {

    static {
        System.load("main");
    }

    public native void dynamiclog();

    public native void staticlog();

}
