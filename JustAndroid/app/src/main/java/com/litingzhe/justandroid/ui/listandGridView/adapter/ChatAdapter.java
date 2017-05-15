package com.litingzhe.justandroid.ui.listandGridView.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.litingzhe.justandroid.R;
import com.zhy.autolayout.utils.AutoUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Copyright 李挺哲
 * 创建人：litingzhe
 * 邮箱：453971498@qq.com
 * Created by litingzhe on 2017/5/9 下午1:43.
 * 类描述：
 */


public class ChatAdapter extends BaseAdapter {

    public static final int TYPE_TO = 0; //自己说的话
    public static final int TYPE_FROM = 1;//朋友说的话

    private Context mContext;

    public ChatAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public int getCount() {

        return 15;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        int type = getItemViewType(position);

        FromViewHolder fromViewHolder = null;
        ToViewHolder toViewHolder=null;
        switch (type) {
            case TYPE_FROM:

                if (convertView == null) {
                    convertView =
                            LayoutInflater.from(mContext).inflate(R.layout.chat_from_msg, null);
                    fromViewHolder = new FromViewHolder(convertView);


                    convertView.setTag(fromViewHolder);

                } else {
                    fromViewHolder = (FromViewHolder) convertView.getTag();

                }
                fromViewHolder.chatFromContent.setText("hello！");
                break;

            case TYPE_TO:


                if (convertView == null) {
                    convertView =
                            LayoutInflater.from(mContext).inflate(R.layout.chat_send_msg, null);
                    toViewHolder = new ToViewHolder(convertView);

                    convertView.setTag(toViewHolder);

                } else {
                    toViewHolder = (ToViewHolder) convertView.getTag();

                }
                toViewHolder.chatSendContent.setText("你好");
                break;


        }

        AutoUtils.auto(convertView);
        return convertView;
    }

    @Override
    public int getItemViewType(int position) {

        if (position % 2 == 1) {


            return TYPE_FROM;
        } else {

            return TYPE_TO;

        }


    }

    @Override
    public int getViewTypeCount() {

        return 2;
    }


    static class FromViewHolder {
        @BindView(R.id.chat_from_icon)
        ImageView chatFromIcon;
        @BindView(R.id.chat_from_name)
        TextView chatFromName;
        @BindView(R.id.chat_from_content)
        TextView chatFromContent;

        FromViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    static class ToViewHolder {
        @BindView(R.id.chat_send_icon)
        ImageView chatSendIcon;
        @BindView(R.id.chat_send_name)
        TextView chatSendName;
        @BindView(R.id.ly_chat_icon)
        LinearLayout lyChatIcon;
        @BindView(R.id.chat_send_content)
        TextView chatSendContent;

        ToViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
