package com.xia.community.controller;

import com.xia.community.mapper.UserMapper;
import com.xia.community.model.User;
import com.xia.community.service.AOuthService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class IndexController {
    @Resource
    private AOuthService aOuthService;

    @GetMapping("/")
    public String index(HttpServletRequest request) {
        // 访问主页后通过Cookie判断该用户是否已经存入数据库中，如果存入则判断以登录状态
        Cookie[] cookies = request.getCookies();
        User user = aOuthService.loginCookie(cookies);
        // 设置session状态
        request.getSession().setAttribute("user", user);
        return "index";
    }
}
