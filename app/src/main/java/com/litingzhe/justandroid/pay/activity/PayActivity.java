package com.litingzhe.justandroid.pay.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.litingzhe.justandroid.R;
import com.litingzhe.justandroid.global.Constants;
import com.litingzhe.justandroid.pay.adapter.ChargeGridAdapter;
import com.litingzhe.justandroid.pay.model.ChargeModel;
import com.litingzhe.justandroid.pay.utils.AliPayUtils.AliPayUtils;
import com.litingzhe.justandroid.pay.utils.AliPayUtils.callback.AliPayCallBackV2;
import com.litingzhe.justandroid.pay.utils.AliPayUtils.v2.PayResultV2;
import com.litingzhe.justandroid.pay.utils.WChatPayUtils.WChatPayUtils;
import com.ningcui.mylibrary.app.base.AbBaseActivity;
import com.ningcui.mylibrary.utiils.AbLogUtil;
import com.ningcui.mylibrary.utiils.AbStrUtil;
import com.ningcui.mylibrary.utiils.AbToastUtil;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Copyright 李挺哲
 * 创建人：litingzhe
 * 邮箱：453971498@qq.com
 * Created by litingzhe on 2017/6/2 下午3:40.
 * 类描述：
 */


public class PayActivity extends AbBaseActivity implements AliPayCallBackV2 {


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
    @BindView(R.id.gridView)
    GridView gridView;
    @BindView(R.id.priceEditText)
    EditText priceEditText;
    @BindView(R.id.prcieConfirmButton)
    Button prcieConfirmButton;
    @BindView(R.id.aLiCheckBox)
    Button aLiCheckBox;
    @BindView(R.id.llPayTypeAliPay)
    LinearLayout llPayTypeAliPay;
    @BindView(R.id.weichatCheckBox)
    Button weichatCheckBox;
    @BindView(R.id.llPayTypeWeiChat)
    LinearLayout llPayTypeWeiChat;
    @BindView(R.id.payButton)
    Button payButton;



    private float chargePrice;


    private String payType;


    private static final int COMMITDATA = 2;

    public String billNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//
        setContentView(R.layout.activity_chargewallet);
        ButterKnife.bind(this);

        navTitle.setText("在线充值");
        chargePrice = 0;
        ChargeModel model1 = new ChargeModel(20, false, 0);
        ChargeModel model2 = new ChargeModel(50, false, 5);
        ChargeModel model3 = new ChargeModel(100, false, 10);
        ChargeModel model4 = new ChargeModel(200, false, 20);
        ChargeModel model5 = new ChargeModel(500, false, 50);
        ArrayList<ChargeModel> data = new ArrayList<>();
        data.add(model1);
        data.add(model2);
        data.add(model3);
        data.add(model4);
        data.add(model5);
        final ChargeGridAdapter adapter = new ChargeGridAdapter(data, mContext);

