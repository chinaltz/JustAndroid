package com.ningcui.mylibrary.viewLib.expandtabview;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.ToggleButton;


import com.ningcui.mylibrary.R;
import com.ningcui.mylibrary.utiils.AbAppUtil;
import com.ningcui.mylibrary.utiils.AbViewUtil;
import com.ningcui.mylibrary.viewLib.listener.AbOnCheckedChangedListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * Copyright 李挺哲
 * 创建人：litingzhe
 * 邮箱：453971498@qq.com
 * Created by litingzhe on 2017/4/11 下午3:23.
 * Info 分类展开控件，封装了下拉动画
 */

public class AbExpandTabView extends LinearLayout {

    private Context context;

    private PopupWindow popupWindow;

    private AbOnCheckedChangedListener onCheckedChangedListener;

    /**顶部的Tab item*/
    private List<ToggleButton> tabButtons = new ArrayList<ToggleButton>();


    /**tab的主布局*/
    private List<View> tabContentViews = new ArrayList<View>();

    /**当前选中的TAB item*/
    private int currentPosition = -1;
    private ToggleButton currentTabButton;

    /**屏幕尺寸*/
    private int displayWidth;
    private int displayHeight;

    /**tab 的图片资源*/
    private HashMap<Integer,Drawable[]> tabButtonDrawable = new HashMap<Integer,Drawable[]>();

    /**
     * 构造
     * @param context
     */
    public AbExpandTabView(Context context) {
        super(context);
        init(context);
    }

    /**
     * 构造
     * @param context
     * @param attrs
     */
    public AbExpandTabView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    /**
     * 初始化
     * @param context
     */
    private void init(Context context) {
        this.context = context;
        DisplayMetrics display = AbAppUtil.getDisplayMetrics(context);
        displayWidth = display.widthPixels;
        displayHeight = display.heightPixels;
        setOrientation(LinearLayout.HORIZONTAL);
        this.setPadding(0,5,0,5);
    }

