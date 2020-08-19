package com.yyxnb.module_login.ui

import android.os.Bundle
import android.view.View
import android.widget.EditText
import com.alibaba.android.arouter.facade.annotation.Route
import com.yyxnb.arch.annotations.BindRes
import com.yyxnb.arch.annotations.BindViewModel
import com.yyxnb.common.CommonManager.toast
import com.yyxnb.common.utils.log.LogUtils.d
import com.yyxnb.common.utils.log.LogUtils.e
import com.yyxnb.common_base.arouter.ARouterConstant.LOGIN_FRAGMENT
import com.yyxnb.common_base.base.BaseFragment
import com.yyxnb.common_base.bean.UserBean
import com.yyxnb.module_login.R
import com.yyxnb.module_login.config.UserManager
import com.yyxnb.module_login.databinding.FragmentLoginBinding
import com.yyxnb.module_login.viewmodel.LoginViewModel

/**
 * 登录
 */
@Route(path = LOGIN_FRAGMENT)
@BindRes
class LoginFragment : BaseFragment() {

    private var binding: FragmentLoginBinding? = null
    private var etPhone: EditText? = null

    @BindViewModel
    lateinit var mViewModel: LoginViewModel

    override fun initLayoutResId(): Int {
        return R.layout.fragment_login
    }

    override fun initView(savedInstanceState: Bundle?) {
        binding = getBinding()
        etPhone = binding!!.etPhone
        etPhone?.setText(UserManager.getUserBean().phone)
        binding!!.ivBack.setOnClickListener { v: View? -> finish() }
        binding!!.tvLogin.setOnClickListener { v: View? ->
            val phone = etPhone!!.text.toString()
            if (phone.isEmpty()) {
                toast("手机号不能为空！")
                return@setOnClickListener
            }
            mViewModel.reqLogin(phone)
        }
    }

    override fun initViewData() {
        super.initViewData()
    }

    override fun initObservable() {
        super.initObservable()
        d("---initObservable--")
        mViewModel.user.observe(this, { userBean: UserBean? ->
            if (userBean != null) {
                e("userBean : $userBean")
                UserManager.setUserBean(userBean)
                finish()
            }
        })
        //        mViewModel.getUserAll().observe(this, userBean -> {
//            if (userBean != null) {
//                LogUtils.list(userBean);
//            }
//        });


//        mViewModel.reqTest();

        //无状态
//        mViewModel.getTest().observe(this, baseData -> {
//
//                TestData data = baseData.getResult().get(0);
//                tvTitle.setText(data.getTestInt() + " \n"
//                        + data.getTestInt2() + " \n"
//                        + data.getTestInt3() + " \n"
//                        + data.getTestDouble() + " \n"
//                        + data.getTestDouble2() + " \n"
//                        + data.getTestDouble3() + " \n"
//                        + data.getTestString() + " \n"
//                        + data.getTestString2() + " \n"
//                        + data.getTestString3() + " \n\n\n\n\n\n" + data.toString());
//
//        });

        //有状态
//        mViewModel.getTest2().observe(this, baseData -> {
//
//            if (baseData != null) {
//                switch (baseData.getStatus()) {
//                    case LOADING:
//                        LogUtils.INSTANCE.w("---LOADING--");
//                        break;
//                    case ERROR:
//                        LogUtils.INSTANCE.e("---initObservable--" + baseData.getMessage());
//                        break;
//                    case SUCCESS:
//                        if (baseData.getData() != null) {
//                            tvTitle.setText(baseData.getData().getResult().get(0).toString());
//                            LogUtils.INSTANCE.w("---SUCCESS--");
//                        }else {
//                            LogUtils.INSTANCE.w("---SUCCESS-- null");
//                        }
//                        break;
//                    default:
//                        break;
//                }
//            }
//        });
    }

    companion object {
        @JvmStatic
        fun newInstance(): LoginFragment {
            val args = Bundle()
            val fragment = LoginFragment()
            fragment.arguments = args
            return fragment
        }
    }
}