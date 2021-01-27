package com.yifan.funwithwatermark;

import androidx.annotation.NonNull;

/**
 * @Description:
 * @Author: ZhengXiang Sun
 * @Data:
 */
public class Student implements Cloneable {

    Teacher t;

    public Teacher getT() {
        return t;
    }

    public void setT(Teacher t) {
        this.t = t;
    }

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

    String name;
    int age;

    @NonNull
    @Override
    protected Object clone() throws CloneNotSupportedException {
        // 改为深复制：
        Student student = (Student) super.clone();
        // 本来是浅复制，现在将Teacher对象复制一份并重新set进来
        student.setT((Teacher) student.getT().clone());
        return student;
    }
}
