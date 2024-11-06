package com.example.likelion.repository;

import com.example.likelion.entity.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
class MemberJpaRepositoryTest {

    @Autowired
    MemberJpaRepository memberJpaRepository;

    @Test
    @Transactional
    public void testMember() {
        Member member = Member.builder()
                .username("memberA")
                .email("memberA@naver.com")
                .build();

        Member savedMember = memberJpaRepository.save(member);
        Member findMember = memberJpaRepository.findById(savedMember.getId()).orElse(null);

        Assertions.assertThat(findMember).isNotNull();
        Assertions.assertThat(findMember.getId()).isEqualTo(member.getId());
        Assertions.assertThat(findMember).isEqualTo(member);
    }

    @Test
    @Transactional
    public void testFindAll() {
        Member member1 = Member.builder()
                .username("member1")
                .email("member1@naver.com")
                .build();
        Member member2 = Member.builder()
                .username("member2")
                .email("member2@naver.com")
                .build();
        Member member3 = Member.builder()
                .username("member3")
                .email("member3@naver.com")
                .build();

        memberJpaRepository.save(member1);
        memberJpaRepository.save(member2);
        memberJpaRepository.save(member3);

        List<Member> members = memberJpaRepository.findAll();

        for (Member member : members) {
            System.out.println(member.getId() + " " + member.getUsername());
        }

        Assertions.assertThat(members).hasSize(3);
    }

    @Test
    @Transactional
    public void testFindAllByUsername() {
        Member member = Member.builder()
                .username("memberA")
                .email("memberA@naver.com")
                .build();

        memberJpaRepository.save(member);

        List<Member> byUsername = memberJpaRepository.findAllByUsername(member.getUsername());

        for (Member member1 : byUsername) {
            System.out.println(member1.getId());
        }

        Assertions.assertThat(byUsername).isNotEmpty();
    }

}