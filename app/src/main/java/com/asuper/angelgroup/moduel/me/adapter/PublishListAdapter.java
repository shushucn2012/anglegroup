package com.asuper.angelgroup.moduel.me.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.Layout;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.asuper.angelgroup.R;
import com.asuper.angelgroup.common.set.GlobalParam;
import com.asuper.angelgroup.common.set.Log;
import com.asuper.angelgroup.common.tool.ComUtils;
import com.asuper.angelgroup.common.tool.FU;
import com.asuper.angelgroup.common.tool.ImageManager;
import com.asuper.angelgroup.moduel.book.OthersMainInfoActivity;
import com.asuper.angelgroup.moduel.home.VideoDetailsActivity;
import com.asuper.angelgroup.moduel.home.adapter.FhCommtAdapter;
import com.asuper.angelgroup.moduel.me.ShowBigPicActivity;
import com.asuper.angelgroup.moduel.me.TurnOutActivity;
import com.asuper.angelgroup.moduel.me.bean.PublishItemBean;
import com.asuper.angelgroup.moduel.me.bean.PublishItemOpenLevel;
import com.asuper.angelgroup.moduel.me.bean.ReturnBean;
import com.asuper.angelgroup.widget.gridview.GridViewForScrollView;
import com.asuper.angelgroup.widget.list.ListViewForScrollView;
import com.ms.square.android.expandabletextview.ExpandableTextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by HP on 2017/3/7.
 */
public class PublishListAdapter extends BaseAdapter {

    private static final int LINES = 3;
    private Context mContext;
    private LayoutInflater factory;
    private List<PublishItemBean> mList;
    private OnRecordDoneListener mOnRecordDoneListener;
    private boolean isOthers; //是否是个人或他人主页 true 是主页， false 是家园

    private final SparseBooleanArray mCollapsedStatus;

