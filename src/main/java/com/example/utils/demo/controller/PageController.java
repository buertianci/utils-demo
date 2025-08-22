package com.example.utils.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 页面控制器
 */
@Controller
public class PageController {
    
    /**
     * 抽奖页面
     */
    @GetMapping("/lottery")
    public String lottery() {
        return "lottery.html";
    }
    
    /**
     * 首页重定向到抽奖页面
     */
    @GetMapping("/")
    public String index() {
        return "redirect:/lottery";
    }
} 