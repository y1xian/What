package com.yyxnb.module_user.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;

import com.yyxnb.common_base.base.CommonViewModel;
import com.yyxnb.common_base.bean.UserBean;
import com.yyxnb.common_base.db.AppDatabase;
import com.yyxnb.common_base.db.UserDao;

public class UserViewModel extends CommonViewModel {

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
