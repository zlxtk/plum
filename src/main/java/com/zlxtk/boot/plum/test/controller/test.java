package com.zlxtk.boot.plum.test.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Description:
 * @Auther: tangyake
 * @Date: 2018/8/10 15:44
 */
@Controller
@RequestMapping("/test")
public class test {

    /**
     * 模板
     * http://localhost:8080/plum/test/starter#
     * @param model
     * @return
     */
    @RequestMapping("/starter")
    public String home(Model model) {
        model.addAttribute("message", "hahahaaaaaa");
        return "test/starter";
    }
}