    /**
     * 设置tab item默认标题和内容View
     */
    public void setData(List<String> tabTexts,List<View> contentViews) {

        for (int i = 0; i < contentViews.size(); i++) {
            View view = contentViews.get(i);
            int maxHeight = (int) (displayHeight * 0.6);
            RelativeLayout parentLayout = new RelativeLayout(context);

            parentLayout.setBackgroundColor(Color.parseColor("#b0000000"));
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, maxHeight);
            layoutParams.leftMargin = 0;
            layoutParams.rightMargin = 0;
            parentLayout.addView(view, layoutParams);

            tabContentViews.add(parentLayout);

            final ToggleButton tabButton = new ToggleButton(context);
            tabButton.setGravity(Gravity.CENTER);
            tabButton.setTextOn(null);
            tabButton.setTextOff(null);
            tabButton.setBackgroundResource(R.drawable.bg_tab_button);
            tabButton.setTextColor(context.getResources().getColor(R.color.gray_content));
            tabButton.setSingleLine(true);

            //默认标题
            tabButton.setText(tabTexts.get(i));

            AbViewUtil.setTextSize(tabButton, 28);

            addView(tabButton,new LayoutParams(0,LayoutParams.WRAP_CONTENT,1));

            View line = new View(context);
            line.setBackgroundResource(R.color.gray_line);
            LayoutParams layoutParamsLine = new  LayoutParams(1,LayoutParams.MATCH_PARENT);
            layoutParamsLine.setMargins(0,5,0,5);
            if (i < contentViews.size() - 1) {
                addView(line, layoutParamsLine);
            }

            //添加到列表
            tabButtons.add(tabButton);

            //用于判断位置
            tabButton.setTag(i);

            tabButton.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(popupWindow == null || !popupWindow.isShowing()){
                        currentTabButton = tabButton;
                        currentPosition = (Integer) currentTabButton.getTag();
                        showPopup(currentPosition);
                    }else{
                        dismissPopup();
                    }

                }
            });

            tabButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (onCheckedChangedListener != null) {
                        onCheckedChangedListener.onCheckedChanged(currentPosition,isChecked);
                    }
                }
            });

        }
    }

    private void showPopup(int position) {

        if (popupWindow == null) {
            popupWindow = new PopupWindow(tabContentViews.get(position), displayWidth, displayHeight);
            //popupWindow.setAnimationStyle(R.style.popupWindowAnimation);
            popupWindow.setFocusable(true);
            popupWindow.setOutsideTouchable(true);
            popupWindow.setBackgroundDrawable(new BitmapDrawable());
        }

        if (popupWindow.getContentView() != tabContentViews.get(position)) {
            popupWindow.setContentView(tabContentViews.get(position));
        }
        popupWindow.showAsDropDown(this, 0, 0);

        Drawable[] right = tabButtonDrawable.get(position);
        currentTabButton.setCompoundDrawablesWithIntrinsicBounds(null,null,right[0],null);

        //
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                Drawable[] right = tabButtonDrawable.get(currentPosition);
                currentTabButton.setCompoundDrawablesWithIntrinsicBounds(null,null,right[1],null);
            }
        });

    }

    /**
     * 如果菜单成展开状态，则让菜单收回去
     */
    public boolean dismissPopup() {
        if (popupWindow != null && popupWindow.isShowing()) {
            popupWindow.dismiss();
            return true;
        } else {
            return false;
        }

    }

    /**
     * 获取tab的监听事件
     */
    public AbOnCheckedChangedListener getOnCheckedChangedListener() {
        return onCheckedChangedListener;
    }

    /**
     * 设置tab的监听事件
     */
    public void setOnCheckedChangedListener(AbOnCheckedChangedListener onCheckedChangedListener) {
        this.onCheckedChangedListener = onCheckedChangedListener;
    }

    /**
     *
     * 设置Tab上文字的大小.
     * @param size
     */
    public void setTabTextSize(float size){
        for(ToggleButton tab:tabButtons){
            AbViewUtil.setTextSize(tab, size);
        }
    }

    /**
     *
     * 设置Tab上文字的颜色.
     * @param color
     */
    public void setTabTextColor(int color){
        for(ToggleButton tab:tabButtons){
            tab.setTextColor(color);
        }
    }

    /**
     * 根据选择的位置设置tab显示的值
     */
    public void setTabText(String valueText, int position) {
        if (position < tabButtons.size()) {
            tabButtons.get(position).setText(valueText);
        }
    }

    /**
     * 根据选择的位置获取tab显示的值
     */
    public String getTabText(int position) {
        if (position < tabButtons.size() && tabButtons.get(position).getText() != null) {
            return tabButtons.get(position).getText().toString();
        }
        return null;
    }

    /**
     * 设置tab的背景资源
     */
    public void setTabBackgroundResource(int position,int resId) {
        if (position < tabButtons.size()) {
            tabButtons.get(position).setBackgroundResource(resId);
        }
    }

    /**
     * 设置tab的图标资源
     */
    public void setTabCompoundDrawablesWithIntrinsicBounds(int position,Drawable[] right) {
        tabButtonDrawable.put(position,right);
        if (position < tabButtons.size()) {
            if(currentPosition!=position){
                tabButtons.get(position).setCompoundDrawablesWithIntrinsicBounds(null,null,right[1],null);
            }else{
                tabButtons.get(position).setCompoundDrawablesWithIntrinsicBounds(null,null,right[0],null);
            }

        }
    }

    /**
     * 获取Tab上的Button
     */
    public ToggleButton getTabButton(int position) {
        if (position < tabButtons.size()) {
            return tabButtons.get(position);
        }
        return null;
    }


    /**
     * 获取当前打开的TabButton
     */
    public ToggleButton getCurrentTabButton() {
        return currentTabButton;
    }


}

