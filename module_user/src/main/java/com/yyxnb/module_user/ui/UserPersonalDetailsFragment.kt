package com.yyxnb.module_user.ui

import android.os.Bundle
import android.view.View
import com.yyxnb.arch.annotations.BindViewModel
import com.yyxnb.common_base.arouter.service.impl.LoginImpl.Companion.instance
import com.yyxnb.common_base.base.BaseFragment
import com.yyxnb.common_base.bean.UserBean
import com.yyxnb.module_user.R
import com.yyxnb.module_user.databinding.FragmentUserPersonalDetailsBinding
import com.yyxnb.module_user.viewmodel.UserViewModel

/**
 * 个人详情页.
 */
class UserPersonalDetailsFragment : BaseFragment() {

    private var binding: FragmentUserPersonalDetailsBinding? = null

    @BindViewModel
    lateinit var mViewModel: UserViewModel

    override fun initLayoutResId(): Int {
        return R.layout.fragment_user_personal_details
    }

    override fun initView(savedInstanceState: Bundle?) {
        binding = getBinding()
        binding!!.iTitle.mTitle.centerTextView.setText(R.string.personal_details)
        binding!!.iTitle.mTitle.setBackListener { finish() }
    }

    override fun initViewData() {
        mViewModel.reqUserId.postValue(instance!!.userInfo!!.userId)
        mViewModel.user.observe(this, { userBean: UserBean? ->
            if (userBean != null) {
                instance!!.updateUserInfo(userBean)
                binding!!.data = userBean
            }
            binding!!.data = userBean
        })
    }
}