package com.xia.community.service;

import com.xia.community.dto.Pagination;

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
}
