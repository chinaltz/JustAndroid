package com.ningcui.mylibrary.viewLib.listener;

/**
 * Copyright 李挺哲
 * 创建人：litingzhe
 * 邮箱：453971498@qq.com
 * Created by litingzhe on 2017/4/11 下午3:23.
 * Info 进度事件监听器
 */
public interface AbOnProgressListener {

    /**
     * 进度.
     *
     * @param progress the progress
     */
    public void onProgress(int progress);

    /**
     * 完成.
     */
    public void onComplete();
}
