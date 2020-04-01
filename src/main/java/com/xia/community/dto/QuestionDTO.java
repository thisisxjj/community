package com.xia.community.dto;

import com.xia.community.model.User;
import lombok.Builder;
import lombok.Data;

/**
 * @author thisXjj
 * @date 2020/4/1 11:01 上午
 */
@Data
@Builder
public class QuestionDTO {
    private Integer id;
    private String title;
    private String description;
    private Long gmtCreate;
    private Long gmtModified;
    private Integer creator;
    private Integer commentCount;
    private Integer viewCount;
    private Integer likeCount;
    private String tag;
    private User user;
}
