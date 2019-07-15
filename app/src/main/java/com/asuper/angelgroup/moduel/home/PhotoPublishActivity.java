package com.asuper.angelgroup.moduel.home;

import android.Manifest;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;


import com.asuper.angelgroup.BaseActivity;
import com.asuper.angelgroup.R;
import com.asuper.angelgroup.common.set.AppUrl;
import com.asuper.angelgroup.common.set.ParamBuild;
import com.asuper.angelgroup.common.tool.AliyunUploadUtils;
import com.asuper.angelgroup.common.tool.ComUtils;
import com.asuper.angelgroup.common.tool.ImageManager;
import com.asuper.angelgroup.common.tool.PermissionHelper;
import com.asuper.angelgroup.net.base.Request;
import com.asuper.angelgroup.net.request.interfa.BaseRequestListener;
import com.asuper.angelgroup.net.request.interfa.JsonRequestListener;
import com.asuper.angelgroup.widget.gridview.GridViewForScrollView;
import com.bigkoo.pickerview.OptionsPickerView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.nereo.multi_image_selector.MultiImageSelector;

import static com.asuper.angelgroup.R.id.edit_info;

/**
 * 照片发布页
 * Created by shubei on 2017/8/24.
 */

public class PhotoPublishActivity extends BaseActivity {

    private PermissionHelper.PermissionModel[] permissionModels = {
            new PermissionHelper.PermissionModel(0, Manifest.permission.CAMERA, "相机")
    };

    private PermissionHelper permissionHelper;

    private static final int REQUEST_IMAGE = 2;

    private ArrayList<String> mSelectPath;
    private ArrayList<String> mSelectPathAdd = new ArrayList<>();
    private ArrayList<String> urlList = new ArrayList<String>();

    private String title, content;

    private ImageView img_add_pic;
    private GridViewForScrollView gv_input_pic;// 图片展示gridview
    private InputPicAdapter adapter;// 图片展示gridview适配器
    private EditText edit_input_word;
    private TextView tv_right, tv_open_level;

    @Override
    public void setLayout() {
        setContentView(R.layout.activity_photo_publish);
    }

    @Override
    public void initView() {
        img_add_pic = (ImageView) findViewById(R.id.img_add_pic);
        gv_input_pic = (GridViewForScrollView) findViewById(R.id.gv_input_pic);
        edit_input_word = (EditText) findViewById(edit_info);

        setPagTitle("发表文字");
        tv_right = findViewById(R.id.tv_right);
        tv_right.setText("发布");
        tv_right.setTextColor(mContext.getResources().getColor(R.color.main_green));
        tv_open_level = findViewById(R.id.tv_open_level);
    }

    @Override
    public void initData() {
        mSelectPath = getIntent().getStringArrayListExtra("selectPath");
        img_add_pic.setVisibility(View.GONE);
        gv_input_pic.setVisibility(View.VISIBLE);
        mSelectPathAdd.clear();
        mSelectPathAdd.addAll(mSelectPath);
        mSelectPathAdd.add("+");
        adapter = new InputPicAdapter();
        gv_input_pic.setAdapter(adapter);
    }

