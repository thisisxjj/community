package com.xia.community.controller;

import com.xia.community.dto.AccessTokenDTO;
import com.xia.community.dto.GitHubUser;
import com.xia.community.provider.GitHubProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;

@Controller
@Slf4j
public class AuthorizeController {
    @Resource
    private GitHubProvider gitHubProvider;

    @GetMapping("/callback")
    public String callback(@RequestParam("code") String code,
                           @RequestParam("state") String state) {
        AccessTokenDTO accessTokenDTO = AccessTokenDTO.builder()
                .client_id("a2e2d3b76a74c7df85a3")
                .client_secret("3edb53d09c0978baa55c7c14d0b75c21fde2e6d1")
                .redirect_uri("http://localhost:8080/callback")
                .code(code)
                .state(state)
                .build();
        String token = gitHubProvider.getAccessToken(accessTokenDTO);
        GitHubUser user = gitHubProvider.getGitHubUser(token);
        log.info("GitHubUser => {}", user.getName());
        return "index";
    }
}
