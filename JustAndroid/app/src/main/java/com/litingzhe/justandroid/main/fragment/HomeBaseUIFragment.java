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
import com.litingzhe.justandroid.ui.activity.MenuUiDialogActivity;
import com.litingzhe.justandroid.ui.activity.listandGridView.MenuListActivity;
import com.litingzhe.justandroid.ui.activity.pullRefresh.MenuPullRefreshActivity;
import com.litingzhe.justandroid.main.adapter.SampleListAdapter;
import com.litingzhe.justandroid.main.model.SampleModel;
import com.ningcui.mylibrary.app.base.AbBaseFragment;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Copyright 李挺哲
 * 创建人：litingzhe
 * 邮箱：453971498@qq.com
 * Created by litingzhe on 2017/4/12 上午10:27.
 * 类描述：
 */


public class HomeBaseUIFragment extends AbBaseFragment {

    @BindView(R.id.nav_back)
    LinearLayout NavBack;
    @BindView(R.id.nav_title)
    TextView navTitle;
    Unbinder unbinder;
    @BindView(R.id.uiListView)
    ListView uiListView;
    Unbinder unbinder1;
    private Context mContext;
    private View rootView;
    private List uiSampleList;

    private SampleListAdapter sampleListAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mContext = getActivity();
        if (rootView == null) {

            rootView = inflater.inflate(R.layout.fragment_ui, null);

            initDataAndView();
        }


        AutoUtils.auto(rootView);


        unbinder1 = ButterKnife.bind(this, rootView);
        return rootView;
    }


    private void initDataAndView() {
        unbinder = ButterKnife.bind(this, rootView);
        NavBack.setVisibility(View.GONE);
        navTitle.setText("常见UI");
        uiSampleList = new ArrayList();

        SampleModel sampleModel1 = new SampleModel("常用对话框", R.mipmap.dialogicon);
        SampleModel sampleModel2 = new SampleModel("下拉刷新", R.mipmap.ui_refresh);
        SampleModel sampleModel3 = new SampleModel("一些列表表格布局", R.mipmap.ui_gridview);
        SampleModel sampleModel4 = new SampleModel("下拉菜单和分段选择", R.mipmap.ui_dropdown_menu);
        SampleModel sampleModel5 = new SampleModel("地图相关", R.mipmap.ui_map);
        SampleModel sampleModel6 = new SampleModel("一些进度条", R.mipmap.ui_loading);

        uiSampleList.add(sampleModel1);
        uiSampleList.add(sampleModel2);
        uiSampleList.add(sampleModel3);

        uiSampleList.add(sampleModel4);
        uiSampleList.add(sampleModel5);
        uiSampleList.add(sampleModel6);
        sampleListAdapter = new SampleListAdapter(uiSampleList, mContext);

        uiListView.setAdapter(sampleListAdapter);
        uiListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                Intent intent = new Intent();

                switch (position) {
                    case 0:
                        intent.setClass(mContext, MenuUiDialogActivity.class);
                        startActivity(intent);
                        break;
                    case 1:
                        intent.setClass(mContext, MenuPullRefreshActivity.class);
                        startActivity(intent);
                        break;

                    case 2:
                        intent.setClass(mContext, MenuListActivity.class);
                        startActivity(intent);
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
