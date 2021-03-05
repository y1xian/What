package com.yyxnb.module_server.controller;

import com.yanzhenjie.andserver.annotation.Addition;
import com.yanzhenjie.andserver.annotation.GetMapping;
import com.yanzhenjie.andserver.annotation.RequestMapping;
import com.yanzhenjie.andserver.annotation.RequestParam;
import com.yanzhenjie.andserver.annotation.RestController;
import com.yanzhenjie.andserver.http.HttpRequest;
import com.yanzhenjie.andserver.http.HttpResponse;
import com.yanzhenjie.andserver.http.cookie.Cookie;
import com.yanzhenjie.andserver.http.session.Session;
import com.yanzhenjie.andserver.util.MediaType;
import com.yyxnb.module_server.component.LoginInterceptor;
import com.yyxnb.module_server.model.UserInfo;

import cn.hutool.core.util.StrUtil;

import static com.yyxnb.module_server.component.LoginInterceptor.LOGIN_TYPE;

@RestController()
@RequestMapping(path = "/user")
public class UserController {


    @GetMapping(path = "/login", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    String login(HttpRequest request, HttpResponse response, @RequestParam("username") String account,
                 @RequestParam("password") String password) {
        if (StrUtil.isNotBlank(account)) {
//        if ("123".equals(account) && "123".equals(password)) {

            Session session = request.getValidSession();
            // 有效时间
            session.setMaxInactiveInterval(10);
            session.setAttribute(LoginInterceptor.LOGIN_ATTRIBUTE, true);

            Cookie cookie = new Cookie("username", account);
            cookie.setMaxAge(10);
            response.addCookie(cookie);

            return "Login successful. " + String.format(" %s  %s  %s  %s",
                    cookie.getName(), cookie.getValue(), session.getLastAccessedTime(), session.getMaxInactiveInterval());
        } else {
            return "Login failed.";
        }
    }

    @Addition(stringType = LOGIN_TYPE, booleanType = true)
    @GetMapping(path = "/get", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    UserInfo userInfo() {

//        Session session = request.getSession();
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId("1");
        userInfo.setUserName("123");
//        userInfo.setToken("" + session.getMaxInactiveInterval());
        return userInfo;
    }

//    @PostMapping(path = "/upload", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
//    String upload(@RequestParam(name = "avatar") MultipartFile file) throws IOException {
//        File localFile = FileUtils.createRandomFile(file);
//        file.transferTo(localFile);
//        return localFile.getAbsolutePath();
//    }
}
