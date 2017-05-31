package com.litingzhe.justandroid.ui.mapView;

import android.Manifest;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdate;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.navi.model.NaviLatLng;
import com.litingzhe.justandroid.R;
import com.litingzhe.justandroid.ui.mapView.AMapUtils.AMapNavBaseActivity;
import com.litingzhe.justandroid.ui.mapView.AMapUtils.LocationProvider;
import com.litingzhe.justandroid.ui.mapView.adapter.HorizontalListViewAdapter;
import com.ningcui.mylibrary.app.base.AbBaseActivity;
import com.ningcui.mylibrary.utiils.AbStrUtil;
import com.ningcui.mylibrary.viewLib.HorizontalListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;


/**
 * Copyright 李挺哲
 * 创建人：litingzhe
 * 邮箱：453971498@qq.com
 * 附近停车场
 * Created by litingzhe on 2017/1/3.
 */

public class MapActivity extends AbBaseActivity implements EasyPermissions.PermissionCallbacks {

    @BindView(R.id.nav_back)
    LinearLayout navBack;
    @BindView(R.id.nav_title)
    TextView navTitle;
    @BindView(R.id.nav_right_icon)
    ImageView navRightIcon;
    @BindView(R.id.nav_right)
    LinearLayout navRight;
    @BindView(R.id.horizontalListView)
    HorizontalListView horizontalListView;
    @BindView(R.id.mapView)
    MapView mapView;
    @BindView(R.id.searchEditText)
    EditText searchEditText;
    @BindView(R.id.locationButton)
    Button locationButton;
    @BindView(R.id.parkAddressText)
    TextView parkAddressText;
    @BindView(R.id.canUseParkNumberText)
    TextView canUseParkNumberText;
    @BindView(R.id.distanceText)
    TextView distanceText;
    @BindView(R.id.perForTimeText)
    TextView perForTimeText;
    @BindView(R.id.supportOnlineText)
    TextView supportOnlineText;
    @BindView(R.id.parkDetialLineraLayout)
    LinearLayout parkDetialLineraLayout;
    @BindView(R.id.goHereButton)
    Button goHereButton;
    @BindView(R.id.paopaoView)
    LinearLayout paopaoView;
    private HorizontalListViewAdapter hListViewAdapter;
    private AMap aMap;


    private LocationProvider locationProvider;

    private boolean isFirstInitMap;


    public boolean isLocationSuccess = false;

