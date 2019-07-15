package com.asuper.angelgroup.moduel.home;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.asuper.angelgroup.BaseActivity;
import com.asuper.angelgroup.R;
import com.asuper.angelgroup.common.set.AppUrl;
import com.asuper.angelgroup.common.set.GlobalParam;
import com.asuper.angelgroup.common.set.Log;
import com.asuper.angelgroup.common.set.ParamBuild;
import com.asuper.angelgroup.common.tool.ComUtils;
import com.asuper.angelgroup.common.tool.DevAttr;
import com.asuper.angelgroup.common.tool.ImageManager;
import com.asuper.angelgroup.common.tool.ViewInitTool;
import com.asuper.angelgroup.moduel.home.bean.NewMsgBean;
import com.asuper.angelgroup.moduel.login.bean.UserBean;
import com.asuper.angelgroup.moduel.me.AboutActivity;
import com.asuper.angelgroup.moduel.me.MeInfoActivity;
import com.asuper.angelgroup.moduel.me.MyMessageActivity;
import com.asuper.angelgroup.moduel.me.SettingActivity;
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

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 首页
 * Created by shubei on 2017/11/16.
 */

public class HomeActivity extends BaseActivity {

    private int PAGE_NUM = 1;
    private static int PAGE_SIZE = 10;

    private View bottom_input_area, area_new_msg;
    private EditText edit_comt;
    private Button btn_send;
    private NaviShopPopWin mNaviShopPopWin;
    private TextView tv_msg_count;
    private ImageView img_msg_head;

    private PullToRefreshListView mPullRefreshListView;
    private PublishListAdapter adapter;
    private List<PublishItemBean> aList;

    private int curClickedPos, curCmtIndex;
    private boolean isRepaly;
    public static boolean needFresh = true;
    public static boolean canRemoveMsg = false;
    private long nextTimp;
    private int msgCountTotal;

    @Override
    public void setLayout() {
        setContentView(R.layout.activity_me);
    }

