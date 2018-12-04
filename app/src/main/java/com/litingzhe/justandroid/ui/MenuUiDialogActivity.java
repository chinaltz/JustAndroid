package com.litingzhe.justandroid.ui;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.litingzhe.justandroid.R;
import com.ningcui.mylibrary.app.base.AbBaseActivity;
import com.ningcui.mylibrary.utiils.AbDialogUtil;
import com.ningcui.mylibrary.utiils.AbStrUtil;
import com.ningcui.mylibrary.utiils.AbToastUtil;
import com.ningcui.mylibrary.viewLib.wheel.AbWheelUtil;
import com.ningcui.mylibrary.viewLib.wheel.AbWheelView;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Copyright 李挺哲
 * 创建人：litingzhe
 * 邮箱：453971498@qq.com
 * Created by litingzhe on 2017/4/17 上午9:48.
 * 类描述：常见的对话框
 */


public class MenuUiDialogActivity extends AbBaseActivity {

    @BindView(R.id.nav_back)
    LinearLayout navBack;
    @BindView(R.id.nav_title)
    TextView navTitle;
    @BindView(R.id.Loading)
    Button Loading;
    @BindView(R.id.ActionSheet)
    Button ActionSheet;
    @BindView(R.id.pickView)
    Button pickView;

    @BindView(R.id.designDialog)
    Button designDialog;
    @BindView(R.id.contentView)
    LinearLayout contentView;
    @BindView(R.id.nav_right_icon)
    ImageView navRightIcon;
    @BindView(R.id.nav_right)
    LinearLayout navRight;
    @BindView(R.id.progressDialog)
    Button progressDialog;

    private ProgressDialog progressHortionalDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_uidiaglog);
        ButterKnife.bind(this);

        navTitle.setText("对话框集合");
        navBack.setVisibility(View.VISIBLE);
        initHortionalProgressBar("横向提示框", "进度框显示中。。。");


        navBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                finish();
            }
        });


    }


    @OnClick({ R.id.designDialog, R.id.pickView,
            R.id.Loading, R.id.ActionSheet, R.id.progressDialog})
    public void onViewClicked(View view) {
        switch (view.getId()) {

            case R.id.Loading:
                //第一个按钮
                AbDialogUtil.showProgressDialog(mContext, R.drawable.progress_circle,
                        "Loading正在显示中。。。", true);

                break;


            case R.id.progressDialog:

                myAsyncTask asyncTask = new myAsyncTask(progressHortionalDialog);
                asyncTask.execute();

                break;


            case R.id.ActionSheet:

                showActionSheet();
                break;


            case R.id.pickView:

                initMDHMWheel();
                break;


            case R.id.designDialog:
                showDesignDialog();


                break;

        }
    }


    private void showActionSheet() {


        View avatarView = View.inflate(mContext, R.layout.view_choose_avatar, null);
        Button albumButton = (Button) avatarView.findViewById(R.id.choose_album);
        Button camButton = (Button) avatarView.findViewById(R.id.choose_cam);
        Button cancelButton = (Button) avatarView.findViewById(R.id.choose_cancel);

        albumButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AbDialogUtil.removeDialog(mContext);


            }
        });
        camButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AbDialogUtil.removeDialog(mContext);


            }
        });
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AbDialogUtil.removeDialog(mContext);


            }
        });


        AbDialogUtil.showDialog(avatarView, Gravity.BOTTOM);
    }

    private void initHortionalProgressBar(String title, String message) {

        progressHortionalDialog = new ProgressDialog(mContext, android.support.v7.app.AlertDialog.BUTTON_NEUTRAL);
        if (title != null) {
            progressHortionalDialog.setTitle(title);
        }

        if (message != null) {
            progressHortionalDialog.setMessage(message);
        }
        progressHortionalDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressHortionalDialog.setIndeterminate(false);

        progressHortionalDialog.setMax(100);

        progressHortionalDialog.setCanceledOnTouchOutside(false);

        progressHortionalDialog.setCancelable(true);

        progressHortionalDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {

                dialog.dismiss();


            }
        });


    }


    /**
     * 初始化 时间选择轮子
     */
    public void initMDHMWheel() {

        final View view = View.inflate(mContext, R.layout.view_choose_three, null);

        final AbWheelView mWheelViewMD = (AbWheelView) view.findViewById(R.id.wheelView1);
        final AbWheelView mWheelViewHH = (AbWheelView) view.findViewById(R.id.wheelView2);
        final AbWheelView mWheelViewMM = (AbWheelView) view.findViewById(R.id.wheelView3);

        Button okBtn = (Button) view.findViewById(R.id.okBtn);
        Button cancelBtn = (Button) view.findViewById(R.id.cancelBtn);
        //mWheelViewMD.setCenterSelectDrawable(this.getResources().getDrawable(R.drawable.wheel_select));
        //mWheelViewMM.setCenterSelectDrawable(this.getResources().getDrawable(R.drawable.wheel_select));
        //mWheelViewHH.setCenterSelectDrawable(this.getResources().getDrawable(R.drawable.wheel_select));
        AbWheelUtil.initWheelPickerMDHM(mWheelViewMD, mWheelViewMM, mWheelViewHH, 2017, 8, 29, 17, 24);

        okBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                AbDialogUtil.removeDialog(view.getContext());
                int index1 = mWheelViewMD.getCurrentItem();
                int index2 = mWheelViewHH.getCurrentItem();
                int index3 = mWheelViewMM.getCurrentItem();

                String dmStr = AbWheelUtil.MDHMList.get(index1);
                Calendar calendar = Calendar.getInstance();
                int second = calendar.get(Calendar.SECOND);
                String val = AbStrUtil.dateTimeFormat(dmStr + " " + index3 + ":" + index2 + ":" + second);
                AbToastUtil.showToast(mContext, "" + val);

            }

        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                AbDialogUtil.removeDialog(view.getContext());
            }

        });

        AbDialogUtil.showDialog(view, Gravity.BOTTOM);
    }


    class myAsyncTask extends AsyncTask<Void, Integer, Boolean> {
        private ProgressDialog dialog = null;
        private int progress = 0;

        public myAsyncTask(ProgressDialog dialog) {
            this.dialog = dialog;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.show();
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            while (progress <= 100) {
                publishProgress(progress);
                try { /*每隔100ms更新一次进度*/
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                progress++;
            } /*模拟下载完成后，3秒后取消 ProgressDialog*/
//            try {
//                Thread.sleep(3000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
            return true;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            dialog.setProgress(values[0]);
            dialog.setMessage(progress + "%");
            if (progress == 100)
                dialog.setTitle("下载完成");
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            if (aBoolean)
                dialog.dismiss();
        }
    }


    private void showDesignDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("温馨提醒");
        builder.setMessage("您已经打开 android.support.v7.app.AlertDialog");
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();

            }
        });
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                dialogInterface.dismiss();


            }
        });
        builder.show();
    }
}
