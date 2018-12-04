package com.litingzhe.justandroid.ui.listandGridView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.litingzhe.justandroid.R;

/**
 * Created by wxy on 2017/4/27.
 */

public class HeaderProductLayout extends LinearLayout {
    LinearLayout mImageView;
    public HeaderProductLayout(Context context) {
        super(context);
        initView(context);
    }

    private void initView(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.header_layout, this);
        mImageView = (LinearLayout) view.findViewById(R.id.header_product_image);
    }

    //量取view此时Y轴的距离
    public int getDistanceY() {
        int[] location = new int[2];
        mImageView.getLocationOnScreen(location);
        int y = location[1];
        return y;
    }

}
