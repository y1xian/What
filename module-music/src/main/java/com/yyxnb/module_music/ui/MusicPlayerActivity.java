package com.yyxnb.module_music.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;
import androidx.databinding.DataBindingUtil;

import com.yyxnb.common_base.base.BaseActivity;
import com.yyxnb.module_music.R;
import com.yyxnb.module_music.bean.MusicBean;
import com.yyxnb.module_music.bean.MusicRecordBean;
import com.yyxnb.module_music.config.MusicSetting;
import com.yyxnb.module_music.databinding.ActivityMusicPlayerBinding;
import com.yyxnb.module_music.db.MusicDatabase;
import com.yyxnb.what.music.MusicPlayerManager;
import com.yyxnb.what.music.interfaces.MusicConstants;
import com.yyxnb.what.music.interfaces.MusicPlayerEventListener;
import com.yyxnb.what.music.utils.MusicStatus;
import com.yyxnb.what.music.utils.MusicSubjectObservable;
import com.yyxnb.what.music.utils.MusicUtils;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

/**
 * 播放音乐Activity
 */
public class MusicPlayerActivity extends BaseActivity implements View.OnClickListener, MusicPlayerEventListener<MusicBean>, Observer {

    private ActivityMusicPlayerBinding binding;

    private SeekBar mSeekBar;
    private ImageView mMusicBtnPlayPause, mMusicPlayerModel, mBtnCollect;
    private TextView mViewTitle, mTotalTime, mCurrentTime, mMusicAlarm, mSubTitle;

    private Handler mHandler;
    //手指是否正在控制seekBar
    private boolean isTouchSeekBar = false;
    private boolean isVisibility = true;

    private MusicBean mMusicBean;
    private MusicRecordBean mRecordBean;

    public static void start(Activity context) {
        Intent intent = new Intent(context, MusicPlayerActivity.class);
        ActivityCompat.startActivity(context, intent,
                ActivityOptionsCompat.makeSceneTransitionAnimation(context).toBundle());
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_music_player);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mSeekBar = binding.musicSeekBar;
        mMusicBtnPlayPause = binding.musicBtnPlayPause;
        mMusicPlayerModel = binding.musicBtnModel;
        mBtnCollect = binding.musicTopCollect;
        mViewTitle = binding.musicTitle;
        mTotalTime = binding.musicTotalTime;
        mCurrentTime = binding.musicCurrentTime;
//        mMusicAlarm = binding.musicCurrentTime;
        mSubTitle = binding.musicSubTitle;

