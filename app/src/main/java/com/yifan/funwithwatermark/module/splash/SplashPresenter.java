package com.yifan.funwithwatermark.module.splash;


import com.yifan.funwithwatermark.base.IPresenter;

class SplashPresenter implements IPresenter {

    private ISplashView mView;

    public SplashPresenter(ISplashView view) {
        mView = view;
        mView.startToMainNow();
    }
}
