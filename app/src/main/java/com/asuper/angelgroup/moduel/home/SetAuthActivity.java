package com.asuper.angelgroup.moduel.home;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.asuper.angelgroup.BaseActivity;
import com.asuper.angelgroup.R;
import com.asuper.angelgroup.common.set.GlobalParam;
import com.asuper.angelgroup.moduel.login.ChooseBabyRelativeActivity;
import com.asuper.angelgroup.moduel.login.bean.RelationItem;

import java.util.ArrayList;
import java.util.List;

/**
 * 选择权限页
 * Created by shubei on 2017/12/8.
 */

public class SetAuthActivity extends BaseActivity {

    private RelationListAdapter adapter;
    private int selectedPos = -1;

    private ListView lv_relation;
    private String getReName;
    private List<String> mlist = new ArrayList<>();
    private List<String> slist = new ArrayList<>();

    @Override
    public void setLayout() {
        setContentView(R.layout.activity_choose_baby_relative);
    }

    @Override
    public void initView() {
        setPagTitle("发表动态");
        lv_relation = findViewById(R.id.lv_relation);
        ((TextView) findViewById(R.id.tv_right)).setTextColor(mContext.getResources().getColor(R.color.main_green));
        ((TextView) findViewById(R.id.tv_right)).setText("完成");
    }

    @Override
    public void initData() {
        getReName = getIntent().getStringExtra("relativeName");

        mlist.add("公开");
        mlist.add("好友圈");
        mlist.add("日记");

        slist.add("所有用户可见");
        slist.add("仅好友可见");
        slist.add("仅自己可见");

        for (int i = 0; i < mlist.size(); i++) {
            if (mlist.get(i).equals(getReName)) {
                selectedPos = i;
            }
        }

        adapter = new RelationListAdapter();
        lv_relation.setAdapter(adapter);
    }

    @Override
    public void initListener() {
        lv_relation.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedPos = position;
                adapter.notifyDataSetChanged();
            }
        });
        area_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectedPos == -1) {
                    showShortToast("未选择！");
                    return;
                }
                Intent backData = new Intent();
                backData.putExtra("relativeName", mlist.get(selectedPos));
                setResult(RESULT_OK, backData);
                finish();
            }
        });
    }

    private class RelationListAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mlist.size();
        }

        @Override
        public String getItem(int position) {
            return mlist.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(mContext).inflate(R.layout.auth_list_item, null);
            }
            TextView rname = (TextView) convertView.findViewById(R.id.tv_relation_name);
            TextView tv_descr = convertView.findViewById(R.id.tv_descr);
            ImageView img_choosse = (ImageView) convertView.findViewById(R.id.img_choosse);
            View bottom_line = convertView.findViewById(R.id.bottom_line);
            if (position == mlist.size() - 1)
                bottom_line.setVisibility(View.GONE);
            else
                bottom_line.setVisibility(View.VISIBLE);
            if (position == selectedPos)
                img_choosse.setImageResource(R.mipmap.icon_select_active);
            else
                img_choosse.setImageResource(R.mipmap.icon_select_normal);
            rname.setText(getItem(position));
            tv_descr.setText(slist.get(position));
            return convertView;
        }
    }
}
