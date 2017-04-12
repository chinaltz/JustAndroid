
package com.ningcui.mylibrary.utiils;

import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.view.Gravity;
import android.view.View;

import com.ningcui.mylibrary.viewLib.dialog.AbAlertDialogFragment;
import com.ningcui.mylibrary.viewLib.dialog.AbProgressDialogFragment;
import com.ningcui.mylibrary.viewLib.dialog.AbSampleDialogFragment;


/**
 * Copyright 李挺哲
 * 创建人：litingzhe
 * 邮箱：453971498@qq.com
 * Created by litingzhe on 2017/4/11 下午3:23.
 * Info Dialog工具类
 */

public class AbDialogUtil {
	
	/** dialog 标记*/
	public static String dialogTag = "dialog";
	
	public static int ThemeHoloLightDialog = android.R.style.Theme_Holo_Light_Dialog;
	
	public static int ThemeLightPanel = android.R.style.Theme_Light_Panel;
	
	/**
	 * 显示一个全屏对话框.
	 * @param view
	 * @return
	 */
	public static AbSampleDialogFragment showFullScreenDialog(View view) {
		FragmentActivity activity = (FragmentActivity)view.getContext();
        // Create and show the dialog.
        AbSampleDialogFragment newFragment = AbSampleDialogFragment.newInstance(DialogFragment.STYLE_NORMAL,android.R.style.Theme_Black_NoTitleBar_Fullscreen,Gravity.CENTER);
        newFragment.setContentView(view);
        FragmentTransaction ft = activity.getFragmentManager().beginTransaction();
        // 指定一个系统转场动画 
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);  
        newFragment.show(ft, dialogTag);
        return newFragment;
    }
	
	/**
	 * 显示一个居中的对话框.
	 * @param view
	 */
	public static AbSampleDialogFragment showDialog(View view) {
		return showDialog(view,Gravity.CENTER);
	}

	/**
	 * 显示一个居中的面板.
	 * @param view
	 */
	public static AbSampleDialogFragment showPanel(View view) {
		return showPanel(view,Gravity.CENTER);
	}


	/**
	 *
	 * 显示一个指定位置对话框.
	 * @param view
	 * @param gravity 位置
	 * @return
	 */
	public static AbSampleDialogFragment showDialog(View view,int gravity) {
		return showDialogOrPanel(view,gravity,ThemeHoloLightDialog);
    }

	/**
	 *
	 * 显示一个指定位置的Panel.
	 * @param view
	 * @param gravity 位置
	 * @return
	 */
	public static AbSampleDialogFragment showPanel(View view,int gravity) {
		return showDialogOrPanel(view,gravity,ThemeLightPanel);
	}
	
	/**
	 * 
	 * 自定义的对话框面板.
	 * @param view    View
	 * @param gravity 位置
	 * @param style   样式 ThemeHoloLightDialog  ThemeLightPanel
	 * @return
	 */
	private static AbSampleDialogFragment showDialogOrPanel(View view,int gravity,int style) {
		FragmentActivity activity = (FragmentActivity)view.getContext();
        // Create and show the dialog.
        AbSampleDialogFragment newFragment = AbSampleDialogFragment.newInstance(DialogFragment.STYLE_NO_TITLE,style,gravity);
        newFragment.setContentView(view);
        
        FragmentTransaction ft = activity.getFragmentManager().beginTransaction();
        // 指定一个系统转场动画   
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);  
        newFragment.show(ft, dialogTag);
        
        return newFragment;
    }

	/**
	 * 显示一个普通对话框.
	 * @param view 对话框View
	 */
	public static AbAlertDialogFragment showAlertDialog(View view) {
		FragmentActivity activity = (FragmentActivity)view.getContext();
		AbAlertDialogFragment alertDialogFragment = new AbAlertDialogFragment();
		alertDialogFragment.setContentView(view);
		FragmentTransaction ft = activity.getFragmentManager().beginTransaction();
		// 指定一个系统转场动画
		ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
		alertDialogFragment.show(ft, dialogTag);
		return alertDialogFragment;
	}
	
	/**
	 * 显示进度框.
	 * @param context the context
	 * @param indeterminateDrawable 用默认请写0
	 * @param message the message
	 */
	public static AbProgressDialogFragment showProgressDialog(Context context, int indeterminateDrawable, String message) {
		FragmentActivity activity = (FragmentActivity)context; 
		AbProgressDialogFragment newFragment = AbProgressDialogFragment.newInstance(indeterminateDrawable,message);
		FragmentTransaction ft = activity.getFragmentManager().beginTransaction();
        // 指定一个系统转场动画   
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);  
		newFragment.show(ft, dialogTag);
	    return newFragment;
    }

	/**
	 * 显示一个隐藏的的对话框.
	 * @param context
	 * @param fragment
     */
	public static void showDialog(Context context,DialogFragment fragment) {
		FragmentActivity activity = (FragmentActivity)context;
		fragment.show(activity.getFragmentManager(), dialogTag);
	}

	
	/**
	 * 移除Fragment.
	 * @param context the context
	 */
	public static void removeDialog(final Context context){
		try {
			FragmentActivity activity = (FragmentActivity)context; 
			FragmentTransaction ft = activity.getFragmentManager().beginTransaction();
	        // 指定一个系统转场动画   
	        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);  
			Fragment prev = activity.getFragmentManager().findFragmentByTag(dialogTag);
			if (prev != null) {
			    ft.remove(prev);
			}
			//不能加入到back栈
			//ft.addToBackStack(null);
		    ft.commit();
		} catch (Exception e) {
			//可能有Activity已经被销毁的异常
			e.printStackTrace();
		}
	}
	
	/**
	 * 移除Fragment和View
	 * @param view
	 */
	public static void removeDialog(View view){
		removeDialog(view.getContext());
		AbViewUtil.removeSelfFromParent(view);
	}
	

}
