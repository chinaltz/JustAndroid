package com.ningcui.mylibrary.viewLib;

import android.app.DialogFragment;
import android.content.DialogInterface;
import android.view.View;
import android.view.animation.Animation;

import com.ningcui.mylibrary.utiils.AbAnimationUtil;
import com.ningcui.mylibrary.utiils.AbDialogUtil;


/**
 * Copyright 李挺哲
 * 创建人：litingzhe
 * 邮箱：453971498@qq.com
 * Created by litingzhe on 2017/4/11 下午3:23.
 * 描述：弹出框的父类
 */
public class AbDialogFragment extends DialogFragment {

	private View mIndeterminateView = null;
	public String mMessage;
	private DialogInterface.OnCancelListener mOnCancelListener = null;
	private DialogInterface.OnDismissListener mOnDismissListener = null;
	private AbDialogOnLoadListener mAbDialogOnLoadListener = null;

	public AbDialogFragment() {
		super();
	}
	

	@Override
	public void onCancel(DialogInterface dialog) {
		// 用户中断
		if (mOnCancelListener != null) {
			mOnCancelListener.onCancel(dialog);
		}

		super.onCancel(dialog);
	}

	@Override
	public void onDismiss(DialogInterface dialog) {
		// 用户隐藏
		if (mOnDismissListener != null) {
			mOnDismissListener.onDismiss(dialog);
		}
		super.onDismiss(dialog);
	}

	public DialogInterface.OnCancelListener getOnCancelListener() {
		return mOnCancelListener;
	}

	public void setOnCancelListener(
			DialogInterface.OnCancelListener onCancelListener) {
		this.mOnCancelListener = onCancelListener;
	}

	public DialogInterface.OnDismissListener getOnDismissListener() {
		return mOnDismissListener;
	}

	public void setOnDismissListener(
			DialogInterface.OnDismissListener onDismissListener) {
		this.mOnDismissListener = onDismissListener;
	}

	
	/**
	 * 加载调用
	 */
	public void load(View v){
		if(mAbDialogOnLoadListener!=null){
			mAbDialogOnLoadListener.onLoad();
		}
		mIndeterminateView = v;
		AbAnimationUtil.playRotateAnimation(mIndeterminateView, 300, Animation.INFINITE,
				Animation.RESTART);
	}

	/**
	 * 加载成功调用
	 */
	public void loadFinish(){
		//停止动画
		loadStop();
		AbDialogUtil.removeDialog(this.getActivity());
	}
	
	/**
	 * 加载结束
	 */
	public void loadStop(){
		//停止动画
		mIndeterminateView.postDelayed(new Runnable(){

			@Override
			public void run() {
				mIndeterminateView.clearAnimation();
			}
			
		}, 200);
		
	}
	
	public AbDialogOnLoadListener getAbDialogOnLoadListener() {
		return mAbDialogOnLoadListener;
	}

	public void setAbDialogOnLoadListener(
			AbDialogOnLoadListener abDialogOnLoadListener) {
		this.mAbDialogOnLoadListener = abDialogOnLoadListener;
	}
	
	public String getMessage() {
		return mMessage;
	}


	public void setMessage(String mMessage) {
		this.mMessage = mMessage;
	}


	/**
	 * 加载事件的接口.
	 */
	public interface AbDialogOnLoadListener {

		/**
		 * 加载
		 */
		public void onLoad();
		
	}

}
