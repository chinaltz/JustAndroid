package com.litingzhe.justandroid.someOther.weex.activity;

import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.litingzhe.justandroid.R;
import com.taobao.weex.WXRenderErrorCode;
import com.taobao.weex.WXSDKInstance;
import com.taobao.weex.utils.WXFileUtils;
import com.taobao.weex.utils.WXSoInstallMgrSdk;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WeexActivity extends AbstractWeexActivity {


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
    @BindView(R.id.index_container)
    FrameLayout indexContainer;
    @BindView(R.id.index_progressBar)
    ProgressBar indexProgressBar;
    @BindView(R.id.index_tip)
    TextView indexTip;
    private ProgressBar mProgressBar;
    private TextView mTipView;

    private LinearLayout settingLin;

    private Context mContext;
//    private TextView mTitleView;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weexnews);
        ButterKnife.bind(this);
        setContainer((ViewGroup) findViewById(R.id.index_container));
        mContext = this;

        getWindow().setFormat(PixelFormat.TRANSLUCENT);

        mProgressBar = (ProgressBar) findViewById(R.id.index_progressBar);
        mTipView = (TextView) findViewById(R.id.index_tip);
        mProgressBar.setVisibility(View.VISIBLE);
        mTipView.setVisibility(View.VISIBLE);

        navBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();

            }
        });

        navTitle.setText("知乎文章");

        if (!WXSoInstallMgrSdk.isCPUSupport()) {
            mProgressBar.setVisibility(View.INVISIBLE);
//            mTipView.setText(R.string.cpu_not_support_tip);
            return;
        }

//    if (TextUtils.equals(sCurrentIp, DEFAULT_IP)) {
//        renderPage(WXFileUtils.loadAsset("index.js", this), "");
//    } else {

//    }


    }


    @Override
    public void onResume() {
        super.onResume();

        Map<String, Object> options = new HashMap<>();
        options.put(WXSDKInstance.BUNDLE_URL, "http://192.168.64.189:8080/dist/app.weex.js");
        options.put("token", "12332aaaaa");
        renderPage(WXFileUtils.loadAsset("app.weex.js", getApplicationContext()),"");
//        renderPageByURL("http://192.168.64.189:8080/dist/app.weex.js", options);
    }

    @Override
    public void onRenderSuccess(WXSDKInstance wxsdkInstance, int i, int i1) {
        super.onRenderSuccess(wxsdkInstance, i, i1);
        mProgressBar.setVisibility(View.GONE);
        mTipView.setVisibility(View.GONE);
    }

    @Override
    public void onException(WXSDKInstance wxsdkInstance, String s, String s1) {
        super.onException(wxsdkInstance, s, s1);
        mProgressBar.setVisibility(View.GONE);
        mTipView.setVisibility(View.VISIBLE);
        if (TextUtils.equals(s, WXRenderErrorCode.WX_NETWORK_ERROR)) {
//            mTipView.setText(R.string.index_tip);
        } else {
            mTipView.setText("render error:" + s1);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


}
