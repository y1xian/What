package com.yyxnb.what.localservice.manager;


import com.yyxnb.what.localservice.bean.LocalFolder;

import java.util.ArrayList;


/**
 * 数据回调
 */
public interface DataCallback {


    void onData(ArrayList<LocalFolder> list);

}
