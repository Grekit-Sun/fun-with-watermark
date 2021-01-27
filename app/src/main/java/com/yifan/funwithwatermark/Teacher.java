package com.yifan.funwithwatermark;

import androidx.annotation.NonNull;

/**
 * @Description:
 * @Author: ZhengXiang Sun
 * @Data:
 */
public class Teacher implements Cloneable {

    String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    int age;

    @NonNull
    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
