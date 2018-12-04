package com.litingzhe.justandroid.designMode.mvp.presenter;

import com.litingzhe.justandroid.designMode.mvp.view.NoteView;
import com.litingzhe.justandroid.global.MyApplication;
import com.litingzhe.justandroid.netdb.db.model.Note;
import com.ningcui.mylibrary.utiils.AbDialogUtil;
import com.ningcui.mylibrary.utiils.AbStrUtil;

import java.util.Date;

/**
 * Copyright 李挺哲
 * 创建人：litingzhe
 * 邮箱：453971498@qq.com
 * Created by litingzhe on 2017/5/31 下午1:57.
 * 类描述：
 */


public class NotePresenter implements BaseViewPresenter {


    private NoteView view;

    public NotePresenter(NoteView view) {
        this.view = view;
    }

    public void addNote(Note note) {

        MyApplication.getInstance().getDaoSession().getNoteDao().insert(note);
        view.disMissDialog();
        view.refreshList();


    }

    public  void deleteNote(Note note) {

        MyApplication.getInstance().getDaoSession().getNoteDao().delete(note);


        view.refreshList();
    }


}
