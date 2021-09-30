package com.xiaoguang.widget.callback;

import android.view.View;

import com.xiaoguang.widget.viewer.TransferConfig;

/**
 * 自定义View回调
 * hxg 2021/9/28 16:00 qq:929842234
 */
public interface CustomViewCallback {
    void onBindView(View customView, TransferConfig config);
}