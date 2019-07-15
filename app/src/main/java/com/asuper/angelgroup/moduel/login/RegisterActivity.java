package com.asuper.angelgroup.moduel.login;


import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.asuper.angelgroup.BaseActivity;
import com.asuper.angelgroup.R;
import com.asuper.angelgroup.common.set.AppUrl;
import com.asuper.angelgroup.common.set.GlobalParam;
import com.asuper.angelgroup.common.set.ParamBuild;
import com.asuper.angelgroup.common.tool.RegexValidator;
import com.asuper.angelgroup.net.base.Request;
import com.asuper.angelgroup.net.request.interfa.BaseRequestListener;
import com.asuper.angelgroup.net.request.interfa.JsonRequestListener;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * 注册页
 * Created by shubei on 2017/8/2.
 */

public class RegisterActivity extends BaseActivity {

    private View area_goto_login;
    private Button btn_register;
    private EditText edit_invite_code, edit_phone, edit_pwd, edit_pwd_confirm;

    @Override
    public void setLayout() {
        setContentView(R.layout.activity_register);
    }

    @Override
    public void initView() {
        area_goto_login = findViewById(R.id.area_goto_login);
        btn_register = findViewById(R.id.btn_register);
        edit_invite_code = findViewById(R.id.edit_invite_code);
        edit_phone = findViewById(R.id.edit_phone);
        edit_pwd = findViewById(R.id.edit_pwd);
        edit_pwd_confirm = findViewById(R.id.edit_pwd_confirm);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {
        area_goto_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(edit_invite_code.getText().toString().trim())) {
                    showShortToast("请输入邀请码");
                    return;
                }
                if (TextUtils.isEmpty(edit_phone.getText().toString().trim())) {
                    showShortToast("请输入手机号");
                    return;
                }
                if (!RegexValidator.isMobile(edit_phone.getText().toString().trim())) {
                    showShortToast("手机号输入有误！");
                    return;
                }
                if (TextUtils.isEmpty(edit_pwd.getText().toString().trim())) {
                    showShortToast("请输入密码");
                    return;
                }
                if (!RegexValidator.isPassword(edit_pwd.getText().toString().trim())) {
                    showShortToast("密码不符合规则！请设置6到16位，由数字和字母组成密码！");
                    return;
                }
                if (TextUtils.isEmpty(edit_pwd_confirm.getText().toString().trim())) {
                    showShortToast("请确认密码");
                    return;
                }
                if (!edit_pwd.getText().toString().trim().equals(edit_pwd_confirm.getText().toString().trim())) {
                    showShortToast("两次输入的密码不一致");
                    return;
                }
                asyncRegister();

            }
        });
    }

    /**
     * 注册
     */
    private void asyncRegister() {
        String wholeUrl = AppUrl.host + AppUrl.register;
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("mobile", edit_phone.getText().toString().trim());
        map.put("password", edit_pwd.getText().toString().trim());
        map.put("password2", edit_pwd_confirm.getText().toString().trim());
        map.put("recommendsCode", edit_invite_code.getText().toString().trim());
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
            startActivity(new Intent(mContext, FillInfoRoleActivity.class));
            finish();
        }
    };
}
