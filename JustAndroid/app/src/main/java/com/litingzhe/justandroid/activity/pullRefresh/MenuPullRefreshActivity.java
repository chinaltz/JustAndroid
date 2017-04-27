package com.litingzhe.justandroid.activity.pullRefresh;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.litingzhe.justandroid.R;
import com.ningcui.mylibrary.app.base.AbBaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Copyright 李挺哲
 * 创建人：litingzhe
 * 邮箱：453971498@qq.com
 * Created by litingzhe on 2017/4/21 下午4:48.
 * 类描述：
 */


public class MenuPullRefreshActivity extends AbBaseActivity {


    @BindView(R.id.nav_back)
    LinearLayout navBack;
    @BindView(R.id.nav_title)
    TextView navTitle;
    @BindView(R.id.nav_right_icon)
    ImageView navRightIcon;
    @BindView(R.id.nav_right)
    LinearLayout navRight;
    @BindView(R.id.swipeRefresh)
    Button swipeRefresh;
    @BindView(R.id.pullRefresh)
    Button pullRefresh;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_pullrefresh);
        ButterKnife.bind(this);
        navTitle.setText("下拉刷新");
        navBack.setVisibility(View.VISIBLE);
        navBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });
        swipeRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent= new Intent(mContext,SwipeRefreshLayoutActivity.class);

                startActivity(intent);

            }
        });

        pullRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent= new Intent(mContext,PullToReFreshLayoutActivity.class);

                startActivity(intent);

            }
        });

    }
}
