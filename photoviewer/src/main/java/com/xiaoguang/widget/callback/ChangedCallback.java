package com.xiaoguang.widget.callback;

import android.view.View;
import android.widget.FrameLayout;

import com.xiaoguang.widget.viewer.TransferConfig;

/**
 * 图片滑动后回调
 * hxg 2021/9/28 16:00 qq:929842234
 */
public interface ChangedCallback {
    void onPageSelectedCallback(int position, View currentView,FrameLayout parentLayout, TransferConfig config);
}