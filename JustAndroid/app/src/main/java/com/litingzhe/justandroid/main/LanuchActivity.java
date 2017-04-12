package com.litingzhe.justandroid.main;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.litingzhe.justandroid.R;
import com.litingzhe.justandroid.main.activity.MainActivity;
import com.ningcui.mylibrary.app.base.AbBaseActivity;

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
    private MyCount myCount = new MyCount(5000, 1000);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lanuch);
        ButterKnife.bind(this);
//        countButton.setText("跳过|"+"3s");
        myCount.start();

    }



    @OnClick(R.id.countButton)
    public void onViewClicked() {

        Intent in=new Intent(mConetxt, MainActivity.class);
        startActivity(in);
        finish();

    }





    public class MyCount extends CountDownTimer {


        public MyCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onFinish() {

            Intent in=new Intent(mConetxt, MainActivity.class);
            startActivity(in);
            finish();

        }

        @Override
        public void onTick(long millisUntilFinished) {

            long second=millisUntilFinished/1000;
            countButton.setText("跳过|"+second+"s");

        }

    }


}
