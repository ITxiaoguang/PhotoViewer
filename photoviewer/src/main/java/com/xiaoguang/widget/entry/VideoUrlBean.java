package com.xiaoguang.widget.entry;

/**
 * hxg 2021/9/25 16:45 qq:929842234
 */
public class VideoUrlBean extends BaseImgUrlBean {
    private String videoUrl;// 视频地址
    private boolean play;// 是否播放
    private String duration;// 视频总时长

    public VideoUrlBean(String url, String videoUrl, boolean play, String duration) {
        super(url);
        this.videoUrl = videoUrl;
        this.play = play;
        this.duration = duration;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public boolean isPlay() {
        return play;
    }

    public void setPlay(boolean play) {
        this.play = play;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }
}
