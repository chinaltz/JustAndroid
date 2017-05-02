package com.litingzhe.justandroid.ui.activity.pullRefresh;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
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

                getData();


            }
        });

        pulltofreshView.setOnFooterLoadListener(new AbPullToRefreshView.OnFooterLoadListener() {
            @Override
            public void onFooterLoad(AbPullToRefreshView abPullToRefreshView) {


                loadData();


            }
        });


    }

    private void loadData() {

        AsyncTask<Void, Void, Void> asyncTask = new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {


                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                addItem();
                adapter.notifyDataSetChanged();
                pulltofreshView.onFooterLoadFinish();
            }
        };
        asyncTask.execute();

    }

    private void getData() {

        AsyncTask<Void, Void, Void> asyncTask = new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                initOignData();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);

                pulltofreshView.onHeaderRefreshFinish();
                adapter.notifyDataSetChanged();
            }
        };
        asyncTask.execute();


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
