package com.yyxnb.arch.base.nav;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.animation.AnimationUtils;

import com.yyxnb.arch.base.BaseFragment;
import com.yyxnb.arch.base.RootActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * 具体的操作实现
 *
 * Created by panda on 2017/7/24.
 */
public final class OpsManager implements OpsInterface {
    private final FragmentActivity context;

    public OpsManager(FragmentActivity context) {
        this.context = context;
    }

    @Override
    public void jumpFragment(BaseFragment from, @NonNull BaseFragment to, Bundle bundle, FragmentAnimBean animBean, @StackModeManager.StackMode int stackMode) {
        jumpFragmentForResult(from, to, bundle, animBean, 0, stackMode);
    }

    @Override
    public void jumpFragmentForResult(final BaseFragment from, @NonNull BaseFragment to, Bundle bundle, FragmentAnimBean animBean, int requestCode, @StackModeManager.StackMode int stackMode) {
        // 先处理堆栈的需求
        boolean isHasInStack = false;
        if (stackMode == StackModeManager.SINGLE_TOP) {
            List<Fragment> fragments = context.getSupportFragmentManager().getFragments();
            List<Fragment> noneEmptyFragments = new ArrayList<>();
            for (Fragment fragment : fragments) {
                if (fragment != null) {
                    noneEmptyFragments.add(fragment);
                }
            }

            BaseFragment topFragment = (BaseFragment) noneEmptyFragments.get(noneEmptyFragments.size() - 1);
            if (topFragment.getClass().getCanonicalName().equals(to.getClass().getCanonicalName())) {
//                topFragment.onNewIntent(topFragment.getBundle());

                isHasInStack = true;
                FragmentTransaction transaction = context.getSupportFragmentManager().beginTransaction();
                transaction.show(topFragment).commit();
                View view = topFragment.getView();
//                if (view != null && topFragment.getFragmentAnimBean() != null) {
//                    view.startAnimation(AnimationUtils.loadAnimation(context, topFragment.getFragmentAnimBean().enter));
//                }
            }
        } else if (stackMode == StackModeManager.SINGLE_TASK) {
            BaseFragment singleTaskTarget = null;
            List<Fragment> fragments = context.getSupportFragmentManager().getFragments();
            for (Fragment fragment : fragments) {
                if (fragment == null) {
                    continue;
                }
                BaseFragment BaseFragment = (BaseFragment) fragment;
                if (BaseFragment.getClass().getCanonicalName().equals(to.getClass().getCanonicalName())) {
                    singleTaskTarget = BaseFragment;
//                    singleTaskTarget.onNewIntent(singleTaskTarget.getBundle());
                    continue;
                }

                if (singleTaskTarget != null) {
                    context.getSupportFragmentManager().popBackStack();
                }
            }

            isHasInStack = (singleTaskTarget != null);
            if (isHasInStack) {
                FragmentTransaction transaction = context.getSupportFragmentManager().beginTransaction();
                transaction.show(singleTaskTarget).commit();
                View view = singleTaskTarget.getView();
//                if (view != null && singleTaskTarget.getFragmentAnimBean() != null) {
//                    view.startAnimation(AnimationUtils.loadAnimation(context, singleTaskTarget.getFragmentAnimBean().enter));
//                }
            }
        }

        if (isHasInStack) {
            return;
        }

        // 新压栈
        if (bundle != null) {
            to.setArguments(bundle);
        }
        FragmentTransaction transaction = context.getSupportFragmentManager().beginTransaction();
        if (animBean != null) {
//            transaction.setCustomAnimations(animBean.enter, animBean.exit, animBean.popEnter, animBean.popExit);
        }
        if (requestCode > 0) {
//            to.setRequestCode(requestCode);
//            to.setFragmentFinishListener(new BaseFragment.OnFragmentFinishListener() {
//                @Override
//                public void callback(int requestCode, int resultCode, Intent intent) {
//                    if (from != null) {
//                        from.onFragmentResult(requestCode, resultCode, intent);
//                    }
//                }
//            });
        }
        transaction.add(Resource.getId(context, "FrameLayoutId"), to, to.getClass().getCanonicalName());
        FragmentStack.getInstance().put(context.getClass().getCanonicalName(), to);
        if (from != null) {
//            from.onCoverStop();
            transaction.hide(from);
        }
        transaction.addToBackStack(to.getClass().getCanonicalName()).commit();
    }

