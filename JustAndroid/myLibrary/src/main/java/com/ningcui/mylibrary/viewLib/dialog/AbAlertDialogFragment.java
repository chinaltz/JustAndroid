package com.ningcui.mylibrary.viewLib.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.View;

/**
 * Copyright 李挺哲
 * 创建人：litingzhe
 * 邮箱：453971498@qq.com
 * Created by litingzhe on 2017/4/11 下午3:23.
 * Info 弹出框fragment
 */
public class AbAlertDialogFragment extends DialogFragment {

	private View contentView;

	/**
	 * 构造函数.
	 */
	public AbAlertDialogFragment() {
	}


	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(),AlertDialog.THEME_HOLO_LIGHT);
		if(contentView!=null){
			builder.setView(contentView);
		}
		
	    return builder.create();
	}

	public View getContentView() {
		return contentView;
	}

	public void setContentView(View contentView) {
		this.contentView = contentView;
	}

}
