package com.yyxnb.what.music.service;

import android.app.Notification;
import android.os.Binder;

import com.yyxnb.what.music.interfaces.IMusic;
import com.yyxnb.what.music.interfaces.IMusicPlayer;
import com.yyxnb.what.music.interfaces.MusicConstants;
import com.yyxnb.what.music.interfaces.MusicPlayerEventListener;
import com.yyxnb.what.music.interfaces.MusicPlayerInfoListener;

import java.util.List;

/**
 * Music Service Binder
 * MusicPlayerService 的中间代理人
 */
public class MusicPlayerBinder extends Binder {

    private final IMusicPlayer iMusicPlayer;

    public MusicPlayerBinder(IMusicPlayer presenter) {
        this.iMusicPlayer = presenter;
    }

    public <T extends IMusic> void setMusic(List<T> musicList, int position) {
        if (null != iMusicPlayer) {
            iMusicPlayer.setMusic(musicList, position);
        }
    }

    public <T extends IMusic> void startPlayMusic(List<T> musicList, int position) {
        if (null != iMusicPlayer) {
            iMusicPlayer.startPlayMusic(musicList, position);
        }
    }

    public void setLockForeground(boolean enable) {
        if (null != iMusicPlayer) {
            iMusicPlayer.setLockForeground(enable);
        }
    }

    public void setNotificationEnable(boolean notificationEnable) {
        if (null != iMusicPlayer) {
            iMusicPlayer.setNotificationEnable(notificationEnable);
        }
    }

    public void setPlayerActivityName(String className) {
        if (null != iMusicPlayer) {
            iMusicPlayer.setPlayerActivityName(className);
        }
    }

    public void setLockActivityName(String className) {
        if (null != iMusicPlayer) {
            iMusicPlayer.setLockActivityName(className);
        }
    }

    public void startPlayMusic(int position) {
        if (null != iMusicPlayer) {
            iMusicPlayer.startPlayMusic(position);
        }
    }

    public <T extends IMusic> void addPlayMusicToTop(T audioInfo) {
        if (null != iMusicPlayer) {
            iMusicPlayer.addPlayMusicToTop(audioInfo);
        }
    }

    public void playOrPause() {
        if (null != iMusicPlayer) {
            iMusicPlayer.playOrPause();
        }
    }

    public void pause() {
        if (null != iMusicPlayer) {
            iMusicPlayer.pause();
        }
    }

    public void play() {
        if (null != iMusicPlayer) {
            iMusicPlayer.play();
        }
    }

    public void setLoop(boolean loop) {
        if (null != iMusicPlayer) {
            iMusicPlayer.setLoop(loop);
        }
    }

    public void continuePlay(String sourcePath) {
        if (null != iMusicPlayer) {
            iMusicPlayer.continuePlay(sourcePath);
        }
    }

    public void continuePlay(String sourcePath, int position) {
        if (null != iMusicPlayer) {
            iMusicPlayer.continuePlay(sourcePath, position);
        }
    }

    public void onReset() {
        if (null != iMusicPlayer) {
            iMusicPlayer.onReset();
        }
    }

    public void onStop() {
        if (null != iMusicPlayer) {
            iMusicPlayer.onStop();
        }
    }

    public <T extends IMusic> void updateMusicPlayerData(List<T> audios, int index) {
        if (null != iMusicPlayer) {
            iMusicPlayer.updateMusicPlayerData(audios, index);
        }
    }

    public int setPlayerModel(int model) {
        if (null != iMusicPlayer) {
            return iMusicPlayer.setPlayerModel(model);
        }
        return MusicConstants.MUSIC_MODEL_LOOP;
    }

    public int getPlayerModel() {
        if (null != iMusicPlayer) {
            return iMusicPlayer.getPlayerModel();
        }
        return MusicConstants.MUSIC_MODEL_LOOP;
    }

    public int setPlayerAlarmModel(int model) {
        if (null != iMusicPlayer) {
            return iMusicPlayer.setPlayerAlarmModel(model);
        }
        return MusicConstants.MUSIC_ALARM_MODEL_0;
    }

    public void onSeekTo(long currentTime) {
        if (null != iMusicPlayer) {
            iMusicPlayer.seekTo(currentTime);
        }
    }


    public void playLastMusic() {
        if (null != iMusicPlayer) {
            iMusicPlayer.playLastMusic();
        }
    }

    public void playNextMusic() {
        if (null != iMusicPlayer) {
            iMusicPlayer.playNextMusic();
        }
    }

    public int playLastIndex() {
        if (null != iMusicPlayer) {
            return iMusicPlayer.playLastIndex();
        }
        return -1;
    }

    public int playNextIndex() {
        if (null != iMusicPlayer) {
            return iMusicPlayer.playNextIndex();
        }
        return -1;
    }

