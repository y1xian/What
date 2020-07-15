package com.yyxnb.lib_audio.core;


import android.arch.lifecycle.Observer;
import android.util.Log;

import com.jeremyliao.liveeventbus.LiveEventBus;
import com.yyxnb.lib_audio.bean.AudioBean;
import com.yyxnb.lib_audio.events.AudioEvent;
import com.yyxnb.lib_audio.exception.AudioQueueEmptyException;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

import static com.yyxnb.lib_audio.AudioConfig.EVENT_AUDIO_KEY;

/**
 * 控制播放逻辑类，注意添加一些控制方法时，要考虑是否需要增加Event,来更新UI
 */
public class AudioController {

    /**
     * 播放方式
     */
    public enum PlayMode {
        /**
         * 列表循环
         */
        LOOP,
        /**
         * 随机
         */
        RANDOM,
        /**
         * 单曲循环
         */
        REPEAT
    }

    private AudioPlayer mAudioPlayer;
    //播放队列,不能为空,不设置主动抛错
    private ArrayList<AudioBean> mQueue = new ArrayList<>();
    private int mQueueIndex = 0;
    private PlayMode mPlayMode = PlayMode.LOOP;

    private AudioController() {
        mAudioPlayer = new AudioPlayer();
        LiveEventBus.get(EVENT_AUDIO_KEY).observeForever(mAudioEvent);
    }

    private void addCustomAudio(int index, AudioBean bean) {
        if (mQueue == null) {
            throw new AudioQueueEmptyException("当前播放队列为空,请先设置播放队列.");
        }
        mQueue.add(index, bean);
    }

    private int queryAudio(AudioBean bean) {
        return mQueue.indexOf(bean);
    }

    private void load(AudioBean bean) {
        mAudioPlayer.load(bean);
    }

    /*
     * 获取播放器当前状态
     */
    private CustomMediaPlayer.Status getStatus() {
        return mAudioPlayer.getStatus();
    }

    /**
     * 下一首
     */
    private AudioBean getNextPlaying() {
        switch (mPlayMode) {
            case LOOP:
                mQueueIndex = (mQueueIndex + 1) % mQueue.size();
                return getPlaying(mQueueIndex);
            case RANDOM:
                mQueueIndex = new Random().nextInt(mQueue.size()) % mQueue.size();
                return getPlaying(mQueueIndex);
            case REPEAT:
                return getPlaying(mQueueIndex);
        }
        return null;
    }

    /**
     * 上一首
     */
    private AudioBean getPreviousPlaying() {
        switch (mPlayMode) {
            case LOOP:
                mQueueIndex = (mQueueIndex + mQueue.size() - 1) % mQueue.size();
                return getPlaying(mQueueIndex);
            case RANDOM:
                mQueueIndex = new Random().nextInt(mQueue.size()) % mQueue.size();
                return getPlaying(mQueueIndex);
            case REPEAT:
                return getPlaying(mQueueIndex);
        }
        return null;
    }

    /**
     * 选择播放第index首
     */
    private AudioBean getPlaying(int index) {
        if (mQueue != null && !mQueue.isEmpty() && index >= 0 && index < mQueue.size()) {
            return mQueue.get(index);
        } else {
            throw new AudioQueueEmptyException("当前播放队列为空,请先设置播放队列.");
        }
    }

    public static AudioController getInstance() {
        return AudioController.SingletonHolder.instance;
    }

    private static class SingletonHolder {
        private static AudioController instance = new AudioController();
    }

    /**
     * 对外提供是否播放中状态
     */
    public boolean isStartState() {
        return CustomMediaPlayer.Status.STARTED == getStatus();
    }

    /**
     * 对外提提供是否暂停状态
     */
    public boolean isPauseState() {
        return CustomMediaPlayer.Status.PAUSED == getStatus();
    }

    public ArrayList<AudioBean> getQueue() {
        return mQueue == null ? new ArrayList<>() : mQueue;
    }

    /**
     * 设置播放队列
     */
    public void setQueue(ArrayList<AudioBean> queue) {
        setQueue(queue, 0);
    }

    public void setQueue(ArrayList<AudioBean> queue, int queueIndex) {
        mQueue.clear();
        mQueue.addAll(queue);
        mQueueIndex = queueIndex;
        //通知 刷新数据源
        LiveEventBus.get(EVENT_AUDIO_KEY).post(new AudioEvent(AudioEvent.AudioState.DATA, mQueue));
    }

