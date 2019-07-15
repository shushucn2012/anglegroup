package com.asuper.angelgroup.moduel.home;

import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;


import com.alibaba.sdk.android.vod.upload.VODUploadCallback;
import com.alibaba.sdk.android.vod.upload.VODUploadClient;
import com.alibaba.sdk.android.vod.upload.VODUploadClientImpl;
import com.alibaba.sdk.android.vod.upload.model.UploadFileInfo;
import com.alibaba.sdk.android.vod.upload.model.VodInfo;
import com.asuper.angelgroup.BaseActivity;
import com.asuper.angelgroup.R;
import com.asuper.angelgroup.common.set.AppUrl;
import com.asuper.angelgroup.common.set.ParamBuild;
import com.asuper.angelgroup.common.tool.AliyunUploadUtils;
import com.asuper.angelgroup.common.tool.ComUtils;
import com.asuper.angelgroup.common.tool.FU;
import com.asuper.angelgroup.common.tool.FilesManager;
import com.asuper.angelgroup.common.tool.ImageManager;
import com.asuper.angelgroup.net.base.Request;
import com.asuper.angelgroup.net.request.interfa.BaseRequestListener;
import com.asuper.angelgroup.net.request.interfa.JsonRequestListener;
import com.asuper.angelgroup.widget.dialog.CircleProgressDialog;

import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.nereo.multi_image_selector.MultiImageSelector;

import static com.asuper.angelgroup.common.tool.FilesManager.DeleteFile;
import static com.asuper.angelgroup.common.tool.ImageManager.saveBitmapToSD;

/**
 * 视频发布页
 * Created by shubei on 2017/6/13.
 */
public class FhVideoPublishActivity extends BaseActivity {

    private ImageView img_video_cover;
    private TextView tv_video_duration;
    private EditText edit_input_word;
    private TextView tv_right, tv_open_level;
    private VODUploadCallback callback;
    private CircleProgressDialog mCircleProgressDialog;

