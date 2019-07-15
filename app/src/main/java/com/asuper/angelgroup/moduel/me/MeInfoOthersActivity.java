package com.asuper.angelgroup.moduel.me;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.asuper.angelgroup.BaseActivity;
import com.asuper.angelgroup.R;
import com.asuper.angelgroup.common.set.AppUrl;
import com.asuper.angelgroup.common.set.GlobalParam;
import com.asuper.angelgroup.common.set.ParamBuild;
import com.asuper.angelgroup.common.tool.AliyunUploadUtils;
import com.asuper.angelgroup.common.tool.DateTool;
import com.asuper.angelgroup.common.tool.ImageManager;
import com.asuper.angelgroup.common.tool.PermissionHelper;
import com.asuper.angelgroup.moduel.login.ChooseBabyRelativeActivity;
import com.asuper.angelgroup.moduel.login.MeEditActivity;
import com.asuper.angelgroup.moduel.me.bean.CityBean;
import com.asuper.angelgroup.moduel.me.bean.MockWords;
import com.asuper.angelgroup.net.base.Request;
import com.asuper.angelgroup.net.request.SimpleRequestListener;
import com.asuper.angelgroup.net.request.interfa.BaseRequestListener;
import com.asuper.angelgroup.net.request.interfa.JsonRequestListener;
import com.asuper.angelgroup.widget.picgetutils.SelectPicPopupWindow;
import com.bigkoo.pickerview.OptionsPickerView;
import com.bigkoo.pickerview.OptionsPickerView.OnOptionsSelectListener;
import com.bigkoo.pickerview.TimePickerView;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 我的信息-关爱人士
 * Created by shubei on 2017/11/17.
 */

public class MeInfoOthersActivity extends BaseActivity {

    private PermissionHelper.PermissionModel[] permissionModels = {
            new PermissionHelper.PermissionModel(1, Manifest.permission.READ_EXTERNAL_STORAGE, "我们需要读取相册图片"),
            new PermissionHelper.PermissionModel(1, Manifest.permission.WRITE_EXTERNAL_STORAGE, "我们需要写入相册图片"),
            new PermissionHelper.PermissionModel(0, Manifest.permission.CAMERA, "我们需要拍照")
    };
    private PermissionHelper permissionHelper;

    private static final int REQ_GET_PIC = 0;
    private static final int REQ_GET_NAME = 1;
    private static final int REQ_GET_RELATIVE = 3;
    private static final int REQ_GET_BABYNAME = 4;
    private static final int REQ_GET_SIGN = 5;
    private static int REQ_GET_ROLE = 6;

    private View area_user_head, area_user_nicky, area_user_sex, area_addr, area_role;
    private ImageView img_user_head;
    private TextView tv_user_name, tv_user_sex, tv_addr, tv_role, tv_jiaose, tv_jiaose_cate, tv_user_id, tv_user_sign;
    private Button btn_finish;

    private String uploadHeadPic;
    private List<CityBean> cityList = new ArrayList<>();
    private int chosenCityId;

    @Override
    public void setLayout() {
        setContentView(R.layout.activity_fillinfo_more_others);
    }

    @Override
    public void initView() {
        setPagTitle("信息设置");
        findViewById(R.id.area_xieyi).setVisibility(View.GONE);
        area_user_head = findViewById(R.id.area_user_head);
        img_user_head = findViewById(R.id.img_user_head);
        area_user_nicky = findViewById(R.id.area_user_nicky);
        tv_user_name = findViewById(R.id.tv_user_name);
        area_user_sex = findViewById(R.id.area_user_sex);
        tv_user_sex = findViewById(R.id.tv_user_sex);
        area_addr = findViewById(R.id.area_addr);
        tv_addr = findViewById(R.id.tv_addr);
        tv_role = findViewById(R.id.tv_role);
        tv_jiaose = findViewById(R.id.tv_jiaose);
        tv_jiaose_cate = findViewById(R.id.tv_jiaose_cate);
        tv_user_id = findViewById(R.id.tv_user_id);
        btn_finish = findViewById(R.id.btn_finish);
        btn_finish.setVisibility(View.GONE);
        tv_user_sign = findViewById(R.id.tv_user_sign);
        findViewById(R.id.area_user_sign).setVisibility(View.VISIBLE);
        findViewById(R.id.area_user_before_info).setVisibility(View.VISIBLE);
    }

