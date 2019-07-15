package com.asuper.angelgroup.moduel.book;

import android.content.Intent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.asuper.angelgroup.BaseActivity;
import com.asuper.angelgroup.MainTabActivity;
import com.asuper.angelgroup.R;
import com.asuper.angelgroup.common.set.AppUrl;
import com.asuper.angelgroup.common.set.GlobalParam;
import com.asuper.angelgroup.common.set.ParamBuild;
import com.asuper.angelgroup.common.tool.ComUtils;
import com.asuper.angelgroup.common.tool.ImageManager;
import com.asuper.angelgroup.moduel.book.adapter.MyAdapter;
import com.asuper.angelgroup.moduel.book.bean.AddRequestBean;
import com.asuper.angelgroup.moduel.book.bean.Contactors;
import com.asuper.angelgroup.moduel.book.bean.User;
import com.asuper.angelgroup.moduel.login.FillInfoMoreActivity;
import com.asuper.angelgroup.moduel.login.FillInfoMoreOthersActivity;
import com.asuper.angelgroup.moduel.login.FillInfoRoleActivity;
import com.asuper.angelgroup.moduel.me.bean.MockPic;
import com.asuper.angelgroup.net.base.Request;
import com.asuper.angelgroup.net.request.interfa.BaseRequestListener;
import com.asuper.angelgroup.net.request.interfa.JsonRequestListener;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 通讯录页面
 * Created by shubei on 2017/11/17.
 */

public class BookActivity extends BaseActivity {

    private ListView listView;
    private TextView textView;
    private LetterIndexView letterIndexView;
    private View area_new_friends, area_focus;

    private List<Contactors> list;
    private MyAdapter adapter;

    @Override
    public void setLayout() {
        setContentView(R.layout.activity_book);
    }

    @Override
    public void initView() {
        setPagTitle("通讯录");
        listView = (ListView) findViewById(R.id.lv);
        textView = (TextView) findViewById(R.id.show_letter_in_center);
        letterIndexView = (LetterIndexView) findViewById(R.id.letter_index_view);
        ((ImageView) findViewById(R.id.img_right)).setImageResource(R.mipmap.icon_search);
        area_new_friends = findViewById(R.id.area_new_friends);
        area_focus = findViewById(R.id.area_focus);
    }

    @Override
    public void initData() {
        list = new ArrayList<>();
        adapter = new MyAdapter(this, list);
        listView.setAdapter(adapter);

        letterIndexView.setTextViewDialog(textView);

        //String[] allUserNames = getResources().getStringArray(R.array.arrUsernames);
    }

    @Override
    protected void onResume() {
        super.onResume();
        asyncGetMyFirends();
    }

    @Override
    public void initListener() {
        letterIndexView.setUpdateListView(new LetterIndexView.UpdateListView() {
            @Override
            public void updateListView(String currentChar) {
                int positionForSection = adapter.getPositionForSection(currentChar.charAt(0));
                listView.setSelection(positionForSection);
            }
        });
        area_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(mContext, SearchFriendsActivity.class));
            }
        });
        area_new_friends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(mContext, NewFriendsActivity.class));
            }
        });
        area_focus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(mContext, FocusFriendsActivity.class));
            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int i, long l) {
                dDialog.showDialog("提示", "确定删除该好友吗？", "取消", "确定", null, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dDialog.dismissDialog();
                        asyncDeleteFriends(i);
                    }
                });
                return true;
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent it = new Intent(mContext, OthersMainInfoActivity.class);
                it.putExtra("OTHER_NAME", list.get(i).getPetName());
                it.putExtra("userId", list.get(i).getFriendUserId());
                startActivity(it);
            }
        });
    }

    /**
     * 我的好友列表
     */
    private void asyncGetMyFirends() {
        String wholeUrl = AppUrl.host + AppUrl.getMyFirends;
        /*Map<String, Object> map = new HashMap<String, Object>();
        map.put("mobile", edit_phone.getText().toString().trim());
        map.put("password", edit_pwd.getText().toString().trim());
        String requestBodyData = ParamBuild.buildParams(map);*/
        netRequest.startRequest(wholeUrl, Request.Method.POST, "", 0, regLsner);
    }

    BaseRequestListener regLsner = new JsonRequestListener() {

        @Override
        public void onStart(int requestId) {
            showDialog();
        }

        @Override
        public void onError(int requestId, String errorCode, String errorMsg) {
            dismissDialog();
            if ("9999".equals(errorCode)) {
                //showShortToast("暂无好友");
                list.clear();
                adapter.notifyDataSetChanged();
            } else {
                showShortToast(errorMsg);
            }
        }

        @Override
        public void onSuccess(int requestId, String url, JSONObject jsonResult) {
            dismissDialog();
            JSONArray jay = jsonResult.optJSONArray("list");
            list.clear();
            if (jay != null && jay.length() > 0) {
                for (int i = 0; i < jay.length(); i++) {
                    Contactors contactors = new Gson().fromJson(jay.optJSONObject(i).toString(), Contactors.class);
                    String substring = contactors.getAllSpellName().toUpperCase().substring(0, 1);
                    if (substring.matches("[A-Z]")) {
                        contactors.setFirstLetter(substring);
                    } else {
                        contactors.setFirstLetter("#");
                    }
                    list.add(contactors);
                }
            }
            Collections.sort(list, new Comparator<Contactors>() {
                @Override
                public int compare(Contactors lhs, Contactors rhs) {
                    if (lhs.getFirstLetter().contains("#")) {
                        return 1;
                    } else if (rhs.getFirstLetter().contains("#")) {
                        return -1;
                    } else {
                        return lhs.getFirstLetter().compareTo(rhs.getFirstLetter());
                    }
                }
            });
            adapter.notifyDataSetChanged();

            listView.setOnScrollListener(new AbsListView.OnScrollListener() {

                @Override
                public void onScrollStateChanged(AbsListView view, int scrollState) {
                }

                @Override
                public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                    if (ComUtils.isListEmpty(list))
                        return;
                    int sectionForPosition = adapter.getSectionForPosition(firstVisibleItem);
                    letterIndexView.updateLetterIndexView(sectionForPosition);
                }
            });
        }
    };

    private void asyncDeleteFriends(int pos) {
        String wholeUrl = AppUrl.host + AppUrl.deleteMyFriend;
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("myFriendId", list.get(pos).getId());
        String requestBodyData = ParamBuild.buildParams(map);
        netRequest.startRequest(wholeUrl, Request.Method.POST, requestBodyData, 0, dfLsner);
    }

    BaseRequestListener dfLsner = new JsonRequestListener() {

        @Override
        public void onStart(int requestId) {
            showDialog();
        }

        @Override
        public void onError(int requestId, String errorCode, String errorMsg) {
            dismissDialog();
            showShortToast(errorMsg);
        }

        @Override
        public void onSuccess(int requestId, String url, JSONObject jsonResult) {
            dismissDialog();
            showShortToast("删除成功！");
            asyncGetMyFirends();
        }
    };


}
