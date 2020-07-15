package com.yyxnb.lib_audio.core;

import android.media.MediaPlayer;

import java.io.IOException;

/**
 * 播放状态
 */
public class CustomMediaPlayer extends MediaPlayer implements MediaPlayer.OnCompletionListener {

    public enum Status {
        IDLE, INITIALIZED, STARTED, PAUSED, STOPPED, COMPLETED
    }

    private Status mState;

    private OnCompletionListener mOnCompletionListener;

    public CustomMediaPlayer() {
        super();
        mState = Status.IDLE;
        super.setOnCompletionListener(this);
    }

    @Override
    public void reset() {
        super.reset();
        mState = Status.IDLE;
    }

    @Override
    public void setDataSource(String path)
            throws IOException, IllegalArgumentException, SecurityException, IllegalStateException {
        super.setDataSource(path);
        mState = Status.INITIALIZED;
    }

    @Override
    public void start() {
        super.start();
        mState = Status.STARTED;
    }

    @Override
    public void setOnCompletionListener(OnCompletionListener listener) {
        this.mOnCompletionListener = listener;
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        mState = Status.COMPLETED;
        if (mOnCompletionListener != null) {
            mOnCompletionListener.onCompletion(mp);
        }
    }

    @Override
    public void stop() throws IllegalStateException {
        super.stop();
        mState = Status.STOPPED;
    }

    @Override
    public void pause() throws IllegalStateException {
        super.pause();
        mState = Status.PAUSED;
    }

    public void setState(Status mState) {
        this.mState = mState;
    }

    public Status getState() {
        return mState;
    }

    public boolean isComplete() {
        return mState == Status.COMPLETED;
    }
}
