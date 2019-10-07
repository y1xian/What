package com.yyxnb.arch.base.nav;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.yyxnb.arch.base.RootActivity;
import com.yyxnb.arch.base.BaseFragment;

/**
 * 操作的接口定义
 */
public interface OpsInterface {
    /**
     * 跳转到新的fragment页面
     *
     * @param from      从哪里来，需要将原始的fragment隐藏则传入这个值
     * @param to        去到哪里
     * @param bundle    传递的数据
     * @param animBean  动画
     * @param stackMode 压栈类型
     */
    public void jumpFragment(BaseFragment from, @NonNull BaseFragment to, Bundle bundle, FragmentAnimBean animBean, @StackModeManager.StackMode int stackMode);

    /**
     * 需要返回结果的跳转到新的fragment页面
     *
     * @param from        从哪里来，需要将原始的fragment隐藏则传入这个值
     * @param to          去到哪里
     * @param bundle      传递的数据
     * @param animBean    动画
     * @param requestCode 请求码，大于0的，否则不处理
     * @param stackMode   压栈类型
     */
    public void jumpFragmentForResult(BaseFragment from, @NonNull BaseFragment to, Bundle bundle, FragmentAnimBean animBean, int requestCode, @StackModeManager.StackMode int stackMode);

    /**
     * fragment重新替换
     *
     * @param target
     */
    public void replace(@NonNull BaseFragment target);

    /**
     * fragment重新替换，带bundle数据的
     *
     * @param target
     * @param bundle
     */
    public void replace(@NonNull BaseFragment target, Bundle bundle);

    /**
     * fragment跳转到新的activity
     *
     * @param target
     * @param bundle
     * @param animBean
     */
    public void jumpActivity(Class<? extends RootActivity> target, Bundle bundle, FragmentAnimBean animBean);

    /**
     * fragment跳转到新的activity
     *
     * @param target
     * @param bundle
     * @param animBean
     * @param requestCode
     */
    public void jumpActivityForResult(Class<? extends RootActivity> target, Bundle bundle, FragmentAnimBean animBean, int requestCode);

    /**
     * fragment跳转到新的activity
     *
     * @param from        增加跳转的来源，跟上面的{@link OpsInterface#jumpActivityForResult(Class, Bundle, FragmentAnimBean, int)}类似
     *                    只是增加来源，缺省的不加来源，默认它是来自activity，那么result就是给到activity的；如果需要将result给到实际发起的fragemnt，则加上来源
     * @param target
     * @param bundle
     * @param animBean
     * @param requestCode
     */
    public void jumpActivityForResult(BaseFragment from, Class<? extends RootActivity> target, Bundle bundle, FragmentAnimBean animBean, int requestCode);

    /**
     * 跳转到其他activity上指定的fragemnt
     *
     * @param target
     * @param fragment fragment的class类
     * @param bundle
     * @param animBean
     */
    public void jumpActivityFragment(Class<? extends RootActivity> target, Class<? extends BaseFragment> fragment, Bundle bundle, FragmentAnimBean animBean);

    /**
     * 跳转到其他activity上指定的fragment
     *
     * @param target
     * @param fragment    fragment的class类
     * @param bundle
     * @param animBean
     * @param requestCode
     */
    public void jumpActivityFragmentForResult(Class<? extends RootActivity> target, Class<? extends BaseFragment> fragment, Bundle bundle, FragmentAnimBean animBean, int requestCode);

    /**
     * 跳转到其他activity上指定的fragment
     *
     * @param from        增加跳转的来源，跟上面的{@link OpsInterface#jumpActivityForResult(Class, Bundle, FragmentAnimBean, int)}类似
     *                    只是增加来源，缺省的不加来源，默认它是来自activity，那么result就是给到activity的；如果需要将result给到实际发起的fragment，则加上来源
     * @param target
     * @param fragment
     * @param bundle
     * @param animBean
     * @param requestCode
     */
    public void jumpActivityFragmentForResult(BaseFragment from, Class<? extends RootActivity> target, Class<? extends BaseFragment> fragment, Bundle bundle, FragmentAnimBean animBean, int requestCode);
}
