package com.asuper.angelgroup.moduel.login;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.asuper.angelgroup.BaseActivity;
import com.asuper.angelgroup.MainTabActivity;
import com.asuper.angelgroup.R;
import com.asuper.angelgroup.common.set.AppUrl;
import com.asuper.angelgroup.common.set.GlobalParam;
import com.asuper.angelgroup.common.set.ParamBuild;
import com.asuper.angelgroup.common.tool.AliyunUploadUtils;
import com.asuper.angelgroup.common.tool.ComUtils;
import com.asuper.angelgroup.common.tool.DateTool;
import com.asuper.angelgroup.common.tool.ExitAppUtils;
import com.asuper.angelgroup.common.tool.ImageManager;
import com.asuper.angelgroup.common.tool.PermissionHelper;
import com.asuper.angelgroup.moduel.login.bean.RelationItem;
import com.asuper.angelgroup.moduel.me.bean.CityBean;
import com.asuper.angelgroup.moduel.me.bean.MockWords;
import com.asuper.angelgroup.net.base.Request;
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
 * 填写父母详细
 * Created by shubei on 2017/11/17.
 */

public class FillInfoMoreActivity extends BaseActivity {

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

    private View area_user_head, area_user_nicky, area_user_sex, area_addr, area_relative, area_baby_nicky, area_baby_sex,
            area_baby_birthday, area_user_sign;
    private ImageView img_user_head, img_chk_xieyi;
    private TextView tv_user_name, tv_user_sex, tv_addr, tv_relative, tv_baby_nicky, tv_baby_sex, tv_baby_birthday, tv_user_sign;
    private Button btn_finish;

    private String uploadHeadPic;
    private List<CityBean> cityList = new ArrayList<>();
    private int chosenCityId;
    private boolean isChecked = true;

    @Override
    public void setLayout() {
        setContentView(R.layout.activity_fillinfo_more);
    }

    @Override
    public void initView() {
        setPagTitle("信息设置");
        area_user_head = findViewById(R.id.area_user_head);
        img_user_head = findViewById(R.id.img_user_head);
        area_user_nicky = findViewById(R.id.area_user_nicky);
        tv_user_name = findViewById(R.id.tv_user_name);
        area_user_sex = findViewById(R.id.area_user_sex);
        tv_user_sex = findViewById(R.id.tv_user_sex);
        area_addr = findViewById(R.id.area_addr);
        tv_addr = findViewById(R.id.tv_addr);
        area_relative = findViewById(R.id.area_relative);
        tv_relative = findViewById(R.id.tv_relative);
        area_baby_nicky = findViewById(R.id.area_baby_nicky);
        tv_baby_nicky = findViewById(R.id.tv_baby_nicky);
        area_baby_sex = findViewById(R.id.area_baby_sex);
        tv_baby_sex = findViewById(R.id.tv_baby_sex);
        area_baby_birthday = findViewById(R.id.area_baby_birthday);
        tv_baby_birthday = findViewById(R.id.tv_baby_birthday);
        btn_finish = findViewById(R.id.btn_finish);
        area_user_sign = findViewById(R.id.area_user_sign);
        tv_user_sign = findViewById(R.id.tv_user_sign);
        img_chk_xieyi = findViewById(R.id.img_chk_xieyi);
        //area_baby_symptom = findViewById(R.id.area_user_sign);
    }

    @Override
    public void initData() {
        MockWords.initRelations();
        MockWords.initProvince();
    }

