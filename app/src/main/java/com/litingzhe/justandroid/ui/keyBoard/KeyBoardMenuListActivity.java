package com.litingzhe.justandroid.ui.keyBoard;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.iflytek.cloud.thirdparty.V;
import com.litingzhe.justandroid.R;
import com.litingzhe.justandroid.main.adapter.SampleListAdapter;
import com.litingzhe.justandroid.main.model.SampleModel;
import com.litingzhe.justandroid.ui.keyBoard.activity.CarNumInputActivty;
import com.litingzhe.justandroid.ui.keyBoard.activity.WeiPayKeyBoardActivity;
import com.ningcui.mylibrary.app.base.AbBaseActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Copyright 李挺哲
 * 创建人：litingzhe
 * 邮箱：453971498@qq.com
 * Created by litingzhe on 2017/6/5 上午10:57.
 * 类描述：键盘输入列表
 */


public class KeyBoardMenuListActivity extends AbBaseActivity {


    @BindView(R.id.nav_back)
    LinearLayout navBack;
    @BindView(R.id.nav_title)
    TextView navTitle;
    @BindView(R.id.nav_right_icon)
    ImageView navRightIcon;
    @BindView(R.id.nav_right_text)
    TextView navRightText;
    @BindView(R.id.nav_right)
    LinearLayout navRight;
    @BindView(R.id.listView)
    ListView listView;
    private ArrayList uiSampleList;
    private SampleListAdapter sampleListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_listview_menu);
        ButterKnife.bind(this);
        navBack.setVisibility(View.VISIBLE);
        uiSampleList = new ArrayList();

        SampleModel sampleModel1 = new SampleModel("车牌号录入", R.mipmap.dialogicon);
        SampleModel sampleModel2 = new SampleModel("密码输入", R.mipmap.ui_refresh);

        uiSampleList.add(sampleModel1);
        uiSampleList.add(sampleModel2);

        sampleListAdapter = new SampleListAdapter(uiSampleList, mContext);

        listView.setAdapter(sampleListAdapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent =new Intent();
                switch (position) {


                    case 0:
                        intent.setClass(mContext,CarNumInputActivty.class);
                        startActivity(intent);

                        break;

                    case 1:
                        intent.setClass(mContext,WeiPayKeyBoardActivity.class);
                        startActivity(intent);

                        break;
                }


            }
        });


    }
}
