package com.zss.login.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author fuguozhang
 * @email fuguozhang@jyblife.com
 * @date 2019/10/28 10:24
 */
@Controller
@Slf4j
public class RouterController {

    @GetMapping("login.html")
    public String login(){
        return "login";
    }

    @GetMapping("index.html")
    public String index(){
        return "index";
    }

    @GetMapping("/")
    public String _login(){
        return "login";
    }
}
