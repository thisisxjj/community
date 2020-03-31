package com.xia.community.controller;

import com.xia.community.dto.AccessTokenDTO;
import com.xia.community.model.User;
import com.xia.community.properties.AccessTokenProperties;
import com.xia.community.service.AOuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Controller
@Slf4j
public class AuthorizeController {

    @Resource
    private AccessTokenProperties accessTokenProperties;

    @Resource
    private AOuthService aOuthService;

    @GetMapping("/callback")
    public String callback(@RequestParam("code") String code,
                           @RequestParam("state") String state,
                           HttpServletResponse response) {
        AccessTokenDTO accessTokenDTO = AccessTokenDTO.builder()
                .client_id(accessTokenProperties.getClientId())
                .client_secret(accessTokenProperties.getClientSecret())
                .redirect_uri(accessTokenProperties.getRedirectUri())
                .code(code)
                .state(state)
                .build();
        User user = aOuthService.gitHubAOuth(accessTokenDTO);
        if (user != null) {
            response.addCookie(new Cookie("token", user.getToken()));
        }
        return "redirect:/";
    }
}