    public PublishListAdapter(Context _context, List<PublishItemBean> _list, boolean isOthers) {
        this.mList = _list;
        this.mContext = _context;
        factory = LayoutInflater.from(mContext);
        this.isOthers = isOthers;
        mCollapsedStatus = new SparseBooleanArray();
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
    public View getView(final int position, View convertView, ViewGroup viewGroup) {
        final ViewHolder holder;
        if (convertView == null) {
            convertView = factory.inflate(R.layout.publish_item_photos, null);
            holder = new ViewHolder();
            holder.area_root = convertView.findViewById(R.id.area_root);
            holder.img_user_head = (ImageView) convertView.findViewById(R.id.img_user_head);
            holder.tv_user_name = (TextView) convertView.findViewById(R.id.tv_user_name);
            holder.tv_intro = (TextView) convertView.findViewById(R.id.tv_intro);

            holder.expandableTextView = convertView.findViewById(R.id.expand_text_view);
            //holder.praise_expand_text_view = convertView.findViewById(R.id.praise_expand_text_view);
            holder.expandable_text = (TextView) convertView.findViewById(R.id.expandable_text);
            holder.expandable_text.setMovementMethod(LinkMovementMethod.getInstance());//不设置 没有点击事件
            holder.img_type = convertView.findViewById(R.id.img_type);
            holder.tv_type = (TextView) convertView.findViewById(R.id.tv_type);
            holder.gv_pics = convertView.findViewById(R.id.gv_pics);
            holder.tv_time = convertView.findViewById(R.id.tv_time);
            holder.tv_delete = convertView.findViewById(R.id.tv_delete);
            holder.img_dothings = convertView.findViewById(R.id.img_dothings);
            holder.area_dothings = convertView.findViewById(R.id.area_dothings);
            holder.area_praise = convertView.findViewById(R.id.area_praise);
            holder.area_comment = convertView.findViewById(R.id.area_comment);
            holder.area_turn = convertView.findViewById(R.id.area_turn);
            holder.area_praise_content = convertView.findViewById(R.id.area_praise_content);
            holder.tv_praise_names = convertView.findViewById(R.id.tv_praise_names);
            holder.tv_praise_names.setMovementMethod(LinkMovementMethod.getInstance());//不设置 没有点击事件
            holder.btn_ss_praise = convertView.findViewById(R.id.btn_ss_praise);
            holder.btn_ss_turnout = convertView.findViewById(R.id.btn_ss_turnout);
            holder.tv_praise_label = convertView.findViewById(R.id.tv_praise_label);
            holder.lv_comment = convertView.findViewById(R.id.lv_comment);
            holder.tv_expand_cmt = convertView.findViewById(R.id.tv_expand_cmt);
            holder.tv_small_cmt = convertView.findViewById(R.id.tv_small_cmt);
            holder.area_turn_content = convertView.findViewById(R.id.area_turn_content);
            holder.tv_turn_names = convertView.findViewById(R.id.tv_turn_names);
            holder.tv_turn_names.setMovementMethod(LinkMovementMethod.getInstance());//不设置 没有点击事件
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final PublishItemBean itemBean = mList.get(position);
        ImageManager.getInstance().displayImg(holder.img_user_head, itemBean.getUserInfo().getPhoto());
        holder.tv_user_name.setText(itemBean.getUserInfo().getUserName());

        if (TextUtils.isEmpty(itemBean.getReturnId())) {
            //holder.tv_content.setText(itemBean.getSummray());
            holder.expandableTextView.setText(itemBean.getSummray(), mCollapsedStatus, position);
        } else {
            //holder.tv_content.setText(getReturnContentClickableSpan(itemBean));
            holder.expandableTextView.setText(getReturnContentClickableSpan(itemBean), mCollapsedStatus, position);
        }

        holder.tv_time.setText(itemBean.getDateTag());

        if (FU.paseInt(itemBean.getAuthority()) == PublishItemOpenLevel.LEVEL_FRIENDS) {
            holder.img_type.setImageResource(R.mipmap.icon_secret);
            holder.tv_type.setText("好友圈");
            holder.tv_type.setTextColor(Color.parseColor("#faa034"));

            holder.area_turn.setVisibility(View.GONE);

        } else if (FU.paseInt(itemBean.getAuthority()) == PublishItemOpenLevel.LEVEL_DIARY) {
            holder.img_type.setImageResource(R.mipmap.icon_diary);
            holder.tv_type.setText("日记");
            holder.tv_type.setTextColor(Color.parseColor("#1fcf7c"));

            holder.area_turn.setVisibility(View.GONE);

        } else if (FU.paseInt(itemBean.getAuthority()) == PublishItemOpenLevel.LEVEL_OPEN && !isUserInTurnList(itemBean.getReturnList())) {
            holder.img_type.setImageResource(R.mipmap.icon_open);
            holder.tv_type.setText("公开");
            holder.tv_type.setTextColor(Color.parseColor("#70a8ee"));

            holder.area_turn.setVisibility(View.VISIBLE);
        } else {
            holder.img_type.setImageResource(R.mipmap.icon_open);
            holder.tv_type.setText("公开");
            holder.tv_type.setTextColor(Color.parseColor("#70a8ee"));

            holder.area_turn.setVisibility(View.GONE);
        }

        if (TextUtils.isEmpty(itemBean.getChildName())) {
            holder.tv_intro.setText(itemBean.getPositionalTitle());
        } else {
            holder.tv_intro.setText(itemBean.getChildName() + itemBean.getRelationName() + "·" + itemBean.getChildMalady() + "·" + itemBean.getChildAge() + "·" + itemBean.getCityName());
        }

        if (isOthers) {
            holder.tv_intro.setVisibility(View.GONE);
        } else {
            holder.tv_intro.setVisibility(View.VISIBLE);
        }

        if (!ComUtils.isListEmpty(itemBean.getReturnList())) {
            holder.area_turn_content.setVisibility(View.VISIBLE);
            holder.tv_turn_names.setText(getTurnOutClickableSpan(itemBean.getReturnList()));
        } else {
            holder.area_turn_content.setVisibility(View.GONE);
        }

        //先将文字的状态保存
        if (itemBean.getHasEllipsis2() == null) {
            //如果textView.getLayout()为空，待TextView渲染结束后重新获取Layout对象。
            holder.tv_turn_names.post(new Runnable() {
                @Override
                public void run() {
                    Layout tvlayout = holder.tv_turn_names.getLayout();
                    if (tvlayout != null) {
                        int ellipsisCount = tvlayout.getEllipsisCount(holder.tv_turn_names.getLineCount() - 1);
                        //是否超出范围:如果行数大于3或者而且ellipsisCount>0超出范围，会显示省略号。
                        if (itemBean.getHasEllipsis2() == null) {
                            if (!(holder.tv_turn_names.getLineCount() <= LINES && ellipsisCount == 0))
                                itemBean.setHasEllipsis2(true);
                            else
                                itemBean.setHasEllipsis2(false);
                        }
                        //如果文字没有超出范围，则隐藏按钮。
                        holder.btn_ss_turnout.setVisibility(itemBean.getHasEllipsis2() ? View.VISIBLE : View.GONE);
                        //文字是否全部展示。
                        itemBean.setShowAll2(ellipsisCount > 0);
                        setTextViewLines(holder.tv_turn_names, holder.btn_ss_turnout, !itemBean.getHasEllipsis2() || !itemBean.isShowAll2());
                    }
                }
            });
        } else {
            holder.btn_ss_turnout.setVisibility(itemBean.getHasEllipsis2() ? View.VISIBLE : View.GONE);
            setTextViewLines(holder.tv_turn_names, holder.btn_ss_turnout, !itemBean.getHasEllipsis2() || !itemBean.isShowAll2());
        }
        holder.btn_ss_turnout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemBean.setShowAll2(!itemBean.isShowAll2());
                //notifyItemChanged(position);
                //这样直接设置展示、收起也可以。
                setTextViewLines(holder.tv_turn_names, holder.btn_ss_turnout, !itemBean.getHasEllipsis2() || !itemBean.isShowAll2());
            }
        });

