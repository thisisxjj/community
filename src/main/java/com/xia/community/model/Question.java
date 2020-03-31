package com.xia.community.model;

import lombok.Builder;
import lombok.Data;

/**
 * @author thisXjj
 * @date 2020/3/31 9:17 下午
 */
@Data
@Builder
public class Question {
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
}
