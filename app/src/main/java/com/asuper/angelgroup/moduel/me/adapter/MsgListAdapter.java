package com.asuper.angelgroup.moduel.me.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.asuper.angelgroup.R;
import com.asuper.angelgroup.common.tool.DateTool;
import com.asuper.angelgroup.common.tool.ImageManager;
import com.asuper.angelgroup.moduel.me.bean.MsgItem;

import java.util.Date;
import java.util.List;

public class MsgListAdapter extends BaseAdapter {

    private List<MsgItem> mList;
    private Context mContext;
    private LayoutInflater factory;

    public MsgListAdapter(Context _context, List<MsgItem> _list) {
        this.mList = _list;
        this.mContext = _context;
        factory = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public MsgItem getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = factory.inflate(R.layout.msglist_item, null);
            holder = new ViewHolder();
            holder.img_user = (ImageView) convertView.findViewById(R.id.img_user);
            holder.img_action = (ImageView) convertView.findViewById(R.id.img_action);
            holder.tv_username = (TextView) convertView.findViewById(R.id.tv_username);
            holder.tv_content = (TextView) convertView.findViewById(R.id.tv_content);
            holder.tv_time = (TextView) convertView.findViewById(R.id.tv_time);
            holder.img_first = (ImageView) convertView.findViewById(R.id.img_first);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        MsgItem mi = mList.get(position);
        holder.tv_username.setText(mi.getSenderName());
        ImageManager.getInstance().displayImg(holder.img_user, mi.getSenderPicture());
        if (mi.getType() == 1) {//转发
            holder.img_action.setVisibility(View.VISIBLE);
            holder.img_action.setImageResource(R.drawable.icon_retweet);
            holder.tv_content.setVisibility(View.GONE);
        } else if (mi.getType() == 3) {
            holder.img_action.setVisibility(View.VISIBLE);
            holder.img_action.setImageResource(R.mipmap.icon_like);
            holder.tv_content.setVisibility(View.GONE);
        } else if (mi.getType() == 2) {
            holder.img_action.setVisibility(View.GONE);
            holder.tv_content.setVisibility(View.VISIBLE);
            if (mi.getCommentVO().getContent().length() <= 38) {
                holder.tv_content.setText(mi.getCommentVO().getContent());
            } else {
                holder.tv_content.setText(mi.getCommentVO().getContent().substring(0, 38) + "...");
            }
        }
        if (!TextUtils.isEmpty(mi.getTitle()) && mi.getTitle().contains("http:")) {
            holder.img_first.setVisibility(View.VISIBLE);
            ImageManager.getInstance().displayImg(holder.img_first, mi.getTitle());
        } else {
            holder.img_first.setVisibility(View.GONE);
        }
        holder.tv_time.setText(toPDateStr(System.currentTimeMillis() + "", mi.getTime()));
        View bottom_line = convertView.findViewById(R.id.bottom_line);
        if (position == getCount() - 1) {
            bottom_line.setVisibility(View.GONE);
        } else {
            bottom_line.setVisibility(View.VISIBLE);
        }
        return convertView;
    }

    class ViewHolder {
        ImageView img_user, img_action, img_first;
        TextView tv_username;
        TextView tv_content;
        TextView tv_time;
    }

    /**
     * 时间个性化转换
     *
     * @param currDate   当前时间
     * @param covertDate 发布时间
     * @return 转化后的字符串
     */
    @SuppressWarnings("deprecation")
    public String toPDateStr(String currDate, String covertDate) {
        int d_minutes, d_hours, d_days;
        long timeNow = Long.parseLong(currDate); // 转换为服务器当前时间戳
        long currcovertDate = Long.parseLong(covertDate);
        long d;
        String result;
        d = (timeNow - currcovertDate) / 1000;
        d_days = (int) (d / 86400);
        d_hours = (int) (d / 3600);
        d_minutes = (int) (d / 60);
        if (d_days > 0 && d_days < 4) {
            result = d_days + "天前";
        } else if (d_days <= 0 && d_hours > 0) {
            result = d_hours + "小时前";
        } else if (d_hours <= 0 && d_minutes > 0) {
            result = d_minutes + "分钟前";
        } else if (d_minutes <= 0 && d >= 0) {
            return Math.round(d) + "秒前";
        } else {
            result = DateTool.L2S(covertDate);
        }
        return result;
    }

}
