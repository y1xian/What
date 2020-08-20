package com.yyxnb.module_music.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.TextUtils
import android.view.View
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityOptionsCompat
import androidx.databinding.DataBindingUtil
import com.yyxnb.common.utils.log.LogUtils.e
import com.yyxnb.common.utils.log.LogUtils.w
import com.yyxnb.common_base.base.BaseActivity
import com.yyxnb.lib_music.MusicPlayerManager
import com.yyxnb.lib_music.interfaces.MusicConstants
import com.yyxnb.lib_music.interfaces.MusicPlayerEventListener
import com.yyxnb.lib_music.utils.MusicStatus
import com.yyxnb.lib_music.utils.MusicSubjectObservable
import com.yyxnb.lib_music.utils.MusicUtils
import com.yyxnb.module_music.R
import com.yyxnb.module_music.bean.MusicBean
import com.yyxnb.module_music.bean.MusicRecordBean
import com.yyxnb.module_music.config.MusicSetting
import com.yyxnb.module_music.databinding.ActivityMusicPlayerBinding
import com.yyxnb.module_music.db.MusicDatabase.Companion.instance
import java.util.*

/**
 * 播放音乐Activity
 */
class MusicPlayerActivity : BaseActivity(), View.OnClickListener, MusicPlayerEventListener<MusicBean>, Observer {

    private var binding: ActivityMusicPlayerBinding? = null
    private var mSeekBar: SeekBar? = null
    private var mMusicBtnPlayPause: ImageView? = null
    private var mMusicPlayerModel: ImageView? = null
    private var mBtnCollect: ImageView? = null
    private var mViewTitle: TextView? = null
    private var mTotalTime: TextView? = null
    private var mCurrentTime: TextView? = null
    private val mMusicAlarm: TextView? = null
    private var mSubTitle: TextView? = null
    private var mHandler: Handler? = null

