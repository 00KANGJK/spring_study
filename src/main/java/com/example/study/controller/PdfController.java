package com.example.study.controller;

import com.example.study.domain.Member;
import com.example.study.service.MemberService;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
public class PdfController {
    private final MemberService memberService;

    public PdfController(MemberService memberService) {
        this.memberService = memberService;
    }

    private static final Logger logger = LoggerFactory.getLogger(PdfController.class);
    private static final Logger downloadLogger = LoggerFactory.getLogger("DownloadLogger");

    @GetMapping("/generatePdf")
    public String generatePdf(@RequestParam("id") Long id) {
        Optional<Member> members = memberService.findId(id);
        Member member = null;
        if(members.isPresent()){
            member = members.get();
        }

        try {
            memberService.generatePdfById(id);
            downloadLogger.info("현재 이 pdf를 다운로드 했습니다. data 번호 :" +member.getStudentid()+" "+member.getCname());
            return "redirect:/";
        } catch (IOException e) {
            e.printStackTrace();
            return "redirect:/error";
        }
    }
}
