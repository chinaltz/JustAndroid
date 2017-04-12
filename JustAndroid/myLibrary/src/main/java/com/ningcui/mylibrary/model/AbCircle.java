package com.ningcui.mylibrary.model;



/**
 * Copyright 李挺哲
 * 创建人：litingzhe
 * 邮箱：453971498@qq.com
 * Created by litingzhe on 2017/4/11 下午3:23.
 * Info 圆形模型
 */
public class AbCircle {
	
	public AbPoint point;
	public double r;

	public AbCircle() {
		super();
	}

	public AbCircle(AbPoint point, double r) {
		super();
		this.point = point;
		this.r = r;
	}

	@Override
	public String toString() {
		return "(" + point.x + "," + point.y + "),r="+r;
	}

}
