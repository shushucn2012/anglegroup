package com.asuper.angelgroup.moduel.login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.asuper.angelgroup.BaseActivity;
import com.asuper.angelgroup.MainTabActivity;
import com.asuper.angelgroup.R;
import com.asuper.angelgroup.common.set.AppUrl;
import com.asuper.angelgroup.common.set.GlobalParam;
import com.asuper.angelgroup.common.set.ParamBuild;
import com.asuper.angelgroup.common.tool.ComUtils;
import com.asuper.angelgroup.common.tool.DateTool;
import com.asuper.angelgroup.common.tool.ExitAppUtils;
import com.asuper.angelgroup.net.base.Request;
import com.asuper.angelgroup.net.request.interfa.BaseRequestListener;
import com.asuper.angelgroup.net.request.interfa.JsonRequestListener;
import com.asuper.angelgroup.widget.videorecorder.CommonUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 登录主页
 * Created by shubei on 2017/6/24.
 */

public class LoginActivity extends BaseActivity {

    public static final String FILE_NAME = "USER_INFO";

    private View area_goto_register;
    private Button btn_login;
    private EditText edit_phone, edit_pwd;

    @Override
    public void setLayout() {
        setContentView(R.layout.activity_login);
    }

    @Override
    public void initView() {
        area_goto_register = findViewById(R.id.area_goto_register);
        btn_login = findViewById(R.id.btn_login);
        edit_phone = findViewById(R.id.edit_phone);
        edit_pwd = findViewById(R.id.edit_pwd);
    }

    @Override
    public void initData() {
        SharedPreferences spf = getSharedPreferences(LoginActivity.FILE_NAME, Context.MODE_PRIVATE);
        String saveUserName = spf.getString("username", "");
        String savePwd = spf.getString("pwd", "");

        edit_phone.setText(saveUserName);
        edit_pwd.setText(savePwd);
    }

    @Override
    public void initListener() {
        area_goto_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(mContext, RegisterActivity.class));
            }
        });
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(edit_phone.getText().toString().trim())) {
                    showShortToast("请输入手机号");
                    return;
                }
                if (TextUtils.isEmpty(edit_pwd.getText().toString().trim())) {
                    showShortToast("请输入密码");
                    return;
                }
                asyncLogin();
                //startActivity(new Intent(mContext, MainTabActivity.class));
            }
        });
    }

    @Override
    public void onBackPressed() {
        ExitAppUtils.getInstance().exit();
    }

    /**
     * 登录
     */
    private void asyncLogin() {
        String wholeUrl = AppUrl.host + AppUrl.login;
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("mobile", edit_phone.getText().toString().trim());
        map.put("password", edit_pwd.getText().toString().trim());
        String requestBodyData = ParamBuild.buildParams(map);
        netRequest.startRequest(wholeUrl, Request.Method.POST, requestBodyData, 0, regLsner);
    }

    BaseRequestListener regLsner = new JsonRequestListener() {

        @Override
        public void onStart(int requestId) {
            showDialog();
        }

        @Override
        public void onError(int requestId, String errorCode, String errorMsg) {
            dismissDialog();
            showShortToast(errorMsg);
        }

        @Override
        public void onSuccess(int requestId, String url, JSONObject jsonResult) {
            dismissDialog();
            GlobalParam.userToken = jsonResult.optString("userToken");
            //asyncGetUserData();
            int type = 0;// 0:账号注册，未选择身份;1:已认领身份；2：已完善个人信息
            type = jsonResult.optInt("type");
            String labelCode = "001";
            labelCode = jsonResult.optString("labelCode");
            if (type == 0) {
                startActivity(new Intent(mContext, FillInfoRoleActivity.class));
            } else if (type == 1) {
                if (labelCode.equals("001")) {//父母家人
                    startActivity(new Intent(mContext, FillInfoMoreActivity.class));
                } else if (labelCode.equals("002")) {//社会关爱
                    startActivity(new Intent(mContext, FillInfoMoreOthersActivity.class));
                }
            } else if (type == 2) {
                startActivity(new Intent(mContext, MainTabActivity.class));
                ComUtils.saveCurrentUserName(mContext);
            }
            SharedPreferences spf = mContext.getSharedPreferences(LoginActivity.FILE_NAME, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = spf.edit();
            editor.putString("username", edit_phone.getText().toString().trim());
            editor.putString("pwd", edit_pwd.getText().toString().trim());
            editor.commit();
            finish();
        }
    };


}