        gridView.setAdapter(adapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                chargePrice = adapter.getItem(i).getPrice();
                adapter.setSelectIndex(i);
                priceEditText.clearFocus();
                prcieConfirmButton.setEnabled(false);
                priceEditText.setText("");
                adapter.notifyDataSetChanged();


            }
        });


        prcieConfirmButton.setEnabled(false);
        priceEditText.clearFocus();//失去焦点
        priceEditText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                chargePrice = 0;
                adapter.setSelectIndex(-1);
                adapter.notifyDataSetChanged();
                priceEditText.requestFocus();
                prcieConfirmButton.setEnabled(true);
                return false;
            }
        });


        aLiCheckBox.setSelected(true);
        payType = "zfb";


    }

    @OnClick({R.id.nav_back,R.id.prcieConfirmButton, R.id.aLiCheckBox, R.id.llPayTypeAliPay, R.id.weichatCheckBox, R.id.llPayTypeWeiChat, R.id.payButton})
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.prcieConfirmButton:
                String s = priceEditText.getEditableText().toString();
                if (AbStrUtil.isEmpty(s)) {
                    AbToastUtil.showToast(mContext, "您未输入金额");
                    return;
                }

                if (!AbStrUtil.isPositiveNumber(s)) {
                    AbToastUtil.showToast(mContext, "请输入合法金额");
                    return;
                }

                chargePrice = Float.parseFloat(s);

                if (chargePrice < 0.01f) {

                    AbToastUtil.showToast(mContext, "最小充值金额为0.01元，请输入合理的充值金额");

                    return;
                }


                priceEditText.clearFocus();


                break;
            case R.id.nav_back:
                finish();
                break;

            case R.id.aLiCheckBox:

                aLiCheckBox.setSelected(true);
                weichatCheckBox.setSelected(false);

                break;
            case R.id.llPayTypeAliPay:
                aLiCheckBox.setSelected(true);
                weichatCheckBox.setSelected(false);

                break;
            case R.id.weichatCheckBox:
                aLiCheckBox.setSelected(false);
                weichatCheckBox.setSelected(true);

                break;
            case R.id.llPayTypeWeiChat:
                aLiCheckBox.setSelected(false);
                weichatCheckBox.setSelected(true);

                break;
            case R.id.payButton:


                String prices = priceEditText.getEditableText().toString();
                if (!AbStrUtil.isEmpty(prices)) {
                    if (!AbStrUtil.isPositiveNumber(prices)) {
                        AbToastUtil.showToast(mContext, "请输入合法金额");
                        return;
                    }
                    chargePrice = Float.parseFloat(prices);
                    priceEditText.clearFocus();
                }

                if (chargePrice < 0.01f) {

                    AbToastUtil.showToast(mContext, "最小充值金额为0.01元，请输入合理的充值金额");

                    return;
                }

                if (AbStrUtil.numLength(chargePrice) > 2) {
                    AbToastUtil.showToast(mContext, "小数点后不能大于2位");

                    return;
                }


                if (chargePrice <= 0) {

                    AbToastUtil.showToast(mContext, "没有选择充值金额");
                    return;
                }

                if (!aLiCheckBox.isSelected() && !weichatCheckBox.isSelected()) {

                    AbToastUtil.showToast(mContext, "没有选择充值方式");
                    return;

                }

                billNo = AbStrUtil.getTradeNo();

                AbLogUtil.i(Constants.MYTAG, "billNo :" + billNo + "length" + billNo.length());
                if (aLiCheckBox.isSelected()) {

//                    AbToastUtil.showToast(mContext, "将要使用 支付宝付款" + chargePrice + "元");
                    payType = "zfb";
                    AliPayUtils.getInstance(mContext).payV2(billNo, "东汇ETC钱包充值", "东汇ETC钱包充值", "" + chargePrice, Constants.CHARGEWALLET_AliPAY, billNo);
                    AliPayUtils.getInstance(mContext).setCallBackV2(PayActivity.this);

                } else {

                    if (!WChatPayUtils.getInstance(mContext).getMsgApi().isWXAppInstalled()) {
                        AbToastUtil.showToast(mContext, "您的手机没有安装微信，请安装微信后再使用微信付款");
                        return;
                    }

                    if (WChatPayUtils.getInstance(mContext).getMsgApi().isWXAppSupportAPI()) {
//                        WChatPayUtils.getInstance(mContext).toPayForWeiChat(billNo, Constants.CHARGEWALLET_WEICHAT, "1", "钱包支付");
                        payType = "wechat";
//                        commitChargeData(billNo);
                        float money=chargePrice*100;

                        WChatPayUtils.getInstance(mContext).toPayForWeiChat(billNo, Constants.CHARGEWALLET_WEICHAT, ""+(int)money, "东汇ETC钱包充值", "");

                        AbToastUtil.showToast(mContext, "将要使用 微信付款" + chargePrice + "元");
                    } else {
                        AbToastUtil.showToast(mContext, "微信版本过低无法支付，请升级你的微信");

                    }
                }

                break;
        }
    }


    @Override
    public void onAliPayResultCallBack(PayResultV2 result) {
        String resultStatus = result.getResultStatus();
        // 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
        if (TextUtils.equals(resultStatus, "9000")) {


            Toast.makeText(mContext, "支付成功",
                    Toast.LENGTH_SHORT).show();
//            Intent in = new Intent(ChargeWalletActivty.this, ChargeWalletSucceuss.class);
//            in.putExtra("billNo", billNo);
//            startActivity(in);

//            finish();

        } else {
            // 判断resultStatus 为非“9000”则代表可能支付失败
            // “8000”代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
            if (TextUtils.equals(resultStatus, "8000")) {
                Toast.makeText(mContext, "支付结果确认中",
                        Toast.LENGTH_SHORT).show();
            } else {
                // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
                Toast.makeText(mContext, "支付失败",
                        Toast.LENGTH_SHORT).show();


            }
        }

    }


}
