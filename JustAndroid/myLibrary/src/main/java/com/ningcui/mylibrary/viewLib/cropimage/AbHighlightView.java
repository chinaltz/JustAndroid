
package com.ningcui.mylibrary.viewLib.cropimage;


import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Region;
import android.graphics.RegionIterator;
import android.graphics.drawable.Drawable;
import android.view.View;

import com.ningcui.mylibrary.R;


/**
 * Copyright 李挺哲
 * 创建人：litingzhe
 * 邮箱：453971498@qq.com
 * Created by litingzhe on 2017/4/11 下午3:23.
 * Info 高亮的图片裁剪框.
 */
public class AbHighlightView {

    /** 上下文View. */
    View contextView;

    /**
     * The Enum ModifyMode.
     */
    public enum ModifyMode {

        /** The None. */
        None,
        /** The Move. */
        Move,
        /** The Grow. */
        Grow
    }

    /** The m mode. */
    private ModifyMode mMode = ModifyMode.None;

    public Rect mDrawRect;

    public RectF mImageRect;

    public RectF mCropRect;

    public Matrix mMatrix;

    private boolean mMaintainAspectRatio = false;

    private float mInitialAspectRatio;

    private boolean mCircle = false;

    private Drawable mResizeDrawableDiagonal;

    private Drawable mResizeDrawableDiagonal2;

    private final Paint mFocusPaint = new Paint();

    private final Paint mNoFocusPaint = new Paint();

    private final Paint mOutlinePaint = new Paint();

    public static final int GROW_NONE = (1 << 0);
    
    public static final int GROW_LEFT_EDGE = (1 << 1);
    
    public static final int GROW_RIGHT_EDGE = (1 << 2);
    
    public static final int GROW_TOP_EDGE = (1 << 3);
    
    public static final int GROW_BOTTOM_EDGE = (1 << 4);
    
    public static final int MOVE = (1 << 5);

    /** The m is focused. */
    public boolean mIsFocused;

    /** The m hidden. */
    boolean mHidden;


    /**
     * 构造函数.
     * @param view the view
     */
    public AbHighlightView(View view) {
        contextView = view;
    }

    /**
     * 初始化图片.
     */
    private void init() {

        mResizeDrawableDiagonal =  contextView.getResources().getDrawable(R.drawable.crop_big);
        mResizeDrawableDiagonal2 =  contextView.getResources().getDrawable(R.drawable.crop_small);
    }

    /**
     * Checks for focus.
     *
     * @return true, if successful
     */
    public boolean hasFocus() {
        return mIsFocused;
    }

    /**
     * Sets the focus.
     *
     * @param f the new focus
     */
    public void setFocus(boolean f) {
        mIsFocused = f;
    }

    /**
     * Sets the hidden.
     *
     * @param hidden the new hidden
     */
    public void setHidden(boolean hidden) {
        mHidden = hidden;
    }

    /**
     * Draw.
     *
     * @param canvas the canvas
     */
    public void draw(Canvas canvas) {
        if (mHidden) {
            return;
        }
        canvas.save();
        Path path = new Path();
        if (!hasFocus()) {
            mOutlinePaint.setColor(0xFF000000);
            canvas.drawRect(mDrawRect, mOutlinePaint);
        } else {
            Rect viewDrawingRect = new Rect();
            contextView.getDrawingRect(viewDrawingRect);
            if (mCircle) {
                float width = mDrawRect.width();
                float height = mDrawRect.height();
                path.addCircle(mDrawRect.left + (width / 2), mDrawRect.top + (height / 2), width / 2, Path.Direction.CW);
                mOutlinePaint.setColor(0xFFEF04D6);
            } else {
                path.addRect(new RectF(mDrawRect), Path.Direction.CW);
                mOutlinePaint.setColor(0xFFFF8A00);
            }
            Region region = new Region(); 
            region.set(viewDrawingRect);
            region.op(mDrawRect, Region.Op.DIFFERENCE);
            RegionIterator iter = new RegionIterator(region);
            Rect r = new Rect();
            while (iter.next(r)) {
            	canvas.drawRect(r, hasFocus() ? mFocusPaint : mNoFocusPaint);
            }

            canvas.restore();
            canvas.drawPath(path, mOutlinePaint);

            if (mMode == ModifyMode.Grow) {
                if (mCircle) {
                    int width = mResizeDrawableDiagonal.getIntrinsicWidth();
                    int height = mResizeDrawableDiagonal.getIntrinsicHeight();

                    int d = (int) Math.round(Math.cos(/* 45deg */Math.PI / 4D) * (mDrawRect.width() / 2D));
                    int x = mDrawRect.left + (mDrawRect.width() / 2) + d - width / 2;
                    int y = mDrawRect.top + (mDrawRect.height() / 2) - d - height / 2;
                    mResizeDrawableDiagonal.setBounds(x, y, x + mResizeDrawableDiagonal.getIntrinsicWidth(), y
                            + mResizeDrawableDiagonal.getIntrinsicHeight());
                    mResizeDrawableDiagonal.draw(canvas);
                } else {
                    //
                }
            }
            if (mCircle) {
            	
            }else{
	            int left = mDrawRect.left + 1;
	            int right = mDrawRect.right + 1;
	            int top = mDrawRect.top + 4;
	            int bottom = mDrawRect.bottom + 3;
	
	            int widthWidth = mResizeDrawableDiagonal.getIntrinsicWidth() / 2;
	            int widthHeight = mResizeDrawableDiagonal.getIntrinsicHeight() / 2;

	            mResizeDrawableDiagonal2.setBounds(left - widthWidth, top - widthHeight, left + widthWidth, top+ widthHeight);
	            mResizeDrawableDiagonal2.draw(canvas);
	            mResizeDrawableDiagonal.setBounds(right - widthWidth, top - widthHeight, right + widthWidth, top+ widthHeight);
	            mResizeDrawableDiagonal.draw(canvas);
	            mResizeDrawableDiagonal.setBounds(left - widthWidth, bottom - widthHeight, left + widthWidth, bottom+ widthHeight);
	            mResizeDrawableDiagonal.draw(canvas);
	            mResizeDrawableDiagonal2.setBounds(right - widthWidth, bottom - widthHeight, right + widthWidth, bottom+ widthHeight);
	            mResizeDrawableDiagonal2.draw(canvas);
            }
        }
    }

