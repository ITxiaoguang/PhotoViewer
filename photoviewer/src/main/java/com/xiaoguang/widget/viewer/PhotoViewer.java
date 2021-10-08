package com.xiaoguang.widget.viewer;

import android.app.Activity;
import android.app.Application;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;

import androidx.appcompat.app.AlertDialog;

import com.gyf.immersionbar.ImmersionBar;
import com.xiaoguang.widget.style.progress.ProgressBarIndicator;
import com.xiaoguang.widget.utils.AppManager;
import com.xiaoguang.widget.utils.FileUtils;
import com.xiaoguang.widget.view.video.ExoVideoView;
import com.xiaoguang.widget.view.video.source.ExoSourceManager;

import java.io.File;

/**
 * Main workflow: <br/>
 * 1、点击缩略图展示缩略图到 photoViewer 过渡动画 <br/>
 * 2、显示下载高清图片进度 <br/>
 * 3、加载完成显示高清图片 <br/>
 * 4、高清图支持手势缩放 <br/>
 * 5、关闭 photoViewer 展示 photoViewer 到原缩略图的过渡动画 <br/>
 * Created by Vans Z on 2017/1/19.
 * <p>
 * email: 196425254@qq.com
 */
public class PhotoViewer implements DialogInterface.OnShowListener,
        DialogInterface.OnKeyListener,
        TransferLayout.OnLayoutResetListener,
        AppManager.OnAppStateChangeListener {

    private Context context;
    private Dialog transDialog;

    private TransferLayout transLayout;
    private TransferConfig transConfig;
    private OnPhotoViewerStateChangeListener transListener;

    // 因为Dialog的关闭有动画延迟，固不能使用 dialog.isShowing, 去判断 photoViewer 的显示逻辑
    private boolean shown;

    /**
     * 构造方法私有化，通过{@link #getDefault(Context)} 创建 photoViewer
     *
     * @param context 上下文环境
     */
    private PhotoViewer(Context context) {
        this.context = context;
        createLayout();
        createDialog();
        AppManager.getInstance().init((Application) context.getApplicationContext());
    }

    /**
     * @param context
     * @return {@link PhotoViewer}
     */
    public static PhotoViewer getDefault(Context context) {
        return new PhotoViewer(context);
    }

    private void createLayout() {
        transLayout = new TransferLayout(context);
        transLayout.setOnLayoutResetListener(this);
    }

    private void createDialog() {
        transDialog = new AlertDialog.Builder(context,
                android.R.style.Theme_Translucent_NoTitleBar_Fullscreen)
                .setView(transLayout)
                .create();
        transDialog.setOnShowListener(this);
        transDialog.setOnKeyListener(this);
    }

    /**
     * 检查参数，如果必须参数缺少，就使用缺省参数或者抛出异常
     */
    private void checkConfig() {
        if (transConfig == null)
            throw new IllegalArgumentException("The parameter TransferConfig can't be null");
        if (transConfig.isSourceEmpty())
            throw new IllegalArgumentException("The parameter sourceUrlList or sourceUriList  can't be empty");
        if (transConfig.getImageLoader() == null)
            throw new IllegalArgumentException("Need to specify an ImageLoader");

        transConfig.setNowThumbnailIndex(Math.max(transConfig.getNowThumbnailIndex(), 0));
        transConfig.setOffscreenPageLimit(transConfig.getOffscreenPageLimit() <= 0
                ? 2 : transConfig.getOffscreenPageLimit());
        transConfig.setDuration(transConfig.getDuration() <= 0
                ? 300 : transConfig.getDuration());
        transConfig.setProgressIndicator(transConfig.getProgressIndicator() == null
                ? new ProgressBarIndicator() : transConfig.getProgressIndicator());
//        transConfig.setIndexIndicator(transConfig.getIndexIndicator() == null
//                ? new CircleIndexIndicator() : transConfig.getIndexIndicator());
        transConfig.setIndexIndicator(transConfig.getIndexIndicator());
    }

    /**
     * 配置 photoViewer 参数对象
     *
     * @param config 参数对象
     * @return photoViewer
     */
    public PhotoViewer apply(TransferConfig config) {
        if (!shown) {
            transConfig = config;
            OriginalViewHelper.getInstance().fillOriginImages(config);
            checkConfig();
            transLayout.apply(config);
        }
        return this;
    }

    /**
     * photoViewer 是否显示
     *
     * @return true ：显示, false ：关闭
     */
    public boolean isShown() {
        return shown;
    }

    /**
     * 显示 photoViewer
     */
    public void show() {
        if (shown) return;
        transDialog.show();
        adjustTopAndBottom();
        if (transListener != null) {
            transListener.onShow();
        }
        shown = true;
    }

    /**
     * 显示 photoViewer, 并设置 OnPhotoViewerChangeListener
     *
     * @param listener {@link OnPhotoViewerStateChangeListener}
     */
    public void show(OnPhotoViewerStateChangeListener listener) {
        if (shown || listener == null) return;
        transDialog.show();
        adjustTopAndBottom();
        transListener = listener;
        transListener.onShow();
        shown = true;
    }

    /**
     * 关闭 photoViewer
     */
    public void dismiss() {
        if (shown && transLayout.dismiss(transConfig.getNowThumbnailIndex())) {
            shown = false;
        }
    }

    /**
     * 获取图片文件
     */
    public File getImageFile(String imageUrl) {
        return transConfig.getImageLoader().getCache(imageUrl);
    }

    /**
     * 清除 photoViewer 缓存,包括图片和视频文件缓存，注意清除视频缓存必须保证 photoViewer 是关闭状态
     */
    public void clear() {
        if (transConfig != null && transConfig.getImageLoader() != null) {
            transConfig.getImageLoader().clearCache();
        }
        File cacheFile = new File(context.getCacheDir(), ExoVideoView.CACHE_DIR);
        if (cacheFile.exists() && !shown) {
            FileUtils.deleteDir(new File(cacheFile, VideoThumbState.FRAME_DIR));
            ExoSourceManager.clearCache(context, cacheFile, null);
        }
    }

    /**
     * dialog 打开时的监听器
     */
    @Override
    public void onShow(DialogInterface dialog) {
        AppManager.getInstance().register(this);
        transLayout.show();
    }

    /**
     * 调整顶部和底部内边距
     */
    private void adjustTopAndBottom() {
        if (context instanceof Activity) {
            // 隐藏状态栏和导航栏，全屏化
            Activity activity = (Activity) context;
            ImmersionBar.with(activity, transDialog)
                    .transparentNavigationBar()
                    .init();
            int top = ImmersionBar.getNotchHeight(activity);
            int bottom = ImmersionBar.getNavigationBarHeight(activity);
            transLayout.setPadding(0, top, 0, bottom);
        }
    }

    @Override
    public void onReset() {
        AppManager.getInstance().unregister(this);
        transDialog.dismiss();
        if (transListener != null)
            transListener.onDismiss();
        shown = false;
    }

    @Override
    public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK &&
                event.getAction() == KeyEvent.ACTION_UP &&
                !event.isCanceled()) {
            dismiss();
            return true;
        }
        return false;
    }

    @Override
    public void onForeground() {
        transLayout.pauseOrPlayVideo(false);
    }

    @Override
    public void onBackground() {
        transLayout.pauseOrPlayVideo(true);
    }

    /**
     * 设置 PhotoViewer 显示和关闭的监听器
     *
     * @param listener {@link OnPhotoViewerStateChangeListener}
     */
    public void setOnPhotoViewerStateChangeListener(OnPhotoViewerStateChangeListener listener) {
        transListener = listener;
    }

    /**
     * 资源销毁，防止内存泄漏
     */
    public void destroy() {
        if (transConfig != null) {
            transConfig.destroy();
            transConfig = null;
        }
    }

    /**
     * PhotoViewer 显示的时候调用 {@link OnPhotoViewerStateChangeListener#onShow()}
     * <p>
     * PhotoViewer 关闭的时候调用 {@link OnPhotoViewerStateChangeListener#onDismiss()}
     */
    public interface OnPhotoViewerStateChangeListener {
        void onShow();

        void onDismiss();
    }

    public interface OnPhotoViewerLongClickListener {
        void onLongClick(FrameLayout parent, View currentView, String imageUri, int pos, TransferConfig config);
    }

}
