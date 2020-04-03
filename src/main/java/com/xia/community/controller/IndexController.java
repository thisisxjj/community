package com.xia.community.controller;

import com.xia.community.dto.Pagination;
import com.xia.community.service.QuestionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;

@Controller
public class IndexController {
    @Resource
    private QuestionService questionService;
    @GetMapping("/")
    public String index(Model model,
                        @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                        @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize) {
        // 循环遍历问题表，获取问题list
        Pagination pagination = questionService.list(pageNum, pageSize);
        model.addAttribute("pagination", pagination);
        return "index";
    }
}
