package com.ningcui.mylibrary.model;

import android.graphics.drawable.Drawable;


/**
 * Copyright 李挺哲
 * 创建人：litingzhe
 * 邮箱：453971498@qq.com
 * Created by litingzhe on 2017/4/11 下午3:23.
 * Info 进程信息
 */

public class AbAppProcessInfo implements Comparable<AbAppProcessInfo>{
	
	/** app名称 */
	public String appName;

	/** 进程名称 */
	public String processName;

	/** 进程PID */
	public int pid;

	/** 进程uid */
	public int uid;

	/** app 图标. */
	public Drawable icon;

	/**  占用的内存. */
	public long memory;
	
	/**  占用的内存. */
	public String cpu;
	
	/**  进程的状态，其中S表示休眠，R表示正在运行，Z表示僵死状态，N表示该进程优先值是负数. */
	public String status;
	
	/**  当前使用的线程数. */
	public String threadsCount;
	
	/**
	 * 空的构造.
	 */
	public AbAppProcessInfo() {
		super();
	}

	/**
	 * 构造函数.
	 *
	 * @param processName the process name
	 * @param pid the pid
	 * @param uid the uid
	 */
	public AbAppProcessInfo(String processName, int pid, int uid) {
		super();
		this.processName = processName;
		this.pid = pid;
		this.uid = uid;
	}

	/**
	 * 比较
	 * @param another
	 * @return
     */
	@Override
	public int compareTo(AbAppProcessInfo another) {
		if(this.processName.compareTo(another.processName)==0){
			if(this.memory < another.memory){
	    		return 1;
	    	}else if(this.memory == another.memory){
	    		return 0;
	    	}else{
	    		return -1;
	    	}
		}else{
			return this.processName.compareTo(another.processName);
		}
	}

}
