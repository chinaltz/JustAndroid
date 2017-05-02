package com.litingzhe.justandroid.ui.activity.listView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.litingzhe.justandroid.R;
import com.litingzhe.justandroid.ui.model.City;
import com.litingzhe.justandroid.ui.model.CityListAdapter;
import com.ningcui.mylibrary.utiils.AbCharacterUtil;
import com.ningcui.mylibrary.viewLib.letterlist.AbLetterFilterListView;
import com.zhy.autolayout.AutoLayoutActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Copyright 李挺哲
 * 创建人：litingzhe
 * 邮箱：453971498@qq.com
 * Created by litingzhe on 2017/4/27 上午9:28.
 * 类描述：城市列表 Activity
 */


/**
 * Created by litingzhe on 2016/12/15.
 */

public class CityListViewActivity extends AutoLayoutActivity {


    @BindView(R.id.nav_back)
    LinearLayout navBack;
    @BindView(R.id.nav_title)
    TextView navTitle;
    @BindView(R.id.nav_right_icon)
    ImageView navRightIcon;
    @BindView(R.id.nav_right)
    LinearLayout navRight;
    @BindView(R.id.editText)
    EditText editText;
    @BindView(R.id.activity_city_search_input)
    LinearLayout activityCitySearchInput;
    @BindView(R.id.searchBox)
    LinearLayout searchBox;
    @BindView(R.id.listView)
    ListView listView;
    @BindView(R.id.letterView)
    AbLetterFilterListView letterView;
    private CityListAdapter mCityListAdapter;
    private ArrayList<City> list;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_citylist);
        ButterKnife.bind(this);
        View headerView = LayoutInflater.from(this).inflate(R.layout.view_city_header, null);
        navTitle.setText("城市列表");
        navBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        listView.addHeaderView(headerView);
        list = new ArrayList<City>();

        // 使用自定义的Adapter
        mCityListAdapter = new CityListAdapter(this, list);
        listView.setAdapter(mCityListAdapter);

        getData();

        editText.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                String str = editText.getText().toString().trim();
                int length = str.length();
                if (length > 0) {
                    filterData(str);
                } else {
                    //
                    getData();
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            public void afterTextChanged(Editable s) {

            }
        });


    }


    void getData() {

        AsyncTask<Void, Void, List> asyncTask = new AsyncTask<Void, Void, List>() {
            @Override
            protected List doInBackground(Void... params) {

                List<City> newList = null;
                try {
                    newList = filledData(getResources().getStringArray(R.array.list));
                } catch (Exception e) {
                }

                return newList;
            }

            @Override
            protected void onPostExecute(List newList) {
                list.clear();
                list.addAll((List<City>) newList);
                //通知Dialog
                mCityListAdapter.notifyDataSetChanged();

                super.onPostExecute(list);
            }
        };
        asyncTask.execute();

    }


    /**
     * 为ListView填充数据
     *
     * @param
     * @return
     */
    private List<City> filledData(String[] array) {
        List<City> newList = new ArrayList<City>();
        //实例化汉字转拼音类
//        AbCharacterUtil characterParser = AbCharacterUtil.g;

        for (int i = 0; i < array.length; i++) {
            City city = new City();
            city.setName(array[i]);
            //汉字转换成拼音
            String pinyin = AbCharacterUtil.getSelling(array[i]);
            String sortString = pinyin.substring(0, 1).toUpperCase();

            // 正则表达式，判断首字母是否是英文字母
            if (sortString.matches("[A-Z]")) {
                city.setFirstLetter(sortString.toUpperCase());
            } else {
                city.setFirstLetter("#");
            }
            newList.add(city);
        }
        Collections.sort(newList);
        return newList;

    }

    /**
     * 根据输入框中的值来过滤数据并更新ListView
     *
     * @param filterStr
     */
    private void filterData(String filterStr) {
        //实例化汉字转拼音类
//        AbCharacterParser characterParser = AbCharacterParser.getInstance();
        List<City> filterDateList = new ArrayList<City>();
        if (!TextUtils.isEmpty(filterStr)) {
            for (City city : list) {
                String name = city.getName();
                if (name.indexOf(filterStr) != -1 || AbCharacterUtil.getSelling(name).startsWith(filterStr)) {
                    filterDateList.add(city);
                }
            }
        }

        // 根据a-z进行排序
        Collections.sort(filterDateList);
        mCityListAdapter.updateListView(filterDateList);
    }

}
