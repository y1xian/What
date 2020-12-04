package com.yyxnb.module_login.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;

import com.yyxnb.common_base.core.CommonViewModel;
import com.yyxnb.common_base.bean.MsgData;
import com.yyxnb.common_res.bean.UserBean;
import com.yyxnb.common_res.config.Http;
import com.yyxnb.common_res.db.AppDatabase;
import com.yyxnb.common_res.db.UserDao;
import com.yyxnb.module_login.config.LoginApi;
import com.yyxnb.lib_utils.encrypt.MD5Utils;

import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    public final static String key = "LoginViewModel";
    private LoginApi mApi = Http.getInstance().create(LoginApi.class);
    private UserDao userDao = AppDatabase.getInstance().userDao();

    private MutableLiveData<UserBean> reqUser = new MutableLiveData();

    public LiveData<UserBean> getUser() {
        return Transformations.switchMap(reqUser, input -> {
            return userDao.getUser(input.userId);
        });
    }

    public LiveData<List<UserBean>> getUserAll() {
        return userDao.getUserAll();
    }

    public void reqLogin(String phone, String code) {
        if (checkMobilePhoneNum(phone)) {

            if (code.length() != 4) {
                msgEvent.setValue(new MsgData(key, "验证码填写错误！"));
                return;
            }

            final UserBean userBean = new UserBean();
            userBean.userId = Math.abs(("uid_" + phone).hashCode());
            userBean.phone = phone;
            userBean.signature = "暂无签名";
            userBean.token = MD5Utils.parseStrToMd5L32(userBean.userId + "-token-" + userBean.phone);
            userBean.nickname = "游客" + phone.substring(7);
            userBean.isLogin = true;
            userBean.loginStatus = 1;
            userDao.insertItem(userBean);

            reqUser.setValue(userBean);

            log("user : " + userBean.toString());
        }
    }

    public void reqVisitorLogin() {
        String phone = System.currentTimeMillis() + "";
        final UserBean userBean = new UserBean();
        userBean.userId = Math.abs(("uid_" + phone).hashCode());
        userBean.phone = phone;
        userBean.signature = "暂无签名";
        userBean.token = MD5Utils.parseStrToMd5L32(userBean.userId + "-token-" + userBean.phone);
        userBean.nickname = "游客" + phone.substring(7);
        userBean.isLogin = true;
        userBean.loginStatus = -1;
        userDao.delAllVisitor();
        userDao.insertItem(userBean);

        reqUser.setValue(userBean);

        log("VisitorLogin : " + userBean.toString());
    }

    // 生成4位随机数
    public void reqSmsCode(String phone) {
        if (checkMobilePhoneNum(phone)) {
            // 避免出现3位数的情况，加1000 取值范围为 1000~9999
            int r = 1000 + new Random().nextInt(9000);
            msgEvent.setValue(new MsgData(key, MsgData.MsgType.NUMBER, r));
            msgEvent.setValue(new MsgData(key, "验证码发送成功"));
        }
    }

    /**
     * 检测手机号
     *
     * @param phone
     * @return
     */
    private boolean checkMobilePhoneNum(String phone) {

        if (phone.isEmpty()) {
            msgEvent.setValue(new MsgData(key, "手机号不能为空！"));
            return false;
        } else if (phone.length() != 11) {
            msgEvent.setValue(new MsgData(key, "手机号格式不正确！"));
            return false;
        }

        /**
         * 判断字符串是否符合手机号码格式
         * 移动号段:   134 135 136 137 138 139 147 148 150 151 152 157 158 159  165 172 178 182 183 184 187 188 198
         * 联通号段:   130 131 132 145 146 155 156 166 170 171 175 176 185 186
         * 电信号段:   133 149 153 170 173 174 177 180 181 189  191  199
         * 虚拟运营商: 170
         * @param str
         * @return 待检测的字符串
         */
        String regex = "^((13[0-9])|(14[5,6,7,9])|(15[^4])|(16[5,6])|(17[0-9])|(18[0-9])|(19[1,8,9]))\\d{8}$";

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(phone);
        if (matcher.matches()) {
            return true;
        }
        msgEvent.setValue(new MsgData(key, "手机号格式不正确！"));
        return false;
    }

}