    //手指是否正在控制seekBar
    private var isTouchSeekBar = false
    private var isVisibility = true
    private var mMusicBean: MusicBean? = null
    private var mRecordBean: MusicRecordBean? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_music_player)
        super.onCreate(savedInstanceState)
    }

    override fun initView(savedInstanceState: Bundle?) {
        mSeekBar = binding!!.musicSeekBar
        mMusicBtnPlayPause = binding!!.musicBtnPlayPause
        mMusicPlayerModel = binding!!.musicBtnModel
        mBtnCollect = binding!!.musicTopCollect
        mViewTitle = binding!!.musicTitle
        mTotalTime = binding!!.musicTotalTime
        mCurrentTime = binding!!.musicCurrentTime
        //        mMusicAlarm = binding.musicCurrentTime;
        mSubTitle = binding!!.musicSubTitle
        try {
            mRecordBean = instance!!.recordDao().lastRecord
            mMusicBean = mRecordBean!!.musicBean
            mViewTitle!!.text = mMusicBean!!.title
            mSubTitle!!.text = mMusicBean!!.author
            mSeekBar!!.secondaryProgress = mRecordBean!!.musicBean!!.totalTime.toInt()
            mSeekBar!!.progress = mRecordBean!!.currenTime.toInt()
            mCurrentTime!!.text = MusicUtils.getInstance().stringForAudioTime(mRecordBean!!.currenTime)
            mTotalTime!!.text = MusicUtils.getInstance().stringForAudioTime(mRecordBean!!.musicBean!!.totalTime.toInt().toLong())
            w("" + mRecordBean)
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            //注册播放器状态监听器
            MusicPlayerManager.getInstance().addOnPlayerEventListener(this)
            //注册观察者模式，主要处理收藏事件
            MusicPlayerManager.getInstance().addObservable(this)
        }


//        getIntentParams(getIntent(), true);
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        //        getIntentParams(intent, false);
    }

    override fun initViewData() {
        mSeekBar!!.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    val durtion = MusicPlayerManager.getInstance().duration
                    if (durtion > 0) {
                        mCurrentTime!!.text = MusicUtils.getInstance().stringForAudioTime(
                                progress * durtion / 100)
                    }
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {
                isTouchSeekBar = true
            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {
                isTouchSeekBar = false
                val duration = MusicPlayerManager.getInstance().duration
                if (duration > 0) {
                    val currentTime = seekBar.progress * duration / 100
                    MusicPlayerManager.getInstance().seekTo(currentTime)
                }
            }
        })
        findViewById<View>(R.id.music_back).setOnClickListener(this)
        findViewById<View>(R.id.music_btn_model).setOnClickListener(this)
        findViewById<View>(R.id.music_btn_last).setOnClickListener(this)
        findViewById<View>(R.id.music_btn_play_pause).setOnClickListener(this)
        findViewById<View>(R.id.music_btn_next).setOnClickListener(this)
        findViewById<View>(R.id.music_btn_menu).setOnClickListener(this)
    }

    override fun initObservable() {
        instance!!.recordDao().lastRecord
    }

    override fun onResume() {
        super.onResume()
        if (MusicPlayerManager.getInstance().isPlaying) {
            if (null != mMusicBtnPlayPause) {
                mMusicBtnPlayPause!!.setImageResource(
                        R.drawable.ic_music_pause_noimal)
            }
        }
    }

    override fun onClick(v: View) {
        val id = v.id
        //播放模式
        if (id == R.id.music_btn_model) {
            MusicPlayerManager.getInstance().changedPlayerPlayModel()
            //上一首
        } else if (id == R.id.music_btn_last) {
            //                int lastPosition = MusicPlayerManager.getInstance().playLastIndex();
//                setCurrentMusicItem(lastPosition);
            MusicPlayerManager.getInstance().playLastMusic()
            //开始、暂停
        } else if (id == R.id.music_btn_play_pause) {
            w("MusicSetting.IS_FIRST_PLAY : " + MusicSetting.IS_FIRST_PLAY)
            if (MusicSetting.IS_FIRST_PLAY) {
                MusicSetting.IS_FIRST_PLAY = false
                val beans = instance!!.musicDao().musicList
                MusicPlayerManager.getInstance().setMusic(beans, MusicUtils.getInstance().getCurrentPlayIndex(beans, mMusicBean!!.mid))
                if (mRecordBean!!.currenTime > 0) {
                    MusicPlayerManager.getInstance().play()
                    MusicPlayerManager.getInstance().seekTo(mRecordBean!!.currenTime)
                } else {
                    MusicPlayerManager.getInstance().play()
                }
            } else {
                MusicPlayerManager.getInstance().playOrPause()
            }

            //下一首
        } else if (id == R.id.music_btn_next) {
            //                int nextPosition = MusicPlayerManager.getInstance().playNextIndex();
//                setCurrentMusicItem(nextPosition);
            MusicPlayerManager.getInstance().playNextMusic()
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
            finish()
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
    private fun getIntentParams(intent: Intent, isOnCreate: Boolean) {
        //Music对象
        val musicId = intent.getStringExtra(MusicConstants.KEY_MUSIC_ID)
        if (musicId!!.isEmpty()) {
            finish()
            return
        }

        //正在播放的对象
        val currentPlayerMusic: MusicBean = MusicPlayerManager.getInstance().getCurrentPlayerMusic()
        //点击了通知栏回显
        if (!isOnCreate && null != currentPlayerMusic && currentPlayerMusic.audioId() == musicId) {
            return
        }
        MusicPlayerManager.getInstance().onCheckedPlayerConfig() //检查播放器配置
        val musicList = intent.getStringExtra(MusicConstants.KEY_MUSIC_LIST)
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
    private fun setCurrentMusicItem(position: Int): Boolean {
        //        if (null != mMusicJukeBoxView && position > -1) {
//            if (Math.abs(mMusicJukeBoxView.getCurrentItem() - position) > 2) {
//                mMusicJukeBoxView.setCurrentMusicItem(position, false, true);
//            } else {
//                smoothScroll = true;
//                mMusicJukeBoxView.setCurrentMusicItem(position, true, false);
//            }
//        }
        return false
    }

    val handler: Handler
        get() {
            if (null == mHandler) {
                mHandler = Handler(Looper.getMainLooper())
            }
            return mHandler!!
        }

    override fun onDestroy() {
        super.onDestroy()
        isVisibility = false
        if (null != mHandler) {
            mHandler!!.removeMessages(0)
            mHandler!!.removeCallbacksAndMessages(null)
            mHandler = null
        }
        MusicPlayerManager.getInstance().removePlayerListener(this)
        MusicPlayerManager.getInstance().removeObserver(this)
        isTouchSeekBar = false
    }

    /**
     * 播放器内部状态
     *
     * @param playerState 播放器内部状态
     * @param message
     */
    override fun onMusicPlayerState(playerState: Int, message: String) {
        w("pppppp playerState : $playerState , message : $message")
        handler.post {
            when (playerState) {
                MusicConstants.MUSIC_PLAYER_PREPARE -> //                    if (null != mMusicAlarm && MusicPlayerManager.getInstance().getPlayerAlarmModel()
//                            != MusicConstants.MUSIC_ALARM_MODEL_0) {
//                        Drawable drawable = getResources().getDrawable(R.drawable.ic_music_alarm_pre);
//                        mMusicAlarm.setCompoundDrawablesWithIntrinsicBounds(drawable,
//                                null, null, null);
//                        mMusicAlarm.setTextColor(Color.parseColor("#F8E71C"));
//                    }
                    if (null != mMusicBtnPlayPause) {
                        mMusicBtnPlayPause!!.setImageResource(
                                R.drawable.ic_music_pause_noimal)
                    }
                MusicConstants.MUSIC_PLAYER_BUFFER -> {
                }
                MusicConstants.MUSIC_PLAYER_PLAYING -> if (null != mMusicBtnPlayPause) {
                    mMusicBtnPlayPause!!.setImageResource(R.drawable.ic_music_pause_noimal)
                }
                MusicConstants.MUSIC_PLAYER_PAUSE -> if (null != mMusicBtnPlayPause) {
                    mMusicBtnPlayPause!!.setImageResource(R.drawable.ic_music_play_noimal)
                }
                MusicConstants.MUSIC_PLAYER_STOP -> {
                    if (null != mMusicBtnPlayPause) {
                        mMusicBtnPlayPause!!.setImageResource(
                                R.drawable.ic_music_pause_noimal)
                    }
                    if (null != mCurrentTime) {
                        mCurrentTime!!.text = "00:00"
                    }
                    if (null != mSeekBar) {
                        mSeekBar!!.secondaryProgress = 0
                        mSeekBar!!.progress = 0
                    }
                }
                MusicConstants.MUSIC_PLAYER_ERROR -> {
                    if (null != mMusicBtnPlayPause) {
                        mMusicBtnPlayPause!!.setImageResource(R.drawable.ic_music_pause_noimal)
                    }
                    if (null != mSeekBar) {
                        mSeekBar!!.secondaryProgress = 0
                        mSeekBar!!.progress = 0
                    }
                    if (null != mCurrentTime) {
                        mCurrentTime!!.text = "00:00"
                    }
                }
                else -> {
                }
            }
        }
    }

    /**
     * 播放器准备完毕
     *
     * @param totalDuration 总时长
     */
    override fun onPrepared(totalDuration: Long) {
        if (null != mTotalTime) {
            mTotalTime!!.text = MusicUtils.getInstance().stringForAudioTime(totalDuration)
        }
    }

    /**
     * 缓冲进度
     *
     * @param percent 百分比
     */
    override fun onBufferingUpdate(percent: Int) {
        if (null != mSeekBar && mSeekBar!!.secondaryProgress < 100) {
            mSeekBar!!.secondaryProgress = percent
        }
    }

    override fun onInfo(event: Int, extra: Int) {}

    /**
     * 内部播放器正在处理的对象发生了变化，这里接收到回调只负责定位，数据更新应以唱片机回调状态为准
     *
     * @param musicInfo 正在播放的对象
     * @param position  当前正在播放的位置
     */
    override fun onPlayMusicOnInfo(musicInfo: MusicBean, position: Int) {
        handler.post {
            mViewTitle!!.text = musicInfo.title
            mSubTitle!!.text = musicInfo.author
            setCurrentMusicItem(position)
        }
    }

    /**
     * 播放地址无效,播放器内部会停止工作，回调至此交由组件处理业务逻辑
     * 若购买成功，调用 MusicPlayerManager.getInstance().continuePlay(String sourcePath);继续
     *
     * @param musicInfo 播放对象
     * @param position  索引
     */
    override fun onMusicPathInvalid(musicInfo: MusicBean, position: Int) {}
    override fun onTaskRuntime(totalDurtion: Long, currentDurtion: Long, alarmResidueDurtion: Long, bufferProgress: Int) {
        updataPlayerParams(totalDurtion, currentDurtion, alarmResidueDurtion, bufferProgress)
    }

    override fun onPlayerConfig(playModel: Int, alarmModel: Int, isToast: Boolean) {
        handler.post { setPlayerConfig(playModel, alarmModel, isToast) }
    }

    /**
     * 监听并更新收藏状态，一般是服务组建中发出的通知状态
     *
     * @param o   Observable
     * @param arg 入参
     */
    override fun update(o: Observable, arg: Any) {
        if (o is MusicSubjectObservable && null != arg && arg is MusicStatus) {
            //收藏状态,针对可能在锁屏界面收藏的同步
//            ToastUtils.normal("收藏");
            e("收藏")
        }
    }

    private fun updataPlayerParams(totalDuration: Long, currentDuration: Long, alarmResidueDuration: Long, bufferProgress: Int) {
        if (isVisibility && null != mSeekBar) {
            //子线程中更新进度
            if (mSeekBar!!.secondaryProgress < 100) {
                mSeekBar!!.secondaryProgress = bufferProgress
            }
            if (totalDuration > -1) {
                if (!isTouchSeekBar) {
                    val progress = (currentDuration.toFloat() / totalDuration * 100).toInt() // 得到当前进度
                    mSeekBar!!.progress = progress
                }
            }
            handler.post {
                //缓冲、播放进度
                if (!isTouchSeekBar && totalDuration > -1) {
                    if (null != mTotalTime && null != mCurrentTime && currentDuration <= totalDuration) {
                        mTotalTime!!.text = MusicUtils.getInstance().stringForAudioTime(totalDuration)
                        mCurrentTime!!.text = MusicUtils.getInstance().stringForAudioTime(currentDuration)
                    }
                }
            }
        }
    }

    private fun setPlayerConfig(playModel: Int, alarmModel: Int, isToast: Boolean) {
        if (playModel > -1 && null != mMusicPlayerModel) {
            mMusicPlayerModel!!.setImageResource(getPlayerModelToWhiteRes(playModel))
        }
    }

    /**
     * 根据播放模式返回资源ID
     *
     * @param playerModel 播放模式
     * @return 资源ICON
     */
    fun getPlayerModelToWhiteRes(playerModel: Int): Int {
        if (playerModel == MusicConstants.MUSIC_MODEL_SINGLE) {
            return R.drawable.ic_music_model_signle_noimal
        } else if (playerModel == MusicConstants.MUSIC_MODEL_LOOP) {
            return R.drawable.ic_music_model_loop_noimal
        } else if (playerModel == MusicConstants.MUSIC_MODEL_RANDOM) {
            return R.drawable.ic_music_lock_model_random_noimal
        }
        return R.drawable.ic_music_model_loop_noimal
    }

    companion object {
        fun start(context: AppCompatActivity?) {
            val intent = Intent(context, MusicPlayerActivity::class.java)
            ActivityCompat.startActivity(context!!, intent,
                    ActivityOptionsCompat.makeSceneTransitionAnimation(context).toBundle())
        }
    }
}