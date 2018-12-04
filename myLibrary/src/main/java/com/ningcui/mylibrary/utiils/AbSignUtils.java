package com.ningcui.mylibrary.utiils;

import com.ningcui.mylibrary.model.ParamModel;

import java.util.List;



/**
 * Copyright 李挺哲
 * 创建人：litingzhe
 * 邮箱：453971498@qq.com
 * Created by litingzhe on 2017/4/11 下午3:23.
 *  Info 微信支付签名辅助类
 */

public class AbSignUtils {

    public static String genPackageSign(List<ParamModel> params,String Key) {
        StringBuilder sb = new StringBuilder();


        for (int i = 0; i < params.size(); i++) {
            sb.append(params.get(i).getKey());
            sb.append('=');
            sb.append(params.get(i).getValue());
            sb.append('&');
        }
        sb.append("key=");
        sb.append(Key);
//AbLogUtil.i(Constants.MYTAG,""+sb);
        String packageSign  =   AbMd5.MD5(sb.toString()).toUpperCase();

//        String packageSign = MD5.getMessageDigest(sb.toString().getBytes()).toUpperCase();

        return packageSign;
    }



}
