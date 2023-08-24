package com.example.study.service;

import com.example.study.domain.Member;
import com.example.study.repository.MemberRepository;
import com.example.study.repository.MemoryMemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional // test를 위해 넣은 data는 db에 영향을 안준다.
class MemberServiceIntegrationTest {
    @Autowired MemberService memberService;
    @Autowired
    MemberRepository memberRepository;

    @Test
    void 회원가입() {
        //given
        Member member = new Member();
        member.setName("spring1");
        //when
        Long saveID = memberService.join(member);
        //then
        Member findMember = memberService.findOne(saveID).get();
        Assertions.assertEquals(member.getName(), findMember.getName());

    }
    @Test
    void 중복_회원_검사(){
        //given
        Member member1 = new Member();
        member1.setName("spring2");

        Member member2 = new Member();
        member2.setName("spring2");
        //when
        memberService.join(member1);
        IllegalStateException e = assertThrows(IllegalStateException.class,
                () -> memberService.join(member2));//예외가 발생해야 한다.
        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
    }
}