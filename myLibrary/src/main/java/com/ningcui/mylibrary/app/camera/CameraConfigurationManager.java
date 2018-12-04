package com.ningcui.mylibrary.app.camera;


import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import android.content.Context;
import android.graphics.ImageFormat;
import android.graphics.Point;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.hardware.Camera.Size;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;

/**
 *
 *
 *@author litingzhe
 *@time 2017/4/11 下午3:30
 */

final class CameraConfigurationManager {

	private static final String TAG = CameraConfigurationManager.class.getSimpleName();

	private static final int TEN_DESIRED_ZOOM = 27;
	private static final int DESIRED_SHARPNESS = 30;

	private static final Pattern COMMA_PATTERN = Pattern.compile(",");

	private final Context context;
	private Point screenResolution;
	private Point cameraResolution;
	private Point pictureResolution;

	CameraConfigurationManager(Context context) {
		this.context = context;
	}

	/**
	 * 初始化相机参数. cameraResolution，screenResolution
	 */
	void initFromCameraParameters(Camera camera) {
		Parameters parameters = camera.getParameters();
		WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		Display display = manager.getDefaultDisplay();
		screenResolution = new Point(display.getWidth(), display.getHeight());
		// preview size is always something like 480*320, other 320*480
		Point screenResolutionForCamera = new Point();
		screenResolutionForCamera.x = screenResolution.x;
		screenResolutionForCamera.y = screenResolution.y;
		// preview size is always something like 480*320, other 320*480
		//
		if (Config.orientation == 1) {
			if (screenResolution.x < screenResolution.y) {
				screenResolutionForCamera.x = screenResolution.y;
				screenResolutionForCamera.y = screenResolution.x;
			}
		}

		Log.d(TAG, "Screen resolution: " + screenResolutionForCamera);
		initCameraResolution(parameters,screenResolutionForCamera);
		
	}