    public int playRandomNextIndex() {
        if (null != iMusicPlayer) {
            return iMusicPlayer.playRandomNextIndex();
        }
        return -1;
    }

    public boolean isPlaying() {
        if (null != iMusicPlayer) {
            return iMusicPlayer.isPlaying();
        }
        return false;
    }

    public long getDuration() {
        if (null != iMusicPlayer) {
            return iMusicPlayer.getDuration();
        }
        return 0;
    }

    public String getCurrentPlayerId() {
        if (null != iMusicPlayer) {
            return iMusicPlayer.getCurrentPlayerId();
        }
        return "";
    }

    public <T extends IMusic> T getCurrentPlayerMusic() {
        if (null != iMusicPlayer) {
            return iMusicPlayer.getCurrentPlayerMusic();
        }
        return null;
    }

    public <T extends IMusic> List<T> getCurrentPlayList() {
        if (null != iMusicPlayer) {
            return iMusicPlayer.getCurrentPlayList();
        }
        return null;
    }

    public void setPlayingChannel(int channel) {
        if (null != iMusicPlayer) {
            iMusicPlayer.setPlayingChannel(channel);
        }
    }


    public int getPlayingChannel() {
        if (null != iMusicPlayer) {
            return iMusicPlayer.getPlayingChannel();
        }
        return MusicConstants.CHANNEL_NET;
    }


    public void onCheckedPlayerConfig() {
        if (null != iMusicPlayer) {
            iMusicPlayer.onCheckedPlayerConfig();
        }
    }

    public void onCheckedCurrentPlayTask() {
        if (null != iMusicPlayer) {
            iMusicPlayer.onCheckedCurrentPlayTask();
        }
    }

    public int getPlayerState() {
        if (null != iMusicPlayer) {
            return iMusicPlayer.getPlayerState();
        }
        return 0;
    }

    public void changedPlayerPlayModel() {
        if (null != iMusicPlayer) {
            iMusicPlayer.changedPlayerPlayModel();
        }
    }

    public void addOnPlayerEventListener(MusicPlayerEventListener listener) {
        if (null != iMusicPlayer) {
            iMusicPlayer.addOnPlayerEventListener(listener);
        }
    }

    public void removePlayerListener(MusicPlayerEventListener listener) {
        if (null != iMusicPlayer) {
            iMusicPlayer.removePlayerListener(listener);
        }
    }

    public void removeAllPlayerListener() {
        if (null != iMusicPlayer) {
            iMusicPlayer.removeAllPlayerListener();
        }
    }

    public void setPlayInfoListener(MusicPlayerInfoListener listener) {
        if (null != iMusicPlayer) {
            iMusicPlayer.setPlayInfoListener(listener);
        }
    }

    public void removePlayInfoListener() {
        if (null != iMusicPlayer) {
            iMusicPlayer.removePlayInfoListener();
        }
    }

    public int getPlayerAlarmModel() {
        if (null != iMusicPlayer) {
            return iMusicPlayer.getPlayerAlarmModel();
        }
        return MusicConstants.MUSIC_ALARM_MODEL_0;
    }

    public void createMiniJukeboxWindow() {
        if (null != iMusicPlayer) {
            iMusicPlayer.createMiniJukeboxWindow();
        }
    }

    public void startServiceForeground() {
        if (null != iMusicPlayer) {
            iMusicPlayer.startServiceForeground();
        }
    }

    public void startServiceForeground(Notification notification) {
        if (null != iMusicPlayer) {
            iMusicPlayer.startServiceForeground(notification);
        }
    }

    public void startServiceForeground(Notification notification, int notifiid) {
        if (null != iMusicPlayer) {
            iMusicPlayer.startServiceForeground(notification, notifiid);
        }
    }

    public void stopServiceForeground() {
        if (null != iMusicPlayer) {
            iMusicPlayer.stopServiceForeground();
        }
    }

    public void createWindow() {
        if (null != iMusicPlayer) {
            iMusicPlayer.createWindow();
        }
    }

    public void startNotification() {
        if (null != iMusicPlayer) {
            iMusicPlayer.startNotification();
        }
    }

    public void startNotification(Notification notification) {
        if (null != iMusicPlayer) {
            iMusicPlayer.startNotification(notification);
        }
    }

    public void startNotification(Notification notification, int notifiid) {
        if (null != iMusicPlayer) {
            iMusicPlayer.startNotification(notification, notifiid);
        }
    }

    public void updateNotification(int state) {
        if (null != iMusicPlayer) {
            iMusicPlayer.updateNotification(state);
        }
    }

    public void cleanNotification() {
        if (null != iMusicPlayer) {
            iMusicPlayer.cleanNotification();
        }
    }
}