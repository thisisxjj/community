package com.xia.community.service;

import com.xia.community.dto.AccessTokenDTO;
import com.xia.community.model.User;

import javax.servlet.http.Cookie;

/**
 * @author thisXjj
 * @date 2020/3/31 7:19 下午
 */
public interface AOuthService {
    /**
     * gitHub鉴权
     * @param accessTokenDTO
     * @return
     */
    User gitHubAOuth(AccessTokenDTO accessTokenDTO);

    User loginCookie(Cookie[] cookies);
}
