package com.ningcui.mylibrary.viewLib.Imagepicker;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

import com.ningcui.mylibrary.R;
import com.ningcui.mylibrary.viewLib.Imagepicker.compress.CompressConfig;
import com.ningcui.mylibrary.viewLib.Imagepicker.compress.CompressImageOptions;
import com.ningcui.mylibrary.viewLib.Imagepicker.compress.CompressInterface;
import com.ningcui.mylibrary.viewLib.Imagepicker.compress.LubanOptions;
import com.ningcui.mylibrary.viewLib.Imagepicker.config.PictureConfig;
import com.ningcui.mylibrary.viewLib.Imagepicker.config.PictureSelectionConfig;
import com.ningcui.mylibrary.viewLib.Imagepicker.dialog.PictureDialog;
import com.ningcui.mylibrary.viewLib.Imagepicker.entity.EventEntity;
import com.ningcui.mylibrary.viewLib.Imagepicker.entity.LocalMedia;
import com.ningcui.mylibrary.viewLib.Imagepicker.entity.LocalMediaFolder;
import com.ningcui.mylibrary.viewLib.Imagepicker.rxbus2.RxBus;
import com.ningcui.mylibrary.viewLib.Imagepicker.tools.AttrsUtils;
import com.ningcui.mylibrary.viewLib.Imagepicker.tools.DebugUtil;
import com.ningcui.mylibrary.viewLib.Imagepicker.tools.DoubleUtils;
import com.ningcui.mylibrary.viewLib.Imagepicker.tools.PictureFileUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class PictureBaseActivity extends FragmentActivity {
    protected Context mContext;
    protected PictureSelectionConfig config;
    protected int spanCount, maxSelectNum, minSelectNum,
            selectionMode, mimeType, videoSecond, compressMaxKB, compressMode,
            compressGrade, compressWidth, compressHeight,
            recordVideoSecond, videoQuality;
    protected boolean isGif, isCamera, enablePreview, isCompress,
            enPreviewVideo, checkNumMode, openClickSound, numComplete, camera,
            previewEggs, statusFont, previewStatusFont;
    protected String cameraPath;
    protected String originalPath;
    protected PictureDialog dialog;
    protected PictureDialog compressDialog;
    protected List<LocalMedia> selectionMedias;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            config = (PictureSelectionConfig) savedInstanceState.getSerializable(PictureConfig.EXTRA_CONFIG);
            cameraPath = savedInstanceState.getString(PictureConfig.BUNDLE_CAMERA_PATH);
            originalPath = savedInstanceState.getString(PictureConfig.BUNDLE_ORIGINAL_PATH);
        } else {
            config = PictureSelectionConfig.getInstance();
        }
        compressDialog = new PictureDialog(this);
        int themeStyleId = config.themeStyleId;
        setTheme(themeStyleId);
        super.onCreate(savedInstanceState);
        mContext = this;
        initConfig();
    }

    /**
     * 获取配置参数
     */
    private void initConfig() {
        camera = config.camera;
        statusFont = AttrsUtils.getTypeValueBoolean
                (this, R.attr.picture_statusFontColor);
        previewStatusFont = AttrsUtils.getTypeValueBoolean
                (this, R.attr.picture_preview_statusFontColor);
        mimeType = config.mimeType;
        selectionMedias = config.selectionMedias;
        selectionMode = config.selectionMode;
        if (selectionMode == PictureConfig.SINGLE) {
            selectionMedias = new ArrayList<>();
        }
        spanCount = config.imageSpanCount;
        isGif = config.isGif;
        isCamera = config.isCamera;
        maxSelectNum = config.maxSelectNum;
        minSelectNum = config.minSelectNum;
        enablePreview = config.enablePreview;
        enPreviewVideo = config.enPreviewVideo;
        checkNumMode = config.checkNumMode = AttrsUtils.getTypeValueBoolean
                (this, R.attr.picture_style_checkNumMode);
        openClickSound = config.openClickSound;
        videoSecond = config.videoSecond;
        isCompress = config.isCompress;
        numComplete = AttrsUtils.getTypeValueBoolean(this, R.attr.picture_style_numComplete);
        compressMaxKB = config.compressMaxkB;
        compressMode = config.compressMode;
        compressGrade = config.compressGrade;
        compressWidth = config.compressWidth;
        compressHeight = config.compressHeight;
        recordVideoSecond = config.recordVideoSecond;
        videoQuality = config.videoQuality;
        previewEggs = config.previewEggs;
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString(PictureConfig.BUNDLE_CAMERA_PATH, cameraPath);
        outState.putString(PictureConfig.BUNDLE_ORIGINAL_PATH, originalPath);
        outState.putSerializable(PictureConfig.EXTRA_CONFIG, config);
    }

    protected void startActivity(Class clz, Bundle bundle) {
        if (!DoubleUtils.isFastDoubleClick()) {
            Intent intent = new Intent();
            intent.setClass(this, clz);
            intent.putExtras(bundle);
            startActivity(intent);
        }
    }

    protected void startActivity(Class clz, Bundle bundle, int requestCode) {
        if (!DoubleUtils.isFastDoubleClick()) {
            Intent intent = new Intent();
            intent.setClass(this, clz);
            intent.putExtras(bundle);
            startActivityForResult(intent, requestCode);
        }
    }

    protected void showToast(String msg) {
        Toast.makeText(mContext, msg, Toast.LENGTH_LONG).show();
    }

    /**
     * loading dialog
     */
    protected void showPleaseDialog() {
        if (!isFinishing()) {
            dismissDialog();
            dialog = new PictureDialog(this);
            dialog.show();
        }
    }

    /**
     * dismiss dialog
     */
    protected void dismissDialog() {
        try {
            if (dialog != null && dialog.isShowing()) {
                dialog.dismiss();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * compress loading dialog
     */
    protected void showCompressDialog() {
        if (!isFinishing() && compressDialog != null) {
            dismissCompressDialog();
            compressDialog.show();
        }
    }

    /**
     * dismiss compress dialog
     */
    protected void dismissCompressDialog() {
        try {
            if (compressDialog != null && compressDialog.isShowing()) {
                compressDialog.dismiss();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * compressImage
     */
    protected void compressImage(final List<LocalMedia> result) {
        showCompressDialog();
        CompressConfig compress_config = CompressConfig.ofDefaultConfig();
        DebugUtil.i("compressImage--->", compressMaxKB + "");
        switch (compressMode) {
            case PictureConfig.SYSTEM_COMPRESS_MODE:
                // 系统自带压缩
                compress_config.enablePixelCompress(true);
                compress_config.enableQualityCompress(true);
                compress_config.setMaxSize(compressMaxKB);
                break;
            case PictureConfig.LUBAN_COMPRESS_MODE:
                // luban压缩
                DebugUtil.i("compressImage WH--->", compressHeight + "\n" + compressHeight);
                LubanOptions option = new LubanOptions.Builder()
                        .setMaxHeight(compressHeight)
                        .setMaxWidth(compressWidth)
                        .setMaxSize(compressMaxKB)
                        .setGrade(compressGrade)
                        .create();
                compress_config = CompressConfig.ofLuban(option);
                break;
        }

        CompressImageOptions.compress(this, compress_config, result,
                new CompressInterface.CompressListener() {
                    @Override
                    public void onCompressSuccess(List<LocalMedia> images) {
                        RxBus.getDefault().post(new EventEntity(PictureConfig.CLOSE_PREVIEW_FLAG));
                        onResult(images);
                    }

                    @Override
                    public void onCompressError(List<LocalMedia> images, String msg) {
                        RxBus.getDefault().post(new EventEntity(PictureConfig.CLOSE_PREVIEW_FLAG));
                        onResult(result);
                    }
                }).compress();
    }


    /**
     * 判断拍照 图片是否旋转
     *
     * @param degree
     * @param file
     */
    protected void rotateImage(int degree, File file) {
        if (degree > 0) {
            // 针对相片有旋转问题的处理方式
            try {
                BitmapFactory.Options opts = new BitmapFactory.Options();//获取缩略图显示到屏幕上
                opts.inSampleSize = 2;
                Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath(), opts);
                Bitmap bmp = PictureFileUtils.rotaingImageView(degree, bitmap);
                PictureFileUtils.saveBitmapFile(bmp, file);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 如果没有任何相册，先创建一个最近相册出来
     *
     * @param folders
     */
    protected void createNewFolder(List<LocalMediaFolder> folders) {
        if (folders.size() == 0) {
            // 没有相册 先创建一个最近相册出来
            LocalMediaFolder newFolder = new LocalMediaFolder();
            String folderName = getString(R.string.picture_camera_roll);
            newFolder.setName(folderName);
            newFolder.setPath("");
            newFolder.setFirstImagePath("");
            folders.add(newFolder);
        }
    }

    /**
     * 将图片插入到相机文件夹中
     *
     * @param path
     * @param imageFolders
     * @return
     */
    protected LocalMediaFolder getImageFolder(String path, List<LocalMediaFolder> imageFolders) {
        File imageFile = new File(path);
        File folderFile = imageFile.getParentFile();

        for (LocalMediaFolder folder : imageFolders) {
            if (folder.getName().equals(folderFile.getName())) {
                return folder;
            }
        }
        LocalMediaFolder newFolder = new LocalMediaFolder();
        newFolder.setName(folderFile.getName());
        newFolder.setPath(folderFile.getAbsolutePath());
        newFolder.setFirstImagePath(path);
        imageFolders.add(newFolder);
        return newFolder;
    }

    /**
     * compress or callback
     *
     * @param result
     */
    protected void handlerResult(List<LocalMedia> result) {
        if (isCompress) {
            compressImage(result);
        } else {
            onResult(result);
        }
    }

    /**
     * return image result
     *
     * @param images
     */
    protected void onResult(List<LocalMedia> images) {
        dismissCompressDialog();
        if (camera
                && selectionMode == PictureConfig.MULTIPLE
                && selectionMedias != null) {
            images.addAll(selectionMedias);
        }
        Intent intent = PictureSelector.putIntentResult(images);
        setResult(RESULT_OK, intent);
        closeActivity();
    }

    /**
     * Close Activity
     */
    protected void closeActivity() {
        finish();
        overridePendingTransition(0, R.anim.a3);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dismissCompressDialog();
        dismissDialog();
    }
}
