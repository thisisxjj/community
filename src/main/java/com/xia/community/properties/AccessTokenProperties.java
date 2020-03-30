package com.xia.community.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(value = "github")
@Data
public class AccessTokenProperties {

    private String clientId;

    private String clientSecret;

    private String redirectUri;

}
