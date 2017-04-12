package com.ningcui.mylibrary.viewLib.listener;

import android.widget.CompoundButton;

/**
 * Copyright 李挺哲
 * 创建人：litingzhe
 * 邮箱：453971498@qq.com
 * Created by litingzhe on 2017/4/11 下午3:23.
 * Info 自定义tab点击回调接口
 */
public abstract class AbOnCheckedChangedListener {

    public void onCheckedChanged(int position, boolean isChecked){};
    public void onCheckedChanged(CompoundButton buttonView,boolean isChecked){};
}
