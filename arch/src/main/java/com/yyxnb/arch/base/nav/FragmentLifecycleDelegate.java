package com.yyxnb.arch.base.nav;

/**
 * fragment存在可见性事件监听不到的问题。
 * <p>
 * 具体表现为同一个activity中fragment f1跳转到fragment f2时，f1无法触发到onPause/onStop，并且从f2回来时，f1也无法触发onStart/onResume。
 * <p>
 * 其他情况都是正常，如：<p>
 * > 跳转到另外的activity上 <p>
 * > fragment popup可以正常触发onPause/onStop <p>
 * > 当前fragment页面按Home建
 * <p>
 * 所以需要增加add fragment时的事件回调
 * <p>
 */
public interface FragmentLifecycleDelegate {
    /**
     * 被覆盖时的回调
     */
    void onCoverStop();

    /**
     * 再现时的回调
     */
    void onReShowResume();
}
