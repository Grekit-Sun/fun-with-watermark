package com.yifan.funwithwatermark.module.splash;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.TextView;


import androidx.core.app.ActivityCompat;

import com.yanzhenjie.permission.Action;
import com.yifan.funwithwatermark.module.main.MainActivity;
import com.yifan.funwithwatermark.R;
import com.yifan.funwithwatermark.base.BaseActivity;
import com.yifan.funwithwatermark.constants.SplashConstants;
import com.yifan.funwithwatermark.helper.PermissionHelper;


import butterknife.BindView;
import butterknife.ButterKnife;


public class SplashActivity extends BaseActivity<SplashPresenter, ISplashView> implements ISplashView {

    @BindView(R.id.appname_txt)
    TextView mAppNameTxt;
    @BindView(R.id.slogan_txt)
    TextView mSloganTxt;
    @BindView(R.id.bottom_appname_txt)
    TextView mBottomAppNameTxt;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        initView();     //初始化控件
    }

    protected void initView() {
        //设置自定义字体
        Typeface typeface = Typeface.createFromAsset(getAssets(), SplashConstants.FONTS);
        mAppNameTxt.setTypeface(typeface);
        mSloganTxt.setTypeface(typeface);
        mBottomAppNameTxt.setTypeface(typeface);
    }

    @Override
    public void startToMainNow() {
        initPremission();
    }


    /**
     * 初始化权限
     */
    private void initPremission() {
        PermissionHelper.requestMultiPermission(this, new Action() {
                    @Override
                    public void onAction(Object data) {
                        delyToMain();
                    }
                }, new Action() {
                    @Override
                    public void onAction(Object data) {
                        delyToMain();
                    }
                }, Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA);
    }

    /**
     * 延时跳转
     */
    private void delyToMain() {
        handleEventDelay(new Runnable() {
            @Override
            public void run() {
                skipToMainActivity();
            }
        }, FLAG_MAIN_DELAY);
    }

    /**
     * 跳至主界面
     */
    private void skipToMainActivity() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            //跳入主界面
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finishActivity();
        }
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_splash_layout;
    }


    @Override
    protected Class<ISplashView> getViewClass() {
        return ISplashView.class;
    }

    @Override
    protected Class<SplashPresenter> getPresenterClass() {
        return SplashPresenter.class;
    }

}
