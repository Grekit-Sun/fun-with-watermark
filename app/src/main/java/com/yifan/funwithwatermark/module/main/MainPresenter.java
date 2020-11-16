package com.yifan.funwithwatermark.module.main;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;

import com.yifan.funwithwatermark.base.IPresenter;
import com.yifan.funwithwatermark.helper.AsyncOperateHelper;
import com.yifan.funwithwatermark.module.main.bean.TikTokBean;
import com.yifan.funwithwatermark.utils.SimpleHttpSubscriber;
import com.yifan.funwithwatermark.utils.UrlUtil;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.disposables.Disposable;

import static com.yifan.funwithwatermark.MyApplication.appCtx;

/**
 * @Author sun
 * @Date 2020-11-16
 * Description:
 */
public class MainPresenter implements IPresenter {

    private IMainView mView;
    private MainModel mModel;
    private DownloadManager mDownloadManager;
    private long mReference;

    public MainPresenter(IMainView view) {
        mView = view;
        mModel = new MainModel();
    }

    /**
     * delete water mark
     */
    void delWaterMark(String url) {
        HashMap<String, String> map = new HashMap<>();
        map.put("url", url);
        mModel.getNoWaterMarkVideo(map, new SimpleHttpSubscriber<TikTokBean>() {
            @Override
            public void onResponse(TikTokBean tikTokBean) {
                downloadVideo(tikTokBean.video_url);
            }
        });
    }

    /**
     * 链接矫正
     * @param webUrl
     */
    void urlCheck(String webUrl){

        new AsyncOperateHelper<Boolean>() {
            @Override
            protected Boolean doInBackground() {
                //转化以http为头

                return UrlUtil.urlCheck(webUrl);
            }

            @Override
            protected void dealResult(Boolean result) {
                mView.isVideoLink(result);
            }

            @Override
            protected void postException(Throwable t) {

            }

            @Override
            protected void onComplete() {

            }

            @Override
            protected void acceptDisposable(Disposable disposable) {

            }
        };
    }

    /**
     * download video without watermark
     */
    private void downloadVideo(String url) {
        new AsyncOperateHelper<TikTokBean>() {
            @Override
            protected TikTokBean doInBackground() {
                //下载任务
                String serviceString = Context.DOWNLOAD_SERVICE;
                //直接使用系统的下载管理器。
                mDownloadManager = (DownloadManager) appCtx.getSystemService(serviceString);
                //可以是视频也可以是图片，分享时要填写正确的type类型，在下面我会列出各种类型
                Uri uri = Uri.parse(url);
                DownloadManager.Request request = new DownloadManager.Request(uri);
                //通知栏的标题
                request.setTitle("视频下载");
                //显示通知栏的说明
                request.setDescription("测试的广告");
//                request.setShowRunningNotification(false);//不显示通知栏（若不显示就不需要写上面的内容）
//                request.setVisibleInDownloadsUi(true ) ;
                //下载到那个文件夹下，以及命名
                request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,
                        "/tiktok" + System.currentTimeMillis() + ".mp4");
                //下载的唯一标识，可以用这个标识来控制这个下载的任务enqueue（）开始执行这个任务
                mReference = mDownloadManager.enqueue(request);
                return null;
            }

            @Override
            protected void dealResult(TikTokBean result) {

            }

            @Override
            protected void postException(Throwable t) {

            }

            @Override
            protected void onComplete() {

            }

            @Override
            protected void acceptDisposable(Disposable disposable) {

            }
        };
    }

    /**
     * 转化为url格式
     * @param str
     * @return
     */
    private String transformUrl(String str){
        char[] chars = str.toCharArray();
    }
    /**
     * 下载监听
     */
    class CompleteReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            //下载完成之后监听
            String action = intent.getAction();
            //下载完成的监听
            if (action.equals(DownloadManager.ACTION_DOWNLOAD_COMPLETE)) {
                mView.doadloadSuccess();
            }
            //点击通知栏，取消下载任务
            if (action.equals(DownloadManager.ACTION_NOTIFICATION_CLICKED)) {
                mDownloadManager.remove((Long) mReference);
            }
        }
    }
}
