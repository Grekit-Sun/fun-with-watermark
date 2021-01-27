package com.yifan.funwithwatermark.RxjavaTest;

import java.util.ArrayList;

/**
 * @Description:
 * @Author: ZhengXiang Sun
 * @Data: 2021-01-27
 */
public class WeChatServer implements Observable {

    private ArrayList<Observer> list;

    private String msg;

    public WeChatServer() {
        list = new ArrayList<>();
    }

    @Override
    public void add(Observer observer) {
        list.add(observer);
    }

    @Override
    public void remove(Observer observer) {
        list.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for(Observer observer : list){
            observer.update(msg);
        }
    }

    //send msg
    public void pushMsg(String msg){
        this.msg = msg;
        System.out.println("微信服务号更新了消息：" + msg);
        notifyObservers();
    }
}