    private String path, duration, size, coverPath, coverUrl;
    private VODUploadClient uploader;
    private String uploadAuth, uploadAddress, requestIdA, videoId, desStr, summary;
    private String shareType = "-1";

    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            logout("===================handleMessage=========================" + msg.arg1);
            if (msg.what == 0) {
                mCircleProgressDialog.dialogDismiss();

                new AliyunUploadUtils(mContext).uploadPic(coverPath, new AliyunUploadUtils.OnUploadFinish() {

                    @Override
                    public void onSuccess(String picUrl) {
                        coverUrl = picUrl;
                        asyncFabu();
                    }

                    @Override
                    public void onError() {
                        showShortToast("封面上传失败，请重试");
                    }
                });
            } else if (msg.what == 1) {
                logout("===================handleMessage=========================" + msg.arg1);
                mCircleProgressDialog.setProgress(msg.arg1);
            } else if (msg.what == 2) {
                showShortToast("上传失败，请重试！");
                mCircleProgressDialog.dialogDismiss();
            }
        }
    };

    //private CircleProgressDialog mCircleProgressDialog;

    @Override
    public void setLayout() {
        setContentView(R.layout.activity_fh_video_publish);
    }

    @Override
    public void initView() {
        mCircleProgressDialog = new CircleProgressDialog(mContext);
        img_video_cover = (ImageView) findViewById(R.id.img_video_cover);
        tv_video_duration = (TextView) findViewById(R.id.tv_video_duration);
        edit_input_word = (EditText) findViewById(R.id.edit_input_word);

        setPagTitle("发表视频");
        tv_right = findViewById(R.id.tv_right);
        tv_right.setText("发布");
        tv_right.setTextColor(mContext.getResources().getColor(R.color.main_green));
        tv_open_level = findViewById(R.id.tv_open_level);
    }

    @Override
    public void initData() {
        path = getIntent().getStringExtra("videopath");
        duration = getIntent().getStringExtra("videoduration");
        size = getIntent().getStringExtra("videosize");
        logout("============================path=========================" + path);
        logout("============================duration=========================" + duration);
        logout("============================size=========================" + size);
        if (TextUtils.isEmpty(size)) {
            try {
                size = FilesManager.getFileSize(path) + "";
            } catch (Exception e) {
                e.printStackTrace();
            }
            logout("============================size2=========================" + size);
        }
        //ImageManager.getInstance().displayImg(img_video_cover, "file:///" + path);
        tv_video_duration.setText(duration);

        //String path  = Environment.getExternalStorageDirectory().getPath();
        MediaMetadataRetriever media = new MediaMetadataRetriever();
        media.setDataSource(path);

        Bitmap bitmap = media.getFrameAtTime();
        img_video_cover.setImageBitmap(bitmap);
        coverPath = saveBitmapToSD(bitmap);
        logout("============================coverPath=========================" + coverPath);
    }

    @Override
    public void initListener() {
        area_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dDialog.showDialog("提示", "退出此次编辑？", "取消", "退出", null, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        hideKeyboard();
                        finish();
                    }
                });
            }
        });
        findViewById(R.id.area_open_level).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(mContext, SetAuthActivity.class);
                it.putExtra("relativeName", tv_open_level.getText().toString());
                startActivityForResult(it, 11);
            }
        });

        callback = new VODUploadCallback() {

            @Override
            public void onUploadStarted(UploadFileInfo uploadFileInfo) {
                uploader.setUploadAuthAndAddress(uploadFileInfo, uploadAuth, uploadAddress);
                logout("=======================================开始上传==========================================");
                mCircleProgressDialog.showDialog();
            }

            /**
             * 上传成功回调
             */
            public void onUploadSucceed(UploadFileInfo info) {
                logout("=======================================上传成功==========================================");
                handler.sendEmptyMessage(0);
            }

            /**
             * 上传失败
             */
            public void onUploadFailed(UploadFileInfo info, String code, String message) {
                logout("=======================================上传失败==========================================");
                handler.sendEmptyMessage(2);
            }

            /**
             * 回调上传进度
             * @param uploadedSize 已上传字节数
             * @param totalSize 总共需要上传字节数
             */
            public void onUploadProgress(UploadFileInfo info, long uploadedSize, long totalSize) {
                Message msg = new Message();
                msg.what = 1;
                msg.arg1 = (int) ((uploadedSize * 100) / totalSize);
                handler.sendMessage(msg);
                logout("=======================================onUploadProgress==========================================" + uploadedSize + "/" + totalSize);
            }

            /**
             * 上传凭证过期后，会回调这个接口
             * 可在这个回调中获取新的上传，然后调用resumeUploadWithAuth继续上传
             */
            public void onUploadTokenExpired() {
                logout("=======================================onUploadTokenExpired==========================================");
            }

            /**
             * 上传过程中，状态由正常切换为异常时触发
             */
            public void onUploadRetry(String code, String message) {
                logout("=======================================onUploadRetry==========================================");
            }

            /**
             * 上传过程中，从异常中恢复时触发
             */
            public void onUploadRetryResume() {
                logout("=======================================onUploadRetryResume==========================================");
            }
        };
        area_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (FU.paseLong(size) > 75 * 1024 * 1024) {
                    showShortToast("不能上传大于75M的视频");
                    return;
                }
                desStr = edit_input_word.getText().toString().trim();
                summary = edit_input_word.getText().toString().trim();
                if (TextUtils.isEmpty(desStr)) {
                    desStr = "天使团";
                }
                /*if (TextUtils.isEmpty(desStr)) {
                    showShortToast("请填写视频描述");
                    return;
                }*/
                asyncDetailsData();
            }
        });
        img_video_cover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(Intent.ACTION_VIEW);

               /* Uri uri;
                if (Build.VERSION.SDK_INT >= 24) {
                    uri = FileProvider.getUriForFile(mContext, "com.asuper.angelgroup.FileProvider", imageFile);
                } else {
                    uri = Uri.fromFile(imageFile);
                }*/

                Uri uri = Uri.parse(path);
                it.setDataAndType(uri, "video/mp4");
                startActivity(it);
            }
        });
    }

    private VodInfo getVodInfo() {
        VodInfo vodInfo = new VodInfo();
        vodInfo.setTitle(desStr);
        vodInfo.setDesc(desStr);
        vodInfo.setCateId(0);
        vodInfo.setIsProcess(true);
        //vodInfo.setCoverUrl("http://www.taobao.com/" + 0 + ".jpg");
        List<String> tags = new ArrayList<>();
        tags.add("61区");
        vodInfo.setTags(tags);
        vodInfo.setIsShowWaterMark(false);
        vodInfo.setPriority(7);
        return vodInfo;
    }

    /**
     * 请求详情数据
     */
    private void asyncDetailsData() {
        String wholeUrl = AppUrl.host + AppUrl.GET_ALIYUN_AUTH;
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("localPath", path);
        map.put("size", size);
        map.put("title", desStr);
        map.put("description", desStr);
        map.put("tags", "61区");
        String requestBodyData = ParamBuild.buildParams(map);
        netRequest.startRequest(wholeUrl, Request.Method.POST, requestBodyData, 0, bannerLsner);
    }

    BaseRequestListener bannerLsner = new JsonRequestListener() {

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
            requestIdA = jsonResult.optString("requestId");
            uploadAuth = jsonResult.optString("uploadAuth");
            uploadAddress = jsonResult.optString("uploadAddress");
            videoId = jsonResult.optString("videoId");

            uploader = new VODUploadClientImpl(mContext);
            uploader.init(callback);
            uploader.addFile(path, getVodInfo());
            uploader.start();
        }
    };

    private void asyncFabu() {
        String wholeUrl = AppUrl.host + AppUrl.RELEASE_VIDEO;
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("summary", summary);
        map.put("mediaId", videoId);
        if (tv_open_level.getText().toString().equals("公开")) {
            map.put("authority", "1");
        } else if (tv_open_level.getText().toString().equals("好友圈")) {
            map.put("authority", "2");
        } else {
            map.put("authority", "3");
        }
        map.put("coverUrl", coverUrl);
        String requestBodyData = ParamBuild.buildParams(map);
        netRequest.startRequest(wholeUrl, Request.Method.POST, requestBodyData, 0, fLsner);
    }

    BaseRequestListener fLsner = new JsonRequestListener() {

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 11) {
            tv_open_level.setText(data.getStringExtra("relativeName"));
        }
    }

    @Override
    public void onBackPressed() {
        dDialog.showDialog("提示", "退出此次编辑？", "取消", "退出", null, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideKeyboard();
                finish();
            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        DeleteFile(new File(coverPath));
    }
}
