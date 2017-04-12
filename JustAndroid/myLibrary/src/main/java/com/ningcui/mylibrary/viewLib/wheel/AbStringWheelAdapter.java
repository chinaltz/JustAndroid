package com.ningcui.mylibrary.viewLib.wheel;



import com.ningcui.mylibrary.utiils.AbStrUtil;

import java.util.List;



/**
 * Copyright 李挺哲
 * 创建人：litingzhe
 * 邮箱：453971498@qq.com
 * Created by litingzhe on 2017/4/11 下午3:23.
 * Info 轮子适配器（字符串）
 */
public class AbStringWheelAdapter implements AbWheelAdapter {
	
	/** 条目列表. */
	private List<String> items;

	/** 长度. */
	private int length = -1;

	/**
	 * 构造函数.
	 * @param items the items
	 */
	public AbStringWheelAdapter(List<String> items) {
		this.items = items;
        getMaximumLength();
	}


	@Override
	public String getItem(int index) {
		if (index >= 0 && index < items.size()) {
			return items.get(index);
		}
		return null;
	}


	@Override
	public int getItemsCount() {
		return items.size();
	}


	@Override
	public int getMaximumLength() {
		if(length!=-1){
			return length;
		}
		for(int i=0;i<items.size();i++){
			String cur = items.get(i);
			int l = AbStrUtil.strLength(cur);
			if(length<l){
                length = l;
			}
		}
		return length;
	}

}
