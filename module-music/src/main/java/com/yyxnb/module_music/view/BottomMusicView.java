package com.yyxnb.module_music.view;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yyxnb.what.core.ToastUtils;
import com.yyxnb.what.core.log.LogUtils;
import com.yyxnb.what.image.ImageManager;
import com.yyxnb.what.music.MusicPlayerManager;
import com.yyxnb.what.music.interfaces.MusicPlayerEventListener;
import com.yyxnb.what.music.utils.MusicStatus;
import com.yyxnb.what.music.utils.MusicSubjectObservable;
import com.yyxnb.what.music.utils.MusicUtils;
import com.yyxnb.module_music.R;
import com.yyxnb.module_music.bean.MusicBean;
import com.yyxnb.module_music.bean.MusicRecordBean;
import com.yyxnb.module_music.config.MusicSetting;
import com.yyxnb.module_music.db.MusicDatabase;
import com.yyxnb.module_music.ui.MusicPlayerActivity;

import java.util.List;
import java.util.Observable;
import java.util.Observer;


/**
 * 播放器底部View
 */
public class BottomMusicView extends RelativeLayout implements MusicPlayerEventListener<MusicBean>, Observer {

    private Context mContext;

    /*
     * View
     */
    private ImageView mLeftView;
    private TextView mTitleView;
    private TextView mAlbumView;
    private ImageView mPlayView;
    private ImageView mRightView;
    private ProgressBar progressBar;

    /*
     * data
     */
    private MusicBean mMusicBean;
    private MusicRecordBean mRecordBean;

//    // 标记是否首次播放
//    private boolean isFirstPlay = true;

    public BottomMusicView(Context context) {
        this(context, null);
    }

