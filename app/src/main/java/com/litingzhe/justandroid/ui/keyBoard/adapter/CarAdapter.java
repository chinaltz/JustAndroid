package com.litingzhe.justandroid.ui.keyBoard.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.litingzhe.justandroid.R;
import com.litingzhe.justandroid.ui.keyBoard.model.Car;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Copyright 李挺哲
 * 创建人：litingzhe
 * 邮箱：453971498@qq.com
 * 车辆 Adapter
 * Created by litingzhe on 2017/1/5.
 */

public class CarAdapter extends BaseAdapter {

    private int selectIndex = -1;

    public int getSelectIndex() {
        return selectIndex;
    }

    public void setSelectIndex(int selectIndex) {
        this.selectIndex = selectIndex;
    }

    private Context mContext;


    private List data;

    public Context getmContext() {
        return mContext;
    }

    public void setmContext(Context mContext) {
        this.mContext = mContext;
    }

    public List getData() {
        return data;
    }

    public void setData(ArrayList data) {
        this.data = data;
    }


    public CarAdapter(Context mContext, List data) {
        this.mContext = mContext;
        this.data = data;
    }

    @Override
    public int getCount() {

        return data.size();
    }

    @Override
    public Car getItem(int i) {
        return (Car) data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null) {

            view = LayoutInflater.from(mContext).inflate(R.layout.item_list_car, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();

        }

        Car car = (Car) data.get(i);
        holder.carNumber.setText(car.getCarNumber());

//
//        if (car.getCarStatus() != null) {
//            AbLogUtil.i(Constants.MYTAG, "carStatus=" + car.getCarStatus().toString());
//        } else {
//            AbLogUtil.i(Constants.MYTAG, "carStatus= null");
//        }

        if (car.getBoundStatus() == 1) {
            holder.defaultFlag.setVisibility(View.VISIBLE);
        } else {
            holder.defaultFlag.setVisibility(View.INVISIBLE);

        }

        if (selectIndex == i) {
            holder.carNumber.setTextColor(0xFFe83921);

        } else {
            holder.carNumber.setTextColor(0xFF474747);
        }

        AutoUtils.autoSize(view);
        return view;
    }


    static class ViewHolder {
        @BindView(R.id.defaultFlag)
        ImageView defaultFlag;
        @BindView(R.id.carNumber)
        TextView carNumber;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
