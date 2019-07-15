package com.asuper.angelgroup.moduel.login.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.asuper.angelgroup.R;
import com.asuper.angelgroup.common.tool.DevAttr;
import com.asuper.angelgroup.moduel.login.bean.LabelBean;
import com.asuper.angelgroup.moduel.login.bean.RolesBean;

import java.util.List;

public class DreamFlagGvAdapter extends BaseAdapter {

    private List<RolesBean.LabelsBean> mList;
    private Context mContext;
    private int selectedPos = -1;

    public DreamFlagGvAdapter(List<RolesBean.LabelsBean> _list, Context _context) {
        this.mList = _list;
        this.mContext = _context;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public RolesBean.LabelsBean getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.gridview_role_item, null);
        }
        TextView tv_child_name = (TextView) convertView.findViewById(R.id.tv_child_name);
        tv_child_name.setText(mList.get(position).getLableName());
        ViewGroup.LayoutParams layoutParams = tv_child_name.getLayoutParams();
        layoutParams.height = DevAttr.dip2px(mContext, 36);
        if (mList.get(position).getIsCheck().equals("1")) {
            tv_child_name.setBackgroundResource(R.drawable.rec_greed_stroke_greed_solid);
            tv_child_name.setTextColor(mContext.getResources().getColor(R.color.com_white));
        } else {
            tv_child_name.setBackgroundResource(R.drawable.rec_gray_stroke_trans_solid);
            tv_child_name.setTextColor(mContext.getResources().getColor(R.color.gc0c0c0));
        }
        return convertView;
    }
}
