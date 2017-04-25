package com.litingzhe.justandroid.main;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.litingzhe.justandroid.R;
import com.litingzhe.justandroid.main.activity.MainActivity;
import com.ningcui.mylibrary.app.base.AbBaseActivity;
import com.ningcui.mylibrary.utiils.AbToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Copyright 李挺哲
 * 创建人：litingzhe
 * 邮箱：453971498@qq.com
 * Created by litingzhe on 2017/4/11 下午5:30.
 * 类描述：启动页面
 */


public class LanuchActivity extends AbBaseActivity {

    @BindView(R.id.lanuchImage)
    ImageView lanuchImage;
    @BindView(R.id.countButton)
    Button countButton;
    private MyCount myCount;

    private boolean isExit = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lanuch);
        ButterKnife.bind(this);
        //初始化计时器
        myCount = new MyCount(5000, 1000);
        //开启
        myCount.start();

    }


    @OnClick(R.id.countButton)
    public void onViewClicked() {

        myCount.cancel();
        Intent in = new Intent(mContext, MainActivity.class);
        startActivity(in);
        finish();

    }


    public class MyCount extends CountDownTimer {


        public MyCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onFinish() {

            Intent in = new Intent(mContext, MainActivity.class);
            startActivity(in);
            finish();

        }

        @Override
        public void onTick(long millisUntilFinished) {

            long second = millisUntilFinished / 1000;
            countButton.setText("跳过|" + second + "s");

        }

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {


            if (isExit == false) {
                isExit = true;
                AbToastUtil.showToast(mContext, "再按一次退出程序");
                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        isExit = false;
                    }

                }, 2000);

            } else {

                myCount.cancel();
                finish();

            }


            return false;
        }
        return super.onKeyDown(keyCode, event);

    }


}
