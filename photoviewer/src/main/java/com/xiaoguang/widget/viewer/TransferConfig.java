package com.xiaoguang.widget.viewer;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ImageView;

import androidx.annotation.IdRes;
import androidx.recyclerview.widget.RecyclerView;

import com.xiaoguang.widget.R;
import com.xiaoguang.widget.callback.ChangedCallback;
import com.xiaoguang.widget.callback.CustomViewCallback;
import com.xiaoguang.widget.entry.BaseImgUrlBean;
import com.xiaoguang.widget.entry.VideoUrlBean;
import com.xiaoguang.widget.loader.ImageLoader;
import com.xiaoguang.widget.style.IIndexIndicator;
import com.xiaoguang.widget.style.IProgressIndicator;
import com.xiaoguang.widget.utils.PVLogUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * PhotoViewer Attributes
 * <p>
 * Created by Vans Z on 2017/1/19.
 * <p>
 * email: 196425254@qq.com
 */
public final class TransferConfig {
    private final static Pattern VIDEO_PATTERN = Pattern.compile(".+(://).+\\.(mp4|wmv|avi|mpeg|rm|rmvb|flv|3gp|mov|mkv|mod|)");

    private int nowThumbnailIndex;
    private int offscreenPageLimit;
    private int missPlaceHolder;
    private int errorPlaceHolder;
    private int backgroundColor;
    private long duration;
    private boolean justLoadHitPage;
    private boolean enableDragClose;
    private boolean enableDragHide;
    private boolean enableDragPause;
    private boolean enableHideThumb;
    private boolean enableScrollingWithPageChange;

    private Drawable missDrawable;
    private Drawable errorDrawable;

    private List<ImageView> originImageList;
    private List<BaseImgUrlBean> sourceBeanList;
    private List<String> sourceUrlList;
    private List<Uri> sourceUriList;

    private IProgressIndicator progressIndicator;
    private IIndexIndicator indexIndicator;
    private ImageLoader imageLoader;

    private @IdRes
    int imageId;
    private ImageView imageView;
    private AbsListView listView;
    private RecyclerView recyclerView;
    private ImageView targetImageView;
    private View customView;
    private CustomViewCallback customViewCallback;
    private int scrollCustomViewId;
    private CustomViewCallback scrollCustomViewCallback;
    private int scrollImageCustomViewId;
    private CustomViewCallback scrollImageCustomViewCallback;
    private int scrollVideoCustomViewId;
    private CustomViewCallback scrollVideoCustomViewCallback;
    private ChangedCallback changedCallback;

    private int headerSize;
    private int footerSize;

    private PhotoViewer.OnPhotoViewerLongClickListener longClickListener;

    public static Builder build() {
        return new Builder();
    }

    public int getNowThumbnailIndex() {
        return nowThumbnailIndex;
    }

    public void setNowThumbnailIndex(int nowThumbnailIndex) {
        this.nowThumbnailIndex = nowThumbnailIndex;
    }

    public int getOffscreenPageLimit() {
        return offscreenPageLimit;
    }

    public void setOffscreenPageLimit(int offscreenPageLimit) {
        this.offscreenPageLimit = offscreenPageLimit;
    }

    public int getMissPlaceHolder() {
        return missPlaceHolder;
    }

    public void setMissPlaceHolder(int missPlaceHolder) {
        this.missPlaceHolder = missPlaceHolder;
    }

    public int getErrorPlaceHolder() {
        return errorPlaceHolder;
    }

    public void setErrorPlaceHolder(int errorPlaceHolder) {
        this.errorPlaceHolder = errorPlaceHolder;
    }

    public int getBackgroundColor() {
        return backgroundColor == 0 ? Color.BLACK : backgroundColor;
    }

