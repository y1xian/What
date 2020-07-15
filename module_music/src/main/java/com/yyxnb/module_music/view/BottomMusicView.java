package com.yyxnb.module_music.view;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.arch.lifecycle.Observer;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jeremyliao.liveeventbus.LiveEventBus;
import com.yyxnb.common.ToastUtils;
import com.yyxnb.common_base.utils.GlideUtils;
import com.yyxnb.lib_audio.AudioHelper;
import com.yyxnb.lib_audio.bean.AudioBean;
import com.yyxnb.lib_audio.events.AudioEvent;
import com.yyxnb.module_music.R;
import com.yyxnb.module_music.ui.MusicPlayerActivity;

import java.util.Objects;

import static com.yyxnb.lib_audio.AudioConfig.EVENT_AUDIO_KEY;


/**
 * 播放器底部View
 */
public class BottomMusicView extends RelativeLayout {

    private Context mContext;

    /*
     * View
     */
    private ImageView mLeftView;
    private TextView mTitleView;
    private TextView mAlbumView;
    private ImageView mPlayView;
    private ImageView mRightView;
    /*
     * data
     */
    private AudioBean mAudioBean;

    public BottomMusicView(Context context) {
        this(context, null);
    }

    public BottomMusicView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BottomMusicView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
//        EventBus.getDefault().register(this);
        LiveEventBus.get(EVENT_AUDIO_KEY).observeForever(mAudioEvent);
        initView();
    }

    private ObjectAnimator animator;

    private void initView() {
        View rootView = LayoutInflater.from(mContext).inflate(R.layout.bottom_view, this);
        rootView.setOnClickListener(v -> {
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
            if (mAudioBean == null) {
                ToastUtils.normal("暂无播放内容");
                return;
            }
            AudioHelper.controller().playOrPause();
        });
        mRightView = rootView.findViewById(R.id.show_list_view);
        mRightView.setOnClickListener(v -> {
            //显示音乐列表对话框
            ToastUtils.normal("列表对话框");
//        MusicListDialog dialog = new MusicListDialog(mContext);
//        dialog.show();
        });
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
//        EventBus.getDefault().unregister(this);
        LiveEventBus.get(EVENT_AUDIO_KEY).removeObserver(mAudioEvent);
        animator = null;
    }

//    @Subscribe(threadMode = ThreadMode.MAIN)
//    public void onAudioLoadEvent(AudioLoadEvent event) {
//        //更新当前view为load状态
//        mAudioBean = event.mAudioBean;
//        showLoadView();
//    }
//
//    @Subscribe(threadMode = ThreadMode.MAIN)
//    public void onAudioPauseEvent(AudioPauseEvent event) {
//        //更新当前view为暂停状态
//        showPauseView();
//    }
//
//    @Subscribe(threadMode = ThreadMode.MAIN)
//    public void onAudioStartEvent(AudioStartEvent event) {
//        //更新当前view为播放状态
//        showPlayView();
//    }
//
//    @Subscribe(threadMode = ThreadMode.MAIN)
//    public void onAudioProgrssEvent(AudioProgressEvent event) {
//        //更新当前view的播放进度
//    }

    @SuppressWarnings("rawtypes")
    private final Observer mAudioEvent = (Observer<AudioEvent>) event -> {
        switch (Objects.requireNonNull(event).audioState) {
            case AudioEvent.AudioState.DATA:
                // 更新数据源
                mAudioBean = event.mAudioBeanList.get(0);
                showFirstView();
                break;
            case AudioEvent.AudioState.LOAD:
                //更新当前view为load状态
                mAudioBean = event.mAudioBean;
                showLoadView();
                break;
            case AudioEvent.AudioState.COMPLETE:
                //插放完毕事件处理
                break;
            case AudioEvent.AudioState.ERROR:
                //播放出错事件处理
                break;
            case AudioEvent.AudioState.START:
                //更新当前view为播放状态
                showPlayView();
                break;
            case AudioEvent.AudioState.PAUSE:
                //更新当前view为暂停状态
                showPauseView();
                break;
            case AudioEvent.AudioState.PROGRESS:
                //更新当前view的播放进度
                break;
            default:
                break;
        }
    };


    private void showFirstView() {
        //目前loading状态的UI处理与pause逻辑一样，分开为了以后好扩展
        if (mAudioBean != null) {
            GlideUtils.loadCircleImage(mAudioBean.albumPic, mLeftView);
            mTitleView.setText(mAudioBean.name);
            mAlbumView.setText(mAudioBean.author + " - " +mAudioBean.album);
            animator.resume();
        }
    }

    private void showLoadView() {
        //目前loading状态的UI处理与pause逻辑一样，分开为了以后好扩展
        if (mAudioBean != null) {
            GlideUtils.loadCircleImage(mAudioBean.albumPic, mLeftView);
            mTitleView.setText(mAudioBean.name);
            mAlbumView.setText(mAudioBean.author + " - " +mAudioBean.album);
            mPlayView.setImageResource(R.mipmap.note_btn_pause_white);
            animator.resume();
        }
    }

    private void showPauseView() {
        if (mAudioBean != null) {
            mPlayView.setImageResource(R.mipmap.note_btn_play_white);
            animator.pause();
        }
    }

    private void showPlayView() {
        if (mAudioBean != null) {
            mPlayView.setImageResource(R.mipmap.note_btn_pause_white);
            animator.resume();
        }
    }
}
