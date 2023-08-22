package com.example.study.service;

import com.example.study.domain.Member;
import com.example.study.repository.MemberRepository;
import com.example.study.repository.MemoryMemberRepository;

import java.util.List;
import java.util.Optional;

public class MemberService {
    private final MemberRepository memberRepository = new MemoryMemberRepository();
//    회원가입
    public Long join (Member member){
        valigatDuplicatMember(member); //중복 회원 검증

        memberRepository.save(member);
        return member.getId();
    }

    private void valigatDuplicatMember(Member member) {
        memberRepository.findByName(member.getName())
            .ifPresent(m ->{
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        });
    }
//전체 회원 조회
    public List<Member> findMembers(){
        return memberRepository.findAll();
    }
    //
    public Optional<Member> findOne(Long memberId){
        return memberRepository.findById(memberId);
    }
}
