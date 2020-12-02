package com.yyxnb.module_widget.ui;

import android.Manifest;
import android.arch.paging.PagedListAdapter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;

import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.yyxnb.lib_adapter.BaseViewHolder;
import com.yyxnb.lib_adapter.SimpleOnItemClickListener;
import com.yyxnb.lib_arch.annotations.BindRes;
import com.yyxnb.common_res.core.AbsListFragment;
import com.yyxnb.module_widget.adapter.MainListAdapter;
import com.yyxnb.module_widget.bean.MainBean;
import com.yyxnb.module_widget.viewmodel.MainViewModel;
import com.yyxnb.utils.permission.PermissionListener;
import com.yyxnb.utils.permission.PermissionUtils;

/**
 * 主页
 */
@BindRes
public class WidgetMainFragment extends AbsListFragment<MainBean, MainViewModel> {

    private MainListAdapter mAdapter = new MainListAdapter();

//    @Override
//    public int initLayoutResId() {
//        return R.layout.fragment_widget_main;
//    }

    @Override
    public void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        mRefreshLayout.setEnableRefresh(false).setEnableLoadMore(false).setEnableOverScrollDrag(true);
        mAdapter.setOnItemClickListener(new SimpleOnItemClickListener() {
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

        PermissionUtils.with(getActivity())
                .addPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .setPermissionsCheckListener(new PermissionListener() {
                    @Override
                    public void permissionRequestSuccess() {
                    }

                    @Override
                    public void permissionRequestFail(String[] grantedPermissions, String[] deniedPermissions, String[] forceDeniedPermissions) {
                    }
                })
                .createConfig()
                .setForceAllPermissionsGranted(true)
                .buildConfig()
                .startCheckPermission();
    }

    @Override
    public void initViewData() {

    }

    private void setMenu(int position) {
        switch (position) {
//            case 11:
//                startFragment(new ToastFragment());
//                break;
//            case 12:
//                startFragment(new SkinMainFragment());
//                break;
//            case 41:
//                startFragment(new TitleFragment());
//                break;
            case 42:
                startFragment(new DialogFragment());
                break;
//            case 43:
//                startFragment(new TagFragment());
//                break;
            case 44:
                startFragment(new PopupFragment());
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