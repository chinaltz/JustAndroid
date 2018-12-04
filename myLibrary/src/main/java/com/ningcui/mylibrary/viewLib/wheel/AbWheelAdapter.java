package com.ningcui.mylibrary.viewLib.wheel;


/**
 * Copyright 李挺哲
 * 创建人：litingzhe
 * 邮箱：453971498@qq.com
 * Created by litingzhe on 2017/4/11 下午3:23.
 * Info 轮子适配器接口
 */
public interface AbWheelAdapter {
	
	/**
	 * 获取条目数量.
	 *
	 */
	public int getItemsCount();
	
	/**
	 * 获取条目的值.
	 * @param index 索引
	 */
	public String getItem(int index);
	
	/**
	 * 获取条目的最大字符长度，中文表示2个.
	 */
	public int getMaximumLength();
}
