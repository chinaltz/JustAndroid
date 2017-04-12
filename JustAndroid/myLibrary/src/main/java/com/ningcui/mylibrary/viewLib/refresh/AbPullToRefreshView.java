package com.ningcui.mylibrary.viewLib.refresh;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.ningcui.mylibrary.utiils.AbViewUtil;


/**
 * Copyright 李挺哲
 * 创建人：litingzhe
 * 邮箱：453971498@qq.com
 * Created by litingzhe on 2017/4/11 下午3:23.
 * Info 下拉刷新和加载更多的View
 */
public class AbPullToRefreshView extends LinearLayout {
	
	/** 上下文. */
	private Context mContext = null;
	
	/** 下拉刷新的开关. */
    private boolean mEnablePullRefresh = true;
    
    /** 加载更多的开关. */
    private boolean mEnableLoadMore = true;
    
    /** x上一次保存的. */
	private int mLastMotionX;
    
    /** y上一次保存的. */
	private int mLastMotionY;
	
	/** header view. */
	private AbListViewBaseHeader mHeaderView;
    private AbListViewHeader defaultViewHeader;

    /** footer view. */
	private AbListViewFooter mFooterView;

	/** list or grid. */
	private AdapterView<?> mAdapterView;

	/** Scrollview. */
	private ScrollView mScrollView;

	/** header view 高度. */
	private int mHeaderViewHeight;

	/** footer view 高度. */
	private int mFooterViewHeight;

	/** 滑动状态. */
	private int mPullState;

	/** 上滑动作. */
	private static final int PULL_UP_STATE = 0;

	/** 下拉动作. */
	private static final int PULL_DOWN_STATE = 1;

	/** 上一次的数量. */
	private int mCount = 0;

	/** 正在下拉刷新. */
	private boolean mPullRefreshing = false;

	/** 正在加载更多. */
	private boolean mPullLoading = false;

	/** Footer加载更多监听器. */
	private OnFooterLoadListener mOnFooterLoadListener;

	/** Header下拉刷新监听器. */
	private OnHeaderRefreshListener mOnHeaderRefreshListener;

	/** 空的View. */
	private View emptyView;

	/**
	 * 构造.
	 * @param context the context
	 * @param attrs the attrs
	 */
	public AbPullToRefreshView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	/**
	 * 构造.
	 * @param context the context
	 */
	public AbPullToRefreshView(Context context) {
		super(context);
		init(context);
	}

	/**
	 * 初始化View.
	 * @param context the context
	 */
	private void init(Context context) {
	    mContext = context;
		this.setOrientation(LinearLayout.VERTICAL);

        // 增加HeaderView 在Activity中自己定义吧
        defaultViewHeader = new AbListViewHeader(mContext);
		setHeaderView(defaultViewHeader);
	}

    public AbListViewHeader getDefaultHeaderView() {
        return  defaultViewHeader;
    }

	/**
	 * 增加头布局.
	 */
	public void setHeaderView(AbListViewBaseHeader headerView) {

        this.mHeaderView = headerView;
		//获取View的高度
		AbViewUtil.measureView(this.mHeaderView);
		mHeaderViewHeight = this.mHeaderView.getMeasuredHeight();

        mHeaderView.setGravity(Gravity.BOTTOM);

		LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, mHeaderViewHeight);
		// 设置topMargin的值为负的header View高度,即将其隐藏在最上方
		params.topMargin = -(mHeaderViewHeight);

        if(getChildCount() > 1){
            removeViewAt(0);
        }

