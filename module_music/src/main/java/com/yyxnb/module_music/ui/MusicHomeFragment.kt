package com.yyxnb.module_music.ui

import android.Manifest
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.alibaba.android.arouter.facade.annotation.Route
import com.yyxnb.adapter.BaseFragmentPagerAdapter
import com.yyxnb.arch.annotations.BindViewModel
import com.yyxnb.common.utils.DpUtils.dp2px
import com.yyxnb.common.utils.log.LogUtils.e
import com.yyxnb.common.utils.log.LogUtils.w
import com.yyxnb.common_base.arouter.ARouterConstant.MUSIC_HOME_FRAGMENT
import com.yyxnb.common_base.base.BaseFragment
import com.yyxnb.common_base.weight.ScaleTransitionPagerTitleView
import com.yyxnb.lib_music.MusicPlayerManager
import com.yyxnb.lib_music.interfaces.MusicPlayerEventListener
import com.yyxnb.lib_music.interfaces.MusicPlayerInfoListener
import com.yyxnb.module_music.R
import com.yyxnb.module_music.bean.MusicBean
import com.yyxnb.module_music.bean.MusicRecordBean
import com.yyxnb.module_music.databinding.FragmentMusicHomeBinding
import com.yyxnb.module_music.db.MusicDatabase.Companion.instance
import com.yyxnb.module_music.view.BottomMusicView
import com.yyxnb.module_music.viewmodel.MusicViewModel
import com.yyxnb.utils.permission.PermissionListener
import com.yyxnb.utils.permission.PermissionUtils
import net.lucode.hackware.magicindicator.MagicIndicator
import net.lucode.hackware.magicindicator.ViewPagerHelper
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator
import java.util.*

/**
 * 音乐首页.
 */
@Route(path = MUSIC_HOME_FRAGMENT)
class MusicHomeFragment : BaseFragment(), MusicPlayerEventListener<MusicBean> {
    private var binding: FragmentMusicHomeBinding? = null
    private var mIndicator: MagicIndicator? = null
    private var mViewPager: ViewPager? = null
    private var mBottomMusicView: BottomMusicView? = null

    @BindViewModel
    lateinit var mViewModel: MusicViewModel

    private val mLists = ArrayList<MusicBean>()
    private val titles = arrayOf("本地音乐", "网络音乐")
    private var fragments: MutableList<Fragment>? = null
    private val isFirstPlay = true
    override fun initLayoutResId(): Int {
        return R.layout.fragment_music_home
    }

    override fun initView(savedInstanceState: Bundle?) {
        binding = getBinding()
        mIndicator = binding!!.mIndicator
        mViewPager = binding!!.mViewPager
        mBottomMusicView = binding!!.mBottomMusicView
    }

    override fun initViewData() {
        if (fragments == null) {
            fragments = ArrayList()
            fragments?.add(MusicLocalListFragment())
            fragments?.add(MusicNetworkFragment())
        }
        initIndicator()
        PermissionUtils.with(activity)
                .addPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .addPermissions(Manifest.permission.WAKE_LOCK)
                .addPermissions(Manifest.permission.VIBRATE)
                .setPermissionsCheckListener(object : PermissionListener {
                    override fun permissionRequestSuccess() {
//                        initData();
//                        mViewModel.reqMusicData();
                    }

                    override fun permissionRequestFail(grantedPermissions: Array<String>, deniedPermissions: Array<String>, forceDeniedPermissions: Array<String>) {}
                })
                .createConfig()
                .setForceAllPermissionsGranted(true)
                .buildConfig()
                .startCheckPermission()
    }

