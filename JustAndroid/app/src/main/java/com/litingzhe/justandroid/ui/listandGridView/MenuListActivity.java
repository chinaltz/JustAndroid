package com.litingzhe.justandroid.ui.listandGridView;

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
import com.litingzhe.justandroid.ui.listandGridView.wechatCircle.activity.WeChatCircleActivity;
import com.ningcui.mylibrary.app.base.AbBaseActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Copyright 李挺哲
 * 创建人：litingzhe
 * 邮箱：453971498@qq.com
 * Created by litingzhe on 2017/4/26 下午5:20.
 * 类描述：列表 表格 主页面
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

        navTitle.setText("一些列表和表格");
        navBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        sampleListViewArray = new ArrayList();

        SampleModel sampleModel1 = new SampleModel("简单城市列表", R.mipmap.ic_launcher);
        SampleModel sampleModel2 = new SampleModel("侧滑按钮的列表使用", R.mipmap.ic_launcher);
        SampleModel sampleModel3 = new SampleModel("RecycleView简单使用", R.mipmap.ic_launcher);
        SampleModel sampleModel4 = new SampleModel("RecycleView瀑布流", R.mipmap.ic_launcher);
        SampleModel sampleModel5 = new SampleModel("可拖拽的RecyleView", R.mipmap.ic_launcher);
        SampleModel sampleModel6 = new SampleModel("九宫格解锁", R.mipmap.ic_launcher);
        SampleModel sampleModel8 = new SampleModel("仿朋友圈", R.mipmap.ic_launcher);
        SampleModel sampleModel7 = new SampleModel("简单的聊天列表", R.mipmap.ic_launcher);

        SampleModel sampleModel9 = new SampleModel("悬停效果的列表/表格", R.mipmap.ic_launcher);

        sampleListViewArray.add(sampleModel1);
        sampleListViewArray.add(sampleModel2);
        sampleListViewArray.add(sampleModel3);
        sampleListViewArray.add(sampleModel4);
        sampleListViewArray.add(sampleModel5);
        sampleListViewArray.add(sampleModel6);
        sampleListViewArray.add(sampleModel7);
        sampleListViewArray.add(sampleModel8);
        sampleListViewArray.add(sampleModel9);

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

                    case 1:
                        intent.setClass(mContext, SwipeLeftActivity.class);
                        startActivity(intent);

                        break;

                    case 2:
                        intent.setClass(mContext, SimpleRecyleViewActivity.class);
                        startActivity(intent);

                        break;

                    case 3:
                        intent.setClass(mContext, StaggeredActivity.class);
                        startActivity(intent);

                        break;

                    case 4:
                        intent.setClass(mContext, DragRecyleViewActivity.class);
                        startActivity(intent);
                        break;

                    case 5:
                        intent.setClass(mContext, NineLockActivity.class);
                        startActivity(intent);
                        break;

                    case 6:
                        intent.setClass(mContext, ChatListActivity.class);
                        startActivity(intent);
                        break;

                    case  7:

                        intent.setClass(mContext, WeChatCircleActivity.class);
                        startActivity(intent);
                        break;

                    case  8:

                        intent.setClass(mContext, StickyRecyleViewActivity.class);
                        startActivity(intent);
                        break;

                    default:

                        break;


                }


            }
        });


    }
}
