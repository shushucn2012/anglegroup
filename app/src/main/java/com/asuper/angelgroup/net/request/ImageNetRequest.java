package com.asuper.angelgroup.net.request;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;

import com.asuper.angelgroup.R;
import com.asuper.angelgroup.common.tool.FilesManager;
import com.asuper.angelgroup.net.base.Request.Method;
import com.asuper.angelgroup.net.base.Response.ErrorListener;
import com.asuper.angelgroup.net.base.Response.Listener;
import com.asuper.angelgroup.net.base.VolleyError;
import com.asuper.angelgroup.net.request.interfa.BaseRequestListener;
import com.asuper.angelgroup.net.request.interfa.ImageRequestListener;
import com.asuper.angelgroup.net.toolbox.ImageRequest;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @description:获取图片网络请求（得到bitmap）
 * @author cai
 * @time:2014年12月13日下午2:34:26
 */
public class ImageNetRequest extends BaseRequest<Bitmap> {

	private static ImageNetRequest imageNetRequest;

	private ImageNetRequest(Context context) {
		mContext = context;
	}

	public synchronized static ImageNetRequest getInstance(Context context) {
		if (imageNetRequest == null) {
			imageNetRequest = new ImageNetRequest(context);
		}
		return imageNetRequest;
	}

	/**
	 * 开始请求网络获取图片
	 * 
	 * @param url
	 *            图片地址
	 * @param requestId
	 *            图片请求id
	 * @param listener
	 *            图片请求过程监听接口
	 */
	public void startRequest(String url, final int requestId, final ImageRequestListener listener) {
		super.startBaseRequest(url, Method.GET, null, requestId, listener);
	}

	@Override
	public void doRequest(final String url, int requestMethod, String requstData, final int requestId,
			final BaseRequestListener listener) {
		request = new ImageRequest(url, new Listener<Bitmap>() {

			@Override
			public void onResponse(final Bitmap response) {
				final File file = new File(FilesManager.getDownLoadImageDir(mContext, url));

				if (!file.exists()) {
					new Thread(new Runnable() {
						public void run() {
							try {
								file.createNewFile();
							} catch (IOException e1) {
								e1.printStackTrace();
							}
							BufferedOutputStream bos = null;
							try {
								bos = new BufferedOutputStream(new FileOutputStream(file));

							} catch (FileNotFoundException e) {
								e.printStackTrace();
							}
							response.compress(Bitmap.CompressFormat.PNG, 100, bos);
							try {
								bos.flush();
								bos.close();
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
					}).start();
				}
				((ImageRequestListener) listener).onSuccess(requestId, url, response);

			}
		}, 0, 0, Config.ARGB_8888, new ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError error) {
				listener.onError(requestId, null, mContext.getString(R.string.net_request_fail));

			}
		});

	}

}
