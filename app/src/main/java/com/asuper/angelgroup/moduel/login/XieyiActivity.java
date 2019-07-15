package com.asuper.angelgroup.moduel.login;

import android.webkit.WebView;
import android.widget.TextView;

import com.asuper.angelgroup.BaseActivity;
import com.asuper.angelgroup.R;
import com.asuper.angelgroup.common.set.AppUrl;

/**
 * 协议
 */
public class XieyiActivity extends BaseActivity {

    private WebView wv_xieyi;

    @Override
    public void setLayout() {
        setContentView(R.layout.activity_xieyi);
    }

    @Override
    public void initView() {
        setPagTitle("用户协议");
        wv_xieyi = (WebView) findViewById(R.id.wv_xieyi);
    }

    @Override
    public void initData() {
        String url = AppUrl.host + "/getRechargeAgreement.do?type=2" ;
        wv_xieyi.loadUrl(url);
    }

    @Override
    public void initListener() {

    }

}
