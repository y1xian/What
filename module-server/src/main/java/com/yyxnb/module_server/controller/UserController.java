package com.yyxnb.module_server.controller;

import com.yanzhenjie.andserver.annotation.PostMapping;
import com.yanzhenjie.andserver.annotation.RequestHeader;
import com.yanzhenjie.andserver.annotation.RequestMapping;
import com.yanzhenjie.andserver.annotation.RequestParam;
import com.yanzhenjie.andserver.annotation.RestController;
import com.yyxnb.module_server.bean.JsonResultVo;
import com.yyxnb.module_server.bean.response.UserVo;
import com.yyxnb.module_server.constants.HeaderKeys;

import cn.hutool.core.bean.BeanUtil;

/**
 * ================================================
 * 作    者：yyx
 * 日    期：2021/03/13
 * 描    述：用户管理
 * ================================================
 */
@RestController
@RequestMapping(path = "/user")
public class UserController extends BaseController {

    /**
     * 用户信息
     *
     * @param token
     * @return
     */
    @PostMapping("/userInfo")
    public JsonResultVo<UserVo> userInfo(@RequestHeader(HeaderKeys.TOKEN) String token) {

        UserVo userVo = userDao.getUser(token);
        return JsonResultVo.success(userVo);
    }

    /**
     * 退出登录
     *
     * @param userId
     * @return
     */
    @PostMapping("/loginOut")
    public JsonResultVo<String> loginOut(@RequestParam("userId") String userId) {
        UserVo userVo = userDao.getUserById(userId);
        if (BeanUtil.isEmpty(userVo)) {
            return JsonResultVo.failure("用户不存在");
        }
        userVo.setToken(null);
        userDao.updateItem(userVo);
        return JsonResultVo.success();
    }

}
