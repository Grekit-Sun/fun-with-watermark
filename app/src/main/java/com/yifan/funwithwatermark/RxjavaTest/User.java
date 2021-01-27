package com.yifan.funwithwatermark.RxjavaTest;

/**
 * @Description:
 * @Author: ZhengXiang Sun
 * @Data: 2021-01-27
 */
public class User implements Observer {

    private String name;
    private String msg;

    public User(String name) {
        this.name = name;
    }

    @Override
    public void update(Object msg) {
        this.msg = (String) msg;
        read();
    }

    private void read() {
        System.out.println(name + "收到了推送消息：" + msg);
    }
}
