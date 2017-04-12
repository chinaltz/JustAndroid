package com.litingzhe.justandroid.main.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.litingzhe.justandroid.R;
import com.ningcui.mylibrary.app.base.AbBaseFragment;
import com.zhy.autolayout.utils.AutoUtils;

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


public class DBDataFragment extends AbBaseFragment {
    @BindView(R.id.nav_back)
    LinearLayout NavBack;
    @BindView(R.id.nav_title)
    TextView navTitle;
    Unbinder unbinder;
    private Context mContext;
    private View rootView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mContext = getActivity();
        if (rootView == null) {

            rootView = inflater.inflate(R.layout.fragment_db, null);
            unbinder = ButterKnife.bind(this, rootView);
            NavBack.setVisibility(View.GONE);
            navTitle.setText("数据持久化");
        }



        AutoUtils.auto(rootView);


        return rootView;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
