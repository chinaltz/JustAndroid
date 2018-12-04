package com.litingzhe.justandroid.ui.mapView.AMapUtils;

import android.content.Context;

import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;

/**
 * Created by litingzhe on 2017/1/12.
 */

public class LocationProvider {
    public AMapLocationClient getLocationClient() {
        return locationClient;
    }

    public void setLocationClient(AMapLocationClient locationClient) {
        this.locationClient = locationClient;
    }

    private AMapLocationClient locationClient = null;
    //    private LocationClientOption mOption,DIYoption;
    private Object objLock = new Object();
    private AMapLocationClientOption DIYoption;

    /***
     * @param locationContext
     */
    public LocationProvider(Context locationContext) {
        synchronized (objLock) {
            if (locationClient == null) {
                locationClient = new AMapLocationClient(locationContext);
                locationClient.setLocationOption(getDefaultOption());
            }
        }
    }

    /**
     * 默认的定位参数
     *
     * @author hongming.wangLocationProvider
     * @since 2.8.0
     */
    public AMapLocationClientOption getDefaultOption() {
        AMapLocationClientOption mOption = new AMapLocationClientOption();
        mOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);//可选，设置定位模式，可选的模式有高精度、仅设备、仅网络。默认为高精度模式
        mOption.setGpsFirst(true);//可选，设置是否gps优先，只在高精度模式下有效。默认关闭
        mOption.setHttpTimeOut(20000);//可选，设置网络请求超时时间。默认为30秒。在仅设备模式下无效
        mOption.setInterval(2000);//可选，设置定位间隔。默认为2秒
        mOption.setNeedAddress(true);//可选，设置是否返回逆地理地址信息。默认是true
        mOption.setOnceLocation(false);//可选，设置是否单次定位。默认是false
        mOption.setOnceLocationLatest(false);//可选，设置是否等待wifi刷新，默认为false.如果设置为true,会自动变为单次定位，持续定位时不要使用
        AMapLocationClientOption.setLocationProtocol(AMapLocationClientOption.AMapLocationProtocol.HTTP);//可选， 设置网络请求的协议。可选HTTP或者HTTPS。默认为HTTP
        mOption.setSensorEnable(false);//可选，设置是否使用传感器。默认是false
        mOption.setWifiScan(true); //可选，设置是否开启wifi扫描。默认为true，如果设置为false会同时停止主动刷新，停止以后完全依赖于系统刷新，定位位置可能存在误差
        mOption.setLocationCacheEnable(true); //可选，设置是否使用缓存定位，默认为true
        return mOption;
    }


    /**
     * 销毁定位
     *
     * @author hongming.wang
     * @since 2.8.0
     */
    public void destroyLocation() {
        if (null != locationClient) {
            /**
             * 如果AMapLocationClient是在当前Activity实例化的，
             * 在Activity的onDestroy中一定要执行AMapLocationClient的onDestroy
             */
            locationClient.onDestroy();
            locationClient = null;


        }
    }


    public boolean registerListener(AMapLocationListener locationListener) {
        boolean isSuccess = false;
        if (locationListener != null) {
            locationClient.setLocationListener(locationListener);
            isSuccess = true;
        }
        return isSuccess;
    }

    public void unregisterListener(AMapLocationListener locationListener) {
        if (locationListener != null) {
            locationClient.unRegisterLocationListener(locationListener);
        }
    }


    /***
     * @param option
     * @return isSuccessSetOption
     */
    public boolean setLocationOption(AMapLocationClientOption option) {
        boolean isSuccess = false;
        if (option != null) {
            if (locationClient.isStarted())
                locationClient.stopLocation();
            DIYoption = option;
            locationClient.setLocationOption(option);
            isSuccess = true;
        }
        return isSuccess;
    }


    /**
     * 开始定位
     *
     * @author hongming.wang
     * @since 2.8.0
     */
    public void start() {
        synchronized (objLock) {
            if (locationClient != null && !locationClient.isStarted()) {
                locationClient.startLocation();
            }
        }
    }


    /**
     * 停止定位
     *
     * @author hongming.wang
     * @since 2.8.0
     */
    public void stop() {
        synchronized (objLock) {
            if (locationClient != null && locationClient.isStarted()) {
                locationClient.stopLocation();
            }
        }
    }


}
