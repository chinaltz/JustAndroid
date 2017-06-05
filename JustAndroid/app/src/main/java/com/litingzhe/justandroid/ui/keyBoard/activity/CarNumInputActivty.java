package com.litingzhe.justandroid.ui.keyBoard.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.litingzhe.justandroid.R;
import com.ningcui.mylibrary.app.base.AbBaseActivity;
import com.ningcui.mylibrary.utiils.AbStrUtil;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Copyright 李挺哲
 * 创建人：litingzhe
 * 邮箱：453971498@qq.com
 * Created by litingzhe on 2017/6/5 上午9:53.
 * 类描述：车牌号录入 页面
 */


public class CarNumInputActivty extends AbBaseActivity implements View.OnFocusChangeListener, View.OnTouchListener {


    @BindView(R.id.nav_back)
    LinearLayout navBack;
    @BindView(R.id.nav_title)
    TextView navTitle;
    @BindView(R.id.nav_right_icon)
    ImageView navRightIcon;
    @BindView(R.id.nav_right_text)
    TextView navRightText;
    @BindView(R.id.nav_right)
    LinearLayout navRight;
    @BindView(R.id.edit_province)
    EditText editProvince;
    @BindView(R.id.edit_1)
    EditText edit1;
    @BindView(R.id.edit_2)
    EditText edit2;
    @BindView(R.id.edit_3)
    EditText edit3;
    @BindView(R.id.edit_4)
    EditText edit4;
    @BindView(R.id.edit_5)
    EditText edit5;
    @BindView(R.id.edit_6)
    EditText edit6;
    @BindView(R.id.addCarButton)
    Button addCarButton;
    @BindView(R.id.carList)
    ListView carList;
    @BindView(R.id.setDefaultButton)
    Button setDefaultButton;
    @BindView(R.id.deleteCarButton)
    RelativeLayout deleteCarButton;
    @BindView(R.id.keyboard_view)
    KeyboardView keyboardView;


    private EditText currentEdit;

    private int currentIndex;


    private EditText[] mArray;
    private Context mContext;
    private KeyboardView mKeyboardView;

    /**
     * 省份简称键盘
     */
    private Keyboard province_keyboard;
    /**
     * 数字与大写字母键盘
     */
    private Keyboard number_keyboar;

    private List carListData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_mycar);
        ButterKnife.bind(this);


        mContext = this;
        navTitle.setText("我的车辆");
        province_keyboard = new Keyboard(mContext, R.xml.province_abbreviation);
        number_keyboar = new Keyboard(mContext, R.xml.number_or_letters);
        mKeyboardView = (KeyboardView)
                findViewById(R.id.keyboard_view);
        mKeyboardView.setKeyboard(province_keyboard);
        mKeyboardView.setEnabled(true);
        mKeyboardView.setPreviewEnabled(false);
        mKeyboardView.setOnKeyboardActionListener(listener);
        editProvince = (EditText) findViewById(R.id.edit_province);
        edit1 = (EditText) findViewById(R.id.edit_1);
        edit2 = (EditText) findViewById(R.id.edit_2);
        edit3 = (EditText) findViewById(R.id.edit_3);
        edit4 = (EditText) findViewById(R.id.edit_4);
        edit5 = (EditText) findViewById(R.id.edit_5);
        edit6 = (EditText) findViewById(R.id.edit_6);
        mArray = new EditText[]{editProvince, edit1,
                edit2, edit3, edit4, edit5, edit6};

        editProvince.setOnFocusChangeListener(this);
        edit1.setOnFocusChangeListener(this);
        edit2.setOnFocusChangeListener(this);
        edit3.setOnFocusChangeListener(this);
        edit4.setOnFocusChangeListener(this);
        edit5.setOnFocusChangeListener(this);
        edit6.setOnFocusChangeListener(this);

        editProvince.setOnTouchListener(this);
        edit1.setOnTouchListener(this);
        edit2.setOnTouchListener(this);
        edit3.setOnTouchListener(this);
        edit4.setOnTouchListener(this);
        edit5.setOnTouchListener(this);
        edit6.setOnTouchListener(this);

//
//        edit2.setFocusable(true);
//        edit2.requestFocus();


        for (int i = 0; i < mArray.length; i++) {
            final int j = i;
            mArray[j].addTextChangedListener(new TextWatcher() {
                private CharSequence temp;
                private int sStart;
                private int sEnd;

                @Override
                public void onTextChanged(CharSequence s, int start,
                                          int before, int count) {
                    temp = s;
                }

                @Override
                public void beforeTextChanged(CharSequence s, int start,
                                              int count, int after) {


                }

                @Override
                public void afterTextChanged(Editable s) {
                    sStart = mArray[j].getSelectionStart();
                    sEnd = mArray[j].getSelectionEnd();

                    if (temp.length() == 1 && j < mArray.length - 1) {
                        mArray[j + 1].setFocusable(true);
                        mArray[j + 1].setFocusableInTouchMode(true);
                        mArray[j + 1].requestFocus();

                    }
                    if (temp.length() > 1) {

                        s.delete(0, 1);
                        int tSelection = sStart;
                        mArray[j].setText(s);
                        mArray[j].setSelection(tSelection);
                        mArray[j].setFocusable(true);

                    }
                }
            });
        }


