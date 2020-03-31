package com.xia.community.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author thisXjj
 * @date 2020/3/31 8:06 下午
 */
@Controller
public class PublishController {
    @GetMapping("publish")
    public String publish() {
        return "publish";
    }
}
