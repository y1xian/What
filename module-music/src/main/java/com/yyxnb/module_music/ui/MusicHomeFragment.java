package com.yyxnb.module_music.ui;

import android.Manifest;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.yyxnb.common_base.base.BaseFragment;
import com.yyxnb.common_res.constants.MusicRouterPath;
import com.yyxnb.common_res.weight.ScaleTransitionPagerTitleView;
import com.yyxnb.module_music.R;
import com.yyxnb.module_music.bean.MusicBean;
import com.yyxnb.module_music.bean.MusicRecordBean;
import com.yyxnb.module_music.databinding.FragmentMusicHomeBinding;
import com.yyxnb.module_music.db.MusicDatabase;
import com.yyxnb.module_music.view.BottomMusicView;
import com.yyxnb.module_music.viewmodel.MusicViewModel;
import com.yyxnb.what.adapter.base.BaseFragmentPagerAdapter;
import com.yyxnb.what.arch.annotations.BindViewModel;
import com.yyxnb.what.core.DpUtils;
import com.yyxnb.what.music.MusicPlayerManager;
import com.yyxnb.what.music.interfaces.MusicInitializeCallBack;
import com.yyxnb.what.music.interfaces.MusicPlayerEventListener;
import com.yyxnb.what.music.interfaces.MusicPlayerInfoListener;
import com.yyxnb.what.permission.PermissionListener;
import com.yyxnb.what.permission.PermissionUtils;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 音乐首页.
 */
@Route(path = MusicRouterPath.HOME_FRAGMENT)
public class MusicHomeFragment extends BaseFragment implements MusicPlayerEventListener<MusicBean> {

    private FragmentMusicHomeBinding binding;

    private MagicIndicator mIndicator;
    private ViewPager mViewPager;

    private BottomMusicView mBottomMusicView;
    @BindViewModel
    MusicViewModel mViewModel;
    private ArrayList<MusicBean> mLists = new ArrayList<>();

    private String[] titles = {"本地音乐", "网络音乐"};
    private List<Fragment> fragments;
    private boolean isFirstPlay = true;

    @Override
    public int initLayoutResId() {
        return R.layout.fragment_music_home;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        binding = getBinding();
        mIndicator = binding.mIndicator;
        mViewPager = binding.mViewPager;
        mBottomMusicView = binding.mBottomMusicView;
    }

    @Override
    public void initViewData() {

        if (fragments == null) {
            fragments = new ArrayList<>();
            fragments.add(new MusicLocalListFragment());
            fragments.add(new MusicNetworkFragment());
        }
        initIndicator();

        PermissionUtils.with(getActivity())
                .addPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .addPermissions(Manifest.permission.WAKE_LOCK)
                .addPermissions(Manifest.permission.VIBRATE)
                .setPermissionsCheckListener(new PermissionListener() {
                    @Override
                    public void permissionRequestSuccess() {
//                        initData();
//                        mViewModel.reqMusicData();
                    }

                    @Override
                    public void permissionRequestFail(String[] grantedPermissions, String[] deniedPermissions, String[] forceDeniedPermissions) {
                    }
                })
                .defaultConfig();
    }

