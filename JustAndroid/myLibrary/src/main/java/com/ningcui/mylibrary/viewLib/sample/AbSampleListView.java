package com.ningcui.mylibrary.viewLib.sample;

import android.content.Context;
import android.database.DataSetObserver;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;

import java.util.ArrayList;

/**
 * Copyright 李挺哲
 * 创建人：litingzhe
 * 邮箱：453971498@qq.com
 * Created by litingzhe on 2017/4/11 下午3:23.
 * Info 自定义ListView 适合一次性展现所有的，不包含滚动与View缓存
 */
public class AbSampleListView extends LinearLayout {

    /** AbSampleListView self . */
    private AbSampleListView view = null;

	/** Adapter. */
	private BaseAdapter adapter = null;

	/** Adapter监听器. */
	private AdapterDataSetObserver dataSetObserver;

	/** View列表. */
	private ArrayList<View> items;

	/** Item点击事件. */
	private OnItemClickListener onItemClickListener;

	/** Item垂直方向间距. */
	private int  itemVertialPadding = 0;
	private int  itemTopPadding = 0;

	public AbSampleListView(Context context) {
		this(context, null);
	}

	public AbSampleListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
        init();

	}

	public AbSampleListView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

    public void  init(){
        this.setOrientation(LinearLayout.VERTICAL);
        items = new ArrayList<View>();
        view = this;
    }

	class AdapterDataSetObserver extends DataSetObserver {

		@Override
		public void onChanged() {
			int count = adapter.getCount();
			if (count > items.size()) {
				//数据有增加
				addChildren();
			}else if(count < items.size()){
				//有删除的数据，删除几个
				int delCount = items.size() - count;
				for(int i = 0;i < delCount;i++){
					//删除最后一个
                    view.removeViewAt(view.getChildCount()-1);
					items.remove(items.size()-1);


				}
			}

			//只是数据更新
			updateViewData();

			super.onChanged();
		}

		@Override
		public void onInvalidated() {
			super.onInvalidated();
		}

	}

	public void setAdapter(BaseAdapter adapter) {
		this.adapter = adapter;

		if (adapter != null && dataSetObserver != null) {
			adapter.unregisterDataSetObserver(dataSetObserver);
		}

		if (adapter != null) {
			dataSetObserver = new AdapterDataSetObserver();
			adapter.registerDataSetObserver(dataSetObserver);
		}
		layoutChildren();
	}

	protected void layoutChildren() {
		removeAllViews();
		items.clear();
		if (adapter != null) {
			int itemCount = adapter.getCount();
			for (int i = 0; i < itemCount; i++) {
				View viewInfo = adapter.getView(i, null, null);
				viewInfo.setVisibility(View.VISIBLE);
				items.add(viewInfo);
				LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT);
				if(i!=0){
					layoutParams.topMargin = itemVertialPadding;
				}else{
					layoutParams.topMargin = itemTopPadding;
				}
				this.addView(viewInfo,layoutParams);

			}
		}
	}

	/**
	 * 只是更新数据内容
	 */
	protected void updateViewData(){
		if (adapter != null) {
			int itemCount = adapter.getCount();
			for (int i = 0; i < itemCount; i++) {
				adapter.getView(i, items.get(i), null);
			}
		}
	}

	/**
	 * 增加子元素.
	 */
	protected void addChildren() {
		if (adapter != null) {
			int count = adapter.getCount();
			if (count > items.size()) {
				for (int i = items.size(); i < count; i++) {
					View viewInfo = adapter.getView(i, null, null);
					items.add(viewInfo);

					LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT);
					if(i!=0){
						layoutParams.topMargin = itemVertialPadding;
					}else{
						layoutParams.topMargin = itemTopPadding;
					}
					this.addView(viewInfo,layoutParams);
				}
			}
		}

	}

	public OnItemClickListener getOnItemClickListener() {
		return onItemClickListener;
	}

	/**
	 * 设置点击事件
	 * @param onItemClickListener
     */
	public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
		this.onItemClickListener = onItemClickListener;
	}

	public int getItemVertialPadding() {
		return itemVertialPadding;
	}

	public void setItemVertialPadding(int itemVertialPadding) {
		this.itemVertialPadding = itemVertialPadding;
	}

	public int getItemTopPadding() {
		return itemTopPadding;
	}

	public void setItemTopPadding(int itemTopPadding) {
		this.itemTopPadding = itemTopPadding;
	}
}
