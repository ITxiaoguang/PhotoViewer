package com.xiaoguang.widget.viewer;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

import android.content.Context;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.xiaoguang.widget.callback.CustomViewCallback;
import com.xiaoguang.widget.view.image.TransferImage;
import com.xiaoguang.widget.view.video.ExoVideoView;

/**
 * 展示大图组件 ViewPager 的图片数据适配器
 * <p>
 * Created by Vans Z on 2017/1/23.
 * <p>
 * email: 196425254@qq.com
 */
class TransferAdapter extends PagerAdapter {
    private TransferLayout viewer;
    private int showIndex;
    private int imageSize;

    private OnInstantiateItemListener onInstantListener;
    private SparseArray<FrameLayout> containLayoutArray;

    TransferAdapter(TransferLayout viewer, int imageSize, int nowThumbnailIndex) {
        this.viewer = viewer;
        this.imageSize = imageSize;
        this.showIndex = nowThumbnailIndex + 1 == imageSize
                ? nowThumbnailIndex - 1 : nowThumbnailIndex + 1;
        this.showIndex = Math.max(showIndex, 0);
        containLayoutArray = new SparseArray<>();
    }

    @Override
    public int getCount() {
        return imageSize;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
        containLayoutArray.remove(position);
        viewer.loadedIndexSet.remove(position);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        // ViewPager instantiateItem 顺序：按 position 递减 OffscreenPageLimit，
        // 再从 position 递增 OffscreenPageLimit 的次序创建页面

        FrameLayout parentLayout = containLayoutArray.get(position);
        if (parentLayout == null) {
            parentLayout = newParentLayout(container, position);
            containLayoutArray.append(position, parentLayout);

            if (position == showIndex && onInstantListener != null)
                onInstantListener.onComplete();
        }
        container.addView(parentLayout);
        return parentLayout;
    }

    @NonNull
    private FrameLayout newParentLayout(ViewGroup container, final int pos) {
        Context context = container.getContext();
        TransferConfig config = viewer.getTransConfig();

        View contentView;
        if (config.isVideoSource(pos)) {
            FrameLayout.LayoutParams centerLp = new FrameLayout.LayoutParams(MATCH_PARENT, WRAP_CONTENT);
            centerLp.gravity = Gravity.CENTER;
            contentView = new ExoVideoView(context);
            contentView.setLayoutParams(centerLp);
        } else {
            // create inner ImageView
            TransferImage imageView = new TransferImage(context);
            imageView.setDuration(config.getDuration());
            imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
            imageView.setLayoutParams(new FrameLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT));
            if (config.isJustLoadHitPage())
                viewer.getTransferState(pos).prepareTransfer(imageView, pos);
            contentView = imageView;
        }

        // create outer ParentLayout
        FrameLayout parentLayout = new FrameLayout(context);
        parentLayout.setLayoutParams(new FrameLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT));
        parentLayout.addView(contentView);

        return parentLayout;
    }

    /**
     * 获取指定索引页面中的 TransferImage
     *
     * @param position position
     */
    TransferImage getImageItem(int position) {
        FrameLayout parentLayout = containLayoutArray.get(position);
        if (parentLayout != null && parentLayout.getChildAt(0) instanceof TransferImage) {
            return ((TransferImage) parentLayout.getChildAt(0));
        }
        return null;
    }

    /**
     * 获取指定索引页面中的 TransferImage
     *
     * @param config   config
     * @param position pos
     */
    TransferImage getImageItem(int position, TransferConfig config) {
        FrameLayout parentLayout = containLayoutArray.get(position);
        if (parentLayout != null) {
            if (0 != config.getScrollCustomViewId()) {
                View inflate = LayoutInflater.from(parentLayout.getContext()).inflate(config.getScrollCustomViewId(), null);
                FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT);
                inflate.setLayoutParams(lp);
                parentLayout.addView(inflate, parentLayout.getChildCount());
                CustomViewCallback callback = config.getScrollCustomViewCallback();
                if (null != callback) {
                    callback.onBindView(inflate, config);
                }
            }
            if (0 != config.getScrollImageCustomViewId()) {
                View inflate = LayoutInflater.from(parentLayout.getContext()).inflate(config.getScrollImageCustomViewId(), null);
                FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT);
                inflate.setLayoutParams(lp);
                parentLayout.addView(inflate, parentLayout.getChildCount());
                CustomViewCallback callback = config.getScrollImageCustomViewCallback();
                if (null != callback) {
                    callback.onBindView(inflate, config);
                }
            }
        }
        if (parentLayout != null && parentLayout.getChildAt(0) instanceof TransferImage) {
            return ((TransferImage) parentLayout.getChildAt(0));
        }
        return null;
    }

    /**
     * 获取指定页面中的 ExoVideoView
     *
     * @param position position
     */
    ExoVideoView getVideoItem(int position) {
        FrameLayout parentLayout = containLayoutArray.get(position);
        if (parentLayout != null && parentLayout.getChildAt(0) instanceof ExoVideoView) {
            return ((ExoVideoView) parentLayout.getChildAt(0));
        }
        return null;
    }

    /**
     * 获取指定页面中的 ExoVideoView
     *
     * @param config   config
     * @param position position
     */
    ExoVideoView getVideoItem(int position, TransferConfig config) {
        FrameLayout parentLayout = containLayoutArray.get(position);
        if (parentLayout != null) {
            if (0 != config.getScrollCustomViewId()) {
                View inflate = LayoutInflater.from(parentLayout.getContext()).inflate(config.getScrollCustomViewId(), null);
                FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT);
                inflate.setLayoutParams(lp);
                parentLayout.addView(inflate, parentLayout.getChildCount());
                CustomViewCallback callback = config.getScrollCustomViewCallback();
                if (null != callback) {
                    callback.onBindView(inflate, config);
                }
            }
            if (0 != config.getScrollVideoCustomViewId()) {
                View inflate = LayoutInflater.from(parentLayout.getContext()).inflate(config.getScrollVideoCustomViewId(), null);
                FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT);
                inflate.setLayoutParams(lp);
                parentLayout.addView(inflate, parentLayout.getChildCount());
                CustomViewCallback callback = config.getScrollVideoCustomViewCallback();
                if (null != callback) {
                    callback.onBindView(inflate, config);
                }
            }
        }
        if (parentLayout != null && parentLayout.getChildAt(0) instanceof ExoVideoView) {
            return ((ExoVideoView) parentLayout.getChildAt(0));
        }
        return null;
    }

    SparseArray<FrameLayout> getCacheItems() {
        return containLayoutArray;
    }

    FrameLayout getParentItem(int position) {
        return containLayoutArray.get(position);
    }

    void setOnInstantListener(OnInstantiateItemListener listener) {
        this.onInstantListener = listener;
    }

    interface OnInstantiateItemListener {
        void onComplete();
    }

}
