package com.yyxnb.module_user.viewmodel;

import com.yyxnb.common_base.base.CommonViewModel;
import com.yyxnb.common_res.utils.UserLiveData;
import com.yyxnb.common_res.db.AppDatabase;
import com.yyxnb.common_res.db.UserDao;

public class UserViewModel extends CommonViewModel {

    private final UserDao userDao = AppDatabase.getInstance().userDao();

    public UserLiveData userLiveData = UserLiveData.getInstance();

//    public MutableLiveData<Integer> reqUserId = new MutableLiveData<>();
//
//    public LiveData<UserVo> getUser() {
//        return Transformations.switchMap(reqUserId, userDao::getUser);
//    }
//
//    public LiveData<UserVo> getUser(int userId) {
//        return userDao.getUser(userId);
//    }

}
