package com.xia.community.provider;

import com.alibaba.fastjson.JSON;
import com.xia.community.dto.AccessTokenDTO;
import com.xia.community.dto.GitHubUser;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.springframework.stereotype.Component;


import java.io.IOException;
import java.util.Objects;

@Component
@Slf4j
public class GitHubProvider {

    public String getAccessToken(AccessTokenDTO accessTokenDTO) {
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");

        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(JSON.toJSONString(accessTokenDTO), mediaType);
        Request request = new Request.Builder()
                .url("https://github.com/login/oauth/access_token")
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String string = response.body().string();
            String token = string.split("&")[0].split("=")[1];
            return token;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public GitHubUser getGitHubUser(String accessToken) {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://api.github.com/user?access_token=" + accessToken)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String userStr = response.body().string();
            return JSON.parseObject(userStr, GitHubUser.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
