package com.litingzhe.justandroid.main.model;

/**
 * Copyright 李挺哲
 * 创建人：litingzhe
 * 邮箱：453971498@qq.com
 * Created by litingzhe on 2017/4/14 下午4:54.
 * 类描述：
 */


public class SampleModel {

    private  String  title;


    private  int  drawableId;

    public SampleModel(String title, int drawableId) {
        this.title = title;
        this.drawableId = drawableId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getDrawableId() {
        return drawableId;
    }

    public void setDrawableId(int drawableId) {
        this.drawableId = drawableId;
    }
}