    private LatLng loctionLatLng;
    /**
     * 定位监听
     */
    AMapLocationListener locationListener = new AMapLocationListener() {


        @Override
        public void onLocationChanged(AMapLocation loc) {

            if (null != loc) {
                //解析定位结果

                if (loc.getErrorCode() == 0) {
                    if (AbStrUtil.isEmpty(loc.getCity())) {
                        isLocationSuccess = false;
                        Toast.makeText(getApplicationContext(), "定位失败", Toast.LENGTH_SHORT).show();
                        locationProvider.stop();

                    } else {
                        isLocationSuccess = true;
                        aMap.clear();
                        loctionLatLng = new LatLng(loc.getLatitude(), loc.getLongitude());
                        addLoactionMark(new LatLng(loc.getLatitude(), loc.getLongitude()));
                        aMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(loc.getLatitude(), loc.getLongitude()), 14));
                        locationProvider.stop();

                    }

                } else {

                    Toast.makeText(getApplicationContext(), "定位失败", Toast.LENGTH_SHORT).show();
                    locationProvider.stop();
                }


            }
        }
    };

    /**
     * 请求 定位 权限码
     */
    public static final int REQUEST_LOCATION_PERM = 121;
    private MarkerOptions markerOption;
    private ArrayList hotAreaList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activitty_nearbypark);
        ButterKnife.bind(this);
        isFirstInitMap = true;
        mContext = this;
        navTitle.setText("地图相关导航");
        String[] titles = {"新街口", "夫子庙", "百家湖", "河西万达"};
        hotAreaList = new ArrayList();
        LatLng xinjiekouLatlng = new LatLng(32.0414637353, 118.7851165312);
        LatLng fuzimiaoLatlng = new LatLng(32.0206226446, 118.7889921341);
        LatLng baijiahuLatlng = new LatLng(31.9319650736, 118.8212252856);
        LatLng hexiLatlng = new LatLng(32.0328683928, 118.7360117294);
        hotAreaList.add(xinjiekouLatlng);
        hotAreaList.add(fuzimiaoLatlng);
        hotAreaList.add(baijiahuLatlng);
        hotAreaList.add(hexiLatlng);
        hListViewAdapter = new HorizontalListViewAdapter(getApplicationContext(), titles);
        horizontalListView.setAdapter(hListViewAdapter);
        horizontalListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                hListViewAdapter.setSelectIndex(position);
                aMap.animateCamera(CameraUpdateFactory.newLatLngZoom((LatLng) hotAreaList.get(position), 14));
                addMarkersToMap((LatLng) hotAreaList.get(position), position + 1);


            }
        });

        mapView.onCreate(savedInstanceState); // 此方法必须重写
        if (aMap == null) {
            aMap = mapView.getMap();
            CameraUpdate mCameraUpdate = CameraUpdateFactory.zoomTo(14);
            aMap.moveCamera(mCameraUpdate);
//            aMap.getUiSettings().setMyLocationButtonEnabled(false);// 设置默认定位按钮是否显示
//            aMap.getUiSettings().setCompassEnabled(false);
            aMap.getUiSettings().setScaleControlsEnabled(true);
            didLocation();
            aMap.setOnMarkerClickListener(new AMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker marker) {

                    int index = Integer.parseInt(marker.getSnippet());

                    popupInfo(paopaoView, index);

                    return true;
                }
            });


            aMap.setOnMapClickListener(new AMap.OnMapClickListener() {
                @Override
                public void onMapClick(LatLng latLng) {

                    paopaoView.setVisibility(View.INVISIBLE);

                }
            });

            aMap.setOnCameraChangeListener(new AMap.OnCameraChangeListener() {
                @Override
                public void onCameraChange(CameraPosition cameraPosition) {

                }

                @Override
                public void onCameraChangeFinish(CameraPosition cameraPosition) {

                    LatLng target = cameraPosition.target;
                    if (isFirstInitMap) {
                        isFirstInitMap = false;

                    } else {
//                        getNearParkList(target.longitude + "", target.latitude + "");
                    }


                }
            });


        }


        searchEditText.setFocusable(false);

        searchEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            }
        });


        locationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                didLocation();


            }
        });


    }


    private void addLoactionMark(LatLng latLng) {


        MarkerOptions markerOption = new MarkerOptions().icon(BitmapDescriptorFactory
                .fromBitmap(BitmapFactory
                        .decodeResource(getResources(), R.drawable.location_map_flag)))
                .position(latLng)
                .infoWindowEnable(false).snippet("Location");


        aMap.addMarker(markerOption);

    }


    private void addMarkersToMap(LatLng latLng, int i) {

        markerOption = new MarkerOptions().icon(BitmapDescriptorFactory
                .defaultMarker(BitmapDescriptorFactory.HUE_RED))
                .position(latLng)
                .draggable(false).snippet(i + "").infoWindowEnable(false);


        aMap.addMarker(markerOption);
    }


    /**
     * 根据info为布局上的控件设置信息
     *
     * @param
     * @param
     */
    protected void popupInfo(ViewGroup mMarkerLy, int
            index) {

        mMarkerLy.setVisibility(View.VISIBLE);

        TextView parkAddressText = (TextView) mMarkerLy
                .findViewById(R.id.parkAddressText);
        TextView canUseParkNumberText = (TextView) mMarkerLy
                .findViewById(R.id.canUseParkNumberText);
        TextView distanceText = (TextView) mMarkerLy
                .findViewById(R.id.distanceText);

        TextView supportOnlineText = (TextView) mMarkerLy
                .findViewById(R.id.supportOnlineText);


        TextView perForTimeText = (TextView) mMarkerLy
                .findViewById(R.id.perForTimeText);

        Button goHereButton = (Button) mMarkerLy
                .findViewById(R.id.goHereButton);
        LinearLayout infoLayout = (LinearLayout) mMarkerLy.findViewById(R.id.parkDetialLineraLayout);


//        final int finalIndex = index;
//        final ParkInfo info = nearList.get(finalIndex);

        parkAddressText.setText("停车场地址");
        perForTimeText.setText("停车场距离");
//
//        DecimalFormat decimalFormat = new DecimalFormat("0.0");//构造方法的字符格式这里如果小数不足2位,会以0补足.
//        String p = decimalFormat.format(distance / 1000);//format 返回的是字符串
        distanceText.setText("3KM");
        canUseParkNumberText.setText("空车位数量");


        supportOnlineText.setText("支持线上支付");
        supportOnlineText.setVisibility(View.VISIBLE);


        goHereButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MapActivity.this, AMapNavBaseActivity.class);
                NaviLatLng endlatlng = new NaviLatLng(32.0414637353, 118.7851165312);
                NaviLatLng beginLatlng = new NaviLatLng(loctionLatLng.latitude,
                        loctionLatLng.longitude);
                Bundle bundle = new Bundle();
                bundle.putParcelable("beginLatlng", beginLatlng);
                bundle.putParcelable("endlatlng", endlatlng);
                intent.putExtras(bundle);
                startActivity(intent);
//                AbToastUtil.showToast(MapActivity.this, "导航");
            }
        });

        infoLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//


            }
        });


    }


    @Override
    protected void onStop() {
        super.onStop();

    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onResume() {
        super.onResume();

        mapView.onResume();
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();

    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
        locationProvider.unregisterListener(locationListener); //注销掉监听
        locationProvider.stop(); //停止定位服务
        locationProvider.destroyLocation();

    }

    @Override
    protected void onStart() {
        super.onStart();


//        didLocation();
    }


    @OnClick({R.id.nav_back, R.id.searchEditText})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.nav_back:
                finish();
                break;
            case R.id.searchEditText:
                break;
        }
    }


    /**
     * EsayPermissions接管权限处理逻辑
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }


    @AfterPermissionGranted(REQUEST_LOCATION_PERM)
    public void didLocation() {


        if (EasyPermissions.hasPermissions(this, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

            locationProvider = new LocationProvider(this);
            //获取locationservice实例，建议应用中只初始化1个location实例，然后使用，可以参考其他示例的activity，都是通过此种方式获取locationservice实例的
            locationProvider.registerListener(locationListener);
            //注册监听
            locationProvider.setLocationOption(locationProvider.getDefaultOption());

            locationProvider.start();

        } else {
            // Ask for one permission


            EasyPermissions.requestPermissions(this, "需要使用位置信息权限",
                    REQUEST_LOCATION_PERM, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
//        Toast.makeText(this, "执行onPermissionsGranted()...", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
//        Toast.makeText(this, "执行onPermissionsDenied()...", Toast.LENGTH_SHORT).show();
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            new AppSettingsDialog.Builder(this, "当前App需要申请位置相关权限,需要打开设置页面么?")
                    .setTitle("权限申请")
                    .setPositiveButton("确认")
                    .setNegativeButton("取消", null /* click listener */)
                    .setRequestCode(REQUEST_LOCATION_PERM)
                    .build()
                    .show();
        }
    }


}