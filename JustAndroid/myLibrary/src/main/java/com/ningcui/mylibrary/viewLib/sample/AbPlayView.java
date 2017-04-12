package com.ningcui.mylibrary.viewLib.sample;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import com.ningcui.mylibrary.R;
import com.ningcui.mylibrary.app.adapter.AbViewPagerAdapter;
import com.ningcui.mylibrary.viewLib.listener.AbOnChangeListener;
import com.ningcui.mylibrary.viewLib.listener.AbOnItemClickListener;
import com.ningcui.mylibrary.viewLib.listener.AbOnScrollListener;
import com.ningcui.mylibrary.viewLib.listener.AbOnTouchListener;

import java.util.ArrayList;
import java.util.List;




/**
 * Copyright 李挺哲
 * 创建人：litingzhe
 * 邮箱：453971498@qq.com
 * Created by litingzhe on 2017/4/11 下午3:23.
 * Info 可播放显示的View
 */
public class AbPlayView extends LinearLayout {
	
	/** 上下文. */
	private Context context;

	/** 内部的ViewPager. */
	private AbInnerViewPager mViewPager;

	/** 导航的布局. */
	private LinearLayout navLinearLayout;
	
	/** 导航布局参数. */
	public LayoutParams navLayoutParams = null;

	/** 计数. */
	private int count, position;

	/** 导航图片. */
	private Drawable displayDrawable;
	private Drawable hideDrawable;
	
	/** 点击. */
	private AbOnItemClickListener mOnItemClickListener;
	
	/** 改变. */
	private AbOnChangeListener mChangeListener;
	
	/** 滚动. */
	private AbOnScrollListener mScrolledListener;
	
	/** 触摸. */
	private AbOnTouchListener mOnTouchListener;
	
	/** List views. */
	private ArrayList<View> mViewList = null;
	
	/** 适配器. */
	private AbViewPagerAdapter mViewPagerAdapter = null;
	
	/** 导航的点父View. */
	private LinearLayout mNavLayoutParent = null;
	
	/** 导航内容的对齐方式. */
	private int navHorizontalGravity = Gravity.RIGHT;
	
	/** 播放的方向. */
	private int playingDirection = 0;
	
	/** 播放的开关. */
	private boolean play = false;
	
	/**
	 * 创建一个AbSlidingPlayView.
	 *
	 * @param context the context
	 */
	public AbPlayView(Context context) {
		super(context);
		initView(context);
	}

