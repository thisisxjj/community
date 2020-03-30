package com.xia.community.controller;

import com.xia.community.dto.AccessTokenDTO;
import com.xia.community.dto.GitHubUser;
import com.xia.community.properties.AccessTokenProperties;
import com.xia.community.provider.GitHubProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

@Controller
@Slf4j
public class AuthorizeController {
    @Resource
    private GitHubProvider gitHubProvider;
    @Resource
    private AccessTokenProperties accessTokenProperties;
    @GetMapping("/callback")
    public String callback(@RequestParam("code") String code,
                           @RequestParam("state") String state,
                           HttpSession session) {
        AccessTokenDTO accessTokenDTO = AccessTokenDTO.builder()
                .client_id(accessTokenProperties.getClientId())
                .client_secret(accessTokenProperties.getClientSecret())
                .redirect_uri(accessTokenProperties.getRedirectUri())
                .code(code)
                .state(state)
                .build();
        // 携带code向access_token接口发送post请求
        String token = gitHubProvider.getAccessToken(accessTokenDTO);
        // 携带获取的access_token向user接口发送get请求，并获取user对象
        GitHubUser user = gitHubProvider.getGitHubUser(token);
        // 进行存储session存储登录用户信息,登录成功写入user对象，登录失败写入null
        session.setAttribute("user", user);
        return "redirect:/";
    }
}
