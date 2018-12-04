package com.litingzhe.justandroid.someOther.weex.weexAdapter;

import android.util.Log;

import com.taobao.weex.adapter.DefaultWXHttpAdapter;
import com.taobao.weex.adapter.IWXHttpAdapter;
import com.taobao.weex.common.WXRequest;
import com.taobao.weex.common.WXResponse;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.builder.GetBuilder;
import com.zhy.http.okhttp.builder.OkHttpRequestBuilder;
import com.zhy.http.okhttp.builder.PostFormBuilder;
import com.zhy.http.okhttp.callback.Callback;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.Set;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Copyright 李挺哲
 * 创建人：litingzhe
 * 邮箱：453971498@qq.com
 * Created by litingzhe on 2017/6/16 上午9:04.
 * 类描述：
 */


public class OKHttpAdapter implements IWXHttpAdapter {


    @Override
    public void sendRequest(WXRequest request, final OnHttpListener listener) {
        PostFormBuilder postFormBuilder = null;
        GetBuilder getBuilder = null;
        if ("POST".equals(request.method) || "PUT".equals(request.method) || "PATCH".equals(request.method)) {
            postFormBuilder = OkHttpUtils.post().url(request.url);

        } else {
            getBuilder = OkHttpUtils.get().url(request.url);
        }
        if (request.paramMap != null) {
            Set<String> keySets = request.paramMap.keySet();

            for (String key : keySets) {
                if (postFormBuilder!=null){
                    postFormBuilder.addParams(key,request.paramMap.get(key));

                }

                if (getBuilder!=null){
                    getBuilder.addParams(key,request.paramMap.get(key));
                }

            }
        }


        if (getBuilder!=null){

            getBuilder.build().execute(new Callback() {
                @Override
                public Object parseNetworkResponse(Response response, int id) throws Exception {

                    WXResponse wxResponse = new WXResponse();

                    wxResponse.statusCode= String.valueOf(response.code());

                    wxResponse.data=response.body().string();
                    if (listener != null) {
                        listener.onHttpFinish(wxResponse);
                    }

                    return null;
                }

                @Override
                public void onError(Call call, Exception e, int id) {


                    Log.d("mataa",e.getLocalizedMessage());

                }

                @Override
                public void onResponse(Object response, int id) {





                }
            });
        }
    }


}
