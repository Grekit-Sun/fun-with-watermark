package com.yifan.funwithwatermark.module.main;

import com.yifan.funwithwatermark.base.IPresenter;

/**
 * @Author sun
 * @Date 2020-11-16
 * Description:
 */
class MainPresenter implements IPresenter {

    private IMainView mView;

    public MainPresenter(IMainView view) {
        mView = view;
    }
}
