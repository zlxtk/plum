package com.zlxtk.boot.plum.security.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Description:
 * @Auther: tangyake
 * @Date: 2018/8/1 18:08
 */
@Controller
public class LoginController {

    @RequestMapping("/")
    public String index(Model model) {
        model.addAttribute("message", "hahaha");
        return "index";
    }
}
