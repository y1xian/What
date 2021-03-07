package com.yyxnb.module_user.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.yyxnb.common_base.core.CommonViewModel;
import com.yyxnb.common_res.bean.UserBean;
import com.yyxnb.common_res.db.AppDatabase;
import com.yyxnb.common_res.db.UserDao;

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