		addView(mHeaderView,0,params);

	}

	/**
	 * 增加尾布局.
	 */
	private void addFooterView() {

        mFooterView = new  AbListViewFooter(mContext);
        mFooterViewHeight = mFooterView.getFooterHeight();

		LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, mFooterViewHeight);
		addView(mFooterView, params);
	}

	/**
	 * 在此添加footer view保证添加到linearlayout中的最后.
	 */
	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
		addFooterView();
		initContentAdapterView();
	}

	/**
	 * init AdapterView like ListView,GridView and so on or init ScrollView.
	 */
	private void initContentAdapterView() {
		int count = getChildCount();
		if (count < 3) {
			throw new IllegalArgumentException("this layout must contain 3 child views,and AdapterView or ScrollView must in the second position!");
		}
		View view = null;
		for (int i = 0; i < count - 1; ++i) {
			view = getChildAt(i);
			if (view instanceof AdapterView<?>) {
				mAdapterView = (AdapterView<?>) view;
			}
			if (view instanceof ScrollView) {
				// finish later
				mScrollView = (ScrollView) view;
			}
		}
		if (mAdapterView == null && mScrollView == null) {
			throw new IllegalArgumentException("must contain a AdapterView or ScrollView in this layout!");
		}
	}


	@Override
	public boolean onInterceptTouchEvent(MotionEvent e) {
		int x = (int) e.getX();
		int y = (int) e.getY();
		switch (e.getAction()) {
		case MotionEvent.ACTION_DOWN:
			// 首先拦截down事件,记录y坐标
			mLastMotionX = x;
			mLastMotionY = y;
			break;
		case MotionEvent.ACTION_MOVE:
			// deltaY > 0 是向下运动,< 0是向上运动
			int deltaX = x - mLastMotionX;
			int deltaY = y - mLastMotionY;

			//解决点击与移动的冲突
			if(Math.abs(deltaX)< Math.abs(deltaY) && Math.abs(deltaY) > 10){
				if (isRefreshViewScroll(deltaY)) {
					return true;
				}
			}

			break;
		}
		return false;
	}

	/**
	 * 如果在onInterceptTouchEvent()方法中没有拦截(即onInterceptTouchEvent()方法中 return
	 * false)则由PullToRefreshView 的子View来处理;否则由下面的方法来处理(即由PullToRefreshView自己来处理)
	 */
	@Override
	public boolean onTouchEvent(MotionEvent event) {

		int y = (int) event.getY();

		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			break;
		case MotionEvent.ACTION_MOVE:
			int deltaY = y - mLastMotionY;
			if (mPullState == PULL_DOWN_STATE) {
				// 执行下拉
				headerPrepareToRefresh(deltaY);
			} else if (mPullState == PULL_UP_STATE) {
				// 执行上拉
				footerPrepareToRefresh(deltaY);
			}
			mLastMotionY = y;
			break;
		//UP和CANCEL执行相同的方法
		case MotionEvent.ACTION_UP:
		case MotionEvent.ACTION_CANCEL:
			int topMargin = getHeaderTopMargin();
			if (mPullState == PULL_DOWN_STATE) {
				if (topMargin >= 0) {
					// 开始刷新
					headerRefreshing();
				} else {
					// 还没有执行刷新，重新隐藏
					setHeaderTopMargin(-mHeaderViewHeight);
				}
			} else if (mPullState == PULL_UP_STATE) {
				//控制在什么时候加载更多
				if (Math.abs(topMargin) >= mHeaderViewHeight + mFooterViewHeight) {
					// 开始执行footer 刷新
					footerLoading();
				} else {
					// 还没有执行刷新，重新隐藏
					setHeaderTopMargin(-mHeaderViewHeight);
				}
			}
			break;

		}
		return super.onTouchEvent(event);
	}

	/**
	 * 判断滑动方向，和是否响应事件.
	 *
	 * @param deltaY  deltaY > 0 是向下运动,< 0是向上运动
	 * @return true, if is refresh view scroll
	 */
	private boolean isRefreshViewScroll(int deltaY) {

		if (mPullRefreshing || mPullLoading) {
			return false;
		}
		// 对于ListView和GridView
		if (mAdapterView != null) {
			// 子view(ListView or GridView)滑动到最顶端
			if (deltaY > 0) {
				// 判断是否禁用下拉刷新操作
				if (!mEnablePullRefresh) {
					return false;
				}
				View child = mAdapterView.getChildAt(0);
				if (child == null) {
					// 如果mAdapterView中没有数据,不拦截
					//return false;

					mPullState = PULL_DOWN_STATE;
					return true;
				}
				if (mAdapterView.getFirstVisiblePosition() == 0 && child.getTop() == 0) {
					mPullState = PULL_DOWN_STATE;
					return true;
				}
				int top = child.getTop();
				int padding = mAdapterView.getPaddingTop();
				if (mAdapterView.getFirstVisiblePosition() == 0 && Math.abs(top - padding) <= 11) {
					mPullState = PULL_DOWN_STATE;
					return true;
				}

			} else if (deltaY < 0) {
				// 判断是否禁用上拉加载更多操作
				if (!mEnableLoadMore) {
					return false;
				}
				View lastChild = mAdapterView.getChildAt(mAdapterView.getChildCount() - 1);
				if (lastChild == null) {
					//如果mAdapterView中没有数据,不拦截
					//return false;
					mPullState = PULL_UP_STATE;
					return true;
				}
				// 最后一个子view的Bottom小于父View的高度说明mAdapterView的数据没有填满父view,
				// 等于父View的高度说明mAdapterView已经滑动到最后
				if (lastChild.getBottom() <= getHeight() && mAdapterView.getLastVisiblePosition() == mAdapterView.getCount() - 1) {
					mPullState = PULL_UP_STATE;
					return true;
				}
			}
		}
		// 对于ScrollView
		if (mScrollView != null) {

			// 子scroll view滑动到最顶端
			View child = mScrollView.getChildAt(0);
			if (deltaY > 0 && mScrollView.getScrollY() == 0) {
				// 判断是否禁用下拉刷新操作
				if (!mEnablePullRefresh) {
					return false;
				}
				mPullState = PULL_DOWN_STATE;
				return true;
			} else if (deltaY < 0 && child.getMeasuredHeight() <= getHeight() + mScrollView.getScrollY()) {
				// 判断是否禁用上拉加载更多操作
				if (!mEnableLoadMore) {
					return false;
				}
				mPullState = PULL_UP_STATE;
				return true;
			}
		}
		return false;
	}

	/**
	 * header 准备刷新,手指移动过程,还没有释放.
	 *
	 * @param deltaY 手指滑动的距离
	 */
	private void headerPrepareToRefresh(int deltaY) {
		if (mPullRefreshing || mPullLoading) {
			return;
		}

		int newTopMargin = updateHeaderViewTopMargin(deltaY);
		// 当header view的topMargin>=0时，说明header view完全显示出来了 ,修改header view 的提示状态
		if (newTopMargin >= 0 && mHeaderView.getState() != AbListViewHeader.STATE_REFRESHING) {
		    //提示松开刷新
		    mHeaderView.setState(AbListViewHeader.STATE_READY);

		} else if (newTopMargin < 0 && newTopMargin > -mHeaderViewHeight) {
		    //提示下拉刷新
		    mHeaderView.setState(AbListViewHeader.STATE_NORMAL);
		}
	}

	/**
	 * footer 准备刷新,手指移动过程,还没有释放 移动footer view高度同样和移动header view
	 * 高度是一样，都是通过修改header view的topmargin的值来达到.
	 *
	 * @param deltaY  手指滑动的距离
	 */
	private void footerPrepareToRefresh(int deltaY) {
		if (mPullRefreshing || mPullLoading) {
			return;
		}
		int newTopMargin = updateHeaderViewTopMargin(deltaY);
		// 如果header view topMargin 的绝对值大于或等于header + footer 的高度
		// 说明footer view 完全显示出来了，修改footer view 的提示状态
		if (Math.abs(newTopMargin) >= (mHeaderViewHeight + mFooterViewHeight) && mFooterView.getState()  != AbListViewFooter.STATE_LOADING) {
			mFooterView.setState(AbListViewFooter.STATE_READY);
		} else if (Math.abs(newTopMargin) < (mHeaderViewHeight + mFooterViewHeight)) {
		    mFooterView.setState(AbListViewFooter.STATE_LOADING);
		}
	}

	/**
	 * 修改Header view top margin的值.
	 *
	 * @param deltaY the delta y
	 * @return the int
	 */
	private int updateHeaderViewTopMargin(int deltaY) {
		LayoutParams params = (LayoutParams) mHeaderView.getLayoutParams();
		float newTopMargin = params.topMargin + deltaY * 0.3f;
		// 这里对上拉做一下限制,因为当前上拉后然后不释放手指直接下拉,会把下拉刷新给触发了
		// 表示如果是在上拉后一段距离,然后直接下拉
		if (deltaY > 0 && mPullState == PULL_UP_STATE && Math.abs(params.topMargin) <= mHeaderViewHeight) {
			return params.topMargin;
		}
		// 同样地,对下拉做一下限制,避免出现跟上拉操作时一样的bug
		if (deltaY < 0 && mPullState == PULL_DOWN_STATE && Math.abs(params.topMargin) >= mHeaderViewHeight) {
			return params.topMargin;
		}
		params.topMargin = (int) newTopMargin;
		mHeaderView.setLayoutParams(params);
		return params.topMargin;
	}

	/**
	 * 下拉刷新.
	 */
	public void headerRefreshing() {
		mPullRefreshing = true;
		mHeaderView.setState(AbListViewHeader.STATE_REFRESHING);
		setHeaderTopMargin(0);
		if (mOnHeaderRefreshListener != null) {
			mOnHeaderRefreshListener.onHeaderRefresh(this);
		}
	}

	/**
	 * 加载更多.
	 */
	private void footerLoading() {
		mPullLoading = true;
		int top = mHeaderViewHeight + mFooterViewHeight;
		setHeaderTopMargin(-top);
		if (mOnFooterLoadListener != null) {
			mOnFooterLoadListener.onFooterLoad(this);
		}
	}

	/**
	 * 设置header view 的topMargin的值.
	 *
	 * @param topMargin the new header top margin
	 */
	private void setHeaderTopMargin(int topMargin) {
		LayoutParams params = (LayoutParams) mHeaderView.getLayoutParams();
		params.topMargin = topMargin;
		mHeaderView.setLayoutParams(params);
	}

	/**
	 * header view 完成更新后恢复初始状态.
	 */
	public void onHeaderRefreshFinish() {
		setHeaderTopMargin(-mHeaderViewHeight);
		mHeaderView.setState(AbListViewHeader.STATE_NORMAL);
		if(mAdapterView!=null){
			mCount = mAdapterView.getCount();
			//判断有没有数据
			if(mCount > 0){
				mFooterView.setState(AbListViewFooter.STATE_READY);
				if (mAdapterView != null && emptyView!=null) {
					mAdapterView.setVisibility(View.VISIBLE);
					emptyView.setVisibility(View.GONE);
				}else if (mScrollView != null  && emptyView!=null) {
					mScrollView.setVisibility(View.VISIBLE);
					emptyView.setVisibility(View.GONE);
				}
			}else{
				//没有数据
				if (mAdapterView != null && emptyView!=null) {
					mAdapterView.setVisibility(View.GONE);
					emptyView.setVisibility(View.VISIBLE);
				}else if (mScrollView != null  && emptyView!=null) {
					mScrollView.setVisibility(View.GONE);
					emptyView.setVisibility(View.VISIBLE);
				}

				mFooterView.setState(AbListViewFooter.STATE_EMPTY);
			}
		}else{
			mFooterView.setState(AbListViewFooter.STATE_READY);
		}

		mPullRefreshing = false;
	}

	/**
	 * footer view 完成更新后恢复初始状态.
	 */
	public void onFooterLoadFinish() {
		setHeaderTopMargin(-mHeaderViewHeight);
		mHeaderView.setState(AbListViewHeader.STATE_NORMAL);
		if(mAdapterView!=null){
			int countNew = mAdapterView.getCount();
			//判断有没有更多数据了
			if(countNew > mCount){
				mFooterView.setState(AbListViewFooter.STATE_READY);
			}else{
				mFooterView.setState(AbListViewFooter.STATE_NO);
			}
		}else{
			mFooterView.setState(AbListViewFooter.STATE_READY);
		}

		mPullLoading = false;
	}


	/**
	 * 获取当前header view 的topMargin.
	 *
	 * @return the header top margin
	 */
	private int getHeaderTopMargin() {
		LayoutParams params = (LayoutParams) mHeaderView.getLayoutParams();
		return params.topMargin;
	}


	/**
	 * 设置下拉刷新的监听器.
	 *
	 * @param headerRefreshListener the new on header refresh listener
	 */
	public void setOnHeaderRefreshListener(OnHeaderRefreshListener headerRefreshListener) {
		mOnHeaderRefreshListener = headerRefreshListener;
	}

	/**
	 * 设置加载更多的监听器.
	 *
	 * @param footerLoadListener the new on footer load listener
	 */
	public void setOnFooterLoadListener(OnFooterLoadListener footerLoadListener) {
		mOnFooterLoadListener = footerLoadListener;
	}


	/**
     * 打开或者关闭下拉刷新功能.
     * @param enable 开关标记
     */
    public void setPullRefreshEnable(boolean enable) {
        mEnablePullRefresh = enable;
    }

    /**
     * 打开或者关闭加载更多功能.
     * @param enable 开关标记
     */
    public void setLoadMoreEnable(boolean enable) {
        mEnableLoadMore = enable;
    }

    /**
     * 下拉刷新是打开的吗.
     *
     * @return true, if is enable pull refresh
     */
    public boolean isEnablePullRefresh(){
        return mEnablePullRefresh;
    }

    /**
     * 加载更多是打开的吗.
     *
     * @return true, if is enable load more
     */
    public boolean isEnableLoadMore(){
        return mEnableLoadMore;
    }

    /**
     * 获取Header View.
     *
     * @return the header view
     */
    public AbListViewBaseHeader getHeaderView() {
        return mHeaderView;
    }

    /**
     * 获取Footer View.
     *
     * @return the footer view
     */
    public AbListViewFooter getFooterView() {
        return mFooterView;
    }
    

	/**
	 * 设置空的View
	 * @param view
     */
	public void setEmptyView(View view){
		this.emptyView = view;
		LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		addView(emptyView, 2,params);
		this.emptyView.setVisibility(View.GONE);
	}
    
    /**
     * Interface definition for a callback to be invoked when list/grid footer
     * view should be refreshed.
     */
	public interface OnFooterLoadListener {
		
		/**
		 * On footer load.
		 * @param view the view
		 */
		public void onFooterLoad(AbPullToRefreshView view);
	}

	/**
	 * Interface definition for a callback to be invoked when list/grid header
	 * view should be refreshed.
	 */
	public interface OnHeaderRefreshListener {
		
		/**
		 * On header refresh.
		 * @param view the view
		 */
		public void onHeaderRefresh(AbPullToRefreshView view);
	}



}
