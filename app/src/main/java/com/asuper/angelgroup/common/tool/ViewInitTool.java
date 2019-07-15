package com.asuper.angelgroup.common.tool;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.Editable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.AbsoluteSizeSpan;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.asuper.angelgroup.R;
import com.asuper.angelgroup.common.set.Log;
import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.ObservableScrollView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

public class ViewInitTool {

    /**
     * 初始化上下拉刷新控件 文字样式
     */
    public static void initPullToRefresh(PullToRefreshScrollView mPullRefreshScrollView, Context context) {
        ILoadingLayout startLabels = mPullRefreshScrollView.getLoadingLayoutProxy(true, false);
        startLabels.setPullLabel("下拉刷新...");// 刚下拉时，显示的提示
        startLabels.setRefreshingLabel("正在刷新...");// 刷新时
        startLabels.setReleaseLabel("放开刷新...");// 下来达到一定距离时，显示的提示
//        Drawable ld = context.getResources().getDrawable(R.drawable.animation_list_small_loading);
//        startLabels.setLoadingDrawable(ld);

        ILoadingLayout endLabels = mPullRefreshScrollView.getLoadingLayoutProxy(false, true);
        endLabels.setPullLabel("上拉加载...");// 刚下拉时，显示的提示
        endLabels.setRefreshingLabel("正在加载...");// 刷新时
        endLabels.setReleaseLabel("放开加载...");// 下来达到一定距离时，显示的提示
    }

    /**
     * 初始化上下拉刷新控件 文字样式
     */
    public static void initPullToRefresh(PullToRefreshListView mPullRefreshListView, Context context) {
        ILoadingLayout startLabels = mPullRefreshListView.getLoadingLayoutProxy(true, false);
        startLabels.setPullLabel("下拉刷新...");// 刚下拉时，显示的提示
        startLabels.setRefreshingLabel("正在刷新...");// 刷新时
        startLabels.setReleaseLabel("放开刷新...");// 下来达到一定距离时，显示的提示
//        Drawable ld = context.getResources().getDrawable(R.drawable.animation_list_small_loading);
//        startLabels.setLoadingDrawable(ld);

        ILoadingLayout endLabels = mPullRefreshListView.getLoadingLayoutProxy(false, true);
        endLabels.setPullLabel("上拉加载...");// 刚下拉时，显示的提示
        endLabels.setRefreshingLabel("正在加载...");// 刷新时
        endLabels.setReleaseLabel("放开加载...");// 下来达到一定距离时，显示的提示
    }

    /**
     * 初始化上下拉刷新控件 文字样式
     */
    public static void initPullToRefresh(PullToRefreshGridView mPullToRefreshGridView) {
        ILoadingLayout startLabels = mPullToRefreshGridView.getLoadingLayoutProxy(true, false);
        startLabels.setPullLabel("下拉刷新...");// 刚下拉时，显示的提示
        startLabels.setRefreshingLabel("正在刷新...");// 刷新时
        startLabels.setReleaseLabel("放开刷新...");// 下来达到一定距离时，显示的提示

        ILoadingLayout endLabels = mPullToRefreshGridView.getLoadingLayoutProxy(false, true);
        endLabels.setPullLabel("上拉加载...");// 刚下拉时，显示的提示
        endLabels.setRefreshingLabel("正在加载...");// 刷新时
        endLabels.setReleaseLabel("放开加载...");// 下来达到一定距离时，显示的提示
    }

