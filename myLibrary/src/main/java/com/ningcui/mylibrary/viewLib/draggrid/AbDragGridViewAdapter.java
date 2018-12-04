package com.ningcui.mylibrary.viewLib.draggrid;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;


public abstract class AbDragGridViewAdapter extends BaseAdapter {

    public int hidePosition = AdapterView.INVALID_POSITION;

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }



    public void hideView(int pos) {
        hidePosition = pos;
        notifyDataSetChanged();
    }

    public void showHideView() {
        hidePosition = AdapterView.INVALID_POSITION;
        notifyDataSetChanged();
    }

    /**
     * 更新拖动时的gridView
     */
    public abstract void swapView(int draggedPos, int destPos);


}
