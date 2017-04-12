package com.ningcui.mylibrary.viewLib.listener;

/**
 * Copyright 李挺哲
 * 创建人：litingzhe
 * 邮箱：453971498@qq.com
 * Created by litingzhe on 2017/4/11 下午3:23.
 * Info 选择事件监听器
 */
public abstract class AbOnItemSelectListener {

    /**
     * 被点击.
     * @param position the position
     */
    public void onSelect(int position){};

    /**
     * 被点击.
     * @param position1 the position
     * @param position2 the position
     */
    public void onSelect(int position1,int position2){};

}
