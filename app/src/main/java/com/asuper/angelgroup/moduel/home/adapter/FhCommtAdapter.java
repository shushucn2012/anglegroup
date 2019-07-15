package com.asuper.angelgroup.moduel.home.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.asuper.angelgroup.R;
import com.asuper.angelgroup.common.set.Log;
import com.asuper.angelgroup.moduel.book.OthersMainInfoActivity;
import com.asuper.angelgroup.moduel.me.adapter.PublishListAdapter;
import com.asuper.angelgroup.moduel.me.bean.PublishItemBean;

import java.util.List;

/**
 * Created by shubei on 2017/6/12.
 */

public class FhCommtAdapter extends BaseAdapter {

    private List<PublishItemBean.ItemCommentListBean> mList;
    private Context mContext;
    private LayoutInflater factory;
    private PublishListAdapter.OnRecordDoneListener mOnRecordDoneListener;
    private int curPos;

    public FhCommtAdapter(Context _context, List<PublishItemBean.ItemCommentListBean> _list, PublishListAdapter.OnRecordDoneListener mOnRecordDoneListener, int index) {
        this.mList = _list;
        this.mContext = _context;
        factory = LayoutInflater.from(mContext);
        this.mOnRecordDoneListener = mOnRecordDoneListener;
        this.curPos = index;
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
            convertView = factory.inflate(R.layout.fh_commt_list_item, null);
            holder = new ViewHolder();
//            holder.tv_user_name = (TextView) convertView.findViewById(R.id.tv_user_name);
            holder.tv_comt_content = (TextView) convertView.findViewById(R.id.tv_comt_content);
//            holder.tv_repaly_name = (TextView) convertView.findViewById(R.id.tv_repaly_name);
//            holder.area_repaly = convertView.findViewById(R.id.area_repaly);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tv_comt_content.setMovementMethod(LinkMovementMethod.getInstance());//不设置 没有点击事件
        final PublishItemBean.ItemCommentListBean item = mList.get(position);
        if (TextUtils.isEmpty(item.getParentUserName())) { // 评论
            //holder.tv_user_name.setText(item.getUserName() + "：");
            holder.tv_comt_content.setText(getClickableSpan(item, position));
            //holder.area_repaly.setVisibility(View.GONE);
        } else { // 回复
            //holder.tv_user_name.setText(item.getUserName());
            //holder.tv_repaly_name.setText(item.getParentUserName() + "：");
            holder.tv_comt_content.setText(getClickableSpan(item, position));
            //holder.area_repaly.setVisibility(View.VISIBLE);
        }
        /*holder.tv_comt_content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnRecordDoneListener.onRepalyClicked(curPos, position);
            }
        });*/
        return convertView;
    }


    class ViewHolder {
        TextView tv_comt_content, tv_user_name, tv_repaly_name;
        View area_repaly;
    }

    private SpannableString getClickableSpan(final PublishItemBean.ItemCommentListBean itemCommentListBean, final int position) {
        View.OnClickListener slistener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(mContext, OthersMainInfoActivity.class);
                it.putExtra("OTHER_NAME", itemCommentListBean.getUserName());
                it.putExtra("userId", itemCommentListBean.getUserId());
                mContext.startActivity(it);
            }
        };
        View.OnClickListener plistener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(mContext, OthersMainInfoActivity.class);
                it.putExtra("OTHER_NAME", itemCommentListBean.getParentUserName());
                it.putExtra("userId", itemCommentListBean.getParentUserId());
                mContext.startActivity(it);
            }
        };
        View.OnClickListener clistener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnRecordDoneListener.onRepalyClicked(curPos, position);
            }
        };
        SpannableString spanableInfo = null;
        if (TextUtils.isEmpty(itemCommentListBean.getParentUserName())) { // 评论
            spanableInfo = new SpannableString(itemCommentListBean.getUserName() + "：" + itemCommentListBean.getContent());
            int start = 0;
            int end = itemCommentListBean.getUserName().length();
            spanableInfo.setSpan(new Clickable(slistener, true), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            spanableInfo.setSpan(new Clickable(clistener, false), end, spanableInfo.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        } else {
            spanableInfo = new SpannableString(itemCommentListBean.getUserName() + " 回复 " + itemCommentListBean.getParentUserName() + "：" + itemCommentListBean.getContent());
            int start = 0;
            int end = itemCommentListBean.getUserName().length();
            spanableInfo.setSpan(new Clickable(slistener, true), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            spanableInfo.setSpan(new Clickable(plistener, true), end + 4, end + 4 + itemCommentListBean.getParentUserName().length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            spanableInfo.setSpan(new Clickable(clistener, false), end + 4 + itemCommentListBean.getParentUserName().length(), spanableInfo.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return spanableInfo;
    }

    /**
     * 内部类，用于截获点击富文本后的事件
     */
    class Clickable extends ClickableSpan {
        private final View.OnClickListener mListener;
        private boolean isChangeColor;

        public Clickable(View.OnClickListener mListener, boolean isChangeColor) {
            this.mListener = mListener;
            this.isChangeColor = isChangeColor;
        }

        @Override
        public void onClick(View view) {
            mListener.onClick(view);
        }

        @Override
        public void updateDrawState(TextPaint ds) {
            if (isChangeColor)
                ds.setColor(mContext.getResources().getColor(R.color.g646287));
            else
                ds.setColor(mContext.getResources().getColor(R.color.g202020));
            ds.setUnderlineText(false);    //去除超链接的下划线
        }
    }

}
