package com.ningcui.mylibrary.utiils;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ImageFormat;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader.TileMode;
import android.graphics.YuvImage;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.View.MeasureSpec;
import android.widget.ImageView;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * Copyright 李挺哲
 * 创建人：litingzhe
 * 邮箱：453971498@qq.com
 * Created by litingzhe on 2017/4/11 下午3:23.
 * Info 图片工具类
 */

public class AbImageUtil {
	
	private final static String TAG = "AbImageUtil";

	/** 图片处理：裁剪. */
	public static final int CUTIMG = 0;

	/** 图片处理：缩放. */
	public static final int SCALEIMG = 1;

	/** 图片处理：不处理. */
	public static final int ORIGINALIMG = 2;

	/** 图片最大宽度. */
	public static final int MAX_WIDTH = 4096/2;

	/** 图片最大高度. */
	public static final int MAX_HEIGHT = 4096/2;





//	/**
//	 * 保存方法
//	 */
//	public static String saveBitmapToCache(Bitmap bitmap, String picName) {
//
//		File file = AbFileUtil.getCacheDir("scale");
//
//		File f = new File(file.getAbsolutePath(), picName);
//		if (f.exists()) {
//			f.delete();
//		}
//		try {
//			FileOutputStream out = new FileOutputStream(f);
//			bitmap.compress(Bitmap.CompressFormat.PNG, 80, out);
//			out.flush();
//			out.close();
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//
//		return f.getAbsolutePath();
//	}

