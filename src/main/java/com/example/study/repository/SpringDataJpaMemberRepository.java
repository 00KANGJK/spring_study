package com.example.study.repository;

import com.example.study.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SpringDataJpaMemberRepository extends JpaRepository<Member,Long>, MemberRepository {
    Optional<Member> findByName(String name);
}
