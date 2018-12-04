package com.litingzhe.justandroid.designMode.mvp.adpater;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.litingzhe.justandroid.R;
import com.litingzhe.justandroid.netdb.db.model.Note;
import com.ningcui.mylibrary.utiils.AbDateUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Copyright 李挺哲
 * 创建人：litingzhe
 * 邮箱：453971498@qq.com
 * Created by litingzhe on 2017/5/25 下午2:25.
 * 类描述：
 */


public class MvpNoteAdapter extends BaseAdapter {

    private Context mContext;
    private List<Note> data;

    public MvpNoteAdapter(Context mCOntext, List<Note> data) {
        this.mContext = mCOntext;
        this.data = data;
    }

    public Context getmCOntext() {
        return mContext;
    }

    public void setmCOntext(Context mCOntext) {
        this.mContext = mCOntext;
    }

    public List<Note> getData() {
        return data;
    }

    public void setData(List<Note> data) {
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder = null;
        if (convertView == null) {

            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_list_note, null);

            viewHolder = new ViewHolder(convertView);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();


        }

        Note note = data.get(position);
        viewHolder.contentText.setText(note.getNoteContent());

        viewHolder.titleText.setText(note.getNoteTitle());

        viewHolder.dateText.setText(AbDateUtil.getStringByFormat(note.getCreatDate(), AbDateUtil.dateFormatYMDHM));
        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.titleText)
        TextView titleText;
        @BindView(R.id.dateText)
        TextView dateText;
        @BindView(R.id.contentText)
        TextView contentText;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
