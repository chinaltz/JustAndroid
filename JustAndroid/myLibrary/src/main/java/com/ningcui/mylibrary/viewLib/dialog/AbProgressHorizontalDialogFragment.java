package com.ningcui.mylibrary.viewLib.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ningcui.mylibrary.R;
import com.ningcui.mylibrary.utiils.AbDialogUtil;
import com.ningcui.mylibrary.utiils.AbStrUtil;
import com.ningcui.mylibrary.viewLib.wheel.AbWheelView;

import java.util.Calendar;


/**
 * Copyright 李挺哲
 * 创建人：litingzhe
 * 邮箱：453971498@qq.com
 * Created by litingzhe on 2017/4/11 下午3:23.
 * Info 进度框fragment
 */
public class AbProgressHorizontalDialogFragment extends DialogFragment {


    /**
     * 创建一个新的AbProgressDialogFragment.
     *
     * @param title        标题
     * @param message      消息提示
     * @param isCancelable 是否可取消
     * @return
     */
    public static AbProgressHorizontalDialogFragment newInstance(String title, String message, boolean isCancelable) {
        AbProgressHorizontalDialogFragment f = new AbProgressHorizontalDialogFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        args.putString("message", message);
        args.putBoolean("isCancelable", isCancelable);
        f.setArguments(args);

        return f;
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String title = getArguments().getString("title");
        String message = getArguments().getString("message");
        boolean isCancelable = getArguments().getBoolean("isCancelable");
        ProgressDialog progressDialog = new ProgressDialog(getActivity(), android.support.v7.app.AlertDialog.BUTTON_NEUTRAL);
        if (title != null) {
            progressDialog.setTitle(title);
        }

        if (message != null) {
            progressDialog.setMessage(message);
        }
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setIndeterminate(false);

        progressDialog.setMax(100);
        progressDialog.setProgress(50);

        progressDialog.setCanceledOnTouchOutside(false);

        if (!isCancelable) {
            progressDialog.setCancelable(false);
            progressDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
                @Override
                public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {

                    if (keyCode == KeyEvent.KEYCODE_BACK) {


                        return true;
                    }
                    return false;
                }
            });

        }

        return progressDialog;
    }




}
