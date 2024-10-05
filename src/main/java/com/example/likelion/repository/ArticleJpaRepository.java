package com.example.likelion.repository;

import com.example.likelion.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArticleJpaRepository extends JpaRepository<Article, Long> {
    List<Article> findByMemberId(Long memberId);
}