    public BottomMusicView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BottomMusicView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initView();
    }

    private ObjectAnimator animator;

    private void initView() {
        View rootView = LayoutInflater.from(mContext).inflate(R.layout.bottom_view, this);
        rootView.setOnClickListener(v -> {
            if (mMusicBean == null) {
                ToastUtils.normal("暂无播放内容");
                return;
            }
            //跳到音乐播放Activitity
            MusicPlayerActivity.start((Activity) mContext);

        });
        mLeftView = rootView.findViewById(R.id.album_view);
        animator = ObjectAnimator.ofFloat(mLeftView, View.ROTATION.getName(), 0f, 360);
        animator.setDuration(10000);
        animator.setInterpolator(new LinearInterpolator());
        animator.setRepeatCount(-1);
        animator.start();

        mTitleView = rootView.findViewById(R.id.audio_name_view);
        mAlbumView = rootView.findViewById(R.id.audio_album_view);
        mPlayView = rootView.findViewById(R.id.play_view);
        mPlayView.setOnClickListener(v -> {
            //处理播放暂停事件
            if (mMusicBean == null) {
                ToastUtils.normal("暂无播放内容");
                return;
            }

            LogUtils.w("MusicSetting.IS_FIRST_PLAY : " + MusicSetting.IS_FIRST_PLAY);
//            AudioHelper.controller().playOrPause();
            if (MusicSetting.IS_FIRST_PLAY) {
                List<MusicBean> beans = MusicDatabase.getInstance().musicDao().getMusicList();

                LogUtils.list(beans);

                MusicPlayerManager.getInstance().setMusic(beans, MusicUtils.getInstance().getCurrentPlayIndex(beans, mMusicBean.mid));

                LogUtils.w("size : " + beans.size() + " , index : " + MusicUtils.getInstance().getCurrentPlayIndex(beans, mMusicBean.mid));
//                MusicPlayerManager.getInstance().addPlayMusicToTop(mMusicBean);

                LogUtils.e("" + MusicPlayerManager.getInstance().getCurrentPlayerMusic());
                MusicSetting.IS_FIRST_PLAY = false;
                if (mRecordBean.currenTime > 0) {
                    long currentTime = mRecordBean.currenTime;
                    MusicPlayerManager.getInstance().play();
                    MusicPlayerManager.getInstance().seekTo(mRecordBean.currenTime);
                } else {
                    MusicPlayerManager.getInstance().play();
                }
            } else {
                MusicPlayerManager.getInstance().playOrPause();
            }
        });
        mRightView = rootView.findViewById(R.id.show_list_view);
        mRightView.setOnClickListener(v -> {
            //显示音乐列表对话框
            ToastUtils.normal("列表对话框");
//        MusicListDialog dialog = new MusicListDialog(mContext);
//        dialog.show();
        });

        progressBar = rootView.findViewById(R.id.progressBar);

        MusicPlayerManager.getInstance().addObservable(this);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        MusicPlayerManager.getInstance().removePlayerListener(this);
        animator = null;
    }


    public void showFirstView() {
        try {
            // 获取最后一条记录
            mRecordBean = MusicDatabase.getInstance().recordDao().getLastRecord();
            mMusicBean = mRecordBean.musicBean;
            progressBar.setMax(Integer.parseInt(mRecordBean.musicBean.totalTime));
            progressBar.setProgress((int) mRecordBean.currenTime);
            LogUtils.e("最后一条 " + mRecordBean);
//        mMusicBean = MusicPlayerManager.getInstance().getCurrentPlayerMusic();
            //目前loading状态的UI处理与pause逻辑一样，分开为了以后好扩展
            if (mMusicBean != null) {
                ImageManager.getInstance().displayImage(mMusicBean.albumPic, mLeftView,
                        R.drawable.ic_music_default_cover, R.drawable.ic_music_default_cover);
                mTitleView.setText(mMusicBean.title);
                mAlbumView.setText(mMusicBean.author + " - " + mMusicBean.album);
//            mPlayView.setImageResource(R.mipmap.note_btn_pause_white);
                animator.resume();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            MusicPlayerManager.getInstance().addOnPlayerEventListener(this);
        }

    }

    public void showLoadView() {
        mMusicBean = MusicPlayerManager.getInstance().getCurrentPlayerMusic();
        //目前loading状态的UI处理与pause逻辑一样，分开为了以后好扩展
        if (mMusicBean != null) {
            ImageManager.getInstance().displayImage(mMusicBean.albumPic, mLeftView,
                    R.drawable.ic_music_default_cover, R.drawable.ic_music_default_cover);
            mTitleView.setText(mMusicBean.title);
            mAlbumView.setText(mMusicBean.author + " - " + mMusicBean.album);
            mPlayView.setImageResource(R.mipmap.note_btn_pause_white);
            animator.resume();
        }
    }

    private void showPauseView() {
        if (mMusicBean != null) {
            mPlayView.setImageResource(R.mipmap.note_btn_play_white);
            animator.pause();
        }
    }

    private void showPlayView() {
        if (mMusicBean != null) {
            mPlayView.setImageResource(R.mipmap.note_btn_pause_white);
            animator.resume();
        }
    }

    @Override
    public void onMusicPlayerState(int playerState, String message) {
        /*
          int MUSIC_PLAYER_STOP = 0; //已结束，或未开始
    int MUSIC_PLAYER_PREPARE = 1; //准备中
    int MUSIC_PLAYER_BUFFER = 2; //缓冲中
    int MUSIC_PLAYER_PLAYING = 3; //播放中
    int MUSIC_PLAYER_PAUSE = 4; //暂停
    int MUSIC_PLAYER_ERROR = 5; //错误
         */
        switch (playerState) {
            case 0:
                break;
            case 1:
                //更新当前view为load状态
                showLoadView();
                break;
            case 2:
                break;
            case 3:
                //更新当前view为播放状态
                showPlayView();
                break;
            case 4:
                //更新当前view为暂停状态
                showPauseView();
                break;
            case 5:
                break;
            default:
                break;
        }
        LogUtils.w("bottom playerState : " + playerState + " , message : " + message);
    }

    @Override
    public void onPrepared(long totalDuration) {
        progressBar.setMax((int) totalDuration);
    }

    @Override
    public void onBufferingUpdate(int percent) {

    }

    @Override
    public void onInfo(int event, int extra) {

    }

    @Override
    public void onPlayMusicOnInfo(MusicBean musicInfo, int position) {

    }

    @Override
    public void onMusicPathInvalid(MusicBean musicInfo, int position) {

    }

    @Override
    public void onTaskRuntime(long totalDuartion, long currentDuration, long alarmResidueDuration, int bufferProgress) {
        progressBar.setProgress((int) currentDuration);
    }

    @Override
    public void onPlayerConfig(int playModel, int alarmModel, boolean isToast) {

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

        }
    }
}