    /**
     * 初始化黏贴控件
     */
    public static void initObservableScrollView(
            final ObservableScrollView scrollView,
            final ObservableScrollView.Callbacks mCallbacks) {
        scrollView.setCallbacks(mCallbacks);
        scrollView.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {

                    @Override
                    public void onGlobalLayout() {
                        mCallbacks.onScrollChanged(scrollView.getScrollY());
                    }
                });
        scrollView.scrollTo(0, 0);
    }

    /**
     * 设置列表为空提示
     */
    public static void setListEmptyView(Context mContext, ListView lv) {
        TextView emptyView = new TextView(mContext);
        emptyView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT));
        emptyView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        emptyView.setTextColor(mContext.getResources()
                .getColor(R.color.g666666));
        emptyView.setGravity(Gravity.CENTER);
        emptyView.setText("暂无数据");
        emptyView.setVisibility(View.GONE);
        ((ViewGroup) lv.getParent()).addView(emptyView);
        lv.setEmptyView(emptyView);
    }

    /**
     * 设置列表为空提示（设置高度）
     */
    public static void setListEmptyView(Context mContext, ListView lv, int mTop) {
        TextView emptyView = new TextView(mContext);
        emptyView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT));
        emptyView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        emptyView.setTextColor(mContext.getResources()
                .getColor(R.color.g666666));
        emptyView.setGravity(Gravity.CENTER);
        emptyView.setText("暂无数据");
        emptyView.setPadding(mTop, mTop, mTop, mTop);
        emptyView.setVisibility(View.GONE);
        ((ViewGroup) lv.getParent()).addView(emptyView);
        lv.setEmptyView(emptyView);
    }

    /**
     * 设置列表为空提示(设置图片) 有文字，图片默认
     */
    public static void setListEmptyByDefaultTipPic(Context mContext, ListView lv) {
        setListEmptyView(mContext, lv, "暂无数据", R.mipmap.quexing, null, 100, 100);
    }

    /**
     * 设置列表为空提示(设置图片) 有文字，图片默认
     */
    public static void setListEmptyTipByDefaultPic(Context mContext, ListView lv, String tip) {
        setListEmptyView(mContext, lv, tip, R.mipmap.quexing, null, 100, 100);
    }

    /**
     * 设置列表为空提示(设置图片)
     */
    public static void setListEmptyView(Context mContext, ListView lv,
                                        String tip, int res, OnClickListener lsner) {
        setListEmptyView(mContext, lv, tip, res, lsner, 0, 0);
    }

    public static void setListEmptyView(Context mContext, ListView lv,
                                        String tip, int res, OnClickListener lsner, int pWidth, int pHeight) {
        LinearLayout ll = new LinearLayout(mContext);
        ll.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        ll.setOrientation(LinearLayout.VERTICAL);
        ll.setGravity(Gravity.CENTER);

        ImageView img = new ImageView(mContext);
        img.setImageResource(res);
        if (pWidth > 0 && pHeight > 0) {
            img.setLayoutParams(new LayoutParams(DevAttr.dip2px(mContext, pWidth),
                    DevAttr.dip2px(mContext, pHeight)));
        } else {
            img.setLayoutParams(new LayoutParams(DevAttr.dip2px(mContext, 200),
                    DevAttr.dip2px(mContext, 200)));
        }
        img.setOnClickListener(lsner);
        TextView emptyView = new TextView(mContext);
        emptyView.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        emptyView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
        emptyView.setTextColor(mContext.getResources().getColor(R.color.g999999));
        emptyView.setText(tip);
        setMargins(emptyView, 0, 50, 0, 0);

        ll.addView(img);
        ll.addView(emptyView);
        ll.setVisibility(View.GONE);
        ll.setTag("empty_tag");

        //-----避免重复加载空提示视图 start------//
        ViewGroup parentGroup = (ViewGroup) lv.getParent();
        boolean hasEmptyView = false;
        for (int i = 0; i < parentGroup.getChildCount(); i++) {
            if (parentGroup.getChildAt(i).getTag() != null && parentGroup.getChildAt(i).getTag().equals("empty_tag")) {
                hasEmptyView = true;
            }
        }
        //-----避免重复加载空提示视图 end------//

        if (!hasEmptyView) { // 不存在空提示视图时才加上去
            ((ViewGroup) lv.getParent()).addView(ll);
            lv.setEmptyView(ll);
        }
    }

    public static void setMargins(View v, int l, int t, int r, int b) {
        if (v.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) v
                    .getLayoutParams();
            p.setMargins(l, t, r, b);
            v.requestLayout();
        }
    }

    /**
     * 停止列表进度条
     */
    public static void listStopLoadView(PullToRefreshListView mPullRefreshListView) {
        mPullRefreshListView.onRefreshComplete();
    }

    /**
     * 将上下拉控件设为到底
     */
    public static void setPullToRefreshViewEnd(PullToRefreshListView mPullRefreshListView) {
        mPullRefreshListView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
    }

    /**
     * 将上下拉控件设为可上下拉
     */
    public static void setPullToRefreshViewBoth(PullToRefreshListView mPullRefreshListView) {
        mPullRefreshListView.setMode(PullToRefreshBase.Mode.BOTH);
    }



    /**
     * 给输入框加小数点后只能输入2位的限制
     *
     * @param edit
     */
    public static void addEditTextLimit2AfterPoint(final EditText edit) {
        edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().contains(".")) {
                    if (s.length() - 1 - s.toString().indexOf(".") > 2) {
                        s = s.toString().subSequence(0, s.toString().indexOf(".") + 3);
                        edit.setText(s);
                        edit.setSelection(s.length());
                    }
                }
                if (s.toString().trim().substring(0).equals(".")) {
                    s = "0" + s;
                    edit.setText(s);
                    edit.setSelection(2);
                }

                if (s.toString().startsWith("0") && s.toString().trim().length() > 1) {
                    if (!s.toString().substring(1, 2).equals(".")) {
                        edit.setText(s.subSequence(0, 1));
                        edit.setSelection(1);
                        return;
                    }
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    /**
     * 给输入框增加验证按钮是否可以点击
     */
    public static void addJudgeBtnEnableListener(final List<EditText> etList, final Button okBtn) {
        okBtn.setTextColor(Color.parseColor("#FF8989"));
        okBtn.setEnabled(false);
        for (int i = 0; i < etList.size(); i++) {
            EditText edit = etList.get(i);
            edit.addTextChangedListener(new TextWatcher() {
                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    boolean isOkBtnEnable = true;
                    Log.out("s=======" + s);
                    if (!TextUtils.isEmpty(s)) {
                        for (EditText etItem : etList) {
                            String inputStr = etItem.getText().toString().trim();
                            Log.out("inputStr=======" + inputStr);
                            if (TextUtils.isEmpty(inputStr)) {
                                isOkBtnEnable = false;
                                break;
                            }
                        }
                    } else {
                        Log.out("isOkBtnEnable1=======" + isOkBtnEnable);
                        isOkBtnEnable = false;
                    }
                    Log.out("isOkBtnEnable1=======" + isOkBtnEnable);
                    if (isOkBtnEnable) {
                        okBtn.setTextColor(Color.parseColor("#ffffff"));
                        okBtn.setEnabled(true);
                    } else {
                        okBtn.setTextColor(Color.parseColor("#FF8989"));
                        okBtn.setEnabled(false);
                    }
                }

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void afterTextChanged(Editable s) {
                }
            });
        }
    }

    /**
     * 给输入框增加验证按钮是否可以点击
     */
    public static void addJudgeBtnAndDelEnableListener(
            final List<EditText> etList, final List<ImageView> delViewList, final Button okBtn) {
        okBtn.setTextColor(Color.parseColor("#FF8989"));
        okBtn.setEnabled(false);
        for (int i = 0; i < etList.size(); i++) {
            EditText edit = etList.get(i);
            final ImageView delImg = delViewList.get(i);
            edit.addTextChangedListener(new TextWatcher() {
                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    boolean isOkBtnEnable = true;
                    if (!TextUtils.isEmpty(s)) {
                        delImg.setVisibility(View.VISIBLE);
                        for (EditText etItem : etList) {
                            String inputStr = etItem.getText().toString().trim();
                            if (TextUtils.isEmpty(inputStr)) {
                                isOkBtnEnable = false;
                                break;
                            }
                        }
                    } else {
                        delImg.setVisibility(View.INVISIBLE);
                        isOkBtnEnable = false;
                    }
                    if (isOkBtnEnable) {
                        okBtn.setTextColor(Color.parseColor("#ffffff"));
                        okBtn.setEnabled(true);
                    } else {
                        okBtn.setTextColor(Color.parseColor("#FF8989"));
                        okBtn.setEnabled(false);
                    }
                }

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void afterTextChanged(Editable s) {
                }
            });
        }
    }

    public static void lineText(TextView tv){
        tv.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
    }

    /**
     * 判断点击区域是否在评论输入框内
     */
    public static boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null) {// && (v instanceof EditText)
            int[] leftTop = {0, 0};
            // 获取输入框当前的location位置
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            if (event.getRawX() > left && event.getRawX() < right
                    && event.getRawY() > top && event.getRawY() < bottom) {
                // 点击的是输入框区域，保留点击EditText的事件
                return false;
            } else {
                return true;
            }
        }
        return false;
    }


}
