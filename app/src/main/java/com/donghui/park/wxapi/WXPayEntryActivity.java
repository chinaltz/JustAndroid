package com.donghui.park.wxapi;


import com.litingzhe.justandroid.R;
import com.litingzhe.justandroid.global.Constants;
import com.ningcui.mylibrary.utiils.AbToastUtil;
import com.tencent.mm.sdk.constants.ConstantsAPI;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;


public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {

    private static final String TAG = "MicroMsg.SDKSample.WXPayEntryActivity";

    private IWXAPI api;
    private Context mContext;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pay_result);
        mContext = this;

        api = WXAPIFactory.createWXAPI(this, Constants.APP_ID);

        api.handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq req) {
    }

    @Override
    public void onResp(BaseResp resp) {


        if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {


            if (resp.errCode == 0) {

                AbToastUtil.showToast(mContext, "支付成功");

//                Intent i = new Intent("com.action.ChargeWalletReceiver");
//                sendBroadcast(i);
//                Intent i1 = new Intent("com.action.CurrentOrderReceiver");
//                sendBroadcast(i1);

            }
            if (resp.errCode == -2) {

                AbToastUtil.showToast(mContext, "支付取消");
//                Intent i = new Intent("com.action.ChargeWalletReceiver");
//                sendBroadcast(i);
            }


            finish();


        }
    }
}