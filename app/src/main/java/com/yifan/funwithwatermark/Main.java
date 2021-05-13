package com.yifan.funwithwatermark;

import com.yifan.funwithwatermark.RxjavaTest.User;
import com.yifan.funwithwatermark.RxjavaTest.WeChatServer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Author: ZhengXiang Sun
 * @Data:
 */
public class Main {

    public int maxLength(int[] arr) {
        HashMap<Integer, Integer> map = new HashMap<>();
        int max = 1;
        for (int start = 0, end = 0; end < arr.length; end++) {
            if (map.containsKey(arr[end])) {
                //重复了
                start = Math.max(start, map.get(arr[end]) + 1);
                //注意：这里一定要取最大的start，不然就错误了
                //为什么？ 因为重复数字的索引很可能比start小
            }
            max = Math.max(max, end - start + 1);
            map.put(arr[end], end);
        }
        return max;
    }

    public static void main(String[] args) {
    }
}
