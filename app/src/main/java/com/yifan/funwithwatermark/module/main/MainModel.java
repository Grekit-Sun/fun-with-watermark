package com.yifan.funwithwatermark.module.main;

import com.yifan.funwithwatermark.manager.RetrofitManager;
import com.yifan.funwithwatermark.module.main.api.MainService;
import com.yifan.funwithwatermark.module.main.bean.TikTokBean;
import com.yifan.funwithwatermark.utils.SimpleHttpSubscriber;

import java.util.HashMap;

/**
 * @Author sun
 * @Date 2020-11-16
 * Description:
 */
class MainModel {

    private MainService mMainService;

    MainModel(){
        mMainService =  RetrofitManager.getInstance().getDefaultRetrofit().create(MainService.class);
    }

    void getNoWaterMarkVideo(HashMap<String, String> map, SimpleHttpSubscriber<TikTokBean> subscriber){
        mMainService.getNoWaterMarkVideo(map)
                .compose()
    }
}
