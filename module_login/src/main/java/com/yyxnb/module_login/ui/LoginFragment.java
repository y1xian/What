package com.yyxnb.module_login.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.EditText;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.yyxnb.arch.annotations.BindRes;
import com.yyxnb.arch.annotations.BindViewModel;
import com.yyxnb.common.ToastUtils;
import com.yyxnb.common.log.LogUtils;
import com.yyxnb.common_base.base.BaseFragment;
import com.yyxnb.module_login.config.UserManager;
import com.yyxnb.module_login.R;
import com.yyxnb.module_login.databinding.FragmentLoginBinding;
import com.yyxnb.module_login.viewmodel.LoginViewModel;

import static com.yyxnb.common_base.arouter.ARouterConstant.LOGIN_FRAGMENT;


/**
 * 登录
 */
@Route(path = LOGIN_FRAGMENT)
@BindRes
public class LoginFragment extends BaseFragment {

    private FragmentLoginBinding binding;

    private EditText etPhone;

    @BindViewModel
    LoginViewModel mViewModel;

    @Override
    public int initLayoutResId() {
        return R.layout.fragment_login;
    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
        binding = getBinding();
        etPhone = binding.etPhone;

        etPhone.setText(UserManager.getInstance().getUserBean().phone);

        binding.ivBack.setOnClickListener(v -> {
            finish();
        });
        binding.tvLogin.setOnClickListener(v -> {
            String phone = etPhone.getText().toString();

            if (phone.isEmpty()) {
                ToastUtils.normal("手机号不能为空！");
                return;
            }
            mViewModel.reqLogin(phone);
        });

    }

    @Override
    public void initViewData() {
        super.initViewData();

    }

    @Override
    public void initObservable() {
        super.initObservable();

        LogUtils.d("---initObservable--");

        mViewModel.getUser().observe(this, userBean -> {
            if (userBean != null) {
                LogUtils.e("userBean : " + userBean.toString());
                UserManager.getInstance().setUserBean(userBean);
                finish();
            }
        });
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

    public static LoginFragment newInstance() {

        Bundle args = new Bundle();

        LoginFragment fragment = new LoginFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
