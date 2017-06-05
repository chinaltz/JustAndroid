package com.litingzhe.justandroid.pay.utils.WChatPayUtils;

/**
 *  * Copyright 李挺哲
 * 创建人：litingzhe
 * 邮箱：453971498@qq.com
 * Created by litingzhe on 2017/1/10.
 */

public class ParamModel  implements Comparable<ParamModel>{

    private  String  key;
    private String value;

    public ParamModel(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public int compareTo(ParamModel another) {


        return this.getKey().compareTo(another.getKey());

    }
}
