package com.ningcui.mylibrary.app.camera;

import java.io.IOException;

import android.content.Context;
import android.graphics.Point;
import android.graphics.Rect;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.util.Log;
import android.view.SurfaceHolder;

/**
 *
 *
 *@author litingzhe
 *@time 2017/4/11 下午3:32
 */
public final class CameraManager {

	private static final String TAG = CameraManager.class.getSimpleName();

	private static CameraManager cameraManager;
	private final CameraConfigurationManager configManager;

	private Camera camera;
	private Rect framingRect;

	private boolean initialized;
	private boolean previewing = false;
	private Camera.AutoFocusCallback autoFocusCallback;
	private Camera.PreviewCallback previewCallback;
	private Camera.PictureCallback pictureCallback;

	public static void init(Context context) {
		if (cameraManager == null) {
			cameraManager = new CameraManager(context);
		}
	}

	public static CameraManager get() {
		return cameraManager;
	}

	private CameraManager(Context context) {
		this.configManager = new CameraConfigurationManager(context);
	}

	/**
	 * 打开相机
	 * 
	 * @param holder
	 * @param cameraID
	 * @throws IOException
	 */
	public void openDriver(SurfaceHolder holder, int cameraID)
			throws IOException {
		if (camera == null) {
			camera = Camera.open(cameraID);
			if (camera == null) {
				throw new IOException();
			}
			if(holder!=null){
				camera.setPreviewDisplay(holder);
			}

			//
			if (Config.orientation == 1) {
				camera.setDisplayOrientation(90);
			}

			if (!initialized) {
				configManager.initFromCameraParameters(camera);
				initialized = true;
			}

			// 设置预览参数
			configManager.setDesiredCameraParameters(camera,cameraID);
			
		}
	}

	/**
	 * 关闭
	 */
	public void closeDriver() {
		if (camera != null) {
			camera.setPreviewCallback(null);
			camera.release();
			camera = null;
		}
	}

	/**
	 * 开始预览
	 */
	public void startPreview() {
		if (camera != null && !previewing) {
			Log.e("Camera", "相机开始预览");
			if(previewCallback!=null){
				camera.setPreviewCallback(previewCallback);
			}
			camera.startPreview();
			previewing = true;
			if(Config.focusMode==0){
				requestAutoFocus();
			}else{
				camera.cancelAutoFocus();
			}
		}else{
			if(Config.focusMode == 0) {
				this.requestAutoFocus();
			}
		}
	}

	/**
	 * 停止预览
	 */
	public void stopPreview() {
		if (camera != null && previewing) {
			Log.e("Camera", "相机停止预览");
			camera.setPreviewCallback(null);
			camera.stopPreview();
			previewing = false;
		}
	}

	/**
	 * 开始对焦
	 */
	public void requestAutoFocus() {
		try {
			if (camera != null && previewing) {
				camera.autoFocus(autoFocusCallback);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Rect getFramingRect() {
		// 1280x720 Point(864, 480)

		// 身份证是80x55
		Point cameraResolution = configManager.getCameraResolution();
		if (camera == null) {
			return null;
		}
		float b = 85.0f / 55;
		int x;
		int y;
		//竖屏
		if (Config.orientation == 1) {
			if(cameraResolution.x < cameraResolution.y){
				x = cameraResolution.x;
				y = cameraResolution.y;
			}else{
				x = cameraResolution.y;
				y = cameraResolution.x;
			}
			
			

		}else{
			if(cameraResolution.x > cameraResolution.y){
				x = cameraResolution.x;
				y = cameraResolution.y;
			}else{
				x = cameraResolution.y;
				y = cameraResolution.x;
			}
		}
		
		float height = y*8/10;
		float width = height*b;
		float leftOffset = (x - width) / 2.0f;
		float topOffset = (y - height) / 2.0f;
		framingRect = new Rect((int) leftOffset, (int) topOffset,
				(int) (leftOffset + width), (int) (topOffset + height));
		
		
		return framingRect;
	}
	
	/**
	 * 
	 * 获取选定窗口.
	 * @return
	 */
	public Rect getTargetRect() {
		return framingRect;
	}

	/**
	 * 拍照
	 */
	public void takePicture() {
		camera.takePicture(null, null, this.pictureCallback);
	}
	
	/**
	 * 闪光灯
	 */
	public void toogleFlash() {
		Parameters parameters = camera.getParameters();
		if(parameters.getFlashMode().equals(Parameters.FLASH_MODE_OFF)){
			parameters.setFlashMode(Parameters.FLASH_MODE_TORCH);
		}else{
			parameters.setFlashMode(Parameters.FLASH_MODE_OFF);
		}
		camera.setParameters(parameters);
	}

	public Camera getCamera() {
		if (camera != null) {
			return camera;
		}
		return null;
	}

	public CameraConfigurationManager getConfigManager() {
		return this.configManager;
	}

	public Point getPreviewSize() {
		return configManager.getCameraResolution();
	}
	
	public Point getPictureSize() {
		return configManager.getPictureResolution();
	}
	
	public Point getScreenResolution() {
		return configManager.getScreenResolution();
	}
	
	public Camera.AutoFocusCallback getAutoFocusCallback() {
		return autoFocusCallback;
	}

	public void setAutoFocusCallback(Camera.AutoFocusCallback autoFocusCallback) {
		this.autoFocusCallback = autoFocusCallback;
	}

	public Camera.PreviewCallback getPreviewCallback() {
		return previewCallback;
	}

	public void setPreviewCallback(Camera.PreviewCallback previewCallback) {
		this.previewCallback = previewCallback;
	}
	
	public Camera.PictureCallback getPictureCallback() {
		return pictureCallback;
	}

	public void setPictureCallback(Camera.PictureCallback pictureCallback) {
		this.pictureCallback = pictureCallback;
	}

}
