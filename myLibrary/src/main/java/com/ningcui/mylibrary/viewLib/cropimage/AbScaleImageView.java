
package com.ningcui.mylibrary.viewLib.cropimage;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.widget.ImageView;

import com.ningcui.mylibrary.utiils.AbImageUtil;


/**
 * Copyright 李挺哲
 * 创建人：litingzhe
 * 邮箱：453971498@qq.com
 * Created by litingzhe on 2017/4/11 下午3:23.
 * Info 可控制放大缩小的ImageView.
 */

public abstract class AbScaleImageView extends ImageView {

    /** 状态:高亮. */
    public static final int STATE_HIGHLIGHT = 0x0;

    /** 状态:双击. */
    public static final int STATE_DOODLE = STATE_HIGHLIGHT + 1;

    /** 状态:没有. */
    public static final int STATE_NONE = STATE_HIGHLIGHT + 2;

    /** 当前状态. */
    protected int currentState = STATE_HIGHLIGHT;

	/** The m base matrix. */
	protected Matrix baseMatrix = new Matrix();

	/** The m supp matrix. */
	protected Matrix suppMatrix = new Matrix();

	/** The m display matrix. */
	private final Matrix displayMatrix = new Matrix();

	/** The m matrix values. */
	private final float[] matrixValues = new float[9];

	/** 当前显示的Bitmap. */
	final public AbRotateBitmap bitmapDisplayed = new AbRotateBitmap(null);

	/** 当前显示的图片宽高. */
	int thisWidth = -1;

	int	thisHeight = -1;

	/** 最大缩放. */
	float maxZoom;

	/** Handler. */
	protected Handler handler = new Handler();

	/** layout 线程. */
	private Runnable onLayoutRunnable = null;

	/** 缩放比例. */
	static final float SCALE_RATE = 1.25F;

    /** 当前图标. */
    public Bitmap bitmap;

	public AbScaleImageView(Context context) {
		super(context);
		init();
	}


	public AbScaleImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	private void init() {
		setScaleType(ScaleType.MATRIX);
	}

	@Override
	protected void onLayout(boolean changed, int left, int top, int right,
			int bottom) {
		super.onLayout(changed, left, top, right, bottom);
		thisWidth = right - left;
		thisHeight = bottom - top;
		Runnable r = onLayoutRunnable;
		if (r != null) {
			onLayoutRunnable = null;
			r.run();
		}
		if (bitmapDisplayed.getBitmap() != null) {
			getProperBaseMatrix(bitmapDisplayed, baseMatrix);
			setImageMatrix(getImageViewMatrix());
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && getScale() > 1.0f) {
			zoomTo(1.0f);
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}


	/**
	 * 设置图片
	 * @param bitmap
     */
	@Override
	public void setImageBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
		setImageBitmap(bitmap, 0);
	}

	/**
	 * 设置图片
	 * @param bitmap the bitmap
	 * @param rotation the rotation  角度
	 */
	private void setImageBitmap(Bitmap bitmap, int rotation) {
		super.setImageBitmap(bitmap);
		Drawable d = getDrawable();
		if (d != null) {
			d.setDither(true);
		}

		Bitmap old = bitmapDisplayed.getBitmap();
		bitmapDisplayed.setBitmap(bitmap);
		bitmapDisplayed.setRotation(rotation);

		if (old != null && old != bitmap) {
			AbImageUtil.releaseBitmap(old);
		}
	}

	/**
	 * Clear.
	 */
	public void clear() {
		setImageBitmapResetBase(null, true);
	}

	public void setImageBitmapResetBase(final Bitmap bitmap,
			final boolean resetSupp) {
		setImageRotateBitmapResetBase(new AbRotateBitmap(bitmap), resetSupp);
	}

	public void setImageRotateBitmapResetBase(final AbRotateBitmap bitmap,
			final boolean resetSupp) {
		final int viewWidth = getWidth();

		if (viewWidth <= 0) {
			onLayoutRunnable = new Runnable() {
				public void run() {
					setImageRotateBitmapResetBase(bitmap, resetSupp);
				}
			};
			return;
		}

		if (bitmap.getBitmap() != null) {
			getProperBaseMatrix(bitmap, baseMatrix);
			setImageBitmap(bitmap.getBitmap(), bitmap.getRotation());
		} else {
			baseMatrix.reset();
			setImageBitmap(null);
		}

		if (resetSupp) {
			suppMatrix.reset();
		}
		setImageMatrix(getImageViewMatrix());
		maxZoom = maxZoom();
	}

	public void center(boolean horizontal, boolean vertical) {
		if (bitmapDisplayed.getBitmap() == null) {
			return;
		}

		Matrix m = getImageViewMatrix();

		RectF rect = new RectF(0, 0, bitmapDisplayed.getBitmap().getWidth(),
				bitmapDisplayed.getBitmap().getHeight());

		m.mapRect(rect);

		float height = rect.height();
		float width = rect.width();

		float deltaX = 0, deltaY = 0;

		if (vertical) {
			int viewHeight = getHeight();
			if (height < viewHeight) {
				deltaY = (viewHeight - height) / 2 - rect.top;
			} else if (rect.top > 0) {
				deltaY = -rect.top;
			} else if (rect.bottom < viewHeight) {
				deltaY = getHeight() - rect.bottom;
			}
		}

		if (horizontal) {
			int viewWidth = getWidth();
			if (width < viewWidth) {
				deltaX = (viewWidth - width) / 2 - rect.left;
			} else if (rect.left > 0) {
				deltaX = -rect.left;
			} else if (rect.right < viewWidth) {
				deltaX = viewWidth - rect.right;
			}
		}

		postTranslate(deltaX, deltaY);
		setImageMatrix(getImageViewMatrix());
	}


