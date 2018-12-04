package com.litingzhe.justandroid.main.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.litingzhe.justandroid.R;
import com.litingzhe.justandroid.main.adapter.SampleListAdapter;
import com.litingzhe.justandroid.main.model.SampleModel;
import com.litingzhe.justandroid.netdb.db.activity.GreenDaoActivity;
import com.litingzhe.justandroid.netdb.net.activity.NetDemoActivity;
import com.ningcui.mylibrary.app.base.AbBaseFragment;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Copyright 李挺哲
 * 创建人：litingzhe
 * 邮箱：453971498@qq.com
 * Created by litingzhe on 2017/4/12 上午10:29.
 * 类描述：
 */


public class NetAndDBDataFragment extends AbBaseFragment {
    @BindView(R.id.nav_back)
    LinearLayout NavBack;
    @BindView(R.id.nav_title)
    TextView navTitle;
    Unbinder unbinder;
    @BindView(R.id.nav_right_icon)
    ImageView navRightIcon;
    @BindView(R.id.nav_right)
    LinearLayout navRight;
    @BindView(R.id.netAndDbListView)
    ListView netAndDbListView;
    Unbinder unbinder1;
    private Context mContext;
    private View rootView;
    private SampleListAdapter sampleListAdapter;
    private ArrayList uiSampleList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mContext = getActivity();
        if (rootView == null) {

            rootView = inflater.inflate(R.layout.fragment_db, null);
            unbinder = ButterKnife.bind(this, rootView);
            NavBack.setVisibility(View.GONE);
            navTitle.setText("网络层+数据持久化");

            initDataAndView();
        }


        AutoUtils.auto(rootView);


        unbinder1 = ButterKnife.bind(this, rootView);
        return rootView;
    }


    private void initDataAndView() {
        unbinder = ButterKnife.bind(this, rootView);
        NavBack.setVisibility(View.GONE);
        navTitle.setText("网络+数据库操作");
        uiSampleList = new ArrayList();

        SampleModel sampleModel1 = new SampleModel("retrofit+OkHttp+RxJava简单示例", R.drawable.net);
        SampleModel sampleModel2 = new SampleModel("GreenDao使用", R.mipmap.db_icon);
        uiSampleList.add(sampleModel1);
        uiSampleList.add(sampleModel2);

        sampleListAdapter = new SampleListAdapter(uiSampleList, mContext);
        netAndDbListView.setAdapter(sampleListAdapter);
        netAndDbListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                Intent intent = new Intent();

                switch (position) {
                    case 0:
                        intent.setClass(mContext, NetDemoActivity.class);
                        startActivity(intent);
                        break;
                    case 1:
                        intent.setClass(mContext, GreenDaoActivity.class);
                        startActivity(intent);
                        break;

                    case 2:

                        break;
                    default:
                        break;


                }

            }
        });


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
