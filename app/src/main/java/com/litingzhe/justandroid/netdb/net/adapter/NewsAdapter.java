package com.litingzhe.justandroid.netdb.net.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.litingzhe.justandroid.R;
import com.litingzhe.justandroid.netdb.net.model.NewslistBean;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by litingzhe on 2016/12/8.
 */

public class NewsAdapter extends BaseAdapter {

    private ArrayList data;
    private Context mContext;

    public ArrayList getData() {
        return data;
    }

    public void setData(ArrayList data) {
        this.data = data;
    }

    public NewsAdapter(ArrayList data, Context mContext) {
        this.data = data;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int i) {
        return data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {

        final ViewHolder holder;
        if (convertView == null) {

            convertView = LayoutInflater.from(mContext).inflate(R.layout.list_item_news, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
            AutoUtils.autoSize(convertView);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        NewslistBean news = (NewslistBean) data.get(position);

        holder.title.setText(news.getTitle());
        holder.content.setText(news.getDescription());

        Glide.with(mContext).load(news.getPicUrl()).into(holder.imageView);

        return convertView;

    }

    /**
     * 增加多条并改变视图.
     *
     * @param news the image paths
     */
    public void addItems(List news) {
        data.addAll(news);
        notifyDataSetChanged();
    }

    /**
     * 增加多条并改变视图.
     */
    public void clearItems() {
        data.clear();
//        notifyDataSetChanged();
    }


    static class ViewHolder {
        @BindView(R.id.imageView)
        ImageView imageView;
        @BindView(R.id.title)
        TextView title;
        @BindView(R.id.content)
        TextView content;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
