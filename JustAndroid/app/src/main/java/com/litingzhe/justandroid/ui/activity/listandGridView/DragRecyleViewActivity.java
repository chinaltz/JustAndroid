package com.litingzhe.justandroid.ui.activity.listandGridView;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.litingzhe.justandroid.R;
import com.litingzhe.justandroid.ui.activity.listandGridView.recyleItemTouchHandler.MyItemTouchHandler;
import com.ningcui.mylibrary.app.base.AbBaseActivity;
import com.ningcui.mylibrary.viewLib.AutoToolbar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DragRecyleViewActivity extends AbBaseActivity {


    @BindView(R.id.navToolBarBack)
    LinearLayout navToolBarBack;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar)
    AutoToolbar toolbar;
    @BindView(R.id.id_recyclerview)
    RecyclerView recyclerView;

    List<String> str = new ArrayList<>();

    private MyAdapter myAdapter;

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

        toolbarTitle.setText("可拖拽的RecyleView");

        for (int i = 0; i < 50; i++) {
            str.add("data: " + i);
        }
        recyclerView.setLayoutManager(new GridLayoutManager(this, 4, GridLayoutManager.VERTICAL, false));
        myAdapter = new MyAdapter();
        recyclerView.setAdapter(myAdapter);
        new ItemTouchHelper(new MyItemTouchHandler(myAdapter)).attachToRecyclerView(recyclerView);
    }

    private class MyAdapter extends MyItemTouchHandler.ItemTouchAdapterImpl {

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new RecyclerView.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_simple_item, parent, false)) {
            };
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            TextView tv = (TextView) holder.itemView.findViewById(R.id.id_num);
            tv.setText(str.get(position)+"");
        }

        @Override
        public int getItemCount() {
            return str.size();
        }

        @Override
        public void onItemMove(int fromPosition, int toPosition) {
            // 拖动排序的回调,这里交换集合中数据的位置
            Collections.swap(str, fromPosition, toPosition);
        }

        @Override
        public void onItemRemove(int position) {
            // 滑动删除的回调,这里删除指定的数据
            str.remove(position);


        }

        @Override
        protected boolean autoOpenSwipe() {


            return true;
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.drag_recyleview, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.id_action_gridview:
                recyclerView.setLayoutManager(new GridLayoutManager(this, 4));
                break;
            case R.id.id_action_listview:
                recyclerView.setLayoutManager(new LinearLayoutManager(this));
                break;



        }
        return true;
    }


}