        try {
            mRecordBean = MusicDatabase.getInstance().recordDao().getLastRecord();
            mMusicBean = mRecordBean.musicBean;
            mViewTitle.setText(mMusicBean.title);
            mSubTitle.setText(mMusicBean.author);
            mSeekBar.setSecondaryProgress(Integer.parseInt(mRecordBean.musicBean.totalTime));
            mSeekBar.setProgress((int) mRecordBean.currenTime);

            mCurrentTime.setText(MusicUtils.getInstance().stringForAudioTime(mRecordBean.currenTime));
            mTotalTime.setText(MusicUtils.getInstance().stringForAudioTime(Integer.parseInt(mRecordBean.musicBean.totalTime)));

            log("" + mRecordBean);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //注册播放器状态监听器
            MusicPlayerManager.getInstance().addOnPlayerEventListener(this);
            //注册观察者模式，主要处理收藏事件
            MusicPlayerManager.getInstance().addObservable(this);
        }


//        getIntentParams(getIntent(), true);


    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
//        getIntentParams(intent, false);

    }

    @Override
    public void initViewData() {

        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    long durtion = MusicPlayerManager.getInstance().getDuration();
                    if (durtion > 0) {
                        mCurrentTime.setText(MusicUtils.getInstance().stringForAudioTime(
                                progress * durtion / 100));
                    }
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                isTouchSeekBar = true;
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                isTouchSeekBar = false;
                long duration = MusicPlayerManager.getInstance().getDuration();
                if (duration > 0) {
                    long currentTime = seekBar.getProgress() * duration / 100;
                    MusicPlayerManager.getInstance().seekTo(currentTime);
                }
            }
        });

        findViewById(R.id.music_back).setOnClickListener(this);
        findViewById(R.id.music_btn_model).setOnClickListener(this);
        findViewById(R.id.music_btn_last).setOnClickListener(this);
        findViewById(R.id.music_btn_play_pause).setOnClickListener(this);
        findViewById(R.id.music_btn_next).setOnClickListener(this);
        findViewById(R.id.music_btn_menu).setOnClickListener(this);
    }

    @Override
    public void initObservable() {
        MusicDatabase.getInstance().recordDao().getLastRecord();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (MusicPlayerManager.getInstance().isPlaying()) {
            if (null != mMusicBtnPlayPause) {
                mMusicBtnPlayPause.setImageResource(
                        R.drawable.ic_music_pause_noimal);
            }
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        //播放模式
        if (id == R.id.music_btn_model) {
            MusicPlayerManager.getInstance().changedPlayerPlayModel();
            //上一首
        } else if (id == R.id.music_btn_last) {
            //                int lastPosition = MusicPlayerManager.getInstance().playLastIndex();
//                setCurrentMusicItem(lastPosition);
            MusicPlayerManager.getInstance().playLastMusic();
            //开始、暂停
        } else if (id == R.id.music_btn_play_pause) {
            log("MusicSetting.IS_FIRST_PLAY : " + MusicSetting.IS_FIRST_PLAY);

            if (MusicSetting.IS_FIRST_PLAY) {
                MusicSetting.IS_FIRST_PLAY = false;
                List<MusicBean> beans = MusicDatabase.getInstance().musicDao().getMusicList();
                MusicPlayerManager.getInstance().setMusic(beans, MusicUtils.getInstance().getCurrentPlayIndex(beans, mMusicBean.mid));
                if (mRecordBean.currenTime > 0) {
                    MusicPlayerManager.getInstance().play();
                    MusicPlayerManager.getInstance().seekTo(mRecordBean.currenTime);
                } else {
                    MusicPlayerManager.getInstance().play();
                }
            } else {
                MusicPlayerManager.getInstance().playOrPause();
            }

            //下一首
        } else if (id == R.id.music_btn_next) {
            //                int nextPosition = MusicPlayerManager.getInstance().playNextIndex();
//                setCurrentMusicItem(nextPosition);
            MusicPlayerManager.getInstance().playNextMusic();
            //菜单
        } else if (id == R.id.music_btn_menu) {
            //                MusicPlayerListDialog.getInstance(MusicPlayerActivity.this).
//                        setMusicOnItemClickListener(new MusicOnItemClickListener() {
//                            @Override
//                            public void onItemClick(View view, int posotion, long musicID) {
//                                setCurrentMusicItem(posotion);
//                            }
//                        }).show();
            //闹钟定时
//            case R.id.music_btn_alarm:
//                MusicAlarmSettingDialog.getInstance(MusicPlayerActivity.this).
//                        setOnAlarmModelListener(new MusicAlarmSettingDialog.OnAlarmModelListener() {
//                            @Override
//                            public void onAlarmModel(int alarmModel) {
//                                final int musicAlarmModel =
//                                        MusicPlayerManager.getInstance().setPlayerAlarmModel(alarmModel);
//                                getHandler().post(new Runnable() {
//                                    @Override
//                                    public void run() {
//                                        setPlayerConfig(-1, musicAlarmModel, true);
//                                    }
//                                });
//                            }
//                        }).show();
//                break;
            //关闭
        } else if (id == R.id.music_back) {
            //                onBackOutPlayer();
            finish();
            //收藏
        } else if (id == R.id.music_top_collect) {
            //                if (null != mMusicJukeBoxView && null != mMusicJukeBoxView.getCurrentMedia()) {
//                    BaseAudioInfo currentMedia = mMusicJukeBoxView.getCurrentMedia();
//                    if (mBtnCollect.isSelected()) {
//                        boolean isSuccess = MusicPlayerManager.getInstance().unCollectMusic(currentMedia.getAudioId());
//                        if (isSuccess) {
//                            mBtnCollect.setSelected(false);
//                        }
//                    } else {
//                        boolean isSuccess = MusicPlayerManager.getInstance().collectMusic(currentMedia);
//                        if (isSuccess) {
//                            mBtnCollect.setSelected(true);
//                            MusicPlayerManager.getInstance().observerUpdata(new MusicStatus());
//                        }
//                    }
//                }
        }
    }

    /**
     * 解析意图
     *
     * @param intent
     * @param isOnCreate 是否是初始化
     */
    private void getIntentParams(Intent intent, boolean isOnCreate) {
        //Music对象
        String musicId = intent.getStringExtra(MusicConstants.KEY_MUSIC_ID);
        if (musicId.isEmpty()) {
            finish();
            return;
        }

        //正在播放的对象
        MusicBean currentPlayerMusic = MusicPlayerManager.getInstance().getCurrentPlayerMusic();
        //点击了通知栏回显
        if (!isOnCreate && null != currentPlayerMusic && currentPlayerMusic.audioId().equals(musicId)) {
            return;
        }
        MusicPlayerManager.getInstance().onCheckedPlayerConfig();//检查播放器配置
        String musicList = intent.getStringExtra(MusicConstants.KEY_MUSIC_LIST);
        if (!TextUtils.isEmpty(musicList)) {
//            MusicParams params = new Gson().fromJson(musicList, new TypeToken<MusicParams>() {
//            }.getType());
//            if (null != params && null != params.getAudioInfos()) {
//                final List<AudioInfo> thisMusicLists = new ArrayList<>();
//                thisMusicLists.addAll(params.getAudioInfos());
//                final int index = MusicUtils.getInstance().getCurrentPlayIndex(thisMusicLists, musicID);
//                if (null != currentPlayerMusic && currentPlayerMusic.getAudioId() == musicID &&
//                        MusicPlayerManager.getInstance().getPlayerState() == MusicConstants.MUSIC_PLAYER_PLAYING) {
//                    //更新播放器内部数据
//                    MusicPlayerManager.getInstance().updateMusicPlayerData(thisMusicLists, index);
//                    onStatusResume(musicID);
//                } else {
//                    MusicPlayerManager.getInstance().onReset();
//                    if (null != mSeekBar) {
//                        mSeekBar.setSecondaryProgress(0);
//                        mSeekBar.setProgress(0);
//                    }
//                    //设置数据，播放时间将在onOffsetPosition回调内被触发
//                    MusicPlayerManager.getInstance().updateMusicPlayerData(thisMusicLists, index);
//                    mMusicJukeBoxView.setNewData(thisMusicLists, index, true);
//                }
//            }
        } else {
            if (null != currentPlayerMusic) {
//                onStatusResume(musicId);
            }
        }
    }

    /**
     * 设置显示项
     *
     * @param position 位置
     */
    private boolean setCurrentMusicItem(int position) {
        boolean smoothScroll = false;
//        if (null != mMusicJukeBoxView && position > -1) {
//            if (Math.abs(mMusicJukeBoxView.getCurrentItem() - position) > 2) {
//                mMusicJukeBoxView.setCurrentMusicItem(position, false, true);
//            } else {
//                smoothScroll = true;
//                mMusicJukeBoxView.setCurrentMusicItem(position, true, false);
//            }
//        }
        return smoothScroll;
    }

    @Override
    public Handler getHandler() {
        if (null == mHandler) {
            mHandler = new Handler(Looper.getMainLooper());
        }
        return mHandler;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        isVisibility = false;
        if (null != mHandler) {
            mHandler.removeMessages(0);
            mHandler.removeCallbacksAndMessages(null);
            mHandler = null;
        }
        MusicPlayerManager.getInstance().removePlayerListener(this);
        MusicPlayerManager.getInstance().removeObserver(this);
        isTouchSeekBar = false;
    }


    /**
     * 播放器内部状态
     *
     * @param playerState 播放器内部状态
     * @param message
     */
    @Override
    public void onMusicPlayerState(int playerState, String message) {
        log("pppppp playerState : " + playerState + " , message : " + message);
        getHandler().post(() -> {
            switch (playerState) {
                case MusicConstants.MUSIC_PLAYER_PREPARE:
//                    if (null != mMusicAlarm && MusicPlayerManager.getInstance().getPlayerAlarmModel()
//                            != MusicConstants.MUSIC_ALARM_MODEL_0) {
//                        Drawable drawable = getResources().getDrawable(R.drawable.ic_music_alarm_pre);
//                        mMusicAlarm.setCompoundDrawablesWithIntrinsicBounds(drawable,
//                                null, null, null);
//                        mMusicAlarm.setTextColor(Color.parseColor("#F8E71C"));
//                    }
                    if (null != mMusicBtnPlayPause) {
                        mMusicBtnPlayPause.setImageResource(
                                R.drawable.ic_music_pause_noimal);
                    }
//                    if (null != mMusicJukeBoxView) {
//                        mMusicJukeBoxView.onStart();
//                    }
                    break;
                case MusicConstants.MUSIC_PLAYER_BUFFER:

                    break;
                case MusicConstants.MUSIC_PLAYER_PLAYING:
                    if (null != mMusicBtnPlayPause) {
                        mMusicBtnPlayPause.setImageResource(R.drawable.ic_music_pause_noimal);
                    }
//                    if (null != mMusicJukeBoxView) mMusicJukeBoxView.onStart();
                    break;
                case MusicConstants.MUSIC_PLAYER_PAUSE:
                    if (null != mMusicBtnPlayPause) {
                        mMusicBtnPlayPause.setImageResource(R.drawable.ic_music_play_noimal);
                    }
//                    if (null != mMusicJukeBoxView) mMusicJukeBoxView.onPause();
                    break;
                case MusicConstants.MUSIC_PLAYER_STOP:
                    if (null != mMusicBtnPlayPause) {
                        mMusicBtnPlayPause.setImageResource(
                                R.drawable.ic_music_pause_noimal);
                    }
                    if (null != mCurrentTime) {
                        mCurrentTime.setText("00:00");
                    }
                    if (null != mSeekBar) {
                        mSeekBar.setSecondaryProgress(0);
                        mSeekBar.setProgress(0);
                    }
//                    if (null != mMusicAlarm) {
//                        Drawable drawable = getResources().getDrawable(R.drawable.ic_music_alarm_noimal);
//                        mMusicAlarm.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);
//                        mMusicAlarm.setTextColor(Color.parseColor("#FFFFFF"));
//                        mMusicAlarm.setText(getString(R.string.text_music_alarm));
//                    }
//                    if (null != mMusicJukeBoxView) mMusicJukeBoxView.onStop();
                    break;
                case MusicConstants.MUSIC_PLAYER_ERROR:
                    if (null != mMusicBtnPlayPause) {
                        mMusicBtnPlayPause.setImageResource(R.drawable.ic_music_pause_noimal);
                    }
                    if (null != mSeekBar) {
                        mSeekBar.setSecondaryProgress(0);
                        mSeekBar.setProgress(0);
                    }
                    if (null != mCurrentTime) {
                        mCurrentTime.setText("00:00");
                    }
//                    if (null != mMusicJukeBoxView) {
//                        mMusicJukeBoxView.onStop();
//                    }
                    break;

                default:
                    break;
            }
        });
    }

    /**
     * 播放器准备完毕
     *
     * @param totalDuration 总时长
     */
    @Override
    public void onPrepared(long totalDuration) {
        if (null != mTotalTime) {
            mTotalTime.setText(MusicUtils.getInstance().stringForAudioTime(totalDuration));
        }
    }

    /**
     * 缓冲进度
     *
     * @param percent 百分比
     */
    @Override
    public void onBufferingUpdate(int percent) {
        if (null != mSeekBar && mSeekBar.getSecondaryProgress() < 100) {
            mSeekBar.setSecondaryProgress(percent);
        }
    }

    @Override
    public void onInfo(int event, int extra) {

    }

    /**
     * 内部播放器正在处理的对象发生了变化，这里接收到回调只负责定位，数据更新应以唱片机回调状态为准
     *
     * @param musicInfo 正在播放的对象
     * @param position  当前正在播放的位置
     */
    @Override
    public void onPlayMusicOnInfo(MusicBean musicInfo, int position) {
        getHandler().post(() -> {

            mViewTitle.setText(musicInfo.title);
            mSubTitle.setText(musicInfo.author);

            setCurrentMusicItem(position);
        });
    }

    /**
     * 播放地址无效,播放器内部会停止工作，回调至此交由组件处理业务逻辑
     * 若购买成功，调用 MusicPlayerManager.getInstance().continuePlay(String sourcePath);继续
     *
     * @param musicInfo 播放对象
     * @param position  索引
     */
    @Override
    public void onMusicPathInvalid(MusicBean musicInfo, int position) {

    }

    @Override
    public void onTaskRuntime(long totalDurtion, long currentDurtion, long alarmResidueDurtion, int bufferProgress) {
        updataPlayerParams(totalDurtion, currentDurtion, alarmResidueDurtion, bufferProgress);
    }

    @Override
    public void onPlayerConfig(int playModel, int alarmModel, boolean isToast) {
        getHandler().post(() -> setPlayerConfig(playModel, alarmModel, isToast));
    }

    /**
     * 监听并更新收藏状态，一般是服务组建中发出的通知状态
     *
     * @param o   Observable
     * @param arg 入参
     */
    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof MusicSubjectObservable && null != arg && arg instanceof MusicStatus) {
            //收藏状态,针对可能在锁屏界面收藏的同步
            log("收藏");
        }
    }

    private void updataPlayerParams(long totalDuration, long currentDuration, long alarmResidueDuration, int bufferProgress) {
        if (isVisibility && null != mSeekBar) {
            //子线程中更新进度
            if (mSeekBar.getSecondaryProgress() < 100) {
                mSeekBar.setSecondaryProgress(bufferProgress);
            }
            if (totalDuration > -1) {
                if (!isTouchSeekBar) {
                    int progress = (int) (((float) currentDuration / totalDuration) * 100);// 得到当前进度
                    mSeekBar.setProgress(progress);
                }
            }
            getHandler().post(() -> {
                //缓冲、播放进度
                if (!isTouchSeekBar && totalDuration > -1) {
                    if (null != mTotalTime && null != mCurrentTime && currentDuration <= totalDuration) {
                        mTotalTime.setText(MusicUtils.getInstance().stringForAudioTime(totalDuration));
                        mCurrentTime.setText(MusicUtils.getInstance().stringForAudioTime(currentDuration));
                    }
                }
//                    if(null!=mMusicJukeBoxView){
//                        mMusicJukeBoxView.updateLrcPosition(currentDurtion);
//                    }
                //定时闹钟状态
//                    if(alarmResidueDurtion<=0){
//                        if(null!=mMusicAlarm){
//                            Drawable drawable = getResources().getDrawable(R.drawable.ic_music_alarm_noimal);
//                            mMusicAlarm.setCompoundDrawablesWithIntrinsicBounds(drawable,null,null,null);
//                            mMusicAlarm.setTextColor(Color.parseColor("#FFFFFF"));
//                            mMusicAlarm.setText(getString(R.string.text_music_alarm));
//                        }
//                        return;
//                    }
//                    if(alarmResidueDurtion>-1&&alarmResidueDurtion <= (60 * 60)){
//                        String audioTime = MusicUtils.getInstance().stringForAudioTime(alarmResidueDurtion*1000);
//                        if(null!=mMusicAlarm) mMusicAlarm.setText(audioTime);
//                    }
            });
        }
    }


    private void setPlayerConfig(int playModel, int alarmModel, boolean isToast) {
        if (playModel > -1 && null != mMusicPlayerModel) {
            mMusicPlayerModel.setImageResource(getPlayerModelToWhiteRes(playModel));
        }
    }

    /**
     * 根据播放模式返回资源ID
     *
     * @param playerModel 播放模式
     * @return 资源ICON
     */
    public int getPlayerModelToWhiteRes(int playerModel) {
        if (playerModel == MusicConstants.MUSIC_MODEL_SINGLE) {
            return R.drawable.ic_music_model_signle_noimal;
        } else if (playerModel == MusicConstants.MUSIC_MODEL_LOOP) {
            return R.drawable.ic_music_model_loop_noimal;
        } else if (playerModel == MusicConstants.MUSIC_MODEL_RANDOM) {
            return R.drawable.ic_music_lock_model_random_noimal;
        }
        return R.drawable.ic_music_model_loop_noimal;
    }


}