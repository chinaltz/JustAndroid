package com.litingzhe.justandroid.main.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.litingzhe.justandroid.R;
import com.litingzhe.justandroid.designMode.mvp.activity.MvpGreenDaoActivity;
import com.litingzhe.justandroid.designMode.mvvm.activity.MvvmGreenDaoActivity;
import com.litingzhe.justandroid.main.adapter.SampleListAdapter;
import com.litingzhe.justandroid.main.model.SampleModel;
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
 * Created by litingzhe on 2017/4/12 上午10:46.
 * 类描述：
 */


public class DesignPatternFragment extends AbBaseFragment {

    @BindView(R.id.nav_back)
    LinearLayout NavBack;
    @BindView(R.id.nav_title)
    TextView navTitle;
    Unbinder unbinder;
    @BindView(R.id.designListView)
    ListView designListView;
    private Context mContext;
    private View rootView;
    private ArrayList uiSampleList;
    private SampleListAdapter sampleListAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mContext = getActivity();
        if (rootView == null) {

            rootView = inflater.inflate(R.layout.fragment_design, null);
            unbinder = ButterKnife.bind(this, rootView);
            NavBack.setVisibility(View.GONE);
            navTitle.setText("Android开发方式及思想");
        }


        uiSampleList = new ArrayList();

        SampleModel sampleModel1 = new SampleModel("简单MVP记事本", R.mipmap.rxicon);
        SampleModel sampleModel2 = new SampleModel("MVVM记事本", R.mipmap.db_icon);
        uiSampleList.add(sampleModel1);
        uiSampleList.add(sampleModel2);

        sampleListAdapter = new SampleListAdapter(uiSampleList, mContext);
        designListView.setAdapter(sampleListAdapter);

        designListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                Intent intent = new Intent();
                switch (position) {

                    case 0:
                        intent.setClass(mContext, MvpGreenDaoActivity.class);

                        startActivity(intent);
                        break;


                    case 1:

                        intent.setClass(mContext, MvvmGreenDaoActivity.class);

                        startActivity(intent);


                        break;
                }


            }
        });
        AutoUtils.auto(rootView);


        return rootView;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
