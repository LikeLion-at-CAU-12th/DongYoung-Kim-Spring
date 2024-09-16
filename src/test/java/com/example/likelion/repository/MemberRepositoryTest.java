package com.example.likelion.repository;

import com.example.likelion.entity.Member;
import com.example.likelion.exception.CustomException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @PersistenceContext
    EntityManager em;

    @BeforeEach
    void setUp() {
        Member member = Member.builder()
                .username("jane Doe")
                .email("janeDoe@email.com")
                .build();
        em.persist(member);
        em.flush();
    }

    @Test
    @DisplayName("이메일이 중복된 경우 회원 가입 실패")
    void email_duplicated() {
        Member member = Member.builder()
                .username("jane Doe2")
                .email("janeDoe@email.com")
                .build();
        assertThrows(CustomException.class, () -> memberRepository.save(member));
    }
}