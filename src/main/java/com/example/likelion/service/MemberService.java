package com.example.likelion.service;

import com.example.likelion.dto.request.JoinRequest;
import com.example.likelion.entity.Member;
import com.example.likelion.repository.MemberJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberJpaRepository memberJpaRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public Page<Member> getMembersByPage(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("username").ascending());
        return memberJpaRepository.findAll(pageable);
    }

    public void printMembersByPage(int page, int size) {
        Page<Member> memberPage = getMembersByPage(page, size);
        List<Member> members = memberPage.getContent();

        for (Member member : members) {
            System.out.println("ID: " + member.getId() + ", username: " + member.getUsername());
        }
    }

    public List<Member> getMembersByAgeGreaterThanEqual20(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("username").ascending());
        Page<Member> memberPage = memberJpaRepository.findByAgeGreaterThanEqual(20, pageable);
        return memberPage.getContent();
    }

    public List<Member> getMembersByNameStartingWith(String prefix, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Member> memberPage = memberJpaRepository.findByUsernameStartingWith(prefix, pageable);
        return memberPage.getContent();
    }

    public void join(JoinRequest joinRequest) {
        if (memberJpaRepository.existsByUsername(joinRequest.getUsername())) {
            return;
        }

        Member member = Member.builder()
                .username(joinRequest.getUsername())
                .password(bCryptPasswordEncoder.encode(joinRequest.getPassword()))
                .email(joinRequest.getEmail())
                .build();

        memberJpaRepository.save(member);
    }
}
