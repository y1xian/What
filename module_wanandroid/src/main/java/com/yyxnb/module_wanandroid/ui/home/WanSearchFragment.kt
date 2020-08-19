package com.yyxnb.module_wanandroid.ui.home

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import com.tencent.mmkv.MMKV
import com.yyxnb.arch.annotations.BindRes
import com.yyxnb.arch.annotations.BindViewModel
import com.yyxnb.common_base.base.BaseFragment
import com.yyxnb.module_wanandroid.R
import com.yyxnb.module_wanandroid.bean.WanClassifyBean
import com.yyxnb.module_wanandroid.config.DataConfig
import com.yyxnb.module_wanandroid.databinding.FragmentWanSearchBinding
import com.yyxnb.module_wanandroid.viewmodel.WanSearchViewModel
import com.yyxnb.view.text.FlowlayoutTags
import java.util.*

/**
 * 搜索.
 */
@BindRes
class WanSearchFragment : BaseFragment() {

    private val mmkv = MMKV.defaultMMKV()
    private var binding: FragmentWanSearchBinding? = null

    @BindViewModel
    var mViewModel: WanSearchViewModel? = null
    private var mHotTags: FlowlayoutTags? = null
    private var mHistoryTags: FlowlayoutTags? = null

    override fun initLayoutResId(): Int {
        return R.layout.fragment_wan_search
    }

    override fun initView(savedInstanceState: Bundle?) {
        binding = getBinding()
        mHotTags = binding!!.mHotTags
        mHistoryTags = binding!!.mHistoryTags
    }

    override fun initViewData() {
        binding!!.ivClose.setOnClickListener { v: View? -> binding!!.etInput.setText("") }
        binding!!.tvSearch.setOnClickListener { v: View? ->
            val key = binding!!.etInput.text.toString()
            initArguments()!!.putString("key", key)
            startFragment(WanAriticleListFragment())
            setHistoryTags(key)
        }
        binding!!.etInput.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable) {
                binding!!.ivClose.visibility = if (s.length > 0) View.VISIBLE else View.GONE
            }
        })
        mHotTags!!.setOnTagClickListener { tag: String ->
            initArguments()!!.putString("key", tag)
            startFragment(WanAriticleListFragment())
            setHistoryTags(tag)
        }
        mHistoryTags!!.setOnTagClickListener { tag: String ->
            initArguments()!!.putString("key", tag)
            startFragment(WanAriticleListFragment())
            setHistoryTags(tag)
        }
    }

    override fun initObservable() {
        mViewModel!!.getSearchData()
        mViewModel!!.searchData.observe(this, { data: List<WanClassifyBean>? ->
            if (data != null) {
                val tag: MutableList<String> = ArrayList()
                for (bean in data) {
                    tag.add(bean.name)
                }
                mHotTags!!.setTags(tag)
            }
        })
    }

    private fun setHistoryTags(str: String) {
        val key = mmkv.getStringSet(DataConfig.SEARCH_HISTORY_KEY, TreeSet())
        //        Set<String> sortSet = new TreeSet<String>((o1, o2) -> {
//            return o2.compareTo(o1);//降序排列
//        });
        if (str.isEmpty()) {
            mHistoryTags!!.setTags(ArrayList(key))
            return
        } else {
            key!!.remove(str)
            key.add(str)
        }

//        sortSet.addAll(key);
        mHistoryTags!!.setTagsUncheckedColorAnimal(false)
        mHistoryTags!!.setTags(ArrayList(key))
        mmkv.encode(DataConfig.SEARCH_HISTORY_KEY, key)
    }

    override fun onVisible() {
        setHistoryTags("")
    }
}