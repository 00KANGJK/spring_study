package com.example.study.repository;

import com.example.study.domain.Member;

import java.util.*;

public class MemoryMemberRepository implements MemberRepository{
    private  static Map<Long, Member> store = new HashMap<>();
    private  static long sequence = 0L; // key값을 자동으로 만들어줌

    @Override
    public Member save(Member member) {
        member.setId(++sequence);
        store.put(member.getId(),member);
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        return Optional.ofNullable(store.get(id)); // null값이 반환되었을 때를 대비함, Optional.ofNullable
    }

    @Override
    public Optional<Member> findByName(String name) {
        return store.values().stream()
                .filter(member -> member.getName().equals(name)) //name과 같은 것을 찾는 것
                .findAny(); // 같은 거 단 한개라도 가져오기
    }

    @Override
    public List<Member> findAll() {
        return new ArrayList<>(store.values());
    }

    public void clearStore() {
        store.clear();
    }
}