    @Override
    public void initListener() {
        permissionHelper = new PermissionHelper(PhotoPublishActivity.this);
        img_add_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                permissionHelper.setOnAlterApplyPermission(new PermissionHelper.OnAlterApplyPermission() {
                    @Override
                    public void OnAlterApplyPermission() {
                        goToMultiImageSelector();
                    }
                });
                permissionHelper.setRequestPermission(permissionModels);
                if (Build.VERSION.SDK_INT < 23) {//6.0以下，不需要动态申请
                    goToMultiImageSelector();
                } else {//6.0+ 需要动态申请
                    //判断是否全部授权过
                    if (permissionHelper.isAllApplyPermission()) {//申请的权限全部授予过，直接运行
                        goToMultiImageSelector();
                    } else {//没有全部授权过，申请
                        permissionHelper.applyPermission();
                    }
                }
            }
        });
        area_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*if (TextUtils.isEmpty(edit_input_word.getText().toString().trim())) {
                    showShortToast("请输入文字");
                    return;
                }*/
               /* if (edit_input_word.getText().toString().trim().length() < 14) {
                    showShortToast("文字最少1个字");
                    return;
                }*/
                if (ComUtils.isListEmpty(mSelectPath)) {
                    showShortToast("请选择需要发布的照片！");
                    return;
                }

                // 图片不为空时,异步压缩再上传
                new CompressNUploadTask().execute();

               /* showShortToast("发布成功!");
                finish();*/
            }
        });
        findViewById(R.id.area_open_level).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              /*  final List<String> optionsItems = new ArrayList<String>();
                optionsItems.add("公开");
                optionsItems.add("好友圈");
                optionsItems.add("日记");
                OptionsPickerView pvOptions = new OptionsPickerView.Builder(mContext, new OptionsPickerView.OnOptionsSelectListener() {
                    @Override
                    public void onOptionsSelect(int options1, int option2, int options3, View v) {
                        tv_open_level.setText(optionsItems.get(options1));
                    }
                }).setSubmitColor(mContext.getResources().getColor(R.color.main_green))//确定按钮文字颜色
                        .setCancelColor(mContext.getResources().getColor(R.color.g333333))//取消按钮文字颜色
                        .build();
                pvOptions.setPicker(optionsItems);
                pvOptions.show();*/
                Intent it = new Intent(mContext, SetAuthActivity.class);
                it.putExtra("relativeName", tv_open_level.getText().toString());
                startActivityForResult(it, 11);
            }
        });
        area_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!TextUtils.isEmpty(edit_input_word.getText().toString().trim())
                        || !ComUtils.isListEmpty(mSelectPath)) {
                    dDialog.showDialog("提示", "退出此次编辑？", "取消", "退出", null, new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            hideKeyboard();
                            finish();
                        }
                    });
                } else {
                    hideKeyboard();
                    finish();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (!TextUtils.isEmpty(edit_input_word.getText().toString().trim())
                || !ComUtils.isListEmpty(mSelectPath)) {
            dDialog.showDialog("提示", "退出此次编辑？", "取消", "退出", null, new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            });
        } else {
            finish();
        }
    }

    /**
     * 去相册
     */
    public void goToMultiImageSelector() {
        MultiImageSelector selector = MultiImageSelector.create();
        selector.showCamera(true);
        selector.count(9);
        selector.multi();
        selector.origin(mSelectPath);
        selector.start(PhotoPublishActivity.this, REQUEST_IMAGE);
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
        if (requestCode == REQUEST_IMAGE) {
            if (resultCode == RESULT_OK) {
                mSelectPath = data.getStringArrayListExtra(MultiImageSelector.EXTRA_RESULT);
                img_add_pic.setVisibility(View.GONE);
                gv_input_pic.setVisibility(View.VISIBLE);
                mSelectPathAdd.clear();
                mSelectPathAdd.addAll(mSelectPath);
                mSelectPathAdd.add("+");
                adapter = new InputPicAdapter();
                gv_input_pic.setAdapter(adapter);
            }
        } else if (resultCode == RESULT_OK && requestCode == 11) {
            tv_open_level.setText(data.getStringExtra("relativeName"));
        }
    }

    private class InputPicAdapter extends BaseAdapter {

        private static final int TYPE_COM = 0;
        private static final int TYPE_ADD = 1;

        @Override
        public int getCount() {
            return mSelectPathAdd.size();
        }

        @Override
        public String getItem(int position) {
            return mSelectPathAdd.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public int getItemViewType(int position) {
            if (position == mSelectPathAdd.size() - 1) {
                return TYPE_ADD;
            }
            return TYPE_COM;
        }

        @Override
        public int getViewTypeCount() {
            return 2;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            int type = getItemViewType(position);
            switch (type) {
                case TYPE_COM:
                    convertView = LayoutInflater.from(mContext).inflate(R.layout.gridview_course_eva_inputpic_item, null);
                    ImageView img_input = (ImageView) convertView.findViewById(R.id.img_input);
                    ImageManager.getInstance().displayImg(img_input, "file:///" + mSelectPathAdd.get(position));
                    ImageView img_delete = (ImageView) convertView.findViewById(R.id.img_delete);
                    img_delete.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mSelectPath.remove(position);
                            mSelectPathAdd.remove(position);
                            notifyDataSetChanged();
                        }
                    });
                    break;
                case TYPE_ADD:
                    convertView = LayoutInflater.from(mContext).inflate(R.layout.layout_photo_add_item, null);
                    View area_add_pic = convertView.findViewById(R.id.area_add_pic);
                    area_add_pic.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            MultiImageSelector selector = MultiImageSelector.create();
                            selector.showCamera(true);
                            selector.count(9);
                            selector.multi();
                            selector.origin(mSelectPath);
                            selector.start(PhotoPublishActivity.this, REQUEST_IMAGE);
                        }
                    });
                    break;
            }
            if (position == 9) {
                convertView.setVisibility(View.GONE);
            } else {
                convertView.setVisibility(View.VISIBLE);
            }
            return convertView;
        }
    }

    /**
     * 压缩再上传
     */
    private class CompressNUploadTask extends AsyncTask<String, Integer, ArrayList<String>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showDialog();
        }

        @Override
        protected ArrayList<String> doInBackground(String... params) {
            // 准备将上传的已压缩图片的文件路径
            ArrayList<String> resizedPathList = new ArrayList<String>();
            for (String wholePath : mSelectPath) {
                File temp = new File(Environment.getExternalStorageDirectory().getPath() + "/GHPCacheFolder/Format");// 自已缓存文件夹
                if (!temp.exists()) {
                    temp.mkdirs();
                }
                String filePath = temp.getAbsolutePath() + "/" + Calendar.getInstance().getTimeInMillis() + ".jpg";
                File tempFile = new File(filePath);
                // 图像保存到文件中
                try {
                    ImageManager.compressBmpToFile(tempFile, wholePath);
                } catch (Exception e) {
                    e.printStackTrace();
                    showShortToast("图片压缩失败！");
                    finish();
                }
                // 将压缩后的地址放入集合
                resizedPathList.add(filePath);
            }
            return resizedPathList;
        }

        @Override
        protected void onPostExecute(ArrayList<String> resizedPathList) {
            dismissDialog();
            new AliyunUploadUtils(mContext).uploadPicList(resizedPathList, new AliyunUploadUtils.OnUploadListFinish() {

                @Override
                public void onError(String path) {
                    showShortToast("上传失败！");
                }

                @Override
                public void onSuccess(List<String> urllist) {
                    urlList.clear();
                    urlList.addAll(urllist);
                    asyncIssuePhoto();
                }
            });
        }
    }

    private void asyncIssuePhoto() {
        String wholeUrl = AppUrl.host + AppUrl.releaseBlog;
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("summary", edit_input_word.getText().toString().trim());
        if (!ComUtils.isListEmpty(urlList)) {
            StringBuilder sb = new StringBuilder();
            for (String p : urlList) {
                sb.append(p);
                sb.append(",");
            }
            map.put("imgUrl", sb.substring(0, sb.length() - 1).toString());
        }
        if (tv_open_level.getText().toString().equals("公开")) {
            map.put("authority", "1");
        } else if (tv_open_level.getText().toString().equals("好友圈")) {
            map.put("authority", "2");
        } else {
            map.put("authority", "3");
        }
        String requestBodyData = ParamBuild.buildParams(map);
        netRequest.startRequest(wholeUrl, Request.Method.POST, requestBodyData, 0, eLsner);
    }

    BaseRequestListener eLsner = new JsonRequestListener() {

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
            showShortToast("发布成功！");
            HomeActivity.needFresh = true;
            finish();
        }
    };
}
