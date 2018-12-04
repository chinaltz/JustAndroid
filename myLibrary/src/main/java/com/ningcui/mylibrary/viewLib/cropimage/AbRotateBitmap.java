package com.ningcui.mylibrary.viewLib.cropimage;

import android.graphics.Bitmap;
import android.graphics.Matrix;

public class AbRotateBitmap {
    
    private Bitmap bitmap;
    
    private int rotation;
    
    public AbRotateBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
        this.rotation = 0;
    }
    
    public AbRotateBitmap(Bitmap bitmap, int rotation) {
        this.bitmap = bitmap;
        this.rotation = rotation % 360;
    }

    public void setRotation(int rotation) {
        this.rotation = rotation;
    }

    public int getRotation() {
        return rotation;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public Matrix getRotateMatrix() {
        Matrix matrix = new Matrix();
        if (rotation != 0) {
            int cx = bitmap.getWidth() / 2;
            int cy = bitmap.getHeight() / 2;
            matrix.preTranslate(-cx, -cy);
            matrix.postRotate(rotation);
            matrix.postTranslate(getWidth() / 2, getHeight() / 2);
        }
        return matrix;
    }

    public boolean isOrientationChanged() {
        return (rotation / 90) % 2 != 0;
    }

    public int getHeight() {
        if (isOrientationChanged()) {
            return bitmap.getWidth();
        } else {
            return bitmap.getHeight();
        }
    }

    public int getWidth() {
        if (isOrientationChanged()) {
            return bitmap.getHeight();
        } else {
            return bitmap.getWidth();
        }
    }

    public void recycle() {
        if (bitmap != null) {
            bitmap.recycle();
            bitmap = null;
        }
    }
}
