package com.litingzhe.justandroid.ui.mapView.adapter;



import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.ThumbnailUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.litingzhe.justandroid.R;
import com.zhy.autolayout.utils.AutoUtils;

/**
 * Copyright 李挺哲
 * 创建人：litingzhe
 * 邮箱：453971498@qq.com
 * 附近停车场 热点地区 Adapter
 * Created by litingzhe on 2016/12/6.
 */

public class HorizontalListViewAdapter extends BaseAdapter{
	private String[] mTitles;
	private Context mContext;
	private LayoutInflater mInflater;
	private int selectIndex = -1;

	public HorizontalListViewAdapter(Context context, String[] titles){
		this.mContext = context;
		this.mTitles = titles;
		mInflater=LayoutInflater.from(mContext);
	}
	@Override
	public int getCount() {
		return mTitles.length;
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

		ViewHolder holder;
		if(convertView==null){
			holder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.item_hot_area, null);
			holder.hotButton=(Button) convertView.findViewById(R.id.hotButton);
			convertView.setTag(holder);
		}else{
			holder=(ViewHolder)convertView.getTag();
		}
		if(position == selectIndex){
			holder.hotButton.setSelected(true);
			holder.hotButton.setTextColor(Color.WHITE);

		}else{
			holder.hotButton.setSelected(false);
			holder.hotButton.setTextColor(Color.BLACK);
		}


		holder.hotButton.setText(mTitles[position]);
		AutoUtils.auto(convertView);
		return convertView;
	}

	private static class ViewHolder {
		private Button hotButton;
	}

	public void setSelectIndex(int i){
		selectIndex = i;

		notifyDataSetChanged();
	}
}