package com.ningcui.mylibrary.app.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;


/**
 * Copyright 李挺哲
 * 创建人：litingzhe
 * 邮箱：453971498@qq.com
 * Created by litingzhe on 2017/4/11 下午3:23.
 * Info 一个通用的ViewPager适配器
 */
public class AbViewPagerAdapter extends PagerAdapter{
	
	/** 上下文. */
	private Context context;
	
	/** View列表. */
	private ArrayList<View> listViews = null;


	/**
	 * 构造函数.
	 * @param context the context
	 * @param listViews the m list views
	 */
	public AbViewPagerAdapter(Context context,ArrayList<View> listViews) {
		this.context = context;
		this.listViews = listViews;
	}

	/**
	 * 获取数量.
	 * @return the count
	 */
	@Override
	public int getCount() {
		return listViews.size();
	}

	/**
	 * Object是否对应这个View.
	 * @param arg0 the arg0
	 * @param arg1 the arg1
	 * @return true, if is view from object
	 */
	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0 == (arg1);
	}

	/**
	 * 显示View.
	 * @param container the container
	 * @param position the position
	 * @return the object
	 */
	@Override
	public Object instantiateItem(View container, int position) {
		View v = listViews.get(position);
		((ViewPager) container).addView(v);
		return v;
	}

	/**
	 * 移除View.
	 * @param container the container
	 * @param position the position
	 * @param object the object
	 */
	@Override
	public void destroyItem(View container, int position, Object object) {
		((ViewPager) container).removeView((View)object);
	}
	
	/**
	 * 很重要，否则不能notifyDataSetChanged.
	 * @param object the object
	 * @return the item position
	 */
	@Override
	public int getItemPosition(Object object) {
		return POSITION_NONE;
	}
	

}
