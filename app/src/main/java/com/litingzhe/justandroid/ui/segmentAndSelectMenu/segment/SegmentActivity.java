package com.litingzhe.justandroid.ui.segmentAndSelectMenu.segment;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.litingzhe.justandroid.R;
import com.ningcui.mylibrary.app.base.AbBaseActivity;
import com.ningcui.mylibrary.viewLib.segment.AwesomeRadioButton;
import com.ningcui.mylibrary.viewLib.segment.SegmentedGroup;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Copyright 李挺哲
 * 创建人：litingzhe
 * 邮箱：453971498@qq.com
 * Created by litingzhe on 2017/5/15 上午10:10.
 * 类描述：仿IOS 分段选择
 */


public class SegmentActivity extends AbBaseActivity {

    @BindView(R.id.nav_back)
    LinearLayout navBack;
    @BindView(R.id.nav_title)
    TextView navTitle;
    @BindView(R.id.nav_right_icon)
    ImageView navRightIcon;
    @BindView(R.id.nav_right)
    LinearLayout navRight;
    @BindView(R.id.button21)
    RadioButton button21;
    @BindView(R.id.button22)
    RadioButton button22;
    @BindView(R.id.segmented2)
    SegmentedGroup segmented2;
    @BindView(R.id.button31)
    RadioButton button31;
    @BindView(R.id.button32)
    RadioButton button32;
    @BindView(R.id.button33)
    RadioButton button33;
    @BindView(R.id.segmented3)
    SegmentedGroup segmented3;
    @BindView(R.id.button41)
    AwesomeRadioButton button41;
    @BindView(R.id.button42)
    AwesomeRadioButton button42;
    @BindView(R.id.button43)
    AwesomeRadioButton button43;
    @BindView(R.id.segmented4)
    SegmentedGroup segmented4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_segment);
        ButterKnife.bind(this);
        navBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });

        navTitle.setText("分段选择");


    }
}
