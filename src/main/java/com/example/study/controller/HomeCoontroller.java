package com.example.study.controller;

import com.example.study.domain.Member;
import com.example.study.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeCoontroller {
    private final MemberService memberService;
    @Autowired  //spring container에서 가져옴
    public HomeCoontroller(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/")
    public String home(){

        return "home";
    }
}
