package com.yyxnb.module_login.config;

import androidx.lifecycle.LiveData;

import com.yyxnb.common_res.bean.BaseData;
import com.yyxnb.common_res.bean.UserVo;
import com.yyxnb.module_login.bean.request.LoginDto;

import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface LoginApi {

    /**
     * 获取验证码
     *
     * @param phone
     * @return
     */
    @POST("login/verificationCode")
    LiveData<BaseData<String>> verificationCode(@Query("phone") String phone);

    /**
     * 手机号登录
     *
     * @param dto
     * @return
     */
    @POST("login/phoneLogin")
    LiveData<BaseData<UserVo>> phoneLogin(@Body LoginDto dto);

    /**
     * 游客登录
     *
     * @return
     */
    @POST("login/visitorLogin")
    LiveData<BaseData<UserVo>> visitorLogin();
}
