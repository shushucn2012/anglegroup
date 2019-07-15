package com.asuper.angelgroup.moduel.book.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.asuper.angelgroup.R;
import com.asuper.angelgroup.common.tool.ImageManager;
import com.asuper.angelgroup.moduel.book.OthersMainInfoActivity;
import com.asuper.angelgroup.moduel.book.bean.Contactors;
import com.asuper.angelgroup.moduel.book.bean.User;

import java.util.List;

/**
 * Created by wangsong on 2016/4/24.
 */
public class MyAdapter extends BaseAdapter implements SectionIndexer {
    private List<Contactors> list;
    private Context context;
    private LayoutInflater inflater;

    public MyAdapter(Context context, List<Contactors> list) {
        this.context = context;
        this.list = list;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Contactors getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.book_listview_item, null);
            holder = new ViewHolder();
            holder.showLetter = (TextView) convertView.findViewById(R.id.show_letter);
            holder.username = (TextView) convertView.findViewById(R.id.username);
            holder.area_content = convertView.findViewById(R.id.area_content);
            holder.userface = convertView.findViewById(R.id.userface);
            holder.user_qianming = convertView.findViewById(R.id.user_qianming);
            holder.bottom_line = convertView.findViewById(R.id.bottom_line);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final Contactors user = list.get(position);
        holder.username.setText(user.getPetName());
        ImageManager.getInstance().displayImg(holder.userface, user.getPictureUrl());
        if (TextUtils.isEmpty(user.getChildName())) {
            holder.user_qianming.setText(user.getPositionalTitle());
        } else {
            holder.user_qianming.setText(user.getChildName() + user.getRelationName() + "·" + user.getChildMalady() + "·" + user.getChildAge() + "·" + user.getCityName());
        }
        //获得当前position是属于哪个分组
        int sectionForPosition = getSectionForPosition(position);
        //获得该分组第一项的position
        int positionForSection = getPositionForSection(sectionForPosition);
        //查看当前position是不是当前item所在分组的第一个item
        //如果是，则显示showLetter，否则隐藏
        if (position == positionForSection) {
            holder.showLetter.setVisibility(View.VISIBLE);
            holder.showLetter.setText(user.getFirstLetter());
        } else {
            holder.showLetter.setVisibility(View.GONE);
        }
        if ((position > 1 && position == positionForSection - 1) || position == list.size() - 1) {
            holder.bottom_line.setVisibility(View.GONE);
        } else {
            holder.bottom_line.setVisibility(View.VISIBLE);
        }
        /*holder.area_content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(context, OthersMainInfoActivity.class);
                it.putExtra("OTHER_NAME", user.getPetName());
                it.putExtra("userId", user.getFriendUserId());
                context.startActivity(it);
            }
        });*/

        return convertView;
    }

    @Override
    public Object[] getSections() {
        return new Object[0];
    }

    //传入一个分组值[A....Z],获得该分组的第一项的position
    @Override
    public int getPositionForSection(int sectionIndex) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getFirstLetter().charAt(0) == sectionIndex) {
                return i;
            }
        }
        return -1;
    }

    //传入一个position，获得该position所在的分组
    @Override
    public int getSectionForPosition(int position) {
        return list.get(position).getFirstLetter().charAt(0);
    }

    class ViewHolder {
        TextView username, showLetter, user_qianming;
        View area_content, bottom_line;
        ImageView userface;
    }
}
