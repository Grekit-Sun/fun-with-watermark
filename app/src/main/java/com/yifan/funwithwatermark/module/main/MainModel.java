package com.yifan.funwithwatermark.module.main;

import com.yifan.funwithwatermark.helper.RxComposerHelper;
import com.yifan.funwithwatermark.manager.RetrofitManager;
import com.yifan.funwithwatermark.module.main.api.MainService;
import com.yifan.funwithwatermark.module.main.bean.TikTokBean;
import com.yifan.funwithwatermark.utils.SimpleHttpSubscriber;

import java.util.HashMap;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

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

    /**
     * 获取无水印video
     * @param map
     * @param subscriber
     */
    void getNoWaterMarkVideo(HashMap<String, String> map, SimpleHttpSubscriber<TikTokBean> subscriber){
        mMainService.getNoWaterMarkVideo(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }
}
