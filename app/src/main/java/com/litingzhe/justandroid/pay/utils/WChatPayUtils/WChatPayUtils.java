package com.litingzhe.justandroid.pay.utils.WChatPayUtils;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.util.Xml;

import com.litingzhe.justandroid.global.Constants;
import com.litingzhe.justandroid.utils.netutils.OkHttp3Utils;
import com.ningcui.mylibrary.utiils.AbLogUtil;
import com.ningcui.mylibrary.utiils.AbStrUtil;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;


import org.xmlpull.v1.XmlPullParser;


import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.BufferedSink;

import static android.content.ContentValues.TAG;

/**
 * Created by litingzhe on 2016/12/26.
 */

public class WChatPayUtils {


    private PayReq req;
    private IWXAPI msgApi = null;
    private Context mContex;

    public PayReq getReq() {
        return req;
    }

    public void setReq(PayReq req) {
        this.req = req;
    }

    public IWXAPI getMsgApi() {
        return msgApi;
    }

    public void setMsgApi(IWXAPI msgApi) {
        this.msgApi = msgApi;
    }

    public Context getmContex() {
        return mContex;
    }

    public void setmContex(Context mContex) {
        this.mContex = mContex;
    }

    private static WChatPayUtils single = null;

    //静态工厂方法
    public static WChatPayUtils getInstance(Context mContex) {
        if (single == null) {
            single = new WChatPayUtils(mContex);
            single.msgApi = WXAPIFactory.createWXAPI(mContex, null);
            single.req = new PayReq();

        }
        return single;
    }


    public WChatPayUtils(Context mContex) {
        this.mContex = mContex;
    }


    public Map<String, String> decodeXml(String content) {

        try {
            Map<String, String> xml = new HashMap<String, String>();
            XmlPullParser parser = Xml.newPullParser();
            parser.setInput(new StringReader(content));
            int event = parser.getEventType();
            while (event != XmlPullParser.END_DOCUMENT) {

                String nodeName = parser.getName();
                switch (event) {
                    case XmlPullParser.START_DOCUMENT:

                        break;
                    case XmlPullParser.START_TAG:

                        if ("xml".equals(nodeName) == false) {
                            //实例化student对象
                            xml.put(nodeName, parser.nextText());
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        break;
                }
                event = parser.next();
            }

            return xml;
        } catch (Exception e) {
            Log.e("orion", e.toString());
        }
        return null;

    }


    private String genNonceStr() {
        Random random = new Random();
        return MD5.getMessageDigest(String.valueOf(random.nextInt(10000)).getBytes());
    }

    private long genTimeStamp() {
        return System.currentTimeMillis() / 1000;
    }

//
//    private String genOutTradNo() {
//        Random random = new Random();
//        return MD5.getMessageDigest(String.valueOf(random.nextInt(10000)).getBytes());
//    }


    //
    private String genProductArgs(String out_trade_no, String notify_url, String total_fee, String body, String orderNo) {
        StringBuffer xml = new StringBuffer();

        try {
            xml.append("</xml>");
            List<ParamModel> packageParams = new LinkedList<ParamModel>();
            packageParams.add(new ParamModel("appid", Constants.APP_ID));
            packageParams.add(new ParamModel("body", body));
            packageParams.add(new ParamModel("mch_id", Constants.MCH_ID));
            if (AbStrUtil.isEmpty(orderNo)) {
                packageParams.add(new ParamModel("nonce_str", out_trade_no));
            } else {
                packageParams.add(new ParamModel("nonce_str", orderNo));
            }

            packageParams.add(new ParamModel("notify_url", notify_url));
            packageParams.add(new ParamModel("out_trade_no", out_trade_no));
            packageParams.add(new ParamModel("spbill_create_ip", "127.0.0.1"));
            packageParams.add(new ParamModel("total_fee", total_fee));
            packageParams.add(new ParamModel("trade_type", "APP"));


            String sign = genPackageSign(packageParams);
            packageParams.add(new ParamModel("sign", sign));


            String xmlstring = toXml(packageParams);

            return xmlstring;

        } catch (Exception e) {
            Log.e(TAG, "genProductArgs fail, ex = " + e.getMessage());
            return null;
        }


    }


    /**
     * 生成签名
     */

    private String genPackageSign(List<ParamModel> params) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < params.size(); i++) {
            sb.append(params.get(i).getKey());
            sb.append('=');
            sb.append(params.get(i).getValue());
            sb.append('&');
        }
        sb.append("key=");
        sb.append(Constants.API_KEY);


