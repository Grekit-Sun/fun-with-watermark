package com.yifan.funwithwatermark;

import com.yifan.funwithwatermark.RxjavaTest.User;
import com.yifan.funwithwatermark.RxjavaTest.WeChatServer;

import java.util.ArrayList;

/**
 * @Description:
 * @Author: ZhengXiang Sun
 * @Data:
 */
public class Main {

    public static void main(String[] args) {

        WeChatServer server = new WeChatServer();

        User user1 = new User("孙悟空");
        User user2 = new User("沙悟净");
        User user3 = new User("猪犺鬣");
        User user4 = new User("唐僧");

        server.add(user1);
        server.add(user2);
        server.add(user3);
        server.add(user4);

        server.pushMsg("微信更新了");

    }
}
