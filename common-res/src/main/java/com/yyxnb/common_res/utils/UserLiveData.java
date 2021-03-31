package com.yyxnb.common_res.utils;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;

import com.yyxnb.common_base.event.SingleLiveEvent;
import com.yyxnb.common_res.bean.UserVo;
import com.yyxnb.common_res.constants.Constants;
import com.yyxnb.common_res.db.AppDatabase;
import com.yyxnb.common_res.db.UserDao;
import com.yyxnb.what.cache.KvUtils;
import com.yyxnb.what.core.log.LogUtils;

import cn.hutool.core.util.StrUtil;

public class UserLiveData extends SingleLiveEvent<UserVo> {

    private static volatile UserLiveData mInstance = null;

    private UserLiveData() {
    }

    public static UserLiveData getInstance() {
        if (null == mInstance) {
            synchronized (UserLiveData.class) {
                if (null == mInstance) {
                    mInstance = new UserLiveData();
                }
            }
        }
        return mInstance;
    }

    private final UserDao userDao = AppDatabase.getInstance().userDao();

    private final SingleLiveEvent<String> reqUser = new SingleLiveEvent<>();

    public LiveData<UserVo> getUser() {
        return Transformations.switchMap(reqUser, userDao::getUser);
    }

    public void loginOut() {
        String token = KvUtils.get(Constants.USER_TOKEN, "loginOut");
        reqUser.postValue(token);
    }

    public void reqUser() {
        String token = KvUtils.get(Constants.USER_TOKEN, "reqUser");
        reqUser.postValue(token);
    }

    @Override
    protected void onActive() {
        super.onActive();
        String token = KvUtils.get(Constants.USER_TOKEN, "");
        if (StrUtil.isNotBlank(token)) {
            reqUser.postValue(token);
        }
        LogUtils.w(String.format("token %s", token));
    }

    @Override
    protected void onInactive() {
        super.onInactive();
        LogUtils.w(String.format("onInactive %s", 1));
    }
}
