package com.yyxnb.module_server.controller;

import com.yanzhenjie.andserver.annotation.RequestMapping;
import com.yanzhenjie.andserver.annotation.RestController;
import com.yyxnb.module_server.db.ServerUserDatabase;
import com.yyxnb.module_server.db.UserDao;

/**
 * ================================================
 * 作    者：yyx
 * 日    期：2021/03/14
 * 描    述：封装控制器
 * ================================================
 */
@RestController
@RequestMapping
public class BaseController {

    protected final UserDao userDao = ServerUserDatabase.getInstance().userDao();

}
