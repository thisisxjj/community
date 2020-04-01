package com.xia.community.service;

import com.xia.community.dto.QuestionDTO;

import java.util.List;

/**
 * @author thisXjj
 * @date 2020/4/1 11:01 上午
 */
public interface QuestionService {
    /**
     * 展示问题列表
     * @return List<QuestionDTO>
     */
    List<QuestionDTO> list();
}
