package com.yyxnb.module_login.ui;

import android.os.Bundle;
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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.yyxnb.common_base.base.BaseFragment;
import com.yyxnb.common_base.event.MessageEvent;
import com.yyxnb.common_base.event.StatusEvent;
import com.yyxnb.common_base.event.TypeEvent;
import com.yyxnb.common_res.constants.LoginRouterPath;
import com.yyxnb.module_login.R;
import com.yyxnb.module_login.config.LoginManager;
import com.yyxnb.module_login.constants.ExtraKeys;
import com.yyxnb.module_login.databinding.FragmentLoginBinding;
import com.yyxnb.module_login.utils.DownTimer;
import com.yyxnb.module_login.viewmodel.LoginViewModel;
import com.yyxnb.what.arch.annotations.BindRes;
import com.yyxnb.what.arch.annotations.BindViewModel;


/**
 * ================================================
 * 作    者：yyx
 * 版    本：1.0
 * 日    期：2020/11/13
 * 历    史：
 * 描    述：登录界面
 * ================================================
 */
@Route(path = LoginRouterPath.MAIN_FRAGMENT)
@BindRes
public class LoginFragment extends BaseFragment {

    private FragmentLoginBinding binding;

    private EditText etPhone;
    private final DownTimer timer = new DownTimer(this);

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

        setOnClickListener(binding.ivBack, binding.tvLogin, binding.tvVisitorLogin, binding.tvVerificationCode, binding.ivUserAgreeCheck);

    }

    @Override
    public void initViewData() {
        super.initViewData();

        binding.etPhone.setText("19999999999");

        initWords();
    }


    @Override
    public void initObservable() {
        super.initObservable();

        mViewModel.getMessageEvent().observe(this, (MessageEvent.MessageObserver) this::toast);

        mViewModel.getTypeEvent().observe(this, (TypeEvent.TypeObserver) t -> {
            if (ExtraKeys.LOGIN.equals(t.type)) {
                LoginManager.getInstance().setToken(t.value.toString());
                mViewModel.userLiveData.reqUser();
                finish();
            } else if (ExtraKeys.CODE.equals(t.type)) {
                binding.etCode.setText(t.value.toString());
                binding.tvVerificationCode.setEnabled(false);
                timer.start();
            }
        });

        mViewModel.getStatusEvent().observe(this, (StatusEvent.StatusObserver) status -> {

            log("login " + status.name());

        });

    }

    @Override
    public void onClickEvent(View v) {
        int id = v.getId();
        if (id == R.id.ivBack) {
            finish();
        } else if (id == R.id.tvLogin) {
            if (!binding.ivUserAgreeCheck.isSelected()) {
                toast("请先勾选协议！");
                return;
            }
            String phone = etPhone.getText().toString();
            String code = binding.etCode.getText().toString();
            mViewModel.reqLogin(phone, code);
        } else if (id == R.id.tvVisitorLogin) {
            if (!binding.ivUserAgreeCheck.isSelected()) {
                toast("请先勾选协议！");
                return;
            }
            mViewModel.reqVisitorLogin();
        } else if (id == R.id.tvVerificationCode) {
            String phone = etPhone.getText().toString();
            mViewModel.reqSmsCode(phone);
        } else if (id == R.id.ivUserAgreeCheck) {
            binding.ivUserAgreeCheck.setSelected(!binding.ivUserAgreeCheck.isSelected());
        }
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
