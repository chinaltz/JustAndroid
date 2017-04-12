package com.ningcui.mylibrary.model;




/**
 * Copyright 李挺哲
 * 创建人：litingzhe
 * 邮箱：453971498@qq.com
 * Created by litingzhe on 2017/4/11 下午3:23.
 * Info 点模型
 */

public class AbPoint {
	
	public double x;
	public double y;
	
	public AbPoint() {
		super();
	}

	public AbPoint(double x, double y) {
		super();
		this.x = x;
		this.y = y;
	}

	@Override
	public String toString() {
		return "(" + x + "," + y + ")";
	}

	@Override
	public boolean equals(Object o) {
		AbPoint point = (AbPoint)o;
		if(this.x == point.x && this.y == point.y){
			return true;   
		}
        return false;   
	}

	@Override
	public int hashCode() {
		return (int)(x*y)^8;
	}
	
	
}
