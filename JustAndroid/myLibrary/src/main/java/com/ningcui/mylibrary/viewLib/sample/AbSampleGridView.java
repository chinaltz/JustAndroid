package com.ningcui.mylibrary.viewLib.sample;

import android.content.Context;
import android.database.DataSetObserver;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Copyright 李挺哲
 * 创建人：litingzhe
 * 邮箱：453971498@qq.com
 * Created by litingzhe on 2017/4/11 下午3:23.
 * Info 自定义GirdView 适合一次性展现所有的，不包含滚动与View缓存
 *
 */

public class AbSampleGridView extends LinearLayout {

	private Context context;

	private int column = 0;

	private List<LinearLayout> columnLayoutList;

	/** Adapter. */
	private BaseAdapter adapter = null;

	/** Adapter监听器. */
	private AdapterDataSetObserver dataSetObserver;

	private ArrayList<View> items;

	private int horizontalPadding = 0;

	private int verticalPadding = 0;

    private LinearLayout contentLayout;

	private AdapterView.OnItemClickListener onItemClickListener;

	public AbSampleGridView(Context context) {
		this(context, null);
	}

	public AbSampleGridView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);

		this.context = context;
        this.setOrientation(LinearLayout.VERTICAL);
        contentLayout = new LinearLayout(context);
        contentLayout.setOrientation(LinearLayout.HORIZONTAL);
        this.addView(contentLayout,new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT));

		items = new ArrayList<View>();
        columnLayoutList = new ArrayList<LinearLayout> ();

		setColumn(1);

	}

	public AbSampleGridView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
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

                    LinearLayout  columnLayout = columnLayoutList.get((items.size()-1)%column);
                    columnLayout.removeViewAt(columnLayout.getChildCount()-1);
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

	/**
	 * 设置数据适配器
	 * @param adapter
     */
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
	 * 重新排列View
	 */
	protected void layoutChildren() {

		removeAllViews();

		items.clear();
		if (adapter != null) {
			int itemCount = adapter.getCount();
			for (int i = 0; i < itemCount; i++) {
				View viewInfo = adapter.getView(i, null, null);
				viewInfo.setVisibility(View.VISIBLE);

                final int position = i;
                viewInfo.setClickable(true);
                viewInfo.setFocusable(true);
                viewInfo.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(onItemClickListener != null){
                            onItemClickListener.onItemClick(null,v,position,position);
                        }
                    }
                });

				items.add(viewInfo);
				addView(i % column,viewInfo);

			}
		}
	}

	/**
	 * 添加子View.
	 */
	protected void addChildren() {
		if (adapter != null) {
			int count = adapter.getCount();
			if (count > items.size()) {
				for (int i = items.size(); i < count; i++) {
					View viewInfo = adapter.getView(i, null, null);

                    final int position = i;
                    viewInfo.setClickable(true);
                    viewInfo.setFocusable(true);
                    viewInfo.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(onItemClickListener != null){
								onItemClickListener.onItemClick(null,v,position,position);
                            }
                        }
                    });
                    items.add(viewInfo);
                    addView(i % column,viewInfo);

				}
			}
		}

	}

	public AdapterView.OnItemClickListener getOnItemClickListener() {
		return onItemClickListener;
	}

    /**
     * 设置点击事件
     * @param onItemClickListener
     */
	public void setOnItemClickListener(AdapterView.OnItemClickListener onItemClickListener) {
		this.onItemClickListener = onItemClickListener;
	}

	public int getColumn() {
		return column;
	}

	/**
	 * 设置列数，只能调用一次
	 * @param column
     */
	public void setColumn(int column) {
        this.columnLayoutList.clear();
        contentLayout.removeAllViews();

		this.column = column;
		for(int i=0;i< column ;i++){
            LayoutParams layoutParams = new LayoutParams(0,LayoutParams.WRAP_CONTENT,1);
			layoutParams.setMargins(horizontalPadding/2,0,horizontalPadding/2,0);
			LinearLayout columnLayout = new LinearLayout(context);
			columnLayout.setOrientation(LinearLayout.VERTICAL);
			columnLayout.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.TOP);
			columnLayout.setPadding(0,0,0,0);
            contentLayout.addView(columnLayout,layoutParams);
            columnLayoutList.add(columnLayout);
		}

	}

	public void  removeAllViews(){
		for(int i=0;i< columnLayoutList.size() ;i++){
            columnLayoutList.get(i).removeAllViews();
		}
	}

	private void addView(int column,View view){
        LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT);
		layoutParams.topMargin = verticalPadding/2;
        layoutParams.bottomMargin = verticalPadding/2;
		columnLayoutList.get(column).addView(view,layoutParams);
	}

	/**
	 * setColumn函数之前调用
	 * @param horizontalPadding
	 * @param verticalPadding
     */
	public void setPadding(int horizontalPadding,int verticalPadding) {
		this.horizontalPadding = horizontalPadding;
		this.verticalPadding = verticalPadding;
	}

	/**
	 * 获取索引上的View
	 * @param index
	 * @return
     */
	public View getItemView(int index){
		return items.get(index);
	}

}
