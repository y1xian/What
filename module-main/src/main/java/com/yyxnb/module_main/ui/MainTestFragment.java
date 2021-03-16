package com.yyxnb.module_main.ui;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;

import com.yyxnb.common_base.core.BaseFragment;
import com.yyxnb.common_res.arouter.ARouterUtils;
import com.yyxnb.common_res.arouter.service.impl.UserImpl;
import com.yyxnb.common_res.constants.ChatRouterPath;
import com.yyxnb.common_res.constants.JokeRouterPath;
import com.yyxnb.common_res.constants.MusicRouterPath;
import com.yyxnb.common_res.constants.NovelRouterPath;
import com.yyxnb.common_res.constants.VideoRouterPath;
import com.yyxnb.common_res.constants.WanRouterPath;
import com.yyxnb.common_res.constants.WidgetRouterPath;
import com.yyxnb.lib_adapter.BaseViewHolder;
import com.yyxnb.lib_adapter.ItemDecoration;
import com.yyxnb.lib_adapter.SimpleOnItemClickListener;
import com.yyxnb.lib_arch.annotations.BindRes;
import com.yyxnb.lib_arch.annotations.BindViewModel;
import com.yyxnb.lib_popup.PopupManager;
import com.yyxnb.module_main.R;
import com.yyxnb.module_main.adapter.MainHomeAdapter;
import com.yyxnb.module_main.config.DataConfig;
import com.yyxnb.module_main.databinding.FragmentMainTestBinding;
import com.yyxnb.module_main.viewmodel.MainTestViewModel;

/**
 * ================================================
 * 作    者：yyx
 * 版    本：1.0
 * 日    期：2020/11/23
 * 历    史：
 * 描    述：方便页面测试
 * ================================================
 */
@BindRes(subPage = true)
public class MainTestFragment extends BaseFragment {

    @BindViewModel
    MainTestViewModel mViewModel;
    private MainHomeAdapter mAdapter;
    private FragmentMainTestBinding binding;

    @Override
    public int initLayoutResId() {
        return R.layout.fragment_main_test;
    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
        binding = getBinding();

        mAdapter = new MainHomeAdapter();
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

        binding.rvContent.setLayoutManager(manager);
        binding.rvContent.setHasFixedSize(true);
        ItemDecoration decoration = new ItemDecoration(getContext());
        decoration.setDividerWidth(12);
        decoration.setDividerHeight(12);
        decoration.setOnlySetItemOffsetsButNoDraw(true);
        decoration.setDrawBorderTopAndBottom(true);
        decoration.setDrawBorderLeftAndRight(true);
        binding.rvContent.addItemDecoration(decoration);
        binding.rvContent.setAdapter(mAdapter);


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
            startFragment(UserImpl.getInstance().mainPage(getContext()));
        });

        binding.tvTitle.setOnClickListener(v -> {
            new PopupManager.Builder(getContext())
                    .asConfirm("警告", "有内鬼，终止学术交流", () -> {
                        // 确定
                    }, () -> {

                    }).show();
        });

        mAdapter.setOnItemClickListener(new SimpleOnItemClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onItemClick(View view, BaseViewHolder holder, int position) {
                super.onItemClick(view, holder, position);
//                AppConfig.getInstance().toast("item  第 " + position);
                switch (mAdapter.getData().get(position).id) {
                    case 1:
                        // 搜索
                        ConstraintLayout mLayout = holder.getView(R.id.clContent);
                        Intent intent = new Intent(getContext(), MainSearchActivity.class);
                        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(getActivity(), mLayout, "transitionSearch").toBundle());
                        break;
                    case 2:
                        // 短视频
//                        startFragment(ARouterUtils.navFragment(VIDEO_MAIN_FRAGMENT));
                        ARouterUtils.navActivity(VideoRouterPath.MAIN_ACTIVITY);
                        break;
                    case 3:
                        // 娱乐
//                        startFragment(ARouterUtils.navFragment(JOKE_MAIN_FRAGMENT));
                        ARouterUtils.navActivity(JokeRouterPath.MAIN_ACTIVITY);
                        break;
                    case 4:
                        // 消息
//                        startFragment(ARouterUtils.navFragment(CHAT_MAIN_FRAGMENT));
                        ARouterUtils.navActivity(ChatRouterPath.MAIN_ACTIVITY);
                        break;
                    case 5:
                        // 玩安卓
//                        startFragment(ARouterUtils.navFragment(WAN_MAIN_FRAGMENT));
                        ARouterUtils.navActivity(WanRouterPath.MAIN_ACTIVITY);
                        break;
                    case 6:
                        // 音乐
//                        startFragment(ARouterUtils.navFragment(MUSIC_HOME_FRAGMENT));
                        ARouterUtils.navActivity(MusicRouterPath.MAIN_ACTIVITY);
                        break;
                    case 7:
                        // 小说
//                        startFragment(ARouterUtils.navFragment(NOVEL_MAIN_FRAGMENT));
                        ARouterUtils.navActivity(NovelRouterPath.MAIN_ACTIVITY);
                        break;
                    case 8:
                        ARouterUtils.navActivity(WidgetRouterPath.MAIN_ACTIVITY);
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
        log("---onVisible---    MainTestFragment");
    }

}
