package com.xia.community.service.impl;

import com.xia.community.dto.AccessTokenDTO;
import com.xia.community.dto.GitHubUser;
import com.xia.community.mapper.UserMapper;
import com.xia.community.model.User;
import com.xia.community.provider.GitHubProvider;
import com.xia.community.service.AOuthService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import java.util.UUID;

/**
 * @author thisXjj
 * @date 2020/3/31 7:25 下午
 */
@Service
public class AOuthServiceImpl implements AOuthService {
    @Resource
    private UserMapper userMapper;
    @Resource
    private GitHubProvider gitHubProvider;

    @Override
    public User gitHubAOuth(AccessTokenDTO accessTokenDTO) {
        // 携带code向access_token接口发送post请求
        String token = gitHubProvider.getAccessToken(accessTokenDTO);
        // 携带获取的access_token向user接口发送get请求，并获取user对象
        GitHubUser gitHubUser = gitHubProvider.getGitHubUser(token);
        // 进行存储session存储登录用户信息,登录成功写入user对象，登录失败写入null
//        session.setAttribute("user", user);
        // 自己制作cookie用作身份识别，保证登录状态持久化保存
        if (gitHubUser != null && gitHubUser.getId() != null) {
            User user = User.builder()
                    .accountId(String.valueOf(gitHubUser.getId()))
                    .name(gitHubUser.getName())
                    .token(UUID.randomUUID().toString())
                    .bio(gitHubUser.getBio())
                    .gmtCreate(System.currentTimeMillis())
                    .gmtModified(System.currentTimeMillis())
                    .avatarUrl(gitHubUser.getAvatarUrl())
                    .build();
            // 将user对象持久化到数据库中
            int row = userMapper.insert(user);
            if (row <= 0) {
                throw new RuntimeException("将user插入数据库失败");
            }
            return user;
        }
        return null;
    }

    @Override
    public User loginCookie(Cookie[] cookies) {
        if (cookies != null && cookies.length > 0) {
            for (Cookie cookie : cookies) {
                // 判断cookie的key是否是token
                if ("token".equals(cookie.getName())) {
                    // 如果是token，查询数据库中是否含有cookie的value也就是数据库中的token字段的uuid
                    User user = userMapper.selectByToken(cookie.getValue());
                    // 判断user是否为null，如果为null，则判断没有登录过，需要登录
                    if (user != null) {
                        return user;
                    }
                }
            }
        }
        return null;
    }
}
