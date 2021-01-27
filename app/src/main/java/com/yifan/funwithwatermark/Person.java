package com.yifan.funwithwatermark;

import androidx.annotation.NonNull;

/**
 * @Description:
 * @Author: ZhengXiang Sun
 * @Data:
 */
public class Person implements Cloneable{

    @NonNull
    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    String address;
    int age;
}
