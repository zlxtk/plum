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
    public String home(Model model) {
        model.addAttribute("message", "hahahaaaaaa");
        return "index";
    }

    @RequestMapping("/login")
    public String login(Model model) {
        return "login";
    }

    @RequestMapping("/index")
    public String index(Model model) {
        model.addAttribute("message", "hahaha");
        return "index";
    }

    @RequestMapping("/admin")
    public String admin(Model model) {
        model.addAttribute("message", "admin");
        return "admin";
    }
}
