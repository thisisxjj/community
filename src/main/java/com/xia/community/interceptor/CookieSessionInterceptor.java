package com.xia.community.interceptor;

import com.xia.community.mapper.UserMapper;
import com.xia.community.model.User;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author thisXjj
 * @date 2020/4/3 2:25 下午
 */
@Component
public class CookieSessionInterceptor implements HandlerInterceptor {
    @Resource
    private UserMapper userMapper;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Cookie[] cookies = request.getCookies();
        if (cookies != null && cookies.length > 0) {
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
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
