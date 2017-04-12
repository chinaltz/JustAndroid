package com.ningcui.mylibrary.app.base;


import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.ningcui.mylibrary.app.global.AbActivityManager;
import com.ningcui.mylibrary.utiils.AbSharedUtil;
import com.zhy.autolayout.AutoLayoutActivity;




/**
 * Copyright 李挺哲
 * 创建人：litingzhe
 * 邮箱：453971498@qq.com
 * Created by litingzhe on 2017/4/11 下午3:23.
 * Info 所有Activity要继承这个父类，便于统一管理
 */
public abstract class AbBaseActivity extends AutoLayoutActivity {
public Context mConetxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        mConetxt=this;

        AbActivityManager.getInstance().addActivity(this);

    }


    /**
     * 返回默认
     *
     * @param view
     */
    public void back(View view) {
        finish();
    }

    /**
     * 结束
     */
    @Override
    public void finish() {
        AbActivityManager.getInstance().removeActivity(this);
        super.finish();
    }


}

