package com.litingzhe.justandroid.activity.pullRefresh;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.litingzhe.justandroid.R;
import com.ningcui.mylibrary.app.base.AbBaseActivity;
import com.ningcui.mylibrary.viewLib.refresh.AbPullToRefreshView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by litingzhe on 2016/12/15.
 */

public class PullToReFreshLayoutActivity extends AbBaseActivity {


    @BindView(R.id.nav_back)
    LinearLayout navBack;
    @BindView(R.id.nav_title)
    TextView navTitle;
    @BindView(R.id.nav_right_icon)
    ImageView navRightIcon;
    @BindView(R.id.nav_right)
    LinearLayout navRight;
    @BindView(R.id.pull_listView)
    ListView pullListView;
    @BindView(R.id.pulltofreshView)
    AbPullToRefreshView pulltofreshView;
    //
    private ArrayList<String> list = new ArrayList<String>();
    private int countIndex = 0;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_pullrefreshlayout);
        ButterKnife.bind(this);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, initOignData());
        pullListView.setAdapter(adapter);


        pulltofreshView.setLoadMoreEnable(true);
        pulltofreshView.setPullRefreshEnable(true);
        pulltofreshView.setOnHeaderRefreshListener(new AbPullToRefreshView.OnHeaderRefreshListener() {
            @Override
            public void onHeaderRefresh(AbPullToRefreshView abPullToRefreshView) {

//                getData();
                Log.i("Tag1234", "onHeaderRefresh" + Thread.currentThread().getName());


            }
        });

        pulltofreshView.setOnFooterLoadListener(new AbPullToRefreshView.OnFooterLoadListener() {
            @Override
            public void onFooterLoad(AbPullToRefreshView abPullToRefreshView) {


//                loadData();
                Log.i("Tag1234", "onFooterLoad" + Thread.currentThread().getName());


            }
        });


    }

    private void loadData() {

    }

    private void getData() {

    }


    public void addItem() {

        countIndex += 1;
        list.add("测试数据" + countIndex);

    }

    public List<String> initOignData() {
        countIndex = 0;
        list.clear();
        for (int i = 1; i <= 5; i++) {

            list.add("测试数据" + i);

        }
        countIndex = 5;
        return list;
    }




}
