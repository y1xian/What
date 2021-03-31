package com.yyxnb.module_video.ui.play;

import android.os.Bundle;
import android.view.View;

import androidx.viewpager.widget.ViewPager;

import com.dueeeke.videoplayer.player.VideoView;
import com.dueeeke.videoplayer.player.VideoViewManager;
import com.yyxnb.common_base.base.BaseFragment;
import com.yyxnb.module_video.R;
import com.yyxnb.module_video.adapter.TikTokAdapter;
import com.yyxnb.module_video.bean.TikTokBean;
import com.yyxnb.module_video.config.DataConfig;
import com.yyxnb.module_video.databinding.FragmentVideoPlayBinding;
import com.yyxnb.module_video.viewmodel.VideoViewModel;
import com.yyxnb.module_video.widget.VerticalViewPager;
import com.yyxnb.module_video.widget.tiktok.TikTokController;
import com.yyxnb.module_video.widget.tiktok.TikTokRenderViewFactory;
import com.yyxnb.what.arch.annotations.BindRes;
import com.yyxnb.what.arch.annotations.BindViewModel;
import com.yyxnb.what.arch.bean.MsgEvent;
import com.yyxnb.what.arch.helper.BusHelper;
import com.yyxnb.what.core.log.LogUtils;
import com.yyxnb.what.permission.PermissionUtils;
import com.yyxnb.what.video.Utils;
import com.yyxnb.what.video.cache.PreloadManager;
import com.yyxnb.what.video.cache.ProxyVideoCacheManager;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static com.yyxnb.common_res.constants.Constants.KEY_VIDEO_BOTTOM_VP;
import static com.yyxnb.common_res.constants.Constants.KEY_VIDEO_BOTTOM_VP_SWITCH;

/**
 * 短视频播放的fragment 可以上下滑动
 */
@BindRes(subPage = true)
public class VideoPlayFragment extends BaseFragment {

    @BindViewModel
    public VideoViewModel mViewModel;

    private FragmentVideoPlayBinding binding;
    private VerticalViewPager mViewPager;

    private VideoView mVideoView;
    private TikTokController mController;
    private PreloadManager mPreloadManager;
    private TikTokAdapter mAdapter;
    private List<TikTokBean> mVideoList = new ArrayList<>();
    private int mCurPos;
    private boolean isCur;

    public static VideoPlayFragment newInstance(int pos, List<TikTokBean> data) {

        Bundle args = new Bundle();
        args.putInt("CurPos", pos);
        args.putSerializable("data", (Serializable) data);
        VideoPlayFragment fragment = new VideoPlayFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int initLayoutResId() {
        return R.layout.fragment_video_play;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        binding = getBinding();
        mViewPager = binding.vpContent;

    }

    @Override
    public void initViewData() {
//        mCurPos = getArguments().getInt("CurPos",0);
//        mVideoList = (List<TikTokBean>) getArguments().getSerializable("data");

        if (!PermissionUtils.hasPermissions(getActivity(), PermissionUtils.FILE_REQUIRE_PERMISSIONS)) {
            PermissionUtils.with(getActivity())
                    .addPermissions(PermissionUtils.FILE_REQUIRE_PERMISSIONS)
                    .defaultConfig();
        }

        initViewPager();
        initVideoView();
        mPreloadManager = PreloadManager.getInstance(getContext());

//        addData(null);

//        mViewPager.post(() -> startPlay(mCurPos));

        // 点赞、评论等交互
        mAdapter.setOnSelectListener((v, position, text) -> {
            switch (position) {
                case 0:
                    break;
                case 5:
                    BusHelper.post(new MsgEvent(KEY_VIDEO_BOTTOM_VP_SWITCH, 1));
                    break;
            }
            toast(text);
        });
    }

    @Override
    public void initObservable() {

        mViewModel.reqVideoList();

        mViewModel.result1().observe(this, data -> {
            if (data != null) {
                mVideoList.addAll(data);
                LogUtils.list(mVideoList);
                mAdapter.notifyDataSetChanged();
                mViewPager.post(() -> startPlay(mCurPos));
            }
        });
    }

    private void initVideoView() {
        mVideoView = new VideoView(getActivity());
        mVideoView.setLooping(true);

        //以下只能二选一，看你的需求
        mVideoView.setRenderViewFactory(TikTokRenderViewFactory.create());
//        mVideoView.setScreenScaleType(VideoView.SCREEN_SCALE_CENTER_CROP);

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

    private void initViewPager() {
//        mViewPager.setOffscreenPageLimit(4);
        mAdapter = new TikTokAdapter(mVideoList);
        mViewPager.setAdapter(mAdapter);
        mViewPager.setOverScrollMode(View.OVER_SCROLL_NEVER);
        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {

            private int mCurItem;

            /**
             * VerticalViewPager是否反向滑动
             */
            private boolean mIsReverseScroll;

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
                if (position == mCurItem) {
                    return;
                }
                mIsReverseScroll = position < mCurItem;
            }

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                if (position == mCurPos) {
                    return;
                }
                startPlay(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
                if (state == VerticalViewPager.SCROLL_STATE_DRAGGING) {
                    mCurItem = mViewPager.getCurrentItem();
                }

                if (state == VerticalViewPager.SCROLL_STATE_IDLE) {
                    mPreloadManager.resumePreload(mCurPos, mIsReverseScroll);
                } else {
                    mPreloadManager.pausePreload(mCurPos, mIsReverseScroll);
                }
            }
        });
    }

    private void startPlay(int position) {
        int count = mViewPager.getChildCount();
        try {
            for (int i = 0; i < count; i++) {
                View itemView = mViewPager.getChildAt(i);
                TikTokAdapter.ViewHolder viewHolder = (TikTokAdapter.ViewHolder) itemView.getTag();
                if (viewHolder.mPosition == position) {
                    mVideoView.release();
                    Utils.removeViewFormParent(mVideoView);

                    TikTokBean tiktokBean = mVideoList.get(position);
                    String playUrl = mPreloadManager.getPlayUrl(tiktokBean.videoUrl);
                    log("startPlay: " + "position: " + position + "  url: " + playUrl);
                    mVideoView.setUrl(playUrl);
                    mController.addControlComponent(viewHolder.mTikTokView, true);
                    viewHolder.mPlayerContainer.addView(mVideoView, 0);
                    mVideoView.start();
                    mCurPos = position;
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addData(View view) {
//        if (mCurPos == 0 && mVideoList == null){
        mVideoList.addAll(DataConfig.getTikTokBeans());
//        }
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onVisible() {
        isCur = true;
        log("Play onVisible");
        BusHelper.post(new MsgEvent(KEY_VIDEO_BOTTOM_VP, false), 100);
        if (mVideoView != null) {
            mVideoView.resume();
        }
    }

    @Override
    public void onInVisible() {
        isCur = false;
        log("Play onInVisible");
        BusHelper.post(new MsgEvent(KEY_VIDEO_BOTTOM_VP, true));
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