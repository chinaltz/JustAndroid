package com.litingzhe.justandroid.ui.listandGridView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import android.app.Service;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.litingzhe.justandroid.R;
import com.litingzhe.justandroid.global.MyApplication;
import com.litingzhe.justandroid.ui.listandGridView.adapter.ChatAdapter;
import com.ningcui.mylibrary.app.base.AbBaseActivity;
import com.ningcui.mylibrary.viewLib.refresh.AbPullToRefreshView;

public class ChatListActivity extends AbBaseActivity implements AbPullToRefreshView.OnFooterLoadListener, AbPullToRefreshView.OnHeaderRefreshListener {

    private MyApplication application;


    private ListView listView;

    private AbPullToRefreshView abPullToRefreshView;


    private EditText contentEt;

    private Button sendBtn, voiceSendBtn;

    private ImageButton addBtn, voiceBtn;

    private LinearLayout otherTypeLy;

    private ImageView sendPic, sendVedio, sendCameraPic;

    private String uploadUrl = null;

    private File cameraFile = null;

    private boolean isHide = false;

    private List<ApplicationInfo> mAppList;
    private ChatAdapter mAdapter;

    private LinearLayout navBack;

    private TextView titleText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        application = (MyApplication) getApplication();
        mAppList = getPackageManager().getInstalledApplications(0);
        initView();

    }

    public void initView() {

        abPullToRefreshView = (AbPullToRefreshView) findViewById(R.id.chat_PullRefreshView);
        navBack = (LinearLayout) findViewById(R.id.nav_back);
        navBack.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();

            }
        });

        titleText = (TextView) findViewById(R.id.nav_title);
        titleText.setText("简单的聊天布局");

        listView = (ListView) findViewById(R.id.chat_listView);
        mAdapter = new ChatAdapter(mContext);
        listView.setAdapter(mAdapter);
        listView.setItemsCanFocus(true);
        contentEt = (EditText) findViewById(R.id.tv_send_content);
        sendBtn = (Button) findViewById(R.id.sendBtn);
        addBtn = (ImageButton) findViewById(R.id.addBtn);
        voiceBtn = (ImageButton) findViewById(R.id.voiceBtn);
        voiceSendBtn = (Button) findViewById(R.id.voiceSendBtn);
        otherTypeLy = (LinearLayout) findViewById(R.id.chatAppPanel);
        sendPic = (ImageView) findViewById(R.id.send_picture);
        sendVedio = (ImageView) findViewById(R.id.send_vedio);
        sendCameraPic = (ImageView) findViewById(R.id.send_camera);
        // 添加不同的消息类型
        addBtn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (otherTypeLy.getVisibility() == View.VISIBLE) {
                    otherTypeLy.setVisibility(View.GONE);
                } else {
                    InputMethodManager mInputMethodManager = (InputMethodManager) getSystemService(Service.INPUT_METHOD_SERVICE);
                    mInputMethodManager.hideSoftInputFromWindow(contentEt.getWindowToken(), 0);
                    otherTypeLy.setVisibility(View.VISIBLE);

                }

            }
        });

        voiceSendBtn.setOnTouchListener(new OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: // 按下
                        voiceSendBtn.setText("松开取消");
                        break;
                    case MotionEvent.ACTION_UP:// 抬起
                        voiceSendBtn.setText("按住说话");

                        break;
                    default:
                        break;
                }
                return false;
            }
        });

        voiceBtn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                if (contentEt.getVisibility() == View.GONE) {
                    contentEt.setVisibility(View.VISIBLE);
                    voiceSendBtn.setVisibility(View.GONE);
                    isHide = false;
                    voiceBtn.setBackgroundResource(R.drawable.button_selector_chat_voice);
                    if (!isHide) {
                        InputMethodManager mInputMethodManager = (InputMethodManager) getSystemService(Service.INPUT_METHOD_SERVICE);
                        mInputMethodManager.hideSoftInputFromWindow(contentEt.getWindowToken(), 0);
                        isHide = true;
                    }
                } else {
                    contentEt.setVisibility(View.GONE);
                    voiceSendBtn.setVisibility(View.VISIBLE);
                    voiceBtn.setBackgroundResource(R.drawable.button_selector_chat_key);
                    InputMethodManager mInputMethodManager = (InputMethodManager) getSystemService(Service.INPUT_METHOD_SERVICE);
                    mInputMethodManager.hideSoftInputFromWindow(contentEt.getWindowToken(), 0);
                    isHide = true;

                }
                otherTypeLy.setVisibility(View.GONE);
            }
        });

        // 切换发送和添加按钮
        contentEt.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String str = contentEt.getText().toString().trim();
                int length = str.length();
                if (length > 0) {
                    sendBtn.setVisibility(View.VISIBLE);
                    addBtn.setVisibility(View.GONE);
                } else {
                    sendBtn.setVisibility(View.GONE);
                    addBtn.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        sendBtn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

            }
        });
        // 拍照
        sendCameraPic.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // 选择本地图片


            }
        });

        sendPic.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {


            }
        });

        sendVedio.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 0:
                    break;
                // 选择图片
                case 1:

                    break;
                // 图片确认
                case 4:


                    break;
                // 视频
                case 3:
                    break;
                default:
                    break;
            }
        }
    }


    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public void onHeaderRefresh(AbPullToRefreshView arg0) {
        abPullToRefreshView.onHeaderRefreshFinish();

    }

    @Override
    public void onFooterLoad(AbPullToRefreshView arg0) {
        abPullToRefreshView.onFooterLoadFinish();

    }


    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }


    class AppAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mAppList.size();
        }

        @Override
        public ApplicationInfo getItem(int position) {
            return mAppList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = View.inflate(getApplicationContext(),
                        R.layout.item_list_swipeleftapp, null);
                new AppAdapter.ViewHolder(convertView);
            }
            AppAdapter.ViewHolder holder = (AppAdapter.ViewHolder) convertView.getTag();
            ApplicationInfo item = getItem(position);
            holder.iv_icon.setImageDrawable(item.loadIcon(getPackageManager()));
            holder.tv_name.setText(item.loadLabel(getPackageManager()));
            holder.iv_icon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(ChatListActivity.this, "iv_icon_click", Toast.LENGTH_SHORT).show();
                }
            });
            holder.tv_name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(ChatListActivity.this, "iv_icon_click", Toast.LENGTH_SHORT).show();
                }
            });
            return convertView;
        }

        class ViewHolder {
            ImageView iv_icon;
            TextView tv_name;

            public ViewHolder(View view) {
                iv_icon = (ImageView) view.findViewById(R.id.iv_icon);
                tv_name = (TextView) view.findViewById(R.id.tv_name);
                view.setTag(this);
            }
        }


    }
}
