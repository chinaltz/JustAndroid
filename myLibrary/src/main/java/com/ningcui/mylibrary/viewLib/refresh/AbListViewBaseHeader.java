
package com.ningcui.mylibrary.viewLib.refresh;
import android.content.Context;
import android.widget.LinearLayout;


/**
 * Copyright 李挺哲
 * 创建人：litingzhe
 * 邮箱：453971498@qq.com
 * Created by litingzhe on 2017/4/11 下午3:23.
 * Info 下拉刷新的Header View 自定义要继承的类
 */
public class AbListViewBaseHeader extends LinearLayout {

    /** 显示 下拉刷新. */
    public final static int STATE_NORMAL = 0;

    /** 显示 松开刷新. */
    public final static int STATE_READY = 1;

    /** 显示 正在刷新.... */
    public final static int STATE_REFRESHING = 2;

	/** 上下文. */
    public Context context;

    /** 当前状态. */
    public int currentState = -1;

	/**
	 * 初始化Header.
	 * @param context the context
	 */
	public AbListViewBaseHeader(Context context) {
		super(context);
        this.context  = context;
	}

	/**
	 * 设置状态.
	 * @param state the new state
	 */
	public void setState(int state) {

		//状态未变化
		if (state == currentState){
            return ;
        }

		switch(state){
			case STATE_NORMAL:
				if (currentState == STATE_READY) {
					//原始状态
				}
				if (currentState == STATE_REFRESHING) {

				}

				break;
			case STATE_READY:
				if (currentState != STATE_READY) {

					
				}
				break;
			case STATE_REFRESHING:

				break;
				default:
        }

        currentState = state;
	}

    public int getState() {
        return currentState;
    }

}
