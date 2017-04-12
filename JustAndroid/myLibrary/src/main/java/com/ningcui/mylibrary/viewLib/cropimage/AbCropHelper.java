
package com.ningcui.mylibrary.viewLib.cropimage;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.media.FaceDetector;
import android.os.Handler;



import java.util.concurrent.CountDownLatch;

/**
 * 裁剪处理.
 */
public class AbCropHelper {

	private Context context;
	public boolean waitingToPick;
	public boolean saving;
	public AbHighlightView highlightView;
	private Handler handler = new Handler ();
	private AbCropImageView imageView;
	private Bitmap bitmap;


	public AbCropHelper(Context context, AbCropImageView imageView){
		this.context = context;
        this.imageView = imageView;
        this.imageView.setCropHelper(this);
	}

	/**
	 * 图片裁剪.
	 * @param bitmap the bitmap
	 */
	public void initCropBitmap(Bitmap bitmap){
		this.bitmap = bitmap;
		startFaceDetection();
	}

	/**
	 * 开始旋转.
	 * @param degrees the degrees
	 */
	public void startRotate(final float degrees) {
		if (((Activity)context).isFinishing()) {
			return;
		}
		final CountDownLatch latch = new CountDownLatch(1);
		Runnable mRunnable =  new Runnable() {
			public void run() {
                handler.post(new Runnable() {
					public void run() {
						try{
							Matrix m = new Matrix();
							m.setRotate(degrees);
							Bitmap tb = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(),m,false);
							bitmap = tb;
							imageView.resetView(tb);
							if (imageView.highlightViews.size() > 0) {
                                highlightView = imageView.highlightViews.get(0);
                                highlightView.setFocus(true);
							}
						}catch (Exception e) {
						}
						latch.countDown();
					}
				});
				try {
					latch.await();
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
				mRunFaceDetection.run();
			}
		};
		new Thread(new BackgroundJob("", mRunnable, handler)).start();
	}

