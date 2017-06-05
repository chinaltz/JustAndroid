package com.litingzhe.justandroid.pay.utils.WChatPayUtils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.litingzhe.justandroid.global.Constants;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;


public class AppRegister extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		final IWXAPI api = WXAPIFactory.createWXAPI(context, null);

	boolean fg=	api.registerApp(Constants.APP_ID);
	Log.i("GFH", "fg======================"+fg);
	}
}
