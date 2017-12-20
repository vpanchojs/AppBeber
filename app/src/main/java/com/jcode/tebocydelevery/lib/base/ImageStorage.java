package com.jcode.tebocydelevery.lib.base;

import java.io.File;

/**
 * Created by victo on 15/03/2017.
 */

public interface ImageStorage {
    String getImageUrl(String id);

    void upload(File file, String id, ImageStorageFinishedListener listener);
}