        String packageSign = MD5.getMessageDigest(sb.toString().getBytes()).toUpperCase();
        Log.e("orion", packageSign);
        return packageSign;
    }

    private String toXml(List<ParamModel> params) {
        StringBuilder sb = new StringBuilder();
        sb.append("<xml>");
        for (int i = 0; i < params.size(); i++) {
            sb.append("<" + params.get(i).getKey() + ">");


            sb.append(params.get(i).getValue());
            sb.append("</" + params.get(i).getKey() + ">");
        }
        sb.append("</xml>");

        Log.e("orion", sb.toString());
        return sb.toString();
    }

    private void genPayReq(Map<String, String> result) {

        req.appId = Constants.APP_ID;
        req.partnerId = Constants.MCH_ID;
        req.prepayId = result.get("prepay_id");
        req.packageValue = "Sign=WXPay";
        req.nonceStr = genNonceStr();
        req.timeStamp = String.valueOf(genTimeStamp());


        List<ParamModel> signParams = new LinkedList<ParamModel>();
        signParams.add(new ParamModel("appid", req.appId));
        signParams.add(new ParamModel("noncestr", req.nonceStr));
        signParams.add(new ParamModel("package", req.packageValue));
        signParams.add(new ParamModel("partnerid", req.partnerId));
        signParams.add(new ParamModel("prepayid", req.prepayId));
        signParams.add(new ParamModel("timestamp", req.timeStamp));

        req.sign = genAppSign(signParams);

        sendPayReq();

//        Log.e(Constants.MYTAG, signParams.toString());

    }

    private void sendPayReq() {

        Log.i("sendPayReq", "req===" + req.toString());
        msgApi.registerApp(Constants.APP_ID);
        msgApi.sendReq(req);
    }


    private String genAppSign(List<ParamModel> params) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < params.size(); i++) {
            sb.append(params.get(i).getKey());
            sb.append('=');
            sb.append(params.get(i).getValue());
            sb.append('&');
        }
        sb.append("key=");
        sb.append(Constants.API_KEY);

//        this.sb.append("sign str\n" + sb.toString() + "\n\n");
        String appSign = MD5.getMessageDigest(sb.toString().getBytes()).toUpperCase();
        Log.e("orion", appSign);
        return appSign;

    }


    public void toPayForWeiChat(String outorderNo, String notify_url, String total_fee, String body, String orderNo) {

        final String entity = genProductArgs(outorderNo, notify_url, total_fee, body, orderNo);


        AsyncTask<Void, Void, String> asyncTask = new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                String url = "https://api.mch.weixin.qq.com/pay/unifiedorder";

                OkHttpClient client = OkHttp3Utils.getOkHttpClient();

                Request request = new Request.Builder()
                        .url(url)
                        .post(RequestBody.create(MediaType.parse("text/xml; charset=utf-8"), entity))
                        .build();
                Response response = null;
                try {
                    response = client.newCall(request).execute();
                    if (response.isSuccessful()) {
                        String str = response.body().string();
                        System.out.println("服务器响应为: " + str);
                        return str;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }


                return null;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

                AbLogUtil.i(Constants.MYTAG, "微信预订单" + s);
//
//                AbToastUtil.showToast(mContex,""+result);

                Map<String, String> xml = decodeXml(s);


                genPayReq(xml);
//


            }
        };

//
        asyncTask.execute();
    }

}
