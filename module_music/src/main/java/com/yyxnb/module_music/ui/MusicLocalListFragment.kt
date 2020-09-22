package com.yyxnb.module_music.ui

import android.Manifest
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.yyxnb.adapter.BaseViewHolder
import com.yyxnb.adapter.MultiItemTypeAdapter
import com.yyxnb.adapter.SimpleOnItemClickListener
import com.yyxnb.arch.annotations.BindRes
import com.yyxnb.arch.annotations.BindViewModel
import com.yyxnb.common_base.base.BaseFragment
import com.yyxnb.lib_music.MusicPlayerManager
import com.yyxnb.localservice.LocalConfig
import com.yyxnb.localservice.bean.LocalMedia
import com.yyxnb.localservice.manager.AudioLoaderManager
import com.yyxnb.localservice.manager.DataCallback
import com.yyxnb.module_music.R
import com.yyxnb.module_music.adapter.MusicLocalListAdapter
import com.yyxnb.module_music.bean.MusicBean
import com.yyxnb.module_music.bean.MusicLocalBean
import com.yyxnb.module_music.bean.MusicRecordBean
import com.yyxnb.module_music.databinding.FragmentMusicLocalListBinding
import com.yyxnb.module_music.db.MusicDatabase.Companion.instance
import com.yyxnb.module_music.viewmodel.MusicViewModel
import com.yyxnb.utils.permission.PermissionListener
import com.yyxnb.utils.permission.PermissionUtils
import java.util.*

/**
 * 本地音乐列表.
 */
@BindRes(subPage = true)
class MusicLocalListFragment : BaseFragment() {

    private var binding: FragmentMusicLocalListBinding? = null
    private var mRecyclerView: RecyclerView? = null
    private var mAdapter: MusicLocalListAdapter? = null

    @BindViewModel
    lateinit var mViewModel: MusicViewModel
    override fun initLayoutResId(): Int {
        return R.layout.fragment_music_local_list
    }

    override fun initView(savedInstanceState: Bundle?) {
        binding = getBinding()
        mRecyclerView = binding!!.mRecyclerView
    }

    override fun initViewData() {
        mAdapter = MusicLocalListAdapter()
        mRecyclerView!!.layoutManager = LinearLayoutManager(context)
        mRecyclerView!!.setHasFixedSize(true)
        mRecyclerView!!.adapter = mAdapter
        mAdapter!!.setOnItemClickListener(object : SimpleOnItemClickListener() {
            override fun onItemClick(view: View, holder: BaseViewHolder, position: Int) {
                super.onItemClick(view, holder, position)
                MusicPlayerManager.getInstance().startPlayMusic(mAdapter!!.data, position)

                //情况之前的数据
                instance!!.musicDao().deleteAll()
                //点击本地音乐时，替换播放数据源
                val musicBeans: List<MusicBean> = ArrayList<MusicBean>(mAdapter!!.data)
                instance!!.musicDao().insertItems(musicBeans)
            }
        })
    }

    override fun initObservable() {
//        mViewModel.reqLocalMusicData();
        mViewModel.localMusicData.observe(this, { data: List<MusicLocalBean?>? ->
            if (data != null && data.size > 0) {
                mAdapter!!.setDataItems(data)
            } else {
                localMusicData
            }
        })
        mViewModel.recordData.observe(this, { data: MusicRecordBean? ->
            if (data != null) {
//                LogUtils.e("最后一条记录： " + data);
            }
        })
    }

    private val localMusicData: Unit
        private get() {
            PermissionUtils.with(activity)
                    .addPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    .addPermissions(Manifest.permission.WAKE_LOCK)
                    .addPermissions(Manifest.permission.VIBRATE)
                    .setPermissionsCheckListener(object : PermissionListener {
                        override fun permissionRequestSuccess() {
                            activity!!.runOnUiThread {
                                loaderManager.initLoader(LocalConfig.AUDIO, null, AudioLoaderManager(activity, DataCallback { list ->
                                    if (list != null) {
                                        val localMedia: List<LocalMedia> = list[0].localMedia
                                        val musicBeans: MutableList<MusicLocalBean> = ArrayList()
                                        for (media: LocalMedia in localMedia) {
                                            val bean = MusicLocalBean()
                                            bean.mid = media.id.toString() + ""
                                            bean.title = media.title
                                            bean.url = media.path
                                            bean.totalTime = media.duration.toString() + ""
                                            bean.author = media.artist
                                            musicBeans.add(bean)
                                        }
                                        instance!!.musicLocalDao().insertItems(musicBeans)
                                        mAdapter!!.setDataItems(musicBeans)
                                    }
                                }))
                            }
                        }

                        override fun permissionRequestFail(grantedPermissions: Array<String>, deniedPermissions: Array<String>, forceDeniedPermissions: Array<String>) {}
                    })
                    .createConfig()
                    .setForceAllPermissionsGranted(true)
                    .buildConfig()
                    .startCheckPermission()
        }
}