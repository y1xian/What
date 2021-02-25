package com.yyxnb.lib_localservice.manager;


import com.yyxnb.lib_localservice.bean.LocalFolder;

import java.util.ArrayList;


/**
 * 数据回调
 */
public interface DataCallback {


    void onData(ArrayList<LocalFolder> list);

}
