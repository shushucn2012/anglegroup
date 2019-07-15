package com.asuper.angelgroup.moduel.book;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.asuper.angelgroup.BaseActivity;
import com.asuper.angelgroup.R;
import com.asuper.angelgroup.common.set.AppUrl;
import com.asuper.angelgroup.common.set.ParamBuild;
import com.asuper.angelgroup.net.base.Request;
import com.asuper.angelgroup.net.request.interfa.BaseRequestListener;
import com.asuper.angelgroup.net.request.interfa.JsonRequestListener;
import com.github.promeg.pinyinhelper.Pinyin;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * 发送好友请求页面
 * Created by shubei on 2017/11/19.
 */

public class SendAddReqActivity extends BaseActivity {

    EditText edit_info;
    private int friendId;

    @Override
    public void setLayout() {
        setContentView(R.layout.activity_send_add_req);
    }

    @Override
    public void initView() {
        setPagTitle("好友申请");
        ((TextView) findViewById(R.id.tv_right)).setText("发送");
        edit_info = findViewById(R.id.edit_info);
    }

    @Override
    public void initData() {
        friendId = getIntent().getIntExtra("friendId", 0);
    }

    @Override
    public void initListener() {
        area_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                asyncSendAddFriendApply();
            }
        });
        edit_info.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int wordNum = 0;
                for (char c : s.toString().toCharArray()) {
                    if (Pinyin.isChinese(c)) {
                        wordNum = wordNum + 2;
                    } else {
                        wordNum = wordNum + 1;
                    }
                }
                if (wordNum > 50) {
                    edit_info.setText(s.subSequence(0, s.length() - 1));
                    //edit_info.setSelection(edit_info.length());
                    edit_info.setSelection(edit_info.getText().length());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    /**
     * 发送添加好友请求
     */
    private void asyncSendAddFriendApply() {
        String wholeUrl = AppUrl.host + AppUrl.sendAddFriendApply;
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("friendId", friendId);
        map.put("remark", edit_info.getText().toString().trim());
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
            showShortToast("发送成功！");
            finish();
        }
    };
}
