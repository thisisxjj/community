package com.xia.community.model;

import lombok.Builder;
import lombok.Data;

/**
 * 用户实体类对象
 */
@Data
@Builder
public class User {
    private Integer id;
    private String accountId;
    private String name;
    private String token;
    private String bio;
    private Long gmtCreate;
    private Long gmtModified;
}
