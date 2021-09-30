package com.xiaoguang.widget.utils;

import android.util.Log;

/**
 * PhotoViewer的全部log
 * hxg 2021/9/28 10:40 qq:929842234
 */
public class PVLogUtil {
    private static final String PHOTO_VIEWER_TAG = "PhotoViewer-";

    public static void i(String tag, String msg) {
        Log.i(PHOTO_VIEWER_TAG + tag, msg);
//        Log.e(PHOTO_VIEWER_TAG + tag, msg);
    }

    public static void e(String tag, String msg) {
        Log.e(PHOTO_VIEWER_TAG + tag, msg);
    }
}