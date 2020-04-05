package com.xia.community.controller;

import com.xia.community.dto.QuestionDTO;
import com.xia.community.model.User;
import com.xia.community.service.QuestionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

/**
 * @author thisXjj
 * @date 2020/4/5 7:41 上午
 */
@Controller
public class QuestionController {
    @Resource
    private QuestionService questionService;
    @GetMapping("question/{id}")
    public String question(@PathVariable("id") Integer id, HttpSession session, Model model) {
        QuestionDTO question = questionService.selectOne(id);
        if (question == null) {
            model.addAttribute("error", "您所查询的问题不存在");
            return "redirect:/";
        }
        // 判断登录用户是否存在，并且登录用户是否是问题创建者
        User user = (User) session.getAttribute("user");
        if (user != null && user.getId().equals(question.getCreator())) {
            model.addAttribute("showEditor", true);
        } else {
            model.addAttribute("showEditor", false);
        }
        model.addAttribute("question", question);
        return "question";
    }
}
