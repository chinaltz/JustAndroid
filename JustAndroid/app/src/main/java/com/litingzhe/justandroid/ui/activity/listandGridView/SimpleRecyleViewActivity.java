package com.litingzhe.justandroid.ui.activity.listandGridView;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.litingzhe.justandroid.R;
import com.litingzhe.justandroid.ui.activity.listandGridView.adapter.SimpleRecyleViewAdapter;
import com.litingzhe.justandroid.ui.activity.listandGridView.recyleDivider.DividerGridItemDecoration;
import com.ningcui.mylibrary.app.base.AbBaseActivity;
import com.ningcui.mylibrary.viewLib.AutoToolbar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Copyright 李挺哲
 * 创建人：litingzhe
 * 邮箱：453971498@qq.com
 * Created by litingzhe on 2017/5/2 下午5:20.
 * 类描述：简单的RecycleView的使用
 */


//
public class SimpleRecyleViewActivity extends AbBaseActivity {


    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar)
    AutoToolbar toolbar;
    @BindView(R.id.id_recyclerview)
    RecyclerView mRecyclerView;
    @BindView(R.id.navToolBarBack)
    LinearLayout navToolBarBack;
    private List<String> mDatas;
    private SimpleRecyleViewAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_recyclerview);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        navToolBarBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });

        initData();

        mAdapter = new SimpleRecyleViewAdapter(this, mDatas);

        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(4,
                StaggeredGridLayoutManager.VERTICAL));


        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView.addItemDecoration(new DividerGridItemDecoration(this));
        // 设置item动画
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        initEvent();

    }

    private void initEvent() {
        mAdapter.setOnItemClickLitener(new SimpleRecyleViewAdapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(SimpleRecyleViewActivity.this, position + " click",
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onItemLongClick(View view, int position) {
                Toast.makeText(SimpleRecyleViewActivity.this, position + " long click",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    protected void initData() {
        mDatas = new ArrayList<String>();
        for (int i = 'A'; i < 'z'; i++) {
            mDatas.add("" + (char) i);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.simple_recyleview, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.id_action_add:
                mAdapter.addData(1);
                break;
            case R.id.id_action_delete:
                mAdapter.removeData(1);
                break;
            case R.id.id_action_gridview:
                mRecyclerView.setLayoutManager(new GridLayoutManager(this, 4));
                break;
            case R.id.id_action_listview:
                mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
                break;
            case R.id.id_action_horizontalGridView:
                mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(4,
                        StaggeredGridLayoutManager.HORIZONTAL));
                break;


        }
        return true;
    }

}
