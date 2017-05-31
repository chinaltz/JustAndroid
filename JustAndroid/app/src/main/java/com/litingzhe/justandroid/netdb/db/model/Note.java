package com.litingzhe.justandroid.netdb.db.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;

import java.util.Date;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Copyright 李挺哲
 * 创建人：litingzhe
 * 邮箱：453971498@qq.com
 * Created by litingzhe on 2017/5/24 下午1:48.
 * 类描述：
 */


@Entity
public class Note {


    @Id(autoincrement = true)
    private Long noteId = null;


    private String noteTitle;


    private String noteContent;


    private Date creatDate;

    // 0 未收藏  1 收藏
    private int favFlag = 0;

    @Generated(hash = 600629301)
    public Note(Long noteId, String noteTitle, String noteContent, Date creatDate,
            int favFlag) {
        this.noteId = noteId;
        this.noteTitle = noteTitle;
        this.noteContent = noteContent;
        this.creatDate = creatDate;
        this.favFlag = favFlag;
    }

    @Generated(hash = 1272611929)
    public Note() {
    }

    public Long getNoteId() {
        return this.noteId;
    }

    public void setNoteId(Long noteId) {
        this.noteId = noteId;
    }

    public String getNoteTitle() {
        return this.noteTitle;
    }

    public void setNoteTitle(String noteTitle) {
        this.noteTitle = noteTitle;
    }

    public String getNoteContent() {
        return this.noteContent;
    }

    public void setNoteContent(String noteContent) {
        this.noteContent = noteContent;
    }

    public Date getCreatDate() {
        return this.creatDate;
    }

    public void setCreatDate(Date creatDate) {
        this.creatDate = creatDate;
    }

    public int getFavFlag() {
        return this.favFlag;
    }

    public void setFavFlag(int favFlag) {
        this.favFlag = favFlag;
    }





}
