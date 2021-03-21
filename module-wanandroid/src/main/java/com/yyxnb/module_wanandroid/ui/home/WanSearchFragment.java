package com.yyxnb.module_wanandroid.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import com.yyxnb.common_base.base.BaseFragment;
import com.yyxnb.what.arch.annotations.BindViewModel;
import com.yyxnb.what.view.text.FlowlayoutTags;
import com.yyxnb.module_wanandroid.R;
import com.yyxnb.module_wanandroid.bean.WanClassifyBean;
import com.yyxnb.module_wanandroid.config.DataConfig;
import com.yyxnb.module_wanandroid.databinding.FragmentWanSearchBinding;
import com.yyxnb.module_wanandroid.viewmodel.WanSearchViewModel;
import com.yyxnb.what.cache.KvUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * 搜索.
 */
//@BindRes
public class WanSearchFragment extends BaseFragment {

    private FragmentWanSearchBinding binding;

    @BindViewModel
    WanSearchViewModel mViewModel;

    private FlowlayoutTags mHotTags;
    private FlowlayoutTags mHistoryTags;

    @Override
    public int initLayoutResId() {
        return R.layout.fragment_wan_search;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        binding = getBinding();

        mHotTags = binding.vHotTags;
        mHistoryTags = binding.vHistoryTags;

        binding.iTitle.vTitle.setBackListener(v -> finish());

    }

    @Override
    public void initViewData() {

        binding.ivClose.setOnClickListener(v -> {
            binding.etInput.setText("");
        });

        binding.tvSearch.setOnClickListener(v -> {
            String key = binding.etInput.getText().toString();
            initArguments().putString("key", key);
            startFragment(new WanAriticleListFragment());
            setHistoryTags(key);
        });

        binding.etInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                binding.ivClose.setVisibility(s.length() > 0 ? View.VISIBLE : View.GONE);
            }
        });

        mHotTags.setOnTagClickListener(tag -> {
            initArguments().putString("key", tag);
            startFragment(new WanAriticleListFragment());
            setHistoryTags(tag);
        });

        mHistoryTags.setOnTagClickListener(tag -> {
            initArguments().putString("key", tag);
            startFragment(new WanAriticleListFragment());
            setHistoryTags(tag);
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 888) {
//            toast("------------");
        }
        log("requestCode: " + requestCode + " , resultCode: " + resultCode);
    }

    @Override
    public void initObservable() {
        mViewModel.getSearchData();

        mViewModel.searchData.observe(this, data -> {
            if (data != null) {

                List<String> tag = new ArrayList<>();
                for (WanClassifyBean bean : data) {
                    tag.add(bean.name);
                }
                mHotTags.setTags(tag);

            }
        });
    }

    private void setHistoryTags(String str) {

        Set<String> key = KvUtils.get(DataConfig.SEARCH_HISTORY_KEY, new TreeSet<>());
//        Set<String> sortSet = new TreeSet<String>((o1, o2) -> {
//            return o2.compareTo(o1);//降序排列
//        });

        if (str.isEmpty()) {
            mHistoryTags.setTags(new ArrayList<>(key));
            return;
        } else {
            key.remove(str);
            key.add(str);
        }

//        sortSet.addAll(key);

        mHistoryTags.setTagsUncheckedColorAnimal(false);
        mHistoryTags.setTags(new ArrayList<>(key));
        KvUtils.save(DataConfig.SEARCH_HISTORY_KEY, key);
    }

    @Override
    public void onVisible() {
        setHistoryTags("");
    }
}