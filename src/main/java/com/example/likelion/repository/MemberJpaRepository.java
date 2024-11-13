package com.example.likelion.repository;

import com.example.likelion.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MemberJpaRepository extends JpaRepository<Member, Long> {
    Member findByUsername(String username);
    List<Member> findAllByUsername(String username);
    Page<Member> findByAgeGreaterThanEqual(int age, Pageable pageable);
    Page<Member> findByUsernameStartingWith(String prefix, Pageable pageable);
    boolean existsByUsername(String username);
    Optional<Member> findByEmail(String email);
}
