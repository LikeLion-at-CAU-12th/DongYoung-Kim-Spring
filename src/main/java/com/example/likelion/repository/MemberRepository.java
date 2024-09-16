package com.example.likelion.repository;

import com.example.likelion.entity.Member;
import com.example.likelion.exception.CustomException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

@Repository
public class MemberRepository {

    @PersistenceContext
    EntityManager em;

    public Long save(Member member) {
        if (findByEmail(member.getEmail()) != null) {
            throw new CustomException("해당 이메일을 가진 유저가 이미 존재합니다.");
        }
        em.persist(member);
        return member.getId();
    }

    public Member findById(Long id) {
        return em.find(Member.class, id);
    }

    public Member findByEmail(String email) {
        try {
            return em.createQuery("select m from Member m where m.email = :email", Member.class)
                    .setParameter("email", email)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
