package com.yyxnb.module_video.ui.home;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import androidx.recyclerview.widget.RecyclerView;

import com.dueeeke.videoplayer.player.VideoView;
import com.dueeeke.videoplayer.player.VideoViewManager;
import com.yyxnb.common_base.base.BaseFragment;
import com.yyxnb.module_video.R;
import com.yyxnb.module_video.adapter.TikTokRvAdapter;
import com.yyxnb.module_video.bean.TikTokBean;
import com.yyxnb.module_video.config.DataConfig;
import com.yyxnb.module_video.databinding.FragmentVideoFollowBinding;
import com.yyxnb.module_video.widget.OnViewPagerListener;
import com.yyxnb.module_video.widget.ViewPagerLayoutManager;
import com.yyxnb.module_video.widget.tiktok.TikTokController;
import com.yyxnb.module_video.widget.tiktok.TikTokRenderViewFactory;
import com.yyxnb.module_video.widget.tiktok.TikTokView;
import com.yyxnb.what.arch.annotations.BindRes;
import com.yyxnb.what.video.Utils;
import com.yyxnb.what.video.cache.PreloadManager;
import com.yyxnb.what.video.cache.ProxyVideoCacheManager;

import java.util.ArrayList;
import java.util.List;

/**
 * 关注
 */
@BindRes(subPage = true)
public class VideoFollowFragment extends BaseFragment {

    private FragmentVideoFollowBinding binding;
    private RecyclerView mRecyclerView;

    private VideoView mVideoView;
    private TikTokController mController;
    private PreloadManager mPreloadManager;

    private ViewPagerLayoutManager viewPagerLayoutManager;
    private TikTokRvAdapter mAdapter;
    private List<TikTokBean> mVideoList = new ArrayList<>();
    private int mCurPos;
    private boolean isCur;

    @Override
    public int initLayoutResId() {
        return R.layout.fragment_video_follow;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        binding = getBinding();
        mRecyclerView = binding.rvContent;
    }

    @Override
    public void initViewData() {
        initRecyclerView();
        initVideoView();
        mPreloadManager = PreloadManager.getInstance(getContext());
    }


    @Override
    public void initObservable() {
        mVideoList.addAll(DataConfig.getTikTokBeans());
        mAdapter.setDataItems(mVideoList);
    }

    private void initRecyclerView() {
        mAdapter = new TikTokRvAdapter();

        viewPagerLayoutManager = new ViewPagerLayoutManager(getContext());
        mRecyclerView.setLayoutManager(viewPagerLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mAdapter);

        viewPagerLayoutManager.setOnViewPagerListener(new OnViewPagerListener() {
            @Override
            public void onInitComplete() {
                //自动播放第index条
                startPlay(mCurPos);
            }

            @Override
            public void onPageRelease(boolean isNext, int position) {
                if (mCurPos == position) {
                    mVideoView.release();
                }
            }

            @Override
            public void onPageSelected(int position, boolean isBottom) {
                if (mCurPos == position) {
                    return;
                }
                startPlay(position);
            }
        });
    }

    private void initVideoView() {
        mVideoView = new VideoView(getActivity());
        mVideoView.setLooping(true);

        //以下只能二选一，看你的需求
        mVideoView.setRenderViewFactory(TikTokRenderViewFactory.create());

        mController = new TikTokController(getActivity());
        mVideoView.setVideoController(mController);

        mVideoView.addOnStateChangeListener(new VideoView.SimpleOnStateChangeListener() {
            @Override
            public void onPlayStateChanged(int playState) {
                if (playState == VideoView.STATE_PLAYING) {
                    log("Play STATE_PLAYING");
                    // 处理快速切换界面，缓存刚刚好就回继续播放的问题
                    if (!isCur) {
                        mVideoView.pause();
                    }
                }
            }
        });

        VideoViewManager.instance().add(mVideoView, "tiktok");
    }

    private void startPlay(int position) {
        View itemView = mRecyclerView.getChildAt(0);

        final TikTokView mTikTokView = itemView.findViewById(R.id.tiktok_View);
        final FrameLayout mPlayerContainer = itemView.findViewById(R.id.container);

        mVideoView.release();
        Utils.removeViewFormParent(mVideoView);
        TikTokBean item = mVideoList.get(position);
        String playUrl = mPreloadManager.getPlayUrl(item.videoUrl);
        log("startPlay: " + "position: " + position + "  url: " + playUrl);
        mVideoView.setUrl(playUrl);
        mController.addControlComponent(mTikTokView, true);
        mPlayerContainer.addView(mVideoView, 0);
        mVideoView.start();
        mCurPos = position;
    }

    @Override
    public void onVisible() {
        isCur = true;
        log("follow v");
        if (mVideoView != null) {
            mVideoView.resume();
        }
    }

    @Override
    public void onInVisible() {
        isCur = false;
        log("follow iv");
        if (mVideoView != null) {
            mVideoView.pause();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mVideoView != null) {
            VideoViewManager.instance().releaseByTag("tiktok");
            mVideoView.release();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mPreloadManager != null) {
            mPreloadManager.removeAllPreloadTask();
        }
        //清除缓存，实际使用可以不需要清除，这里为了方便测试
        ProxyVideoCacheManager.clearAllCache(getContext());
    }

}