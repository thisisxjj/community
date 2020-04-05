package com.xia.community.service;

import com.xia.community.dto.Pagination;
import com.xia.community.dto.QuestionDTO;
import com.xia.community.model.Question;
import com.xia.community.model.User;

/**
 * @author thisXjj
 * @date 2020/4/1 11:01 上午
 */
public interface QuestionService {
    /**
     * 展示问题列表
     * @return List<QuestionDTO>
     * @param pageNum
     * @param pageSize
     */
    Pagination list(Integer pageNum, Integer pageSize);

    /**
     * 展示个人问题列表
     * @param user
     * @param pageNum
     * @param pageSize
     * @return
     */
    Pagination list(User user, Integer pageNum, Integer pageSize);

    /**
     * 展示问题细节
     * @param id
     * @return
     */
    QuestionDTO selectOne(Integer id);

    /**
     * 创建或更新问题
     * @param question
     */
    void createOrUpdate(Question question);
}
