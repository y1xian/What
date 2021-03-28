package com.yyxnb.module_login.viewmodel;

import com.yyxnb.common_base.base.CommonViewModel;
import com.yyxnb.common_base.event.TypeEvent;
import com.yyxnb.common_res.bean.BaseData;
import com.yyxnb.common_res.bean.UserVo;
import com.yyxnb.common_res.config.Http;
import com.yyxnb.common_res.db.AppDatabase;
import com.yyxnb.common_res.db.UserDao;
import com.yyxnb.common_res.utils.UserLiveData;
import com.yyxnb.module_login.bean.request.LoginDto;
import com.yyxnb.module_login.config.LoginApi;
import com.yyxnb.module_login.constants.ExtraKeys;
import com.yyxnb.what.core.log.LogUtils;

import cn.hutool.core.util.PhoneUtil;

/**
 * ================================================
 * 作    者：yyx
 * 版    本：1.0
 * 日    期：2020/11/13
 * 历    史：
 * 描    述：LoginViewModel
 * ================================================
 */
public class LoginViewModel extends CommonViewModel {

    private final LoginApi mApi = Http.getInstance().create(LoginApi.class);
    private final UserDao userDao = AppDatabase.getInstance().userDao();
    public UserLiveData userLiveData = UserLiveData.getInstance();

    /**
     * 手机验证码登录
     *
     * @param phone
     * @param code
     */
    public void reqLogin(String phone, String code) {
        if (PhoneUtil.isPhone(phone)) {

            if (code.length() != 4) {
                getMessageEvent().setValue("验证码填写错误！");
                return;
            }

            LoginDto dto = new LoginDto();
            dto.setPhone(phone);
            dto.setCode(code);
            launchOnlyResult(mApi.phoneLogin(dto), new HttpResponseCallback<BaseData<UserVo>>() {
                @Override
                public void onSuccess(BaseData<UserVo> data) {
                    userDao.insertItem(data.data);
                    getTypeEvent().postValue(new TypeEvent(ExtraKeys.LOGIN, data.data.getToken()));
                }

                @Override
                public void onError(String msg) {
                }
            });

        } else {
            getMessageEvent().setValue("请输入正确的手机号码");
        }
    }

    /**
     * 游客登录
     */
    public void reqVisitorLogin() {
        launchOnlyResult(mApi.visitorLogin(), new HttpResponseCallback<BaseData<UserVo>>() {
            @Override
            public void onSuccess(BaseData<UserVo> data) {
                userDao.insertItem(data.data);
                getTypeEvent().postValue(new TypeEvent(ExtraKeys.LOGIN, data.data.getToken()));
            }

            @Override
            public void onError(String msg) {
            }
        });
    }

    /**
     * 获取验证码
     *
     * @param phone
     */
    public void reqSmsCode(String phone) {
        if (PhoneUtil.isPhone(phone)) {

            launchOnlyResult(mApi.verificationCode(phone), new HttpResponseCallback<BaseData<String>>() {
                @Override
                public void onSuccess(BaseData<String> data) {
                    getTypeEvent().postValue(new TypeEvent(ExtraKeys.CODE, data.data));
                }

                @Override
                public void onError(String msg) {
                    LogUtils.e(msg);
                }
            });
        } else {
            getMessageEvent().setValue("请输入正确的手机号码");
        }

    }

}
