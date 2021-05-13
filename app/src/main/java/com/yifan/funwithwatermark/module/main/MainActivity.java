package com.yifan.funwithwatermark.module.main;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.location.GpsStatus;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.os.SystemClock;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.yifan.funwithwatermark.R;
import com.yifan.funwithwatermark.base.BaseActivity;
import com.yifan.funwithwatermark.utils.ClipboardUtil;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity<MainPresenter, IMainView> implements IMainView {

    @BindView(R.id.ed_video_link)
    EditText mEdVideoLink;
    @BindView(R.id.btn_del_video)
    Button mBtnDelVideo;
    @BindView(R.id.tv_model)
    TextView mTvModel;
    @BindView(R.id.lat)
    TextView mTvLat;
    @BindView(R.id.Lng)
    TextView mTvLngl;
    @BindView(R.id.old_lat)
    TextView mTvOldLat;
    @BindView(R.id.old_Lng)
    TextView mTvOldLngl;
    @BindView(R.id.cnt)
    TextView mTvCnt;

    private long cnt;

    private double old_lat;
    private double old_lng;

    private static final String TAG = "MainActivity";

    private static final String ACTION_UPLOAD_USE_APP = "action upload use app data1";
    private static final String ACTION_UPLOAD_POWER_DATA = "action upload power data1";
    private static final int UPLOAD_REPEAT_TIME = 20000;

    private String mWebUrl;
    private String mPower = "70%";

    private MyConn conn;
    private TestService.MyBinder myBinder;//我定义的中间人对象

    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (ACTION_UPLOAD_USE_APP.equals(intent.getAction())) {
                Log.d(TAG, "" + context.getClass().getName());
                setAlarm(context, ACTION_UPLOAD_USE_APP);
            } else if (ACTION_UPLOAD_POWER_DATA.equals(intent.getAction())) {
            }
        }
    };

    static ScheduledExecutorService executorService = new ScheduledThreadPoolExecutor(3, new ThreadFactory() {
        @Override
        public Thread newThread(Runnable r) {
            Thread thread = new Thread(r);
            thread.setDaemon(true);
            thread.setName("scheduled.demo.thread");
            return thread;
        }
    });

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        setAlarm(this, ACTION_UPLOAD_USE_APP);
//        if (record()) {
//            mTvModel.setText(" power:" + null);
//        }

        initService();
        other();

    }

    private void other() {
        mBtnDelVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //链接校验
                mWebUrl = mEdVideoLink.getText().toString().trim();
                if (mWebUrl != null && mPresenter != null) {
                    mWebUrl = mPresenter.transformUrl(mWebUrl);
                    mPresenter.urlCheck(mWebUrl);
                }
            }
        });
        Runnable runnable = () -> {
            String now = "" + System.currentTimeMillis();
            System.out.println(Thread.currentThread() + " execute time = " + now);
        };

        executorService.scheduleWithFixedDelay(
                runnable, 5, 1, TimeUnit.SECONDS);
    }

    private void initService() {
        //连接服务
        conn = new MyConn();
        bindService(new Intent(this, TestService.class), conn, BIND_AUTO_CREATE);
    }

    private void setAlarm(Context context, String action) {
        //registerReceiver
        IntentFilter filter = new IntentFilter();
        filter.addAction(action);
        context.registerReceiver(mReceiver, filter);

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(action);
        PendingIntent p = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime() + UPLOAD_REPEAT_TIME, UPLOAD_REPEAT_TIME, p);
    }

    private boolean record() {
        try {
            int p = Integer.parseInt(mPower);
            mTvModel.setText(" power:" + p);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        //剪切板检测
        clipboardDetection();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //当activity 销毁的时候 解绑服务
        unbindService(conn);
    }

    /**
     * 剪切板检测
     *
     * @return
     */
    private void clipboardDetection() {
        String clipboardContent = ClipboardUtil.getClipboardContent(this);
        if (clipboardContent != null) {
            mPresenter.urlCheck(clipboardContent);
        }
    }

    @Override
    public void doadloadSuccess() {
        Toast.makeText(this, getString(R.string.download_video_success), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void isVideoLink(boolean isVideoLink) {
        //delete watermark
        mPresenter.delWaterMark(mWebUrl);
    }

    //监视服务的状态
    private class MyConn implements ServiceConnection {

        //当服务连接成功调用
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            //获取中间人对象
            myBinder = (TestService.MyBinder) service;
            myBinder.registerListenr(new TestService.Iservice() {
                @Override
                public void onLocationChanged(double Lat, double Lng) {
                    cnt += 1;
                    mTvCnt.setText("位置更新：" + cnt + "次");
                    mTvLat.setText("经度：" + Lat);
                    mTvLngl.setText("纬度：" + Lng);
                    mTvOldLat.setText("上一次经度：" + old_lat);
                    mTvOldLngl.setText("上一次纬度：" + old_lng);
                    old_lat = Lat;
                    old_lng = Lng;
                }
            });
        }

        //失去连接
        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected Class<IMainView> getViewClass() {
        return IMainView.class;
    }

    @Override
    protected Class<MainPresenter> getPresenterClass() {
        return MainPresenter.class;
    }

}