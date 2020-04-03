package com.xia.community.controller;

import com.xia.community.dto.Pagination;
import com.xia.community.model.User;
import com.xia.community.service.QuestionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author thisXjj
 * @date 2020/4/2 8:10 下午
 */
@Controller
public class ProfileController {
    @Resource
    private QuestionService questionService;
    @GetMapping("/profile/{action}")
    public String profile(@PathVariable("action") String action,
                          @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                          @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
                          HttpServletRequest request,
                          Model model) {

        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            return "redirect:/";
        }

        if ("questions".equals(action)) {
            model.addAttribute("section", action);
            model.addAttribute("sectionName", "我的提问");
            Pagination pagination = questionService.list(user, pageNum, pageSize);
            model.addAttribute("pagination", pagination);
        } else if ("replies".equals(action)) {
            model.addAttribute("section", action);
            model.addAttribute("sectionName", "最新回复");
        }
        return "profile";
    }
}
