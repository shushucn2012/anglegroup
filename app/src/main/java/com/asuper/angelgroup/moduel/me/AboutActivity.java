package com.asuper.angelgroup.moduel.me;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.TextView;

import com.asuper.angelgroup.BaseActivity;
import com.asuper.angelgroup.R;
import com.asuper.angelgroup.common.set.GlobalParam;

/**
 * 关于
 * Created by shubei on 2017/11/18.
 */

public class AboutActivity extends BaseActivity{

    private TextView tv_version, tv_company, tv_company_en, tv_service_desk_phone, tv_giftcenter_phone;

    @Override
    public void setLayout() {
        setContentView(R.layout.activity_aboutme);
    }

    @Override
    public void initView() {
        setPagTitle("关于我们");
        tv_version = (TextView) findViewById(R.id.tv_version);
        tv_company = (TextView) findViewById(R.id.tv_company);
    }

    @Override
    public void initData() {
    }

    @Override
    public void initListener() {
    }
}
