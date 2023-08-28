package com.example.study.service;

import com.example.study.domain.Member;
import com.example.study.repository.MemberRepository;
import com.example.study.repository.MemoryMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;

@Transactional
public class MemberService {
    private final MemberRepository memberRepository;


    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    //    회원가입
    public Long join (Member member){
        memberRepository.save(member);
        return member.getId();
    }


//전체 회원 조회
    public List<Member> findMembers(){
        return memberRepository.findAll();
    }
    //
    public Optional<Member> findName(String memberName){
        return memberRepository.findByName(memberName);
    }  //이름 검색 기능

    public Optional<Member> findId(Long id){ return memberRepository.findById(id); };

    public void generatePdfById(Long id) throws IOException {
        try {
            Class.forName("org.h2.Driver");
            Connection connection = DriverManager.getConnection(
                    "jdbc:h2:tcp://localhost/~/test",
                    "sa",
                    "");

            Statement statement = connection.createStatement();
            String sql = "SELECT name, studentid,department,cname,startdate,enddate FROM member WHERE id = " + id;
            ResultSet resultSet = statement.executeQuery(sql);

            if (resultSet.next()) {

                PDDocument document = new PDDocument();
                PDPage first = new PDPage(PDRectangle.A4);
                document.addPage(first);

                PDImageXObject backgroundImage = PDImageXObject.createFromFile("src/main/resources/static/img/back.jpg", document);
                try (PDPageContentStream contents = new PDPageContentStream(document, first, PDPageContentStream.AppendMode.APPEND, true, true)) {
                    contents.drawImage(backgroundImage, 0, 0, first.getMediaBox().getWidth(), first.getMediaBox().getHeight());

                    PDType0Font font = PDType0Font.load(document, new File("src/main/resources/static/font/malgunbd.ttf"));
                    contents.beginText();
                    contents.setFont(font, 14);
                    String name = resultSet.getString("name");
                    String studentId = resultSet.getString("studentid");
                    String department = resultSet.getString("department");
                    String cname = resultSet.getString("cname");
                    String startDate = resultSet.getString("startdate");
                    String endDate = resultSet.getString("enddate");


                    contents.newLineAtOffset(80, 500);
                    contents.showText("이  름: " + name);
                    contents.newLineAtOffset(0, -24);
                    contents.showText("학  번: " + studentId);
                    contents.newLineAtOffset(0, -24);
                    contents.showText("학  부: " + department);
                    contents.newLineAtOffset(0, -24);
                    contents.showText("캠프명: " + cname);
                    contents.newLineAtOffset(0, -24);
                    contents.showText("캠프기간: " + startDate + " ~ " + endDate);
                    contents.newLineAtOffset(40, -80);

                    contents.setFont(font, 18);
                    contents.showText("위 학생은 위의 교육과정을 수료하였음으므로");
                    contents.newLineAtOffset(80, -24);
                    contents.showText("이 증서를 수여합니다.");

                    contents.endText();


                }

                String cname = resultSet.getString("cname");
                String studentId = resultSet.getString("studentid");
                String downloadsPath = System.getProperty("user.home") + "/Downloads";
                document.save(downloadsPath + "/"+studentId+"_"+cname+" 수료증.pdf");


                System.out.println("PDF Created");
                document.close();

            } else {
                System.out.println("No member with ID " + id + " found.");
            }
            connection.close();
        }catch (ClassNotFoundException | SQLException e){
            e.printStackTrace();
        }
    }
}
