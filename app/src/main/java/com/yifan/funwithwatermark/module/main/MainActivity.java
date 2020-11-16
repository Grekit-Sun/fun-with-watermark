package com.yifan.funwithwatermark.module.main;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.yifan.funwithwatermark.R;
import com.yifan.funwithwatermark.base.BaseActivity;
import com.yifan.funwithwatermark.utils.ClipboardUtil;
import com.yifan.funwithwatermark.utils.UrlUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity<MainPresenter, IMainView> implements IMainView {

    @BindView(R.id.ed_video_link)
    EditText mEdVideoLink;
    @BindView(R.id.btn_del_video)
    Button mBtnDelVideo;

    private String mWebUrl;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        mBtnDelVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //链接校验
                mWebUrl = mEdVideoLink.getText().toString().trim();
                if (mWebUrl != null && mPresenter != null) {
                    mPresenter.urlCheck(mWebUrl);
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        //剪切板检测
        clipboardDetection();
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