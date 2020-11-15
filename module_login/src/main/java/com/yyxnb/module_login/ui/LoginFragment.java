package com.yyxnb.module_login.ui;

import android.Manifest;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.BackgroundColorSpan;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.yyxnb.lib_arch.annotations.BindRes;
import com.yyxnb.lib_arch.annotations.BindViewModel;
import com.yyxnb.common_base.base.BaseFragment;
import com.yyxnb.module_login.R;
import com.yyxnb.module_login.config.UserManager;
import com.yyxnb.module_login.databinding.FragmentLoginBinding;
import com.yyxnb.module_login.utils.DownTimer;
import com.yyxnb.module_login.viewmodel.LoginViewModel;
import com.yyxnb.lib_system.PhoneInfoUtils;
import com.yyxnb.lib_system.permission.PermissionListener;
import com.yyxnb.lib_system.permission.PermissionUtils;

import static com.yyxnb.common_base.arouter.ARouterConstant.LOGIN_FRAGMENT;


/**
 * ================================================
 * 作    者：yyx
 * 版    本：1.0
 * 日    期：2020/11/13
 * 历    史：
 * 描    述：登录界面
 * ================================================
 */
@Route(path = LOGIN_FRAGMENT)
@BindRes
public class LoginFragment extends BaseFragment {

    private FragmentLoginBinding binding;

    private EditText etPhone;
    private DownTimer timer = new DownTimer(this);

    private final String[] words = new String[]{"《用户协议》", "《隐私政策》"};

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

        timer.setTotalTime(10 * 1000);
        etPhone.setText(UserManager.getInstance().getUserBean().phone);

        binding.ivBack.setOnClickListener(v -> {
            finish();
        });
        binding.tvLogin.setOnClickListener(v -> {
            if (!binding.ivUserAgreeCheck.isSelected()) {
                toast("请先勾选协议！");
                return;
            }
            String phone = etPhone.getText().toString();
            String code = binding.etCode.getText().toString();
            mViewModel.reqLogin(phone, code);
        });
        binding.tvVisitorLogin.setOnClickListener(v -> {
            if (!binding.ivUserAgreeCheck.isSelected()) {
                toast("请先勾选协议！");
                return;
            }
            mViewModel.reqVisitorLogin();
        });
        binding.tvVerificationCode.setOnClickListener(v -> {
            String phone = etPhone.getText().toString();
            mViewModel.reqSmsCode(phone);
        });
        binding.ivUserAgreeCheck.setOnClickListener(v -> {
            binding.ivUserAgreeCheck.setSelected(!binding.ivUserAgreeCheck.isSelected());
        });

        timer.setTimerListener(new DownTimer.TimeListener() {
            @Override
            public void onFinish() {
                Log.d("Tag", "结束");
                binding.tvVerificationCode.setText(getString(R.string.login_verification_code));
                binding.tvVerificationCode.setEnabled(true);
                log("" + binding.tvVerificationCode.isEnabled());
            }

            @Override
            public void onInterval(long remainTime) {
                Log.d("Tag", "剩余：" + remainTime + " , 剩余：" + (remainTime / 1000));
                binding.tvVerificationCode.setText("" + (remainTime / 1000) + "s后重新发送");
            }
        });

    }

    @Override
    public void initViewData() {
        super.initViewData();

        PermissionUtils.with(getActivity())
                //添加所有你需要申请的权限
                .addPermissions(Manifest.permission.READ_PHONE_STATE)
                //添加权限申请回调监听 如果申请失败 会返回已申请成功的权限列表，用户拒绝的权限列表和用户点击了不再提醒的永久拒绝的权限列表
                .setPermissionsCheckListener(new PermissionListener() {
                    @Override
                    public void permissionRequestSuccess() {
                        //所有权限授权成功才会回调这里
                        try {
                            log("" + PhoneInfoUtils.getPhoneInfo());
//                            log("" + PhoneInfoUtils.getNativePhoneNumber());
                            // 去掉+86 ，默认是+86手机号
                            binding.etPhone.setText(PhoneInfoUtils.getNativePhoneNumber().replace("+86", ""));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void permissionRequestFail(String[] grantedPermissions, String[] deniedPermissions, String[] forceDeniedPermissions) {
                        //当有权限没有被授权就会回调这里
                        //会返回已申请成功的权限列表（grantedPermissions）
                        //用户拒绝的权限列表（deniedPermissions）
                        //用户点击了不再提醒的永久拒绝的权限列表（forceDeniedPermissions）
                    }
                })
                //生成配置
                .createConfig()
                //配置是否强制用户授权才可以使用，当设置为true的时候，如果用户拒绝授权，会一直弹出授权框让用户授权
                .setForceAllPermissionsGranted(true)
                //配置当用户点击了不再提示的时候，会弹窗指引用户去设置页面授权，这个参数是弹窗里面的提示内容
                .setForceDeniedPermissionTips("请前往设置->应用->【" + PermissionUtils.getAppName(getContext()) + "】->权限中打开相关权限，否则功能无法正常运行！")
                //构建配置并生效
                .buildConfig()
                //开始授权
                .startCheckPermission();

        initWords();
    }


    @Override
    public void initObservable() {
        super.initObservable();

        log("---initObservable--");

        mViewModel.getUser().observe(this, userBean -> {
            if (userBean != null) {
                log("userBean : " + userBean.toString());
                UserManager.getInstance().setUserBean(userBean);
                finish();
            }
        });

        mViewModel.msgEvent.observe(this, msgData -> {
            if (LoginViewModel.key.equals(msgData.key)) {
                switch (msgData.type) {
                    case TOAST:
                        toast(msgData.value.toString());
                        break;
                    case MSG:
                        break;
                    case NUMBER:
                        binding.etCode.setText(msgData.value.toString());
                        binding.tvVerificationCode.setEnabled(false);
                        timer.start();
                        break;
                    case LOADING:
                        break;
                    case HIDE_LOADING:
                        break;
                    default:
                        break;
                }
            }
        });

    }

    /**
     * 协议高亮
     */
    private void initWords() {
        String content = binding.tvUserAgreementPolicy.getText().toString();
        SpannableStringBuilder strSpannable = new SpannableStringBuilder(content);
        ClickableSpan clickUserSpan = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                toast(words[0]);
            }

            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(getResources().getColor(R.color.colorTheme));
                ds.setUnderlineText(false);
            }
        };

        ClickableSpan clickPolicySpan = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                toast(words[1]);
            }

            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(getResources().getColor(R.color.colorTheme));
                ds.setUnderlineText(false);
            }
        };
        strSpannable.setSpan(clickUserSpan, content.lastIndexOf(words[0]),
                content.lastIndexOf(words[0]) + words[0].length(),
                Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        strSpannable.setSpan(clickPolicySpan, content.lastIndexOf(words[1]),
                content.lastIndexOf(words[1]) + words[1].length(),
                Spannable.SPAN_EXCLUSIVE_INCLUSIVE);

        // 设置背景色为白色，不然会有选中效果
        strSpannable.setSpan(new BackgroundColorSpan(getResources().getColor(R.color.white)), content.lastIndexOf(words[0]),
                content.lastIndexOf(words[0]) + words[0].length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        strSpannable.setSpan(new BackgroundColorSpan(getResources().getColor(R.color.white)), content.lastIndexOf(words[1]),
                content.lastIndexOf(words[1]) + words[1].length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);

        binding.tvUserAgreementPolicy.setText(strSpannable);
        binding.tvUserAgreementPolicy.setMovementMethod(LinkMovementMethod.getInstance());
    }

}
