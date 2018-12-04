package com.ningcui.mylibrary.model;

/**
 * Copyright 李挺哲
 * 创建人：litingzhe
 * 邮箱：453971498@qq.com
 * Created by litingzhe on 2017/4/11 下午3:23.
 * Info 进程信息
 */

public class AbProcessInfo{

	/**
	 * 进程uid.
	 */
	public String uid;
	
	/** 进程名称 */
	public String processName;

	/** 进程pid */
	public int pid;

	/**  占用的内存 B. */
	public long memory;
	
	/**  占用的CPU. */
	public String cpu;
	
	/**  进程的状态，其中S表示休眠，R表示正在运行，Z表示僵死状态，N表示该进程优先值是负数. */
	public String status;
	
	/**  当前使用的线程数. */
	public String threadsCount;
	
	/**
	 * 空构造函数.
	 */
	public AbProcessInfo() {
		super();
	}

	/**
	 * 构造函数.
	 *
	 * @param processName the process name
	 * @param pid the pid
	 */
	public AbProcessInfo(String processName, int pid) {
		super();
		this.processName = processName;
		this.pid = pid;
	}


}
