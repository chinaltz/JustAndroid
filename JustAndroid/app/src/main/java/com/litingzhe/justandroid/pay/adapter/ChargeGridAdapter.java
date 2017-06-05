package com.litingzhe.justandroid.pay.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.litingzhe.justandroid.R;
import com.litingzhe.justandroid.pay.model.ChargeModel;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Copyright 李挺哲
 * 创建人：litingzhe
 * 邮箱：453971498@qq.com
 * 钱包充值条目 Adapter
 * Created by litingzhe on 2017/1/3.
 */

public class ChargeGridAdapter extends BaseAdapter {


    public ArrayList<ChargeModel> getData() {
        return data;
    }

    public void setData(ArrayList<ChargeModel> data) {
        this.data = data;
    }

    public Context getmContext() {
        return mContext;
    }

    public void setmContext(Context mContext) {
        this.mContext = mContext;
    }

    private ArrayList<ChargeModel> data;
    private Context mContext;
    private int selectIndex = -1;

    public int getSelectIndex() {
        return selectIndex;
    }

    public void setSelectIndex(int selectIndex) {
        this.selectIndex = selectIndex;
    }

    public ChargeGridAdapter(ArrayList<ChargeModel> data, Context mContext) {
        this.data = data;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public ChargeModel getItem(int i) {
        return data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

        ViewHolder viewHolder = null;
        final ChargeModel model = data.get(position);
        if (view == null) {

            view = LayoutInflater.from(mContext).inflate(R.layout.item_grid_chargewallet, null);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        viewHolder.priceText.setText(model.getPrice() + "元");

        viewHolder.presentPrcieText.setVisibility(View.GONE);

//        if (model.getPresentation() > 0) {
//
//            viewHolder.presentPrcieText.setVisibility(View.VISIBLE);
//            viewHolder.presentPrcieText.setText("送"+model.getPresentation()+"元");
//        } else {
//            viewHolder.presentPrcieText.setVisibility(View.GONE);
//        }


        if (selectIndex == position) {
            viewHolder.llPrice.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.item_selete_true_circle));
            viewHolder.priceText.setTextColor(Color.WHITE);
            viewHolder.presentPrcieText.setTextColor(Color.WHITE);
        } else {
            viewHolder.llPrice.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.item_selete_false_circle));
            viewHolder.priceText.setTextColor(Color.BLACK);
        }

        if (selectIndex == -1) {
            viewHolder.llPrice.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.item_selete_false_circle));
            viewHolder.priceText.setTextColor(Color.BLACK);
        }
        AutoUtils.autoSize(view);

        return view;


    }

    static class ViewHolder {
        @BindView(R.id.priceText)
        TextView priceText;
        @BindView(R.id.presentPrcieText)
        TextView presentPrcieText;
        @BindView(R.id.ll_price)
        LinearLayout llPrice;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

//    static class ViewHolder {
//        @InjectView(R.id.priceText)
//        TextView priceText;
//        @InjectView(R.id.presentPrcieText)
//        TextView presentPrcieText;
//
//        @InjectView(R.id.ll_price)
//        LinearLayout llPrice;
//
//        ViewHolder(View view) {
//            ButterKnife.inject(this, view);
//        }
//    }
}
