package com.yyxnb.module_login.config;

import com.yyxnb.common_res.bean.UserVo;
import com.yyxnb.common_res.constants.Constants;
import com.yyxnb.common_res.utils.UserLiveData;
import com.yyxnb.what.cache.KvUtils;

import cn.hutool.core.util.StrUtil;

public class LoginManager {

    private static volatile LoginManager mInstance = null;

    private LoginManager() {
    }

    public static LoginManager getInstance() {
        if (null == mInstance) {
            synchronized (LoginManager.class) {
                if (null == mInstance) {
                    mInstance = new LoginManager();
                }
            }
        }
        return mInstance;
    }

    private UserVo userVo = null;

    public UserVo getUserInfo() {
        if (userVo == null) {
//            int uid = KvUtils.get(USER_ID, 0);
//            if (uid != 0) {
//                userVo = AppDatabase.getInstance().userDao().getUserMainThread(uid);
//                if (userVo == null) {
//                    userVo = new UserVo();
//                }
//            } else {
                userVo = new UserVo();
//            }
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

    public void setToken(String token) {
        KvUtils.save(Constants.USER_TOKEN, token);
    }

    // 退出登录
    public void loginOut() {
        KvUtils.remove(Constants.USER_ID);
        KvUtils.remove(Constants.USER_TOKEN);
        UserLiveData.getInstance().loginOut();
        userVo = null;
    }
}
