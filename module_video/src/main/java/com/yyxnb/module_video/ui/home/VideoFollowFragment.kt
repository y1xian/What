package com.yyxnb.module_video.ui.home

import android.os.Bundle
import android.widget.FrameLayout
import androidx.recyclerview.widget.RecyclerView
import com.dueeeke.videoplayer.player.AbstractPlayer
import com.dueeeke.videoplayer.player.VideoView
import com.dueeeke.videoplayer.player.VideoView.SimpleOnStateChangeListener
import com.dueeeke.videoplayer.player.VideoViewManager
import com.dueeeke.videoplayer.util.L
import com.yyxnb.arch.annotations.BindRes
import com.yyxnb.common.utils.log.LogUtils.e
import com.yyxnb.common.utils.log.LogUtils.w
import com.yyxnb.common_base.base.BaseFragment
import com.yyxnb.module_video.R
import com.yyxnb.module_video.adapter.TikTokRvAdapter
import com.yyxnb.module_video.bean.TikTokBean
import com.yyxnb.module_video.config.DataConfig.tikTokBeans
import com.yyxnb.module_video.databinding.FragmentVideoFollowBinding
import com.yyxnb.module_video.widget.OnViewPagerListener
import com.yyxnb.module_video.widget.ViewPagerLayoutManager
import com.yyxnb.module_video.widget.tiktok.TikTokController
import com.yyxnb.module_video.widget.tiktok.TikTokRenderViewFactory
import com.yyxnb.module_video.widget.tiktok.TikTokView
import com.yyxnb.video.Utils
import com.yyxnb.video.cache.PreloadManager
import com.yyxnb.video.cache.ProxyVideoCacheManager
import java.util.*

/**
 * 关注
 */
@BindRes(subPage = true)
class VideoFollowFragment : BaseFragment() {

    private var binding: FragmentVideoFollowBinding? = null
    private var mRecyclerView: RecyclerView? = null
    private var mVideoView: VideoView<AbstractPlayer>? = null
    private var mController: TikTokController? = null
    private var mPreloadManager: PreloadManager? = null
    private var viewPagerLayoutManager: ViewPagerLayoutManager? = null
    private var mAdapter: TikTokRvAdapter? = null
    private val mVideoList: MutableList<TikTokBean> = ArrayList()
    private var mCurPos = 0
    private var isCur = false

    override fun initLayoutResId(): Int {
        return R.layout.fragment_video_follow
    }

    override fun initView(savedInstanceState: Bundle?) {
        binding = getBinding()
        mRecyclerView = binding!!.mRecyclerView
    }

    override fun initViewData() {
        initRecyclerView()
        initVideoView()
        mPreloadManager = PreloadManager.getInstance(context)
    }

    override fun initObservable() {
        mVideoList.addAll(tikTokBeans!!)
        mAdapter!!.setDataItems(mVideoList)
    }

    private fun initRecyclerView() {
        mAdapter = TikTokRvAdapter()
        viewPagerLayoutManager = ViewPagerLayoutManager(context)
        mRecyclerView!!.layoutManager = viewPagerLayoutManager
        mRecyclerView!!.setHasFixedSize(true)
        mRecyclerView!!.adapter = mAdapter
        viewPagerLayoutManager!!.setOnViewPagerListener(object : OnViewPagerListener {
            override fun onInitComplete() {
                //自动播放第index条
                startPlay(mCurPos)
            }

            override fun onPageRelease(isNext: Boolean, position: Int) {
                if (mCurPos == position) {
                    mVideoView!!.release()
                }
            }

            override fun onPageSelected(position: Int, isBottom: Boolean) {
                if (mCurPos == position) {
                    return
                }
                startPlay(position)
            }
        })
    }

    private fun initVideoView() {
        mVideoView = VideoView<AbstractPlayer>(context)
        mVideoView?.setLooping(true)

        //以下只能二选一，看你的需求
        mVideoView?.setRenderViewFactory(TikTokRenderViewFactory.create())
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

    private fun startPlay(position: Int) {
        val itemView = mRecyclerView!!.getChildAt(0)
        val mTikTokView: TikTokView = itemView.findViewById(R.id.tiktok_View)
        val mPlayerContainer = itemView.findViewById<FrameLayout>(R.id.container)
        mVideoView!!.release()
        Utils.removeViewFormParent(mVideoView)
        val (_, _, _, _, _, _, videoUrl) = mVideoList[position]
        val playUrl = mPreloadManager!!.getPlayUrl(videoUrl)
        L.i("startPlay: position: $position  url: $playUrl")
        mVideoView!!.setUrl(playUrl)
        mController!!.addControlComponent(mTikTokView, true)
        mPlayerContainer.addView(mVideoView, 0)
        mVideoView!!.start()
        mCurPos = position
    }

    override fun onVisible() {
        isCur = true
        w("follow v")
        if (mVideoView != null) {
            mVideoView!!.resume()
        }
    }

    override fun onInVisible() {
        isCur = false
        w("follow iv")
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
}