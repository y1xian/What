package com.yyxnb.module_login.viewmodel;

import com.yyxnb.common_base.bean.LiveEvent;
import com.yyxnb.common_base.core.CommonViewModel;
import com.yyxnb.common_res.bean.BaseData;
import com.yyxnb.common_res.bean.UserVo;
import com.yyxnb.common_res.config.Http;
import com.yyxnb.common_res.config.UserLiveData;
import com.yyxnb.common_res.db.AppDatabase;
import com.yyxnb.common_res.db.UserDao;
import com.yyxnb.lib_network.SingleLiveEvent;
import com.yyxnb.module_login.bean.request.LoginDto;
import com.yyxnb.module_login.config.LoginApi;
import com.yyxnb.util_core.log.LogUtils;

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
    public SingleLiveEvent<LiveEvent> liveEvent = new SingleLiveEvent<>();
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
                liveEvent.setValue(new LiveEvent("验证码填写错误！"));
                return;
            }

            LoginDto dto = new LoginDto();
            dto.setPhone(phone);
            dto.setCode(code);
            launchOnlyResult(mApi.phoneLogin(dto), new HttpResponseCallback<BaseData<UserVo>>() {
                @Override
                public void onSuccess(BaseData<UserVo> data) {
                    userDao.insertItem(data.data);
                    liveEvent.postValue(new LiveEvent(LiveEvent.MsgType.VALUE, data.data.getToken(), "login"));
                }

                @Override
                public void onError(String msg) {
                }
            });

        } else {
            liveEvent.setValue(new LiveEvent("请输入正确的手机号码"));
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
                liveEvent.postValue(new LiveEvent(LiveEvent.MsgType.VALUE, data.data.getToken(), "login"));
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
                    liveEvent.postValue(new LiveEvent(LiveEvent.MsgType.VALUE, data.data, "code"));
                }

                @Override
                public void onError(String msg) {
                    LogUtils.e(msg);
                }
            });
        } else {
            liveEvent.setValue(new LiveEvent("请输入正确的手机号码"));
        }

    }

}
