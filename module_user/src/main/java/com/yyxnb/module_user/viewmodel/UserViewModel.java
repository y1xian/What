package com.yyxnb.module_user.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;

import com.yyxnb.common_base.bean.UserBean;
import com.yyxnb.common_base.db.AppDatabase;
import com.yyxnb.common_base.db.UserDao;
import com.yyxnb.lib_network.BaseViewModel;

public class UserViewModel extends BaseViewModel {

    private UserDao userDao = AppDatabase.getInstance().userDao();

    public MutableLiveData<Integer> reqUserId = new MutableLiveData<>();

    public LiveData<UserBean> getUser() {
        return Transformations.switchMap(reqUserId, input -> {
            return userDao.getUser(input);
        });
    }

    public LiveData<UserBean> getUser(int userId) {
        return userDao.getUser(userId);
    }

}
