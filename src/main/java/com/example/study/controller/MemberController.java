package com.example.study.controller;

import com.example.study.domain.Member;
import com.example.study.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class MemberController {

    private final MemberService memberService;
    @Autowired  //spring container에서 가져옴
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/members/new")
    public String createForm(){
        return "members/creatMemberForm";
    }

    @PostMapping("/members/new")
    public String create(MemberForm form){
        Member member = new Member();
        member.setName(form.getName());
        member.setStudentid(form.getStudentid());
        member.setCname(form.getCname());
        member.setDepartment(form.getDepartment());
        member.setStartdate(form.getStartdate());
        member.setEnddate(form.getEnddate());

        memberService.join(member);

        return "redirect:/";
    }

}
