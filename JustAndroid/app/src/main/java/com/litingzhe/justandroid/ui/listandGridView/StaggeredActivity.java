package com.litingzhe.justandroid.ui.listandGridView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.litingzhe.justandroid.R;
import com.litingzhe.justandroid.ui.listandGridView.adapter.StaggeredAdapter;
import com.litingzhe.justandroid.ui.listandGridView.model.StaggeredType;
import com.litingzhe.justandroid.utils.netutils.OkHttp3Utils;
import com.ningcui.mylibrary.app.base.AbBaseActivity;
import com.ningcui.mylibrary.utiils.AbSnackbarUtil;
import com.ningcui.mylibrary.viewLib.AutoToolbar;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Copyright 李挺哲
 * 创建人：litingzhe
 * 邮箱：453971498@qq.com
 * Created by litingzhe on 2017/5/3 下午5:20.
 * 类描述：RecycleView 瀑布流 使用
 */


public class StaggeredActivity extends AbBaseActivity {

    @BindView(R.id.navToolBarBack)
    LinearLayout navToolBarBack;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar)
    AutoToolbar toolbar;
    @BindView(R.id.staggered_recycler)
    RecyclerView recyclerview;
    @BindView(R.id.staggered_swipe_refresh)
    SwipeRefreshLayout swipeRefreshLayout;

    private StaggeredAdapter mAdapter;
    private List<StaggeredType> staggeredTypes;
    private StaggeredGridLayoutManager mLayoutManager;
    private int lastVisibleItem;
    private int page = 1;
    private ItemTouchHelper itemTouchHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staggerde);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        navToolBarBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        toolbarTitle.setText("RecycleView瀑布流");
        initView();
        setListener();

        new GetData().execute("http://gank.io/api/data/福利/10/1");

    }

    private void initView() {

        recyclerview = (RecyclerView) findViewById(R.id.staggered_recycler);
        mLayoutManager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        recyclerview.setLayoutManager(mLayoutManager);

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.staggered_swipe_refresh);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary, R.color.colorPrimaryDark, R.color.colorAccent);
        swipeRefreshLayout.setProgressViewOffset(false, 0, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources().getDisplayMetrics()));

    }

    private void setListener() {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page = 1;
                new GetData().execute("http://gank.io/api/data/福利/10/1");
            }
        });

        itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.Callback() {
            @Override
            public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                int dragFlags = 0;
                if (recyclerView.getLayoutManager() instanceof StaggeredGridLayoutManager) {
                    dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
                }
                return makeMovementFlags(dragFlags, 0);
            }

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                int from = viewHolder.getAdapterPosition();
                int to = target.getAdapterPosition();
                StaggeredType moveItem = staggeredTypes.get(from);
                staggeredTypes.remove(from);
                staggeredTypes.add(to, moveItem);
                mAdapter.notifyItemMoved(from, to);
                return true;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

            }

            @Override
            public boolean isLongPressDragEnabled() {
                return false;
            }
        });

        recyclerview.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
//                0：当前屏幕停止滚动；1时：屏幕在滚动 且 用户仍在触碰或手指还在屏幕上；2时：随用户的操作，屏幕上产生的惯性滑动；
                if (newState == RecyclerView.SCROLL_STATE_IDLE
                        && lastVisibleItem + 2 >= mLayoutManager.getItemCount()) {
                    new GetData().execute("http://gank.io/api/data/福利/10/" + (++page));
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int[] positions = mLayoutManager.findLastVisibleItemPositions(null);
                lastVisibleItem = Math.max(positions[0], positions[1]);
            }
        });
    }

    private class GetData extends AsyncTask<String, Integer, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            swipeRefreshLayout.setRefreshing(true);
        }

        @Override
        protected String doInBackground(String... params) {

            return OkHttp3Utils.get(params[0]);
        }

        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (!TextUtils.isEmpty(result)) {

                JSONObject jsonObject;
                Gson gson = new Gson();
                String jsonData = null;

                try {
                    jsonObject = new JSONObject(result);
                    jsonData = jsonObject.getString("results");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (staggeredTypes == null || staggeredTypes.size() == 0) {
                    staggeredTypes = gson.fromJson(jsonData, new TypeToken<List<StaggeredType>>() {
                    }.getType());
//                    StaggeredType pages = new StaggeredType();
//                    pages.setPage(page);
//                    staggeredTypes.add(pages);
                } else {
                    List<StaggeredType> more = gson.fromJson(jsonData, new TypeToken<List<StaggeredType>>() {
                    }.getType());
                    staggeredTypes.addAll(more);
//                    StaggeredType pages = new StaggeredType();
//                    pages.setPage(page);
//                    staggeredTypes.add(pages);
                }

                if (mAdapter == null) {
                    recyclerview.setAdapter(mAdapter = new StaggeredAdapter(StaggeredActivity.this, staggeredTypes));

                    //设置点击监听
                    mAdapter.setOnItemClickListener(new StaggeredAdapter.OnRecyclerViewItemClickListener() {
                        @Override
                        public void onItemClick(View view) {
                            int position = recyclerview.getChildAdapterPosition(view);
                            AbSnackbarUtil.ShortSnackbar(recyclerview, "点击第" + position + "个", AbSnackbarUtil.Info).show();
                        }

                        @Override
                        public void onItemLongClick(View view) {


                            itemTouchHelper.startDrag(recyclerview.getChildViewHolder(view));
                        }
                    });

                    itemTouchHelper.attachToRecyclerView(recyclerview);
                } else {
                    mAdapter.notifyDataSetChanged();
                }
            }
            swipeRefreshLayout.setRefreshing(false);
        }
    }

}
