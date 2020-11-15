package com.yifan.funwithwatermark.module.main;

import android.os.Bundle;
import android.widget.EditText;

import com.yifan.funwithwatermark.R;
import com.yifan.funwithwatermark.base.BaseActivity;
import com.yifan.funwithwatermark.utils.UrlUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity<MainPresenter, IMainView> implements IMainView {

    @BindView(R.id.ed_video_link)
    EditText mEdVideoLink;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        //url校验
        urlCheck();
    }

    /**
     * 链接校验
     */
    private void urlCheck() {
        String webUrl = mEdVideoLink.getText().toString();
        if (UrlUtil.urlCheck(webUrl)) {
            //是链接

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