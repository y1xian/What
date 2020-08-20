package com.yyxnb.module_video.ui.play

import android.Manifest
import android.os.Bundle
import android.view.View
import androidx.viewpager.widget.ViewPager.SimpleOnPageChangeListener
import com.dueeeke.videoplayer.player.AbstractPlayer
import com.dueeeke.videoplayer.player.VideoView
import com.dueeeke.videoplayer.player.VideoView.SimpleOnStateChangeListener
import com.dueeeke.videoplayer.player.VideoViewManager
import com.dueeeke.videoplayer.util.L
import com.yyxnb.arch.annotations.BindRes
import com.yyxnb.arch.annotations.BindViewModel
import com.yyxnb.arch.common.Bus.post
import com.yyxnb.arch.common.MsgEvent
import com.yyxnb.common.CommonManager.toast
import com.yyxnb.common.interfaces.OnSelectListener
import com.yyxnb.common.utils.log.LogUtils.e
import com.yyxnb.common_base.base.BaseFragment
import com.yyxnb.common_base.config.Constants.KEY_VIDEO_BOTTOM_VP
import com.yyxnb.common_base.config.Constants.KEY_VIDEO_BOTTOM_VP_SWITCH
import com.yyxnb.module_video.R
import com.yyxnb.module_video.adapter.TikTokAdapter
import com.yyxnb.module_video.bean.TikTokBean
import com.yyxnb.module_video.config.DataConfig.tikTokBeans
import com.yyxnb.module_video.databinding.FragmentVideoPlayBinding
import com.yyxnb.module_video.viewmodel.VideoViewModel
import com.yyxnb.module_video.widget.VerticalViewPager
import com.yyxnb.module_video.widget.tiktok.TikTokController
import com.yyxnb.module_video.widget.tiktok.TikTokRenderViewFactory
import com.yyxnb.utils.permission.PermissionListener
import com.yyxnb.utils.permission.PermissionUtils
import com.yyxnb.video.Utils
import com.yyxnb.video.cache.PreloadManager
import com.yyxnb.video.cache.ProxyVideoCacheManager
import java.io.Serializable
import java.util.*

/**
 * 短视频播放的fragment 可以上下滑动
 */
@BindRes
class VideoPlayFragment : BaseFragment() {

    @BindViewModel
    lateinit var mViewModel: VideoViewModel

    private var binding: FragmentVideoPlayBinding? = null
    private var mViewPager: VerticalViewPager? = null
    private var mVideoView: VideoView<AbstractPlayer>? = null
    private var mController: TikTokController? = null
    private var mPreloadManager: PreloadManager? = null
    private var mAdapter: TikTokAdapter? = null
    private val mVideoList: MutableList<TikTokBean> = ArrayList()
    private var mCurPos = 0
    private var isCur = false

    override fun initLayoutResId(): Int {
        return R.layout.fragment_video_play
    }

    override fun initView(savedInstanceState: Bundle?) {
        binding = getBinding()
        mViewPager = binding!!.mViewPager
        PermissionUtils.with(activity)
                .addPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .setPermissionsCheckListener(object : PermissionListener {
                    override fun permissionRequestSuccess() {}
                    override fun permissionRequestFail(grantedPermissions: Array<String>, deniedPermissions: Array<String>, forceDeniedPermissions: Array<String>) {}
                })
                .createConfig()
                .setForceAllPermissionsGranted(true)
                .buildConfig()
                .startCheckPermission()
    }

    override fun initViewData() {
//        mCurPos = getArguments().getInt("CurPos",0);
//        mVideoList = (List<TikTokBean>) getArguments().getSerializable("data");
        initViewPager()
        initVideoView()
        mPreloadManager = PreloadManager.getInstance(context)

//        addData(null);

//        mViewPager.post(() -> startPlay(mCurPos));

        // 点赞、评论等交互
        mAdapter!!.setOnSelectListener(object : OnSelectListener {
            override fun onClick(v: View, position: Int, text: String?) {
                when (position) {
                    0 -> {
                    }
                    5 -> post(MsgEvent(KEY_VIDEO_BOTTOM_VP_SWITCH, "", 1))
                }
                toast(text!!)
            }

        })
    }

    override fun initObservable() {
        mViewModel.reqVideoList()
        mViewModel.result1().observe(this, { data: List<TikTokBean>? ->
            if (data != null) {
                mVideoList.addAll(data)
                //                LogUtils.list(mVideoList);
            }
            mAdapter!!.notifyDataSetChanged()
            mViewPager!!.post { startPlay(mCurPos) }
        })
    }

