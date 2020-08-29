package com.yyxnb.module_wanandroid.ui

import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.yyxnb.arch.annotations.BindRes
import com.yyxnb.arch.base.IActivity
import com.yyxnb.module_wanandroid.R
import com.yyxnb.module_wanandroid.databinding.ActivityWanWebBinding
import com.yyxnb.view.titlebar.TitleBar
import com.yyxnb.webview.WebActivity

@BindRes
class WanWebActivity : WebActivity(), IActivity {

    private var binding: ActivityWanWebBinding? = null
    private var title: String? = null
    private var mTitle: TitleBar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_wan_web)
        super.onCreate(savedInstanceState)

    }

    override fun initView(savedInstanceState: Bundle?) {
        mTitle = binding!!.iTitle.mTitle
        mLinearLayout = binding!!.mLinearLayout;

        title = intent.getStringExtra("title")
        url = intent.getStringExtra("url")
        mTitle?.centerTextView?.text = title
        mTitle!!.setBackListener { finish() }

        initAgent(url, mLinearLayout)
    }
}