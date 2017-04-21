package com.litingzhe.justandroid.main.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.litingzhe.justandroid.R;
import com.litingzhe.justandroid.main.model.SampleModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Copyright 李挺哲
 * 创建人：litingzhe
 * 邮箱：453971498@qq.com
 * Created by litingzhe on 2017/4/14 下午2:07.
 * 类描述：Demo ListView 的适配器
 */


public class SampleListAdapter extends BaseAdapter {

    private List data;


    private Context mContext;

    public SampleListAdapter(List data, Context mContext) {
        this.data = data;
        this.mContext = mContext;
    }

    public SampleListAdapter(List data) {
        this.data = data;
    }

    public List getData() {
        return data;
    }

    public void setData(List data) {
        this.data = data;
    }

    public Context getmContext() {
        return mContext;
    }

    public void setmContext(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;


    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;

        if (convertView == null) {

            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_sample_list, null);
            viewHolder=new ViewHolder(convertView);
            convertView.setTag(viewHolder);

        }
        else {

            viewHolder = (ViewHolder) convertView.getTag();
        }
        SampleModel model= (SampleModel) data.get(position);

        viewHolder.imageView.setBackgroundDrawable(mContext.getResources().getDrawable(model.getDrawableId()));
        viewHolder.textView.setText(model.getTitle());

        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.imageView)
        ImageView imageView;
        @BindView(R.id.textView)
        TextView textView;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
