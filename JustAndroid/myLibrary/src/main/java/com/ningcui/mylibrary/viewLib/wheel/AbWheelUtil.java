
package com.ningcui.mylibrary.viewLib.wheel;




import com.ningcui.mylibrary.utiils.AbDateUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;



/**
 * Copyright 李挺哲
 * 创建人：litingzhe
 * 邮箱：453971498@qq.com
 * Created by litingzhe on 2017/4/11 下午3:23.
 * Info 轮子工具类
 */

public class AbWheelUtil {

	public final static  List<String> MDHMList = new ArrayList<String>();
	
	/**
	 * 默认的年月日的日期选择器.
	 *
	 * @param mWheelViewY  选择年的轮子
	 * @param mWheelViewM  选择月的轮子
	 * @param mWheelViewD  选择日的轮子
	 * @param defaultYear  默认显示的年
	 * @param defaultMonth the default month
	 * @param defaultDay the default day
	 * @param minYear    开始的年
	 * @param maxYear     结束的年
	 */
	public static void initWheelPickerYMD(final AbWheelView mWheelViewY,final AbWheelView mWheelViewM,final AbWheelView mWheelViewD,
			 int defaultYear,int defaultMonth,int defaultDay,final int minYear,int maxYear){

		// 添加大小月月份并将其转换为list,方便之后的判断
		String[] months_big = { "1", "3", "5", "7", "8", "10", "12" };
		String[] months_little = { "4", "6", "9", "11" };
		//时间选择可以这样实现
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH)+1;
		int day = calendar.get(Calendar.DATE);

		if(defaultYear <= 0){
			 defaultYear = year;
		}
		if(defaultMonth <= 0){
			defaultMonth = month;
		}
		if(defaultDay <= 0){
			defaultDay = day;
		}

		final List<String> list_big = Arrays.asList(months_big);
		final List<String> list_little = Arrays.asList(months_little);

		//设置"年"的显示数据
		mWheelViewY.setAdapter(new AbNumericWheelAdapter(minYear, maxYear));
		mWheelViewY.setCyclic(true);// 可循环滚动
		mWheelViewY.setLabel("年");  // 添加文字
		mWheelViewY.setCurrentItem(defaultYear - minYear);// 初始化时显示的数据
		mWheelViewY.setValueTextSize(35);
		mWheelViewY.setLabelTextSize(35);
		mWheelViewY.setLabelTextColor(0x80000000);
		//mWheelViewY.setCenterSelectDrawable(this.getResources().getDrawable(R.drawable.wheel_select));

		// 月
		mWheelViewM.setAdapter(new AbNumericWheelAdapter(1, 12));
		mWheelViewM.setCyclic(true);
		mWheelViewM.setLabel("月");
		mWheelViewM.setCurrentItem(defaultMonth-1);
		mWheelViewM.setValueTextSize(35);
		mWheelViewM.setLabelTextSize(35);
		mWheelViewM.setLabelTextColor(0x80000000);
		//mWheelViewM.setCenterSelectDrawable(this.getResources().getDrawable(R.drawable.wheel_select));

		// 日
		// 判断大小月及是否闰年,用来确定"日"的数据
		if (list_big.contains(String.valueOf(month))) {
			mWheelViewD.setAdapter(new AbNumericWheelAdapter(1, 31));
		} else if (list_little.contains(String.valueOf(month))) {
			mWheelViewD.setAdapter(new AbNumericWheelAdapter(1, 30));
		} else {
			// 闰年
			if (AbDateUtil.isLeapYear(year)){
				mWheelViewD.setAdapter(new AbNumericWheelAdapter(1, 29));
			}else{
				mWheelViewD.setAdapter(new AbNumericWheelAdapter(1, 28));
			}
		}
		mWheelViewD.setCyclic(true);
		mWheelViewD.setLabel("日");
		mWheelViewD.setCurrentItem(defaultDay - 1);
		mWheelViewD.setValueTextSize(35);
		mWheelViewD.setLabelTextSize(35);
		mWheelViewD.setLabelTextColor(0x80000000);
		//mWheelViewD.setCenterSelectDrawable(this.getResources().getDrawable(R.drawable.wheel_select));

