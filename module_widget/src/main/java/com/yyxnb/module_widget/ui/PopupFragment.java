package com.yyxnb.module_widget.ui;

import android.arch.paging.PagedListAdapter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;

import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.yyxnb.adapter.BaseViewHolder;
import com.yyxnb.adapter.MultiItemTypePagedAdapter;
import com.yyxnb.common.AppConfig;
import com.yyxnb.common_base.base.AbsListFragment;
import com.yyxnb.module_widget.adapter.MainListAdapter;
import com.yyxnb.module_widget.bean.MainBean;
import com.yyxnb.module_widget.viewmodel.PopupViewModel;
import com.yyxnb.popup.PopupManager;
import com.yyxnb.popup.impl.LoadingPopupView;
import com.yyxnb.popup.interfaces.OnInputConfirmListener;
import com.yyxnb.popup.interfaces.OnSelectListener;

/**
 * Popup.
 */
public class PopupFragment extends AbsListFragment<MainBean, PopupViewModel> {

    private MainListAdapter mAdapter = new MainListAdapter();

    @Override
    public void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        mRefreshLayout.setEnableRefresh(false).setEnableLoadMore(false).setEnableOverScrollDrag(true);
        mAdapter.setOnItemClickListener(new MultiItemTypePagedAdapter.SimpleOnItemClickListener() {
            @Override
            public void onItemClick(View view, BaseViewHolder holder, int position) {
                super.onItemClick(view, holder, position);
                setMenu(mAdapter.getData().get(position).id);
            }
        });

        GridLayoutManager manager = new GridLayoutManager(getContext(), 2);
        mAdapter.setSpanSizeLookup((gridLayoutManager, position) -> {
            if (mAdapter.getData().get(position).type == 1) {
                return 2;
            }
            return 1;
        });
        mRecyclerView.setLayoutManager(manager);
        decoration.setDividerWidth(5);
        decoration.setDividerHeight(5);
        decoration.setDrawBorderTopAndBottom(true);
        decoration.setDrawBorderLeftAndRight(true);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void setMenu(int position) {
        switch (position) {
            case 11:
                //带确认和取消按钮的弹窗
                new PopupManager.Builder(getContext())
                        .asConfirm("标题", "内容", () -> {

                        }).show();
                break;
            case 12:
                new PopupManager.Builder(getContext())
                        .asConfirm("标题", "内容", () -> {

                        }).show();
                break;
            case 13:
                //带确认和取消按钮，输入框的弹窗
                new PopupManager.Builder(getContext())
                        //.dismissOnBackPressed(false)
                        .isDestroyOnDismiss(true) //对于只使用一次的弹窗，推荐设置这个
                        .autoOpenSoftInput(true)
//                        .autoFocusEditText(false) //是否让弹窗内的EditText自动获取焦点，默认是true
                        .isRequestFocus(false)
                        //.moveUpToKeyboard(false)   //是否移动到软键盘上面，默认为true
                        .asInputConfirm("标题", null, null, "内容",
                                new OnInputConfirmListener() {
                                    @Override
                                    public void onConfirm(String text) {
                                        AppConfig.getInstance().toast("input text: " + text);
//                                new XPopup.Builder(getContext()).asLoading().show();
                                    }
                                })
                        .show();
                break;
            case 14:
                //在中间弹出的List列表弹窗
                new PopupManager.Builder(getContext())
//                        .maxWidth(600)
//                        .isDarkTheme(true)
                        .isDestroyOnDismiss(true) //对于只使用一次的弹窗，推荐设置这个
                        .asCenterList("请选择一项", new String[]{"条目1", "条目2", "条目3", "条目4"},
                                new OnSelectListener() {
                                    @Override
                                    public void onSelect(int position, String text) {
                                        AppConfig.getInstance().toast("click " + text);
                                    }
                                })
//                        .bindLayout(R.layout.my_custom_attach_popup) //自定义布局
                        .show();
                break;
            case 15:
                new PopupManager.Builder(getContext())
//                        .maxWidth(600)
//                        .isDarkTheme(true)
                        .isDestroyOnDismiss(true) //对于只使用一次的弹窗，推荐设置这个
                        .asCenterList("请选择一项", new String[]{"条目1", "条目2", "条目3", "条目4"},
                                new OnSelectListener() {
                                    @Override
                                    public void onSelect(int position, String text) {
                                        AppConfig.getInstance().toast("click " + text);
                                    }
                                })
//                        .bindLayout(R.layout.my_custom_attach_popup) //自定义布局
                        .show();
                break;
            case 16:

                break;
            case 17:
                //在中间弹出的List列表弹窗，带选中效果
                new PopupManager.Builder(getContext())
                        .isDestroyOnDismiss(true) //对于只使用一次的弹窗，推荐设置这个
                        .asCenterList("请选择一项", new String[]{"条目1", "条目2", "条目3", "条目4"},
                                null, 1,
                                new OnSelectListener() {
                                    @Override
                                    public void onSelect(int position, String text) {
                                        AppConfig.getInstance().toast("click " + text);
                                    }
                                })
//                        .bindLayout(R.layout.my_custom_attach_popup) //自定义布局
                        .show();
                break;
            case 18:

                break;
            case 19:
                //在中间弹出的Loading加载框
                final LoadingPopupView loadingPopup = (LoadingPopupView) new PopupManager.Builder(getContext())
                        .asLoading("正在加载中")
                        .show();
                loadingPopup.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        loadingPopup.setTitle("正在加载长度变化了");
                    }
                }, 1000);
//                loadingPopup.smartDismiss();
//                loadingPopup.dismiss();
                loadingPopup.delayDismissWith(3000, new Runnable() {
                    @Override
                    public void run() {
                        AppConfig.getInstance().toast("我消失了！！！");
                    }
                });
                break;
            case 20:

                break;

            case 41:
                //从底部弹出，带手势拖拽的列表弹窗
                new PopupManager.Builder(getContext())
//                        .isDarkTheme(true)
                        .hasShadowBg(true)
                        .isDestroyOnDismiss(true) //对于只使用一次的弹窗，推荐设置这个
//                        .enableDrag(false)
                        .asBottomList("请选择一项", new String[]{"条目1", "条目2", "条目3", "条目4", "条目5"},
                                new OnSelectListener() {
                                    @Override
                                    public void onSelect(int position, String text) {
                                        AppConfig.getInstance().toast("click " + text);
                                    }
                                })
                        .show();
                break;
            case 42:
                break;
            case 43:
                //从底部弹出，带手势拖拽的列表弹窗,带选中效果
                new PopupManager.Builder(getContext())
                        .isDestroyOnDismiss(true) //对于只使用一次的弹窗，推荐设置这个
                        .asBottomList("", new String[]{"条目1", "条目2", "条目3", "条目4", "条目5"},
                                null, 2,
                                new OnSelectListener() {
                                    @Override
                                    public void onSelect(int position, String text) {
                                        AppConfig.getInstance().toast("click " + text);
                                    }
                                })
                        .show();
                break;
            default:
                break;
        }
    }

    @Override
    public PagedListAdapter getAdapter() {
        return mAdapter;
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {

    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {

    }
}