//

//
//        if (!AbStrUtil.isEmpty(app.city.getCityCode())) {
//            getCarNumByCityCode(app.city.getCityCode());
//        } else {

            editProvince.setFocusable(true);
            editProvince.requestFocus();
//        }


//        addCar();




    }




    private KeyboardView.OnKeyboardActionListener listener = new KeyboardView.OnKeyboardActionListener() {
        @Override
        public void swipeUp() {
        }

        @Override
        public void swipeRight() {
        }

        @Override
        public void swipeLeft() {
        }

        @Override
        public void swipeDown() {
        }

        @Override
        public void onText(CharSequence text) {
        }

        @Override
        public void onRelease(int primaryCode) {
        }

        @Override
        public void onPress(int primaryCode) {
        }

        @Override
        public void onKey(int primaryCode, int[] keyCodes) {
            Editable editable = currentEdit.getText();
            int start = currentEdit.getSelectionStart();
            if (primaryCode == -1) {//
                //隐藏键盘
                if (isShow()) {
                    hideKeyboard();
                }


            } else if (primaryCode == -3) {// 回退

                Log.i("Tag1111", "onKey currentIndex= " + currentIndex);
                if (editable != null) {
                    //没有输入内容时软键盘重置为省份简称软键盘
                    editable.clear();

                    if (currentIndex > 0) {
                        currentIndex = currentIndex - 1;
//                        Log.i("Tag1111", "onKeyChange currentIndex= " + currentIndex);
                        mArray[currentIndex].setFocusable(true);
                        mArray[currentIndex].requestFocus();

                    }


                }

            } else {
                editable.insert(start, Character.toString((char) primaryCode));
                if (currentIndex == 6 && mArray[6].getText().length() > 0) {


                    hideKeyboard();
                }


            }
        }
    };
    /**
     * 指定切换软键盘 isnumber false表示要切换为省份简称软键盘 true表示要切换为数字软键盘
     */

    public void changeKeyboard(boolean isnumber) {
        if (isnumber) {
            mKeyboardView.setKeyboard(number_keyboar);
        } else {
            mKeyboardView.setKeyboard(province_keyboard);
        }
    }


    /**
     * 软键盘展示
     */
    public void showKeyboard() {
        int visibility = mKeyboardView.getVisibility();
        if (visibility == View.GONE || visibility == View.INVISIBLE) {
            mKeyboardView.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 软键盘隐藏
     */
    public void hideKeyboard() {
        int visibility = mKeyboardView.getVisibility();
        if (visibility == View.VISIBLE) {
            mKeyboardView.setVisibility(View.INVISIBLE);
        }
    }

    /**
     * 禁掉系统软键盘
     */
    public void hideSoftInputMethod() {

        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        int currentVersion = Build.VERSION.SDK_INT;
        String methodName = null;
        if (currentVersion >= 16) {
            // 4.2
            methodName = "setShowSoftInputOnFocus";
        } else if (currentVersion >= 14) {
            // 4.0
            methodName = "setSoftInputShownOnFocus";
        }
        if (methodName == null) {
            currentEdit.setInputType(InputType.TYPE_NULL);
        } else {
            Class<EditText> cls = EditText.class;
            Method setShowSoftInputOnFocus;
            try {
                setShowSoftInputOnFocus = cls.getMethod(methodName,
                        boolean.class);
                setShowSoftInputOnFocus.setAccessible(true);
                setShowSoftInputOnFocus.invoke(currentEdit, false);
            } catch (NoSuchMethodException e) {
                currentEdit.setInputType(InputType.TYPE_NULL);
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 软键盘展示状态
     */
    public boolean isShow() {
        return mKeyboardView.getVisibility() == View.VISIBLE;
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (isShow()) {
                hideKeyboard();
            } else {
                finish();
            }
        }
        return false;
    }


    @Override
    public void onFocusChange(View v, boolean hasFocus) {

        if (hasFocus) {

            currentEdit = (EditText) v;

            for (int i = 0; i < mArray.length; i++) {

                EditText temp = mArray[i];
                if (temp == (EditText) v) {
                    currentIndex = i;
                }
            }
//            Toast.makeText(mContext, "currentIndex" + currentIndex, Toast.LENGTH_SHORT).show();
//            Log.i("Tag1111", "onFocusChange currentIndex= " + currentIndex);
            if (currentIndex == 0) {
                changeKeyboard(false);
            } else {
                changeKeyboard(true);
            }
            hideSoftInputMethod();

            if (!isShow()) {
                showKeyboard();
            }


        }


    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {


        return super.onTouchEvent(event);
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {

        if (!isShow()) {
            showKeyboard();

        }
        return false;

    }



}
