package com.asuper.angelgroup.common.set;

import com.asuper.angelgroup.common.tool.AliyunUploadBean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * 通讯接口传入参数组件
 *
 * @author super
 */
public class ParamBuild {
    /**
     * 将map参数拼接为String
     *
     * @param params
     *            参数集合
     */
    /**
     * 将map参数拼接为String
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static String buildParams(Map params) {
        // 每一天请求都添加当前时间参数
        params.put("timestamp", System.currentTimeMillis());
        calcServerSign(params);
        boolean f = false;
        StringBuilder sb = new StringBuilder();
        for (Map.Entry kv : (Set<Map.Entry>) params.entrySet()) {
            if (f)
                sb.append("&");
            Object v = kv.getValue();
            if (v instanceof List) {
                List l = (List) v;
                for (Object item : l) {
                    sb.append("&");
                    // sb.append(kv.getkey()).append("=")
                    // .append(urlencoder.encode((string) item));
                    sb.append(kv.getKey()).append("=").append((String) item);
                }
            } else {
                // sb.append(kv.getKey())
                // .append("=")
                // .append(kv.getValue() == null ? "" : URLEncoder
                // .encode(kv.getValue().toString()));
                sb.append(kv.getKey())
                        .append("=")
                        .append(kv.getValue() == null ? "" : kv.getValue()
                                .toString());
            }
            f = true;
        }
        Log.e("http-params", "------http-params======" + sb.toString());
        return sb.toString();
    }

    @SuppressWarnings("rawtypes")
    private static void calcServerSign(Map params) {
        List<String> sortedDataItemList = new ArrayList<String>();
        Iterator iterator = params.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry entry = (Map.Entry) iterator.next();
            // String paramName = (String) entry.getKey();
            // String paramValues = (String) entry.getValue();
            // if (paramValues != null) {
            sortedDataItemList.add(entry.getKey() + "=" + entry.getValue());
            // }
        }

        // 升序
        Collections.sort(sortedDataItemList);
        StringBuffer sb = new StringBuffer();
        for (String data : sortedDataItemList) {
            sb.append(data);
        }
        GlobalParam.x_sign = encodeMD5(sb.append("sO&C%3Mq@lqY4PHt").toString());
    }

    /**
     * MD5加密
     */
    public static String encodeMD5(String strInput) {
        StringBuffer buf = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(strInput.getBytes("UTF-8"));
            byte b[] = md.digest();
            buf = new StringBuffer(b.length * 2);
            for (int i = 0; i < b.length; i++) {
                if (((int) b[i] & 0xff) < 0x10) { /* & 0xff转换无符号整型 */
                    buf.append("0");
                }
                buf.append(Long.toHexString((int) b[i] & 0xff)); /* 转换16进制,下方法同 */
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return buf.toString();
    }

    /**
     * 组建获取阿里云oss加密url请求参数
     *
     * @param piclist 图片
     */
    public static String getAliyunUrl(List<AliyunUploadBean> piclist) {
        Map<String, Object> map = new HashMap<String, Object>();
        JSONArray jay = new JSONArray();
        for (AliyunUploadBean aliyunUploadBean : piclist) {
            JSONObject jot = new JSONObject();
            try {
                jot.put("key", aliyunUploadBean.getKey());
                jot.put("ContentType", aliyunUploadBean.getContentType());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            jay.put(jot);
        }
        map.put("piclist", jay.toString());
        return buildParams(map);
    }

}
