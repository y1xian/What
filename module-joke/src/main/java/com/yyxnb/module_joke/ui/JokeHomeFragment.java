package com.yyxnb.module_joke.ui;

import android.content.pm.ActivityInfo;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.transition.Transition;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.dueeeke.videocontroller.StandardVideoController;
import com.dueeeke.videocontroller.component.CompleteView;
import com.dueeeke.videocontroller.component.ErrorView;
import com.dueeeke.videocontroller.component.GestureView;
import com.dueeeke.videocontroller.component.PrepareView;
import com.dueeeke.videocontroller.component.TitleView;
import com.dueeeke.videocontroller.component.VodControlView;
import com.dueeeke.videoplayer.player.VideoView;
import com.dueeeke.videoplayer.player.VideoViewManager;
import com.dueeeke.videoplayer.util.L;
import com.yyxnb.common_base.base.BaseFragment;
import com.yyxnb.common_res.constants.JokeRouterPath;
import com.yyxnb.module_joke.R;
import com.yyxnb.module_joke.adapter.JokeHomeAdapter;
import com.yyxnb.module_joke.bean.TikTokBean;
import com.yyxnb.module_joke.config.DataConfig;
import com.yyxnb.module_joke.databinding.FragmentJokeHomeBinding;
import com.yyxnb.what.adapter.ItemDecoration;
import com.yyxnb.what.adapter.SimpleOnItemClickListener;
import com.yyxnb.what.adapter.base.BaseViewHolder;
import com.yyxnb.what.video.Utils;
import com.yyxnb.what.video.cache.PreloadManager;
import com.yyxnb.what.video.cache.ProxyVideoCacheManager;

import java.util.ArrayList;
import java.util.List;

import static androidx.recyclerview.widget.RecyclerView.SCROLL_STATE_IDLE;

/**
 * joke 首页.
 */
@Route(path = JokeRouterPath.HOME_FRAGMENT)
public class JokeHomeFragment extends BaseFragment {

    private FragmentJokeHomeBinding binding;
    private RecyclerView mRecyclerView;
    private JokeHomeAdapter mAdapter;

    protected VideoView mVideoView;
    protected StandardVideoController mController;
    protected ErrorView mErrorView;
    protected CompleteView mCompleteView;
    protected TitleView mTitleView;

    protected LinearLayoutManager mLinearLayoutManager;
    protected List<TikTokBean> mVideos = new ArrayList<>();

    private PreloadManager mPreloadManager;

    /**
     * 当前播放的位置
     */
    protected int mCurPos = -1;
    /**
     * 上次播放的位置，用于页面切回来之后恢复播放
     */
    protected int mLastPos = mCurPos;


    @Override
    public int initLayoutResId() {
        return R.layout.fragment_joke_home;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        binding = getBinding();
        mRecyclerView = binding.rvContent;

        mPreloadManager = PreloadManager.getInstance(getContext());

        initVideoView();

        mAdapter = new JokeHomeAdapter();
        mLinearLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        ItemDecoration decoration = new ItemDecoration(getContext());
//        decoration.setDividerColor(Color.WHITE)
//                .setDividerHeight(1)
//                .setDividerWidth(1)
//                .setDrawBorderTopAndBottom(true)
//                .setDrawBorderLeftAndRight(true);
        mRecyclerView.addItemDecoration(decoration);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView.addOnChildAttachStateChangeListener(new RecyclerView.OnChildAttachStateChangeListener() {
            @Override
            public void onChildViewAttachedToWindow(@NonNull View view) {

            }

            @Override
            public void onChildViewDetachedFromWindow(@NonNull View view) {
                FrameLayout playerContainer = view.findViewById(R.id.player_container);
                if (playerContainer != null) {
                    View v = playerContainer.getChildAt(0);
                    if (v != null && v == mVideoView && !mVideoView.isFullScreen()) {
                        releaseVideoView();
                    }
                }

            }
        });

        mAdapter.setOnItemClickListener(new SimpleOnItemClickListener() {
            @Override
            public void onItemClick(View view, BaseViewHolder holder, int position) {
                super.onItemClick(view, holder, position);
                if (mVideos.get(position).type == 0) {
                    startPlay(position);
                }
            }
        });

    }

    private void initVideoView() {
        mVideoView = new VideoView(getActivity());

        mVideoView.setOnStateChangeListener(new VideoView.SimpleOnStateChangeListener() {
            @Override
            public void onPlayStateChanged(int playState) {
                //监听VideoViewManager释放，重置状态
                if (playState == VideoView.STATE_IDLE) {
                    Utils.removeViewFormParent(mVideoView);
                    mLastPos = mCurPos;
                    mCurPos = -1;
                }
            }
        });

        mController = new StandardVideoController(getActivity());
        mErrorView = new ErrorView(getActivity());
        mController.addControlComponent(mErrorView);
        mCompleteView = new CompleteView(getActivity());
        mController.addControlComponent(mCompleteView);
        mTitleView = new TitleView(getActivity());
        mController.addControlComponent(mTitleView);
        mController.addControlComponent(new VodControlView(getActivity()));
        mController.addControlComponent(new GestureView(getActivity()));
//        mController.setEnableOrientation(true);
        mVideoView.setVideoController(mController);

        VideoViewManager.instance().add(mVideoView, "SEAMLESS");
    }

