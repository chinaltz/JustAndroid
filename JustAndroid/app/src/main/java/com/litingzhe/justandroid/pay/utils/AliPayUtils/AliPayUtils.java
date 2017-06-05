package com.litingzhe.justandroid.pay.utils.AliPayUtils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;

import com.alipay.sdk.app.PayTask;
import com.litingzhe.justandroid.global.Constants;
import com.litingzhe.justandroid.pay.utils.AliPayUtils.callback.AliPayCallBack;
import com.litingzhe.justandroid.pay.utils.AliPayUtils.callback.AliPayCallBackV2;
import com.litingzhe.justandroid.pay.utils.AliPayUtils.v1.PayResult;
import com.litingzhe.justandroid.pay.utils.AliPayUtils.v1.SignUtils;
import com.litingzhe.justandroid.pay.utils.AliPayUtils.v2.OrderInfoUtil2_0;
import com.litingzhe.justandroid.pay.utils.AliPayUtils.v2.PayResultV2;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.Random;


//import org.xutils.common.task.AbsTask;
//import org.xutils.x;

/**
 * Created by litingzhe on 2016/12/27.
 */


public class AliPayUtils {

    private AliPayCallBack callBack;
    private AliPayCallBackV2 callBackV2;

    public AliPayCallBackV2 getCallBackV2() {
        return callBackV2;
    }

    public void setCallBackV2(AliPayCallBackV2 callBackV2) {
        this.callBackV2 = callBackV2;
    }

    public AliPayCallBack getCallBack() {
        return callBack;
    }

    public void setCallBack(AliPayCallBack callBack) {
        this.callBack = callBack;
    }

    private static AliPayUtils single = null;
    private Context mContex;

    public AliPayUtils(Context mContex) {
        this.mContex = mContex;
    }


    //静态工厂方法
    public static AliPayUtils getInstance(Context mContex) {
        if (single == null) {
            single = new AliPayUtils(mContex);
        }
        return single;
    }


