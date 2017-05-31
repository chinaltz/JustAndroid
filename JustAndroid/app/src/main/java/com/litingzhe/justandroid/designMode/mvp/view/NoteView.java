package com.litingzhe.justandroid.designMode.mvp.view;

/**
 * Copyright 李挺哲
 * 创建人：litingzhe
 * 邮箱：453971498@qq.com
 * Created by litingzhe on 2017/5/31 下午1:58.
 * 类描述：
 */


public interface NoteView extends BaseView {



    void refreshList();

    void showAddNoteDialog();

    void disMissDialog();


}
