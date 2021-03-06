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
     * ??????????????????????????????
     *
     * @return true : ???
     */
    public boolean isSourceEmpty() {
        return (sourceUrlList == null || sourceUrlList.isEmpty())
                && (sourceUriList == null || sourceUriList.isEmpty())
                && (sourceBeanList == null || sourceBeanList.isEmpty());
    }

    /**
     * ???????????? position ???????????????????????????
     * FIX BUG ???????????????????????????
     *
     * @param position ??? -1 ??????????????? nowThumbnailIndex
     * @return true : ???????????????
     * ????????????{@link Builder#setSourceBeanList(List< BaseImgUrlBean >)}??????????????????
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

        // ???????????????
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
        private ImageView targetImageView;// ??????ImageView

        private int headerSize;
        private int footerSize;

        private PhotoViewer.OnPhotoViewerLongClickListener longClickListener;

        /**
         * ??????????????????????????????????????????
         */
        public Builder setNowThumbnailIndex(int nowThumbnailIndex) {
            this.nowThumbnailIndex = nowThumbnailIndex;
            return this;
        }

        /**
         * <p>ViewPager ???????????????????????????????????? : ??????????????????????????????????????????????????????</p>
         * <p>?????????1, ?????????????????????3???(nowThumbnailIndex, nowThumbnailIndex
         * + 1, nowThumbnailIndex - 1);?????? 2, ????????????5??????????????????</p>
         * <p>?????????????????????????????????????????????????????????????????????????????????????????????????????????
         * ??????????????????????????????</p>
         */
        public Builder setOffscreenPageLimit(int offscreenPageLimit) {
            this.offscreenPageLimit = offscreenPageLimit;
            return this;
        }

        /**
         * ??????????????????(??????ID)
         */
        public Builder setMissPlaceHolder(int missPlaceHolder) {
            this.missPlaceHolder = missPlaceHolder;
            return this;
        }

        /**
         * ?????????????????????????????????(??????ID)
         */
        public Builder setErrorPlaceHolder(int errorPlaceHolder) {
            this.errorPlaceHolder = errorPlaceHolder;
            return this;
        }

        /**
         * ??? photoViewer ????????????????????????
         */
        public Builder setBackgroundColor(int backgroundColor) {
            this.backgroundColor = backgroundColor;
            return this;
        }

        /**
         * ??????????????????
         */
        public Builder setDuration(long duration) {
            this.duration = duration;
            return this;
        }

        /**
         * ????????????????????????????????????
         */
        public Builder enableJustLoadHitPage(boolean justLoadHitPage) {
            this.justLoadHitPage = justLoadHitPage;
            return this;
        }

        /**
         * ????????????????????????
         */
        public Builder enableDragClose(boolean enableDragClose) {
            this.enableDragClose = enableDragClose;
            return this;
        }

        /**
         * ?????????????????????????????????????????????????????? view
         */
        public Builder enableDragHide(boolean enableDragHide) {
            this.enableDragHide = enableDragHide;
            return this;
        }

        /**
         * ???????????????????????????????????????????????????
         */
        public Builder enableDragPause(boolean enableDragPause) {
            this.enableDragPause = enableDragPause;
            return this;
        }

        /**
         * ??????????????? photoViewer ???????????????????????????
         */
        public Builder enableHideThumb(boolean enableHideThumb) {
            this.enableHideThumb = enableHideThumb;
            return this;
        }

        /**
         * ???????????????????????? page ?????????????????????????????????????????????
         * RecyclerView/GridView/ListView ??????, ????????????
         * ??????????????????????????????????????? view ????????????????????????
         * ??????????????????????????? photoViewer ????????????????????????,
         * ?????????????????????????????????
         */
        public Builder enableScrollingWithPageChange(boolean enableScrollingWithPageChange) {
            this.enableScrollingWithPageChange = enableScrollingWithPageChange;
            return this;
        }

        /**
         * ???????????????????????????????????????
         */
        public Builder autoAdjustDirection(boolean autoAdjustDirection) {
            this.autoAdjustDirection = autoAdjustDirection;
            return this;
        }

        /**
         * ??????????????????(Drawable ??????)
         */
        public Builder setMissDrawable(Drawable missDrawable) {
            this.missDrawable = missDrawable;
            return this;
        }

        /**
         * ?????????????????????????????????(Drawable ??????)
         */
        public Builder setErrorDrawable(Drawable errorDrawable) {
            this.errorDrawable = errorDrawable;
            return this;
        }

        /**
         * ??????????????????????????????
         */
        public Builder setSourceBeanList(List<BaseImgUrlBean> sourceBeanList) {
            this.sourceBeanList = sourceBeanList;
            return this;
        }

        /**
         * ????????????????????????
         * format: java.lang.String
         */
        public Builder setSourceUrlList(List<String> sourceUrlList) {
            this.sourceUrlList = sourceUrlList;
            return this;
        }

        /**
         * ????????????????????????
         * format: android.net.Uri
         */
        public Builder setSourceUriList(List<Uri> sourceUriList) {
            this.sourceUriList = sourceUriList;
            return this;
        }

        /**
         * ?????????????????? ImageView ??????
         */
        public Builder setOriginImageList(List<ImageView> originImageList) {
            this.originImageList = originImageList;
            return this;
        }

        /**
         * ??????????????????????????? (???????????? ProgressPieIndicator), ????????????
         * IProgressIndicator ??????????????????????????????????????????
         */
        public Builder setProgressIndicator(IProgressIndicator progressIndicator) {
            this.progressIndicator = progressIndicator;
            return this;
        }

        /**
         * ????????????????????? (?????? ?????????), ????????????
         * IIndexIndicator ??????????????????????????????????????????
         */
        public Builder setIndexIndicator(IIndexIndicator indexIndicator) {
            this.indexIndicator = indexIndicator;
            return this;
        }

        /**
         * ??????????????? (???????????? GlideImageLoader), ????????????
         * ImageLoader ????????????????????????????????????
         */
        public Builder setImageLoader(ImageLoader imageLoader) {
            this.imageLoader = imageLoader;
            return this;
        }

        /**
         * ?????? photoViewer ?????????????????????
         */
        public Builder setOnLongClickListener(PhotoViewer.OnPhotoViewerLongClickListener listener) {
            this.longClickListener = listener;
            return this;
        }

        /**
         * ??? photoViewer ????????????????????????????????????
         * ????????????
         *
         * @param customView ?????????view
         */
        public Builder setCustomView(View customView) {
            this.customView = customView;
            return this;
        }

        /**
         * ??? photoViewer ????????????????????????????????????
         * ????????????
         *
         * @param customView         ?????????view
         * @param customViewCallback ?????????view??????
         */
        public Builder setCustomView(View customView, CustomViewCallback customViewCallback) {
            this.customView = customView;
            this.customViewCallback = customViewCallback;
            return this;
        }

        /**
         * ??? photoViewer ????????????????????????????????????
         * ????????????????????????????????????
         */
        public Builder setScrollCustomView(int scrollCustomViewId, CustomViewCallback scrollCustomViewCallback) {
            this.scrollCustomViewId = scrollCustomViewId;
            this.scrollCustomViewCallback = scrollCustomViewCallback;
            return this;
        }

        /**
         * ??? photoViewer ????????????????????????????????????
         * ????????????????????????????????????
         */
        public Builder setScrollImageCustomView(int scrollImageCustomViewID, CustomViewCallback scrollImageCustomViewCallback) {
            this.scrollImageCustomViewId = scrollImageCustomViewID;
            this.scrollImageCustomViewCallback = scrollImageCustomViewCallback;
            return this;
        }

        /**
         * ??? photoViewer ????????????????????????????????????
         * ????????????????????????????????????
         */
        public Builder setScrollVideoCustomView(int scrollVideoCustomViewId, CustomViewCallback scrollVideoCustomViewCallback) {
            this.scrollVideoCustomViewId = scrollVideoCustomViewId;
            this.scrollVideoCustomViewCallback = scrollVideoCustomViewCallback;
            return this;
        }

        /**
         * ViewPager ??????????????????
         */
        public Builder setChangedCallback(ChangedCallback changedCallback) {
            this.changedCallback = changedCallback;
            return this;
        }

        /**
         * ?????? ListView
         *
         * @param imageId item layout ?????? ImageView Resource ID
         */
        public TransferConfig bindListView(AbsListView listView, int imageId) {
            this.listView = listView;
            this.imageId = imageId;
            return create();
        }

        /**
         * ?????? ListView, ????????? header ??? footer ?????????
         *
         * @param imageId item layout ?????? ImageView Resource ID
         */
        public TransferConfig bindListView(AbsListView listView, int headerSize, int footerSize, int imageId) {
            this.listView = listView;
            this.headerSize = headerSize;
            this.footerSize = footerSize;
            this.imageId = imageId;
            return create();
        }

        /**
         * ?????? RecyclerView
         * ???imageId???????????????????????????
         *
         * @param imageId item layout ?????? ImageView Resource ID
         * @see #bindRecyclerView(RecyclerView, ImageView)
         */
        @Deprecated
        public TransferConfig bindRecyclerView(RecyclerView recyclerView, int imageId) {
            this.recyclerView = recyclerView;
            this.imageId = imageId;
            return create();
        }

        /**
         * ?????? RecyclerView
         *
         * @param targetImageView item layout ?????? ImageView
         */
        public TransferConfig bindRecyclerView(RecyclerView recyclerView, ImageView targetImageView) {
            this.recyclerView = recyclerView;
            this.targetImageView = targetImageView;
            return create();
        }

        /**
         * ?????? RecyclerView, ????????? header ??? footer ?????????
         *
         * @param imageId item layout ?????? ImageView Resource ID
         */
        public TransferConfig bindRecyclerView(RecyclerView recyclerView, int headerSize, int footerSize, int imageId) {
            this.recyclerView = recyclerView;
            this.headerSize = headerSize;
            this.footerSize = footerSize;
            this.imageId = imageId;
            return create();
        }

        /**
         * ???????????? ImageView
         */
        public TransferConfig bindImageView(ImageView imageView) {
            this.imageView = imageView;
            return create();
        }

        /**
         * ???????????? ImageView, ?????????????????? url
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
