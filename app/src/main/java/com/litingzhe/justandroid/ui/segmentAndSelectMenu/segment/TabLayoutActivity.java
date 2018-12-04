package com.litingzhe.justandroid.ui.segmentAndSelectMenu.segment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.litingzhe.justandroid.R;
import com.ningcui.mylibrary.app.base.AbBaseActivity;
import com.zhy.autolayout.utils.AutoUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Copyright 李挺哲
 * 创建人：litingzhe
 * 邮箱：453971498@qq.com
 * Created by litingzhe on 2017/5/15 下午2:09.
 * 类描述：TabLayout 简单使用
 */


public class TabLayoutActivity extends AbBaseActivity {

    @BindView(R.id.nav_back)
    LinearLayout navBack;
    @BindView(R.id.nav_title)
    TextView navTitle;
    @BindView(R.id.nav_right_icon)
    ImageView navRightIcon;
    @BindView(R.id.nav_right)
    LinearLayout navRight;
    @BindView(R.id.topTabLayout)
    TabLayout topTabLayout;
    @BindView(R.id.bottomTabLayout)
    TabLayout bottomTabLayout;


    private String[] titleList = null;
    private int[] icons = new int[]{
            R.mipmap.mainpage_home_nor_ic,
            R.mipmap.mainpage_topic_nor_ic,
            R.mipmap.mainpage_category_nor_ic,
            R.mipmap.mainpage_shopping_cart_nor_ic,
            R.mipmap.mainpage_person_nor_ic
    };

    private int[] icons_press = new int[]{
            R.mipmap.mainpage_home_pressed_ic,
            R.mipmap.mainpage_topic_pressed_ic,
            R.mipmap.mainpage_category_pressed_ic,
            R.mipmap.mainpage_shopping_cart_pressed_ic,
            R.mipmap.mainpage_person_pressed_ic
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tablayout);
        ButterKnife.bind(this);
        navBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        navTitle.setText("desgin中TabLayout");


        topTabLayout.addTab(topTabLayout.newTab().setText("Tab 1"));

        topTabLayout.addTab(topTabLayout.newTab().setText("Tab 2"));

        topTabLayout.addTab(topTabLayout.newTab().setText("Tab 3"));

        topTabLayout.addTab(topTabLayout.newTab().setText("Tab 4"));

        topTabLayout.addTab(topTabLayout.newTab().setText("Tab 5"));

        topTabLayout.addTab(topTabLayout.newTab().setText("Tab 6"));

        topTabLayout.addTab(topTabLayout.newTab().setText("Tab 7"));

        topTabLayout.addTab(topTabLayout.newTab().setText("Tab 8"));


        bottomTabLayout.setTabMode(TabLayout.MODE_FIXED);

        titleList = new String[]{
                "UI", "Net&DB", "设计模式", "购物车", "其他"
        };


        //为TabLayout添加tab名称
        for (int i = 0; i < titleList.length; i++) {
            TabLayout.Tab tab = bottomTabLayout.newTab();
            tab.setCustomView(getTabView(i));
            bottomTabLayout.addTab(tab);
        }
        bottomTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                    View view = tab.getCustomView();
                    ImageView img = (ImageView) view.findViewById(R.id.tab_icon);
                    TextView title = (TextView) view.findViewById(R.id.tab_title);

                        img.setImageResource(icons_press[tab.getPosition()]);
                        title.setTextColor(getResources().getColor(R.color.mainColor));

                }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                View view = tab.getCustomView();
                ImageView img = (ImageView) view.findViewById(R.id.tab_icon);
                TextView title = (TextView) view.findViewById(R.id.tab_title);
                img.setImageResource(icons[tab.getPosition()]);
                title.setTextColor(getResources().getColor(R.color.gray));

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }


    /**
     * 添加getTabView的方法，来进行自定义Tab的布局View
     *
     * @param position
     * @return
     */
    public View getTabView(int position) {
        LayoutInflater mInflater = LayoutInflater.from(this);

       View view = mInflater.inflate(R.layout.item_bottom_tab, null);
        AutoUtils.auto(view);

        TextView tv = (TextView) view.findViewById(R.id.tab_title);
        tv.setText(titleList[position]);
        ImageView img = (ImageView) view.findViewById(R.id.tab_icon);
        if (position == 0) {
            tv.setTextColor(getResources().getColor(R.color.mainColor));
            img.setImageResource(icons_press[position]);
        } else {
            tv.setTextColor(getResources().getColor(R.color.gray));
            img.setImageResource(icons[position]);
        }
        return view;
    }
}
