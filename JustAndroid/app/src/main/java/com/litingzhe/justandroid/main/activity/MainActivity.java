package com.litingzhe.justandroid.main.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.litingzhe.justandroid.R;
import com.litingzhe.justandroid.main.adapter.MainFragmentAdapter;
import com.litingzhe.justandroid.main.fragment.NetAndDBDataFragment;
import com.litingzhe.justandroid.main.fragment.DesignPatternFragment;
import com.litingzhe.justandroid.main.fragment.HomeBaseUIFragment;
import com.litingzhe.justandroid.main.fragment.OtherUtilsFragment;
import com.litingzhe.justandroid.main.fragment.ShopFragment;
import com.ningcui.mylibrary.app.base.AbBaseActivity;
import com.ningcui.mylibrary.utiils.AbToastUtil;
import com.ningcui.mylibrary.viewLib.sample.AbViewPager;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Copyright 李挺哲
 * 创建人：litingzhe
 * 邮箱：453971498@qq.com
 * Created by litingzhe on 2017/4/11 下午3:22.
 * 类描述：MainActivity 主页
 */


public class MainActivity extends AbBaseActivity {

    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.tab_line)
    View tabLine;
    @BindView(R.id.viewPager)
    AbViewPager viewPager;
    @BindView(R.id.mainView)
    RelativeLayout mainView;

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

    private HomeBaseUIFragment homeBaseUIFragment;
    private NetAndDBDataFragment netAndDbDataFragment;
    private DesignPatternFragment designPatternFragment;
    private ShopFragment shopFragment;
    private OtherUtilsFragment otherUtilsFragment;
    private ArrayList<Fragment> fragmentList;
    private boolean isExit=false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        InitView();

    }

    //初始化View
    private void InitView() {

        viewPager.setOffscreenPageLimit(4);

        fragmentList = new ArrayList<Fragment>();

        homeBaseUIFragment = new HomeBaseUIFragment();

        netAndDbDataFragment = new NetAndDBDataFragment();

        designPatternFragment = new DesignPatternFragment();

        shopFragment = new ShopFragment();

        otherUtilsFragment = new OtherUtilsFragment();


        fragmentList.add(homeBaseUIFragment);
        fragmentList.add(netAndDbDataFragment);
        fragmentList.add(designPatternFragment);
        fragmentList.add(shopFragment);
        fragmentList.add(otherUtilsFragment);


        tabLayout.setTabMode(TabLayout.MODE_FIXED);

        titleList = new String[]{
                "UI", "Net&DB", "设计模式", "购物车", "其他"
        };

        MainFragmentAdapter adapter = new MainFragmentAdapter(getSupportFragmentManager(), titleList, fragmentList);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        //设置ViewPager 是否可以滑动
        viewPager.setPagingEnabled(true);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {


                for (int i = 0; i < titleList.length; i++) {
                    TabLayout.Tab tab = tabLayout.getTabAt(i);
                    View view = tab.getCustomView();
                    ImageView img = (ImageView) view.findViewById(R.id.tab_icon);
                    TextView title = (TextView) view.findViewById(R.id.tab_title);

                    if (position == i) {
                        img.setImageResource(icons_press[i]);
                        title.setTextColor(getResources().getColor(R.color.mainColor));
                    } else {
                        img.setImageResource(icons[i]);
                        title.setTextColor(getResources().getColor(R.color.gray));
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        //为TabLayout添加tab名称
        for (int i = 0; i < titleList.length; i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            tab.setCustomView(getTabView(i));
        }

        viewPager.setCurrentItem(0);
    }


    /**
     * 添加getTabView的方法，来进行自定义Tab的布局View
     *
     * @param position
     * @return
     */
    public View getTabView(int position) {
        LayoutInflater mInflater = LayoutInflater.from(this);
        View view = null;

        view = mInflater.inflate(R.layout.item_bottom_tab, null);
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


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {


        if (keyCode == KeyEvent.KEYCODE_BACK) {


            if (isExit == false) {
                isExit = true;
                AbToastUtil.showToast(mContext,"再按一次退出程序");
                new Handler().postDelayed(new Runnable(){

                    @Override
                    public void run() {
                        isExit = false;
                    }

                }, 2000);

            } else {

                finish();

            }


            return false;
        }
        return super.onKeyDown(keyCode, event);
    }
}
