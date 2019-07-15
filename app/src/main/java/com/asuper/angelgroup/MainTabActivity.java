package com.asuper.angelgroup;

import android.app.TabActivity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Looper;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.asuper.angelgroup.common.tool.ExitAppUtils;
import com.asuper.angelgroup.moduel.book.BookActivity;
import com.asuper.angelgroup.moduel.find.FindActivity;
import com.asuper.angelgroup.moduel.home.ChoosePublishTypeActivity;
import com.asuper.angelgroup.moduel.home.HomeActivity;
import com.asuper.angelgroup.moduel.me.MeActivity;

/**
 * 主界面
 * create by super
 */
@SuppressWarnings("deprecation")
public class MainTabActivity extends TabActivity implements OnCheckedChangeListener {
    private Context mContext;
    private TabHost tabHost;
    private RadioButton radio_button_home;
    private RadioButton radio_button_book;
    private RadioButton radio_button_find;
    private RadioButton radio_button_me;

    private Intent homeIntent;
    private Intent bookIntent;
    private Intent findIntent;
    private Intent meIntent;// 我的
    private long _firstTime = 0;
    private ImageView img_add;

    @Override
    public Resources getResources() {
        Resources res = super.getResources();
        Configuration config = new Configuration();
        config.setToDefaults();
        res.updateConfiguration(config, res.getDisplayMetrics());
        return res;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_tab_layout);
        // 判断当前SDK版本号，如果是4.4以上，就是支持沉浸式状态栏的
        /*if (VERSION.SDK_INT >= VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }*/
        intView();
        setupIntent();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 注销-其他页面要改变当前tab页的广播
        mContext.unregisterReceiver(tabChangeReceiver);
    }

    private void intView() {
        mContext = this;
        // 注册-其他页面要改变当前tab页的广播
        registerTabChangeReceiver();
        radio_button_home = (RadioButton) findViewById(R.id.radio_button_home);
        radio_button_book = (RadioButton) findViewById(R.id.radio_button_book);
        radio_button_find = (RadioButton) findViewById(R.id.radio_button_find);
        radio_button_me = (RadioButton) findViewById(R.id.radio_button_me);

        homeIntent = new Intent(this, HomeActivity.class);
        bookIntent = new Intent(this, BookActivity.class);
        findIntent = new Intent(this, FindActivity.class);
        meIntent = new Intent(this, MeActivity.class);

        radio_button_home.setOnCheckedChangeListener(this);
        radio_button_book.setOnCheckedChangeListener(this);
        radio_button_find.setOnCheckedChangeListener(this);
        radio_button_me.setOnCheckedChangeListener(this);

        findViewById(R.id.img_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(mContext, ChoosePublishTypeActivity.class));
            }
        });
    }

    private void setupIntent() {
        this.tabHost = getTabHost();
        setupSettingTab("家园", "tab_home", homeIntent);
        setupSettingTab("通讯录", "tab_book", bookIntent);
        setupSettingTab("发现", "tab_find", findIntent);
        setupSettingTab("我的", "tab_me", meIntent);
    }

    private void setupSettingTab(String str, String tag, Intent intent) {
        tabHost.addTab(tabHost.newTabSpec(tag).setIndicator(getView(str)).setContent(intent));
    }

    private View getView(String str) {
        View view = LayoutInflater.from(this).inflate(R.layout.tab_item_layout, null);
        TextView tab_item_name = (TextView) view.findViewById(R.id.tab_item_name);
        tab_item_name.setText(str);
        return view;
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            switch (buttonView.getId()) {
                case R.id.radio_button_home:
                    tabHost.setCurrentTabByTag("tab_home");
                    break;
                case R.id.radio_button_book:
                    tabHost.setCurrentTabByTag("tab_book");
                    break;
                case R.id.radio_button_find:
                    tabHost.setCurrentTabByTag("tab_find");
                    break;
                case R.id.radio_button_me:
                    tabHost.setCurrentTabByTag("tab_me");
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * 其他页面要改变当前tab页的广播
     */
    private void registerTabChangeReceiver() {
        IntentFilter filter = new IntentFilter();
        filter.addAction("ACTION_TAB_CHANGE");
        mContext.registerReceiver(tabChangeReceiver, filter);
    }

    private BroadcastReceiver tabChangeReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            // 接收到广播，改变当前tab页
            String tab_name = intent.getStringExtra("TAB_NAME");
            if (tab_name != null && !tab_name.equals("")) {
                if ("tab_home".equals(tab_name)) {
                    radio_button_home.setChecked(true);
                } else if ("tab_book".equals(tab_name)) {
                    radio_button_book.setChecked(true);
                } else if ("tab_find".equals(tab_name)) {
                    radio_button_find.setChecked(true);
                } else if ("tab_me".equals(tab_name)) {
                    radio_button_me.setChecked(true);
                }
            }
        }
    };

    /**
     * 两次退出程序
     */
    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
            long secondTime = System.currentTimeMillis();
            if (secondTime - _firstTime > 2000) {// 如果两次按键时间间隔大于2000毫秒，则不退出
                Toast.makeText(mContext, "再按一次退出程序...", Toast.LENGTH_SHORT).show();
                _firstTime = secondTime;// 更新firstTime
                return true;
            } else {
                ExitAppUtils.getInstance().exit();
            }
            return true;
        }
        return super.dispatchKeyEvent(event);
    }

}