    /**
     * 获取原图.
     * @param file File对象
     * @return Bitmap 图片
     */
    public static Bitmap getBitmap(File file) {
        Bitmap resizeBmp = null;
        try {
            resizeBmp = BitmapFactory.decodeFile(file.getPath());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resizeBmp;
    }

	/**
	 * 从互联网上获取原始大小图片.
	 * @param url 要下载文件的网络地址
	 * @return Bitmap 新图片
	 */
	public static Bitmap getBitmap(String url) {
        return getBitmap(url,-1,-1);
	}
	
	/**
	 * 从互联网上获取指定大小的图片.
	 * @param url
	 *            要下载文件的网络地址
	 * @param desiredWidth
	 *            新图片的宽
	 * @param desiredHeight
	 *            新图片的高
	 * @return Bitmap 新图片
	 */
	public static Bitmap getBitmap(String url,int desiredWidth, int desiredHeight) {
		Bitmap resizeBmp = null;
		URLConnection con = null;
		InputStream is = null;
		try {
			URL imageURL = new URL(url);
			con = imageURL.openConnection();
			con.setDoInput(true);
			con.connect();
			is = con.getInputStream();
            resizeBmp = getBitmap(is,desiredWidth,desiredHeight);
		} catch (Exception e) {
			e.printStackTrace();
			AbLogUtil.d(AbImageUtil.class, "" + e.getMessage());
		} finally {
			try {
				if (is != null) {
					is.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return resizeBmp;
	}
	
	/**
	 * 从流中获取指定大小的图片（压缩）.
	 * @param inputStream
	 * @param desiredWidth
	 * @param desiredHeight
	 * @return
	 */
	public static Bitmap getBitmap(InputStream inputStream,int desiredWidth, int desiredHeight) {
        Bitmap resizeBmp = null;
        try {

            if(desiredWidth <= 0 || desiredHeight <= 0){

                BitmapFactory.Options opts = new BitmapFactory.Options();
                // 默认为ARGB_8888.
                opts.inPreferredConfig = Config.RGB_565;

                // 以下两个字段需一起使用：
                // 产生的位图将得到像素空间，如果系统gc，那么将被清空。当像素再次被访问，如果Bitmap已经decode，那么将被自动重新解码
                opts.inPurgeable = true;
                // 位图可以共享一个参考输入数据(inputstream、阵列等)
                opts.inInputShareable = true;
                // 缩放的比例，缩放是很难按准备的比例进行缩放的，通过inSampleSize来进行缩放，其值表明缩放的倍数，SDK中建议其值是2的指数值
                opts.inSampleSize = 1;
                // 创建内存
                opts.inJustDecodeBounds = false;
                // 使图片不抖动
                opts.inDither = false;
                resizeBmp =  BitmapFactory.decodeStream(inputStream,null, opts);
            }else{
                byte [] data =  AbStreamUtil.stream2Bytes(inputStream);
                resizeBmp = getBitmap(data,desiredWidth,desiredHeight);
            }

        } catch (Exception e) {
            e.printStackTrace();
            AbLogUtil.d(AbImageUtil.class, "" + e.getMessage());
        }
        return resizeBmp;
	}
	
	/**
	 * 从byte数组获取预期大小的图片（压缩）.
	 * @param data
	 * @param desiredWidth
	 * @param desiredHeight
	 * @return
	 */
	public static Bitmap getBitmap(byte [] data,int desiredWidth, int desiredHeight) {
		Bitmap resizeBmp = null;
		try {
			BitmapFactory.Options opts = new BitmapFactory.Options();
			// 设置为true,decodeFile先不创建内存 只获取一些解码边界信息即图片大小信息
			opts.inJustDecodeBounds = true;
			BitmapFactory.decodeByteArray(data, 0, data.length, opts);

			// 获取图片的原始宽度高度
			int srcWidth = opts.outWidth;
			int srcHeight = opts.outHeight;

			//等比获取最大尺寸
			int[] size = resizeToMaxSize(srcWidth, srcHeight, desiredWidth, desiredHeight);
			desiredWidth = size[0];
			desiredHeight = size[1];

			// 默认为ARGB_8888.
			opts.inPreferredConfig = Config.RGB_565;

			// 以下两个字段需一起使用：
			// 产生的位图将得到像素空间，如果系统gc，那么将被清空。当像素再次被访问，如果Bitmap已经decode，那么将被自动重新解码
			opts.inPurgeable = true;
			// 位图可以共享一个参考输入数据(inputstream、阵列等)
			opts.inInputShareable = true;
			// 缩放的比例，缩放是很难按准备的比例进行缩放的，通过inSampleSize来进行缩放，其值表明缩放的倍数，SDK中建议其值是2的指数值
			int sampleSize = computeSampleSize(srcWidth,srcHeight,desiredWidth,desiredHeight);
			opts.inSampleSize = sampleSize;

			// 创建内存
			opts.inJustDecodeBounds = false;
			// 使图片不抖动
			opts.inDither = false;
			resizeBmp =  BitmapFactory.decodeByteArray(data, 0, data.length, opts);

            // 缩放的比例
            float scale = getMinScale(resizeBmp.getWidth(), resizeBmp.getHeight(), desiredWidth, desiredHeight);
            if(scale < 1){
                // 缩小
                resizeBmp = scaleBitmap(resizeBmp, scale);
            }

		} catch (Exception e) {
			e.printStackTrace();
			AbLogUtil.d(AbImageUtil.class, "" + e.getMessage());
		}
		return resizeBmp;
	}


	/**
	 * 缩放图片.
	 * 
	 * @param file
	 *            File对象
	 * @param desiredWidth
	 *            新图片的宽   根据原始图片比例会有不同
	 * @param desiredHeight
	 *            新图片的高   根据原始图片比例会有不同
	 * @return Bitmap 新图片
	 */
	public static Bitmap getScaleBitmap(File file, int desiredWidth, int desiredHeight) {

		BitmapFactory.Options opts = new BitmapFactory.Options();

		int[] size = getBitmapSize(file);

		// 获取图片的原始宽度高度
		int srcWidth = size[0];
		int srcHeight = size[1];
		
		//需要的尺寸重置
		int[] sizeNew = resizeToMaxSize(srcWidth, srcHeight, desiredWidth, desiredHeight);
		desiredWidth = sizeNew[0];
		desiredHeight = sizeNew[1];

		// 默认为ARGB_8888. 必须565  不然有水痕
		opts.inPreferredConfig = Config.RGB_565;

		// 以下两个字段需一起使用：
		// 产生的位图将得到像素空间，如果系统gc，那么将被清空。当像素再次被访问，如果Bitmap已经decode，那么将被自动重新解码
		opts.inPurgeable = true;
		// 位图可以共享一个参考输入数据(inputstream、阵列等)
		opts.inInputShareable = true;
		// 缩放的比例，缩放是很难按准备的比例进行缩放的，通过inSampleSize来进行缩放，其值表明缩放的倍数，SDK中建议其值是2的指数值
        int sampleSize = computeSampleSize(srcWidth,srcHeight,desiredWidth,desiredHeight);
		opts.inSampleSize = sampleSize;
		// 创建内存
		opts.inJustDecodeBounds = false;
		// 使图片不抖动
		opts.inDither = false;

		Bitmap resizeBmp = BitmapFactory.decodeFile(file.getPath(), opts);

		// 缩放的比例
		float scale = getMinScale(resizeBmp.getWidth(), resizeBmp.getHeight(), desiredWidth, desiredHeight);
		if(scale < 1){
			// 缩小
			resizeBmp = scaleBitmap(resizeBmp, scale);
		}

		return resizeBmp;
	}

    /**
     * 获取缩略图
     * @param file
     * @param desiredWidth
     * @param desiredHeight
     * @return
     */
    public static Bitmap getThumbnail(File file,int desiredWidth, int desiredHeight){

        BitmapFactory.Options opts = new BitmapFactory.Options();

        int[] size = getBitmapSize(file);

        // 获取图片的原始宽度高度
        int srcWidth = size[0];
        int srcHeight = size[1];

        //需要的尺寸重置
        int[] sizeNew = resizeToMaxSize(srcWidth, srcHeight, desiredWidth, desiredHeight);
        desiredWidth = sizeNew[0];
        desiredHeight = sizeNew[1];

        // 默认为ARGB_8888. 必须565  不然有水痕
        opts.inPreferredConfig = Config.RGB_565;

        // 以下两个字段需一起使用：
        // 产生的位图将得到像素空间，如果系统gc，那么将被清空。当像素再次被访问，如果Bitmap已经decode，那么将被自动重新解码
        opts.inPurgeable = true;
        // 位图可以共享一个参考输入数据(inputstream、阵列等)
        opts.inInputShareable = true;
        // 缩放的比例，缩放是很难按准备的比例进行缩放的，通过inSampleSize来进行缩放，其值表明缩放的倍数，SDK中建议其值是2的指数值
        //int sampleSize = computeSampleSize(srcWidth,srcHeight,desiredWidth,desiredHeight);

        double wr = (double) srcWidth / desiredWidth;
        double hr = (double) srcHeight / desiredHeight;
        double ratio = Math.min(wr, hr);
        double ratioMax = 1;
        float n = 1.0f;
        //2的倍数的最大值
        while(true){
            if((ratioMax *2) <= ratio){
                ratioMax *= 2;
            }else{
                ratioMax *= 2;
                break;
            }
        }

        while ((n * 2) <= ratioMax) {
            n *= 2;
        }

        opts.inSampleSize = (int)n;
        // 创建内存
        opts.inJustDecodeBounds = false;
        // 使图片不抖动
        opts.inDither = false;

        Bitmap resizeBmp = BitmapFactory.decodeFile(file.getPath(), opts);

        return resizeBmp;

    }

	/**
	 * 缩放图片.
	 *
	 * @param bitmap
	 *            the bitmap
	 * @param desiredWidth
	 *            新图片的宽
	 * @param desiredHeight
	 *            新图片的高
	 * @return Bitmap 新图片
	 */
	public static Bitmap getScaleBitmap(Bitmap bitmap, int desiredWidth, int desiredHeight) {

		if (!checkBitmap(bitmap)) {
			return null;
		}

		// 获得图片的宽高
		int srcWidth = bitmap.getWidth();
		int srcHeight = bitmap.getHeight();

		int[] size = resizeToMaxSize(srcWidth, srcHeight, desiredWidth, desiredHeight);
		desiredWidth = size[0];
		desiredHeight = size[1];

		Bitmap resizeBmp = null;
		float scale = getMinScale(srcWidth, srcHeight, desiredWidth, desiredHeight);
		if(scale < 1){
			// 缩小
			resizeBmp = scaleBitmap(bitmap, scale);
		}
		return resizeBmp;
	}


	/**
	 * 裁剪图片，先缩放后裁剪.
	 *
	 * @param file
	 *            File对象
	 * @param desiredWidth
	 *            新图片的宽
	 * @param desiredHeight
	 *            新图片的高
	 * @return Bitmap 新图片
	 */
	public static Bitmap getCutBitmap(File file, int desiredWidth, int desiredHeight) {

		BitmapFactory.Options opts = new BitmapFactory.Options();

		int[] size = getBitmapSize(file);

		// 获取图片的原始宽度高度
		int srcWidth = size[0];
		int srcHeight = size[1];

		//需要的尺寸重置
		int[] sizeNew = resizeToMaxSize(srcWidth, srcHeight, desiredWidth, desiredHeight);
		desiredWidth = sizeNew[0];
		desiredHeight = sizeNew[1];

		// 默认为ARGB_8888. 必须565  不然有水痕
		opts.inPreferredConfig = Config.RGB_565;

		// 以下两个字段需一起使用：
		// 产生的位图将得到像素空间，如果系统gc，那么将被清空。当像素再次被访问，如果Bitmap已经decode，那么将被自动重新解码
		opts.inPurgeable = true;
		// 位图可以共享一个参考输入数据(inputstream、阵列等)
		opts.inInputShareable = true;
		// 缩放的比例，缩放是很难按准备的比例进行缩放的，通过inSampleSize来进行缩放，其值表明缩放的倍数，SDK中建议其值是2的指数值
        int sampleSize = computeSampleSize(srcWidth,srcHeight,desiredWidth,desiredHeight);
		opts.inSampleSize = sampleSize;
		// 创建内存
		opts.inJustDecodeBounds = false;
		// 使图片不抖动
		opts.inDither = false;
		Bitmap resizeBmp = BitmapFactory.decodeFile(file.getPath(), opts);

        // 缩放的比例,缩小或者放大
        float scale = getMinScale(resizeBmp.getWidth(), resizeBmp.getHeight(), desiredWidth, desiredHeight);
		resizeBmp = scaleBitmap(resizeBmp, scale);

        //超出的裁掉
        if (resizeBmp.getWidth() > desiredWidth || resizeBmp.getHeight() > desiredHeight) {
            resizeBmp  = getCutBitmap(resizeBmp,desiredWidth,desiredHeight);
        }
		return resizeBmp;
	}

	/**
	 * 裁剪图片.
	 * 
	 * @param bitmap
	 *            the bitmap
	 * @param desiredWidth
	 *            新图片的宽
	 * @param desiredHeight
	 *            新图片的高
	 * @return Bitmap 新图片
	 */
	public static Bitmap getCutBitmap(Bitmap bitmap, int desiredWidth, int desiredHeight) {

		if (!checkBitmap(bitmap)) {
			return null;
		}
		
		if (!checkSize(desiredWidth, desiredHeight)) {
			return null;
		}

		Bitmap resizeBmp = null;

		try {
			int width = bitmap.getWidth();
			int height = bitmap.getHeight();

			int offsetX = 0;
			int offsetY = 0;

			if (width > desiredWidth) {
				offsetX = (width - desiredWidth) / 2;
			} else {
				desiredWidth = width;
			}

			if (height > desiredHeight) {
				offsetY = (height - desiredHeight) / 2;
			} else {
				desiredHeight = height;
			}

			resizeBmp = Bitmap.createBitmap(bitmap, offsetX, offsetY, desiredWidth,desiredHeight);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (resizeBmp != bitmap) {
				bitmap.recycle();
			}
		}
		return resizeBmp;
	}
	
	/**
	 * 根据等比例缩放图片.
	 * 
	 * @param bitmap
	 *            the bitmap
	 * @param scale
	 *            比例
	 * @return Bitmap 新图片
	 */
	public static Bitmap scaleBitmap(Bitmap bitmap, float scale) {

		if (!checkBitmap(bitmap)) {
			return null;
		}

		if (scale == 1) {
			return bitmap;
		}

		Bitmap resizeBmp = null;
		try {
			// 获取Bitmap资源的宽和高
			int bmpW = bitmap.getWidth();
			int bmpH = bitmap.getHeight();
			
			// 注意这个Matirx是android.graphics底下的那个
			Matrix matrix = new Matrix();
			// 设置缩放系数，分别为原来的0.8和0.8
			matrix.postScale(scale, scale);
			resizeBmp = Bitmap.createBitmap(bitmap, 0, 0, bmpW, bmpH, matrix, true);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (resizeBmp != bitmap) {
				bitmap.recycle();
			}
		}
		return resizeBmp;
	}
	
	/**
	 * 获取图片尺寸
	 * 
	 * @param file File对象
	 * @return Bitmap 新图片
	 */
	public static int[] getBitmapSize(File file) {
		int[] size = new int[2];
		BitmapFactory.Options opts = new BitmapFactory.Options();
		// 设置为true,decodeFile先不创建内存 只获取一些解码边界信息即图片大小信息
		opts.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(file.getPath(), opts);
		// 获取图片的原始宽度高度
		size[0] = opts.outWidth;
		size[1] = opts.outHeight;
		return size;
	}

	/**
	 * 
	 * 获取缩小的比例.
	 * @param srcWidth
	 * @param srcHeight
	 * @param desiredWidth
	 * @param desiredHeight
	 * @return
	 */
	private static float getMinScale(int srcWidth, int srcHeight, int desiredWidth,
			int desiredHeight) {

        // 缩放的比例
        if(desiredWidth <= 0 || desiredHeight <= 0){
            return 1;
        }

        float scale = 0;
		// 计算缩放比例，宽高的最小比例
		float scaleWidth = (float) desiredWidth / srcWidth;
		float scaleHeight = (float) desiredHeight / srcHeight;
		if (scaleWidth > scaleHeight) {
			scale = scaleWidth;
		} else {
			scale = scaleHeight;
		}
		
		return scale;
	}

	private static int[] resizeToMaxSize(int srcWidth, int srcHeight,
			int desiredWidth, int desiredHeight) {
		int[] size = new int[2];
		if(desiredWidth <= 0){
			desiredWidth = srcWidth;
		}
		if(desiredHeight <= 0){
			desiredHeight = srcHeight;
		}
		if (desiredWidth > MAX_WIDTH) {
			// 重新计算大小
			desiredWidth = MAX_WIDTH;
			float scaleWidth = (float) desiredWidth / srcWidth;
			desiredHeight = (int) (desiredHeight * scaleWidth);
		}

		if (desiredHeight > MAX_HEIGHT) {
			// 重新计算大小
			desiredHeight = MAX_HEIGHT;
			float scaleHeight = (float) desiredHeight / srcHeight;
			desiredWidth = (int) (desiredWidth * scaleHeight);
		}
		size[0] = desiredWidth;
		size[1] = desiredHeight;
		return size;
	}

	private static boolean checkBitmap(Bitmap bitmap) {
		if (bitmap == null) {
			AbLogUtil.e(AbImageUtil.class, "原图Bitmap为空了");
			return false;
		}

		if (bitmap.getWidth() <= 0 || bitmap.getHeight() <= 0) {
			AbLogUtil.e(AbImageUtil.class, "原图Bitmap大小为0");
			return false;
		}
		return true;
	}

	private static boolean checkSize(int desiredWidth, int desiredHeight) {
		if (desiredWidth <= 0 || desiredHeight <= 0) {
			AbLogUtil.e(AbImageUtil.class, "请求Bitmap的宽高参数必须大于0");
			return false;
		}
		return true;
	}

	/**
	 * Drawable转Bitmap.
	 * 
	 * @param drawable
	 *            要转化的Drawable
	 * @return Bitmap
	 */
	public static Bitmap drawableToBitmap(Drawable drawable) {
		Bitmap bitmap = Bitmap
				.createBitmap(
						drawable.getIntrinsicWidth(),
						drawable.getIntrinsicHeight(),
						drawable.getOpacity() != PixelFormat.OPAQUE ? Config.ARGB_8888
								: Config.RGB_565);
		Canvas canvas = new Canvas(bitmap);
		drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
				drawable.getIntrinsicHeight());
		drawable.draw(canvas);
		return bitmap;
	}

	/**
	 * Bitmap对象转换Drawable对象.
	 * 
	 * @param bitmap
	 *            要转化的Bitmap对象
	 * @return Drawable 转化完成的Drawable对象
	 */
	public static Drawable bitmapToDrawable(Bitmap bitmap) {
		BitmapDrawable mBitmapDrawable = null;
		try {
			if (bitmap == null) {
				return null;
			}
			mBitmapDrawable = new BitmapDrawable(bitmap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mBitmapDrawable;
	}

	/**
	 * Bitmap对象转换TransitionDrawable对象.
	 * 
	 * @param bitmap
	 *            要转化的Bitmap对象 imageView.setImageDrawable(td);
	 *            td.startTransition(200);
	 * @return Drawable 转化完成的Drawable对象
	 */
	@Nullable
	public static TransitionDrawable bitmapToTransitionDrawable(Bitmap bitmap) {
		TransitionDrawable mBitmapDrawable = null;
		try {
			if (bitmap == null) {
				return null;
			}
			mBitmapDrawable = new TransitionDrawable(new Drawable[] {
					new ColorDrawable(Color.TRANSPARENT),
					new BitmapDrawable(bitmap) });
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mBitmapDrawable;
	}

	/**
	 * Drawable对象转换TransitionDrawable对象.
	 * 
	 * @param drawable
	 *            要转化的Drawable对象 imageView.setImageDrawable(td);
	 *            td.startTransition(200);
	 * @return Drawable 转化完成的Drawable对象
	 */
	public static TransitionDrawable drawableToTransitionDrawable(Drawable drawable) {
		TransitionDrawable mBitmapDrawable = null;
		try {
			if (drawable == null) {
				return null;
			}
			mBitmapDrawable = new TransitionDrawable(new Drawable[] {
					new ColorDrawable(Color.TRANSPARENT), drawable });
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mBitmapDrawable;
	}

	/**
	 * 将Bitmap转换为byte[].
	 * 
	 * @param bitmap
	 *            the bitmap
	 * @param mCompressFormat
	 *            图片格式 Bitmap.CompressFormat.JPEG,CompressFormat.PNG
	 * @param needRecycle
	 *            是否需要回收
	 * @return byte[] 图片的byte[]
	 */
	public static byte[] bitmap2Bytes(Bitmap bitmap,
			Bitmap.CompressFormat mCompressFormat, final boolean needRecycle) {
		byte[] result = null;
		ByteArrayOutputStream output = null;
		try {
			output = new ByteArrayOutputStream();
			bitmap.compress(mCompressFormat, 70, output);
			result = output.toByteArray();
			if (needRecycle) {
				bitmap.recycle();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (output != null) {
				try {
					output.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return result;
	}

	/**
	 * 获取Bitmap大小.
	 * 
	 * @param bitmap
	 *            the bitmap
	 * @param mCompressFormat
	 *            图片格式 Bitmap.CompressFormat.JPEG,CompressFormat.PNG
	 * @return 图片的大小
	 */
	public static int getByteCount(Bitmap bitmap,
			Bitmap.CompressFormat mCompressFormat) {
		int size = 0;
		ByteArrayOutputStream output = null;
		try {
			output = new ByteArrayOutputStream();
			bitmap.compress(mCompressFormat, 70, output);
			byte[] result = output.toByteArray();
			size = result.length;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (output != null) {
				try {
					output.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return size;
	}

	/**
	 * 将byte[]转换为Bitmap.
	 * 
	 * @param b
	 *            图片格式的byte[]数组
	 * @return bitmap 得到的Bitmap
	 */
	public static Bitmap bytes2Bimap(byte[] b) {
		Bitmap bitmap = null;
		try {
			if (b.length != 0) {
				bitmap = BitmapFactory.decodeByteArray(b, 0, b.length);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bitmap;
	}

	/**
	 * 将ImageView转换为Bitmap.
	 * 
	 * @param view
	 *            要转换为bitmap的View
	 * @return byte[] 图片的byte[]
	 */
	public static Bitmap imageView2Bitmap(ImageView view) {
		Bitmap bitmap = null;
		try {
			bitmap = Bitmap.createBitmap(view.getDrawingCache());
			view.setDrawingCacheEnabled(false);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bitmap;
	}

	/**
	 * 将View转换为Drawable.需要最上层布局为Linearlayout
	 * 
	 * @param view
	 *            要转换为Drawable的View
	 * @return BitmapDrawable Drawable
	 */
	public static Drawable view2Drawable(View view) {
		BitmapDrawable mBitmapDrawable = null;
		try {
			Bitmap newbmp = view2Bitmap(view);
			if (newbmp != null) {
				mBitmapDrawable = new BitmapDrawable(newbmp);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mBitmapDrawable;
	}

	/**
	 * 将View转换为Bitmap.需要最上层布局为Linearlayout
	 * 
	 * @param view
	 *            要转换为bitmap的View
	 * @return byte[] 图片的byte[]
	 */
	public static Bitmap view2Bitmap(View view) {
		Bitmap bitmap = null;
		try {
			if (view != null) {
				view.setDrawingCacheEnabled(true);
				view.measure(
						MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED),
						MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
				view.layout(0, 0, view.getMeasuredWidth(),
						view.getMeasuredHeight());
				view.buildDrawingCache();
				bitmap = view.getDrawingCache();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bitmap;
	}

	/**
	 * 将View转换为byte[].
	 * 
	 * @param view
	 *            要转换为byte[]的View
	 * @param compressFormat
	 *            the compress format
	 * @return byte[] View图片的byte[]
	 */
	public static byte[] view2Bytes(View view,
			Bitmap.CompressFormat compressFormat) {
		byte[] b = null;
		try {
			Bitmap bitmap = AbImageUtil.view2Bitmap(view);
			b = AbImageUtil.bitmap2Bytes(bitmap, compressFormat, true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return b;
	}

	/**
	 * 旋转Bitmap为一定的角度.
	 * 
	 * @param bitmap
	 *            the bitmap
	 * @param degrees
	 *            the degrees
	 * @return the bitmap
	 */
	public static Bitmap rotateBitmap(Bitmap bitmap, float degrees) {
		Bitmap mBitmap = null;
		try {
			Matrix m = new Matrix();
			m.setRotate(degrees % 360);
			mBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
					bitmap.getHeight(), m, false);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mBitmap;
	}

	/**
	 * 旋转Bitmap为一定的角度并四周暗化处理.
	 * 
	 * @param bitmap
	 *            the bitmap
	 * @param degrees
	 *            the degrees
	 * @return the bitmap
	 */
	public static Bitmap rotateBitmapTranslate(Bitmap bitmap, float degrees) {
		Bitmap mBitmap = null;
		int width;
		int height;
		try {
			Matrix matrix = new Matrix();
			if ((degrees / 90) % 2 != 0) {
				width = bitmap.getWidth();
				height = bitmap.getHeight();
			} else {
				width = bitmap.getHeight();
				height = bitmap.getWidth();
			}
			int cx = width / 2;
			int cy = height / 2;
			matrix.preTranslate(-cx, -cy);
			matrix.postRotate(degrees);
			matrix.postTranslate(cx, cy);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mBitmap;
	}

	/**
	 * 转换图片转换成圆形.
	 * 
	 * @param bitmap
	 *            传入Bitmap对象
	 * @return the bitmap
	 */
	public static Bitmap toRoundBitmap(Bitmap bitmap) {
		if (bitmap == null) {
			return null;
		}
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();
		float roundPx;
		float left, top, right, bottom, dst_left, dst_top, dst_right, dst_bottom;
		if (width <= height) {
			roundPx = width / 2;
			top = 0;
			bottom = width;
			left = 0;
			right = width;
			height = width;
			dst_left = 0;
			dst_top = 0;
			dst_right = width;
			dst_bottom = width;
		} else {
			roundPx = height / 2;
			float clip = (width - height) / 2;
			left = clip;
			right = width - clip;
			top = 0;
			bottom = height;
			width = height;
			dst_left = 0;
			dst_top = 0;
			dst_right = height;
			dst_bottom = height;
		}

		Bitmap output = Bitmap.createBitmap(width, height, Config.ARGB_8888);
		Canvas canvas = new Canvas(output);
		final int color = 0xff424242;
		final Paint paint = new Paint();
		final Rect src = new Rect((int) left, (int) top, (int) right,
				(int) bottom);
		final Rect dst = new Rect((int) dst_left, (int) dst_top,
				(int) dst_right, (int) dst_bottom);
		final RectF rectF = new RectF(dst);
		paint.setAntiAlias(true);
		canvas.drawARGB(0, 0, 0, 0);
		paint.setColor(color);
		canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		canvas.drawBitmap(bitmap, src, dst, paint);
		return output;
	}

	/**
	 * 转换图片转换成圆角
	 * @param bitmap
	 * @param roundPx
     * @return
     */
	public static Bitmap toRoundBitmap(Bitmap bitmap,int roundPx) {
		if (bitmap == null) {
			return null;
		}
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();
		float left, top, right, bottom, dst_left, dst_top, dst_right, dst_bottom;
		if (width <= height) {
			top = 0;
			bottom = width;
			left = 0;
			right = width;
			height = width;
			dst_left = 0;
			dst_top = 0;
			dst_right = width;
			dst_bottom = width;
		} else {
			float clip = (width - height) / 2;
			left = clip;
			right = width - clip;
			top = 0;
			bottom = height;
			width = height;
			dst_left = 0;
			dst_top = 0;
			dst_right = height;
			dst_bottom = height;
		}

		Bitmap output = Bitmap.createBitmap(width, height, Config.ARGB_8888);
		Canvas canvas = new Canvas(output);
		final int color = 0xff424242;
		final Paint paint = new Paint();
		final Rect src = new Rect((int) left, (int) top, (int) right,
				(int) bottom);
		final Rect dst = new Rect((int) dst_left, (int) dst_top,
				(int) dst_right, (int) dst_bottom);
		final RectF rectF = new RectF(dst);
		paint.setAntiAlias(true);
		canvas.drawARGB(0, 0, 0, 0);
		paint.setColor(color);
		canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		canvas.drawBitmap(bitmap, src, dst, paint);
		return output;
	}

	/**
	 * 转换图片转换成镜面效果的图片.
	 * 
	 * @param bitmap
	 *            传入Bitmap对象
	 * @return the bitmap
	 */
	public static Bitmap toReflectionBitmap(Bitmap bitmap) {
		if (bitmap == null) {
			return null;
		}

		try {
			int reflectionGap = 1;
			int width = bitmap.getWidth();
			int height = bitmap.getHeight();

			// This will not scale but will flip on the Y axis
			Matrix matrix = new Matrix();
			matrix.preScale(1, -1);

			// Create a Bitmap with the flip matrix applied to it.
			// We only want the bottom half of the image
			Bitmap reflectionImage = Bitmap.createBitmap(bitmap, 0, height / 2,
					width, height / 2, matrix, false);

			// Create a new bitmap with same width but taller to fit
			// reflection
			Bitmap bitmapWithReflection = Bitmap.createBitmap(width,
					(height + height / 2), Config.ARGB_8888);

			// Create a new Canvas with the bitmap that's big enough for
			// the image plus gap plus reflection
			Canvas canvas = new Canvas(bitmapWithReflection);
			// Draw in the original image
			canvas.drawBitmap(bitmap, 0, 0, null);
			// Draw in the gap
			Paint deafaultPaint = new Paint();
			canvas.drawRect(0, height, width, height + reflectionGap,
					deafaultPaint);
			// Draw in the reflection
			canvas.drawBitmap(reflectionImage, 0, height + reflectionGap, null);
			// Create a shader that is a linear gradient that covers the
			// reflection
			Paint paint = new Paint();
			LinearGradient shader = new LinearGradient(0, bitmap.getHeight(),
					0, bitmapWithReflection.getHeight() + reflectionGap,
					0x70ffffff, 0x00ffffff, TileMode.CLAMP);
			// Set the paint to use this shader (linear gradient)
			paint.setShader(shader);
			// Set the Transfer mode to be porter duff and destination in
			paint.setXfermode(new PorterDuffXfermode(Mode.DST_IN));
			// Draw a rectangle using the paint with our linear gradient
			canvas.drawRect(0, height, width, bitmapWithReflection.getHeight()
					+ reflectionGap, paint);

			bitmap = bitmapWithReflection;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bitmap;
	}

	/**
	 * 释放Bitmap对象.
	 * 
	 * @param bitmap
	 *            要释放的Bitmap
	 */
	public static void releaseBitmap(Bitmap bitmap) {
		if (bitmap != null) {
			try {
				if (!bitmap.isRecycled()) {
					AbLogUtil.d(AbImageUtil.class,
							"Bitmap释放" + bitmap.toString());
					bitmap.recycle();
				}
			} catch (Exception e) {
			}
			bitmap = null;
		}
	}

	/**
	 * 释放Bitmap数组.
	 * 
	 * @param bitmaps
	 *            要释放的Bitmap数组
	 */
	public static void releaseBitmapArray(Bitmap[] bitmaps) {
		if (bitmaps != null) {
			try {
				for (Bitmap bitmap : bitmaps) {
					if (bitmap != null && !bitmap.isRecycled()) {
						AbLogUtil.d(AbImageUtil.class,
								"Bitmap释放" + bitmap.toString());
						bitmap.recycle();
					}
				}
			} catch (Exception e) {
			}
		}
	}
	
	/**
	 * 
	 * 将yuv格式转换成灰度bitmap.
	 * @param yuvData
	 * @param width
	 * @param height
	 * @return
	 */
	public Bitmap yuv2GrayBitmap(byte[] yuvData, int width, int height) {
		int[] pixels = new int[width * height];
		byte[] yuv = yuvData;
		int inputOffset = 0;
		for (int y = 0; y < height; y++) {
			int outputOffset = y * width;
			for (int x = 0; x < width; x++) {
				int grey = yuv[inputOffset + x] & 0xff;
				pixels[outputOffset + x] = 0xFF000000 | (grey * 0x00010101);
			}
			inputOffset += width;
		}

		Bitmap bitmap = Bitmap.createBitmap(width, height, Config.ARGB_8888);
		bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
		return bitmap;
	}

	/**
	 * 将yuv格式转换成bitmap
	 * ImageFormat.NV21 || format == ImageFormat.YUY2
	 * @param data yuv 数据
	 * @param width
	 * @param height
	 * @return RGB565 format bitmap
	 */
	public static Bitmap yuv2Bitmap(byte[] data, int width, int height) {
		final YuvImage image = new YuvImage(data, ImageFormat.NV21, width, height, null);
	    ByteArrayOutputStream os = new ByteArrayOutputStream(data.length);
	    if(!image.compressToJpeg(new Rect(0, 0, width, height), 100, os)){
	        return null;
	    }
	    byte[] tmp = os.toByteArray();
	    Bitmap bitmap = BitmapFactory.decodeByteArray(tmp, 0,tmp.length); 
		return bitmap;
	}
		
	/**
	 * 
	 * Yuv
	 * ImageFormat.NV21 || format == ImageFormat.YUY2.
	 * @param data
	 * @param width
	 * @param height
	 * @param rect
	 * @return
	 */
    public static Bitmap cropYuv2Bitmap(byte []data, int width, int height, Rect rect){
		int w = rect.width();
		int h = rect.height();
		int frameSize = width*height;
		int []pixels = new int [w*h];
		byte []yuv = data;
		int yOffset = rect.top*width + rect.left;
		int uvOffset = (rect.top/2)*width + (rect.left/2)*2 + frameSize;
		int y, u, v, k;
		
		for(int i = 0; i < h; ++i){
			int outputOffset = i*w;
			for(int j = 0; j < w; ++j){
				y = (0xff & yuv[yOffset+j])-16;
				
				k = ((j>>1)<<1);
				v = (0xff & yuv[uvOffset+k])-128;
				u = (0xff & yuv[uvOffset+k+1])-128;
				
	            int y1192 = 1192 * y;
                int r = (y1192 + 1634 * v);
                int g = (y1192 - 833 * v - 400 * u);
                int b = (y1192 + 2066 * u);

                if (r < 0) r = 0; else if (r > 262143) r = 262143;
                if (g < 0) g = 0; else if (g > 262143) g = 262143;
                if (b < 0) b = 0; else if (b > 262143) b = 262143;

                pixels[outputOffset+j] = 0xff000000 | ((r << 6) & 0xff0000) | ((g >> 2) & 0xff00) | ((b >> 10) & 0xff);
			}
			yOffset += width;
			if(((rect.top+i) & 1) == 1){ uvOffset+= width; }
		}
		Bitmap bitmap = Bitmap.createBitmap(w, h, Config.ARGB_8888);
		bitmap.setPixels(pixels, 0, w, 0, 0, w, h);
		return bitmap;
    }

	/**
	 * 找到最合适的SampleSize
	 * @param width
	 * @param height
	 * @param desiredWidth
	 * @param desiredHeight
	 * @return
	 */
	private static int computeSampleSize(int width, int height, int desiredWidth, int desiredHeight) {
		double wr = (double) width / desiredWidth;
		double hr = (double) height / desiredHeight;
		double ratio = Math.min(wr, hr);
        float n = 1.0f;
        while ((n * 2) <= ratio) {
            n *= 2;
        }
		return (int) n;
	}


	/**
	 * The main method.
	 * 
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		// System.out.println(getHashCode(""));
	}

}
