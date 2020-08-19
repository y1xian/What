package com.yyxnb.module_user.ui.wallet

import android.os.Bundle
import android.view.View
import com.yyxnb.arch.annotations.BindRes
import com.yyxnb.common_base.base.BaseFragment
import com.yyxnb.module_user.R
import com.yyxnb.module_user.databinding.FragmentUserWalletBinding

/**
 * 钱包.
 */
@BindRes
class UserWalletFragment : BaseFragment() {

    private var binding: FragmentUserWalletBinding? = null

    override fun initLayoutResId(): Int {
        return R.layout.fragment_user_wallet
    }

    override fun initView(savedInstanceState: Bundle?) {
        binding = getBinding()
        binding!!.iTitle.mTitle.centerTextView.setText(R.string.wallet)
        binding!!.iTitle.mTitle.setBackListener { v: View? -> finish() }
    }
}