    /**
     * Sets the mode.
     *
     * @param mode the new mode
     */
    public void setMode(ModifyMode mode) {
        if (mode != mMode) {
            mMode = mode;
            contextView.invalidate();
        }
    }

    public int getHit(float x, float y) {
        Rect r = computeLayout();
        final float hysteresis = 20F;
        int retval = GROW_NONE;

        if (mCircle) {
            float distX = x - r.centerX();
            float distY = y - r.centerY();
            int distanceFromCenter = (int) Math.sqrt(distX * distX + distY * distY);
            int radius = mDrawRect.width() / 2;
            int delta = distanceFromCenter - radius;
            if (Math.abs(delta) <= hysteresis) {
                if (Math.abs(distY) > Math.abs(distX)) {
                    if (distY < 0) {
                        retval = GROW_TOP_EDGE;
                    } else {
                        retval = GROW_BOTTOM_EDGE;
                    }
                } else {
                    if (distX < 0) {
                        retval = GROW_LEFT_EDGE;
                    } else {
                        retval = GROW_RIGHT_EDGE;
                    }
                }
            } else 
            	if (distanceFromCenter < radius) {
                retval = MOVE;
            } else {
                retval = GROW_NONE;
            }
        } else {
            boolean verticalCheck = (y >= r.top - hysteresis) && (y < r.bottom + hysteresis);
            boolean horizCheck = (x >= r.left - hysteresis) && (x < r.right + hysteresis);

            if ((Math.abs(r.left - x) < hysteresis) && verticalCheck) {
                retval |= GROW_LEFT_EDGE;
            }
            if ((Math.abs(r.right - x) < hysteresis) && verticalCheck) {
                retval |= GROW_RIGHT_EDGE;
            }
            if ((Math.abs(r.top - y) < hysteresis) && horizCheck) {
                retval |= GROW_TOP_EDGE;
            }
            if ((Math.abs(r.bottom - y) < hysteresis) && horizCheck) {
                retval |= GROW_BOTTOM_EDGE;
            }

            if (retval == GROW_NONE && r.contains((int) x, (int) y)) {
                retval = MOVE;
            }
        }
        return retval;
    }

    /**
     * Handle motion.
     *
     * @param edge the edge
     * @param dx the dx
     * @param dy the dy
     */
    public void handleMotion(int edge, float dx, float dy) {
        Rect r = computeLayout();
        if (edge == GROW_NONE) {
            return;
        } else if (edge == MOVE) {
            moveBy(dx * (mCropRect.width() / r.width()), dy * (mCropRect.height() / r.height()));
        } else {
            if (((GROW_LEFT_EDGE | GROW_RIGHT_EDGE) & edge) == 0) {
                dx = 0;
            }

            if (((GROW_TOP_EDGE | GROW_BOTTOM_EDGE) & edge) == 0) {
                dy = 0;
            }

            float xDelta = dx * (mCropRect.width() / r.width());
            float yDelta = dy * (mCropRect.height() / r.height());
            growBy((((edge & GROW_LEFT_EDGE) != 0) ? -1 : 1) * xDelta, (((edge & GROW_TOP_EDGE) != 0) ? -1 : 1) * yDelta);
        }
    }

