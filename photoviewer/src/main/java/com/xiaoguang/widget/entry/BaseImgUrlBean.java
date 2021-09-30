package com.xiaoguang.widget.entry;

import java.io.Serializable;
import java.util.Map;

/**
 * 图片基类
 * hxg 2021/9/25 16:45 qq:929842234
 */
public class BaseImgUrlBean implements Serializable {
    private String imageUrl;// 图片url
    private Map<String, Object> extension;// 拓展

    public BaseImgUrlBean(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public Map<String, Object> getExtension() {
        return extension;
    }

    public void setExtension(Map<String, Object> extension) {
        this.extension = extension;
    }
}
