package com.yyxnb.module_user.ui

import android.os.Bundle
import android.view.View
import com.yyxnb.common_base.arouter.service.impl.LoginImpl.Companion.instance
import com.yyxnb.common_base.base.BaseFragment
import com.yyxnb.module_user.R
import com.yyxnb.module_user.databinding.FragmentUserSetUpBinding
import com.yyxnb.popup.PopupManager

/**
 * 设置.
 */
class UserSetUpFragment : BaseFragment() {

    private var binding: FragmentUserSetUpBinding? = null

    override fun initLayoutResId(): Int {
        return R.layout.fragment_user_set_up
    }

    override fun initView(savedInstanceState: Bundle?) {
        binding = getBinding()
        binding!!.iTitle.mTitle.centerTextView.setText(R.string.set_up)
        binding!!.iTitle.mTitle.setBackListener { finish() }
        binding!!.tvLoginOut.setOnClickListener {
            PopupManager.Builder(context)
                    .asConfirm(null, "是否确认退出登录", {

                        // 确定
                        instance!!.loginOut()
                        finish()
                    }) {}.show()
        }
    }
}