package com.asuper.angelgroup.moduel.login;

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
import com.asuper.angelgroup.moduel.login.bean.RelationItem;

import java.util.ArrayList;
import java.util.List;

/**
 * 选择宝宝关系
 * Created by shubei on 2017/11/17.
 */

public class ChooseBabyRelativeActivity extends BaseActivity {

    private RelationListAdapter adapter;
    private int selectedPos = -1;

    private ListView lv_relation;
    private String getReName;

    @Override
    public void setLayout() {
        setContentView(R.layout.activity_choose_baby_relative);
    }

    @Override
    public void initView() {
        setPagTitle("与宝贝关系");
        lv_relation = findViewById(R.id.lv_relation);
    }

    @Override
    public void initData() {
        getReName = getIntent().getStringExtra("relativeName");

        for (int i = 0; i < GlobalParam.relationList.size(); i++) {
            if (GlobalParam.relationList.get(i).getRelationName().equals(getReName)) {
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
                    showShortToast("未选择关系！");
                    return;
                }
                Intent backData = new Intent();
                backData.putExtra("relativeName", GlobalParam.relationList.get(selectedPos).getRelationName());
                setResult(RESULT_OK, backData);
                finish();
            }
        });
    }

    private class RelationListAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return GlobalParam.relationList.size();
        }

        @Override
        public RelationItem getItem(int position) {
            return GlobalParam.relationList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(mContext).inflate(R.layout.relation_list_item, null);
            }
            TextView rname = (TextView) convertView.findViewById(R.id.tv_relation_name);
            ImageView img_choosse = (ImageView) convertView.findViewById(R.id.img_choosse);
            View bottom_line = convertView.findViewById(R.id.bottom_line);
            if (position == GlobalParam.relationList.size() - 1)
                bottom_line.setVisibility(View.GONE);
            else
                bottom_line.setVisibility(View.VISIBLE);
            if (position == selectedPos)
                img_choosse.setVisibility(View.VISIBLE);
            else
                img_choosse.setVisibility(View.INVISIBLE);
            rname.setText(getItem(position).getRelationName());
            return convertView;
        }
    }
}
