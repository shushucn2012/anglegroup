package com.asuper.angelgroup.moduel.me;

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.asuper.angelgroup.BaseActivity;
import com.asuper.angelgroup.R;
import com.asuper.angelgroup.common.set.AppUrl;
import com.asuper.angelgroup.common.set.ParamBuild;
import com.asuper.angelgroup.common.tool.ImageManager;
import com.asuper.angelgroup.moduel.home.HomeActivity;
import com.asuper.angelgroup.net.base.Request;
import com.asuper.angelgroup.net.request.interfa.BaseRequestListener;
import com.asuper.angelgroup.net.request.interfa.JsonRequestListener;
import com.bigkoo.pickerview.OptionsPickerView;
import com.github.promeg.pinyinhelper.Pinyin;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 转发页面
 * Created by shubei on 2017/12/5.
 */

public class TurnOutActivity extends BaseActivity {

    private String userPic, userName, summray, picUrls, mediaId;
    private int itemId;

    private TextView tv_right, tv_open_level;
    private EditText edit_info;
    private ImageView img_user_head;
    private TextView tv_user_name, tv_content;

    @Override
    public void setLayout() {
        setContentView(R.layout.activity_topic_turnout);
    }

    @Override
    public void initView() {
        img_user_head = findViewById(R.id.img_user_head);
        tv_user_name = findViewById(R.id.tv_user_name);
        tv_content = findViewById(R.id.tv_content);

        setPagTitle("转发");
        tv_right = findViewById(R.id.tv_right);
        tv_right.setText("发布");
        tv_right.setTextColor(mContext.getResources().getColor(R.color.main_green));
        tv_open_level = findViewById(R.id.tv_open_level);
        edit_info = findViewById(R.id.edit_info);
    }

    @Override
    public void initData() {
        userPic = getIntent().getStringExtra("userPic");
        userName = getIntent().getStringExtra("userName");
        summray = getIntent().getStringExtra("summray");
        itemId = getIntent().getIntExtra("itemId", 0);
        picUrls = getIntent().getStringExtra("picUrls");
        mediaId = getIntent().getStringExtra("mediaId");

        ImageManager.getInstance().displayImg(img_user_head, userPic);
        tv_user_name.setText("@" + userName);
        tv_content.setText(summray);
    }

    @Override
    public void initListener() {
        findViewById(R.id.area_open_level).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final List<String> optionsItems = new ArrayList<String>();
                optionsItems.add("公开");
                optionsItems.add("好友圈");
                optionsItems.add("日记");
                OptionsPickerView pvOptions = new OptionsPickerView.Builder(mContext, new OptionsPickerView.OnOptionsSelectListener() {
                    @Override
                    public void onOptionsSelect(int options1, int option2, int options3, View v) {
                        tv_open_level.setText(optionsItems.get(options1));
                    }
                }).setSubmitColor(mContext.getResources().getColor(R.color.main_green))//确定按钮文字颜色
                        .setCancelColor(mContext.getResources().getColor(R.color.g333333))//取消按钮文字颜色
                        .build();
                pvOptions.setPicker(optionsItems);
                pvOptions.show();
            }
        });
        area_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(edit_info.getText().toString().trim())) {
                    showShortToast("请输入文字");
                    return;
                }
                String wordsWhole = edit_info.getText().toString().trim();
                int wordNum = 0;
                for (char c : wordsWhole.toCharArray()) {
                    if (Pinyin.isChinese(c)) {
                        wordNum = wordNum + 2;
                    } else {
                        wordNum = wordNum + 1;
                    }
                }
                if (wordNum < 14) {
                    showShortToast("最少输入14字符（7个汉字）");
                    return;
                }
                asyncReleaseBlog();
            }
        });
    }

    /**
     * 请求个人信息
     */
    private void asyncReleaseBlog() {
        String wholeUrl = AppUrl.host + AppUrl.retransmissionBlog;
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("summary", edit_info.getText().toString().trim());
        if (!TextUtils.isEmpty(mediaId)) {//视频
            map.put("mediaId", mediaId);
            wholeUrl = AppUrl.host + AppUrl.retransmissionVideo;
        } else {//图文
            if (!TextUtils.isEmpty(picUrls)) {
                map.put("imgUrl", picUrls);
            }
        }
        if (tv_open_level.getText().toString().equals("公开")) {
            map.put("authority", "1");
        } else if (tv_open_level.getText().toString().equals("好友圈")) {
            map.put("authority", "2");
        } else {
            map.put("authority", "3");
        }
        map.put("retransmissionId", itemId);
        String requestBodyData = ParamBuild.buildParams(map);
        netRequest.startRequest(wholeUrl, Request.Method.POST, requestBodyData, 0, getLsner);
    }

    BaseRequestListener getLsner = new JsonRequestListener() {

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
            showShortToast("发布成功!");
            HomeActivity.needFresh = true;
            finish();
        }
    };
}
