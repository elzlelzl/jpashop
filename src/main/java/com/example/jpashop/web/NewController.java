package com.example.jpashop.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
public class NewController {
    @RequestMapping("/home")
    public String home() {
        log.info("home controller");
        return "home";
    }
}