package com.asuper.angelgroup.moduel.me;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.asuper.angelgroup.BaseActivity;
import com.asuper.angelgroup.MainTabActivity;
import com.asuper.angelgroup.R;
import com.asuper.angelgroup.common.set.AppUrl;
import com.asuper.angelgroup.common.set.GlobalParam;
import com.asuper.angelgroup.common.set.ParamBuild;
import com.asuper.angelgroup.common.tool.ComUtils;
import com.asuper.angelgroup.common.tool.DevAttr;
import com.asuper.angelgroup.common.tool.ImageManager;
import com.asuper.angelgroup.common.tool.ViewInitTool;
import com.asuper.angelgroup.moduel.login.FillInfoRoleActivity;
import com.asuper.angelgroup.moduel.login.bean.UserBean;
import com.asuper.angelgroup.moduel.me.adapter.PublishListAdapter;
import com.asuper.angelgroup.moduel.me.bean.MockPic;
import com.asuper.angelgroup.moduel.me.bean.MockWords;
import com.asuper.angelgroup.moduel.me.bean.PublishItemBean;
import com.asuper.angelgroup.moduel.me.bean.PublishItemOpenLevel;
import com.asuper.angelgroup.moduel.me.bean.PublishItemType;
import com.asuper.angelgroup.net.base.Request;
import com.asuper.angelgroup.net.request.SimpleRequestListener;
import com.asuper.angelgroup.net.request.interfa.BaseRequestListener;
import com.asuper.angelgroup.net.request.interfa.JsonRequestListener;
import com.asuper.angelgroup.widget.pw.NaviShopPopWin;
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
 * 我的主页
 * Created by shubei on 2017/11/17.
 */

public class MeActivity extends BaseActivity {

    private static final int REQ_FOR_INFO = 1;
    private int PAGE_NUM = 1;
    private static int PAGE_SIZE = 10;

    private View bottom_input_area;
    private EditText edit_comt;
    private Button btn_send;
    private NaviShopPopWin mNaviShopPopWin;
    private View headerView;
    private ImageView img_user_head;
    private TextView tv_user_name, tv_intro, tv_resume;

    private PullToRefreshListView mPullRefreshListView;
    private PublishListAdapter adapter;
    private List<PublishItemBean> aList;
    private int curClickedPos, curCmtIndex;
    private boolean isRepaly;

    @Override
    public void setLayout() {
        setContentView(R.layout.activity_me);
    }

    @Override
    public void initView() {
        setPagTitle("我的");
        mNaviShopPopWin = new NaviShopPopWin(mContext);
        bottom_input_area = findViewById(R.id.bottom_input_area);
        mPullRefreshListView = (PullToRefreshListView) findViewById(R.id.pull_refresh_list);
        edit_comt = findViewById(R.id.edit_comt);
        btn_send = findViewById(R.id.btn_send);
        ViewInitTool.initPullToRefresh(mPullRefreshListView, mContext);
        headerView = View.inflate(getApplicationContext(), R.layout.list_mine_header, null);
        img_user_head = headerView.findViewById(R.id.img_user_head);
        tv_user_name = headerView.findViewById(R.id.tv_user_name);
        tv_intro = headerView.findViewById(R.id.tv_intro);
        tv_resume = headerView.findViewById(R.id.tv_resume);
        mPullRefreshListView.getRefreshableView().addHeaderView(headerView);
    }

    @Override
    public void initData() {
        aList = new ArrayList<PublishItemBean>();
        adapter = new PublishListAdapter(mContext, aList, true);
        mPullRefreshListView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (GlobalParam.currentUser == null)
            return;
        fillUserBaseData();
        asyncGetContentUserInfo();
    }

    public void fillUserBaseData() {
        ImageManager.getInstance().displayImg(img_user_head, GlobalParam.currentUser.getPictureUrl());
        tv_user_name.setText(GlobalParam.currentUser.getPetName());
        if ("002".equals(GlobalParam.currentUser.getRechargeTypeCode())) {
            tv_intro.setText(GlobalParam.currentUser.getPositionalTitle());
        } else if ("001".equals(GlobalParam.currentUser.getRechargeTypeCode())) {
            tv_intro.setText(GlobalParam.currentUser.getUserChildVO().getPetName() + GlobalParam.currentUser.getUserRelationConstantVO().getRelationName() + "·" + GlobalParam.currentUser.getUserIdentityLabel().getLableName()
                    + "·" + GlobalParam.currentUser.getUserChildVO().getAge() + "·" + GlobalParam.currentUser.getCityName());
        } else {
            tv_intro.setText("");
        }
        tv_resume.setText(GlobalParam.currentUser.getResume());
    }

