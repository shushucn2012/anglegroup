package com.asuper.angelgroup.moduel.login;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;

import com.asuper.angelgroup.BaseActivity;
import com.asuper.angelgroup.R;
import com.asuper.angelgroup.common.set.AppUrl;
import com.asuper.angelgroup.common.set.GlobalParam;
import com.asuper.angelgroup.common.set.ParamBuild;
import com.asuper.angelgroup.common.tool.ComUtils;
import com.asuper.angelgroup.moduel.login.adapter.DreamFlagGvAdapter;
import com.asuper.angelgroup.moduel.login.bean.LabelBean;
import com.asuper.angelgroup.moduel.login.bean.RolesBean;
import com.asuper.angelgroup.net.base.Request;
import com.asuper.angelgroup.net.request.interfa.BaseRequestListener;
import com.asuper.angelgroup.net.request.interfa.JsonRequestListener;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 选择角色页
 * Created by shubei on 2017/11/17.
 */

public class FillInfoRoleActivity extends BaseActivity {

    private GridView gv_label, gv_label2;
    private DreamFlagGvAdapter mDreamFlagGvAdapter, mDreamFlagGvAdapter2;
    private List<RolesBean.LabelsBean> flagBeanList = new ArrayList<>();//家人
    private List<RolesBean.LabelsBean> flagBeanList2 = new ArrayList<>();//关爱人士
    private ImageView img_parents_ckb, img_others_ckb;
    private Button btn_next;
    private int roleType = -1; //0 父母，1 关爱
    private List<RolesBean> rolesBeanList = new ArrayList<>();

    @Override
    public void setLayout() {
        setContentView(R.layout.activity_fillinfo_role);
    }

    @Override
    public void initView() {
        setPagTitle("我是");
        gv_label = findViewById(R.id.gv_label);
        gv_label2 = findViewById(R.id.gv_label2);
        img_parents_ckb = findViewById(R.id.img_parents_ckb);
        img_others_ckb = findViewById(R.id.img_others_ckb);
        btn_next = findViewById(R.id.btn_next);
    }

    @Override
    public void initData() {
      /*  flagBeanList.add(new LabelBean(0, "自闭症", false));
        flagBeanList.add(new LabelBean(0, "唐氏综合症", false));
        flagBeanList.add(new LabelBean(0, "脑瘫", false));
        flagBeanList.add(new LabelBean(0, "发育迟缓", false));
        flagBeanList.add(new LabelBean(0, "综合", false));
        flagBeanList.add(new LabelBean(0, "其他", false));*/

        mDreamFlagGvAdapter = new DreamFlagGvAdapter(flagBeanList, mContext);
        gv_label.setAdapter(mDreamFlagGvAdapter);

       /* flagBeanList2.add(new LabelBean(0, "康复", false));
        flagBeanList2.add(new LabelBean(0, "教育", false));
        flagBeanList2.add(new LabelBean(0, "公益", false));
        flagBeanList2.add(new LabelBean(0, "心理支援", false));
        flagBeanList2.add(new LabelBean(0, "法律支援", false));
        flagBeanList2.add(new LabelBean(0, "其他", false));*/

        mDreamFlagGvAdapter2 = new DreamFlagGvAdapter(flagBeanList2, mContext);
        gv_label2.setAdapter(mDreamFlagGvAdapter2);
        asyncGetAllRoles();
    }

