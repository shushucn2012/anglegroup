package com.asuper.angelgroup;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.FragmentActivity;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import com.asuper.angelgroup.R;
import com.asuper.angelgroup.common.set.Log;
import com.asuper.angelgroup.common.tool.ExitAppUtils;
import com.asuper.angelgroup.common.tool.ToastUtil;
import com.asuper.angelgroup.net.request.StringNetRequest;
import com.asuper.angelgroup.widget.dialog.CommonProgressDialog;
import com.asuper.angelgroup.widget.dialog.DoubleDialog;
import com.nostra13.universalimageloader.core.DisplayImageOptions;

/**
 * 基础activity类
 *
 * @author super
 */
public abstract class BaseActivity extends FragmentActivity {
    public Context mContext;
    /**
     * log打印需要用到的标签
     */
    public String Tag = "BaseActivity";
    public String actTag;// 网络请求标记，用于标识请求以便取消
    /**
     * 传入参数为字符串拼接方式的网络请求实例
     */
    public StringNetRequest netRequest;
    public CommonProgressDialog commonProgressDialog;
    public DoubleDialog dDialog;

    public View area_left, area_right;
    public TextView page_title;
    public DisplayImageOptions options;

    public String shareUrl = "", sharePic = "", shareTitle = "", shareDescription = "";

    public OnClickListener rOnClickListener;

    @Override
    public Resources getResources() {
        Resources res = super.getResources();
        Configuration config = new Configuration();
        config.setToDefaults();
        res.updateConfiguration(config, res.getDisplayMetrics());
        return res;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setLayout();
        ExitAppUtils.getInstance().addActivity(this);
        Tag = this.getClass().getSimpleName();
        logout("Tag------onCreate-----" + Tag);
        actTag = Tag + System.currentTimeMillis();
        netRequest = StringNetRequest.getInstance(mContext);
        netRequest.setActTag(actTag);
        commonProgressDialog = new CommonProgressDialog(mContext);
        // 进度框不可取消
        setCancelable(false);
        dDialog = new DoubleDialog(mContext);
        options = new DisplayImageOptions.Builder()
                .showImageForEmptyUri(R.mipmap.default_pic)
                .showImageOnLoading(R.mipmap.default_pic)
                .showImageOnFail(R.mipmap.default_pic)
                .cacheInMemory(true).cacheOnDisk(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .delayBeforeLoading(100)
                .build();
        initView();
        initData();
        initTopBar();
        initListener();
    }

    private void initTopBar() {
        area_left = findViewById(R.id.area_left);
        area_right = findViewById(R.id.area_right);
        if (area_left != null) {
            area_left.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            });
        }
        if (area_right != null) {
            area_right.setOnClickListener(rOnClickListener);
        }
    }

    public void setROnClickListener(OnClickListener lsner) {
        rOnClickListener = lsner;
    }

    @Override
    protected void onResume() {
        super.onResume();
        netRequest.setActTag(actTag);
    }

    @Override
    protected void onPause() {
        super.onPause();
        logout("Tag------onPause-----" + Tag);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        netRequest.setActTag(actTag);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        netRequest.cancelAllRequest(actTag);
        logout("---onDestroy---netRequest---cancelAllRequest---" + actTag);
        ExitAppUtils.getInstance().delActivity(this);
    }

    /**
     * 设置布局
     */
    public abstract void setLayout();

    /**
     * 初始化视图
     */
    public abstract void initView();

    /**
     * 初始化数据
     */
    public abstract void initData();

    /**
     * 初始化监听事件
     */
    public abstract void initListener();

    /**
     * 设置页面标题
     */
    public void setPagTitle(String str) {
        page_title = (TextView) findViewById(R.id.page_title);
        page_title.setText(str);
    }

    /**
     * 打印日志
     */
    public void logout(String msg) {
        Log.out(msg);
    }


    /**
     * toast短提示
     */
    public void showShortToast(String text) {
        Toast.makeText(mContext, text, Toast.LENGTH_SHORT).show();
    }

    public void showDialog() {
        try {
            if (commonProgressDialog.isShow()) {
                return;
            } else {
                commonProgressDialog.showDialog(null);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showDialog(String msg) {
        try {
            if (commonProgressDialog.isShow()) {
                commonProgressDialog.setMessage(msg);
            } else {
                commonProgressDialog.showDialog(msg);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setCancelable(boolean is) {
        commonProgressDialog.setCancelable(is);
    }

    public void dismissDialog() {
        try {
            if (commonProgressDialog.isShow()) {
                commonProgressDialog.dialogDismiss();
            } else {
                return;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isDialogShowing() {
        return commonProgressDialog.isShow();
    }

    public void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
        }
    }

}
