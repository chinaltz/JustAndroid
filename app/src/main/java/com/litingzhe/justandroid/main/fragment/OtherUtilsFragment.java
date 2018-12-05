package com.litingzhe.justandroid.main.fragment;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.litingzhe.justandroid.R;
import com.litingzhe.justandroid.main.adapter.SampleListAdapter;
import com.litingzhe.justandroid.main.model.SampleModel;
import com.litingzhe.justandroid.someOther.camera.activity.CaptureActivity;
import com.litingzhe.justandroid.someOther.captureVideo.CaptureVideoActivity;
import com.litingzhe.justandroid.someOther.imagePicker.activity.ImageVideoPickActivity;
import com.litingzhe.justandroid.someOther.musicPlayer.MusicPlayerActivity;
import com.litingzhe.justandroid.someOther.qrCode.activity.QRCaptureActivity;
import com.litingzhe.justandroid.someOther.videoPlayer.activity.VideoPlayerActivity;
import com.litingzhe.justandroid.someOther.voiceRecord.activity.VoiceRecordActivity;
import com.litingzhe.justandroid.someOther.weex.activity.WeexActivity;
import com.ningcui.mylibrary.app.base.AbBaseFragment;
import com.ningcui.mylibrary.utiils.AbStrUtil;
import com.zhy.autolayout.utils.AutoUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import freemarker.template.utility.StringUtil;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * Copyright 李挺哲
 * 创建人：litingzhe
 * 邮箱：453971498@qq.com
 * Created by litingzhe on 2017/4/12 上午10:46.
 * 类描述：其他页面
 */


public class OtherUtilsFragment extends AbBaseFragment implements EasyPermissions.PermissionCallbacks {
    @BindView(R.id.nav_back)
    LinearLayout NavBack;
    @BindView(R.id.nav_title)
    TextView navTitle;
    Unbinder unbinder;
    @BindView(R.id.nav_right_icon)
    ImageView navRightIcon;
    @BindView(R.id.nav_right_text)
    TextView navRightText;
    @BindView(R.id.nav_right)
    LinearLayout navRight;
    @BindView(R.id.uiListView)
    ListView uiListView;
    Unbinder unbinder1;
    private Context mContext;
    private View rootView;
    private ArrayList uiSampleList;
    private SampleListAdapter sampleListAdapter;
    public static final int REQUEST_Camera_PERM = 1011;
    public static final int REQUEST_QRCamera_PERM = 1012;

    //相机拍视频请求码
    public static final int REQUEST_Camera_CAPTUREVIDEO = 1013;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mContext = getActivity();
        if (rootView == null) {

            rootView = inflater.inflate(R.layout.fragment_other, null);
            initDataAndView();
        }


        AutoUtils.auto(rootView);


        unbinder1 = ButterKnife.bind(this, rootView);
        return rootView;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    private void initDataAndView() {
        unbinder = ButterKnife.bind(this, rootView);
        NavBack.setVisibility(View.GONE);
        navTitle.setText("其他常见的功能");
        uiSampleList = new ArrayList();

        SampleModel sampleModel1 = new SampleModel("自定义相机", R.drawable.camera);
        SampleModel sampleModel2 = new SampleModel("二维码扫描", R.drawable.qrcode);
        SampleModel sampleModel3 = new SampleModel("图片/视频选择", R.drawable.selectimage);
        SampleModel sampleModel4 = new SampleModel("视频播放", R.drawable.videoplayer);
        SampleModel sampleModel5 = new SampleModel("音频播放", R.drawable.music);
        SampleModel sampleModel6 = new SampleModel("WEEX混合开发", R.drawable.weex);
        SampleModel sampleModel7 = new SampleModel("录音机", R.drawable.voice_record);
        SampleModel sampleModel8 = new SampleModel("视频录制", R.drawable.video_capture);

        uiSampleList.add(sampleModel1);
        uiSampleList.add(sampleModel2);
        uiSampleList.add(sampleModel3);
        uiSampleList.add(sampleModel4);
        uiSampleList.add(sampleModel5);
        uiSampleList.add(sampleModel6);
        uiSampleList.add(sampleModel7);
        uiSampleList.add(sampleModel8);

        sampleListAdapter = new SampleListAdapter(uiSampleList, mContext);
        uiListView.setAdapter(sampleListAdapter);
        uiListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                Intent intent = new Intent();

                switch (position) {
                    case 0:
                        didStartCapture();
                        break;
                    case 1:
                        didQrStartCapture();
                        break;


                    case 2:
                        intent.setClass(mContext, ImageVideoPickActivity.class);
                        startActivity(intent);

                        break;

                    case 3:
                        intent.setClass(mContext, VideoPlayerActivity.class);
                        startActivity(intent);
                        break;

                    case 4:
                        intent.setClass(mContext, MusicPlayerActivity.class);
                        startActivity(intent);
                        break;

                    case 5:
                        intent.setClass(mContext, WeexActivity.class);
                        startActivity(intent);
                        break;


                    case 6:
                        intent.setClass(mContext, VoiceRecordActivity.class);
                        startActivity(intent);
                        break;

                    case 7:
                        didStartCaptureVideo();
                        break;
                    default:
                        break;


                }

            }
        });


    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }


    @AfterPermissionGranted(REQUEST_Camera_CAPTUREVIDEO)
    public void didStartCaptureVideo() {


        if (EasyPermissions.hasPermissions(mContext, Manifest.permission.CAMERA)) {


            String videoFilePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + AbStrUtil.get36UUID()
                    + ".mp4";
            File videoFile = new File(videoFilePath);

            // 启动视频录制
            CaptureVideoActivity.start((Activity) mContext, videoFilePath, 1000);

        } else {
            // Ask for one permission


            EasyPermissions.requestPermissions(this, "需要使用相机信息权限",
                    REQUEST_Camera_CAPTUREVIDEO, Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO);
        }
    }


    @AfterPermissionGranted(REQUEST_QRCamera_PERM)
    public void didQrStartCapture() {


        if (EasyPermissions.hasPermissions(mContext, Manifest.permission.CAMERA)) {

            Intent intent = new Intent();
            intent.setClass(mContext, QRCaptureActivity.class);
            startActivity(intent);
        } else {
            // Ask for one permission


            EasyPermissions.requestPermissions(this, "需要使用相机信息权限",
                    REQUEST_QRCamera_PERM, Manifest.permission.CAMERA);
        }
    }


    @AfterPermissionGranted(REQUEST_Camera_PERM)
    public void didStartCapture() {


        if (EasyPermissions.hasPermissions(mContext, Manifest.permission.CAMERA)) {

            Intent intent = new Intent();
            intent.setClass(mContext, CaptureActivity.class);
            startActivity(intent);
        } else {
            // Ask for one permission


            EasyPermissions.requestPermissions(this, "需要使用相机信息权限",
                    REQUEST_Camera_PERM, Manifest.permission.CAMERA);
        }
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {

        if (requestCode == REQUEST_Camera_PERM) {
            didStartCapture();
        } else if (requestCode == REQUEST_QRCamera_PERM) {
            didQrStartCapture();
        } else if (requestCode == REQUEST_Camera_CAPTUREVIDEO) {
            didStartCaptureVideo();

        }

    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {

        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            new AppSettingsDialog.Builder(this, "当前App需要申请位置相关权限,需要打开设置页面么?")
                    .setTitle("权限申请")
                    .setPositiveButton("确认")
                    .setNegativeButton("取消", null /* click listener */)
                    .setRequestCode(REQUEST_Camera_PERM)
                    .build()
                    .show();
        }


    }
}