    @Override
    public void initObservable() {
//        if (!LoginImpl.getInstance().isLogin()) {
//            startFragment(ARouterUtils.navFragment(LOGIN_FRAGMENT));
//        }

//        MusicService.startMusicService();

//        mViewModel.reqMusicData();

        MusicPlayerManager.getInstance().initialize(getActivity(), new MusicInitializeCallBack() {
            @Override
            public void onSuccess() {
                log("初始化");
                mBottomMusicView.showFirstView();
            }
        });


//        mViewModel.getMusicData().observe(this, data -> {
//            if (data != null) {
////                AudioHelper.controller().setQueue(data);
////                MusicPlayerManager.getInstance().setPlayingChannel(MusicConstants.CHANNEL_HISTROY);
//                MusicPlayerManager.getInstance().setMusic(data, 0);
////                MusicPlayerManager.getInstance().playOrPause();
//                mBottomMusicView.showFirstView();
//                LogUtils.list(data);
//                MusicPlayerManager.getInstance().addOnPlayerEventListener(this);
//            }
//        });

        MusicPlayerManager.getInstance().setPlayInfoListener((MusicPlayerInfoListener<MusicBean>) (musicInfo, position) -> {
            log(position + " , onPlayMusicOnInfo：" + musicInfo.toString());

            MusicRecordBean recordBean = new MusicRecordBean();
            recordBean.musicBean = musicInfo;
//            recordBean.currenTime = 0;
            recordBean.updateTime = System.currentTimeMillis();
            MusicDatabase.getInstance().recordDao().insertItem(recordBean);
            MusicPlayerManager.getInstance().addOnPlayerEventListener(this);
        });


//        mViewModel.getRecordData().observe(this, data -> {
//            if (data != null && isFirstPlay) {
//                mBottomMusicView.showFirstView();
//                MusicPlayerManager.getInstance().addOnPlayerEventListener(this);
//                isFirstPlay = false;
//            }
//        });

//        AudioHelper.controller().setQueue(mLists);


//        LogUtils.w("当前 ：" + MusicDatabase.getInstance().recordDao().getLastRecord());
//         // 全部记录
//        LogUtils.list(MusicDatabase.getInstance().recordDao().getRecordAll());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        MusicPlayerManager.getInstance().removeAllPlayerListener();
    }

    @Override
    public void onMusicPlayerState(int playerState, String message) {
        log("playerState : " + playerState + " , message : " + message);
    }

    @Override
    public void onPrepared(long totalDuration) {
        log("totalDuration : " + totalDuration);
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
    public void onTaskRuntime(long totalDuration, long currentDuration, long alarmResidueDuration, int bufferProgress) {
        //更新进度
        MusicRecordBean recordBean = new MusicRecordBean();
        recordBean.currenTime = currentDuration;
        recordBean.updateTime = System.currentTimeMillis();
        recordBean.musicBean = MusicPlayerManager.getInstance().getCurrentPlayerMusic();
        MusicDatabase.getInstance().recordDao().insertItem(recordBean);
    }

    @Override
    public void onPlayerConfig(int playModel, int alarmModel, boolean isToast) {

    }

    private void initIndicator() {
        CommonNavigator commonNavigator = new CommonNavigator(getActivity());
        //ture 即标题平分屏幕宽度的模式
        commonNavigator.setAdjustMode(true);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {

            @Override
            public int getCount() {
                return titles.length;
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                ScaleTransitionPagerTitleView colorTransitionPagerTitleView = new ScaleTransitionPagerTitleView(context);
                colorTransitionPagerTitleView.setNormalColor(Color.WHITE);
                colorTransitionPagerTitleView.setSelectedColor(Color.WHITE);
                colorTransitionPagerTitleView.setText(titles[index]);
                colorTransitionPagerTitleView.setOnClickListener(view -> mViewPager.setCurrentItem(index));
                return colorTransitionPagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator indicator = new LinePagerIndicator(context);
                //设置宽度
                indicator.setLineWidth(DpUtils.dp2px(getContext(), 20));
                //设置高度
                indicator.setLineHeight(DpUtils.dp2px(getContext(), 2));
                //设置颜色
                indicator.setColors(Color.WHITE);
                //设置圆角
                indicator.setRoundRadius(5);
                //设置模式
                indicator.setMode(LinePagerIndicator.MODE_EXACTLY);
                return indicator;
            }
        });
        mIndicator.setNavigator(commonNavigator);

        mViewPager.setOffscreenPageLimit(titles.length);
        mViewPager.setAdapter(new BaseFragmentPagerAdapter(getChildFragmentManager(), fragments, Arrays.asList(titles)));
        //与ViewPagger联动
        ViewPagerHelper.bind(mIndicator, mViewPager);
    }
}