package com.yyxnb.module_user.config;

import com.yyxnb.common_res.bean.UserVo;
import com.yyxnb.common_res.constants.Constants;
import com.yyxnb.what.cache.KvUtils;

import cn.hutool.core.util.StrUtil;

public class UserManager {

    private static volatile UserManager mInstance = null;

    private UserManager() {
    }

    public static UserManager getInstance() {
        if (null == mInstance) {
            synchronized (UserManager.class) {
                if (null == mInstance) {
                    mInstance = new UserManager();
                }
            }
        }
        return mInstance;
    }

    private UserVo userVo = null;

    public UserVo getUserInfo() {
        if (userVo == null) {
            userVo = new UserVo();
        }
        return userVo;
    }

    public void setUserBean(UserVo vo) {
        KvUtils.save(Constants.USER_ID, vo.getUserId());
        KvUtils.save(Constants.USER_TOKEN, vo.getToken());
        this.userVo = vo;
    }

    // 是否登录
    public boolean isLogin() {
        return StrUtil.isNotBlank(token());
    }

    // token
    public String token() {
        return KvUtils.get(Constants.USER_TOKEN, "");
    }
}
