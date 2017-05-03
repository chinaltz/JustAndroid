package com.litingzhe.justandroid.ui.activity.listandGridView.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.litingzhe.justandroid.R;
import com.litingzhe.justandroid.ui.activity.listandGridView.model.StaggeredType;

import java.util.List;

/**
 * Copyright 李挺哲
 * 创建人：litingzhe
 * 邮箱：453971498@qq.com
 * Created by litingzhe on 2017/5/2 下午2:20.
 * 类描述：瀑布流适配
 */

public  class StaggeredAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener, View.OnLongClickListener {

    private Context mContext;
    private List<StaggeredType> datas;

    public static interface OnRecyclerViewItemClickListener {
        void onItemClick(View view);
        void onItemLongClick(View view);
    }

    private OnRecyclerViewItemClickListener mOnItemClickListener = null;

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        mOnItemClickListener = listener;
    }


    public StaggeredAdapter(Context context, List<StaggeredType> datas) {
        mContext=context;
        this.datas=datas;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        if(viewType==0){
            View view = LayoutInflater.from(mContext
                    ).inflate(R.layout.recycle_staggered_item, parent,
                    false);
            MyViewHolder holder = new MyViewHolder(view);

            view.setOnClickListener(this);
            view.setOnLongClickListener(this);

            return holder;
        }else{
            MyViewHolder2 holder2=new MyViewHolder2(LayoutInflater.from(
                    mContext).inflate(R.layout.recycle_staggered_page_item, parent,
                    false));
            return holder2;
        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof MyViewHolder){

            Glide.with(mContext).load(datas.get(position).getUrl()).fitCenter()
                    .into(((MyViewHolder) holder).iv);

//            Picasso.with(mContext).load(datas.get(position).getUrl()).into(((MyViewHolder) holder).iv);


        }else if(holder instanceof MyViewHolder2){
            ((MyViewHolder2) holder).tv.setText(datas.get(position).getPage()+"页");
        }

    }

    @Override
    public int getItemCount()
    {
        return datas.size();
    }

    @Override
    public int getItemViewType(int position) {

        //判断item是图还是显示页数（图片有URL）
        if (!TextUtils.isEmpty(datas.get(position).getUrl())) {
            return 0;
        } else {
            return 1;
        }
    }

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {

            mOnItemClickListener.onItemClick(v);
        }

    }


    @Override
    public boolean onLongClick(View v) {
        if (mOnItemClickListener != null) {
            mOnItemClickListener.onItemLongClick(v);
        }
        return false;
    }

    class MyViewHolder extends RecyclerView.ViewHolder
    {
        private ImageView iv;

        public MyViewHolder(View view)
        {
            super(view);
            iv = (ImageView) view.findViewById(R.id.iv);
        }
    }
    class MyViewHolder2 extends RecyclerView.ViewHolder
    {
        private TextView tv;

        public MyViewHolder2(View view)
        {
            super(view);
            tv = (TextView) view.findViewById(R.id.tv);
        }
    }

}
