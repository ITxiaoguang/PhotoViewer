package com.xiaoguang.widget.entry;

/**
 * 带缩略图实体
 * hxg 2021/9/25 16:45 qq:929842234
 */
public class ThumbImgUrlBean extends BaseImgUrlBean {

    private String thumbUrl;// 图片缓存地址

    public ThumbImgUrlBean(String url, String thumbUrl) {
        super(url);
        this.thumbUrl = thumbUrl;
    }

    public String getThumbUrl() {
        return thumbUrl;
    }

    public void setThumbUrl(String thumbUrl) {
        this.thumbUrl = thumbUrl;
    }
}
