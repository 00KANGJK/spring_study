package com.example.study.controller;

import com.example.study.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class MemberController {

    private final MemberService memberService;
    @Autowired  //spring container에서 가져옴
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }
}
