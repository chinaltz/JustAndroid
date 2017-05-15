package com.litingzhe.justandroid.ui.segmentAndSelectMenu;

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
import com.litingzhe.justandroid.ui.segmentAndSelectMenu.dropMenu.activity.DropMenuActivity;
import com.litingzhe.justandroid.ui.segmentAndSelectMenu.segment.SegmentActivity;
import com.litingzhe.justandroid.ui.segmentAndSelectMenu.segment.TabLayoutActivity;
import com.ningcui.mylibrary.app.base.AbBaseActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Copyright 李挺哲
 * 创建人：litingzhe
 * 邮箱：453971498@qq.com
 * Created by litingzhe on 2017/5/12 下午2:13.
 * 类描述：分段选择与下拉菜单（类似美团）
 */


public class MeunSegmentAndDropActivity extends AbBaseActivity {


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

        setContentView(R.layout.activity_segmentanddropmenu);
        ButterKnife.bind(this);

        navBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        navTitle.setText("下拉菜单与分段选择");

        sampleListViewArray = new ArrayList();

        SampleModel sampleModel1 = new SampleModel("仿美团下拉菜单", R.mipmap.ic_launcher);
        SampleModel sampleModel2 = new SampleModel("仿IOSSegment", R.mipmap.ic_launcher);
        SampleModel sampleModel3 = new SampleModel("TabLayout实现Tab", R.mipmap.ic_launcher);

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
                        intent.setClass(mContext, DropMenuActivity.class);

                        startActivity(intent);


                        break;

                    case 1:
                        intent.setClass(mContext, SegmentActivity.class);

                        startActivity(intent);

                        break;

                    case 2:
                        intent.setClass(mContext, TabLayoutActivity.class);

                        startActivity(intent);

                        break;

                    default:
                        break;
                }


            }
        });

    }


}
