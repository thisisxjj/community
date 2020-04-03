package com.xia.community.controller;

import com.xia.community.model.User;
import com.xia.community.service.PublishService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author thisXjj
 * @date 2020/3/31 8:06 下午
 */
@Controller
@RequestMapping("publish")
public class PublishController {
    @Resource
    private PublishService publishService;
    @GetMapping
    public String publish() {
        return "publish";
    }
    @PostMapping
    public String doPublish(
            @RequestParam(value = "title",required = false) String title,
            @RequestParam(value = "description",required = false) String description,
            @RequestParam(value = "tag",required = false) String tag,
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
        // 如果是登录状态，则将发布的问题插入到数据库中
        publishService.create(title, description, tag, user);
        return "redirect:/";
    }
}
