package com.example.likelion.service;

import com.example.likelion.entity.Member;
import com.example.likelion.repository.MemberJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final MemberJpaRepository memberRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest oAuth2UserRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(oAuth2UserRequest);

        String email = oAuth2User.getAttribute("email");
        String username = oAuth2User.getAttribute("name");
        System.out.println("oAuth2UserRequest: " + oAuth2UserRequest);
        System.out.println("oAuth2User: " + oAuth2User);
        System.out.println("email: " + email);
        System.out.println("username: " + username);

        Member member = memberRepository.findByEmail(email)
                .orElseGet(() -> Member.builder()
                        .email(email)
                        .username(username)
                        .password("")
                        .build());

        if (member.getId() != null) {
            member.changeUsername(username);
        }

        memberRepository.save(member);

        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")),
                oAuth2User.getAttributes(),
                "sub"
        );
    }
}
