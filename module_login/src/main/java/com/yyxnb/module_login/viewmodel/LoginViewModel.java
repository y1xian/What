package com.yyxnb.module_login.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;

import com.yyxnb.common.log.LogUtils;
import com.yyxnb.common_base.bean.UserBean;
import com.yyxnb.common_base.config.Http;
import com.yyxnb.common_base.db.AppDatabase;
import com.yyxnb.common_base.db.UserDao;
import com.yyxnb.http.BaseViewModel;
import com.yyxnb.module_login.config.LoginApi;
import com.yyxnb.utils.encrypt.MD5Utils;

import java.util.List;

public class LoginViewModel extends BaseViewModel {

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

    public void reqLogin(String phone) {
        UserBean userBean = new UserBean();
        userBean.userId = Math.abs(("uid_" + phone).hashCode());
        userBean.phone = phone;
        userBean.token = MD5Utils.parseStrToMd5L32(userBean.userId + "-token-" + userBean.phone);
        userBean.nickname = "游客" + phone.substring(7);
        userBean.isLogin = true;
        userDao.insertItem(userBean);

        reqUser.setValue(userBean);

        LogUtils.w("user : " + userBean.toString());
    }
//
//    private MutableLiveData<Map<String, String>> reqTeam = new MutableLiveData();
//
//    public LiveData<BaseDatas<List<TestData>>> getTest() {
//        return Transformations.switchMap(reqTeam, input -> mApi.getTest());
//    }
//
//    public LiveData<Resource<BaseDatas<List<TestData>>>> getTest2() {
//        return Transformations.switchMap(reqTeam, input -> mRepository.getTest2());
//    }
//
//    public void reqTest() {
//        Map<String, String> map = new LinkedHashMap<>();
//        map.put("name", "李白");
//        reqTeam.postValue(map);
//    }

}
