package com.yyxnb.module_video.utils;

import com.dueeeke.videoplayer.player.VideoView;

public class VideoManager {

    private static VideoManager videoManager;

    public static VideoManager getInstance() {
        if (videoManager == null){
            synchronized (VideoManager.class){
                if (videoManager == null){
                    videoManager = new VideoManager();
                }
            }
        }
        return videoManager;
    }

    private VideoView videoView;

    public VideoView getVideoView() {
        return videoView;
    }

    public void setVideoView(VideoView videoView) {
        this.videoView = videoView;
    }
}
