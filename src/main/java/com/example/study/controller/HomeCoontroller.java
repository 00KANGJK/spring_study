package com.example.study.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeCoontroller {
    @GetMapping("/")
    public String home(){
        return "home";
    }

}
