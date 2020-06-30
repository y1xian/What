package com.yyxnb.module_main.ui;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;

import com.yyxnb.adapter.BaseViewHolder;
import com.yyxnb.adapter.ItemDecoration;
import com.yyxnb.adapter.MultiItemTypeAdapter;
import com.yyxnb.arch.annotations.BindViewModel;
import com.yyxnb.common.log.LogUtils;
import com.yyxnb.common_base.arouter.ARouterUtils;
import com.yyxnb.common_base.base.BaseFragment;
import com.yyxnb.module_main.R;
import com.yyxnb.module_main.adapter.MainHomeAdapter;
import com.yyxnb.module_main.config.DataConfig;
import com.yyxnb.module_main.databinding.FragmentMainHomeBinding;
import com.yyxnb.module_main.viewmodel.MainViewModel;
import com.yyxnb.view.popup.Popup;

import static com.yyxnb.common_base.arouter.ARouterConstant.JOKE_MAIN_FRAGMENT;
import static com.yyxnb.common_base.arouter.ARouterConstant.USER_FRAGMENT;
import static com.yyxnb.common_base.arouter.ARouterConstant.VIDEO_VIDEO;

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

        GridLayoutManager manager = new GridLayoutManager(getContext(), 4);
        mAdapter.setSpanSizeLookup((gridLayoutManager, position) -> {
            //noinspection ConstantConditions
//            if (mAdapter.getData().get(position).span == 1){
//                return 1;
//            }else if (mAdapter.getData().get(position).span == 2){
//                return (int) 1.5;
//            }else {
//                return 3;
//            }
            return mAdapter.getData().get(position).span;
        });

        binding.mRecyclerView.setLayoutManager(manager);
        binding.mRecyclerView.setHasFixedSize(true);
        ItemDecoration decoration = new ItemDecoration(getContext());
        decoration.setDividerWidth(12);
        decoration.setDividerHeight(12);
        decoration.setOnlySetItemOffsetsButNoDraw(true);
        decoration.setDrawBorderTopAndBottom(true);
        decoration.setDrawBorderLeftAndRight(true);
        binding.mRecyclerView.addItemDecoration(decoration);
        binding.mRecyclerView.setAdapter(mAdapter);


//        SkinTheme theme = new SkinTheme.Builder(getActivity())
//                .backgroundColor(R.id.mLayout,R.attr.skin_colorPrimary)
//                .build();
//
//        theme.setTheme(SkinTheme.getCurrentThemeId());

//        Bus.observe(this, msgEvent -> {
//            if (msgEvent.getCode() == KEY_SKIN_SWITCH){
//                theme.setTheme((Integer) msgEvent.getData());
//            }
//        });
    }

    @Override
    public void initViewData() {
        super.initViewData();
        mAdapter.setDataItems(DataConfig.getMainBeans());

        binding.ivHead.setOnClickListener(v -> {
            startFragment(ARouterUtils.navFragment(USER_FRAGMENT));
        });

        binding.tvTitle.setOnClickListener(v -> {
            new Popup.Builder(getContext())
                    .asConfirm("警告", "有内鬼，终止学术交流", () -> {
                        // 确定
                    }, () -> {

                    }).show();
        });

        mAdapter.setOnItemClickListener(new MultiItemTypeAdapter.SimpleOnItemClickListener() {
            @Override
            public void onItemClick(View view, BaseViewHolder holder, int position) {
                super.onItemClick(view, holder, position);
//                AppConfig.getInstance().toast("item  第 " + position);
                switch (mAdapter.getData().get(position).id) {
                    case 1:
                        ConstraintLayout mLayout = holder.getView(R.id.mLayout);
                        Intent intent = new Intent(getContext(), MainSearchActivity.class);
                        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(getActivity(), mLayout, "transitionSearch").toBundle());
                        break;
                    case 2:
//                        startFragment(ARouterUtils.navFragment(VIDEO_MAIN_FRAGMENT));
                        ARouterUtils.navActivity(VIDEO_VIDEO);
                        break;
                    case 3:
                        startFragment(ARouterUtils.navFragment(JOKE_MAIN_FRAGMENT));
                        break;
                    default:
                        break;
                }

            }
        });
    }

    @Override
    public void initObservable() {

    }

    @Override
    public void onVisible() {
        super.onVisible();
        getBaseDelegate().setNeedsStatusBarAppearanceUpdate();
        LogUtils.d("---onVisible---");
    }

}
