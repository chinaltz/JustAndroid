package com.litingzhe.justandroid.ui;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.litingzhe.justandroid.R;
import com.ningcui.mylibrary.app.base.AbBaseActivity;
import com.ningcui.mylibrary.viewLib.listener.AbOnProgressListener;
import com.ningcui.mylibrary.viewLib.progress.AbCircleProgressBar;
import com.ningcui.mylibrary.viewLib.progress.AbHorizontalProgressBar;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Copyright 李挺哲
 * 创建人：litingzhe
 * 邮箱：453971498@qq.com
 * Created by litingzhe on 2017/5/12 下午5:12.
 * 类描述： 进度条
 */


public class MenuProgressBarActivity extends AbBaseActivity {


    @BindView(R.id.nav_back)
    LinearLayout navBack;
    @BindView(R.id.nav_title)
    TextView navTitle;
    @BindView(R.id.nav_right_icon)
    ImageView navRightIcon;
    @BindView(R.id.nav_right)
    LinearLayout navRight;
    @BindView(R.id.horizontalProgressBar)
    AbHorizontalProgressBar horizontalProgressBar;
    @BindView(R.id.numberText)
    TextView numberText;
    @BindView(R.id.maxText)
    TextView maxText;
    @BindView(R.id.stepText)
    TextView stepText;
    @BindView(R.id.circle_numberText)
    TextView circleNumberText;
    @BindView(R.id.circle_maxText)
    TextView circleMaxText;
    @BindView(R.id.circleProgressBar)
    AbCircleProgressBar circleProgressBar;

    // 最大1000
    private int max = 100;
    private int progress = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_progress);
        ButterKnife.bind(this);
        navTitle.setText("进度条");
        navBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });


        circleMaxText.setText("总共  "+String.valueOf(max));
        horizontalProgressBar.setMax(max);
        horizontalProgressBar.setProgress(progress);


        startAddProgress();

        horizontalProgressBar.setOnProgressListener(new AbOnProgressListener() {
            @Override
            public void onProgress(int i) {

            }

            @Override
            public void onComplete() {
                progress = 0;
                horizontalProgressBar.reset();
                circleProgressBar.reset();
            }
        });



    }


    public void startAddProgress() {
        progress = progress+10;
        numberText.setText(String.valueOf(progress));
        circleNumberText.setText(String.valueOf(progress));
        horizontalProgressBar.setProgress(progress);
        circleProgressBar.setProgress(progress);
        mUpdateHandler.sendEmptyMessageDelayed(1, 1000);
    }

    private Handler mUpdateHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    startAddProgress();
                    break;
            }
            super.handleMessage(msg);
        }
    };

    private Runnable mUpdateRunnable = new Runnable() {
        public void run() {
            while (true) {
                Message message = new Message();
                message.what = 1;
                mUpdateHandler.sendMessage(message);
                try {
                    // 更新间隔毫秒数
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    };
}