    @Override
    public void initData() {
        MockWords.initRelations();
        MockWords.initProvince();
        ImageManager.getInstance().displayImg(img_user_head, GlobalParam.currentUser.getPictureUrl());
        tv_user_name.setText(GlobalParam.currentUser.getPetName());
        tv_user_sex.setText(GlobalParam.currentUser.getSex() == 1 ? "男" : "女");
        tv_user_sign.setText(GlobalParam.currentUser.getResume());
        tv_addr.setText(GlobalParam.currentUser.getCityName());
        area_role = findViewById(R.id.area_role);
        tv_role.setText(GlobalParam.currentUser.getPositionalTitle());
        tv_jiaose.setText("关爱人士");
        if (GlobalParam.currentUser.getUserIdentityLabel() != null)
            tv_jiaose_cate.setText(GlobalParam.currentUser.getUserIdentityLabel().getLableName());
        tv_user_id.setText(GlobalParam.currentUser.getLoveShopId() + "");
    }

    @Override
    public void initListener() {
        permissionHelper = new PermissionHelper(MeInfoOthersActivity.this);
        area_user_head.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                permissionHelper.setOnAlterApplyPermission(new PermissionHelper.OnAlterApplyPermission() {
                    @Override
                    public void OnAlterApplyPermission() {
                        Intent it = new Intent(mContext, SelectPicPopupWindow.class);
                        startActivityForResult(it, REQ_GET_PIC);
                    }
                });
                permissionHelper.setRequestPermission(permissionModels);
                if (Build.VERSION.SDK_INT < 23) {//6.0以下，不需要动态申请
                    Intent it = new Intent(mContext, SelectPicPopupWindow.class);
                    startActivityForResult(it, REQ_GET_PIC);
                } else {//6.0+ 需要动态申请
                    //判断是否全部授权过
                    if (permissionHelper.isAllApplyPermission()) {//申请的权限全部授予过，直接运行
                        Intent it = new Intent(mContext, SelectPicPopupWindow.class);
                        startActivityForResult(it, REQ_GET_PIC);
                    } else {//没有全部授权过，申请
                        permissionHelper.applyPermission();
                    }
                }
            }
        });
        area_user_nicky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(mContext, MeEditActivity.class);
                it.putExtra("oldData", tv_user_name.getText().toString().trim());
                it.putExtra("pageTitle", "用户昵称");
                it.putExtra("maxLength", 32);
                startActivityForResult(it, REQ_GET_NAME);
            }
        });
        findViewById(R.id.area_user_sign).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(mContext, MeEditActivity.class);
                it.putExtra("oldData", tv_user_sign.getText().toString().trim());
                it.putExtra("pageTitle", "个性签名");
                it.putExtra("maxLength", 50);
                startActivityForResult(it, REQ_GET_SIGN);
            }
        });
        area_user_sex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final List<String> optionsItems = new ArrayList<String>();
                optionsItems.add("男");
                optionsItems.add("女");
                OptionsPickerView pvOptions = new OptionsPickerView.Builder(mContext, new OnOptionsSelectListener() {
                    @Override
                    public void onOptionsSelect(int options1, int option2, int options3, View v) {
                        tv_user_sex.setText(optionsItems.get(options1));
                    }
                }).setSubmitColor(mContext.getResources().getColor(R.color.main_green))//确定按钮文字颜色
                        .setCancelColor(mContext.getResources().getColor(R.color.g333333))//取消按钮文字颜色
                        .build();
                pvOptions.setPicker(optionsItems);
                pvOptions.show();
            }
        });
        area_addr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OptionsPickerView pvOptions = new OptionsPickerView.Builder(mContext, new OnOptionsSelectListener() {
                    @Override
                    public void onOptionsSelect(int options1, int option2, int options3, View v) {
                        //tv_addr.setText(GlobalParam.provinceList.get(options1).getProvinceName());
                        asyncGetSupportCitys(GlobalParam.provinceList.get(options1).getId());
                    }
                }).setSubmitColor(mContext.getResources().getColor(R.color.main_green))//确定按钮文字颜色
                        .setCancelColor(mContext.getResources().getColor(R.color.g333333))//取消按钮文字颜色
                        .build();
                pvOptions.setPicker(GlobalParam.provinceList);
                pvOptions.show();
            }
        });
        area_role.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(mContext, MeEditActivity.class);
                it.putExtra("oldData", tv_role.getText().toString().trim());
                it.putExtra("pageTitle", "用户身份");
                it.putExtra("maxLength", 50);
                startActivityForResult(it, REQ_GET_ROLE);
            }
        });
        area_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                asyncUpdateBaseInfo();
                finish();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        permissionHelper.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        permissionHelper.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK || data == null)
            return;
        if (requestCode == REQ_GET_PIC) {
            String picPath = data.getStringExtra("path");
            logout("picPath======" + picPath);
            Bitmap bmp = ImageManager.getInstance().readFileBitMap(picPath);
            img_user_head.setImageBitmap(bmp);
            new AliyunUploadUtils(mContext).uploadPic(picPath, new AliyunUploadUtils.OnUploadFinish() {

                @Override
                public void onSuccess(String picUrl) {
                    uploadHeadPic = picUrl;
                }

                @Override
                public void onError() {
                    showShortToast("头像上传失败请重试！");
                }
            });
        } else if (requestCode == REQ_GET_NAME) {
            tv_user_name.setText(data.getStringExtra("newData"));
        } else if (requestCode == REQ_GET_SIGN) {
            tv_user_sign.setText(data.getStringExtra("newData"));
        } else if (requestCode == REQ_GET_ROLE) {
            tv_role.setText(data.getStringExtra("newData"));
        }
    }

    /**
     * 更新用户信息
     */
    private void asyncUpdateBaseInfo() {
        String wholeUrl = AppUrl.host + AppUrl.updateBaseInfo;
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("labelCode", "002");
        if (!TextUtils.isEmpty(uploadHeadPic)) {
            map.put("pictureUrl", uploadHeadPic);
            GlobalParam.currentUser.setPictureUrl(uploadHeadPic);
        } else {
            map.put("pictureUrl", GlobalParam.currentUser.getPictureUrl());
        }
        if (!TextUtils.isEmpty(tv_user_name.getText().toString().trim())) {
            map.put("petName", tv_user_name.getText().toString().trim());
            GlobalParam.currentUser.setPetName(tv_user_name.getText().toString().trim());
        }
        if (!TextUtils.isEmpty(tv_user_sign.getText().toString().trim())) {
            map.put("resume", tv_user_sign.getText().toString().trim());
            GlobalParam.currentUser.setResume(tv_user_sign.getText().toString().trim());
        }
        if (!TextUtils.isEmpty(tv_user_sex.getText().toString())) {
            //map.put("sex", tv_user_sex.getText().toString().equals("女") ? 1 : 0);
            map.put("sex", tv_user_sex.getText().toString());
            GlobalParam.currentUser.setSex(tv_user_sex.getText().toString().equals("女") ? 2 : 1);
        }
        if (chosenCityId != 0) {
            map.put("cityId", chosenCityId);
        } else {
            map.put("cityId", GlobalParam.currentUser.getCityId());
        }
        if (!TextUtils.isEmpty(tv_role.getText().toString().trim())) {
            map.put("positionalTitle", tv_role.getText().toString().trim());
            GlobalParam.currentUser.setPositionalTitle(tv_role.getText().toString().trim());
        }
        String requestBodyData = ParamBuild.buildParams(map);
        netRequest.startRequest(wholeUrl, Request.Method.POST, requestBodyData, 0, new SimpleRequestListener());
    }

    private void asyncGetSupportCitys(int pid) {
        String wholeUrl = AppUrl.host + AppUrl.getCitysByProvince;
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("pid", pid);
        String requestBodyData = ParamBuild.buildParams(map);
        netRequest.startRequest(wholeUrl, Request.Method.POST, requestBodyData, 0, listener);
    }

    BaseRequestListener listener = new JsonRequestListener() {

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
            JSONArray jay = jsonResult.optJSONArray("list");
            if (jay == null || jay.length() <= 0) {
                return;
            }
            cityList.clear();
            for (int i = 0; i < jay.length(); i++) {
                JSONObject jot = jay.optJSONObject(i);
                Gson gson = new Gson();
                CityBean b = gson.fromJson(jot.toString(), CityBean.class);
                cityList.add(b);
            }
            OptionsPickerView pvOptions = new OptionsPickerView.Builder(mContext, new OnOptionsSelectListener() {
                @Override
                public void onOptionsSelect(int options1, int option2, int options3, View v) {
                    tv_addr.setText(cityList.get(options1).getCityName());
                    chosenCityId = cityList.get(options1).getId();
                    GlobalParam.currentUser.setCityId(chosenCityId);
                    GlobalParam.currentUser.setCityName(cityList.get(options1).getCityName());
                }
            }).setSubmitColor(mContext.getResources().getColor(R.color.main_green))//确定按钮文字颜色
                    .setCancelColor(mContext.getResources().getColor(R.color.g333333))//取消按钮文字颜色
                    .build();
            pvOptions.setPicker(cityList);
            pvOptions.show();
        }
    };

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        asyncUpdateBaseInfo();
    }
}