    //subject 商品名称 body 商品详情  price 商品价格
    public void pay(String OrderNo,String subject, String body, String price,String notifyUrl,String out_trade_no) {
        if (TextUtils.isEmpty(Constants.AliAppId) || TextUtils.isEmpty(Constants.RSA_PRIVATE)
                || TextUtils.isEmpty(Constants.RSA_PRIVATE)) {
            new AlertDialog.Builder(mContex)
                    .setTitle("警告")
                    .setMessage("需要配置PARTNER | RSA_PRIVATE| SELLER")
                    .setPositiveButton("确定",
                            new DialogInterface.OnClickListener() {
                                public void onClick(
                                        DialogInterface dialoginterface, int i) {
                                    //
                                    ((Activity) mContex).finish();
                                }
                            }).show();
            return;
        }
        // 订单


        final String taskProductName = subject;
        final String taskProductbody = body;
        final String taskProductPrice = ""+price;
        final String taskNotifyUrl = notifyUrl;
        final String taskoutTradeNo = ""+out_trade_no;
        final String taskTradeNo = OrderNo;


        AsyncTask<Void, Void, String> asyncTask=new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                String orderInfo = getOrderInfo(taskTradeNo,taskProductName, taskProductbody,taskProductPrice,taskNotifyUrl, taskoutTradeNo);
                Log.e("GFH", "订单信息=" + orderInfo);
                // 对订单做RSA 签名
                String sign = sign(orderInfo);
                try {
                    // 仅需对sign 做URL编码
                    Log.e("GFH", "00=" + sign);
                    sign = URLEncoder.encode(sign, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

                // 完整的符合支付宝参数规范的订单信息
                final String payInfo = orderInfo + "&sign=\"" + sign + "\"&"
                        + getSignType();

                PayTask alipay = new PayTask((Activity) mContex);
                // 调用支付接口，获取支付结果
                String result = alipay.pay(payInfo, true);

                return result;
            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);

                PayResult payResult = new PayResult(result);
//
//
                // 支付宝返回此次支付结果及加签，建议对支付宝签名信息拿签约时支付宝提供的公钥做验签
                String resultInfo = payResult.getResult();


                if (callBack != null) {

                    callBack.onAliPayResultCallBack(payResult);
                }
//
            }
        };
        asyncTask.execute();

    }






    //subject 商品名称 body 商品详情  price 商品价格
    public void payV2(String OrderNo,String subject, String body, String price,String notifyUrl,String out_trade_no) {
        if (TextUtils.isEmpty(Constants.AliAppId) || TextUtils.isEmpty(Constants.RSA_PRIVATE)
                || TextUtils.isEmpty(Constants.RSA_PRIVATE)) {
            new AlertDialog.Builder(mContex)
                    .setTitle("警告")
                    .setMessage("需要配置PARTNER | RSA_PRIVATE| SELLER")
                    .setPositiveButton("确定",
                            new DialogInterface.OnClickListener() {
                                public void onClick(
                                        DialogInterface dialoginterface, int i) {
                                    //
                                    ((Activity) mContex).finish();
                                }
                            }).show();
            return;
        }
        // 订单

        final String taskProductName = subject;
        final String taskProductbody = body;
        final String taskProductPrice = ""+price;
        final String taskNotifyUrl = notifyUrl;
        final String taskoutTradeNo = ""+out_trade_no;
        final String taskTradeNo = OrderNo;



        AsyncTask<Void, Void,  Map<String, String>> asyncTask=new AsyncTask<Void, Void,  Map<String, String>>() {
            @Override
            protected  Map<String, String> doInBackground(Void... params) {

                Map<String, String> mapParams = OrderInfoUtil2_0.buildOrderParamMap( taskTradeNo, taskProductName,  taskProductbody,  taskProductPrice, taskNotifyUrl, taskoutTradeNo);
                String orderParam = OrderInfoUtil2_0.buildOrderParam(mapParams);
                String sign = OrderInfoUtil2_0.getSign(mapParams, Constants.RSA_PRIVATE, true);
                final String orderInfo = orderParam + "&" + sign;
//                EnvUtils.setEnv(EnvUtils.EnvEnum.SANDBOX);
                PayTask alipay = new PayTask((Activity) mContex);
                // 调用支付接口，获取支付结果
                Map<String, String> result = alipay.payV2(orderInfo, true);
                return result;
            }

            @Override
            protected void onPostExecute( Map<String, String> s) {
                super.onPostExecute(s);
                PayResultV2 payResult = new PayResultV2(s);
//

                // 支付宝返回此次支付结果及加签，建议对支付宝签名信息拿签约时支付宝提供的公钥做验签
                String resultInfo = payResult.getResult();


                if (callBackV2 != null) {

                    callBackV2.onAliPayResultCallBack(payResult);
                }
//

            }
        };
        asyncTask.execute();
    }


    /**
     * create the order info. 创建订单信息
     */
    public String getOrderInfo(String  OrderNo,String subject, String body, String price,String notifyUrl,String out_trade_no) {

        // 签约合作者身份ID
        String orderInfo = "partner=" + "\"" + Constants.AliAppId + "\"";

        // 签约卖家支付宝账号
        orderInfo += "&seller_id=" + "\"" + Constants.RSA_PRIVATE + "\"";

        // 商户网站唯一订单号
        orderInfo += "&out_trade_no=" + "\"" + out_trade_no+ "\"";

        // 商品名称
        orderInfo += "&subject=" + "\"" + subject + "\"";

        // 商品详情
        orderInfo += "&body=" + "\"" + body + "\"";

        // 商品金额
        orderInfo += "&total_fee=" + "\"" + price + "\"";

        // 服务器异步通知页面路径
//		orderInfo += "&notify_url=" + "\"" + "http://notify.msp.hk/notify.htm"
//				+ "\"";
        orderInfo += "&notify_url=" + "\"" + notifyUrl
                + "\"";
//		orderInfo += "&notify_url=" + "\"" + "http://211.137.181.202:18081/sd_mobile_service/notify.do"
//				+ "\"";
        // 服务接口名称， 固定值
        orderInfo += "&service=\"mobile.securitypay.pay\"";

        // 支付类型， 固定值
        orderInfo += "&payment_type=\"1\"";

        // 参数编码， 固定值
        orderInfo += "&_input_charset=\"utf-8\"";

        // 设置未付款交易的超时时间
        // 默认30分钟，一旦超时，该笔交易就会自动被关闭。
        // 取值范围：1m～15d。
        // m-分钟，h-小时，d-天，1c-当天（无论交易何时创建，都在0点关闭）。
        // 该参数数值不接受小数点，如1.5h，可转换为90m。
//        orderInfo += "&it_b_pay=\"30m\"";

        // extern_token为经过快登授权获取到的alipay_open_id,带上此参数用户将使用授权的账户进行支付
        // orderInfo += "&extern_token=" + "\"" + extern_token + "\"";

        // 支付宝处理完请求后，当前页面跳转到商户指定页面的路径，可空
        orderInfo += "&return_url=\"m.alipay.com\"";

        // 调用银行卡支付，需配置此参数，参与签名， 固定值 （需要签约《无线银行卡快捷支付》才能使用）
        // orderInfo += "&paymethod=\"expressGateway\"";

        orderInfo += "&passback_params=" + "\""+OrderNo + "\"";


        return orderInfo;
    }

    /**
     * get the out_trade_no for an order. 生成商户订单号，该值在商户端应保持唯一（可自定义格式规范）
     */
    public String getOutTradeNo() {
        SimpleDateFormat format = new SimpleDateFormat("MMddHHmmss",
                Locale.getDefault());
        Date date = new Date();
        String key = format.format(date);

        Random r = new Random();
        key = key + r.nextInt();
        key = key.substring(0, 15);
        return key;
    }

//    /**
//     * sign the order info. 对订单信息进行签名
//     *
//     * @param content 待签名订单信息
//     */
    public String sign(String content) {
        return SignUtils.sign(content, Constants.RSA_PRIVATE);
    }

    /**
     * get the sign type we use. 获取签名方式
     */
    public String getSignType() {
        return "sign_type=\"RSA\"";
    }


}