    @Override
    public void replace(@Nullable BaseFragment target) {
        replace(target, null);
    }

    @Override
    public void replace(@Nullable BaseFragment target, Bundle bundle) {
        if (bundle != null) {
            target.setArguments(bundle);
        }
        NavigationFragment navigationFragment = new NavigationFragment();
        navigationFragment.setRootFragment(target);

        FragmentTransaction transaction = context.getSupportFragmentManager().beginTransaction();
        transaction
                .setTransition(FragmentTransaction.TRANSIT_NONE)  // fragment缺省的动画
                .replace(Resource.getId(context, "FrameLayoutId"), navigationFragment, navigationFragment.getClass().getCanonicalName())
                .commit();
        FragmentStack.getInstance().put(context.getClass().getCanonicalName(), target);
    }

    @Override
    public void jumpActivity(Class<? extends RootActivity> target, Bundle bundle, FragmentAnimBean animBean) {
        jumpActivityForResult(target, bundle, animBean, 0);
    }

    @Override
    public void jumpActivityForResult(Class<? extends RootActivity> target, Bundle bundle, FragmentAnimBean animBean, int requestCode) {
        jumpActivityForResult(null, target, bundle, animBean, requestCode);
    }

    @Override
    public void jumpActivityForResult(BaseFragment from, Class<? extends RootActivity> target, Bundle bundle, FragmentAnimBean animBean, int requestCode) {
        Intent intent = new Intent(context, target);
        if (bundle == null) {
            bundle = new Bundle();
        }
        bundle.putParcelable(RootActivity.ANIM_KEY, animBean);
        intent.putExtras(bundle);

        if (requestCode > 0) {
            if (from == null) {
                context.startActivityForResult(intent, requestCode);
            } else {
                from.startActivityForResult(intent, requestCode);
            }
        } else {
            context.startActivity(intent);
        }

        if (animBean != null) {
//            context.overridePendingTransition(animBean.enter, animBean.exit);
        }
    }

    @Override
    public void jumpActivityFragment(Class<? extends RootActivity> target, Class<? extends BaseFragment> fragment, Bundle bundle, FragmentAnimBean animBean) {
        jumpActivityFragmentForResult(target, fragment, bundle, animBean, 0);
    }

    @Override
    public void jumpActivityFragmentForResult(Class<? extends RootActivity> target, Class<? extends BaseFragment> fragment, Bundle bundle, FragmentAnimBean animBean, int requestCode) {
        jumpActivityFragmentForResult(null, target, fragment, bundle, animBean, requestCode);
    }

    @Override
    public void jumpActivityFragmentForResult(BaseFragment from, Class<? extends RootActivity> target, Class<? extends BaseFragment> fragment, Bundle bundle, FragmentAnimBean animBean, int requestCode) {
        Intent intent = new Intent(context, target);
        if (bundle == null) {
            bundle = new Bundle();
        }
        bundle.putParcelable(RootActivity.ANIM_KEY, animBean);
        bundle.putString(RootActivity.NEW_FRAGMENT_KEY, fragment.getCanonicalName());
        intent.putExtras(bundle);

        if (requestCode > 0) {
            if (from == null) {
                context.startActivityForResult(intent, requestCode);
            } else {
                from.startActivityForResult(intent, requestCode);
            }
        } else {
            context.startActivity(intent);
        }

        if (animBean != null) {
//            context.overridePendingTransition(animBean.enter, animBean.exit);
        }
    }
}
