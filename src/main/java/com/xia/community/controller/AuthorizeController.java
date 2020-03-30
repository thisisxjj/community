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

@Controller
@Slf4j
public class AuthorizeController {
    @Resource
    private GitHubProvider gitHubProvider;
    @Resource
    private AccessTokenProperties accessTokenProperties;
    @GetMapping("/callback")
    public String callback(@RequestParam("code") String code,
                           @RequestParam("state") String state) {
        AccessTokenDTO accessTokenDTO = AccessTokenDTO.builder()
                .client_id(accessTokenProperties.getClientId())
                .client_secret(accessTokenProperties.getClientSecret())
                .redirect_uri(accessTokenProperties.getRedirectUri())
                .code(code)
                .state(state)
                .build();
        String token = gitHubProvider.getAccessToken(accessTokenDTO);
        GitHubUser user = gitHubProvider.getGitHubUser(token);
        log.info("GitHubUser => {}", user.getName());
        return "index";
    }
}