    @Override
    public void initListener() {
        mPullRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {

            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                PAGE_NUM = 1;
                asyncGetContentUserInfo();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                PAGE_NUM++;
                asyncGetContentUserInfo();
            }
        });
        adapter.setOnRecordDoneListener(new PublishListAdapter.OnRecordDoneListener() {
            @Override
            public void onCommentClicked(int position) {
                isRepaly = false;
                curClickedPos = position;
                showComtArea();
            }

            @Override
            public void onRepalyClicked(int position, int cmtIndex) {
                aList.get(position).setDongThingAreaShowing(false);//关闭操作框
                adapter.notifyDataSetChanged();

                isRepaly = true;
                curClickedPos = position;
                curCmtIndex = cmtIndex;
                showComtArea();
            }

            @Override
            public void onDeleteClicked(final int position) {
                dDialog.showDialog("提示", "确定删除该条记录吗？", "取消", "确定", null, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dDialog.dismissDialog();

                        curClickedPos = position;
                        asyncDelItem();

                        aList.remove(position);
                        adapter.notifyDataSetChanged();
                    }
                });
            }

            @Override
            public void onPraiseClicked(int position) {
                curClickedPos = position;
                asyncAddPraise();
            }

            @Override
            public void onDelPraiseClicked(int position, int cmtIndex) {
                curClickedPos = position;
                curCmtIndex = cmtIndex;
                asyncDelPraise();
            }
        });
        btn_send.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(edit_comt.getText().toString().trim())) {
                    showShortToast("您未填写评论！");
                    return;
                }
                asyncAddComment();
            }
        });
        area_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int offX = DevAttr.dip2px(mContext, -120);
                int offY = DevAttr.dip2px(mContext, 3);
                mNaviShopPopWin.showAsDropDown(findViewById(R.id.area_right), offX, offY);
            }
        });
        mNaviShopPopWin.getAreaMeInfo().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mNaviShopPopWin.dismiss();
                if (GlobalParam.currentUser == null)
                    return;
                if ("001".equals(GlobalParam.currentUser.getRechargeTypeCode()))
                    startActivity(new Intent(mContext, MeInfoActivity.class));
                else
                    startActivity(new Intent(mContext, MeInfoOthersActivity.class));
            }
        });
        mNaviShopPopWin.getAreaToMsg().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mNaviShopPopWin.dismiss();
                startActivity(new Intent(mContext, MyMessageActivity.class));
            }
        });
        mNaviShopPopWin.getAreaToAbout().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mNaviShopPopWin.dismiss();
                startActivity(new Intent(mContext, AboutActivity.class));
            }
        });
        mNaviShopPopWin.getAreaToSetting().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mNaviShopPopWin.dismiss();
                startActivity(new Intent(mContext, SettingActivity.class));
            }
        });
    }

    /**
     * 请求个人发布历史数据
     */
    private void asyncGetContentUserInfo() {
        String wholeUrl = AppUrl.host + AppUrl.contentUserInfo;
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("curPage", PAGE_NUM);
        map.put("pageSize", PAGE_SIZE);
        String requestBodyData = ParamBuild.buildParams(map);
        netRequest.startRequest(wholeUrl, Request.Method.POST, requestBodyData, 0, regLsner);
    }

    BaseRequestListener regLsner = new JsonRequestListener() {

        @Override
        public void onStart(int requestId) {
            if (PAGE_NUM == 1)
                showDialog();
        }

        @Override
        public void onError(int requestId, String errorCode, String errorMsg) {
            dismissDialog();
            showShortToast(errorMsg);
            ViewInitTool.listStopLoadView(mPullRefreshListView);
            if (PAGE_NUM > 1) {
                PAGE_NUM--;
            }
        }

        @Override
        public void onSuccess(int requestId, String url, JSONObject jsonResult) {
            dismissDialog();
            ViewInitTool.listStopLoadView(mPullRefreshListView);
            ArrayList<PublishItemBean> currentPageList = new ArrayList<>();
            JSONArray actJay = jsonResult.optJSONObject("content").optJSONArray("list");
            // 第一次查询的时候没有数据，则提示没有数据，页面置空
            if (PAGE_NUM == 1 && (actJay == null || actJay.length() <= 0)) {
                aList.clear();
                adapter.notifyDataSetChanged();
                ViewInitTool.setPullToRefreshViewEnd(mPullRefreshListView);
                return;
            }
            // 首次加载清空所有项列表,并重置控件为可下滑
            if (PAGE_NUM == 1) {
                aList.clear();
                ViewInitTool.setPullToRefreshViewBoth(mPullRefreshListView);
            }
            // 如果当前页已经是最后一页，则列表控件置为不可下滑
            if (PAGE_NUM >= jsonResult.optJSONObject("content").optInt("totalPage")) {
                ViewInitTool.setPullToRefreshViewEnd(mPullRefreshListView);
            }
            for (int i = 0; i < actJay.length(); i++) {
                JSONObject actJot = actJay.optJSONObject(i);
                PublishItemBean c = new Gson().fromJson(actJot.toString(), PublishItemBean.class);
                currentPageList.add(c);
            }
            aList.addAll(currentPageList);
            adapter.notifyDataSetChanged();
        }
    };

    /**
     * 点击评论按钮，显示评论输入框
     */
    public void showComtArea() {
        //mPullRefreshListView.getRefreshableView().setSelectionFromTop(curClickedPos + 2, 0);
        mPullRefreshListView.getRefreshableView().setSelectionFromTop(curClickedPos + 3, DevAttr.getScreenHeight(mContext) - DevAttr.dip2px(mContext, 45 + 23 + 50 + 50));

        // 点击评论按钮，显示评论输入框
        bottom_input_area.setVisibility(View.VISIBLE);
        edit_comt.requestFocus();

        InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        im.showSoftInput(edit_comt, 0);

        if (isRepaly) {
            edit_comt.setHint("回复");
        } else {
            edit_comt.setHint("说点什么...");
        }
    }

    /**
     * 监控点击按钮如果点击在评论输入框之外就关闭输入框，变回报名栏
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = bottom_input_area;// getCurrentFocus();
            if (ViewInitTool.isShouldHideInput(v, ev)) {
                hideKeyboard();
                bottom_input_area.setVisibility(View.GONE);
            }
            return super.dispatchTouchEvent(ev);
        }
        // 必不可少，否则所有的组件都不会有TouchEvent了
        if (getWindow().superDispatchTouchEvent(ev)) {
            return true;
        }
        return onTouchEvent(ev);
    }

   /* @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK)
            return;
        if (requestCode == REQ_FOR_INFO) {
            fillUserBaseData();
        }
    }*/

    /**
     * 点赞
     */
    private void asyncAddPraise() {
        String wholeUrl = AppUrl.host + AppUrl.addPraise;
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("itemId", aList.get(curClickedPos).getItemId());
        map.put("source", aList.get(curClickedPos).getClassifyType());
        String requestBodyData = ParamBuild.buildParams(map);
        netRequest.startRequest(wholeUrl, Request.Method.POST, requestBodyData, 0, pLsner);
    }

    BaseRequestListener pLsner = new JsonRequestListener() {

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
            showShortToast("点赞成功！");
            //------------------即时更新点赞 start -----------------------------//
            aList.get(curClickedPos).getPraiseList().add(new PublishItemBean.PraiseListBean(GlobalParam.currentUser.getId(), GlobalParam.currentUser.getName()));
            adapter.notifyDataSetChanged();
            //------------------即时更新点赞 end -----------------------------//
        }
    };

    /**
     * 取消点赞
     */
    private void asyncDelPraise() {
        String wholeUrl = AppUrl.host + AppUrl.delPraise;
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("pariseId", aList.get(curClickedPos).getItemId());
        map.put("source", aList.get(curClickedPos).getClassifyType());
        String requestBodyData = ParamBuild.buildParams(map);
        netRequest.startRequest(wholeUrl, Request.Method.POST, requestBodyData, 0, dLsner);
    }

    BaseRequestListener dLsner = new JsonRequestListener() {

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
            showShortToast("取消点赞成功！");
            //------------------即时取消点赞 start -----------------------------//
            aList.get(curClickedPos).getPraiseList().remove(aList.get(curClickedPos).getPraiseList().get(curCmtIndex));
            adapter.notifyDataSetChanged();
            //------------------即时取消点赞 end -----------------------------//
        }
    };

    /**
     * 发布评论
     */
    private void asyncAddComment() {
        String wholeUrl = AppUrl.host + AppUrl.addComment;
        Map<String, Object> map = new HashMap<String, Object>();
        if (isRepaly) {
            map.put("parentId", aList.get(curClickedPos).getItemCommentList().get(curCmtIndex).getId());
        }
        map.put("itemId", aList.get(curClickedPos).getItemId());
        map.put("content", edit_comt.getText().toString().trim());
        map.put("source", aList.get(curClickedPos).getClassifyType());
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
            //------------------即时更新评论 start -----------------------------//
            PublishItemBean.ItemCommentListBean itemCommentListBean = new PublishItemBean.ItemCommentListBean();
            itemCommentListBean.setUserId(GlobalParam.currentUser.getId());
            itemCommentListBean.setContent(edit_comt.getText().toString().trim());
            itemCommentListBean.setUserName(GlobalParam.currentUser.getName());
            itemCommentListBean.setCreateDate(System.currentTimeMillis());
            if (isRepaly) {
                itemCommentListBean.setParentUserName(aList.get(curClickedPos).getItemCommentList().get(curCmtIndex).getUserName());
            }
            aList.get(curClickedPos).getItemCommentList().add(itemCommentListBean);
            adapter.notifyDataSetChanged();
            //------------------即时更新评论 end -----------------------------//

            dismissDialog();
            showShortToast("评论成功！");
            // 清空评论并隐藏评论框
            edit_comt.setText("");
            hideKeyboard();
            bottom_input_area.setVisibility(View.GONE);
        }
    };

    private void asyncDelItem() {
        String wholeUrl = AppUrl.host + AppUrl.deleteItem;
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("itemId", aList.get(curClickedPos).getItemId());
        map.put("source", aList.get(curClickedPos).getClassifyType());
        String requestBodyData = ParamBuild.buildParams(map);
        netRequest.startRequest(wholeUrl, Request.Method.POST, requestBodyData, 0, new SimpleRequestListener());
    }
}