    @Override
    public void initView() {
        setPagTitle("家园");
        mNaviShopPopWin = new NaviShopPopWin(mContext);
        bottom_input_area = findViewById(R.id.bottom_input_area);
        mPullRefreshListView = (PullToRefreshListView) findViewById(R.id.pull_refresh_list);
        edit_comt = findViewById(R.id.edit_comt);
        btn_send = findViewById(R.id.btn_send);
        ViewInitTool.initPullToRefresh(mPullRefreshListView, mContext);
        area_new_msg = findViewById(R.id.area_new_msg);
        tv_msg_count = findViewById(R.id.tv_msg_count);
        img_msg_head = findViewById(R.id.img_msg_head);
        mPullRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {

            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                asyncGetUserInfo();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                PAGE_NUM++;
                asyncGetMyFriendsCircle(PAGE_NUM, PAGE_SIZE);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (needFresh) {
            asyncGetUserInfo();
            needFresh = false;
        }
        if (canRemoveMsg) {
            msgCountTotal = 0;
            area_new_msg.setVisibility(View.GONE);
            canRemoveMsg = false;
        }
    }

    @Override
    public void initData() {
        aList = new ArrayList<PublishItemBean>();
        adapter = new PublishListAdapter(mContext, aList, false);
        mPullRefreshListView.setAdapter(adapter);
        startAutoPlay();
    }

    @Override
    public void initListener() {
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
        area_right.setVisibility(View.GONE);
        area_new_msg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(mContext, MyMessageActivity.class));
                msgCountTotal = 0;
                area_new_msg.setVisibility(View.GONE);
            }
        });
        mPullRefreshListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int scrollState) {
                logout("===============state================" + scrollState);
                switch (scrollState) {
                    case SCROLL_STATE_IDLE://滑动停止时调用

                        break;
                    case SCROLL_STATE_TOUCH_SCROLL://正在滚动时调用
                        boolean isLvNeedNotify = false; // 是否需要重刷列表数据状态
                        for (PublishItemBean itemBean : aList) {
                            if (itemBean.isDongThingAreaShowing()) {
                                isLvNeedNotify = true;
                                break;
                            }
                        }
                        if (isLvNeedNotify) {
                            //滚动时，所有项的框都隐藏
                            for (int k = 0; k < aList.size(); k++) {
                                aList.get(k).setDongThingAreaShowing(false);
                            }
                            adapter.notifyDataSetChanged();
                        }
                        break;
                    case SCROLL_STATE_FLING://手指快速滑动时,在离开ListView由于惯性滑动

                        break;
                }
            }

            @Override
            public void onScroll(AbsListView absListView, int i, int i1, int i2) {

            }
        });
    }

    /**
     * 点击评论按钮，显示评论输入框
     */
    public void showComtArea() {
        //mPullRefreshListView.scrollTo(0, mPullRefreshListView.getRefreshableView().getChildAt(curClickedPos).getTop());

       /* int index = mPullRefreshListView.getRefreshableView().getFirstVisiblePosition();
        View v = mPullRefreshListView.getRefreshableView().getChildAt(1);
        int top = (v == null) ? 0 : v.getTop();*/


        mPullRefreshListView.getRefreshableView().setSelectionFromTop(curClickedPos + 2, DevAttr.getScreenHeight(mContext) - DevAttr.dip2px(mContext, 45 + 23 + 50 + 50));

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

    /***
     * 请求首页数据
     * @param mPageIndex 页码
     * @param mPageItemNum 每页条数
     */
    private void asyncGetMyFriendsCircle(int mPageIndex, int mPageItemNum) {
        String wholeUrl = AppUrl.host + AppUrl.myFriendsCircle;
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("curPage", mPageIndex);
        map.put("pageSize", mPageItemNum);
        String requestBodyData = ParamBuild.buildParams(map);
        netRequest.startRequest(wholeUrl, Request.Method.POST, requestBodyData, 0, mLsner);
    }

    BaseRequestListener mLsner = new JsonRequestListener() {

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
     * 请求个人信息
     */
    private void asyncGetUserInfo() {
        String wholeUrl = AppUrl.host + AppUrl.getUser;
        String requestBodyData = "";
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
            ViewInitTool.listStopLoadView(mPullRefreshListView);
        }

        @Override
        public void onSuccess(int requestId, String url, JSONObject jsonResult) {
            dismissDialog();
            GlobalParam.currentUser = new Gson().fromJson(jsonResult.toString(), UserBean.class);
            PAGE_NUM = 1;
            asyncGetMyFriendsCircle(PAGE_NUM, PAGE_SIZE);
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

    private void asyncDelItem() {
        String wholeUrl = AppUrl.host + AppUrl.deleteItem;
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("itemId", aList.get(curClickedPos).getItemId());
        map.put("source", aList.get(curClickedPos).getClassifyType());
        String requestBodyData = ParamBuild.buildParams(map);
        netRequest.startRequest(wholeUrl, Request.Method.POST, requestBodyData, 0, new SimpleRequestListener());
    }

    /**
     * 开始轮询
     */
    public void startAutoPlay() {
        if (!isAutoPlay) {
            viewHandler.sendEmptyMessageDelayed(WHAT_PLAYING, DELAY_MILLIS);
            isAutoPlay = true;
        }
    }

    /**
     * 停止轮询
     */
    public void stopAutoPlay() {
        if (isAutoPlay) {
            viewHandler.removeMessages(WHAT_PLAYING);
            isAutoPlay = false;
        }
    }

    private boolean isAutoPlay; //是否开始轮询
    private static int WHAT_PLAYING = 111;
    private static int DELAY_MILLIS = 10 * 1000;

    private final Handler viewHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            if (msg.what == WHAT_PLAYING && GlobalParam.userToken != null) {
                asyncGetMessageCount();
                viewHandler.sendEmptyMessageDelayed(WHAT_PLAYING, DELAY_MILLIS);
            }
            super.handleMessage(msg);
        }
    };

    /**
     * 轮询新消息
     */
    private void asyncGetMessageCount() {
        String wholeUrl = AppUrl.host + AppUrl.getMessageCount;
        Map<String, Object> map = new HashMap<String, Object>();
        if (nextTimp > 0)
            map.put("data", nextTimp);
        String requestBodyData = ParamBuild.buildParams(map);
        netRequest.startRequest(wholeUrl, Request.Method.POST, requestBodyData, 0, gmLsner);
    }

    BaseRequestListener gmLsner = new JsonRequestListener() {

        @Override
        public void onStart(int requestId) {
        }

        @Override
        public void onError(int requestId, String errorCode, String errorMsg) {
        }

        @Override
        public void onSuccess(int requestId, String url, JSONObject jsonResult) {
            NewMsgBean newMsgBean = new Gson().fromJson(jsonResult.toString(), NewMsgBean.class);
            nextTimp = newMsgBean.getTime();
            msgCountTotal += newMsgBean.getCount();
            if (newMsgBean.getCount() > 0) {
                if (area_new_msg != null)
                    area_new_msg.setVisibility(View.VISIBLE);
                if (tv_msg_count != null)
                    tv_msg_count.setText(msgCountTotal + " 条新消息");
                if (img_msg_head != null)
                    ImageManager.getInstance().displayImg(img_msg_head, newMsgBean.getMessages().get(0).getSenderPicture());
            }
//            else {
//                if (area_new_msg != null)
//                    area_new_msg.setVisibility(View.GONE);
//            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopAutoPlay();
    }

    public void getKeyBoardHeight() {
        // Add these code in activity, such as onCreate method.
        final Context context = getApplicationContext();
        final RelativeLayout parentLayout = (RelativeLayout) findViewById(R.id.parent);
        final View myLayout = getWindow().getDecorView();
        parentLayout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

            @Override
            public void onGlobalLayout() {
                Rect r = new Rect();
                // r will be populated with the coordinates of your view that area still visible.
                parentLayout.getWindowVisibleDisplayFrame(r);
                int screenHeight = myLayout.getRootView().getHeight();
                int heightDiff = screenHeight - (r.bottom - r.top);
                if (heightDiff > 100) {
                    // if more than 100 pixels, its probably a keyboard
                    // get status bar height
                    int statusBarHeight = 0;
                    try {
                        Class<?> c = Class.forName("com.android.internal.R$dimen");
                        Object obj = c.newInstance();
                        Field field = c.getField("status_bar_height");
                        int x = Integer.parseInt(field.get(obj).toString());
                        statusBarHeight = context.getResources().getDimensionPixelSize(x);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    int realKeyboardHeight = heightDiff - statusBarHeight;
                    logout("keyboard height = " + realKeyboardHeight);
                }
            }
        });
    }
}