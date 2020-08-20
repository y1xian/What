package com.yyxnb.module_music.view

import android.animation.ObjectAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.animation.LinearInterpolator
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.yyxnb.common.CommonManager.toast
import com.yyxnb.common.utils.log.LogUtils.e
import com.yyxnb.common.utils.log.LogUtils.list
import com.yyxnb.common.utils.log.LogUtils.w
import com.yyxnb.image_loader.ImageManager
import com.yyxnb.lib_music.MusicPlayerManager
import com.yyxnb.lib_music.interfaces.MusicPlayerEventListener
import com.yyxnb.lib_music.utils.MusicStatus
import com.yyxnb.lib_music.utils.MusicSubjectObservable
import com.yyxnb.lib_music.utils.MusicUtils
import com.yyxnb.module_music.R
import com.yyxnb.module_music.bean.MusicBean
import com.yyxnb.module_music.bean.MusicRecordBean
import com.yyxnb.module_music.config.MusicSetting
import com.yyxnb.module_music.db.MusicDatabase.Companion.instance
import com.yyxnb.module_music.ui.MusicPlayerActivity
import java.util.*

/**
 * 播放器底部View
 */
class BottomMusicView @JvmOverloads constructor(private val mContext: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : RelativeLayout(mContext, attrs, defStyleAttr), MusicPlayerEventListener<MusicBean>, Observer {
    /*
     * View
     */
    private var mLeftView: ImageView? = null
    private var mTitleView: TextView? = null
    private var mAlbumView: TextView? = null
    private var mPlayView: ImageView? = null
    private var mRightView: ImageView? = null
    private var progressBar: ProgressBar? = null

    /*
     * data
     */
    private var mMusicBean: MusicBean? = null
    private var mRecordBean: MusicRecordBean? = null
    private var animator: ObjectAnimator? = null
    private fun initView() {
        val rootView = LayoutInflater.from(mContext).inflate(R.layout.bottom_view, this)
        rootView.setOnClickListener { v: View? ->
            if (mMusicBean == null) {
                toast("暂无播放内容")
                return@setOnClickListener
            }
            //跳到音乐播放Activitity
            MusicPlayerActivity.start(mContext as AppCompatActivity)
        }
        mLeftView = rootView.findViewById(R.id.album_view)
        animator = ObjectAnimator.ofFloat(mLeftView, ROTATION.name, 0f, 360f)
        animator?.setDuration(10000)
        animator?.setInterpolator(LinearInterpolator())
        animator?.setRepeatCount(-1)
        animator?.start()
        mTitleView = rootView.findViewById(R.id.audio_name_view)
        mAlbumView = rootView.findViewById(R.id.audio_album_view)
        mPlayView = rootView.findViewById(R.id.play_view)
        mPlayView?.setOnClickListener(OnClickListener { v: View? ->
            //处理播放暂停事件
            if (mMusicBean == null) {
                toast("暂无播放内容")
                return@OnClickListener
            }
            w("MusicSetting.IS_FIRST_PLAY : " + MusicSetting.IS_FIRST_PLAY)
            //            AudioHelper.controller().playOrPause();
            if (MusicSetting.IS_FIRST_PLAY) {
                val beans: List<MusicBean?> = instance!!.musicDao().musicList
                list(beans)
                MusicPlayerManager.getInstance().setMusic(beans, MusicUtils.getInstance().getCurrentPlayIndex(beans, mMusicBean!!.mid))
                w("size : " + beans.size + " , index : " + MusicUtils.getInstance().getCurrentPlayIndex(beans, mMusicBean!!.mid))
                //                MusicPlayerManager.getInstance().addPlayMusicToTop(mMusicBean);
                e("" + MusicPlayerManager.getInstance().getCurrentPlayerMusic())
                MusicSetting.IS_FIRST_PLAY = false
                if (mRecordBean!!.currenTime > 0) {
                    val currentTime = mRecordBean!!.currenTime
                    MusicPlayerManager.getInstance().play()
                    MusicPlayerManager.getInstance().seekTo(mRecordBean!!.currenTime)
                } else {
                    MusicPlayerManager.getInstance().play()
                }
            } else {
                MusicPlayerManager.getInstance().playOrPause()
            }
        })
        mRightView = rootView.findViewById(R.id.show_list_view)
        mRightView?.setOnClickListener(OnClickListener { v: View? ->
            //显示音乐列表对话框
            toast("列表对话框")
        })
        progressBar = rootView.findViewById(R.id.progressBar)
        MusicPlayerManager.getInstance().addObservable(this)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        MusicPlayerManager.getInstance().removePlayerListener(this)
        animator = null
    }

    fun showFirstView() {
        try {
            // 获取最后一条记录
            mRecordBean = instance!!.recordDao().lastRecord
            mMusicBean = mRecordBean!!.musicBean
            progressBar!!.max = mRecordBean!!.musicBean!!.totalTime.toInt()
            progressBar!!.progress = mRecordBean!!.currenTime.toInt()
            e("最后一条 $mRecordBean")
            //        mMusicBean = MusicPlayerManager.getInstance().getCurrentPlayerMusic();
            //目前loading状态的UI处理与pause逻辑一样，分开为了以后好扩展
            if (mMusicBean != null) {
                ImageManager.getInstance().displayImage(mMusicBean!!.albumPic, mLeftView,
                        R.drawable.ic_music_default_cover, R.drawable.ic_music_default_cover)
                mTitleView!!.text = mMusicBean!!.title
                mAlbumView!!.text = mMusicBean!!.author + " - " + mMusicBean!!.album
                //            mPlayView.setImageResource(R.mipmap.note_btn_pause_white);
                animator!!.resume()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            MusicPlayerManager.getInstance().addOnPlayerEventListener(this)
        }
    }

    fun showLoadView() {
        mMusicBean = MusicPlayerManager.getInstance().getCurrentPlayerMusic()
        //目前loading状态的UI处理与pause逻辑一样，分开为了以后好扩展
        if (mMusicBean != null) {
            ImageManager.getInstance().displayImage(mMusicBean!!.albumPic, mLeftView,
                    R.drawable.ic_music_default_cover, R.drawable.ic_music_default_cover)
            mTitleView!!.text = mMusicBean!!.title
            mAlbumView!!.text = mMusicBean!!.author + " - " + mMusicBean!!.album
            mPlayView!!.setImageResource(R.mipmap.note_btn_pause_white)
            animator!!.resume()
        }
    }

    private fun showPauseView() {
        if (mMusicBean != null) {
            mPlayView!!.setImageResource(R.mipmap.note_btn_play_white)
            animator!!.pause()
        }
    }

    private fun showPlayView() {
        if (mMusicBean != null) {
            mPlayView!!.setImageResource(R.mipmap.note_btn_pause_white)
            animator!!.resume()
        }
    }

    override fun onMusicPlayerState(playerState: Int, message: String) {
        /*
          int MUSIC_PLAYER_STOP = 0; //已结束，或未开始
    int MUSIC_PLAYER_PREPARE = 1; //准备中
    int MUSIC_PLAYER_BUFFER = 2; //缓冲中
    int MUSIC_PLAYER_PLAYING = 3; //播放中
    int MUSIC_PLAYER_PAUSE = 4; //暂停
    int MUSIC_PLAYER_ERROR = 5; //错误
         */
        when (playerState) {
            0 -> {
            }
            1 ->                 //更新当前view为load状态
                showLoadView()
            2 -> {
            }
            3 ->                 //更新当前view为播放状态
                showPlayView()
            4 ->                 //更新当前view为暂停状态
                showPauseView()
            5 -> {
            }
            else -> {
            }
        }
        w("bottom playerState : $playerState , message : $message")
    }

    override fun onPrepared(totalDuration: Long) {
        progressBar!!.max = totalDuration.toInt()
    }

    override fun onBufferingUpdate(percent: Int) {}
    override fun onInfo(event: Int, extra: Int) {}
    override fun onPlayMusicOnInfo(musicInfo: MusicBean, position: Int) {}
    override fun onMusicPathInvalid(musicInfo: MusicBean, position: Int) {}
    override fun onTaskRuntime(totalDuartion: Long, currentDuration: Long, alarmResidueDuration: Long, bufferProgress: Int) {
        progressBar!!.progress = currentDuration.toInt()
    }

    override fun onPlayerConfig(playModel: Int, alarmModel: Int, isToast: Boolean) {}

    /**
     * 监听并更新收藏状态，一般是服务组建中发出的通知状态
     *
     * @param o   Observable
     * @param arg 入参
     */
    override fun update(o: Observable, arg: Any) {
        if (o is MusicSubjectObservable && null != arg && arg is MusicStatus) {
        }
    }

    //    // 标记是否首次播放
    //    private boolean isFirstPlay = true;
    init {
        initView()
    }
}