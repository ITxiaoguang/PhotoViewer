package com.xiaoguang.widget.viewer;

import android.util.Log;
import android.widget.AbsListView;
import android.widget.ImageView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vans Z on 2020/5/22.
 */
class OriginalViewHelper {
    private static final String TAG = "OriginalViewHelper";
    private TransferConfig transConfig;

    private OriginalViewHelper() {
    }

    private static class SingletonHolder {
        private final static OriginalViewHelper instance = new OriginalViewHelper();
    }

    static OriginalViewHelper getInstance() {
        return SingletonHolder.instance;
    }

    void fillOriginImages(TransferConfig config) {
        transConfig = config;
        List<ImageView> originImageList = new ArrayList<>();
        if (transConfig.getRecyclerView() != null) {
            if (null != transConfig.getTargetImageView()) {
                fillBySource(originImageList);
            } else {
                fillByRecyclerView(originImageList);
            }
        } else if (transConfig.getListView() != null) {
            fillByListView(originImageList);
        } else if (transConfig.getImageView() != null) {
            originImageList.add(transConfig.getImageView());
            int size = transConfig.getSourceSize();
            for (int i = 0; i < size - 1; i++) {
                originImageList.add(null);
            }
        }
        transConfig.setOriginImageList(originImageList);
    }

    /**
     * FIX BUG 多布局情况下，计算list出错的问题
     */
    private List<ImageView> fillBySource(final List<ImageView> originImageList) {
        RecyclerView recyclerView = transConfig.getRecyclerView();
        int childCount = recyclerView.getChildCount();
        int targetViewIndex = 0;
        int imageSize = 0;
        for (int i = 0; i < childCount; i++) {
            ImageView originImage = recyclerView.getChildAt(i)
                    .findViewById(transConfig.getTargetImageView().getId());
            if (originImage != null) {
                ++imageSize;
                // 两个view的内存相等
                if (transConfig.getTargetImageView() == originImage) {
                    targetViewIndex = imageSize;
                }
                originImageList.add(originImage);
            }
        }

        int totalCount = transConfig.getSourceSize();
        int firstPos = transConfig.getNowThumbnailIndex() + 1 - targetViewIndex;
        int lastPos = transConfig.getNowThumbnailIndex() + 1 + (originImageList.size() - targetViewIndex);

        fillPlaceHolder(originImageList, totalCount, firstPos, lastPos);

        return originImageList;
    }

    @Deprecated
    private void fillByRecyclerView(final List<ImageView> originImageList) {
        final int headerSize = transConfig.getHeaderSize();
        final int footerSize = transConfig.getFooterSize();
        RecyclerView recyclerView = transConfig.getRecyclerView();
        int childCount = recyclerView.getChildCount();
        for (int i = 0; i < childCount; i++) {
            ImageView originImage = recyclerView.getChildAt(i)
                    .findViewById(transConfig.getImageId());
            if (originImage != null)
                originImageList.add(originImage);
        }

        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        int firstPos = 0, lastPos = 0;
        // todo 这里算不对，没有把多布局过滤，需重做
        int totalCount = layoutManager.getItemCount() - headerSize - footerSize;
        if (layoutManager instanceof LinearLayoutManager) {
            LinearLayoutManager linLayMan = (LinearLayoutManager) layoutManager;
            firstPos = linLayMan.findFirstVisibleItemPosition();
            firstPos = firstPos < headerSize ? 0 : firstPos - headerSize;
            lastPos = linLayMan.findLastVisibleItemPosition();
            lastPos = lastPos > totalCount ? totalCount - 1 : lastPos - headerSize;
        }
        fillPlaceHolder(originImageList, totalCount, firstPos, lastPos);
        Log.e(TAG, String.format("totalCount = %s, firstPos = %s, lastPos = %s",
                totalCount, firstPos, lastPos));
    }

    private void fillByListView(final List<ImageView> originImageList) {
        final int headerSize = transConfig.getHeaderSize();
        final int footerSize = transConfig.getFooterSize();
        AbsListView absListView = transConfig.getListView();
        int childCount = absListView.getChildCount();
        for (int i = 0; i < childCount; i++) {
            ImageView originImage = absListView.getChildAt(i)
                    .findViewById(transConfig.getImageId());
            originImageList.add(originImage);
        }

        int firstPos = absListView.getFirstVisiblePosition() - headerSize;
        int lastPos = absListView.getLastVisiblePosition() - headerSize - footerSize;
        int totalCount = absListView.getCount() - headerSize - footerSize;
        fillPlaceHolder(originImageList, totalCount, firstPos, lastPos);
    }

    private void fillPlaceHolder(List<ImageView> originImageList, int totalCount, int firstPos, int lastPos) {
        if (firstPos > 0) {
            for (int pos = firstPos; pos > 0; pos--) {
                originImageList.add(0, null);
            }
        }
        if (lastPos < totalCount) {
            for (int i = (totalCount - 1 - lastPos); i > 0; i--) {
                originImageList.add(null);
            }
        }
    }
}
