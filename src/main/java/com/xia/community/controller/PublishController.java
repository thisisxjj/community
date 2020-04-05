package com.xia.community.controller;

import com.xia.community.dto.QuestionDTO;
import com.xia.community.model.Question;
import com.xia.community.model.User;
import com.xia.community.service.QuestionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author thisXjj
 * @date 2020/3/31 8:06 下午
 */
@Controller
public class PublishController {
    @Resource
    private QuestionService questionService;
    @GetMapping("publish")
    public String publish() {
        return "publish";
    }
    @GetMapping("/publish/{id}")
    public String edit(@PathVariable("id") Integer id,
                       Model model) {
        QuestionDTO question = questionService.selectOne(id);
        model.addAttribute("title", question.getTitle());
        model.addAttribute("description", question.getDescription());
        model.addAttribute("tag", question.getTag());
        model.addAttribute("id", question.getId());
        return "publish";
    }
    @PostMapping("publish")
    public String doPublish(
            @RequestParam(value = "title", required = false) String title,
            @RequestParam(value = "description", required = false) String description,
            @RequestParam(value = "tag", required = false) String tag,
            @RequestParam(value = "questionId", required = false) Integer id,
            HttpServletRequest request,
            Model model) {
        model.addAttribute("title", title);
        model.addAttribute("description", description);
        model.addAttribute("tag", tag);
        if (title == null || title.length() == 0) {
            model.addAttribute("error", "问题标题不能为空");
            return "publish";
        }
        if (description == null || description.length() == 0) {
            model.addAttribute("error", "问题补充不能为空");
            return "publish";
        }
        if (tag == null || tag.length() == 0) {
            model.addAttribute("error", "标签不能为空");
            return "publish";
        }
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            model.addAttribute("error", "您还未登录，请先登录");
            return "redirect:/";
        }
        Question question = Question.builder()
                .title(title)
                .description(description)
                .tag(tag)
                .id(id)
                .creator(user.getId())
                .build();
        // 如果是登录状态，则将发布的问题插入到数据库中
        questionService.createOrUpdate(question);
        return "redirect:/";
    }
}