    @Override
    public void initListener() {
        permissionHelper = new PermissionHelper(FillInfoMoreActivity.this);
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
                it.putExtra("maxLength", 10);
                startActivityForResult(it, REQ_GET_NAME);
            }
        });
        area_user_sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(mContext, MeEditActivity.class);
                it.putExtra("oldData", tv_user_sign.getText().toString().trim());
                it.putExtra("pageTitle", "个性签名");
                it.putExtra("maxLength", 30);
                startActivityForResult(it, REQ_GET_SIGN);
            }
        });
        area_baby_nicky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(mContext, MeEditActivity.class);
                it.putExtra("oldData", tv_baby_nicky.getText().toString().trim());
                it.putExtra("pageTitle", "宝贝昵称");
                it.putExtra("maxLength", 10);
                startActivityForResult(it, REQ_GET_BABYNAME);
            }
        });
        area_user_sex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final List<String> optionsItems = new ArrayList<String>();
                optionsItems.add("男");
                optionsItems.add("女");
                OptionsPickerView pvOptions = new OptionsPickerView.Builder(mContext, new OptionsPickerView.OnOptionsSelectListener() {
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
        area_baby_sex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final List<String> optionsItems = new ArrayList<String>();
                optionsItems.add("男");
                optionsItems.add("女");
                OptionsPickerView pvOptions = new OptionsPickerView.Builder(mContext, new OptionsPickerView.OnOptionsSelectListener() {
                    @Override
                    public void onOptionsSelect(int options1, int option2, int options3, View v) {
                        tv_baby_sex.setText(optionsItems.get(options1));
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
                OptionsPickerView pvOptions = new OptionsPickerView.Builder(mContext, new OptionsPickerView.OnOptionsSelectListener() {
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
        area_relative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(mContext, ChooseBabyRelativeActivity.class);
                it.putExtra("relativeName", tv_relative.getText().toString().trim());
                startActivityForResult(it, REQ_GET_RELATIVE);
            }
        });
        area_baby_birthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initTimePicker();
            }
        });
        btn_finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(uploadHeadPic)) {
                    showShortToast("请选择头像");
                    return;
                }
                if (TextUtils.isEmpty(tv_user_name.getText().toString().trim())) {
                    showShortToast("请填写您的昵称");
                    return;
                }
                if (TextUtils.isEmpty(tv_user_sex.getText().toString().trim())) {
                    showShortToast("请填写您的性别");
                    return;
                }
                if (TextUtils.isEmpty(tv_addr.getText().toString().trim())) {
                    showShortToast("请填写您的所在地");
                    return;
                }
                if (TextUtils.isEmpty(tv_relative.getText().toString().trim())) {
                    showShortToast("请填写您与宝贝的关系");
                    return;
                }
                if (TextUtils.isEmpty(tv_baby_nicky.getText().toString().trim())) {
                    showShortToast("请填写宝贝的昵称");
                    return;
                }
                if (TextUtils.isEmpty(tv_baby_sex.getText().toString().trim())) {
                    showShortToast("请填写宝贝的性别");
                    return;
                }
                if (TextUtils.isEmpty(tv_baby_birthday.getText().toString().trim())) {
                    showShortToast("请填写宝贝的生日");
                    return;
                }
                if (!isChecked) {
                    showShortToast("用户协议未勾选");
                    return;
                }
                asyncUpdateBaseInfo();
            }
        });
        img_chk_xieyi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isChecked) {
                    img_chk_xieyi.setImageResource(R.mipmap.icon_select_normal);
                    isChecked = false;
                } else {
                    img_chk_xieyi.setImageResource(R.mipmap.icon_select_active);
                    isChecked = true;
                }
            }
        });
        findViewById(R.id.tv_xieyi).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(mContext, XieyiActivity.class));
            }
        });
    }

    private void initTimePicker() {
        //控制时间范围(如果不设置范围，则使用默认时间1900-2100年，此段代码可注释)
        //因为系统Calendar的月份是从0-11的,所以如果是调用Calendar的set方法来设置时间,月份的范围也要是从0-11
        Calendar selectedDate = Calendar.getInstance();
        Calendar startDate = Calendar.getInstance();
        startDate.set(1990, 0, 23);
        Calendar endDate = Calendar.getInstance();
        endDate.set(2019, 11, 28);
        //时间选择器
        TimePickerView pvTime = new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                // 这里回调过来的v,就是show()方法里面所添加的 View 参数，如果show的时候没有添加参数，v则为null
                /*btn_Time.setText(getTime(date));*/
                tv_baby_birthday.setText(DateTool.parseTime(date.getTime()));
            }
        }).setType(new boolean[]{true, true, true, false, false, false})//年月日时分秒 的显示与否，不设置则默认全部显示
                .setLabel("年", "月", "日", "", "", "")
                .isCenterLabel(false)
                .setDividerColor(mContext.getResources().getColor(R.color.gc0c0c0))
                .setContentSize(21)
                .setDate(selectedDate)
                .setRangDate(startDate, endDate)
                .setSubmitColor(mContext.getResources().getColor(R.color.main_green))//确定按钮文字颜色
                .setCancelColor(mContext.getResources().getColor(R.color.g333333))//取消按钮文字颜色
//                .setBackgroundId(0x00FFFFFF) //设置外部遮罩颜色
                .setDecorView(null)
                .build();
        pvTime.show();
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
        } else if (requestCode == REQ_GET_RELATIVE) {
            tv_relative.setText(data.getStringExtra("relativeName"));
        } else if (requestCode == REQ_GET_BABYNAME) {
            tv_baby_nicky.setText(data.getStringExtra("newData"));
        }
    }

    /**
     * 更新用户信息
     */
    private void asyncUpdateBaseInfo() {
        String wholeUrl = AppUrl.host + AppUrl.updateBaseInfo;
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("labelCode", "001");
        map.put("pictureUrl", uploadHeadPic);
        map.put("petName", tv_user_name.getText().toString().trim());
        map.put("sex", tv_user_sex.getText().toString());
        map.put("cityId", chosenCityId);
        map.put("relationId", getRelationId());
        map.put("childName", tv_baby_nicky.getText().toString().trim());
        map.put("childSex", tv_baby_sex.getText().toString().equals("女") ? 1 : 0);
        map.put("birthday", tv_baby_birthday.getText().toString().trim().replace("年", "-").replace("月", "-").replace("日", ""));
        String requestBodyData = ParamBuild.buildParams(map);
        netRequest.startRequest(wholeUrl, Request.Method.POST, requestBodyData, 0, regLsner);
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
            showShortToast("完善信息成功！");
            startActivity(new Intent(mContext, MainTabActivity.class));
            ComUtils.saveCurrentUserName(mContext);
            finish();
        }
    };

    public String getRelationId() {
        for (int i = 0; i < GlobalParam.relationList.size(); i++) {
            if (GlobalParam.relationList.get(i).getRelationName().equals(tv_relative.getText().toString())) {
                return GlobalParam.relationList.get(i).getId();
            }
        }
        return "" ;
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
            OptionsPickerView pvOptions = new OptionsPickerView.Builder(mContext, new OptionsPickerView.OnOptionsSelectListener() {
                @Override
                public void onOptionsSelect(int options1, int option2, int options3, View v) {
                    tv_addr.setText(cityList.get(options1).getCityName());
                    chosenCityId = cityList.get(options1).getId();
                }
            }).setSubmitColor(mContext.getResources().getColor(R.color.main_green))//确定按钮文字颜色
                    .setCancelColor(mContext.getResources().getColor(R.color.g333333))//取消按钮文字颜色
                    .build();
            pvOptions.setPicker(cityList);
            pvOptions.show();
        }
    };
}
