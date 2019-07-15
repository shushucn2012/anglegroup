package com.asuper.angelgroup.widget.pw;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.asuper.angelgroup.R;


/**
 * 二级导航窗口
 *
 * @author super
 */
public class NaviShopPopWin extends PopupWindow {

    private View toolView, area_meinfo,area_to_msg, area_to_about, area_to_setting;

    public NaviShopPopWin(Context context) {
        super(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        toolView = inflater.inflate(R.layout.pw_navi_shop_layout, null);
        area_meinfo = toolView.findViewById(R.id.area_meinfo);
        area_to_msg = toolView.findViewById(R.id.area_to_msg);
        area_to_about = toolView.findViewById(R.id.area_to_about);
        area_to_setting = toolView.findViewById(R.id.area_to_setting);
        // 设置按钮监听
        // 设置SelectPicPopupWindow的View
        this.setContentView(toolView);
        // 设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(RelativeLayout.LayoutParams.WRAP_CONTENT);
        // 设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(RelativeLayout.LayoutParams.WRAP_CONTENT);
        // 设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        // 设置SelectPicPopupWindow弹出窗体的背景
        this.setBackgroundDrawable(null);
        // mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
        toolView.setOnTouchListener(new OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    dismiss();
                }
                return true;
            }
        });
    }

    public View getAreaMeInfo(){
        return area_meinfo;
    }

    public View getAreaToMsg(){
        return area_to_msg;
    }

    public View getAreaToAbout(){
        return area_to_about;
    }

    public View getAreaToSetting(){
        return area_to_setting;
    }
}
