package com.example.study.controller;

import com.example.study.service.MemberService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class PdfController {
    private final MemberService memberService;

    public PdfController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/generatePdf")
    public String generatePdf(@RequestParam("id") Long id) {
        try {
            memberService.generatePdfById(id);
            return "PDF generated successfully!";
        } catch (IOException e) {
            e.printStackTrace();
            return "Error generating PDF: " + e.getMessage();
        }
    }
}
