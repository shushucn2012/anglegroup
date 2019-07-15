/**
 * 
 */
package com.asuper.angelgroup.net.request;

import android.content.Context;

import com.asuper.angelgroup.common.set.GlobalParam;
import com.asuper.angelgroup.common.set.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
//import com.wjl.comsmartpos.modules.user.UserManager;
//import com.wjl.comsmartpos.modules.user.bean.UserBean;

/**
 * @description:string类型字符传导方式网络请求
 * @author cai
 * @time:2014年12月11日下午12:37:13
 */
public class StringNetRequest extends DataRequest {
	private static StringNetRequest stringNetRequest;

	private StringNetRequest(Context context) {
		mContext = context;
	}

	public synchronized static StringNetRequest getInstance(Context context) {
		if (stringNetRequest == null) {
			stringNetRequest = new StringNetRequest(context);
		}
		return stringNetRequest;
	}

	@Override
	public Map<String, String> getPrivateHeaders() {
		HashMap<String, String> header = new HashMap<String, String>();
		header.put("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
		header.put("cityId", GlobalParam.chooseCityCode);
		header.put("userToken", GlobalParam.userToken);
		JSONObject clientInfoJot = new JSONObject();
		try {
			clientInfoJot.put("clientAppName", "android");
			clientInfoJot.put("clientAppVersion", GlobalParam.versionName);
			clientInfoJot.put("clientSystem", "android");
			clientInfoJot.put("clientVersion", android.os.Build.VERSION.RELEASE);
			clientInfoJot.put("deviceCode", GlobalParam.macAddress);
			clientInfoJot.put("longitude", GlobalParam.longitude);
			clientInfoJot.put("latitude", GlobalParam.latitude);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		header.put("clientInfo", clientInfoJot.toString());
		Log.e("httpheader", "------httpheader======" + header);
		return header;
	}

}