    public void addQueue(ArrayList<AudioBean> queue) {
        mQueue.addAll(queue);
        LiveEventBus.get(EVENT_AUDIO_KEY).post(new AudioEvent(AudioEvent.AudioState.DATA, mQueue));
    }

    /**
     * 队列头添加播放哥曲
     */
    public void addAudio(AudioBean bean) {
        this.addAudio(0, bean);
    }

    public void addAudio(int index, AudioBean bean) {
        if (mQueue == null) {
            throw new AudioQueueEmptyException("当前播放队列为空,请先设置播放队列.");
        }
        int query = queryAudio(bean);
        if (query <= -1) {
            //没添加过此id的歌曲，添加且直播番放
            addCustomAudio(index, bean);
            setPlayIndex(index);
        } else {
            AudioBean currentBean = getNowPlaying();
            if (!currentBean.url.equals(bean.url)) {
                //添加过且不是当前播放，播，否则什么也不干
                setPlayIndex(query);
            }
        }
    }

    public void setPlayIndex(int index) {
        if (mQueue == null) {
            throw new AudioQueueEmptyException("当前播放队列为空,请先设置播放队列.");
        }
        mQueueIndex = index;
        play();
    }

    /**
     * 播放模式
     */
    public PlayMode getPlayMode() {
        return mPlayMode;
    }

    /**
     * 播放模式选择
     */
    public void setPlayMode(PlayMode playMode) {
        mPlayMode = playMode;
        //还要对外发送切换事件，更新UI
        LiveEventBus.get(EVENT_AUDIO_KEY).post(new AudioEvent(AudioEvent.AudioState.PLAY_MODE, mPlayMode));
    }

    /**
     * 当前位置
     */
    public int getQueueIndex() {
        return mQueueIndex;
    }

    /**
     * 添加/移除到收藏
     */
//  public void changeFavourite() {
//    if (null != GreenDaoHelper.selectFavourite(getNowPlaying())) {
//      //已收藏，移除
//      GreenDaoHelper.removeFavourite(getNowPlaying());
//      EventBus.getDefault().post(new AudioFavouriteEvent(false));
//    } else {
//      //未收藏，添加收藏
//      GreenDaoHelper.addFavourite(getNowPlaying());
//      EventBus.getDefault().post(new AudioFavouriteEvent(true));
//    }
//  }

    /**
     * 播放/暂停切换
     */
    public void playOrPause() {
        if (isStartState()) {
            pause();
            Log.e("---", "暂停");
        } else if (isPauseState()) {
            resume();
            Log.e("---", "播放");
        } else {
            play();
            Log.e("---", "首次播放");
        }
    }

    /**
     * 加载当前index歌曲
     */
    public void play() {
        AudioBean bean = getPlaying(mQueueIndex);
        load(bean);
    }

    /**
     * 加载下一首歌曲
     */
    public void next() {
        AudioBean bean = getNextPlaying();
        load(bean);
    }

    /**
     * 加载上一首歌曲
     */
    public void previous() {
        AudioBean bean = getPreviousPlaying();
        load(bean);
    }

    /**
     * 对外提供获取当前播放时间
     */
    public int getNowPlayTime() {
        return mAudioPlayer.getCurrentPosition();
    }

    /**
     * 对外提供获取总播放时间
     */
    public int getTotalPlayTime() {
        return mAudioPlayer.getCurrentPosition();
    }

    /**
     * 对外提供的获取当前歌曲信息
     */
    public AudioBean getNowPlaying() {
        return getPlaying(mQueueIndex);
    }

    public void resume() {
        mAudioPlayer.resume();
    }

    public void pause() {
        mAudioPlayer.pause();
    }

    public void release() {
        mAudioPlayer.release();
        LiveEventBus.get(EVENT_AUDIO_KEY).removeObserver(mAudioEvent);
    }

    @SuppressWarnings("rawtypes")
    private final Observer mAudioEvent = (Observer<AudioEvent>) event -> {
        switch (Objects.requireNonNull(event).audioState) {
            //插放完毕事件处理
            case AudioEvent.AudioState.COMPLETE:
                //播放出错事件处理
            case AudioEvent.AudioState.ERROR:
                next();
                break;
            default:
                break;
        }
    };

}
