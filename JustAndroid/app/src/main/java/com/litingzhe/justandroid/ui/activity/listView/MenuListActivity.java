package com.litingzhe.justandroid.ui.activity.listView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.litingzhe.justandroid.R;
import com.litingzhe.justandroid.main.adapter.SampleListAdapter;
import com.litingzhe.justandroid.main.model.SampleModel;
import com.ningcui.mylibrary.app.base.AbBaseActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Copyright 李挺哲
 * 创建人：litingzhe
 * 邮箱：453971498@qq.com
 * Created by litingzhe on 2017/4/26 下午5:20.
 * 类描述：ListView 主页面
 */


public class MenuListActivity extends AbBaseActivity {


    @BindView(R.id.nav_back)
    LinearLayout navBack;
    @BindView(R.id.nav_title)
    TextView navTitle;
    @BindView(R.id.nav_right_icon)
    ImageView navRightIcon;
    @BindView(R.id.nav_right)
    LinearLayout navRight;
    @BindView(R.id.listView)
    ListView listView;
    private ArrayList sampleListViewArray;
    private SampleListAdapter sampleListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_listview_menu);
        ButterKnife.bind(this);

        navTitle.setText("一些列表");
        navBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        sampleListViewArray = new ArrayList();

        SampleModel sampleModel1 = new SampleModel("简单城市列表", R.mipmap.ic_launcher);
        SampleModel sampleModel2 = new SampleModel("侧滑按钮的列表是", R.mipmap.ic_launcher);
        SampleModel sampleModel3 = new SampleModel("拖拽的列表", R.mipmap.ic_launcher);

        sampleListViewArray.add(sampleModel1);
        sampleListViewArray.add(sampleModel2);
        sampleListViewArray.add(sampleModel3);
        sampleListAdapter = new SampleListAdapter(sampleListViewArray, mContext);

        listView.setAdapter(sampleListAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                Intent intent = new Intent();
                switch (position) {

                    case 0:
                        intent.setClass(mContext, CityListViewActivity.class);
                        startActivity(intent);
                        break;

                    case  1:
                        intent.setClass(mContext, SwipeLeftActivity.class);
                        startActivity(intent);

                        break;
                    default:
                        break;


                }


            }
        });


    }
}