    override fun initObservable() {
//        if (!LoginImpl.getInstance().isLogin()) {
//            startFragment(ARouterUtils.navFragment(LOGIN_FRAGMENT));
//        }

//        MusicService.startMusicService();

//        mViewModel.reqMusicData();
        MusicPlayerManager.getInstance().initialize(activity) {
            w("初始化")
            mBottomMusicView!!.showFirstView()
        }


//        mViewModel.getMusicData().observe(this, data -> {
//            if (data != null) {
////                AudioHelper.controller().setQueue(data);
////                MusicPlayerManager.getInstance().setPlayingChannel(MusicConstants.CHANNEL_HISTROY);
//                MusicPlayerManager.getInstance().setMusic(data, 0);
////                MusicPlayerManager.getInstance().playOrPause();
//                mBottomMusicView.showFirstView();
//                LogUtils.list(data);
//                MusicPlayerManager.getInstance().addOnPlayerEventListener(this);
//            }
//        });
        MusicPlayerManager.getInstance().setPlayInfoListener(MusicPlayerInfoListener { musicInfo: MusicBean, position: Int ->
            e("$position , onPlayMusicOnInfo：$musicInfo")
            val recordBean = MusicRecordBean()
            recordBean.musicBean = musicInfo
            //            recordBean.currenTime = 0;
            recordBean.updateTime = System.currentTimeMillis()
            instance!!.recordDao().insertItem(recordBean)
            MusicPlayerManager.getInstance().addOnPlayerEventListener(this)
        } as MusicPlayerInfoListener<MusicBean>)


//        mViewModel.getRecordData().observe(this, data -> {
//            if (data != null && isFirstPlay) {
//                mBottomMusicView.showFirstView();
//                MusicPlayerManager.getInstance().addOnPlayerEventListener(this);
//                isFirstPlay = false;
//            }
//        });

//        AudioHelper.controller().setQueue(mLists);


//        LogUtils.w("当前 ：" + MusicDatabase.getInstance().recordDao().getLastRecord());
//         // 全部记录
//        LogUtils.list(MusicDatabase.getInstance().recordDao().getRecordAll());
    }

    override fun onDestroy() {
        super.onDestroy()
        MusicPlayerManager.getInstance().removeAllPlayerListener()
    }

    override fun onMusicPlayerState(playerState: Int, message: String) {
        e("playerState : $playerState , message : $message")
    }

    override fun onPrepared(totalDuration: Long) {
        e("totalDuration : $totalDuration")
    }

    override fun onBufferingUpdate(percent: Int) {}

    override fun onInfo(event: Int, extra: Int) {}

    override fun onPlayMusicOnInfo(musicInfo: MusicBean, position: Int) {}

    override fun onMusicPathInvalid(musicInfo: MusicBean, position: Int) {}

    override fun onTaskRuntime(totalDuration: Long, currentDuration: Long, alarmResidueDuration: Long, bufferProgress: Int) {
        //更新进度
        val recordBean = MusicRecordBean()
        recordBean.currenTime = currentDuration
        recordBean.updateTime = System.currentTimeMillis()
        recordBean.musicBean = MusicPlayerManager.getInstance().getCurrentPlayerMusic()
        instance!!.recordDao().insertItem(recordBean)
    }

    override fun onPlayerConfig(playModel: Int, alarmModel: Int, isToast: Boolean) {}
    private fun initIndicator() {
        val commonNavigator = CommonNavigator(activity)
        //ture 即标题平分屏幕宽度的模式
        commonNavigator.isAdjustMode = true
        commonNavigator.adapter = object : CommonNavigatorAdapter() {
            override fun getCount(): Int {
                return titles.size
            }

            override fun getTitleView(context: Context, index: Int): IPagerTitleView {
                val colorTransitionPagerTitleView = ScaleTransitionPagerTitleView(context)
                colorTransitionPagerTitleView.normalColor = Color.WHITE
                colorTransitionPagerTitleView.selectedColor = Color.WHITE
                colorTransitionPagerTitleView.text = titles[index]
                colorTransitionPagerTitleView.setOnClickListener { view: View? -> mViewPager!!.currentItem = index }
                return colorTransitionPagerTitleView
            }

            override fun getIndicator(context: Context): IPagerIndicator {
                val indicator = LinePagerIndicator(context)
                //设置宽度
                indicator.lineWidth = dp2px(getContext(), 20f).toFloat()
                //设置高度
                indicator.lineHeight = dp2px(getContext(), 2f).toFloat()
                //设置颜色
                indicator.setColors(Color.WHITE)
                //设置圆角
                indicator.roundRadius = 5f
                //设置模式
                indicator.mode = LinePagerIndicator.MODE_EXACTLY
                return indicator
            }
        }
        mIndicator!!.navigator = commonNavigator
        mViewPager!!.offscreenPageLimit = titles.size
        mViewPager!!.adapter = BaseFragmentPagerAdapter(childFragmentManager, fragments, Arrays.asList(*titles))
        //与ViewPagger联动
        ViewPagerHelper.bind(mIndicator, mViewPager)
    }
}