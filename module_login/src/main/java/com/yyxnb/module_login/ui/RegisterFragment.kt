package com.yyxnb.module_login.ui

import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.yyxnb.common.utils.log.LogUtils.d
import com.yyxnb.common_base.base.BaseFragment
import com.yyxnb.module_login.R

/**
 * 注册
 */
class RegisterFragment : BaseFragment() {

    private var tvShow: TextView? = null

    override fun initLayoutResId(): Int {
        return R.layout.fragment_register
    }

    override fun initView(savedInstanceState: Bundle?) {
        tvShow = findViewById<TextView>(R.id.tvShow)
        tvShow!!.setOnClickListener { v: View? -> startFragment(LoginFragment.newInstance()) }
    }

    override fun initViewData() {
        super.initViewData()
        d("-RegisterFragment--initViewData--")
    }

    companion object {
        fun newInstance(): RegisterFragment {
            val args = Bundle()
            val fragment = RegisterFragment()
            fragment.arguments = args
            return fragment
        }
    }
}