	/**
	 * 从xml初始化的AbSlidingPlayView.
	 *
	 * @param context the context
	 * @param attrs the attrs
	 */
	public AbPlayView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView(context);
	}
	
	/**
	 * 初始化这个View.
	 * @param context the context
	 */
	public void initView(Context context){
		this.context = context;
		navLayoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		this.setOrientation(LinearLayout.VERTICAL);
		
		RelativeLayout mRelativeLayout = new RelativeLayout(context);

		mViewPager = new AbInnerViewPager(context);
		//手动创建的ViewPager,如果用fragment必须调用setId()方法设置一个id
		mViewPager.setId(R.id.view_pager);
		//导航的点
		mNavLayoutParent = new LinearLayout(context);
		mNavLayoutParent.setPadding(0,5, 0, 5);
		navLinearLayout = new LinearLayout(context);
		navLinearLayout.setPadding(15, 1, 15, 1);
		navLinearLayout.setVisibility(View.INVISIBLE);
		mNavLayoutParent.addView(navLinearLayout,new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT));
		
		RelativeLayout.LayoutParams lp1 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp1.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM,RelativeLayout.TRUE);
        lp1.addRule(RelativeLayout.CENTER_HORIZONTAL,RelativeLayout.TRUE);
        lp1.addRule(RelativeLayout.CENTER_VERTICAL,RelativeLayout.TRUE);
        mRelativeLayout.addView(mViewPager,lp1);
		
		RelativeLayout.LayoutParams lp2 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp2.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM,RelativeLayout.TRUE);
		mRelativeLayout.addView(mNavLayoutParent,lp2);
		addView(mRelativeLayout,new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));

        mViewList = new ArrayList<View>();
		mViewPagerAdapter = new AbViewPagerAdapter(context,mViewList);
		mViewPager.setAdapter(mViewPagerAdapter);
		mViewPager.setFadingEdgeLength(0);
		mViewPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int position) {
				makesurePosition();
				onPageSelectedCallBack(position);
			}

			@Override
			public void onPageScrollStateChanged(int state) {
			}

			@Override
			public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
				onPageScrolledCallBack(position);
			}

		});
		
	}

	/**
	 * 创建导航点.
	 */
	public void creatIndex() {
		//显示下面的点
		navLinearLayout.removeAllViews();
		mNavLayoutParent.setHorizontalGravity(navHorizontalGravity);
		navLinearLayout.setGravity(Gravity.CENTER);
		navLinearLayout.setVerticalGravity(Gravity.CENTER);
		navLinearLayout.setVisibility(View.VISIBLE);
		count = mViewList.size();
		navLayoutParams.setMargins(5, 5, 5, 5);
		navLayoutParams.width = 25;
		navLayoutParams.height = 25;
		for (int j = 0; j < count; j++) {
			ImageView imageView = new ImageView(context);
			imageView.setLayoutParams(navLayoutParams);
			if (j == 0) {
				imageView.setImageDrawable(displayDrawable);
			} else {
				imageView.setImageDrawable(hideDrawable);
			}
			navLinearLayout.addView(imageView, j);
		}
		
	}


	/**
	 * 定位点的位置.
	 */
	public void makesurePosition() {
		position = mViewPager.getCurrentItem();
		for (int j = 0; j < count; j++) {
			if (position == j) {
				((ImageView)navLinearLayout.getChildAt(position)).setImageDrawable(displayDrawable);
			} else {
				((ImageView)navLinearLayout.getChildAt(j)).setImageDrawable(hideDrawable);
			}
		}
	}
	
	/**
	 * 添加可播放视图.
	 *
	 * @param view the view
	 */
	public void addView(View view){
        mViewList.add(view);
		if(view instanceof AbsListView){
		}else{
			view.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					if(mOnItemClickListener!=null){
						mOnItemClickListener.onItemClick(v,position);
					}
				}
			});
			view.setOnTouchListener(new OnTouchListener() {
				
				@Override
				public boolean onTouch(View view, MotionEvent event) {
					if(mOnTouchListener!=null){
					    mOnTouchListener.onTouch(event);
					}
					return false;
				}
			});
		}

		notifyDataSetChanged();

	}
	
	/**
	 * 添加可播放视图列表.
	 * @param views the views
	 */
	public void addViews(List<View> views){
        mViewList.addAll(views);
		for(View view:views){
			view.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					if(mOnItemClickListener!=null){
						mOnItemClickListener.onItemClick(v,position);
					}
				}
			});
			
			view.setOnTouchListener(new OnTouchListener() {
				
				@Override
				public boolean onTouch(View view, MotionEvent event) {
					if(mOnTouchListener!=null){
					   mOnTouchListener.onTouch(event);
					}
					return false;
				}
			});
		}
		notifyDataSetChanged();
	}
	
	/**
	 * 删除可播放视图.
	 */
	@Override
	public void removeAllViews(){
        mViewList.clear();
		notifyDataSetChanged();
	}

	/**
	 * 更新
	 */
	public void notifyDataSetChanged(){
		mViewPagerAdapter.notifyDataSetChanged();
		creatIndex();
	}
	
	
	
	/**
	 * 设置页面切换事件.
	 * @param position the position
	 */
	private void onPageScrolledCallBack(int position) {
		if(mScrolledListener!=null){
			mScrolledListener.onScrollPosition(position);
		}
		
	}
	
	/**
	 * 设置页面切换事件.
	 * @param position the position
	 */
	private void onPageSelectedCallBack(int position) {
		if(mChangeListener!=null){
			mChangeListener.onChange(position);
		}
		
	}
	
	
	/** 用与轮换的 handler. */
	private Handler handler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			if (msg.what==0) {
				int count = mViewList.size();
				int i = mViewPager.getCurrentItem();
				if(playingDirection==0){
					if(i == count-1){
						playingDirection = -1;
						i--;  
					}else{
						i++;
					}
				}else{
					if(i == 0){
						playingDirection = 0;
						i++;
					}else{
						i--;
					}
				}
				
				mViewPager.setCurrentItem(i, true);
				if(play){
					handler.postDelayed(runnable, 5000);  
				}
		     }
		}
		
	};  
	
	/** 用于轮播的线程. */
	private Runnable runnable = new Runnable() {  
	    public void run() {  
	    	if(mViewPager!=null){
	    		handler.sendEmptyMessage(0);
			} 
	    }  
	};  

	
	/**
	 * 自动轮播.
	 */
	public void startPlay(){
		if(handler!=null){
		   play  = true;
		   handler.postDelayed(runnable, 5000);  
		}
	}
	
	/**
	 * 自动轮播.
	 */
	public void stopPlay(){
		if(handler!=null){
			play  = false;
			handler.removeCallbacks(runnable);  
		}
	}
	
	/**
	 * 设置点击事件监听.
	 *
	 * @param onItemClickListener the new on item click listener
	 */
	public void setOnItemClickListener(AbOnItemClickListener onItemClickListener) {
		mOnItemClickListener = onItemClickListener;
	}
	
	
	/**
	 * 设置页面切换的监听器.
	 *
	 * @param onChangeListener the new on page change listener
	 */
    public void setOnPageChangeListener(AbOnChangeListener onChangeListener) {
    	mChangeListener = onChangeListener;
	}
    
    /**
     * 设置页面滑动的监听器.
     *
     * @param onScrollListener the new on page scrolled listener
     */
    public void setOnPageScrolledListener(AbOnScrollListener onScrollListener) {
    	mScrolledListener = onScrollListener;
	}
    
    /**
     * 设置页面Touch的监听器.
     *
     * @param onTouchListener the new on touch listener
     */
    public void setOnTouchListener(AbOnTouchListener onTouchListener){
    	mOnTouchListener = onTouchListener;
    }
    
	
	/**
	 * 设置页的指示图标
	 *
	 * @param displayResId 选择状态图
	 * @param hideResId 未选择状态图
	 */
	public void setNavPageResources(int displayResId,int hideResId) {
		this.displayDrawable = this.getResources().getDrawable(displayResId);
		this.hideDrawable = this.getResources().getDrawable(hideResId);
		creatIndex();
	}
	

	/**
	 * 获取这个滑动的ViewPager类.
	 *
	 * @return the view pager
	 */
	public ViewPager getViewPager() {
		return mViewPager;
	}
	
	/**
	 * 获取当前的View的数量.
	 *
	 * @return the count
	 */
	public int getCount() {
		return mViewList.size();
	}
	
	/**
	 * 设置页显示条的位置,在AddView前设置.
	 *
	 * @param horizontalGravity the nav horizontal gravity
	 */
	public void setNavHorizontalGravity(int horizontalGravity) {
		navHorizontalGravity = horizontalGravity;
	}
	
	/**
	 * 如果外层有ScrollView需要设置.
	 *
	 * @param parentScrollView the new parent scroll view
	 */
	public void setParentScrollView(ScrollView parentScrollView) {
		this.mViewPager.setParentScrollView(parentScrollView);
	}
	
	/**
	 * 如果外层有ListView需要设置.
	 *
	 * @param parentListView the new parent list view
	 */
	public void setParentListView(ListView parentListView) {
		this.mViewPager.setParentListView(parentListView);
	}
	
	/**
	 * 设置导航点的背景.
	 *
	 * @param resid the new nav layout background
	 */
	public void setNavLayoutBackground(int resid){
		navLinearLayout.setBackgroundResource(resid);
	}
	
}
