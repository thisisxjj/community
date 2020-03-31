package com.xia.community.controller;

import com.xia.community.mapper.UserMapper;
import com.xia.community.pojo.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class IndexController {
    @Resource
    private UserMapper userMapper;

    @GetMapping("/")
    public String index(HttpServletRequest request) {
        // 访问主页后通过Cookie判断该用户是否已经存入数据库中，如果存入则判断以登录状态
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            // 判断cookie的key是否是token
            if ("token".equals(cookie.getName())) {
                // 如果是token，查询数据库中是否含有cookie的value也就是数据库中的token字段的uuid
                User user = userMapper.selectByToken(cookie.getValue());
                // 判断user是否为null，如果为null，则判断没有登录过，需要登录
                if (user != null) {
                    request.getSession().setAttribute("user", user);
                }
                break;
            }
        }
        return "index";
    }
}
