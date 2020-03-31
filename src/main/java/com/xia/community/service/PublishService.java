package com.xia.community.service;

import com.xia.community.model.User;

/**
 * @author thisXjj
 * @date 2020/3/31 9:51 下午
 */
public interface PublishService {
    /**
     * 发布问题
     * @param title
     * @param description
     * @param tag
     * @param user
     */
    void create(String title, String description, String tag, User user);
}