		// 添加"年"监听
		AbWheelView.AbOnWheelChangedListener wheelListener_year = new AbWheelView.AbOnWheelChangedListener() {

			public void onChanged(AbWheelView wheel, int oldValue, int newValue) {
				int year_num = newValue + minYear;
				int mDIndex = mWheelViewM.getCurrentItem();
				// 判断大小月及是否闰年,用来确定"日"的数据
				if (list_big.contains(String.valueOf(mWheelViewM.getCurrentItem() + 1))) {
					mWheelViewD.setAdapter(new AbNumericWheelAdapter(1, 31));
				} else if (list_little.contains(String.valueOf(mWheelViewM.getCurrentItem() + 1))) {
					mWheelViewD.setAdapter(new AbNumericWheelAdapter(1, 30));
				} else {
					if (AbDateUtil.isLeapYear(year_num))
						mWheelViewD.setAdapter(new AbNumericWheelAdapter(1, 29));
					else
						mWheelViewD.setAdapter(new AbNumericWheelAdapter(1, 28));
				}
				mWheelViewM.setCurrentItem(mDIndex);

			}
		};
		// 添加"月"监听
		AbWheelView.AbOnWheelChangedListener wheelListener_month = new AbWheelView.AbOnWheelChangedListener() {

			public void onChanged(AbWheelView wheel, int oldValue, int newValue) {
				int month_num = newValue + 1;
				// 判断大小月及是否闰年,用来确定"日"的数据
				if (list_big.contains(String.valueOf(month_num))) {
					mWheelViewD.setAdapter(new AbNumericWheelAdapter(1, 31));
				} else if (list_little.contains(String.valueOf(month_num))) {
					mWheelViewD.setAdapter(new AbNumericWheelAdapter(1, 30));
				} else {
					int year_num = mWheelViewY.getCurrentItem() + minYear;
					if (AbDateUtil.isLeapYear(year_num))
						mWheelViewD.setAdapter(new AbNumericWheelAdapter(1, 29));
					else
						mWheelViewD.setAdapter(new AbNumericWheelAdapter(1, 28));
				}
				mWheelViewD.setCurrentItem(0);
			}
		};
		mWheelViewY.addChangingListener(wheelListener_year);
		mWheelViewM.addChangingListener(wheelListener_month);

    }

	/**
	 * 默认当前时间的月日时分的时间选择器.
	 * @param mWheelViewMD  选择月日的轮子
	 * @param mWheelViewHH 选择时间的轮子
	 * @param mWheelViewMM  选择分的轮
	 */
	public static void initWheelPickerMDHM(final AbWheelView mWheelViewMD,final AbWheelView mWheelViewHH,final AbWheelView mWheelViewMM){
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH)+1;
		int day = calendar.get(Calendar.DATE);
		int hour = calendar.get(Calendar.HOUR_OF_DAY);
		int minute = calendar.get(Calendar.MINUTE);
		//int second = calendar.get(Calendar.SECOND);
		initWheelPickerMDHM(mWheelViewMD,mWheelViewHH,mWheelViewMM,year,month,day,hour,minute);
	}

	/**
	 * 默认的月日时分的时间选择器.
	 * @param mWheelViewMD  选择月日的轮子
	 * @param mWheelViewHH the m wheel view hh
	 * @param mWheelViewMM  选择分的轮子
	 * @param defaultYear the default year
	 * @param defaultMonth the default month
	 * @param defaultDay the default day
	 * @param defaultHour the default hour
	 * @param defaultMinute the default minute
	 */
	public static void initWheelPickerMDHM(final AbWheelView mWheelViewMD,final AbWheelView mWheelViewHH,final AbWheelView mWheelViewMM,
			 int defaultYear,int defaultMonth,int defaultDay,int defaultHour,int defaultMinute){


		// 添加大小月月份并将其转换为list,方便之后的判断
		String[] months_big = { "1", "3", "5", "7", "8", "10", "12" };
		String[] months_little = { "4", "6", "9", "11" };
		final List<String> list_big = Arrays.asList(months_big);
		final List<String> list_little = Arrays.asList(months_little);
		//
		final List<String> textDMList = new ArrayList<String>();

		for(int i=1;i<13;i++){
			if(list_big.contains(String.valueOf(i))){
				for(int j=1;j<32;j++){
					textDMList.add(i+"月"+" "+j+"日");
					MDHMList.add(defaultYear+"-"+i+"-"+j);
				}
			}else{
				if(i==2){
					if(AbDateUtil.isLeapYear(defaultYear)){
						for(int j=1;j<28;j++){
							textDMList.add(i+"月"+" "+j+"日");
							MDHMList.add(defaultYear+"-"+i+"-"+j);
						}
					}else{
						for(int j=1;j<29;j++){
							textDMList.add(i+"月"+" "+j+"日");
							MDHMList.add(defaultYear+"-"+i+"-"+j);
						}
					}
				}else{
					for(int j=1;j<29;j++){
						textDMList.add(i+"月"+" "+j+"日");
						MDHMList.add(defaultYear+"-"+i+"-"+j);
					}
				}
			}

		}
		String currentDay = defaultMonth+"月"+" "+defaultDay+"日";
		int currentDayIndex = textDMList.indexOf(currentDay);

		// 月日
		mWheelViewMD.setAdapter(new AbStringWheelAdapter(textDMList));
		mWheelViewMD.setCyclic(true);
		mWheelViewMD.setLabel("");
		mWheelViewMD.setCurrentItem(currentDayIndex);
		mWheelViewMD.setValueTextSize(35);
		mWheelViewMD.setLabelTextSize(35);
		mWheelViewMD.setLabelTextColor(0x80000000);
		//mWheelViewMD.setCenterSelectDrawable(this.getResources().getDrawable(R.drawable.wheel_select));

		// 时
		mWheelViewHH.setAdapter(new AbNumericWheelAdapter(0, 23));
		mWheelViewHH.setCyclic(true);
		mWheelViewHH.setLabel("点");
		mWheelViewHH.setCurrentItem(defaultHour);
		mWheelViewHH.setValueTextSize(35);
		mWheelViewHH.setLabelTextSize(35);
		mWheelViewHH.setLabelTextColor(0x80000000);
		//mWheelViewH.setCenterSelectDrawable(this.getResources().getDrawable(R.drawable.wheel_select));

		// 分
		mWheelViewMM.setAdapter(new AbNumericWheelAdapter(0, 59));
		mWheelViewMM.setCyclic(true);
		mWheelViewMM.setLabel("分");
		mWheelViewMM.setCurrentItem(defaultMinute);
		mWheelViewMM.setValueTextSize(35);
		mWheelViewMM.setLabelTextSize(35);
		mWheelViewMM.setLabelTextColor(0x80000000);
		//mWheelViewM.setCenterSelectDrawable(this.getResources().getDrawable(R.drawable.wheel_select));

    }

	/**
	 * 默认的时分的时间选择器.
	 * @param mWheelViewHH the m wheel view hh
	 * @param mWheelViewMM  选择分的轮子
	 */
	public static void initWheelPickerHM(final AbWheelView mWheelViewHH,final AbWheelView mWheelViewMM) {
		Calendar calendar = Calendar.getInstance();
		int hour = calendar.get(Calendar.HOUR_OF_DAY);
		int minute = calendar.get(Calendar.MINUTE);
		initWheelPickerHM(mWheelViewHH,mWheelViewMM,hour,minute);
	}

	/**
	 * 默认的时分的时间选择器.
	 *
	 * @param mWheelViewHH the m wheel view hh
	 * @param mWheelViewMM  选择分的轮子
	 * @param defaultHour the default hour
	 * @param defaultMinute the default minute
	 */
	public static void initWheelPickerHM(final AbWheelView mWheelViewHH,final AbWheelView mWheelViewMM,
			 int defaultHour,int defaultMinute){

		// 时
		mWheelViewHH.setAdapter(new AbNumericWheelAdapter(0, 23));
		mWheelViewHH.setCyclic(true);
		mWheelViewHH.setLabel("点");
		mWheelViewHH.setCurrentItem(defaultHour);
		mWheelViewHH.setValueTextSize(35);
		mWheelViewHH.setLabelTextSize(35);
		mWheelViewHH.setLabelTextColor(0x80000000);
		//mWheelViewH.setCenterSelectDrawable(this.getResources().getDrawable(R.drawable.wheel_select));
		
		// 分
		mWheelViewMM.setAdapter(new AbNumericWheelAdapter(0, 59));
		mWheelViewMM.setCyclic(true);
		mWheelViewMM.setLabel("分");
		mWheelViewMM.setCurrentItem(defaultMinute);
		mWheelViewMM.setValueTextSize(35);
		mWheelViewMM.setLabelTextSize(35);
		mWheelViewMM.setLabelTextColor(0x80000000);
		//mWheelViewM.setCenterSelectDrawable(this.getResources().getDrawable(R.drawable.wheel_select));
		
    }

	
}
