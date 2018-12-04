package com.ningcui.mylibrary.viewLib.refresh;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ningcui.mylibrary.utiils.AbViewUtil;


/**
 * Copyright 李挺哲
 * 创建人：litingzhe
 * 邮箱：453971498@qq.com
 * Created by litingzhe on 2017/4/11 下午3:23.
 * Info 加载更多Footer View类
 */
public class AbListViewFooter extends LinearLayout {
	
	/** 上下文 */
	private Context mContext;
	
	/** 当前状态 */
    private int mState = -1;
	
	/** 准备状态 */
	public final static int STATE_READY = 1;
	
	/** 加载状态 */
	public final static int STATE_LOADING = 2;
	
	/** 无状态 */
	public final static int STATE_NO = 3;
	
	/** 空状态 */
	public final static int STATE_EMPTY = 4;

	/** 尾部的主布局. */
	private LinearLayout footerView;
	
	/** 尾部进度View. */
	private ProgressBar footerProgressBar;
	
	/**  尾部文本View. */
	private TextView footerTextView;
	
	/** 尾部的高度 */
	private int footerHeight;
	
	/**
	 * 构造函数
	 * @param context the context
	 */
	public AbListViewFooter(Context context) {
		super(context);
		initView(context);
	}

	/**
	 * 构造函数
	 * @param context the context
	 * @param attrs the attrs
	 */
	public AbListViewFooter(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView(context);
		setState(STATE_READY);
	}
	
	/**
	 * 初始化View.
	 * @param context the context
	 */
	private void initView(Context context) {
		mContext = context;
		
		//底部刷新
		footerView  = new LinearLayout(context);  
		//设置布局 水平方向  
		footerView.setOrientation(LinearLayout.HORIZONTAL);
		footerView.setGravity(Gravity.CENTER); 
		footerView.setMinimumHeight(AbViewUtil.scaleValue(mContext,100));
		footerTextView = new TextView(context);  
		footerTextView.setGravity(Gravity.CENTER_VERTICAL);
		setTextColor(Color.rgb(107, 107, 107));
		AbViewUtil.setTextSize(footerTextView,30);

		AbViewUtil.setPadding(footerView, 0, 10, 0, 10);
		
		footerProgressBar = new ProgressBar(context,null,android.R.attr.progressBarStyle);
		footerProgressBar.setVisibility(View.GONE);
		
		LayoutParams layoutParamsWW = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		layoutParamsWW.gravity = Gravity.CENTER;
		layoutParamsWW.width = AbViewUtil.scaleValue(mContext, 50);
		layoutParamsWW.height = AbViewUtil.scaleValue(mContext, 50);
		layoutParamsWW.rightMargin = AbViewUtil.scaleValue(mContext, 10);
		footerView.addView(footerProgressBar,layoutParamsWW);
		
		LayoutParams layoutParamsWW1 = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		footerView.addView(footerTextView,layoutParamsWW1);
		
		LayoutParams layoutParamsFW = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		addView(footerView,layoutParamsFW);
		
		//获取View的高度
		AbViewUtil.measureView(this);
		footerHeight = this.getMeasuredHeight();
	}

	/**
	 * 设置当前状态.
	 * @param state the new state
	 */
	public void setState(int state) {
		
		if (state == STATE_READY) {
			footerView.setVisibility(View.VISIBLE);
			footerTextView.setVisibility(View.VISIBLE);
			footerProgressBar.setVisibility(View.GONE);
			footerTextView.setText("载入更多");
		} else if (state == STATE_LOADING) {
			footerView.setVisibility(View.VISIBLE);
			footerTextView.setVisibility(View.VISIBLE);
			footerProgressBar.setVisibility(View.VISIBLE);
			footerTextView.setText("正在加载...");
		}else if(state == STATE_NO){
			footerView.setVisibility(View.GONE);
			footerTextView.setVisibility(View.VISIBLE);
			footerProgressBar.setVisibility(View.GONE);
			footerTextView.setText("没有了！");
		}else if(state == STATE_EMPTY){
			footerView.setVisibility(View.GONE);
			footerTextView.setVisibility(View.GONE);
			footerProgressBar.setVisibility(View.GONE);
			footerTextView.setText("没有数据");
		}
		mState = state;
	}
	
	/**
	 * 获取可见的高度.
	 * @return the visiable height
	 */
	public int getVisiableHeight() {
		LayoutParams lp = (LayoutParams)footerView.getLayoutParams();
		return lp.height;
	}

	/**
	 * 隐藏footerView.
	 */
	public void hide() {
		LayoutParams lp = (LayoutParams) footerView.getLayoutParams();
		lp.height = 0;
		footerView.setLayoutParams(lp);
		footerView.setVisibility(View.GONE);
	}

	/**
	 * 显示footerView.
	 */
	public void show() {
		footerView.setVisibility(View.VISIBLE);
		LayoutParams lp = (LayoutParams) footerView.getLayoutParams();
		lp.height = LayoutParams.WRAP_CONTENT;
		footerView.setLayoutParams(lp);
	}

	
	/**
	 * 设置字体颜色.
	 * @param color the new text color
	 */
	public void setTextColor(int color){
		footerTextView.setTextColor(color);
	}
	
	/**
	 * 设置字体大小.
	 * @param size the new text size
	 */
	public void setTextSize(int size){
		footerTextView.setTextSize(size);
	}
	
	/**
	 * 设置背景颜色.
	 * @param color the new background color
	 */
	public void setBackgroundColor(int color){
		footerView.setBackgroundColor(color);
	}

	/**
	 * 获取Footer ProgressBar，用于设置自定义样式.
	 * @return the footer progress bar
	 */
	public ProgressBar getFooterProgressBar() {
		return footerProgressBar;
	}

	/**
	 * 设置Footer ProgressBar样式.
	 * @param indeterminateDrawable the new footer progress bar drawable
	 */
	public void setFooterProgressBarDrawable(Drawable indeterminateDrawable) {
		footerProgressBar.setIndeterminateDrawable(indeterminateDrawable);
	}

	/**
	 * 获取高度.
	 * @return the footer height
	 */
	public int getFooterHeight() {
		return footerHeight;
	}
	
	/**
	 * 设置高度.
	 * @param height 新的高度
	 */
	public void setVisiableHeight(int height) {
		if (height < 0) height = 0;
		LayoutParams lp = (LayoutParams) footerView.getLayoutParams();
		lp.height = height;
		footerView.setLayoutParams(lp);
	}
	
	/**
	 * 获取当前状态.
	 * @return the state
	 */
	public int getState(){
	    return mState;
	}
	

}