	protected float getValue(Matrix matrix, int whichValue) {
		matrix.getValues(matrixValues);
		return matrixValues[whichValue];
	}

	protected float getScale(Matrix matrix) {
		return getValue(matrix, Matrix.MSCALE_X);
	}

	public float getScale() {
		return getScale(suppMatrix);
	}

	private void getProperBaseMatrix(AbRotateBitmap bitmap, Matrix matrix) {
		float viewWidth = getWidth();
		float viewHeight = getHeight();

		float w = bitmap.getWidth();
		float h = bitmap.getHeight();
		matrix.reset();

		float widthScale = Math.min(viewWidth / w, 2.0f);
		float heightScale = Math.min(viewHeight / h, 2.0f);
		float scale = Math.min(widthScale, heightScale);

		matrix.postConcat(bitmap.getRotateMatrix());
		matrix.postScale(scale, scale);

		matrix.postTranslate((viewWidth - w * scale) / 2F, (viewHeight - h
				* scale) / 2F);
	}

	protected Matrix getImageViewMatrix() {
		displayMatrix.set(baseMatrix);
		displayMatrix.postConcat(suppMatrix);
		return displayMatrix;
	}

	/**
	 * 获取缩放最大比例
	 * @return
     */
	protected float maxZoom() {
		if (bitmapDisplayed.getBitmap() == null) {
			return 1F;
		}

		float fw = (float) bitmapDisplayed.getWidth() / (float) thisWidth;
		float fh = (float) bitmapDisplayed.getHeight() / (float) thisHeight;
		float max = Math.max(fw, fh) * 4;
		max = (max < 1.0f) ? 1.0f : max;
		return max;
	}

	/**
	 * 缩放.
	 * @param scale the scale
	 * @param centerX the center x
	 * @param centerY the center y
	 */
	protected void zoomTo(float scale, float centerX, float centerY) {
		if (scale > maxZoom) {
			scale = maxZoom;
		}

		float oldScale = getScale();
		float deltaScale = scale / oldScale;

		suppMatrix.postScale(deltaScale, deltaScale, centerX, centerY);
		setImageMatrix(getImageViewMatrix());
		center(true, true);
	}

	/**
	 * 缩放.
	 * @param scale the scale
	 * @param centerX the center x
	 * @param centerY the center y
	 * @param durationMs the duration ms
	 */
	protected void zoomTo(final float scale, final float centerX,
			final float centerY, final float durationMs) {
		final float incrementPerMs = (scale - getScale()) / durationMs;
		final float oldScale = getScale();
		final long startTime = System.currentTimeMillis();

		handler.post(new Runnable() {
			public void run() {
				long now = System.currentTimeMillis();
				float currentMs = Math.min(durationMs, now - startTime);
				float target = oldScale + (incrementPerMs * currentMs);
				zoomTo(target, centerX, centerY);

				if (currentMs < durationMs) {
					handler.post(this);
				}
			}
		});
	}

	/**
	 * 缩放.
	 * @param scale the scale  比例
	 */
	protected void zoomTo(float scale) {
		float cx = getWidth() / 2F;
		float cy = getHeight() / 2F;
		zoomTo(scale, cx, cy);
	}

	/**
	 * 缩小一次.
	 */
	protected void zoomIn() {
		zoomIn(SCALE_RATE);
	}

	/**
	 * 放大一次.
	 */
	protected void zoomOut() {
		zoomOut(SCALE_RATE);
	}

	/**
	 * 缩小.
	 *
	 * @param rate the rate
	 */
	protected void zoomIn(float rate) {
		if (getScale() >= maxZoom) {
			return;
		}
		if (bitmapDisplayed.getBitmap() == null) {
			return;
		}

		float cx = getWidth() / 2F;
		float cy = getHeight() / 2F;

		suppMatrix.postScale(rate, rate, cx, cy);
		setImageMatrix(getImageViewMatrix());
	}

	/**
	 * 放大.
	 *
	 * @param rate the rate
	 */
	protected void zoomOut(float rate) {
		if (bitmapDisplayed.getBitmap() == null) {
			return;
		}

		float cx = getWidth() / 2F;
		float cy = getHeight() / 2F;

		// Zoom out to at most 1x.
		Matrix tmp = new Matrix(suppMatrix);
		tmp.postScale(1F / rate, 1F / rate, cx, cy);

		if (getScale(tmp) < 1F) {
			suppMatrix.setScale(1F, 1F, cx, cy);
		} else {
			suppMatrix.postScale(1F / rate, 1F / rate, cx, cy);
		}
		setImageMatrix(getImageViewMatrix());
		center(true, true);
	}

	/**
	 * 移动．
	 * @param dx the dx
	 * @param dy the dy
	 */
	protected void postTranslate(float dx, float dy) {
		suppMatrix.postTranslate(dx, dy);
	}

	/**
	 * 移动和缩放.
	 * @param dx the dx
	 * @param dy the dy
	 */
	protected void panBy(float dx, float dy) {
		postTranslate(dx, dy);
		setImageMatrix(getImageViewMatrix());
	}

}
