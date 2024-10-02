package com.example.likelion.repository;

import com.example.likelion.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberJpaRepository extends JpaRepository<Member, Long> {
    List<Member> findByUsername(String username);
    Page<Member> findByAgeGreaterThanEqual(int age, Pageable pageable);
    Page<Member> findByUsernameStartingWith(String prefix, Pageable pageable);
}
