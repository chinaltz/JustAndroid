package com.ningcui.mylibrary.viewLib.listener;

/**
 * Copyright 李挺哲
 * 创建人：litingzhe
 * 邮箱：453971498@qq.com
 * Created by litingzhe on 2017/4/11 下午3:23.
 * Info 滚动事件监听器
 */
public abstract  class AbOnScrollListener {

    /**
     * 滚动事件
     * @param position 位置
     */
    public void onScrollPosition(int position){};

    /**
     * 滚动事件
     * @param scrollY Y滚动的距离
     */
    public void onScrollY(int scrollY){};


}
