package com.asuper.angelgroup.net.request;

import com.asuper.angelgroup.net.request.interfa.JsonRequestListener;

import org.json.JSONObject;

/**
 * Created by shubei on 2017/5/26.
 */

public class SimpleRequestListener implements JsonRequestListener {

    @Override
    public void onStart(int requestId) {

    }

    @Override
    public void onError(int requestId, String errorCode, String errorMsg) {

    }

    @Override
    public void onSuccess(int requestId, String url, JSONObject jsonResult) {

    }
}
