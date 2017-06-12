package com.ningcui.mylibrary.viewLib.Imagepicker.observable;


import com.ningcui.mylibrary.viewLib.Imagepicker.entity.LocalMedia;
import com.ningcui.mylibrary.viewLib.Imagepicker.entity.LocalMediaFolder;

import java.util.List;

/**
 * author：luck
 * project：PictureSelector
 * package：com.luck.picture.lib.observable
 * email：893855882@qq.com
 * data：17/1/16
 */
public interface ObserverListener {
    void observerUpFoldersData(List<LocalMediaFolder> folders);

    void observerUpSelectsData(List<LocalMedia> selectMedias);
}