        if (!ComUtils.isListEmpty(itemBean.getPraiseList())) {
            holder.area_praise_content.setVisibility(View.VISIBLE);
            holder.tv_praise_names.setText(getPraiseClickableSpan(itemBean.getPraiseList()));
            if (isUserInPraiseList(itemBean.getUserInfo().getUserId(), itemBean.getPraiseList())) {
                holder.tv_praise_label.setText("取消");
            } else {
                holder.tv_praise_label.setText("赞");
            }
        } else {
            holder.area_praise_content.setVisibility(View.GONE);
            holder.tv_praise_names.setText("");
            holder.tv_praise_label.setText("赞");
        }
        //先将文字的状态保存
        if (itemBean.getHasEllipsis() == null) {
            //如果textView.getLayout()为空，待TextView渲染结束后重新获取Layout对象。
            holder.tv_praise_names.post(new Runnable() {
                @Override
                public void run() {
                    Layout tvlayout = holder.tv_praise_names.getLayout();
                    if (tvlayout != null) {
                        int ellipsisCount = tvlayout.getEllipsisCount(holder.tv_praise_names.getLineCount() - 1);
                        //是否超出范围:如果行数大于3或者而且ellipsisCount>0超出范围，会显示省略号。
                        if (itemBean.getHasEllipsis() == null) {
                            if (!(holder.tv_praise_names.getLineCount() <= LINES && ellipsisCount == 0))
                                itemBean.setHasEllipsis(true);
                            else
                                itemBean.setHasEllipsis(false);
                        }
                        //如果文字没有超出范围，则隐藏按钮。
                        holder.btn_ss_praise.setVisibility(itemBean.getHasEllipsis() ? View.VISIBLE : View.GONE);
                        //文字是否全部展示。
                        itemBean.setShowAll(ellipsisCount > 0);
                        setTextViewLines(holder.tv_praise_names, holder.btn_ss_praise, !itemBean.getHasEllipsis() || !itemBean.isShowAll());
                    }
                }
            });
        } else {
            holder.btn_ss_praise.setVisibility(itemBean.getHasEllipsis() ? View.VISIBLE : View.GONE);
            setTextViewLines(holder.tv_praise_names, holder.btn_ss_praise, !itemBean.getHasEllipsis() || !itemBean.isShowAll());
        }
        holder.btn_ss_praise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemBean.setShowAll(!itemBean.isShowAll());
                //notifyItemChanged(position);
                //这样直接设置展示、收起也可以。
                setTextViewLines(holder.tv_praise_names, holder.btn_ss_praise, !itemBean.getHasEllipsis() || !itemBean.isShowAll());
            }
        });

        if (itemBean.getUserInfo().getUserId() == GlobalParam.currentUser.getId() && isOthers) {
            holder.tv_delete.setVisibility(View.VISIBLE);
        } else {
            holder.tv_delete.setVisibility(View.GONE);
        }

        if (itemBean.isDongThingAreaShowing()) {//如果这个属性为真，则显示操作框，否则隐藏
            holder.area_dothings.setVisibility(View.VISIBLE);
        } else {
            holder.area_dothings.setVisibility(View.GONE);
        }
        holder.img_dothings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //点击操作框，如果操作框显示就隐藏，如果隐藏就显示，其他所有项的框都隐藏
                if (itemBean.isDongThingAreaShowing()) {
                    itemBean.setDongThingAreaShowing(false);
                } else {
                    itemBean.setDongThingAreaShowing(true);
                }
                for (int k = 0; k < mList.size(); k++) {
                    if (k != position) {
                        mList.get(k).setDongThingAreaShowing(false);
                    }
                }
                notifyDataSetChanged();
            }
        });
        holder.area_root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //点击任意区域，所有项的框都隐藏
                for (int k = 0; k < mList.size(); k++) {
                    mList.get(k).setDongThingAreaShowing(false);
                }
                notifyDataSetChanged();
            }
        });

        holder.tv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnRecordDoneListener.onDeleteClicked(position);
            }
        });
        holder.area_praise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.area_dothings.setVisibility(View.GONE);
                itemBean.setDongThingAreaShowing(false);
                if (holder.tv_praise_label.getText().toString().equals("取消")) {
                    mOnRecordDoneListener.onDelPraiseClicked(position, getPraiseItemPosOfUser(itemBean.getPraiseList()));
                } else {
                    mOnRecordDoneListener.onPraiseClicked(position);
                }
            }
        });
        holder.area_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.area_dothings.setVisibility(View.GONE);
                itemBean.setDongThingAreaShowing(false);
                mOnRecordDoneListener.onCommentClicked(position);
            }
        });

        holder.area_turn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.area_dothings.setVisibility(View.GONE);
                itemBean.setDongThingAreaShowing(false);
                Intent it = new Intent(mContext, TurnOutActivity.class);
                it.putExtra("userPic", itemBean.getUserInfo().getPhoto());
                it.putExtra("userName", itemBean.getUserInfo().getUserName());
                it.putExtra("summray", itemBean.getSummray());
                it.putExtra("itemId", itemBean.getItemId());
                if (!ComUtils.isListEmpty(itemBean.getItemMediaList())) {
                    StringBuilder sb = new StringBuilder();
                    for (PublishItemBean.ItemMediaListBean media : itemBean.getItemMediaList()) {
                        sb.append(media.getMediaUrl());
                        sb.append(",");
                    }
                    it.putExtra("picUrls", sb.toString().substring(0, sb.length() - 1));
                }
                if (FU.paseInt(itemBean.getClassifyType()) == 1) {
                    it.putExtra("mediaId", itemBean.getItemMediaList().get(0).getMediaId());
                }
                mContext.startActivity(it);
            }
        });

        if (!ComUtils.isListEmpty(itemBean.getItemMediaList())) {
            InputPicAdapter inputPicAdapter = new InputPicAdapter(mContext, itemBean.getItemMediaList(), FU.paseInt(itemBean.getClassifyType()));
            holder.gv_pics.setAdapter(inputPicAdapter);
            holder.gv_pics.setVisibility(View.VISIBLE);
        } else {
            InputPicAdapter inputPicAdapter = new InputPicAdapter(mContext, new ArrayList<PublishItemBean.ItemMediaListBean>(), FU.paseInt(itemBean.getClassifyType()));
            holder.gv_pics.setAdapter(inputPicAdapter);
            holder.gv_pics.setVisibility(View.GONE);
        }

        holder.gv_pics.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int index, long id) {
                if (FU.paseInt(itemBean.getClassifyType()) == 1) {//如果是视频
                    Intent it = new Intent(mContext, VideoDetailsActivity.class);
                    it.putExtra("itemId", itemBean.getItemId());
                    mContext.startActivity(it);
                    return;
                }

                Intent bIt = new Intent(mContext, ShowBigPicActivity.class);
                bIt.putExtra("picUrl", mList.get(position).getItemMediaList().get(index).getMediaUrl());
                List<String> urlList = new ArrayList<>();
                for (PublishItemBean.ItemMediaListBean itemMediaListBean : mList.get(position).getItemMediaList()) {
                    urlList.add(itemMediaListBean.getMediaUrl());
                }
                if (urlList.size() > 1)// url集合
                    bIt.putStringArrayListExtra("urlList", (ArrayList<String>) urlList);
                mContext.startActivity(bIt);
            }
        });

        if (!ComUtils.isListEmpty(itemBean.getItemCommentList())) {
            Collections.sort(itemBean.getItemCommentList(), new Comparator<PublishItemBean.ItemCommentListBean>() {
                @Override
                public int compare(PublishItemBean.ItemCommentListBean itemCommentListBean, PublishItemBean.ItemCommentListBean t1) {
                    long num1 = itemCommentListBean.getCreateDate();
                    long num2 = t1.getCreateDate();
                    if (num1 > num2) {
                        return 1;
                    } else if (num1 < num2) {
                        return -1;
                    } else {
                        return 0;
                    }
                }
            });
            final List<PublishItemBean.ItemCommentListBean> showTeacherInfoList = new ArrayList<>();

            if (itemBean.getItemCommentList().size() > 10) {
                showTeacherInfoList.clear();
                showTeacherInfoList.addAll(itemBean.getItemCommentList().subList(0, 10));
                holder.tv_expand_cmt.setVisibility(View.VISIBLE);
                holder.tv_small_cmt.setVisibility(View.GONE);
            } else {
                showTeacherInfoList.clear();
                showTeacherInfoList.addAll(itemBean.getItemCommentList());
                holder.tv_expand_cmt.setVisibility(View.GONE);
                holder.tv_small_cmt.setVisibility(View.GONE);
            }

            final FhCommtAdapter fhCommtAdapter = new FhCommtAdapter(mContext, showTeacherInfoList, mOnRecordDoneListener, position);
            holder.lv_comment.setAdapter(fhCommtAdapter);
            holder.lv_comment.setVisibility(View.VISIBLE);

            holder.tv_expand_cmt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showTeacherInfoList.clear();
                    showTeacherInfoList.addAll(itemBean.getItemCommentList());
                    fhCommtAdapter.notifyDataSetChanged();
                    holder.tv_expand_cmt.setVisibility(View.GONE);
                    holder.tv_small_cmt.setVisibility(View.VISIBLE);
                }
            });

            holder.tv_small_cmt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showTeacherInfoList.clear();
                    showTeacherInfoList.addAll(itemBean.getItemCommentList().subList(0, 10));
                    fhCommtAdapter.notifyDataSetChanged();
                    holder.tv_expand_cmt.setVisibility(View.VISIBLE);
                    holder.tv_small_cmt.setVisibility(View.GONE);
                }
            });
        } else {
            holder.lv_comment.setAdapter(new FhCommtAdapter(mContext, new ArrayList<PublishItemBean.ItemCommentListBean>(), mOnRecordDoneListener, position));
            holder.lv_comment.setVisibility(View.GONE);
            holder.tv_expand_cmt.setVisibility(View.GONE);
            holder.tv_small_cmt.setVisibility(View.GONE);
        }
        holder.lv_comment.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                itemBean.setDongThingAreaShowing(false);
                mOnRecordDoneListener.onRepalyClicked(position, pos);
            }
        });
        holder.img_user_head.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(mContext, OthersMainInfoActivity.class);
                it.putExtra("OTHER_NAME", mList.get(position).getUserInfo().getUserName());
                it.putExtra("userId", mList.get(position).getUserInfo().getUserId());
                mContext.startActivity(it);
            }
        });
        return convertView;
    }

    class ViewHolder {
        ExpandableTextView expandableTextView;
        TextView tv_user_name, expandable_text, tv_praise_names, btn_ss_praise, btn_ss_turnout, tv_type, tv_time, tv_delete, tv_praise_label, tv_intro, tv_expand_cmt, tv_small_cmt, tv_turn_names;
        ImageView img_user_head, img_type, img_dothings;
        GridViewForScrollView gv_pics;
        ListViewForScrollView lv_comment;
        View area_dothings, area_praise, area_comment, area_praise_content, area_turn, area_turn_content;
        View area_root;
    }

    public interface OnRecordDoneListener {
        void onCommentClicked(int position);

        void onRepalyClicked(int position, int cmtIndex);

        void onDeleteClicked(int position);

        void onPraiseClicked(int position);

        void onDelPraiseClicked(int position, int cmtIndex);
    }

    public void setOnRecordDoneListener(OnRecordDoneListener onRecordDoneListener) {
        this.mOnRecordDoneListener = onRecordDoneListener;
    }

    private class InputPicAdapter extends BaseAdapter {

        private List<PublishItemBean.ItemMediaListBean> iList;
        private int type;

        public InputPicAdapter(Context mContext, List<PublishItemBean.ItemMediaListBean> mList, int type) {
            this.iList = mList;
            factory = LayoutInflater.from(mContext);
            this.type = type;
        }

        @Override
        public int getCount() {
            return iList.size();
        }

        @Override
        public PublishItemBean.ItemMediaListBean getItem(int position) {
            return iList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (convertView == null) {
                convertView = LayoutInflater.from(mContext).inflate(R.layout.gridview_course_eva_show_item, null);
            }
            ImageView img_input = (ImageView) convertView.findViewById(R.id.img_input);
            ImageView img_play = (ImageView) convertView.findViewById(R.id.img_play);
            if (type == 1) {
                img_play.setVisibility(View.VISIBLE);
            } else {
                img_play.setVisibility(View.GONE);
            }
            ImageManager.getInstance().displayImg(img_input, iList.get(position).getMediaUrl());
            return convertView;
        }
    }

    public boolean isUserInPraiseList(int userId, List<PublishItemBean.PraiseListBean> praiseList) {
        for (PublishItemBean.PraiseListBean pb : praiseList) {
            if (pb.getUserId() == GlobalParam.currentUser.getId())
                return true;
        }
        return false;
    }

    public boolean isUserInTurnList(List<ReturnBean> returnList) {
        for (ReturnBean pb : returnList) {
            if (pb.getUserId() == GlobalParam.currentUser.getId())
                return true;
        }
        return false;
    }

    public int getPraiseItemPosOfUser(List<PublishItemBean.PraiseListBean> praiseList) {
        for (int i = 0; i < praiseList.size(); i++) {
            if (praiseList.get(i).getUserId() == GlobalParam.currentUser.getId())
                return i;
        }
        return 0;
    }

    private SpannableString getTurnOutClickableSpan(List<ReturnBean> plist) {
        String allNames = getAllTurnNames(plist);
        SpannableString spanableInfo = new SpannableString(allNames);
        for (int i = 0; i < plist.size(); i++) {
            if (i == 0) {
                spanableInfo.setSpan(new Clickable(plist.get(0).getUserId(), plist.get(0).getUserName()), 0, plist.get(0).getUserName().length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            } else {
                String beforeStr = getAllTurnNames(plist.subList(0, i));
                int start = beforeStr.length() + 1;
                int end = start + plist.get(i).getUserName().length();
                spanableInfo.setSpan(new Clickable(plist.get(i).getUserId(), plist.get(i).getUserName()), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }
        return spanableInfo;
    }

    public String getAllTurnNames(List<ReturnBean> plist) {
        StringBuilder sb = new StringBuilder();
        for (ReturnBean pb : plist) {
            sb.append(pb.getUserName());
            sb.append(",");
        }
        return sb.substring(0, sb.length() - 1).toString();
    }

    private SpannableString getPraiseClickableSpan(List<PublishItemBean.PraiseListBean> plist) {
        String allNames = getAllPraiseNames(plist);
        SpannableString spanableInfo = new SpannableString(allNames);
        for (int i = 0; i < plist.size(); i++) {
            if (i == 0) {
                spanableInfo.setSpan(new Clickable(plist.get(0).getUserId(), plist.get(0).getUserName()), 0, plist.get(0).getUserName().length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            } else {
                String beforeStr = getAllPraiseNames(plist.subList(0, i));
                int start = beforeStr.length() + 1;
                int end = start + plist.get(i).getUserName().length();
                spanableInfo.setSpan(new Clickable(plist.get(i).getUserId(), plist.get(i).getUserName()), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }
        return spanableInfo;
    }

    public String getAllPraiseNames(List<PublishItemBean.PraiseListBean> plist) {
        StringBuilder sb = new StringBuilder();
        for (PublishItemBean.PraiseListBean pb : plist) {
            sb.append(pb.getUserName());
            sb.append(",");
        }
        return sb.substring(0, sb.length() - 1).toString();
    }

    private SpannableString getReturnContentClickableSpan(PublishItemBean itemBean) {
        if (TextUtils.isEmpty(itemBean.getReturnUserName())) {
            return new SpannableString(itemBean.getSummray());
        }
        SpannableString spanableInfo = new SpannableString(itemBean.getSummray() + " 转发自@" + itemBean.getReturnUserName() + "：" + (TextUtils.isEmpty(itemBean.getReturnSummary()) ? "" : itemBean.getReturnSummary()));
        int start = itemBean.getSummray().length();
        int end = start + 5 + itemBean.getReturnUserName().length();
        spanableInfo.setSpan(new ClickableReturn(FU.paseInt(itemBean.getReturnUserId()), itemBean.getReturnUserName()), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spanableInfo;
    }

    /**
     * 内部类，用于截获点击富文本后的事件
     */
    class Clickable extends ClickableSpan {
        private int userId;
        private String userName;

        public Clickable(int userId, String userName) {
            this.userId = userId;
            this.userName = userName;
        }

        @Override
        public void onClick(View view) {
            Intent it = new Intent(mContext, OthersMainInfoActivity.class);
            it.putExtra("OTHER_NAME", userName);
            it.putExtra("userId", userId);
            mContext.startActivity(it);
        }

        @Override
        public void updateDrawState(TextPaint ds) {
            ds.setColor(mContext.getResources().getColor(R.color.g646287));
            ds.setUnderlineText(false);    //去除超链接的下划线
        }
    }

    class ClickableReturn extends ClickableSpan {
        private int userId;
        private String userName;

        public ClickableReturn(int userId, String userName) {
            this.userId = userId;
            this.userName = userName;
        }

        @Override
        public void onClick(View view) {
            Intent it = new Intent(mContext, OthersMainInfoActivity.class);
            it.putExtra("OTHER_NAME", userName);
            it.putExtra("userId", userId);
            mContext.startActivity(it);
        }

        @Override
        public void updateDrawState(TextPaint ds) {
            ds.setColor(Color.parseColor("#70a8ee"));
            ds.setUnderlineText(false);    //去除超链接的下划线
        }
    }

    private void setTextViewLines(TextView textView, TextView button, boolean isShowAll) {
        if (!isShowAll) {
            //展示全部，按钮设置为点击收起。
            textView.setMaxHeight(mContext.getResources().getDisplayMetrics().heightPixels);
            button.setText("收起");
        } else {
            //显示3行，按钮设置为点击显示全部。
            textView.setMaxLines(LINES);
            button.setText("展开");
        }
    }
}
