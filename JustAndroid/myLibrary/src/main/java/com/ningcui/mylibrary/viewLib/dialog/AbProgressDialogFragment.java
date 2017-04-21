package com.ningcui.mylibrary.viewLib.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.KeyEvent;


/**
 * Copyright 李挺哲
 * 创建人：litingzhe
 * 邮箱：453971498@qq.com
 * Created by litingzhe on 2017/4/11 下午3:23.
 * Info 进度框fragment
 */
public class AbProgressDialogFragment extends DialogFragment {


	/**
	 * 创建一个新的AbProgressDialogFragment.
	 * @param indeterminateDrawable  进度图片
	 * @param message  消息提示
     * @return
     */
	public static AbProgressDialogFragment newInstance(int indeterminateDrawable, String message, boolean isCancelable) {
		AbProgressDialogFragment f = new AbProgressDialogFragment();
		Bundle args = new Bundle();
		args.putInt("indeterminateDrawable", indeterminateDrawable);
		args.putString("message", message);
        args.putBoolean("isCancelable", isCancelable);
		f.setArguments(args);

		return f;
	}
	

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		int indeterminateDrawable = getArguments().getInt("indeterminateDrawable");
		String message = getArguments().getString("message");
		boolean isCancelable=getArguments().getBoolean("isCancelable");
		ProgressDialog progressDialog = new ProgressDialog(getActivity(),AlertDialog.THEME_HOLO_LIGHT);
		if(indeterminateDrawable > 0){
			progressDialog.setIndeterminateDrawable(getActivity().getResources().getDrawable(indeterminateDrawable));
		}
		
		if(message != null){
			progressDialog.setMessage(message);
		}



		progressDialog.setCanceledOnTouchOutside(false);

        if (!isCancelable){
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
