package com.asuper.angelgroup.moduel.home;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;

import com.asuper.angelgroup.BaseActivity;
import com.asuper.angelgroup.R;
import com.asuper.angelgroup.common.set.GlobalParam;
import com.asuper.angelgroup.common.tool.ComUtils;
import com.asuper.angelgroup.common.tool.PermissionHelper;
import com.asuper.angelgroup.widget.picgetutils.Utils;
import com.asuper.angelgroup.widget.videorecorder.RecordActivity;
import com.asuper.angelgroup.widget.videorecorder.VideoSelectActivity;
import com.dmcbig.mediapicker.PickerActivity;
import com.dmcbig.mediapicker.PickerConfig;
import com.dmcbig.mediapicker.entity.Media;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * 选择发布类型页面
 */
public class ChoosePublishTypeActivity extends BaseActivity implements OnClickListener {

    private PermissionHelper.PermissionModel[] permissionModels = {
            new PermissionHelper.PermissionModel(0, Manifest.permission.CAMERA, "相机"),
            new PermissionHelper.PermissionModel(1, Manifest.permission.RECORD_AUDIO, "录音")
    };

    private PermissionHelper permissionHelper;

    View area_words, area_camera, area_photo;

    ArrayList<Media> select;

    @Override
    public void setLayout() {
        setContentView(R.layout.choose_publish_type_dialog);
    }

    @Override
    public void initView() {
        area_words = findViewById(R.id.area_words);
        area_camera = findViewById(R.id.area_camera);
        area_photo = findViewById(R.id.area_photo);
    }

    @Override
    public void initData() {
    }

    @Override
    public void initListener() {
        permissionHelper = new PermissionHelper(ChoosePublishTypeActivity.this);
        findViewById(R.id.img_close).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        area_words.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, TopicPublisActivity.class);
                startActivity(intent);
                finish();
            }
        });
        area_camera.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                permissionHelper.setOnAlterApplyPermission(new PermissionHelper.OnAlterApplyPermission() {
                    @Override
                    public void OnAlterApplyPermission() {
                        goToRecord();
                    }
                });
                permissionHelper.setRequestPermission(permissionModels);
                if (Build.VERSION.SDK_INT < 23) {//6.0以下，不需要动态申请
                    goToRecord();
                } else {//6.0+ 需要动态申请
                    //判断是否全部授权过
                    if (permissionHelper.isAllApplyPermission()) {//申请的权限全部授予过，直接运行
                        goToRecord();
                    } else {//没有全部授权过，申请
                        permissionHelper.applyPermission();
                        //showShortToast("相机权限未开启，请在应用设置页面授权！");
                    }
                }
            }
        });
        area_photo.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Intent intent = new Intent(mContext, PhotoPublishActivity.class);
                startActivity(intent);
                finish();*/
                permissionHelper.setOnAlterApplyPermission(new PermissionHelper.OnAlterApplyPermission() {
                    @Override
                    public void OnAlterApplyPermission() {
                        goToPhoto();
                    }
                });
                permissionHelper.setRequestPermission(permissionModels);
                if (Build.VERSION.SDK_INT < 23) {//6.0以下，不需要动态申请
                    goToPhoto();
                } else {//6.0+ 需要动态申请
                    //判断是否全部授权过
                    if (permissionHelper.isAllApplyPermission()) {//申请的权限全部授予过，直接运行
                        goToPhoto();
                    } else {//没有全部授权过，申请
                        permissionHelper.applyPermission();
                        //showShortToast("相机权限未开启，请在应用设置页面授权！");
                    }
                }
            }
        });
    }

    /**
     * 去拍摄
     */
    public void goToRecord() {
        Intent it = new Intent(mContext, PhotoTakeActivity.class);
        startActivity(it);
        finish();
    }

    /**
     * 去相册
     */
    public void goToPhoto() {
        Intent intent = new Intent(ChoosePublishTypeActivity.this, PickerActivity.class);
        intent.putExtra(PickerConfig.SELECT_MODE, PickerConfig.PICKER_IMAGE_VIDEO);//default image and video (Optional)
        long maxSize = 188743680L;//long long long
        intent.putExtra(PickerConfig.MAX_SELECT_SIZE, maxSize); //default 180MB (Optional)
        intent.putExtra(PickerConfig.MAX_SELECT_COUNT, 9);  //default 40 (Optional)
        intent.putExtra(PickerConfig.DEFAULT_SELECTED_LIST, select); // (Optional)
        startActivityForResult(intent, 200);
    }


    @Override
    public void onClick(View view) {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        permissionHelper.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, final Intent data) {
        permissionHelper.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 200 && resultCode == PickerConfig.RESULT_CODE) {
            select = data.getParcelableArrayListExtra(PickerConfig.EXTRA_RESULT);
            ArrayList<String> mSelectPath = new ArrayList<>();
            for (Media media : select) {
                Log.e("media", media.path);
                mSelectPath.add(media.path);
                Log.e("media", "s:" + media.size);
                if (media.mediaType == 3) {
                    Intent intent = new Intent(mContext, FhVideoPublishActivity.class);
                    intent.putExtra("videopath", media.path);
                    intent.putExtra("videoduration", media.time);
                    intent.putExtra("videosize", media.size);
                    startActivity(intent);
                    finish();
                    return;
                }
            }
            Intent intent = new Intent(mContext, PhotoPublishActivity.class);
            intent.putExtra("selectPath", mSelectPath);
            startActivity(intent);
            finish();
        }
    }
}
