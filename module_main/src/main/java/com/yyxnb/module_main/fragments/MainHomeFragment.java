package com.yyxnb.module_main.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.util.Log;

import com.alibaba.android.arouter.launcher.ARouter;
import com.yyxnb.adapter.ItemDecoration;
import com.yyxnb.arch.annotations.BindViewModel;
import com.yyxnb.arch.base.BaseFragment;
import com.yyxnb.common.log.LogUtils;
import com.yyxnb.module_main.R;
import com.yyxnb.module_main.adapter.MainHomeAdapter;
import com.yyxnb.module_main.bean.MainHomeBean;
import com.yyxnb.module_main.config.DataConfig;
import com.yyxnb.module_main.databinding.FragmentMainHomeBinding;
import com.yyxnb.module_main.viewmodel.MainViewModel;

import static com.yyxnb.module_base.arouter.ARouterConstant.VIDEO_VIDEO;

/**
 * 主页
 */
public class MainHomeFragment extends BaseFragment {

    @BindViewModel
    MainViewModel mViewModel;
    private MainHomeAdapter mAdapter = new MainHomeAdapter();
    private FragmentMainHomeBinding binding;

    @Override
    public int initLayoutResId() {
        return R.layout.fragment_main_home;
    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
        binding = getBinding();
//        mAdapter = new MainHomeAdapter();

        GridLayoutManager manager = new GridLayoutManager(getContext(), 3);
        mAdapter.setSpanSizeLookup((gridLayoutManager, position) -> {
            //noinspection ConstantConditions
            if (mAdapter.getData().get(position).span == 1){
                return 1;
            }else if (mAdapter.getData().get(position).span == 2){
                return (int) 1.5;
            }else {
                return 3;
            }
        });

        binding.mRecyclerView.setLayoutManager(manager);
        binding.mRecyclerView.setHasFixedSize(true);
        ItemDecoration decoration = new ItemDecoration(getContext());
        decoration.setDividerWidth(5);
        decoration.setDividerHeight(5);
//        decoration.setDrawBorderTopAndBottom(true);
//        decoration.setDrawBorderLeftAndRight(true);
        binding.mRecyclerView.addItemDecoration(decoration);
        binding.mRecyclerView.setAdapter(mAdapter);


        binding.tvTitle.setOnClickListener(v -> {
//            startFragment(new MainClassificationFragment());
//            startFragment((BaseFragment) ARouter.getInstance().build("/login/LoginFragment").navigation());
            ARouter.getInstance().build(VIDEO_VIDEO).navigation();
        });

    }

    @Override
    public void initViewData() {
        super.initViewData();

//        mViewModel.getPageData().observe(this,mainHomeBeans -> {
//            if (mainHomeBeans != null){
//                mAdapter.setDataItems(mainHomeBeans);
//            }
//
//        });

        LogUtils.e( " DataConfig.getMainBeans() " + DataConfig.getMainBeans().size() );
        for (MainHomeBean bean : DataConfig.getMainBeans()){
            Log.w("213 ","b " + bean.id);
        }
        mAdapter.setDataItems(DataConfig.getMainBeans());
//        mAdapter.addDataItem(DataConfig.getMainBeans().get(0));
    }

    @Override
    public void onVisible() {
        super.onVisible();
        getBaseDelegate().setNeedsStatusBarAppearanceUpdate();
        LogUtils.d("---onVisible---");
    }

}
