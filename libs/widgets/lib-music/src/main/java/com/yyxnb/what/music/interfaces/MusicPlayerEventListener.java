package com.yyxnb.what.music.interfaces;


/**
 * MusicPlayer Event Listener
 */
public interface MusicPlayerEventListener<T extends IMusic> {

    /**
     * 播放器所有状态回调
     *
     * @param playerState 播放器内部状态
     */
    void onMusicPlayerState(int playerState, String message);

    /**
     * 播放器准备好了
     *
     * @param totalDuration 总时长
     */
    void onPrepared(long totalDuration);

    /**
     * 缓冲百分比
     *
     * @param percent 百分比
     */
    void onBufferingUpdate(int percent);

    /**
     * 播放器反馈信息
     *
     * @param event 事件
     * @param extra
     */
    void onInfo(int event, int extra);

    /**
     * 当前正在播放的任务
     *
     * @param musicInfo 正在播放的对象
     * @param position  当前正在播放的位置
     */

    void onPlayMusicOnInfo(T musicInfo, int position);

    /**
     * 音频地址无效,组件可处理付费购买等逻辑
     *
     * @param musicInfo 播放对象
     * @param position  索引
     */
    void onMusicPathInvalid(T musicInfo, int position);

    /**
     * @param totalDuration        音频总时间
     * @param currentDuration      当前播放的位置
     * @param alarmResidueDuration 闹钟剩余时长
     * @param bufferProgress      当前缓冲进度
     */
    void onTaskRuntime(long totalDuration, long currentDuration, long alarmResidueDuration, int bufferProgress);

    /**
     * @param playModel  播放模式
     * @param alarmModel 闹钟模式
     * @param isToast    是否吐司提示
     */
    void onPlayerConfig(int playModel, int alarmModel, boolean isToast);

}
