package com.asuper.angelgroup.moduel.me;

import android.widget.ListView;

import com.asuper.angelgroup.BaseActivity;
import com.asuper.angelgroup.R;
import com.asuper.angelgroup.common.set.AppUrl;
import com.asuper.angelgroup.common.set.ParamBuild;
import com.asuper.angelgroup.common.tool.ViewInitTool;
import com.asuper.angelgroup.moduel.home.HomeActivity;
import com.asuper.angelgroup.moduel.me.adapter.MsgListAdapter;
import com.asuper.angelgroup.moduel.me.bean.MsgItem;
import com.asuper.angelgroup.net.base.Request;
import com.asuper.angelgroup.net.request.interfa.BaseRequestListener;
import com.asuper.angelgroup.net.request.interfa.JsonRequestListener;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 我的消息
 * Created by shubei on 2017/12/4.
 */

public class MyMessageActivity extends BaseActivity {

    private int PAGE_NUM = 1;
    private final int PAGE_SIZE = 10;

    private PullToRefreshListView mPullRefreshListView;
    private ListView actualListView;

    private MsgListAdapter mAdapter;
    private List<MsgItem> msgDataList;

    @Override
    public void setLayout() {
        setContentView(R.layout.activity_msg);
    }

    @Override
    public void initView() {
        setPagTitle("消息");
        mPullRefreshListView = (PullToRefreshListView) findViewById(R.id.pull_refresh_list);
        ViewInitTool.initPullToRefresh(mPullRefreshListView, mContext);
        actualListView = mPullRefreshListView.getRefreshableView();
        msgDataList = new ArrayList<MsgItem>();
        mAdapter = new MsgListAdapter(mContext, msgDataList);
        actualListView.setAdapter(mAdapter);
        ViewInitTool.setListEmptyView(mContext, actualListView);
    }

    @Override
    protected void onResume() {
        super.onResume();
        asyncGetMsgList();
    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {
        mPullRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {

            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                PAGE_NUM = 1;
                asyncGetMsgList();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                PAGE_NUM++;
                asyncGetMsgList();
            }
        });
    }

    /**
     * 请求消息数据
     */
    private void asyncGetMsgList() {
        String wholeUrl = AppUrl.host + AppUrl.getMessageByUser;
        Map<String, Object> map = new HashMap<String, Object>();
//        map.put("myFriendId", list.get(pos).getId());
        map.put("curPage", PAGE_NUM);
        String requestBodyData = ParamBuild.buildParams(map);
        netRequest.startRequest(wholeUrl, Request.Method.POST, requestBodyData, 0, listener);
    }

    BaseRequestListener listener = new JsonRequestListener() {

        @Override
        public void onStart(int requestId) {
            showDialog();
        }

        @Override
        public void onError(int requestId, String errorCode, String errorMsg) {
            dismissDialog();
            listStopLoadView();
            showShortToast(errorMsg);
        }

        @Override
        public void onSuccess(int requestId, String url, JSONObject jsonResult) {
            dismissDialog();
            listStopLoadView();
            ArrayList<MsgItem> currentPageList = new ArrayList<MsgItem>();
            JSONArray actJay = jsonResult.optJSONArray("list");
            // 第一次查询的时候没有数据，则提示没有数据，页面置空
            if (PAGE_NUM == 1 && (actJay == null || actJay.length() <= 0)) {
                msgDataList.clear();
                mAdapter.notifyDataSetChanged();
                setPullToRefreshViewEnd();
                return;
            }
            // 首次加载清空所有项列表,并重置控件为可下滑
            if (PAGE_NUM == 1) {
                msgDataList.clear();
                setPullToRefreshViewBoth();
            }
            // 如果当前页已经是最后一页，则列表控件置为不可下滑
            if (PAGE_NUM >= jsonResult.optInt("totalPage")) {
                setPullToRefreshViewEnd();
            }
            for (int i = 0; i < actJay.length(); i++) {
                JSONObject actJot = actJay.optJSONObject(i);
                MsgItem c = new Gson().fromJson(actJot.toString(), MsgItem.class);
                currentPageList.add(c);
            }
            msgDataList.addAll(currentPageList);
            mAdapter.notifyDataSetChanged();
            HomeActivity.canRemoveMsg = true;
        }
    };

    /**
     * 停止列表进度条
     */
    protected void listStopLoadView() {
        mPullRefreshListView.onRefreshComplete();
    }

    /**
     * 将上下拉控件设为到底
     */
    protected void setPullToRefreshViewEnd() {
        mPullRefreshListView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
    }

    /**
     * 将上下拉控件设为可上下拉
     */
    protected void setPullToRefreshViewBoth() {
        mPullRefreshListView.setMode(PullToRefreshBase.Mode.BOTH);
    }
}