    /**
     * Move by.
     *
     * @param dx the dx
     * @param dy the dy
     */
    void moveBy(float dx, float dy) {
        Rect invalRect = new Rect(mDrawRect);

        mCropRect.offset(dx, dy);

        mCropRect.offset(Math.max(0, mImageRect.left - mCropRect.left), Math.max(0, mImageRect.top - mCropRect.top));

        mCropRect.offset(Math.min(0, mImageRect.right - mCropRect.right), Math.min(0, mImageRect.bottom - mCropRect.bottom));

        mDrawRect = computeLayout();
        invalRect.union(mDrawRect);
        invalRect.inset(-10, -10);
        contextView.invalidate();
    }

    /**
     * Grow by.
     *
     * @param dx the dx
     * @param dy the dy
     */
    void growBy(float dx, float dy) {
        if (mMaintainAspectRatio) {
            if (dx != 0) {
                dy = dx / mInitialAspectRatio;
            } else if (dy != 0) {
                dx = dy * mInitialAspectRatio;
            }
        }

        RectF r = new RectF(mCropRect);
        if (dx > 0F && r.width() + 2 * dx > mImageRect.width()) {

            float adjustment = (mImageRect.width() - r.width()) / 2F;
            dx = adjustment;
            if (mMaintainAspectRatio) {
                dy = dx / mInitialAspectRatio;
            }
        }
        if (dy > 0F && r.height() + 2 * dy > mImageRect.height()) {
            float adjustment = (mImageRect.height() - r.height()) / 2F;
            dy = adjustment;
            if (mMaintainAspectRatio) {
                dx = dy * mInitialAspectRatio;
            }
        }

        r.inset(-dx, -dy);

        final float widthCap = 25F;
        if (r.width() < widthCap) {
        	return;
        }
        float heightCap = mMaintainAspectRatio ? (widthCap / mInitialAspectRatio) : widthCap;
        if (r.height() < heightCap) {
        	return;
        }

        if (r.left < mImageRect.left) {
            r.offset(mImageRect.left - r.left, 0F);
        } else if (r.right > mImageRect.right) {
            r.offset(-(r.right - mImageRect.right), 0);
        }
        if (r.top < mImageRect.top) {
            r.offset(0F, mImageRect.top - r.top);
        } else if (r.bottom > mImageRect.bottom) {
            r.offset(0F, -(r.bottom - mImageRect.bottom));
        }

        mCropRect.set(r);
        mDrawRect = computeLayout();
        contextView.invalidate();
    }

    /**
     * Gets the crop rect.
     *
     * @return the crop rect
     */
    public Rect getCropRect() {
        return new Rect((int) mCropRect.left, (int) mCropRect.top, (int) mCropRect.right, (int) mCropRect.bottom);
    }

    /**
     * Compute layout.
     *
     * @return the rect
     */
    private Rect computeLayout() {
		RectF r = new RectF(mCropRect.left, mCropRect.top, mCropRect.right, mCropRect.bottom);
        mMatrix.mapRect(r);
        return new Rect(Math.round(r.left), Math.round(r.top), Math.round(r.right), Math.round(r.bottom));
    }

    /**
     * Invalidate.
     */
    public void invalidate() {
        mDrawRect = computeLayout();
    }

    /**
     * Setup.
     *
     * @param m the m
     * @param imageRect the image rect
     * @param cropRect the crop rect
     * @param circle the circle
     * @param maintainAspectRatio the maintain aspect ratio
     */
    public void setup(Matrix m, Rect imageRect, RectF cropRect, boolean circle, boolean maintainAspectRatio) {
        if (circle) {
            maintainAspectRatio = true;
        }
        mMatrix = new Matrix(m);

        mCropRect = cropRect;
        mImageRect = new RectF(imageRect);
        mMaintainAspectRatio = maintainAspectRatio;
        mCircle = circle;

        mInitialAspectRatio = mCropRect.width() / mCropRect.height();
        mDrawRect = computeLayout();

        mFocusPaint.setARGB(125, 50, 50, 50);
        mNoFocusPaint.setARGB(125, 50, 50, 50);
        mOutlinePaint.setStrokeWidth(3F);
        mOutlinePaint.setStyle(Paint.Style.STROKE);
        mOutlinePaint.setAntiAlias(true);

        mMode = ModifyMode.None;
        init();
    }


}
