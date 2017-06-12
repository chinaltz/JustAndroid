package com.litingzhe.justandroid.netdb.net.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.litingzhe.justandroid.R;
import com.litingzhe.justandroid.netService.NetWork;
import com.litingzhe.justandroid.netdb.net.adapter.NewsAdapter;
import com.litingzhe.justandroid.netdb.net.model.NewList;
import com.litingzhe.justandroid.netdb.net.model.NewslistBean;
import com.ningcui.mylibrary.app.base.AbBaseActivity;
import com.ningcui.mylibrary.utiils.AbDialogUtil;
import com.ningcui.mylibrary.viewLib.refresh.AbPullToRefreshView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Copyright 李挺哲
 * 创建人：litingzhe
 * 邮箱：453971498@qq.com
 * Created by litingzhe on 2017/4/28 下午1:24.
 * 类描述：网络访问示例 新闻
 */


public class NetDemoActivity extends AbBaseActivity {

    @BindView(R.id.news_listView)
    ListView newsListView;
    @BindView(R.id.PullToRefreshView)
    AbPullToRefreshView PullToRefreshView;
    @BindView(R.id.nav_back)
    LinearLayout navBack;
    @BindView(R.id.nav_title)
    TextView navTitle;
    @BindView(R.id.nav_right_icon)
    ImageView navRightIcon;
    @BindView(R.id.nav_right)
    LinearLayout navRight;
    private ArrayList newsArrayList;
    private NewsAdapter newsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_news);
        ButterKnife.bind(this);

        navTitle.setText("科技新闻");
        navBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        newsArrayList = new ArrayList();
        newsAdapter = new NewsAdapter(newsArrayList, mContext);
        newsListView.setAdapter(newsAdapter);

        newsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                NewslistBean news = (NewslistBean) newsAdapter.getItem(position);
                Intent intent = new Intent();
                intent.setClass(mContext, WebViewActivity.class);
                intent.putExtra("title", news.getDescription());
                intent.putExtra("url", news.getUrl());
                startActivity(intent);


            }
        });

        getData();

        PullToRefreshView.setPullRefreshEnable(true);
        PullToRefreshView.setLoadMoreEnable(false);
        PullToRefreshView.setOnHeaderRefreshListener(new AbPullToRefreshView.OnHeaderRefreshListener() {
            @Override
            public void onHeaderRefresh(AbPullToRefreshView view) {


                getData();
            }
        });

    }


    private void getData() {
        AbDialogUtil.showProgressDialog(mContext, R.drawable.progress_circle, "正在请求网络，请稍后", true);

        NetWork.getNewsList("5c1b6939d3b5c7f9286e74b4bbeea009", "10", new Observer<NewList>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(NewList newList) {

                newsAdapter.clearItems();
                newsAdapter.addItems(newList.getNewslist());

            }

            @Override
            public void onError(Throwable e) {
                Toast.makeText(mContext, "错误" + e.getMessage(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onComplete() {

                AbDialogUtil.removeDialog(mContext);
//
                PullToRefreshView.onHeaderRefreshFinish();

            }

        });


    }

}
