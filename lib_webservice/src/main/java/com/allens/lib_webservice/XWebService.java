package com.allens.lib_webservice;

import android.support.v4.util.SimpleArrayMap;
import android.util.Log;

import com.google.gson.Gson;

import org.ksoap2.serialization.SoapObject;

/**
 * Created by allens on 2017/12/18.
 */

public class XWebService {


    public static <T> void getIntentData(final Class<T> tClass, final String methodName, final String json, final OnResultListener<T> onResultListener) {
        SimpleArrayMap<String, String> arrayMap = null;
        if (json != null) {
            arrayMap = new SimpleArrayMap<>();
            arrayMap.put("args", json);
        }
        WebServiceUtils.create().call("url", "NAMESPACE", methodName, arrayMap, new WebServiceUtils.Response() {
            @Override
            public void onSuccess(SoapObject result) {
                if (result != null) {
                    String mJson = result.getProperty(0).toString();
                    Log.i("XWebService", "服务端获取的JSON---->" + methodName + "---->" + mJson);
                    Gson gson = new Gson();
                    T bean = gson.fromJson(mJson, tClass);
                    onResultListener.onSuccess(bean);
                }
            }

            @Override
            public void onError(Exception e) {
                onResultListener.onError();
            }
        });

    }

    public interface OnResultListener<T> {

        void onSuccess(T bean);

        void onError();
    }


}
