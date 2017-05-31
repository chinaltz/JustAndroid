package com.litingzhe.justandroid.designMode.mvp.activity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.litingzhe.justandroid.R;
import com.litingzhe.justandroid.designMode.mvp.presenter.NotePresenter;
import com.litingzhe.justandroid.designMode.mvp.view.NoteView;
import com.litingzhe.justandroid.global.MyApplication;
import com.litingzhe.justandroid.netdb.db.adapter.NoteAdapter;
import com.litingzhe.justandroid.netdb.db.dao.NoteDao;
import com.litingzhe.justandroid.netdb.db.model.Note;
import com.ningcui.mylibrary.app.base.AbBaseActivity;
import com.ningcui.mylibrary.utiils.AbDialogUtil;
import com.ningcui.mylibrary.utiils.AbStrUtil;
import com.ningcui.mylibrary.utiils.AbToastUtil;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Copyright 李挺哲
 * 创建人：litingzhe
 * 邮箱：453971498@qq.com
 * Created by litingzhe on 2017/4/28 下午2:41.
 * 类描述：GreenDao 简单使用
 */


public class MvpGreenDaoActivity extends AbBaseActivity implements NoteView {

    @BindView(R.id.nav_back)
    LinearLayout navBack;
    @BindView(R.id.nav_title)
    TextView navTitle;
    @BindView(R.id.nav_right_icon)
    ImageView navRightIcon;
    @BindView(R.id.nav_right)
    LinearLayout navRight;
    @BindView(R.id.nav_right_text)
    TextView navRightText;
    @BindView(R.id.listView)
    SwipeMenuListView listView;


    private NoteDao noteDao;

    private List<Note> noteList;
    private NoteAdapter adapter;

    private NotePresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_note);
        ButterKnife.bind(this);
        navBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        presenter = new NotePresenter(this);
        noteDao = MyApplication.getInstance().getDaoSession().getNoteDao();
        navBack.setVisibility(View.VISIBLE);
        navTitle.setText("简单笔记本");
        navRight.setVisibility(View.VISIBLE);
        navRightText.setText("添加");
        noteList = new ArrayList<>();
        adapter = new NoteAdapter(mContext, noteList);


        navRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showAddNoteDialog();
            }
        });

        refreshList();
        listView.setAdapter(adapter);


//1.创建侧滑按钮菜单
        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {
                // create "open" item

                // create "delete" item
                SwipeMenuItem deleteItem = new SwipeMenuItem(
                        getApplicationContext());
                // set item background
                deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9,
                        0x3F, 0x25)));
                // set item width
                deleteItem.setWidth(150);
                // set a icon
                deleteItem.setIcon(R.drawable.ic_delete);
                // add to menu
                menu.addMenuItem(deleteItem);
            }
        };
        // 设置 MenuCreator
        listView.setMenuCreator(creator);

        // 2. 设置侧滑按钮 监听事件
        listView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                switch (index) {
                    case 0:
                        // delete

                        presenter.deleteNote(noteList.get(position));
                        break;

                }
                return false;
            }
        });


    }


    @Override
    public void showMsg(String Msg) {

        AbToastUtil.showToast(mContext, Msg);

    }


    @Override
    public void refreshList() {
        noteList.clear();
        QueryBuilder<Note> qb = noteDao.queryBuilder();
        noteList.addAll(qb.list());
        adapter.notifyDataSetChanged();

    }

    @Override
    public void showAddNoteDialog() {

        View view = LayoutInflater.from(mContext).inflate(R.layout.view_add_note, null);
        final EditText titleText = (EditText) view.findViewById(R.id.titleText);
        final EditText contentText = (EditText) view.findViewById(R.id.contentText);
        Button commitButton = (Button) view.findViewById(R.id.commitButton);
        AbDialogUtil.showDialog(view, Gravity.CENTER);

        commitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String title = titleText.getText().toString();
                String content = contentText.getText().toString();
                if (AbStrUtil.isEmpty(title)) {
//                    disMissDialog();
                    showMsg("请输入记事本标题");
                    return;
                }
                if (AbStrUtil.isEmpty(content)) {
//                    disMissDialog();
                    showMsg("请输入记事本内容");
                    return;
                }
                Note note = new Note();
                note.setCreatDate(new Date());
                note.setNoteTitle(title);
                note.setFavFlag(0);
                note.setNoteContent(content);
                presenter.addNote(note);


            }
        });

    }

    @Override
    public void disMissDialog() {

        AbDialogUtil.removeDialog(mContext);


    }
}
