package com.yyxnb.common_base.base;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.github.anzewei.parallaxbacklayout.ParallaxBack;
import com.github.anzewei.parallaxbacklayout.ParallaxHelper;
import com.github.anzewei.parallaxbacklayout.widget.ParallaxBackLayout;
import com.yyxnb.common_base.constants.ArgumentKeys;
import com.yyxnb.what.arch.action.ArchAction;
import com.yyxnb.what.arch.action.BundleAction;
import com.yyxnb.what.arch.annotations.SwipeStyle;
import com.yyxnb.what.arch.base.IActivity;
import com.yyxnb.what.arch.base.IFragment;
import com.yyxnb.what.arch.base.Java8Observer;
import com.yyxnb.what.arch.config.AppManager;
import com.yyxnb.what.arch.delegate.ActivityDelegate;
import com.yyxnb.what.core.KeyboardUtils;
import com.yyxnb.what.core.action.AnimAction;
import com.yyxnb.what.core.action.ClickAction;
import com.yyxnb.what.core.action.HandlerAction;
import com.yyxnb.what.skinloader.SkinInflaterFactory;

import java.lang.ref.WeakReference;
import java.util.List;

import me.jessyan.autosize.AutoSizeCompat;

/**
 * 建议 {@link ContainerActivity#initBaseFragment()}  }
 */
@ParallaxBack(edgeMode = ParallaxBack.EdgeMode.EDGE)
public abstract class BaseActivity extends AppCompatActivity
        implements IActivity, ArchAction, BundleAction, HandlerAction, ClickAction, AnimAction {

    protected final String TAG = getClass().getCanonicalName();
    protected WeakReference<Context> mContext;

    private Java8Observer java8Observer;
    protected ActivityDelegate mActivityDelegate = getBaseDelegate();

    @Override
    public Context getContext() {
        return mContext.get();
    }

    public BaseActivity() {
        java8Observer = new Java8Observer(TAG);
        getLifecycle().addObserver(java8Observer);
        getLifecycle().addObserver(mActivityDelegate);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        SkinInflaterFactory.setFactory(this);
        getWindow().setBackgroundDrawable(null);
        mContext = new WeakReference<>(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setSwipeBack(int mSwipeBack) {
        final ParallaxBackLayout layout = ParallaxHelper.getParallaxBackLayout(this, true);
        switch (mSwipeBack) {
            case SwipeStyle.FULL:
                ParallaxHelper.enableParallaxBack(this);
                //全屏滑动
                layout.setEdgeMode(ParallaxBackLayout.EDGE_MODE_FULL);
                break;
            case SwipeStyle.EDGE:
                ParallaxHelper.enableParallaxBack(this);
                //边缘滑动
                layout.setEdgeMode(ParallaxBackLayout.EDGE_MODE_DEFAULT);
                break;
            case SwipeStyle.NONE:
                ParallaxHelper.disableParallaxBack(this);
                break;
            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        removeCallbacks();
        getLifecycle().removeObserver(java8Observer);
        mContext.clear();
        mContext = null;
        mActivityDelegate = null;
    }

    @Nullable
    @Override
    public Bundle getBundle() {
        return getIntent().getExtras();
    }

    @Override
    public Resources getResources() {
        //需要升级到 v1.1.2 及以上版本才能使用 AutoSizeCompat
        //如果没有自定义需求用这个方法
        AutoSizeCompat.autoConvertDensityOfGlobal(super.getResources());
        //如果有自定义需求就用这个方法
//        AutoSizeCompat.autoConvertDensity(super.getResources(), 667f, false);
        return super.getResources();
    }

    @Override
    public void onBackPressed() {
        List<Fragment> fragments = getSupportFragmentManager().getFragments();
        AppManager instance = AppManager.getInstance();
        if (instance.fragmentCount() > 1 && !fragments.isEmpty()) {
            if (fragments.get(fragments.size() - 1) instanceof IFragment) {
                BaseFragment current = (BaseFragment) instance.currentFragment();
                Fragment before = instance.beforeFragment();
                //将回调的传入到fragment中去
                if (current != null && before != null) {
                    before.onActivityResult(0, current.getResultCode(), current.getResult());
                }
            }
        }
        if (fragments.isEmpty()) {
            super.onBackPressed();
        } else {
            ActivityCompat.finishAfterTransition(this);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //把操作放在用户点击的时候
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            //得到当前页面的焦点,ps:有输入框的页面焦点一般会被输入框占据
            View v = getCurrentFocus();
            //判断用户点击的是否是输入框以外的区域
            if (mActivityDelegate.isShouldHideKeyboard(v, event)) {
                //收起键盘
                if (v != null) {
                    KeyboardUtils.hideSoftInput(v);
                }
            }
        }
        return super.onTouchEvent(event);
    }


    public <T extends IFragment> void startFragment(T targetFragment) {
        try {
            Intent intent = new Intent(this, ContainerActivity.class);
            Bundle bundle = targetFragment.initArguments();
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra(ArgumentKeys.FRAGMENT, targetFragment.getClass().getCanonicalName());
            intent.putExtra(ArgumentKeys.BUNDLE, bundle);
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public <T extends IFragment> void setRootFragment(T fragment, int containerId) {
        try {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(containerId, (Fragment) fragment, fragment.sceneId());
            transaction.addToBackStack(fragment.sceneId());
            transaction.commitAllowingStateLoss();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