	/**
	 * 设置相机参数
	 * 
	 * @param camera
	 */
	void setDesiredCameraParameters(Camera camera,int cameraID) {
		Parameters parameters = camera.getParameters();
		parameters.setPreviewSize(cameraResolution.x, cameraResolution.y);
		parameters.setPictureSize(pictureResolution.x, pictureResolution.y);
		Size size = parameters.getPreviewSize();
		Log.e(TAG, "camera default previewSize: " + size.width + ","+ size.height);
		
		Size size1 = parameters.getPictureSize();
		Log.e(TAG, "camera default pictureSize: " + size1.width + ","+ size1.height);
		
		parameters.setPictureFormat(ImageFormat.JPEG);
		//parameters.setJpegQuality(85);
		parameters.setPreviewFormat(ImageFormat.NV21);
		Log.e(TAG, "camera setPreviewSize: " + cameraResolution.x + ","+ cameraResolution.y);
		if(cameraID==0){
			//对焦模式
            if(Config.focusMode==0){
                parameters.setFocusMode(Parameters.FLASH_MODE_AUTO);
            }else{
                parameters.setFocusMode(Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
            }

		}

		parameters.setZoom(0);
		setFlash(camera,false);
		//setZoom(parameters);
		camera.setParameters(parameters);
	}

	private void initCameraResolution(Parameters parameters,Point screenResolution) {
		List<Size> list = parameters.getSupportedPreviewSizes();
		List<Size> list2 = parameters.getSupportedPictureSizes();
		for(Size size: list){
			Log.d(TAG, "camera preview-size-values: " +size.width+"x"+size.height);
		}
		
		cameraResolution = findBestPreviewSizeValue(list,screenResolution);
        pictureResolution = findBestPictureSizeValue(list2,screenResolution);

		Log.e(TAG, "camera best preview-size-values: " + cameraResolution.x+ "," + cameraResolution.y);
		Log.e(TAG, "camera best picture-size-values: " + pictureResolution.x+ "," + pictureResolution.y);
	}

	private Point findBestPreviewSizeValue(
			List<Size> list, Point screenResolution) {
		int bestX = 0;
		int bestY = 0;
		int diff = Integer.MAX_VALUE;
		for (Size previewSize : list) {
			int newX = previewSize.width;
			int newY = previewSize.height;

			int newDiff = Math.abs(newX - screenResolution.x) + Math.abs(newY - screenResolution.y);
			if (newDiff == 0) {
				bestX = newX;
				bestY = newY;
				break;
			} else if (newDiff < diff) {
				bestX = newX;
				bestY = newY;
				diff = newDiff;
			}
		}
		if (bestX > 0 && bestY > 0) {
			return new Point(bestX, bestY);
		}
		return null;
	}

	/**
	 * 比例最相近的最大分辨率
	 * @param list
	 * @param screenResolution
     * @return
     */
	private Point findBestPictureSizeValue(
			List<Size> list, Point screenResolution) {

        //比预览的大1倍
        screenResolution = new Point(screenResolution.x*2,screenResolution.y*2);

        int bestX = 0;
        int bestY = 0;
        int diff = Integer.MAX_VALUE;
        for (Size previewSize : list) {
            int newX = previewSize.width;
            int newY = previewSize.height;

            int newDiff = Math.abs(newX - screenResolution.x) + Math.abs(newY - screenResolution.y);
            if (newDiff == 0) {
                bestX = newX;
                bestY = newY;
                break;
            } else if (newDiff < diff) {
                bestX = newX;
                bestY = newY;
                diff = newDiff;
            }
        }
        if (bestX > 0 && bestY > 0) {
            return new Point(bestX, bestY);
        }
        return null;
	}

	private static int findBestMotZoomValue(CharSequence stringValues,int tenDesiredZoom) {
		int tenBestValue = 0;
		for (String stringValue : COMMA_PATTERN.split(stringValues)) {
			stringValue = stringValue.trim();
			double value;
			try {
				value = Double.parseDouble(stringValue);
			} catch (NumberFormatException nfe) {
				return tenDesiredZoom;
			}
			int tenValue = (int) (10.0 * value);
			if (Math.abs(tenDesiredZoom - value) < Math.abs(tenDesiredZoom
					- tenBestValue)) {
				tenBestValue = tenValue;
			}
		}
		return tenBestValue;
	}

	/**
	 * 
	 * 设置闪光灯.
	 */
	private void setFlash(Camera camera,boolean open) {
		Parameters parameters = camera.getParameters();
		if(open){
			parameters.setFlashMode(Parameters.FLASH_MODE_TORCH);
		}else{
			parameters.setFlashMode(Parameters.FLASH_MODE_OFF);
		}
	}

	/**
	 * 
	 * 设置缩放.
	 * @param parameters
	 */
	private void setZoom(Parameters parameters) {

		String zoomSupportedString = parameters.get("zoom-supported");
		if (zoomSupportedString != null
				&& !Boolean.parseBoolean(zoomSupportedString)) {
			return;
		}

		int tenDesiredZoom = TEN_DESIRED_ZOOM;

		String maxZoomString = parameters.get("max-zoom");
		if (maxZoomString != null) {
			try {
				int tenMaxZoom = (int) (10.0 * Double
						.parseDouble(maxZoomString));
				if (tenDesiredZoom > tenMaxZoom) {
					tenDesiredZoom = tenMaxZoom;
				}
			} catch (NumberFormatException nfe) {
				Log.w(TAG, "Bad max-zoom: " + maxZoomString);
			}
		}

		String takingPictureZoomMaxString = parameters
				.get("taking-picture-zoom-max");
		if (takingPictureZoomMaxString != null) {
			try {
				int tenMaxZoom = Integer.parseInt(takingPictureZoomMaxString);
				if (tenDesiredZoom > tenMaxZoom) {
					tenDesiredZoom = tenMaxZoom;
				}
			} catch (NumberFormatException nfe) {
				Log.w(TAG, "Bad taking-picture-zoom-max: "
						+ takingPictureZoomMaxString);
			}
		}

		String motZoomValuesString = parameters.get("mot-zoom-values");
		if (motZoomValuesString != null) {
			tenDesiredZoom = findBestMotZoomValue(motZoomValuesString,
					tenDesiredZoom);
		}

		String motZoomStepString = parameters.get("mot-zoom-step");
		if (motZoomStepString != null) {
			try {
				double motZoomStep = Double.parseDouble(motZoomStepString
						.trim());
				int tenZoomStep = (int) (10.0 * motZoomStep);
				if (tenZoomStep > 1) {
					tenDesiredZoom -= tenDesiredZoom % tenZoomStep;
				}
			} catch (NumberFormatException nfe) {
				// continue
			}
		}

		if (maxZoomString != null || motZoomValuesString != null) {
			parameters.set("zoom", String.valueOf(tenDesiredZoom / 10.0));
		}

		if (takingPictureZoomMaxString != null) {
			parameters.set("taking-picture-zoom", tenDesiredZoom);
		}
	}

	public Point getCameraResolution() {
		return cameraResolution;
	}

	public Point getScreenResolution() {
		return screenResolution;
	}

	public Point getPictureResolution() {
		return pictureResolution;
	}
}