    @Override
    public void initListener() {
        gv_label.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                flagBeanList.get(position).setIsCheck("1");
                for (int i = 0; i < flagBeanList.size(); i++) {
                    if (i != position) {
                        flagBeanList.get(i).setIsCheck("0");
                    }
                }
                mDreamFlagGvAdapter.notifyDataSetChanged();
                clearListDataChosen(2);
                img_parents_ckb.setImageResource(R.mipmap.icon_select_active);
                img_others_ckb.setImageResource(R.mipmap.icon_select_normal);
                roleType = 0;
            }
        });

        gv_label2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                flagBeanList2.get(position).setIsCheck("1");
                for (int i = 0; i < flagBeanList2.size(); i++) {
                    if (i != position) {
                        flagBeanList2.get(i).setIsCheck("0");
                    }
                }
                mDreamFlagGvAdapter2.notifyDataSetChanged();
                clearListDataChosen(0);
                img_parents_ckb.setImageResource(R.mipmap.icon_select_normal);
                img_others_ckb.setImageResource(R.mipmap.icon_select_active);
                roleType = 1;
            }
        });

        img_parents_ckb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                img_parents_ckb.setImageResource(R.mipmap.icon_select_active);
                img_others_ckb.setImageResource(R.mipmap.icon_select_normal);
                clearListDataChosen(2);
                roleType = 0;
            }
        });
        img_others_ckb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                img_parents_ckb.setImageResource(R.mipmap.icon_select_normal);
                img_others_ckb.setImageResource(R.mipmap.icon_select_active);
                clearListDataChosen(0);
                roleType = 1;
            }
        });
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (roleType == -1) {
                    showShortToast("请选择身份");
                    return;
                }
                if(TextUtils.isEmpty(getCheckedItemId(flagBeanList))
                        && TextUtils.isEmpty(getCheckedItemId(flagBeanList2))){
                    showShortToast("请选择标签");
                    return;
                }
                asyncAddIdentityLabels();
            }
        });
    }

    private void clearListDataChosen(int index) {
        if (index == 0) {
            for (int i = 0; i < flagBeanList.size(); i++) {
                flagBeanList.get(i).setIsCheck("0");
            }
            mDreamFlagGvAdapter.notifyDataSetChanged();
        } else {
            for (int i = 0; i < flagBeanList2.size(); i++) {
                flagBeanList2.get(i).setIsCheck("0");
            }
            mDreamFlagGvAdapter2.notifyDataSetChanged();
        }
    }

    /**
     * 获取所有身份
     */
    private void asyncGetAllRoles() {
        String wholeUrl = AppUrl.host + AppUrl.getIdentityLabels;
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
            showShortToast(errorMsg);
        }

        @Override
        public void onSuccess(int requestId, String url, JSONObject jsonResult) {
            dismissDialog();
            JSONArray jsonArray = jsonResult.optJSONArray("list");
            if (jsonArray != null && jsonArray.length() > 0) {
                for (int i = 0; i < jsonArray.length(); i++) {
                    RolesBean rolesBean = new Gson().fromJson(jsonArray.optJSONObject(i).toString(), RolesBean.class);
                    rolesBeanList.add(rolesBean);
                }
            }
            if (rolesBeanList.size() > 0) {
                for (int i = 0; i < rolesBeanList.get(0).getLabels().size(); i++) {
                    RolesBean.LabelsBean labelsBean = rolesBeanList.get(0).getLabels().get(i);
                    if (labelsBean.getIdentityId() == 1) {
                        flagBeanList.add(labelsBean);
                    }
                }
                mDreamFlagGvAdapter.notifyDataSetChanged();
            }
            if (rolesBeanList.size() > 1) {
                for (int i = 0; i < rolesBeanList.get(1).getLabels().size(); i++) {
                    RolesBean.LabelsBean labelsBean = rolesBeanList.get(1).getLabels().get(i);
                    if (labelsBean.getIdentityId() == 2) {
                        flagBeanList2.add(labelsBean);
                    }
                }
                mDreamFlagGvAdapter2.notifyDataSetChanged();
            }
        }
    };

    /**
     * 更新身份
     */
    private void asyncAddIdentityLabels() {
        String wholeUrl = AppUrl.host + AppUrl.addIdentityLabels;
        Map<String, Object> map = new HashMap<String, Object>();
        if (roleType == 0) {
            map.put("labelId", getCheckedItemId(flagBeanList));
            map.put("labelCode", "001");
        } else if (roleType == 1) {
            map.put("labelId", getCheckedItemId(flagBeanList2));
            map.put("labelCode", "002");
        }
        String requestBodyData = ParamBuild.buildParams(map);
        netRequest.startRequest(wholeUrl, Request.Method.POST, requestBodyData, 0, addLsner);
    }

    BaseRequestListener addLsner = new JsonRequestListener() {

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
            if (roleType == 0) {
                startActivity(new Intent(mContext, FillInfoMoreActivity.class));
            } else {
                startActivity(new Intent(mContext, FillInfoMoreOthersActivity.class));
            }
        }
    };

    public String getCheckedItemId(List<RolesBean.LabelsBean> list) {
        for (RolesBean.LabelsBean lb :
                list) {
            if(lb.getIsCheck().equals("1"))
                return lb.getId();
        }
        return "";
    }
}
