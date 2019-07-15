package com.asuper.angelgroup.common.tool;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.asuper.angelgroup.common.set.Log;
import com.asuper.angelgroup.widget.dialog.CommonProgressDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by shubei on 2017/6/30.
 */

public class MyFileUpload {

    private static final int UPLOAD_SUCCESS = 0;

    private static final int UPLOAD_FAILED = 1;

    private Context mContext;

    private CommonProgressDialog commonProgressDialog;
    private OnUploadFinish mOnUploadFinish;

    private Handler mHandler = new Handler() {

        public void handleMessage(Message msg) {
            switch (msg.what) {
                case UPLOAD_SUCCESS:
                    String url = (String) msg.obj;
                    Log.out("url====================" + url);
                    dismissDialog();
                    mOnUploadFinish.onSuccess(url);
                    break;
                case UPLOAD_FAILED:
                    dismissDialog();
                    mOnUploadFinish.onError();
                    break;
            }
        }
    };

    public MyFileUpload(Context context) {
        mContext = context;
        commonProgressDialog = new CommonProgressDialog(mContext);
        commonProgressDialog.setCancelable(false);
    }

    public void startUpload(final String file, final String RequestURL, OnUploadFinish onUploadFinish) {
        mOnUploadFinish = onUploadFinish;
        showDialog("正在上传图片...");
        final File fileItem = new File(file);
        new Thread() {
            public void run() {
                uploadFile(fileItem, RequestURL);
            }
        }.start();
    }

    public void startUploadWav(final String file, final String RequestURL, OnUploadFinish onUploadFinish) {
        mOnUploadFinish = onUploadFinish;
        final File fileItem = new File(file);
        new Thread() {
            public void run() {
                uploadFile(fileItem, RequestURL);
            }
        }.start();
    }

    private int uploadFile(File file, String RequestURL) {
        String TAG = "uploadFile";
        int TIME_OUT = 10 * 1000; // 超时时间
        String CHARSET = "utf-8"; // 设置编码
        int res = 0;
        String result = null;
        String BOUNDARY = UUID.randomUUID().toString(); // 边界标识 随机生成
        String PREFIX = "--", LINE_END = "\r\n";
        String CONTENT_TYPE = "multipart/form-data"; // 内容类型

        try {
            URL url = new URL(RequestURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(TIME_OUT);
            conn.setConnectTimeout(TIME_OUT);
            conn.setDoInput(true); // 允许输入流
            conn.setDoOutput(true); // 允许输出流
            conn.setUseCaches(false); // 不允许使用缓存
            conn.setRequestMethod("POST"); // 请求方式
            conn.setRequestProperty("Charset", CHARSET); // 设置编码
            conn.setRequestProperty("connection", "keep-alive");
            conn.setRequestProperty("Content-Type", CONTENT_TYPE + ";boundary=" + BOUNDARY);

            /**
             * 当文件不为空时执行上传
             */
            DataOutputStream dos = new DataOutputStream(conn.getOutputStream());
            StringBuffer sb = new StringBuffer();
            sb.append(PREFIX);
            sb.append(BOUNDARY);
            sb.append(LINE_END);
            /**
             * 这里重点注意： name里面的值为服务器端需要key 只有这个key 才可以得到对应的文件
             * filename是文件的名字，包含后缀名
             */

            sb.append("Content-Disposition: form-data; name=\"file\"; filename=\""
                    + file.getName() + "\"" + LINE_END);
            sb.append("Content-Type: application/octet-stream; charset="
                    + CHARSET + LINE_END);
            sb.append(LINE_END);
            dos.write(sb.toString().getBytes());
            InputStream is = new FileInputStream(file);
            byte[] bytes = new byte[1024];
            int len = 0;
            while ((len = is.read(bytes)) != -1) {
                dos.write(bytes, 0, len);
            }
            is.close();
            dos.write(LINE_END.getBytes());
            byte[] end_data = (PREFIX + BOUNDARY + PREFIX + LINE_END).getBytes();
            dos.write(end_data);
            dos.flush();
            /**
             * 获取响应码 200=成功 当响应成功，获取响应的流
             */
            res = conn.getResponseCode();
            if (res == 200) {
                InputStream input = conn.getInputStream();
                StringBuffer sb1 = new StringBuffer();
                int ss;
                while ((ss = input.read()) != -1) {
                    sb1.append((char) ss);
                }
                result = sb1.toString();
                JSONObject jo;
                String imgUrl = "";
                try {
                    jo = new JSONObject(result);
                    imgUrl = jo.getString("URL");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.out("------upload-----result : " + result);
                Message msg = mHandler.obtainMessage();
                msg.what = UPLOAD_SUCCESS;
                msg.obj = imgUrl;
                mHandler.sendMessage(msg);
            } else {
                mHandler.sendEmptyMessage(UPLOAD_FAILED);
            }
        } catch (MalformedURLException e) {
            mHandler.sendEmptyMessage(UPLOAD_FAILED);
            e.printStackTrace();
        } catch (IOException e) {
            mHandler.sendEmptyMessage(UPLOAD_FAILED);
            e.printStackTrace();
        }
        return res;
    }

    /**
     * 上传完成回调
     */
    public interface OnUploadFinish {
        void onError();

        void onSuccess(String picUrl);
    }

    public void showDialog(String msg) {
        try {
            if (commonProgressDialog.isShow()) {
                return;
            } else {
                commonProgressDialog.showDialog(msg);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void dismissDialog() {
        try {
            if (commonProgressDialog.isShow()) {
                commonProgressDialog.dialogDismiss();
            } else {
                return;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
