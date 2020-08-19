package com.yyxnb.module_user.ui

import android.os.Bundle
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.tencent.mmkv.MMKV
import com.yyxnb.arch.annotations.BarStyle
import com.yyxnb.arch.annotations.BindRes
import com.yyxnb.arch.annotations.BindViewModel
import com.yyxnb.common.utils.log.LogUtils.d
import com.yyxnb.common.utils.log.LogUtils.e
import com.yyxnb.common_base.arouter.ARouterConstant.LOGIN_FRAGMENT
import com.yyxnb.common_base.arouter.ARouterConstant.USER_FRAGMENT
import com.yyxnb.common_base.arouter.ARouterUtils.navFragment
import com.yyxnb.common_base.arouter.service.impl.LoginImpl.Companion.instance
import com.yyxnb.common_base.base.BaseFragment
import com.yyxnb.common_base.bean.UserBean
import com.yyxnb.common_base.config.BaseConfig
import com.yyxnb.common_base.config.Constants.USER_ID
import com.yyxnb.module_user.R
import com.yyxnb.module_user.databinding.FragmentUserBinding
import com.yyxnb.module_user.ui.wallet.UserWalletFragment
import com.yyxnb.module_user.viewmodel.UserViewModel

/**
 * 我的 - 界面.
 */
@BindRes(statusBarStyle = BarStyle.LIGHT_CONTENT)
@Route(path = USER_FRAGMENT)
class UserFragment : BaseFragment() {

    private var binding: FragmentUserBinding? = null

    @BindViewModel
    lateinit var mViewModel: UserViewModel

    override fun initLayoutResId(): Int {
        return R.layout.fragment_user
    }

    override fun initView(savedInstanceState: Bundle?) {
        binding = getBinding()
        binding!!.clHead.setOnClickListener { v: View? ->
            if (!instance!!.isLogin) {
                startFragment(navFragment(LOGIN_FRAGMENT))
            } else {
                startFragment(UserPersonalDetailsFragment())
            }
        }
        binding!!.ivWallet.setOnClickListener { v: View? -> startFragment(UserWalletFragment()) }
        binding!!.rbSetUp.setOnClickListener { v: View? -> startFragment(UserSetUpFragment()) }
    }

    override fun initViewData() {}

    override fun initObservable() {
        mViewModel!!.user.observe(this, { userBean: UserBean? ->
            if (userBean != null) {
                instance!!.updateUserInfo(userBean)
                binding!!.data = userBean
                e("u : $userBean ${instance!!.userInfo.toString()}")
            }
            binding!!.data = userBean
        })
    }

    override fun onInVisible() {
        super.onInVisible()
        d("---onInVisible---")
    }

    override fun onVisible() {
        super.onVisible()
        getBaseDelegate().setNeedsStatusBarAppearanceUpdate()
        d("---onVisible---" + MMKV.defaultMMKV().decodeInt(USER_ID, 0))
        mViewModel!!.reqUserId.postValue(BaseConfig.kv.decodeInt(USER_ID, 0))
    }
}