    @Override
    public void initViewData() {
        mVideos.addAll(DataConfig.getTikTokBeans());
        mAdapter.setDataItems(mVideos);

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == SCROLL_STATE_IDLE) { //滚动停止
                    autoPlayVideo(recyclerView);
                    int lastVisibleItem = mLinearLayoutManager.findFirstVisibleItemPosition();
                    int lastVisibleItem1 = mLinearLayoutManager.findFirstCompletelyVisibleItemPosition();
                    log("lastVisibleItem " + lastVisibleItem + " ," + lastVisibleItem1);
                }
            }

            private void autoPlayVideo(RecyclerView view) {
                if (view == null) {
                    return;
                }
                //遍历RecyclerView子控件,如果mPlayerContainer完全可见就开始播放
                int count = view.getChildCount();
                L.d("ChildCount:" + count);
                for (int i = 0; i < count; i++) {
                    View itemView = view.getChildAt(i);
                    if (itemView == null || itemView.findViewById(R.id.player_container) == null) {
                        continue;
                    }

                    //第一个Item完全显示的item位置 ,否则为-1
                    int findFirst = mLinearLayoutManager.findFirstCompletelyVisibleItemPosition();

                    FrameLayout mPlayerContainer = itemView.findViewById(R.id.player_container);

//                    BaseViewHolder holder = (BaseViewHolder) itemView.getTag();
                    Rect rect = new Rect();
                    mPlayerContainer.getLocalVisibleRect(rect);
                    int height = mPlayerContainer.getHeight();
                    if (rect.top == 0 && rect.bottom == height && findFirst != -1) {
                        log(" pos " + mAdapter.mPosition + " , findFirst " + findFirst);
                        startPlay(findFirst);
                        break;
                    }
                }
            }
        });

        mRecyclerView.post(() -> {
            //自动播放第一个
            if (mVideos.get(0).type == 0) {
                startPlay(0);
            }
        });
    }

    /**
     * 开始播放
     *
     * @param position 列表位置
     */
    protected void startPlay(int position) {
        log("mCurPos " + mCurPos + ", p " + position);
        if (mCurPos == position) {
            return;
        }
        if (mCurPos != -1) {
            releaseVideoView();
        }
        TikTokBean videoBean = mVideos.get(position);
        //边播边存
        String playUrl = mPreloadManager.getPlayUrl(videoBean.videoUrl);
        log("startPlay: " + "position: " + position + "  url: " + playUrl);

        mVideoView.setUrl(playUrl);
        mTitleView.setTitle(videoBean.title);
        View itemView = mLinearLayoutManager.findViewByPosition(position);
        if (itemView == null || itemView.findViewById(R.id.player_container) == null) {
            return;
        }

        final FrameLayout mPlayerContainer = itemView.findViewById(R.id.player_container);
        final PrepareView mPrepareView = itemView.findViewById(R.id.prepare_view);

//        BaseViewHolder viewHolder = (BaseViewHolder) itemView.getTag();
        //把列表中预置的PrepareView添加到控制器中，注意isPrivate此处只能为true。
        mController.addControlComponent(mPrepareView, true);
        Utils.removeViewFormParent(mVideoView);
        mPlayerContainer.addView(mVideoView, 0);
        //播放之前将VideoView添加到VideoViewManager以便在别的页面也能操作它
//        VideoViewManager.instance().add(mVideoView, "LIST");
        mVideoView.start();
        mCurPos = position;

    }

    private void releaseVideoView() {
        mVideoView.release();
        if (mVideoView.isFullScreen()) {
            mVideoView.stopFullScreen();
        }
        if (getActivity().getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
            getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        mCurPos = -1;
    }

    private void restoreVideoView() {
        //还原播放器
        View itemView = mLinearLayoutManager.findViewByPosition(mCurPos);
        if (itemView == null || itemView.findViewById(R.id.player_container) == null) {
            return;
        }

        FrameLayout mPlayerContainer = itemView.findViewById(R.id.player_container);
        PrepareView mPrepareView = itemView.findViewById(R.id.prepare_view);
        mVideoView = VideoViewManager.instance().get("SEAMLESS");
        Utils.removeViewFormParent(mVideoView);
        mPlayerContainer.addView(mVideoView, 0);

        mController.addControlComponent(mPrepareView, true);
        mController.setPlayState(mVideoView.getCurrentPlayState());
        mController.setPlayerState(mVideoView.getCurrentPlayerState());

        mRecyclerView.postDelayed(() -> mVideoView.setVideoController(mController), 100);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onStart() {
        super.onStart();
        if (!addTransitionListener()) {
            restoreVideoView();
        }
    }

    @RequiresApi(21)
    private boolean addTransitionListener() {
        final Transition transition = getActivity().getWindow().getSharedElementExitTransition();
        if (transition != null) {
            transition.addListener(new Transition.TransitionListener() {
                @Override
                public void onTransitionEnd(Transition transition) {
                    restoreVideoView();
                    transition.removeListener(this);
                }

                @Override
                public void onTransitionStart(Transition transition) {

                }

                @Override
                public void onTransitionCancel(Transition transition) {
                    transition.removeListener(this);
                }

                @Override
                public void onTransitionPause(Transition transition) {
                }

                @Override
                public void onTransitionResume(Transition transition) {
                }
            });
            return true;
        }
        return false;
    }

    @Override
    public void onVisible() {
        log("joke ov");
        if (mVideoView != null) {
            mVideoView.resume();
        }
    }

    @Override
    public void onInVisible() {
        log("joke iv");
        if (mVideoView != null) {
            mVideoView.pause();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        log("joke od");
        if (mVideoView != null) {
            mVideoView.release();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        log("joke odv");
        if (mPreloadManager != null) {
            mPreloadManager.removeAllPreloadTask();
        }
        VideoViewManager.instance().releaseByTag("SEAMLESS");
        //清除缓存，实际使用可以不需要清除，这里为了方便测试
        ProxyVideoCacheManager.clearAllCache(getContext());
    }
}