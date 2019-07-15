package com.asuper.angelgroup.moduel.me;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.asuper.angelgroup.BaseActivity;
import com.asuper.angelgroup.R;
import com.asuper.angelgroup.common.set.GlobalParam;
import com.asuper.angelgroup.common.tool.ComUtils;
import com.asuper.angelgroup.common.tool.DataCleanManager;
import com.asuper.angelgroup.common.tool.ExitAppUtils;
import com.asuper.angelgroup.moduel.home.HomeActivity;
import com.asuper.angelgroup.moduel.login.LoginActivity;
import com.asuper.angelgroup.moduel.login.XieyiActivity;
import com.asuper.angelgroup.moduel.login.bean.UserManager;
import com.asuper.angelgroup.widget.videorecorder.CommonUtils;

/**
 * 设置
 * Created by shubei on 2017/11/18.
 */

public class SettingActivity extends BaseActivity {

    private View area_clear_cache, area_xieyi;
    private Button btn_logout;
    private TextView tv_clear_cache;

    @Override
    public void setLayout() {
        setContentView(R.layout.activity_setting);
    }

    @Override
    public void initView() {
        setPagTitle("设置");
        area_clear_cache = findViewById(R.id.area_clear_cache);
        btn_logout = (Button) findViewById(R.id.btn_logout);
        tv_clear_cache = (TextView) findViewById(R.id.tv_clear_cache);
        area_xieyi = findViewById(R.id.area_xieyi);
    }

    @Override
    public void initData() {
        try {
            tv_clear_cache.setText(DataCleanManager.getTotalCacheSize(mContext));
        } catch (Exception e) {
            e.printStackTrace();
            tv_clear_cache.setText("15.8M");
        }
    }

    @Override
    public void initListener() {
        area_clear_cache.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataCleanManager.clearAllCache(mContext);
                tv_clear_cache.setText("0K");
                showShortToast("缓存清理成功！");
            }
        });
        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLoginOutDialog();
            }
        });
        area_xieyi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(mContext, XieyiActivity.class));
            }
        });
    }

    public void showLoginOutDialog() {
        dDialog.showDialog("提示", "确定要退出吗？", "取消", "确定", new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dDialog.dismissDialog();

            }
        }, new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dDialog.dismissDialog();
                ComUtils.deleteCurrentUserName(mContext);
                GlobalParam.currentUser = null;
                GlobalParam.userToken = null;
                HomeActivity.needFresh = true;
                for (Activity act : ExitAppUtils.getInstance().getActList()) {
                    act.finish();
                }
                startActivity(new Intent(mContext, LoginActivity.class));
            }
        });
    }
}
