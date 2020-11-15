package com.yifan.funwithwatermark.module.main.api;

import com.yifan.funwithwatermark.module.main.bean.TikTokBean;

import java.util.HashMap;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

/**
 * @Author sun
 * @Date 2020-11-16
 * Description:
 */
public interface MainService {

    @GET("douyin/api.php")
    Observable<TikTokBean> getNoWaterMarkVideo(@QueryMap HashMap<String, String> map);

}
