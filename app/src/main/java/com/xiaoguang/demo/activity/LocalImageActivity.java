package com.xiaoguang.demo.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.xiaoguang.widget.style.index.NumberIndexIndicator;
import com.xiaoguang.widget.style.progress.ProgressBarIndicator;
import com.xiaoguang.widget.transfer.TransferConfig;
import com.core.glideimageloader.GlideImageLoader;
import com.xiaoguang.demo.R;
import com.xiaoguang.demo.SourceConfig;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;

/**
 * 使用 GlideImageLoader 演示
 */
public class LocalImageActivity extends BaseActivity {
    private List<String> images;

    @Override
    protected int getContentView() {
        return R.layout.activity_grid_view;
    }

    @Override
    protected void initView() {
        gvImages = findViewById(R.id.gv_images);
    }

    @Override
    protected void testTransferee() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{
                            Manifest.permission.READ_EXTERNAL_STORAGE},
                    READ_EXTERNAL_STORAGE);
        } else {
            images = SourceConfig.getLatestPhotoPaths(this, 99);
            if (images != null && !images.isEmpty()) {
                initTransfereeConfig();
                gvImages.setAdapter(new NineGridAdapter());
            }
        }
    }

    private void initTransfereeConfig() {
        config = TransferConfig.build()
                .setSourceUrlList(images)
                .setMissPlaceHolder(R.mipmap.ic_empty_photo)
                .setErrorPlaceHolder(R.mipmap.ic_empty_photo)
                .setProgressIndicator(new ProgressBarIndicator())
                .setIndexIndicator(new NumberIndexIndicator())
                .setImageLoader(GlideImageLoader.with(getApplicationContext()))
                .enableJustLoadHitPage(true)
                .bindListView(gvImages, R.id.iv_thum);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == READ_EXTERNAL_STORAGE) {
            images = SourceConfig.getLatestPhotoPaths(this, 99);
            if (images != null && !images.isEmpty()) {
                initTransfereeConfig();
                gvImages.setAdapter(new NineGridAdapter());
            }
        } else {
            Toast.makeText(this, "请允许获取相册图片文件访问权限", Toast.LENGTH_SHORT).show();
        }
    }

    private class NineGridAdapter extends CommonAdapter<String> {

        public NineGridAdapter() {
            super(LocalImageActivity.this, R.layout.item_image, images);
        }

        @Override
        protected void convert(ViewHolder viewHolder, String item, final int position) {
            final ImageView imageView = viewHolder.getView(R.id.iv_thum);
            Glide.with(imageView)
                    .load(item)
                    .placeholder(R.mipmap.ic_empty_photo)
                    .into(imageView);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    config.setNowThumbnailIndex(position);
                    transferee.apply(config).show();
                }
            });
        }
    }

}
