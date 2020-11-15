package com.yyxnb.common_base.base;

import com.yyxnb.common_base.bean.MsgData;
import com.yyxnb.lib_network.BaseViewModel;
import com.yyxnb.lib_network.SingleLiveEvent;

public class CommonViewModel extends BaseViewModel {
    public final SingleLiveEvent<MsgData> msgEvent = new SingleLiveEvent<>();
}