	/**
	 * 开始图片裁剪框显示.
	 */
	private void startFaceDetection() {
		if (((Activity)context).isFinishing()) {
			return;
		}
		Runnable mRunnable = new Runnable() {
			public void run() {
				final CountDownLatch latch = new CountDownLatch(1);
				final Bitmap b = bitmap;
                handler.post(new Runnable() {
					public void run() {
						if (b != bitmap && b != null) {
							imageView.setImageBitmapResetBase(b, true);
							bitmap.recycle();
							bitmap = b;
						}
						if (imageView.getScale() == 1.0f) {
							imageView.center(true, true);
						}
						latch.countDown();
					}
				});
				try {
					latch.await();
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
				mRunFaceDetection.run();
			}
		};
		new Thread(new BackgroundJob("", mRunnable, handler)).start();
	}

    /**
     * 获取裁剪好的bitmap
     * @param width
     * @param height
     * @return
     */
    public Bitmap getCropBitmap(int width,int height){
        final Bitmap bmp = cropBitmap(bitmap,width,height);
        imageView.highlightViews.clear();
        return bmp;
    }

	/**
	 * 取消裁剪.
	 */
	public void cancelCrop(){
		imageView.highlightViews.clear();
		imageView.invalidate();
	}

    /**
     * 保存Bitmap
     * @param bitmap
     * @return
     */
	private Bitmap cropBitmap(Bitmap bitmap,int width,int height) {
		if (saving)
			return bitmap;

		if (highlightView == null) {
			return bitmap;
		}

		saving = true;

		Rect rect = highlightView.getCropRect();
		Bitmap croppedImage = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
		Canvas canvas = new Canvas(croppedImage);
		Rect dstRect = new Rect(0, 0, width, height);
		canvas.drawBitmap(bitmap, rect, dstRect, null);
		return croppedImage;
	}

	Runnable mRunFaceDetection = new Runnable() {
		float mScale = 1F;
		Matrix mImageMatrix;
		FaceDetector.Face[] mFaces = new FaceDetector.Face[3];
		int mNumFaces;

		private void handleFace(FaceDetector.Face f) {
			PointF midPoint = new PointF();

			int r = ((int) (f.eyesDistance() * mScale)) * 2;
			f.getMidPoint(midPoint);
			midPoint.x *= mScale;
			midPoint.y *= mScale;

			int midX = (int) midPoint.x;
			int midY = (int) midPoint.y;

			AbHighlightView hv = new AbHighlightView(imageView);

			int width = bitmap.getWidth();
			int height = bitmap.getHeight();

			Rect imageRect = new Rect(0, 0, width, height);

			RectF faceRect = new RectF(midX, midY, midX, midY);
			faceRect.inset(-r, -r);
			if (faceRect.left < 0) {
				faceRect.inset(-faceRect.left, -faceRect.left);
			}

			if (faceRect.top < 0) {
				faceRect.inset(-faceRect.top, -faceRect.top);
			}

			if (faceRect.right > imageRect.right) {
				faceRect.inset(faceRect.right - imageRect.right, faceRect.right - imageRect.right);
			}

			if (faceRect.bottom > imageRect.bottom) {
				faceRect.inset(faceRect.bottom - imageRect.bottom, faceRect.bottom - imageRect.bottom);
			}

			hv.setup(mImageMatrix, imageRect, faceRect, false, true);

			imageView.add(hv);
		}

		// 默认的框子.
		private void makeDefault() {
			AbHighlightView hv = new AbHighlightView(imageView);

			int width = bitmap.getWidth();
			int height = bitmap.getHeight();

			Rect imageRect = new Rect(0, 0, width, height);

			// make the default size about 4/5 of the width or height
			int cropWidth = Math.min(width, height) * 4 / 5;
			int cropHeight = cropWidth;

			int x = (width - cropWidth) / 2;
			int y = (height - cropHeight) / 2;

			RectF cropRect = new RectF(x, y, x + cropWidth, y + cropHeight);
			hv.setup(mImageMatrix, imageRect, cropRect, false, true);
			imageView.add(hv);
		}

		// Scale the image down for faster face detection.
		private Bitmap prepareBitmap() {
			if (bitmap == null) {
				return null;
			}

			// 256 pixels wide is enough.
			// CR: F => f (or change all f to F).
			if (bitmap.getWidth() > 256) {
				mScale = 256.0F / bitmap.getWidth();
			}
			Matrix matrix = new Matrix();
			matrix.setScale(mScale, mScale);
			Bitmap faceBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
			return faceBitmap;
		}

		public void run() {
			mImageMatrix = imageView.getImageMatrix();
			Bitmap faceBitmap = prepareBitmap();

			mScale = 1.0F / mScale;
			if (faceBitmap != null) {
				FaceDetector detector = new FaceDetector(faceBitmap.getWidth(), faceBitmap.getHeight(), mFaces.length);
				mNumFaces = detector.findFaces(faceBitmap, mFaces);
			}

			if (faceBitmap != null && faceBitmap != bitmap) {
				faceBitmap.recycle();
			}

			handler.post(new Runnable() {
				public void run() {
					waitingToPick = mNumFaces > 1;
					makeDefault();
					imageView.invalidate();
					if (imageView.highlightViews.size() > 0) {
                        highlightView = imageView.highlightViews.get(0);
                        highlightView.setFocus(true);
					}

					if (mNumFaces > 1) {
					}
				}
			});
		}
	};

	/**
	 * The Class BackgroundJob.
	 */
	public class BackgroundJob implements Runnable{

		private String message;
		private Runnable mJob;
		private Handler mHandler;

		public BackgroundJob(String m, Runnable job, Handler handler){
			message = m;
			mJob = job;
			mHandler = handler;
		}

		public void run(){
			final CountDownLatch latch = new CountDownLatch(1);
			mHandler.post(new Runnable() {
				public void run() {
					latch.countDown();
				}
			});
			try {
				latch.await();
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
			try {
				mJob.run();
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
