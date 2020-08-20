package com.yyxnb.module_music.ui

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.yyxnb.adapter.BaseViewHolder
import com.yyxnb.adapter.MultiItemTypeAdapter
import com.yyxnb.arch.annotations.BindRes
import com.yyxnb.arch.annotations.BindViewModel
import com.yyxnb.common_base.base.BaseFragment
import com.yyxnb.lib_music.MusicPlayerManager
import com.yyxnb.module_music.R
import com.yyxnb.module_music.adapter.MusicNetWorkListAdapter
import com.yyxnb.module_music.bean.MusicBean
import com.yyxnb.module_music.databinding.FragmentMusicNetworkBinding
import com.yyxnb.module_music.db.MusicDatabase.Companion.instance
import com.yyxnb.module_music.viewmodel.MusicViewModel

/**
 * 网络音乐.
 */
@BindRes(subPage = true)
class MusicNetworkFragment : BaseFragment() {
    private var binding: FragmentMusicNetworkBinding? = null
    private var mRecyclerView: RecyclerView? = null
    private var mAdapter: MusicNetWorkListAdapter? = null

    @BindViewModel
    var mViewModel: MusicViewModel? = null
    override fun initLayoutResId(): Int {
        return R.layout.fragment_music_network
    }

    override fun initView(savedInstanceState: Bundle?) {
        binding = getBinding()
        mRecyclerView = binding!!.mRecyclerView
    }

    override fun initViewData() {
        mAdapter = MusicNetWorkListAdapter()
        mRecyclerView!!.layoutManager = LinearLayoutManager(context)
        mRecyclerView!!.setHasFixedSize(true)
        mRecyclerView!!.adapter = mAdapter
        mAdapter!!.setOnItemClickListener(object : MultiItemTypeAdapter.SimpleOnItemClickListener() {
            override fun onItemClick(view: View, holder: BaseViewHolder, position: Int) {
                super.onItemClick(view, holder, position)

                // 更新数据源
                instance!!.musicDao().deleteAll()
                instance!!.musicDao().insertItems(mAdapter!!.data)
                MusicPlayerManager.getInstance().startPlayMusic(mAdapter!!.data, position)
            }
        })
    }

    override fun initObservable() {
        mViewModel!!.reqMusicData()
        mViewModel!!.musicData.observe(this, { data: List<MusicBean?>? ->
            if (data != null && data.size > 0) {
                mAdapter!!.setDataItems(data)
            }
        })
    }
}