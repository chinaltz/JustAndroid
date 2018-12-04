package com.litingzhe.justandroid.ui.listandGridView;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.litingzhe.justandroid.R;
import com.litingzhe.justandroid.ui.listandGridView.adapter.StickRecyleViewAdapter;
import com.ningcui.mylibrary.app.base.AbBaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Copyright 李挺哲
 * 创建人：litingzhe
 * 邮箱：453971498@qq.com
 * Created by litingzhe on 2017/5/12 上午9:59.
 * 类描述：
 */


public class StickyRecyleViewActivity extends AbBaseActivity {

    @BindView(R.id.nav_back)
    LinearLayout navBack;
    @BindView(R.id.nav_title)
    TextView navTitle;
    @BindView(R.id.nav_right_icon)
    ImageView navRightIcon;
    @BindView(R.id.nav_right)
    LinearLayout navRight;
    @BindView(R.id.main_recycler)
    RecyclerView mainRecycler;

    LinearLayout mImageView;
    private HeaderProductLayout mHeaderView;

    private int imageY=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_stickyview);
        ButterKnife.bind(this);
        mImageView = (LinearLayout) findViewById(R.id.header_product_image);


        navBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });

        navTitle.setText("带悬停的RecyleView");

        mainRecycler.setLayoutManager(new LinearLayoutManager(this));
        List<String> strings = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            strings.add("" + i);
        }
        mHeaderView = new HeaderProductLayout(this);
        StickRecyleViewAdapter simpleAdapter = new StickRecyleViewAdapter(strings);
        simpleAdapter.addHeaderView(mHeaderView);
        mainRecycler.setAdapter(simpleAdapter);

        mainRecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {


            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                int[] location = new int[2];
                mImageView.getLocationOnScreen(location);
                int y = location[1];
                Log.d("MYTAG","imageY == "+y);
                imageY = y;
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (mHeaderView == null) return;
                int getTop = mHeaderView.getDistanceY();
                Log.d("MYTAG","getTop == "+getTop);
                if (getTop <= imageY) {
                    mImageView.setVisibility(View.VISIBLE);
                } else {
                    mImageView.setY(0);
                    mImageView.setVisibility(View.GONE);
                }
            }
        });

    }
}
