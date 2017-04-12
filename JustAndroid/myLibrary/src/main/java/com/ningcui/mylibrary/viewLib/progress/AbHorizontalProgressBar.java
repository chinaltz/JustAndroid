
package com.ningcui.mylibrary.viewLib.progress;

import android.content.Context;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.EmbossMaskFilter;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader.TileMode;
import android.util.AttributeSet;
import android.view.View;

import com.ningcui.mylibrary.viewLib.listener.AbOnProgressListener;


/**
 * Copyright 李挺哲
 * 创建人：litingzhe
 * 邮箱：453971498@qq.com
 * Created by litingzhe on 2017/4/11 下午3:23.
 * Info 长条形的ProgressBar
 */
public class AbHorizontalProgressBar extends View {

	/** 当前进度. */
	private int progress = 0;

	/** 最大进度. */
	private int max = 100;

	/** 绘制轨迹. */
	private Paint pathPaint = null;

	/** 绘制填充*/
	private Paint fillPaint = null;

	/** 路径宽度. */
	private int pathWidth = 35;

	/** 宽度. */
	private int width;

	/** 高度. */
	private int height;

	/** 灰色轨迹. */
	private int pathColor = 0xFFF0EEDF;

	/** 灰色轨迹边. */
	private int pathBorderColor = 0xFFD2D1C4;

	/** 梯度渐变的填充颜色. */
	private int[] fillColors = new int[] {0xFF3DF346,0xFF02C016};

	/** 指定了光源的方向和环境光强度来添加浮雕效果. */
	private EmbossMaskFilter emboss = null;

	/** 设置光源的方向. */
	float[] direction = new float[]{1,1,1};

	/** 设置环境光亮度. */
	float light = 0.4f;

	/** 选择要应用的反射等级. */
	float specular = 6;

	/** 向 mask应用一定级别的模糊. */
	float blur = 3.5f;

	/** 指定了一个模糊的样式和半径来处理 Paint 的边缘. */
	private BlurMaskFilter mBlur = null;

	/** 监听器. */
	private AbOnProgressListener onProgressListener = null;

	/** view重绘的标记. */
	private boolean reset = false;


	/**
	 *  构造函数.
	 *
	 * @param context the context
	 * @param attrs the attrs
	 */
	public AbHorizontalProgressBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		pathPaint  = new Paint();
		// 设置是否抗锯齿
		pathPaint.setAntiAlias(true);
		// 帮助消除锯齿
		pathPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
		// 设置中空的样式
		pathPaint.setStyle(Paint.Style.FILL);
		pathPaint.setDither(true);
		//pathPaint.setStrokeJoin(Paint.Join.ROUND);
		
		fillPaint = new Paint();
		// 设置是否抗锯齿
		fillPaint.setAntiAlias(true);
		// 帮助消除锯齿
		fillPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
		// 设置中空的样式
		fillPaint.setStyle(Paint.Style.FILL);
		fillPaint.setDither(true);
		//fillPaint.setStrokeJoin(Paint.Join.ROUND);
		
		emboss = new EmbossMaskFilter(direction,light,specular,blur);  
		mBlur = new BlurMaskFilter(20, BlurMaskFilter.Blur.NORMAL);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		if(reset){
			canvas.drawColor(Color.TRANSPARENT);
			reset = false;
		}
		this.width = getMeasuredWidth();
		this.height = getMeasuredHeight();
		
		// 设置画笔颜色
		pathPaint.setColor(pathColor);
		// 设置画笔宽度
		pathPaint.setStrokeWidth(pathWidth);
				
		//添加浮雕效果
		pathPaint.setMaskFilter(emboss); 
		canvas.drawRect(0, 0, this.width, this.height, pathPaint);
		pathPaint.setColor(pathBorderColor);
		canvas.drawRect(0, 0, this.width, this.height, pathPaint);
		
		
		LinearGradient linearGradient = new LinearGradient(0, 0, this.width, this.height, 
				fillColors[0], fillColors[1], TileMode.CLAMP); 
		fillPaint.setShader(linearGradient);
		
		//模糊效果
		fillPaint.setMaskFilter(mBlur);
		
		//设置线的类型,边是圆的
		fillPaint.setStrokeCap(Paint.Cap.ROUND);
		
		fillPaint.setStrokeWidth(pathWidth);
		canvas.drawRect(0, 0, ((float) progress / max) * this.width,this.height, fillPaint);
		
	}

	public AbOnProgressListener getAbOnProgressListener() {
		return onProgressListener;
	}

	public void setOnProgressListener(AbOnProgressListener onProgressListener) {
		this.onProgressListener = onProgressListener;
	}  
	
	/**
	 * 重置进度.
	 */
	public void reset(){
		reset  = true;
		this.progress = 0;
		this.invalidate();
	}
	
	
	/**
	 * 获取颜色
	 * @return
	 */
	public int[] getFillColors() {
		return fillColors;
	}

	/**
	 * 设置颜色
	 * @param fillColors
	 */
	public void setFillColors(int[] fillColors) {
		this.fillColors = fillColors;
		invalidate();
	}

    /**
     * 获取背景色
     * @return
     */
	public int getPathColor() {
		return pathColor;
	}

	/**
     * 设置背景色
     * @return
     */
	public void setPathColor(int pathColor) {
		this.pathColor = pathColor;
		invalidate();
	}

	/**
     * 获取背景边框色
     * @return
     */
	public int getPathBorderColor() {
		return pathBorderColor;
	}

	/**
     * 设置背景边框色
     * @return
     */
	public void setPathBorderColor(int pathBorderColor) {
		this.pathBorderColor = pathBorderColor;
		invalidate();
	}

	/**
	 * 获取当前进度.
	 *
	 * @return the progress
	 */
	public int getProgress() {
		return progress;
	}

	/**
	 * 设置当前进度.
	 * @param progress the new progress
	 */
	public void setProgress(int progress) {
		this.progress = progress;
		this.invalidate();
		if(this.onProgressListener!=null){
			if(this.max <= this.progress){
				this.onProgressListener.onComplete();
			}else{
				this.onProgressListener.onProgress(progress);
			}
		}
	}

	/**
	 * 获取最大进度.
	 *
	 * @return the max
	 */
	public int getMax() {
		return max;
	}

	/**
	 * 设置最大进度.
	 *
	 * @param max the new max
	 */
	public void setMax(int max) {
		this.max = max;
	}


}
