package com.xia.community.service.impl;

import com.xia.community.mapper.QuestionMapper;
import com.xia.community.model.Question;
import com.xia.community.model.User;
import com.xia.community.service.PublishService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author thisXjj
 * @date 2020/3/31 10:01 下午
 */
@Service
public class PublishServiceImpl implements PublishService {
    @Resource
    private QuestionMapper questionMapper;

    @Override
    public void create(String title, String description, String tag, User user) {
        Question question = Question.builder()
                .title(title)
                .description(description)
                .tag(tag)
                .creator(user.getId())
                .gmtCreate(System.currentTimeMillis())
                .gmtModified(System.currentTimeMillis())
                .build();
        questionMapper.create(question);
    }
}
