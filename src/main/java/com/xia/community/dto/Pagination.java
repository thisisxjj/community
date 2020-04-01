package com.xia.community.dto;

import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author thisXjj
 * @date 2020/4/1 7:38 下午
 */
// 分页
@Data
@Builder
public class Pagination {
    private Integer currentPage; // 当前页
    private Integer totalPage; // 总页数
    private Integer totalCount; // 总记录条数
    private Integer pageSize; // 每页显示的条数
    private Integer size; // 当前页实际条数
    private List<QuestionDTO> questionList; // 问题列表
    private List<Integer> pages; // 显示的页数集合

    private Boolean showPrevious; // 展示上一页
    private Boolean showFirst; // 展示第一页
    private Boolean showNext; // 展示下一页
    private Boolean showEnd; // 展示最后一页

    public void setPagination(Integer pageNum, Integer pageSize, Integer totalCount, Integer totalPage) {
        this.totalCount = totalCount;
        // 如果totalCount模上pageSize 等于0 totalPage等于totalCount/pageSize
        this.totalPage = totalPage;
        this.currentPage = pageNum <= this.totalPage ? pageNum : this.totalPage;
        this.pageSize = pageSize;
        if (currentPage < this.totalPage) {
            if (totalCount < pageSize) {
                this.size = totalCount;
            } else {
                this.size = pageSize;
            }
        } else {
            this.size = totalCount % pageSize;
        }
        this.pages = new ArrayList<>();
        pages.add(currentPage);
        for (int i = 1; i <= 3; i++) {
            if (currentPage - i > 0) {
                pages.add(0,currentPage - i);
            }
            if (currentPage + i <= this.totalPage) {
                pages.add(currentPage + i);
            }
        }
        // 如果当前页是1，则不显示上一页
        this.showPrevious = this.currentPage != 1;
        // 如果当前页是最后一页，则不显示下一页
        this.showNext = this.currentPage != this.totalPage;
        // 如果pages中有第1页，则不显示回到第一页按钮
        this.showFirst = !this.pages.contains(1);
        // 如果pages中有最后一页，则不显示回到最后一页
        this.showEnd = !this.pages.contains(this.totalPage);
    }
}
