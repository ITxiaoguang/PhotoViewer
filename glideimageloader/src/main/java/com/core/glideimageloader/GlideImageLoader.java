package com.core.glideimageloader;

import android.content.Context;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.xiaoguang.widget.loader.ImageLoader;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Vans Z on 2020-02-28.
 * 暂不支持百分比进度指示器
 */
public class GlideImageLoader implements ImageLoader {
    private static String CACHE_DIR = "PhotoViewerGlide";

    private Context context;
    private String savePath;
    private Map<String, SourceCallback> callbackMap;

    private GlideImageLoader(Context context, String savePath) {
        this.context = context;
        this.savePath = savePath;
        this.callbackMap = new HashMap<>();
    }

    public static GlideImageLoader with(Context context) {
        return new GlideImageLoader(context, null);
    }

    public static GlideImageLoader with(Context context, String savePath) {
        return new GlideImageLoader(context, savePath);
    }

    @Override
    public void loadSource(final String imageUrl, final SourceCallback callback) {
        callbackMap.put(imageUrl, callback);
        if (callback != null) callback.onStart();
        Glide.with(context).download(imageUrl).listener(new RequestListener<File>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<File> target, boolean isFirstResource) {
                SourceCallback callback = callbackMap.get(imageUrl);
                if (callback != null)
                    callback.onDelivered(STATUS_DISPLAY_FAILED, null);
                callbackMap.remove(imageUrl);
                return false;
            }

            @Override
            public boolean onResourceReady(final File resource, Object model, Target<File> target, DataSource dataSource, boolean isFirstResource) {
                if (callback != null)
                    callback.onDelivered(STATUS_DISPLAY_SUCCESS, resource);
                callbackMap.remove(imageUrl);
                return false;
            }
        }).preload();
    }

    @Override
    public File getCache(String url) {
        File cacheFile = new File(getCacheDir(), getFileName(url));
        return cacheFile.exists() ? cacheFile : null;
    }

    @Override
    public void clearCache() {
        Glide.get(context).clearMemory();
        new Thread(new Runnable() {
            @Override
            public void run() {
                Glide.get(context).clearDiskCache();
                delete(getCacheDir());
            }
        }).start();
    }

    @Override
    public File getCacheDir() {
        File cacheDir;
        if (null != savePath) {
            cacheDir = new File(savePath);
        } else {
            cacheDir = new File(context.getCacheDir(), CACHE_DIR);
        }
        if (!cacheDir.exists()) cacheDir.mkdirs();
        return cacheDir;
    }

    private String getFileName(String imageUrl) {
        String[] nameArray = imageUrl.split("/");
        return nameArray[nameArray.length - 1];
    }


    /**
     * Delete the directory.
     *
     * @param file The file.
     * @return {@code true}: success<br>{@code false}: fail
     */
    public static boolean delete(final File file) {
        if (file == null) return false;
        if (file.isDirectory()) {
            return deleteDir(file);
        }
        return deleteFile(file);
    }

    /**
     * Delete the directory.
     *
     * @param dir The directory.
     * @return {@code true}: success<br>{@code false}: fail
     */
    private static boolean deleteDir(final File dir) {
        if (dir == null) return false;
        // dir doesn't exist then return true
        if (!dir.exists()) return true;
        // dir isn't a directory then return false
        if (!dir.isDirectory()) return false;
        File[] files = dir.listFiles();
        if (files != null && files.length != 0) {
            for (File file : files) {
                if (file.isFile()) {
                    if (!file.delete()) return false;
                } else if (file.isDirectory()) {
                    if (!deleteDir(file)) return false;
                }
            }
        }
        return dir.delete();
    }

    /**
     * Delete the file.
     *
     * @param file The file.
     * @return {@code true}: success<br>{@code false}: fail
     */
    private static boolean deleteFile(final File file) {
        return file != null && (!file.exists() || file.isFile() && file.delete());
    }
}