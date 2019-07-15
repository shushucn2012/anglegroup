package com.asuper.angelgroup.moduel.book.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.asuper.angelgroup.R;
import com.asuper.angelgroup.common.tool.ImageManager;
import com.asuper.angelgroup.moduel.book.bean.AddRequestBean;

import java.util.List;

/**
 * Created by shubei on 2017/7/16.
 */

public class FocusListAdapter extends BaseAdapter {

    private List<AddRequestBean> mList;
    private LayoutInflater factory;

    public FocusListAdapter(Context mContext, List<AddRequestBean> mList) {
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
            convertView = factory.inflate(R.layout.focus_list_item, null);
            holder = new ViewHolder();
            holder.tv_user_name = (TextView) convertView.findViewById(R.id.tv_user_name);
            holder.tv_unit = (TextView) convertView.findViewById(R.id.tv_unit);
            holder.img_user_head = (ImageView) convertView.findViewById(R.id.img_user_head);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        AddRequestBean addRequestBean = mList.get(position);
        ImageManager.getInstance().displayImg(holder.img_user_head, addRequestBean.getPictureUrl());
        holder.tv_user_name.setText(addRequestBean.getPetName());
        if (TextUtils.isEmpty(addRequestBean.getChildName())) {
            holder.tv_unit.setText(addRequestBean.getPositionalTitle());
        } else {
            holder.tv_unit.setText(addRequestBean.getChildName() + addRequestBean.getRelationName() + "·" + addRequestBean.getChildMalady() + "·" + addRequestBean.getChildAge() + "·" + addRequestBean.getCityName());
        }
        return convertView;
    }

    class ViewHolder {
        TextView tv_user_name, tv_unit, tv_add, tv_tip;
        ImageView img_user_head;
    }

}
