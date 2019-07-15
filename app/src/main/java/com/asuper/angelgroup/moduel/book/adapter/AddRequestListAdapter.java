package com.asuper.angelgroup.moduel.book.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.asuper.angelgroup.R;
import com.asuper.angelgroup.common.set.AppUrl;
import com.asuper.angelgroup.common.set.GlobalParam;
import com.asuper.angelgroup.common.tool.ImageManager;
import com.asuper.angelgroup.moduel.book.bean.AddRequestBean;

import java.util.List;

/**
 * Created by shubei on 2017/7/16.
 */

public class AddRequestListAdapter extends BaseAdapter {

    private List<AddRequestBean> mList;
    private LayoutInflater factory;
    private OnAddClickedLsner mOnAddClickedLsner;

    public AddRequestListAdapter(Context mContext, List<AddRequestBean> mList) {
        this.mList = mList;
        factory = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = factory.inflate(R.layout.addrequest_list_item, null);
            holder = new ViewHolder();
            holder.tv_user_name = (TextView) convertView.findViewById(R.id.tv_user_name);
            holder.tv_unit = (TextView) convertView.findViewById(R.id.tv_unit);
            holder.tv_add = (TextView) convertView.findViewById(R.id.tv_add);
            holder.tv_tip = (TextView) convertView.findViewById(R.id.tv_tip);
            holder.img_user_head = (ImageView) convertView.findViewById(R.id.img_user_head);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        AddRequestBean addRequestBean = mList.get(position);
        ImageManager.getInstance().displayImg(holder.img_user_head, addRequestBean.getPictureUrl());
        holder.tv_user_name.setText(addRequestBean.getPetName());
        holder.tv_unit.setText(addRequestBean.getRemark());
        if (addRequestBean.getStatus().equals("等待同意") && addRequestBean.getType() == 1) {//1:等待己方验证
            holder.tv_tip.setVisibility(View.GONE);
            holder.tv_add.setVisibility(View.VISIBLE);
        } else if (addRequestBean.getStatus().equals("等待同意") && addRequestBean.getType() == 0) {//0:等待对方验证
            holder.tv_tip.setVisibility(View.VISIBLE);
            holder.tv_add.setVisibility(View.GONE);
            holder.tv_tip.setText("等待对方验证");
        } else {
            holder.tv_tip.setVisibility(View.VISIBLE);
            holder.tv_add.setVisibility(View.GONE);
            holder.tv_tip.setText("已添加");
        }
        holder.tv_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnAddClickedLsner != null) {
                    mOnAddClickedLsner.onCliked(position);
                }
            }
        });
        return convertView;
    }

    class ViewHolder {
        TextView tv_user_name, tv_unit, tv_add, tv_tip;
        ImageView img_user_head;
    }

    public interface OnAddClickedLsner {
        void onCliked(int postion);
    }

    public void setOnAddClickedLsner(OnAddClickedLsner lsner) {
        mOnAddClickedLsner = lsner;
    }
}
