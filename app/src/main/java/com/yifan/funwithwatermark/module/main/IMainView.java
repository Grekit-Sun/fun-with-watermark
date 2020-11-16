package com.yifan.funwithwatermark.module.main;

/**
 * @Author sun
 * @Date 2020-11-16
 * Description:
 */
interface IMainView {

    /**
     * 下载视频成功
     */
    void doadloadSuccess();

    /**
     * 是否为链接
     * @param isVideoLink
     */
    void isVideoLink (boolean isVideoLink);
}
