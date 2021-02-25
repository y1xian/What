package com.yyxnb.module_login.config;

import com.yyxnb.common_res.bean.UserBean;
import com.yyxnb.common_res.db.AppDatabase;
import com.yyxnb.util_cache.KvUtils;

import static com.yyxnb.common_res.config.Constants.USER_ID;

public class UserManager {

    private static volatile UserManager manager;

    public static UserManager getInstance() {
        if (manager == null){
            synchronized (UserManager.class){
                if (manager == null){
                    manager = new UserManager();
                }
            }
        }
        return manager;
    }

    private UserBean userBean = null;

    public UserBean getUserBean() {
        if (userBean == null) {
            int uid = KvUtils.get(USER_ID, 0);
            if (uid != 0) {
                userBean = AppDatabase.getInstance().userDao().getUserMainThread(uid);
                if (userBean == null) {
                    userBean = new UserBean();
                }
            } else {
                userBean = new UserBean();
            }
        }
        return userBean;
    }

    public void setUserBean(UserBean userBean) {
        KvUtils.save(USER_ID,userBean.userId);
        this.userBean = userBean;
    }

    // 是否登录
    public boolean isLogin() {
        return getUserBean().isLogin;
    }

    // token
    public String token() {
        return getUserBean().token;
    }

    // 退出登录
    public void loginOut() {
        AppDatabase.getInstance().userDao().deleteItem(userBean);
        KvUtils.remove(USER_ID);
        userBean = null;
    }
}
