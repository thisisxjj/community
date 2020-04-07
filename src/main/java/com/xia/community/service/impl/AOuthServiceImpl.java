package com.xia.community.service.impl;

import com.xia.community.dto.AccessTokenDTO;
import com.xia.community.dto.GitHubUser;
import com.xia.community.exception.CustomizeErrorCode;
import com.xia.community.exception.UserException;
import com.xia.community.mapper.UserMapper;
import com.xia.community.model.User;
import com.xia.community.provider.GitHubProvider;
import com.xia.community.service.AOuthService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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
                    .avatarUrl(gitHubUser.getAvatarUrl())
                    .build();
            // 将user对象持久化到数据库中
            Integer row = createOrUpdate(user);
            if (row < 1) {
                throw new UserException(CustomizeErrorCode.USER_CREATE_OR_UPDATE_ERROR);
            }
            return user;
        }
        return null;
    }

    private Integer createOrUpdate(User user) {
        User dbUser = userMapper.selectByAccountId(Integer.valueOf(user.getAccountId()));
        Integer row;
        // 如果从数据库中查询的user不为空，则更新数据库的user
        if (dbUser != null) {
            dbUser.setAvatarUrl(user.getAvatarUrl());
            dbUser.setBio(user.getBio());
            dbUser.setName(user.getName());
            dbUser.setToken(user.getToken());
            dbUser.setGmtModified(System.currentTimeMillis());
            row = userMapper.update(dbUser);
        } else {
            dbUser.setGmtCreate(System.currentTimeMillis());
            dbUser.setGmtModified(System.currentTimeMillis());
            row = userMapper.insert(user);
        }
        return row;
    }


}
