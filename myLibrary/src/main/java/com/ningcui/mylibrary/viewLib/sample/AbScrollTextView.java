
package com.ningcui.mylibrary.viewLib.sample;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Copyright 李挺哲
 * 创建人：litingzhe
 * 邮箱：453971498@qq.com
 * Created by litingzhe on 2017/4/11 下午3:23.
 * Info 跑马灯一直跑
 */
public class AbScrollTextView extends TextView {


	public AbScrollTextView(Context context) {
		this(context,null);
	}

	public AbScrollTextView(Context context, AttributeSet attrs) {
		this(context, attrs,0);
	}


	public AbScrollTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		//关键
		this.setSingleLine(true);

	}

	/**
	 * 设置为焦点，能一直滚动.
	 */
	@Override
	public boolean isFocused() {
		return true;
	}

}
