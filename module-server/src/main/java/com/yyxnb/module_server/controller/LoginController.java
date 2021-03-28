package com.yyxnb.module_server.controller;

import com.yanzhenjie.andserver.annotation.PostMapping;
import com.yanzhenjie.andserver.annotation.RequestBody;
import com.yanzhenjie.andserver.annotation.RequestMapping;
import com.yanzhenjie.andserver.annotation.RequestParam;
import com.yanzhenjie.andserver.annotation.RestController;
import com.yyxnb.module_server.bean.JsonResultVo;
import com.yyxnb.module_server.bean.request.LoginDto;
import com.yyxnb.module_server.bean.response.CodeVo;
import com.yyxnb.module_server.bean.response.LoginVo;
import com.yyxnb.module_server.bean.response.UserVo;
import com.yyxnb.module_server.constants.UserLevel;
import com.yyxnb.module_server.db.CodeDao;
import com.yyxnb.module_server.db.ServerUserDatabase;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.crypto.SecureUtil;

/**
 * ================================================
 * 作    者：yyx
 * 日    期：2021/03/13
 * 描    述：登录
 * ================================================
 */
@RestController
@RequestMapping(path = "/login")
public class LoginController extends BaseController {

    private final CodeDao codeDao = ServerUserDatabase.getInstance().codeDao();

    /**
     * 手机号登录
     *
     * @param dto
     * @return
     */
    @PostMapping("/phoneLogin")
    public JsonResultVo<UserVo> login(@RequestBody LoginDto dto) {

        LoginVo loginVo = new LoginVo();
        if (codeDao.findCode(dto.getPhone(), dto.getCode())) {
            // 清掉该条验证码
            codeDao.deleteCode(dto.getPhone());
            loginInfo(loginVo, dto.getPhone(), false);
        } else {
            return JsonResultVo.failure("验证码不存在或已失效");
        }
        UserVo userVo = userDao.getUser(loginVo.getToken());
        return JsonResultVo.success(userVo);
    }

    /**
     * 游客登录
     *
     * @return
     */
    @PostMapping("/visitorLogin")
    public JsonResultVo<UserVo> visitorLogin() {
        LoginVo loginVo = new LoginVo();
        userDao.deleteAllVisitor();
        loginInfo(loginVo, "199" + RandomUtil.randomNumbers(8), true);

        UserVo userVo = userDao.getUser(loginVo.getToken());
        return JsonResultVo.success(userVo);
    }

    /**
     * 获取验证码
     *
     * @param phone
     * @return
     */
    @PostMapping("/verificationCode")
    public JsonResultVo<String> verificationCode(@RequestParam("phone") String phone) {

        String code = RandomUtil.randomNumbers(4);

        codeDao.insertItem(new CodeVo(phone, code));

        return JsonResultVo.successData(code);
    }

    private void loginInfo(LoginVo loginVo, String phone, boolean isVisitorLogin) {

        UserVo userVo = userDao.getUser(phone);

        if (BeanUtil.isEmpty(userVo)) {
            userVo = new UserVo();
            userVo.setPhone(phone);
            userVo.setUserId("uid_" + Math.abs(phone.hashCode()));
            userVo.setNickName("用户-" + RandomUtil.randomString(6));
            userVo.setSignature("笑死，签名压根没个性");
            userVo.setCreateTime(DateUtil.now());
        }
        // 每次登陆都刷新token
        userVo.setToken(SecureUtil.md5(DateUtil.current() + "-token-" + phone));

        if (isVisitorLogin) {
            userVo.setUserLevel(UserLevel.VISITOR.getLevel());
        }
        userVo.setLastTime(DateUtil.now());
        userDao.insertItem(userVo);

        loginVo.setToken(userVo.getToken());
    }

}
