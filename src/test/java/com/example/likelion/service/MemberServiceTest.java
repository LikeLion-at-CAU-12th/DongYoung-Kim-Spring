package com.example.likelion.service;

import com.example.likelion.entity.Member;
import com.example.likelion.repository.MemberJpaRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MemberServiceTest {

    @Autowired
    MemberService memberService;

    @Autowired
    MemberJpaRepository memberJpaRepository;

    private final Random random = new Random();

    @BeforeEach
    void setUp() {
        IntStream.range(0, 100).forEach(i -> {
            String username = "user" + random.nextInt(10000);
            String email = "user" + random.nextInt(10000) + "@example.com";
            int age = random.nextInt(60 - 18 + 1) + 18;
            Member member = Member.builder()
                    .username(username)
                    .email(email)
                    .age(age)
                    .build();
            memberJpaRepository.save(member);
        });
    }

    @Test
    void printMembersByPage() {
        memberService.printMembersByPage(0, 5);
        System.out.println("---");
        memberService.printMembersByPage(1, 5);
        System.out.println("---");
        memberService.printMembersByPage(0, 10);
    }

    @Test
    void getMembersByAgeGreaterThanEqual20() {
        List<Member> results = memberService.getMembersByAgeGreaterThanEqual20(0, 10);

        for (Member member : results) {
            Assertions.assertThat(member.getAge()).isGreaterThanOrEqualTo(20);
            System.out.println("ID: " + member.getId() + ", username: " + member.getUsername() + ", age: " + member.getAge());
        }
    }

    @Test
    void getMembersByNameStartingWith() {
        String prefix = "user12";
        List<Member> results = memberService.getMembersByNameStartingWith(prefix, 0, 10);

        for (Member member : results) {
            Assertions.assertThat(member.getUsername()).startsWith(prefix);
            System.out.println("ID: " + member.getId() + ", username: " + member.getUsername() + ", age: " + member.getAge());
        }
    }
}