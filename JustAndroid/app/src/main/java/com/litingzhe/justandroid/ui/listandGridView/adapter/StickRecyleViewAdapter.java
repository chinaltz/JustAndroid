package com.litingzhe.justandroid.ui.listandGridView.adapter;

import android.support.annotation.Nullable;


import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.litingzhe.justandroid.R;

import java.util.List;

/**
 * Created by wxy on 2017/4/27.
 */

public class StickRecyleViewAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    public StickRecyleViewAdapter(@Nullable List<String> data) {
        super(R.layout.item_recyleview_sticky, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {

    }
}