    private fun initVideoView() {
        mVideoView = VideoView<AbstractPlayer>(context)
        mVideoView?.setLooping(true)

        //以下只能二选一，看你的需求
        mVideoView?.setRenderViewFactory(TikTokRenderViewFactory.create())
        //        mVideoView.setScreenScaleType(VideoView.SCREEN_SCALE_CENTER_CROP);
        mController = TikTokController(context)
        mVideoView?.setVideoController(mController)
        mVideoView?.addOnStateChangeListener(object : SimpleOnStateChangeListener() {
            override fun onPlayStateChanged(playState: Int) {
                if (playState == VideoView.STATE_PLAYING) {
                    e("Play STATE_PLAYING")
                    // 处理快速切换界面，缓存刚刚好就回继续播放的问题
                    if (!isCur) {
                        mVideoView?.pause()
                    }
                }
            }
        })
        VideoViewManager.instance().add(mVideoView, "tiktok")
    }

    private fun initViewPager() {
//        mViewPager.setOffscreenPageLimit(4);
        mAdapter = TikTokAdapter(mVideoList)
        mViewPager!!.adapter = mAdapter
        mViewPager!!.overScrollMode = View.OVER_SCROLL_NEVER
        mViewPager!!.setOnPageChangeListener(object : SimpleOnPageChangeListener() {
            private var mCurItem = 0

            /**
             * VerticalViewPager是否反向滑动
             */
            private var mIsReverseScroll = false
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
                if (position == mCurItem) {
                    return
                }
                mIsReverseScroll = position < mCurItem
            }

            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                if (position == mCurPos) {
                    return
                }
                startPlay(position)
            }

            override fun onPageScrollStateChanged(state: Int) {
                super.onPageScrollStateChanged(state)
                if (state == VerticalViewPager.SCROLL_STATE_DRAGGING) {
                    mCurItem = mViewPager!!.currentItem
                }
                if (state == VerticalViewPager.SCROLL_STATE_IDLE) {
                    mPreloadManager!!.resumePreload(mCurPos, mIsReverseScroll)
                } else {
                    mPreloadManager!!.pausePreload(mCurPos, mIsReverseScroll)
                }
            }
        })
    }

    private fun startPlay(position: Int) {
        val count = mViewPager!!.childCount
        for (i in 0 until count) {
            val itemView = mViewPager!!.getChildAt(i)
            val viewHolder = itemView.tag as TikTokAdapter.ViewHolder
            if (viewHolder.mPosition == position) {
                mVideoView!!.release()
                Utils.removeViewFormParent(mVideoView)
                val (_, _, _, _, _, _, videoUrl) = mVideoList[position]
                val playUrl = mPreloadManager!!.getPlayUrl(videoUrl)
                L.i("startPlay: position: $position  url: $playUrl")
                mVideoView!!.setUrl(playUrl)
                mController!!.addControlComponent(viewHolder.mTikTokView, true)
                viewHolder.mPlayerContainer.addView(mVideoView, 0)
                mVideoView!!.start()
                mCurPos = position
                break
            }
        }
    }

    fun addData(view: View?) {
//        if (mCurPos == 0 && mVideoList == null){
        mVideoList.addAll(tikTokBeans!!)
        //        }
        mAdapter!!.notifyDataSetChanged()
    }

    override fun onVisible() {
        isCur = true
        e("Play onVisible")
        post(MsgEvent(KEY_VIDEO_BOTTOM_VP, data = false), 100)
        if (mVideoView != null) {
            mVideoView!!.resume()
        }
    }

    override fun onInVisible() {
        isCur = false
        e("Play onInVisible")
        post(MsgEvent(KEY_VIDEO_BOTTOM_VP, data = true))
        if (mVideoView != null) {
            mVideoView!!.pause()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (mVideoView != null) {
            VideoViewManager.instance().releaseByTag("tiktok")
            mVideoView!!.release()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        if (mPreloadManager != null) {
            mPreloadManager!!.removeAllPreloadTask()
        }
        //清除缓存，实际使用可以不需要清除，这里为了方便测试
        ProxyVideoCacheManager.clearAllCache(context)
    }

    companion object {
        fun newInstance(pos: Int, data: List<TikTokBean?>?): VideoPlayFragment {
            val args = Bundle()
            args.putInt("CurPos", pos)
            args.putSerializable("data", data as Serializable?)
            val fragment = VideoPlayFragment()
            fragment.arguments = args
            return fragment
        }
    }
}