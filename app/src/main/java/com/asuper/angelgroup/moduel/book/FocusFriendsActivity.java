package com.asuper.angelgroup.moduel.book;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.asuper.angelgroup.BaseActivity;
import com.asuper.angelgroup.R;
import com.asuper.angelgroup.common.set.AppUrl;
import com.asuper.angelgroup.moduel.book.adapter.AddRequestListAdapter;
import com.asuper.angelgroup.moduel.book.adapter.FocusListAdapter;
import com.asuper.angelgroup.moduel.book.bean.AddRequestBean;
import com.asuper.angelgroup.net.base.Request;
import com.asuper.angelgroup.net.request.interfa.BaseRequestListener;
import com.asuper.angelgroup.net.request.interfa.JsonRequestListener;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 关注的好友页面
 * Created by shubei on 2017/11/18.
 */

public class FocusFriendsActivity extends BaseActivity {

    private List<AddRequestBean> mList;
    private FocusListAdapter adapter;

    private ListView lv_request;

    @Override
    public void setLayout() {
        setContentView(R.layout.activity_new_friends);
    }

    @Override
    public void initView() {
        setPagTitle("我的关注");
        lv_request = (ListView) findViewById(R.id.lv_request);
    }

    @Override
    public void initData() {
        mList = new ArrayList<>();
        adapter = new FocusListAdapter(mContext, mList);
        lv_request.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        asyncGetFriendApply();
    }

    @Override
    public void initListener() {
        lv_request.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent it = new Intent(mContext, OthersMainInfoActivity.class);
                it.putExtra("OTHER_NAME", mList.get(i).getPetName());
                it.putExtra("userId", mList.get(i).getFocusUserId());
                startActivity(it);
            }
        });
    }

    private void asyncGetFriendApply() {
        String wholeUrl = AppUrl.host + AppUrl.getMyFocuUsers;
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
            if ("9977".equals(errorCode)) {

            } else {
                showShortToast(errorMsg);
            }
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
}
