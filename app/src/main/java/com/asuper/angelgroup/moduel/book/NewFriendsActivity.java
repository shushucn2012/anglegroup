package com.asuper.angelgroup.moduel.book;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.asuper.angelgroup.BaseActivity;
import com.asuper.angelgroup.R;
import com.asuper.angelgroup.common.set.AppUrl;
import com.asuper.angelgroup.common.set.ParamBuild;
import com.asuper.angelgroup.common.tool.FU;
import com.asuper.angelgroup.moduel.book.adapter.AddRequestListAdapter;
import com.asuper.angelgroup.moduel.book.bean.AddRequestBean;
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
 * 新朋友页面
 * Created by shubei on 2017/11/18.
 */

public class NewFriendsActivity extends BaseActivity {

    private List<AddRequestBean> mList;
    private AddRequestListAdapter adapter;

    private ListView lv_request;

    @Override
    public void setLayout() {
        setContentView(R.layout.activity_new_friends);
    }

    @Override
    public void initView() {
        setPagTitle("新的朋友");
        lv_request = (ListView) findViewById(R.id.lv_request);
    }

    @Override
    public void initData() {
        mList = new ArrayList<>();
        adapter = new AddRequestListAdapter(mContext, mList);
        lv_request.setAdapter(adapter);
        asyncGetFriendApply();
    }

    @Override
    public void initListener() {
        adapter.setOnAddClickedLsner(new AddRequestListAdapter.OnAddClickedLsner() {
            @Override
            public void onCliked(final int postion) {
                dDialog.showDialog("请确认添加“" + mList.get(postion).getPetName() + "”为好友", "好友有权看你好友圈，但无权看你日记", "再考虑一下", "确认添加", null, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dDialog.dismissDialog();
                        asyncAgreeAddFriendApply(mList.get(postion).getId());
                    }
                });
            }
        });
        lv_request.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent it = new Intent(mContext, OthersMainInfoActivity.class);
                it.putExtra("OTHER_NAME", mList.get(i).getPetName());
                it.putExtra("userId", mList.get(i).getFriendId());
                startActivity(it);
            }
        });
    }

    private void asyncGetFriendApply() {
        String wholeUrl = AppUrl.host + AppUrl.getSendFriendApplyRecords;
        /*Map<String, Object> map = new HashMap<String, Object>();
        map.put("friendId", friendId);
        map.put("remark", edit_info.getText().toString().trim());
        String requestBodyData = ParamBuild.buildParams(map);*/
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
            mList.clear();
            if (jay != null && jay.length() > 0) {
                for (int i = 0; i < jay.length(); i++) {
                    mList.add(new Gson().fromJson(jay.optJSONObject(i).toString(), AddRequestBean.class));
                }
            }
            adapter.notifyDataSetChanged();
        }
    };

    private void asyncAgreeAddFriendApply(String addFriendId) {
        String wholeUrl = AppUrl.host + AppUrl.agreeAddFriendApply;
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("addFriendId", addFriendId);
        String requestBodyData = ParamBuild.buildParams(map);
        netRequest.startRequest(wholeUrl, Request.Method.POST, requestBodyData, 0, aLsner);
    }

    BaseRequestListener aLsner = new JsonRequestListener() {

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
            showShortToast("接受好友请求成功！");
            asyncGetFriendApply();
        }
    };
}
