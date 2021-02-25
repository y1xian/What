package com.yyxnb.module_video.ui.main;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.SparseArray;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;

import com.yyxnb.lib_arch.annotations.BindRes;
import com.yyxnb.util_core.DpUtils;
import com.yyxnb.common_res.arouter.ARouterUtils;
import com.yyxnb.common_res.arouter.service.impl.LoginImpl;
import com.yyxnb.common_base.core.BaseFragment;
import com.yyxnb.module_video.R;
import com.yyxnb.module_video.databinding.FragmentVideoMainBottomBinding;
import com.yyxnb.module_video.ui.find.VideoFindFragment;
import com.yyxnb.module_video.ui.home.VideoHomeFragment;
import com.yyxnb.lib_view.text.DrawableRadioButton;

import static com.yyxnb.common_res.arouter.ARouterConstant.LOGIN_FRAGMENT;
import static com.yyxnb.common_res.arouter.ARouterConstant.CHAT_LIST_FRAGMENT;
import static com.yyxnb.common_res.arouter.ARouterConstant.USER_MAIN_FRAGMENT;

/**
 * 主页
 */
@BindRes(subPage = true)
public class VideoMainBottomFragment extends BaseFragment implements View.OnClickListener {

    private static final int HOME = 0;
    private static final int FIND = 1;
    private static final int MSG = 2;
    private static final int ME = 3;

    private DrawableRadioButton mBtnHome;
    private DrawableRadioButton mBtnFind;
    private DrawableRadioButton mBtnMsg;
    private DrawableRadioButton mBtnMe;
    private View mRecordTip;
    private Animation mAnimation;

    //当前选中的fragment的key
    private int mCurKey;
    private SparseArray<Fragment> mSparseArray;
    private FragmentManager mFragmentManager;
    private VideoHomeFragment mHomeFragment;
    private boolean mShowingRecordTip;

    private FragmentVideoMainBottomBinding binding;

    @Override
    public int initLayoutResId() {
        return R.layout.fragment_video_main_bottom;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        binding = getBinding();
        mBtnHome = binding.btnHome;
        mBtnFind = binding.btnFind;
        mBtnMsg = binding.btnMsg;
        mBtnMe = binding.btnMe;
        mRecordTip = binding.ivRecordTip;
        mBtnHome.setOnClickListener(this);
        mBtnFind.setOnClickListener(this);
        mBtnMsg.setOnClickListener(this);
        mBtnMe.setOnClickListener(this);
        mRecordTip.setOnClickListener(this);
    }

    @Override
    public void initViewData() {
        log("main bottom initViewData ");

        mFragmentManager = getChildFragmentManager();
        if (mSparseArray == null) {
            mSparseArray = new SparseArray<>();
            mHomeFragment = new VideoHomeFragment();
            mSparseArray.put(HOME, mHomeFragment);
            mSparseArray.put(FIND, new VideoFindFragment());
            mSparseArray.put(MSG, (Fragment) ARouterUtils.navFragment(CHAT_LIST_FRAGMENT));
//            mSparseArray.put(ME, VideoUserFragment.newInstance(true));
            mSparseArray.put(ME, (Fragment) ARouterUtils.navFragment(USER_MAIN_FRAGMENT));
        }

        mCurKey = HOME;

        FragmentTransaction ft = mFragmentManager.beginTransaction();
        for (int i = 0, size = mSparseArray.size(); i < size; i++) {
            Fragment fragment = mSparseArray.valueAt(i);
            ft.add(R.id.fl_content, fragment);
            if (mSparseArray.keyAt(i) == mCurKey) {
                ft.show(fragment);
            } else {
                ft.hide(fragment);
            }
        }
        ft.commit();


        mAnimation = new TranslateAnimation(Animation.ABSOLUTE, 0, Animation.ABSOLUTE, 0,
                Animation.ABSOLUTE, 0, Animation.ABSOLUTE, DpUtils.dp2px(getActivity(), 5));
        mAnimation.setRepeatCount(-1);
        mAnimation.setRepeatMode(Animation.REVERSE);
        mAnimation.setDuration(400);
    }


    @Override
    public void onVisible() {
        log("main ov");
    }

    public void showRecordTip(boolean show) {
        if (mCurKey == ME) {
            toggleRecordTip(show);
        }
    }

    @Override
    public void onDestroy() {
        if (mRecordTip != null) {
            mRecordTip.clearAnimation();
        }
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btn_home) {
            toggleHome();
        } else if (id == R.id.btn_find) {
            toggleFind();
        } else if (id == R.id.btn_msg) {
            toggleMsg();
        } else if (id == R.id.btn_me) {
            toggleMe();
        } else if (id == R.id.iv_record_tip) {
            // 跳录制
        }
    }

    private void toggle(int key) {
        if (key == mCurKey) {
            if (key == 0) {
                // 刷新首页

            }
            return;
        }
        mCurKey = key;
        FragmentTransaction ft = mFragmentManager.beginTransaction();
        for (int i = 0, size = mSparseArray.size(); i < size; i++) {
            Fragment fragment = mSparseArray.valueAt(i);
            if (mSparseArray.keyAt(i) == mCurKey) {
                ft.show(fragment);
            } else {
                ft.hide(fragment);
            }
        }
        ft.commitAllowingStateLoss();
    }

    /**
     * 切换到Home
     */
    private void toggleHome() {
        toggle(HOME);
        if (mBtnHome != null) {
            mBtnHome.doToggle();
        }
        if (mHomeFragment.isRecommend()) {
            setCanScroll(true);
        }
        toggleRecordTip(false);
    }

    /**
     * 切换到发现
     */
    private void toggleFind() {
        toggle(FIND);
        if (mBtnFind != null) {
            mBtnFind.doToggle();
        }
        setCanScroll(false);
        toggleRecordTip(false);

    }

    /**
     * 切换到消息
     */
    private void toggleMsg() {
//        if (BaseConfig.getInstance().isLogin()) {
            toggle(MSG);
            if (mBtnMsg != null) {
                mBtnMsg.doToggle();
            }
            setCanScroll(false);
            toggleRecordTip(false);
//        } else {
//            forwardLogin();
//        }
    }

    /**
     * 切换到我的
     */
    private void toggleMe() {
//        if (BaseConfig.getInstance().isLogin()) {
            toggle(ME);
            if (mBtnMe != null) {
                mBtnMe.doToggle();
            }
            setCanScroll(false);
            toggleRecordTip(true);
//        } else {
//            forwardLogin();
//        }
    }

    // 显示动画
    private void toggleRecordTip(boolean show) {
        if (mShowingRecordTip == show) {
            return;
        }
        mShowingRecordTip = show;
        if (mRecordTip != null && mAnimation != null) {
            if (show) {
                if (mRecordTip.getVisibility() != View.VISIBLE) {
                    mRecordTip.setVisibility(View.VISIBLE);
                }
                mRecordTip.startAnimation(mAnimation);
            } else {
                mRecordTip.clearAnimation();
                if (mRecordTip.getVisibility() == View.VISIBLE) {
                    mRecordTip.setVisibility(View.INVISIBLE);
                }
            }
        }
    }

    // 跳转登录
    private void forwardLogin() {
        log("跳登录 " + LoginImpl.getInstance().getUserInfo().toString());
        startFragment(ARouterUtils.navFragment(LOGIN_FRAGMENT));
    }

    // 最底层vp { @link VideoMainFragment } 是否能切换 （视频/个人）
    private void setCanScroll(boolean b) {

    }
}