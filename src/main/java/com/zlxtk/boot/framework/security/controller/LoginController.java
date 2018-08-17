package com.zlxtk.boot.framework.security.controller;

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
        return "index";
    }

    @RequestMapping("/login")
    public String login(Model model) {
        return "login";
    }

    @RequestMapping("/index")
    public String index(Model model) {
        return "index";
    }

    @RequestMapping("/admin")
    public String admin(Model model) {
        return "admin/admin";
    }
}
