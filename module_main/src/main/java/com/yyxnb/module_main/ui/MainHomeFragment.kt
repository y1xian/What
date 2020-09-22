package com.yyxnb.module_main.ui

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.GridLayoutManager
import com.yyxnb.adapter.BaseViewHolder
import com.yyxnb.adapter.ItemDecoration
import com.yyxnb.adapter.MultiItemTypeAdapter
import com.yyxnb.adapter.SimpleOnItemClickListener
import com.yyxnb.arch.annotations.BindViewModel
import com.yyxnb.common.utils.log.LogUtils.d
import com.yyxnb.common_base.arouter.ARouterConstant.JOKE_MAIN
import com.yyxnb.common_base.arouter.ARouterConstant.JOKE_MAIN_FRAGMENT
import com.yyxnb.common_base.arouter.ARouterConstant.MESSAGE_LIST_FRAGMENT
import com.yyxnb.common_base.arouter.ARouterConstant.MESSAGE_MAIN
import com.yyxnb.common_base.arouter.ARouterConstant.MUSIC_HOME_FRAGMENT
import com.yyxnb.common_base.arouter.ARouterConstant.MUSIC_MAIN
import com.yyxnb.common_base.arouter.ARouterConstant.NOVEL_MAIN
import com.yyxnb.common_base.arouter.ARouterConstant.USER_FRAGMENT
import com.yyxnb.common_base.arouter.ARouterConstant.VIDEO_VIDEO
import com.yyxnb.common_base.arouter.ARouterConstant.WAN_MAIN
import com.yyxnb.common_base.arouter.ARouterConstant.WAN_MAIN_FRAGMENT
import com.yyxnb.common_base.arouter.ARouterConstant.WIDGET_MAIN
import com.yyxnb.common_base.arouter.ARouterUtils.navActivity
import com.yyxnb.common_base.arouter.ARouterUtils.navFragment
import com.yyxnb.common_base.base.BaseFragment
import com.yyxnb.module_main.R
import com.yyxnb.module_main.adapter.MainHomeAdapter
import com.yyxnb.module_main.config.DataConfig.mainBeans
import com.yyxnb.module_main.databinding.FragmentMainHomeBinding
import com.yyxnb.module_main.viewmodel.MainViewModel
import com.yyxnb.popup.PopupManager

/**
 * 主页
 */
class MainHomeFragment : BaseFragment() {

    @BindViewModel
    lateinit var mViewModel: MainViewModel

    private val mAdapter = MainHomeAdapter()
    private var binding: FragmentMainHomeBinding? = null

    override fun initLayoutResId(): Int {
        return R.layout.fragment_main_home
    }

    override fun initView(savedInstanceState: Bundle?) {
        binding = getBinding()
        val manager = GridLayoutManager(context, 4)
        mAdapter.setSpanSizeLookup { gridLayoutManager: GridLayoutManager?, position: Int -> mAdapter.data[position].span }
        binding!!.mRecyclerView.layoutManager = manager
        binding!!.mRecyclerView.setHasFixedSize(true)
        val decoration = ItemDecoration(context)
        decoration.setDividerWidth(12)
        decoration.setDividerHeight(12)
        decoration.isOnlySetItemOffsetsButNoDraw = true
        decoration.isDrawBorderTopAndBottom = true
        decoration.isDrawBorderLeftAndRight = true
        binding!!.mRecyclerView.addItemDecoration(decoration)
        binding!!.mRecyclerView.adapter = mAdapter


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

    override fun initViewData() {
        super.initViewData()
        mAdapter.setDataItems(mainBeans)
        binding!!.ivHead.setOnClickListener { v: View? -> startFragment(navFragment(USER_FRAGMENT)) }
        binding!!.tvTitle.setOnClickListener { v: View? ->
            PopupManager.Builder(context)
                    .asConfirm("警告", "有内鬼，终止学术交流", {}) {}.show()
        }
        mAdapter.setOnItemClickListener(object : SimpleOnItemClickListener() {
            override fun onItemClick(view: View, holder: BaseViewHolder, position: Int) {
                super.onItemClick(view, holder, position)
                when (mAdapter.data[position].id) {
                    1 -> {
                        // 搜索
                        val mLayout: ConstraintLayout = holder.getView(R.id.mLayout)
                        val intent = Intent(context, MainSearchActivity::class.java)
                        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(activity, mLayout, "transitionSearch").toBundle())
                    }
                    2 ->                         // 短视频
//                        startFragment(ARouterUtils.navFragment(VIDEO_MAIN_FRAGMENT));
                        navActivity(VIDEO_VIDEO)
                    3 ->                         // 娱乐
//                        startFragment(navFragment(JOKE_MAIN_FRAGMENT))
                        navActivity(JOKE_MAIN)
                    4 ->                         // 消息
//                        startFragment(navFragment(MESSAGE_LIST_FRAGMENT))
                        navActivity(MESSAGE_MAIN)
                    5 ->                         // 玩安卓
//                        startFragment(navFragment(WAN_MAIN_FRAGMENT))
                        navActivity(WAN_MAIN)
                    6 ->                         // 音乐
//                        startFragment(navFragment(MUSIC_HOME_FRAGMENT))
                        navActivity(MUSIC_MAIN)
                    7 ->                         // 小说
                        navActivity(NOVEL_MAIN)
                    8 ->
                        navActivity(WIDGET_MAIN)
                    else -> {
                    }
                }
            }
        })
    }

    override fun initObservable() {}

    override fun onVisible() {
        super.onVisible()
        getBaseDelegate().setNeedsStatusBarAppearanceUpdate()
        d("---onVisible---")
    }
}