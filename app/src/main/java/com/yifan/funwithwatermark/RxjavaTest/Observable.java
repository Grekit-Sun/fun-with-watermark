package com.yifan.funwithwatermark.RxjavaTest;

/**
 * @Description: 被观察者
 * @Author: ZhengXiang Sun
 * @Data: 2021-01-27
 */
public interface Observable {

    void add(Observer observer);

    void remove(Observer observer);

    void notifyObservers();
}
