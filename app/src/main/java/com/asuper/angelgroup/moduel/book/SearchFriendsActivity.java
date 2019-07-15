package com.asuper.angelgroup.moduel.book;

import android.content.Intent;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.asuper.angelgroup.BaseActivity;
import com.asuper.angelgroup.R;
import com.asuper.angelgroup.common.set.AppUrl;
import com.asuper.angelgroup.common.set.ParamBuild;
import com.asuper.angelgroup.common.tool.ComUtils;
import com.asuper.angelgroup.common.tool.ImageManager;
import com.asuper.angelgroup.moduel.book.adapter.SearchListAdapter;
import com.asuper.angelgroup.moduel.book.bean.Contactors;
import com.asuper.angelgroup.moduel.login.bean.UserBean;
import com.asuper.angelgroup.net.base.Request;
import com.asuper.angelgroup.net.request.interfa.BaseRequestListener;
import com.asuper.angelgroup.net.request.interfa.JsonRequestListener;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 搜索好友页面
 * Created by shubei on 2017/11/18.
 */

public class SearchFriendsActivity extends BaseActivity {

    private TextView tv_cancel;
    private EditText edit_sousuo;
    private ImageView img_userface;
    private TextView tv_username;
    private ListView lv_result;

    private List<UserBean> userBeanList = new ArrayList<>();
    private UserBean searchUser;
    private List<Contactors> cList = new ArrayList<>();
    private SearchListAdapter searchListAdapter;
    private String keyword;

    @Override
    public void setLayout() {
        setContentView(R.layout.activity_search_friends);
    }

    @Override
    public void initView() {
        tv_cancel = findViewById(R.id.tv_cancel);
        edit_sousuo = findViewById(R.id.edit_sousuo);
        img_userface = findViewById(R.id.img_userface);
        tv_username = findViewById(R.id.tv_username);
        lv_result = findViewById(R.id.lv_result);
    }

    @Override
    public void initData() {
        searchListAdapter = new SearchListAdapter(mContext, cList);
        lv_result.setAdapter(searchListAdapter);
    }

    @Override
    public void initListener() {
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        edit_sousuo.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    /*Intent it = new Intent(mContext, TopicSearchResultActivity.class);
                    String keyword = edit_sousuo.getText().toString().trim();
                    it.putExtra("keyword", keyword);
                    startActivity(it);*/
                    keyword = edit_sousuo.getText().toString().trim();
                    if (TextUtils.isEmpty(keyword)) {
                        showShortToast("请输入用户昵称");
                        return false;
                    }
                    asyncGetMyFirends();
                    //asyncSearchUserInfo();
                }
                return false;
            }
        });
        findViewById(R.id.area_user).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(mContext, OthersMainInfoActivity.class);
                it.putExtra("userId", searchUser.getId());
                it.putExtra("OTHER_NAME", searchUser.getPetName());
                startActivity(it);
            }
        });
        lv_result.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent it = new Intent(mContext, OthersMainInfoActivity.class);
                it.putExtra("userId", cList.get(position).getFriendUserId());
                it.putExtra("OTHER_NAME", cList.get(position).getPetName());
                startActivity(it);
            }
        });
    }

    /**
     * 我的好友列表
     */
    private void asyncGetMyFirends() {
        String wholeUrl = AppUrl.host + AppUrl.getMyFirends;
        netRequest.startRequest(wholeUrl, Request.Method.POST, "", 0, regLsner);
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
            JSONArray jay = jsonResult.optJSONArray("list");
            cList.clear();
            if (jay != null && jay.length() > 0) {
                for (int i = 0; i < jay.length(); i++) {
                    Contactors contactors = new Gson().fromJson(jay.optJSONObject(i).toString(), Contactors.class);
                    if (keyword.toUpperCase().contains(contactors.getPetName().toUpperCase())
                            || contactors.getPetName().toUpperCase().contains(keyword.toUpperCase())) {
                        cList.add(contactors);
                    }
                }
            }
            searchListAdapter.notifyDataSetChanged();
            if (ComUtils.isListEmpty(cList)) {
                findViewById(R.id.tv_nouser).setVisibility(View.VISIBLE);
            } else {
                findViewById(R.id.tv_nouser).setVisibility(View.GONE);
            }
        }
    };

    /**
     * 搜索好友
     */
    private void asyncSearchUserInfo() {
        String wholeUrl = AppUrl.host + AppUrl.searchUserInfo;
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("userIdCode", edit_sousuo.getText().toString().trim());
        String requestBodyData = ParamBuild.buildParams(map);
        netRequest.startRequest(wholeUrl, Request.Method.POST, requestBodyData, 0, sLsner);
    }

    BaseRequestListener sLsner = new JsonRequestListener() {

        @Override
        public void onStart(int requestId) {
            showDialog();
        }

        @Override
        public void onError(int requestId, String errorCode, String errorMsg) {
            dismissDialog();
            //showShortToast(errorMsg);
            findViewById(R.id.area_user).setVisibility(View.GONE);
            findViewById(R.id.tv_nouser).setVisibility(View.VISIBLE);
        }

        @Override
        public void onSuccess(int requestId, String url, JSONObject jsonResult) {
            dismissDialog();
            searchUser = new Gson().fromJson(jsonResult.toString(), UserBean.class);
            if (searchUser != null) {
                findViewById(R.id.area_user).setVisibility(View.VISIBLE);
                findViewById(R.id.tv_nouser).setVisibility(View.GONE);
                ImageManager.getInstance().displayImg(img_userface, searchUser.getPictureUrl());
                tv_username.setText(searchUser.getPetName());
            }
        }
    };
}