    public void setBackgroundColor(int backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public boolean isJustLoadHitPage() {
        return justLoadHitPage;
    }

    public void enableJustLoadHitPage(boolean justLoadHitPage) {
        this.justLoadHitPage = justLoadHitPage;
    }

    public boolean isEnableDragClose() {
        return enableDragClose;
    }

    public void enableDragClose(boolean enableDragClose) {
        this.enableDragClose = enableDragClose;
    }

    public void enableDragHide(boolean enableDragHide) {
        this.enableDragHide = enableDragHide;
    }

    public boolean isEnableDragHide() {
        return enableDragHide;
    }

    public boolean isEnableDragPause() {
        return enableDragPause;
    }

    public void enableDragPause(boolean enableDragPause) {
        this.enableDragPause = enableDragPause;
    }

    public boolean isEnableHideThumb() {
        return enableHideThumb;
    }

    public void enableHideThumb(boolean enableHideThumb) {
        this.enableHideThumb = enableHideThumb;
    }

    public boolean isEnableScrollingWithPageChange() {
        return enableScrollingWithPageChange;
    }

    public void enableScrollingWithPageChange(boolean enableScrollingWithPageChange) {
        this.enableScrollingWithPageChange = enableScrollingWithPageChange;
    }

    public Drawable getMissDrawable(Context context) {
        if (missDrawable != null)
            return missDrawable;
        else if (missPlaceHolder != 0)
            return context.getResources().getDrawable(missPlaceHolder);
        else
            return context.getResources().getDrawable(R.drawable.ic_empty_photo);
    }

    public void setMissDrawable(Drawable missDrawable) {
        this.missDrawable = missDrawable;
    }

    public Drawable getErrorDrawable(Context context) {
        if (errorDrawable != null)
            return errorDrawable;
        else if (errorPlaceHolder != 0)
            return context.getResources().getDrawable(errorPlaceHolder);
        else
            return context.getResources().getDrawable(R.drawable.ic_empty_photo);
    }

    public void setErrorDrawable(Drawable errorDrawable) {
        this.errorDrawable = errorDrawable;
    }

    List<ImageView> getOriginImageList() {
        return originImageList == null ? new ArrayList<ImageView>() : originImageList;
    }

    public void setOriginImageList(List<ImageView> originImageList) {
        this.originImageList = originImageList;
    }

    public List<BaseImgUrlBean> getSourceBeanList() {
        return sourceBeanList;
    }

    public void setSourceBeanList(List<BaseImgUrlBean> sourceBeanList) {
        this.sourceBeanList = sourceBeanList;
    }

    private List<String> getSourceUrlList() {
        if (sourceUrlList == null || sourceUrlList.isEmpty()) {
            sourceUrlList = new ArrayList<>();
            if (sourceUriList != null && !sourceUriList.isEmpty()) {
                for (Uri uri : sourceUriList) {
                    sourceUrlList.add(uri.toString());
                }
            }
        }
        return sourceUrlList;
    }

    public void setSourceUrlList(List<String> sourceUrlList) {
        this.sourceUrlList = sourceUrlList;
    }

    public String getSourceUrl(int position) {
        if (null != sourceBeanList) {
            BaseImgUrlBean bean = sourceBeanList.get(position);
            return bean.getImageUrl();
        } else {
            return getSourceUrlList().get(position);
        }
    }

    public String getVideoSourceUrl(int position) {
        if (null != sourceBeanList) {
            BaseImgUrlBean bean = sourceBeanList.get(position);
            if (bean instanceof VideoUrlBean) {
                return ((VideoUrlBean) bean).getVideoUrl();
            } else {
                return null;
            }
        } else {
            return getSourceUrlList().get(position);
        }
    }

    public int getSourceSize() {
        if (null != sourceBeanList) {
            return sourceBeanList.size();
        } else {
            return getSourceUrlList().size();
        }
    }

    public void setSourceUriList(List<Uri> sourceUriList) {
        this.sourceUriList = sourceUriList;
    }

    public IProgressIndicator getProgressIndicator() {
        return progressIndicator;
    }

    public void setProgressIndicator(IProgressIndicator progressIndicator) {
        this.progressIndicator = progressIndicator;
    }

    public IIndexIndicator getIndexIndicator() {
        return indexIndicator;
    }

    public void setIndexIndicator(IIndexIndicator indexIndicator) {
        this.indexIndicator = indexIndicator;
    }

    public ImageLoader getImageLoader() {
        return imageLoader;
    }

    public void setImageLoader(ImageLoader imageLoader) {
        this.imageLoader = imageLoader;
    }

    public PhotoViewer.OnPhotoViewerLongClickListener getLongClickListener() {
        return longClickListener;
    }

    public void setLongClickListener(PhotoViewer.OnPhotoViewerLongClickListener longClickListener) {
        this.longClickListener = longClickListener;
    }

    /**
     * 原图路径集合是否为空
     *
     * @return true : 空
     */
    public boolean isSourceEmpty() {
        return (sourceUrlList == null || sourceUrlList.isEmpty())
                && (sourceUriList == null || sourceUriList.isEmpty())
                && (sourceBeanList == null || sourceBeanList.isEmpty());
    }

    /**
     * 判断当前 position 下的资源是不是视频
     * FIX BUG 解决判断视频不友好
     *
     * @param position 为 -1 值，表示取 nowThumbnailIndex
     * @return true : 是视频资源
     * 推荐使用{@link Builder#setSourceBeanList(List< BaseImgUrlBean >)}方式设置资源
     */
    public boolean isVideoSource(int position) {
        int pos = position == -1 ? nowThumbnailIndex : position;
        String sourceUrl;
        if (null != sourceUrlList) {
            sourceUrl = sourceUrlList.get(pos);
        } else {
            BaseImgUrlBean bean = sourceBeanList.get(pos);
            return bean instanceof VideoUrlBean;
        }
        if (TextUtils.isEmpty(sourceUrl)) {
            PVLogUtil.e("TransferConfig", "sourceUrl isEmpty");
            return false;
        }
        return VIDEO_PATTERN.matcher(sourceUrl).matches();
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }

    public AbsListView getListView() {
        return listView;
    }

    public void setListView(AbsListView listView) {
        this.listView = listView;
    }

    public RecyclerView getRecyclerView() {
        return recyclerView;
    }

    public void setRecyclerView(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
    }

    public ImageView getTargetImageView() {
        return targetImageView;
    }

    public void setTargetImageView(ImageView targetImageView) {
        this.targetImageView = targetImageView;
    }

    public View getCustomView() {
        return customView;
    }

    public void setCustomView(View customView) {
        this.customView = customView;
    }

    public CustomViewCallback getCustomViewCallback() {
        return customViewCallback;
    }

    public void setCustomViewCallback(CustomViewCallback customViewCallback) {
        this.customViewCallback = customViewCallback;
    }

    public int getScrollCustomViewId() {
        return scrollCustomViewId;
    }

    public void setScrollCustomViewId(int scrollCustomViewId) {
        this.scrollCustomViewId = scrollCustomViewId;
    }

    public CustomViewCallback getScrollCustomViewCallback() {
        return scrollCustomViewCallback;
    }

    public void setScrollCustomViewCallback(CustomViewCallback scrollCustomViewCallback) {
        this.scrollCustomViewCallback = scrollCustomViewCallback;
    }

    public int getScrollImageCustomViewId() {
        return scrollImageCustomViewId;
    }

    public void setScrollImageCustomViewId(int scrollImageCustomViewId) {
        this.scrollImageCustomViewId = scrollImageCustomViewId;
    }

    public CustomViewCallback getScrollImageCustomViewCallback() {
        return scrollImageCustomViewCallback;
    }

    public void setScrollImageCustomViewCallback(CustomViewCallback scrollImageCustomViewCallback) {
        this.scrollImageCustomViewCallback = scrollImageCustomViewCallback;
    }

    public int getScrollVideoCustomViewId() {
        return scrollVideoCustomViewId;
    }

    public void setScrollVideoCustomViewId(int scrollVideoCustomViewId) {
        this.scrollVideoCustomViewId = scrollVideoCustomViewId;
    }

    public CustomViewCallback getScrollVideoCustomViewCallback() {
        return scrollVideoCustomViewCallback;
    }

    public void setScrollVideoCustomViewCallback(CustomViewCallback scrollVideoCustomViewCallback) {
        this.scrollVideoCustomViewCallback = scrollVideoCustomViewCallback;
    }

    public ChangedCallback getChangedCallback() {
        return changedCallback;
    }

    public void setChangedCallback(ChangedCallback changedCallback) {
        this.changedCallback = changedCallback;
    }

    public int getHeaderSize() {
        return headerSize;
    }

    public void setHeaderSize(int headerSize) {
        this.headerSize = headerSize;
    }

    public int getFooterSize() {
        return footerSize;
    }

    public void setFooterSize(int footerSize) {
        this.footerSize = footerSize;
    }

    public void destroy() {
        setImageView(null);
        setCustomView(null);
        setCustomViewCallback(null);
        setScrollCustomViewId(0);
        setScrollCustomViewCallback(null);
        setScrollImageCustomViewId(0);
        setScrollImageCustomViewCallback(null);
        setScrollVideoCustomViewId(0);
        setScrollVideoCustomViewCallback(null);
        setChangedCallback(null);
        setListView(null);
        setRecyclerView(null);
        setTargetImageView(null);
        setProgressIndicator(null);
        setIndexIndicator(null);
        setImageLoader(null);
        setOriginImageList(null);
        setSourceBeanList(null);
        setSourceUrlList(null);
        setSourceUriList(null);
        setMissDrawable(null);
        setErrorDrawable(null);
    }

    public static class Builder {
        private int nowThumbnailIndex;
        private int offscreenPageLimit;
        private int missPlaceHolder;
        private int errorPlaceHolder;
        private int backgroundColor;
        private long duration;
        private boolean justLoadHitPage = false;
        private boolean enableDragClose = true;
        private boolean enableDragHide = true;
        private boolean enableDragPause = false;
        private boolean enableHideThumb = true;
        private boolean enableScrollingWithPageChange = false;
        private boolean autoAdjustDirection = true;

        private Drawable missDrawable;
        private Drawable errorDrawable;

        private List<BaseImgUrlBean> sourceBeanList;
        private List<String> sourceUrlList;
        private List<Uri> sourceUriList;
        private List<ImageView> originImageList;

        private IProgressIndicator progressIndicator;
        private IIndexIndicator indexIndicator;
        private ImageLoader imageLoader;

        // 自定义布局
        private View customView;
        private CustomViewCallback customViewCallback;
        private int scrollCustomViewId;
        private CustomViewCallback scrollCustomViewCallback;
        private int scrollImageCustomViewId;
        private CustomViewCallback scrollImageCustomViewCallback;
        private int scrollVideoCustomViewId;
        private CustomViewCallback scrollVideoCustomViewCallback;
        private ChangedCallback changedCallback;

        private @IdRes
        int imageId;
        private ImageView imageView;
        private AbsListView listView;
        private RecyclerView recyclerView;
        private ImageView targetImageView;// 目标ImageView

        private int headerSize;
        private int footerSize;

        private PhotoViewer.OnPhotoViewerLongClickListener longClickListener;

        /**
         * 当前缩略图在所有图片中的索引
         */
        public Builder setNowThumbnailIndex(int nowThumbnailIndex) {
            this.nowThumbnailIndex = nowThumbnailIndex;
            return this;
        }

        /**
         * <p>ViewPager 中进行初始化并创建的页面 : 设置为当前页面加上当前页面两侧的页面</p>
         * <p>默认为1, 表示第一次加载3张(nowThumbnailIndex, nowThumbnailIndex
         * + 1, nowThumbnailIndex - 1);值为 2, 表示加载5张。依次类推</p>
         * <p>这个参数是为了优化而提供的，值越大，初始化创建的页面越多，保留的页面也
         * 越多，推荐使用默认值</p>
         */
        public Builder setOffscreenPageLimit(int offscreenPageLimit) {
            this.offscreenPageLimit = offscreenPageLimit;
            return this;
        }

        /**
         * 缺省的占位图(资源ID)
         */
        public Builder setMissPlaceHolder(int missPlaceHolder) {
            this.missPlaceHolder = missPlaceHolder;
            return this;
        }

        /**
         * 图片加载错误显示的图片(资源ID)
         */
        public Builder setErrorPlaceHolder(int errorPlaceHolder) {
            this.errorPlaceHolder = errorPlaceHolder;
            return this;
        }

        /**
         * 为 photoViewer 组件设置背景颜色
         */
        public Builder setBackgroundColor(int backgroundColor) {
            this.backgroundColor = backgroundColor;
            return this;
        }

        /**
         * 动画播放时长
         */
        public Builder setDuration(long duration) {
            this.duration = duration;
            return this;
        }

        /**
         * 仅仅只加载当前显示的图片
         */
        public Builder enableJustLoadHitPage(boolean justLoadHitPage) {
            this.justLoadHitPage = justLoadHitPage;
            return this;
        }

        /**
         * 是否可以拖拽关闭
         */
        public Builder enableDragClose(boolean enableDragClose) {
            this.enableDragClose = enableDragClose;
            return this;
        }

        /**
         * 拖拽关闭时是否隐藏除主视图以外的其他 view
         */
        public Builder enableDragHide(boolean enableDragHide) {
            this.enableDragHide = enableDragHide;
            return this;
        }

        /**
         * 拖拽关闭时是否暂停当前页面视频播放
         */
        public Builder enableDragPause(boolean enableDragPause) {
            this.enableDragPause = enableDragPause;
            return this;
        }

        /**
         * 是否开启当 photoViewer 打开时，隐藏缩略图
         */
        public Builder enableHideThumb(boolean enableHideThumb) {
            this.enableHideThumb = enableHideThumb;
            return this;
        }

        /**
         * 是否启动列表随着 page 的切换而置顶滚动，仅仅针对绑定
         * RecyclerView/GridView/ListView 有效, 启动之后
         * 因为列表会实时滚动，缩略图 view 将不会出现为空的
         * 现象，从而保证关闭 photoViewer 时为过渡关闭动画,
         * 而不会出现扩散消失动画
         */
        public Builder enableScrollingWithPageChange(boolean enableScrollingWithPageChange) {
            this.enableScrollingWithPageChange = enableScrollingWithPageChange;
            return this;
        }

        /**
         * 是否启用图片的方向自动校正
         */
        public Builder autoAdjustDirection(boolean autoAdjustDirection) {
            this.autoAdjustDirection = autoAdjustDirection;
            return this;
        }

        /**
         * 缺省的占位图(Drawable 格式)
         */
        public Builder setMissDrawable(Drawable missDrawable) {
            this.missDrawable = missDrawable;
            return this;
        }

        /**
         * 图片加载错误显示的图片(Drawable 格式)
         */
        public Builder setErrorDrawable(Drawable errorDrawable) {
            this.errorDrawable = errorDrawable;
            return this;
        }

        /**
         * 高清图的地址实体集合
         */
        public Builder setSourceBeanList(List<BaseImgUrlBean> sourceBeanList) {
            this.sourceBeanList = sourceBeanList;
            return this;
        }

        /**
         * 高清图的地址集合
         * format: java.lang.String
         */
        public Builder setSourceUrlList(List<String> sourceUrlList) {
            this.sourceUrlList = sourceUrlList;
            return this;
        }

        /**
         * 高清图的地址集合
         * format: android.net.Uri
         */
        public Builder setSourceUriList(List<Uri> sourceUriList) {
            this.sourceUriList = sourceUriList;
            return this;
        }

        /**
         * 过渡前原始的 ImageView 集合
         */
        public Builder setOriginImageList(List<ImageView> originImageList) {
            this.originImageList = originImageList;
            return this;
        }

        /**
         * 加载高清图的进度条 (默认内置 ProgressPieIndicator), 可自实现
         * IProgressIndicator 接口定义自己的图片加载进度条
         */
        public Builder setProgressIndicator(IProgressIndicator progressIndicator) {
            this.progressIndicator = progressIndicator;
            return this;
        }

        /**
         * 图片索引指示器 (默认 无索引), 可自实现
         * IIndexIndicator 接口定义自己的图片索引指示器
         */
        public Builder setIndexIndicator(IIndexIndicator indexIndicator) {
            this.indexIndicator = indexIndicator;
            return this;
        }

        /**
         * 图片加载器 (默认内置 GlideImageLoader), 可自实现
         * ImageLoader 接口定义自己的图片加载器
         */
        public Builder setImageLoader(ImageLoader imageLoader) {
            this.imageLoader = imageLoader;
            return this;
        }

        /**
         * 绑定 photoViewer 长按操作监听器
         */
        public Builder setOnLongClickListener(PhotoViewer.OnPhotoViewerLongClickListener listener) {
            this.longClickListener = listener;
            return this;
        }

        /**
         * 在 photoViewer 视图放置用户自定义的视图
         * 视图固定
         *
         * @param customView 自定义view
         */
        public Builder setCustomView(View customView) {
            this.customView = customView;
            return this;
        }

        /**
         * 在 photoViewer 视图放置用户自定义的视图
         * 视图固定
         *
         * @param customView         自定义view
         * @param customViewCallback 自定义view回调
         */
        public Builder setCustomView(View customView, CustomViewCallback customViewCallback) {
            this.customView = customView;
            this.customViewCallback = customViewCallback;
            return this;
        }

        /**
         * 在 photoViewer 视图放置用户自定义的视图
         * 跟随图片滑动的自定义视图
         */
        public Builder setScrollCustomView(int scrollCustomViewId, CustomViewCallback scrollCustomViewCallback) {
            this.scrollCustomViewId = scrollCustomViewId;
            this.scrollCustomViewCallback = scrollCustomViewCallback;
            return this;
        }

        /**
         * 在 photoViewer 视图放置用户自定义的视图
         * 跟随图片滑动的自定义视图
         */
        public Builder setScrollImageCustomView(int scrollImageCustomViewID, CustomViewCallback scrollImageCustomViewCallback) {
            this.scrollImageCustomViewId = scrollImageCustomViewID;
            this.scrollImageCustomViewCallback = scrollImageCustomViewCallback;
            return this;
        }

        /**
         * 在 photoViewer 视图放置用户自定义的视图
         * 跟随视频滑动的自定义视图
         */
        public Builder setScrollVideoCustomView(int scrollVideoCustomViewId, CustomViewCallback scrollVideoCustomViewCallback) {
            this.scrollVideoCustomViewId = scrollVideoCustomViewId;
            this.scrollVideoCustomViewCallback = scrollVideoCustomViewCallback;
            return this;
        }

        /**
         * ViewPager 滑动改变回调
         */
        public Builder setChangedCallback(ChangedCallback changedCallback) {
            this.changedCallback = changedCallback;
            return this;
        }

        /**
         * 绑定 ListView
         *
         * @param imageId item layout 中的 ImageView Resource ID
         */
        public TransferConfig bindListView(AbsListView listView, int imageId) {
            this.listView = listView;
            this.imageId = imageId;
            return create();
        }

        /**
         * 绑定 ListView, 并指定 header 和 footer 的数量
         *
         * @param imageId item layout 中的 ImageView Resource ID
         */
        public TransferConfig bindListView(AbsListView listView, int headerSize, int footerSize, int imageId) {
            this.listView = listView;
            this.headerSize = headerSize;
            this.footerSize = footerSize;
            this.imageId = imageId;
            return create();
        }

        /**
         * 绑定 RecyclerView
         * 用imageId在多布局计算时出错
         *
         * @param imageId item layout 中的 ImageView Resource ID
         * @see #bindRecyclerView(RecyclerView, ImageView)
         */
        @Deprecated
        public TransferConfig bindRecyclerView(RecyclerView recyclerView, int imageId) {
            this.recyclerView = recyclerView;
            this.imageId = imageId;
            return create();
        }

        /**
         * 绑定 RecyclerView
         *
         * @param targetImageView item layout 中的 ImageView
         */
        public TransferConfig bindRecyclerView(RecyclerView recyclerView, ImageView targetImageView) {
            this.recyclerView = recyclerView;
            this.targetImageView = targetImageView;
            return create();
        }

        /**
         * 绑定 RecyclerView, 并指定 header 和 footer 的数量
         *
         * @param imageId item layout 中的 ImageView Resource ID
         */
        public TransferConfig bindRecyclerView(RecyclerView recyclerView, int headerSize, int footerSize, int imageId) {
            this.recyclerView = recyclerView;
            this.headerSize = headerSize;
            this.footerSize = footerSize;
            this.imageId = imageId;
            return create();
        }

        /**
         * 绑定单个 ImageView
         */
        public TransferConfig bindImageView(ImageView imageView) {
            this.imageView = imageView;
            return create();
        }

        /**
         * 绑定单个 ImageView, 并指定图片的 url
         */
        public TransferConfig bindImageView(ImageView imageView, String sourceUrl) {
            this.imageView = imageView;
            this.sourceUrlList = new ArrayList<>();
            sourceUrlList.add(sourceUrl);
            return create();
        }

        public TransferConfig create() {
            TransferConfig config = new TransferConfig();

            config.setNowThumbnailIndex(nowThumbnailIndex);
            config.setOffscreenPageLimit(offscreenPageLimit);
            config.setMissPlaceHolder(missPlaceHolder);
            config.setErrorPlaceHolder(errorPlaceHolder);
            config.setBackgroundColor(backgroundColor);
            config.setDuration(duration);
            config.enableJustLoadHitPage(justLoadHitPage);
            config.enableDragClose(enableDragClose);
            config.enableDragHide(enableDragHide);
            config.enableDragPause(enableDragPause);
            config.enableHideThumb(enableHideThumb);
            config.enableScrollingWithPageChange(enableScrollingWithPageChange);

            config.setMissDrawable(missDrawable);
            config.setErrorDrawable(errorDrawable);

            config.setSourceBeanList(sourceBeanList);
            config.setSourceUrlList(sourceUrlList);
            config.setSourceUriList(sourceUriList);
            config.setOriginImageList(originImageList);

            config.setProgressIndicator(progressIndicator);
            config.setIndexIndicator(indexIndicator);
            config.setImageLoader(imageLoader);
            config.setCustomView(customView);
            config.setCustomViewCallback(customViewCallback);
            config.setScrollCustomViewId(scrollCustomViewId);
            config.setScrollCustomViewCallback(scrollCustomViewCallback);
            config.setScrollImageCustomViewId(scrollImageCustomViewId);
            config.setScrollImageCustomViewCallback(scrollImageCustomViewCallback);
            config.setScrollVideoCustomViewId(scrollVideoCustomViewId);
            config.setScrollVideoCustomViewCallback(scrollVideoCustomViewCallback);
            config.setChangedCallback(changedCallback);

            config.setImageId(imageId);
            config.setImageView(imageView);
            config.setListView(listView);
            config.setRecyclerView(recyclerView);
            config.setTargetImageView(targetImageView);
            config.setHeaderSize(headerSize);
            config.setFooterSize(footerSize);

            config.setLongClickListener(longClickListener);
            return config;
        }
    }
}
