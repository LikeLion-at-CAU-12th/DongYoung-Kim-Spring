package com.example.likelion.repository;

import com.example.likelion.entity.Article;
import com.example.likelion.entity.ArticleLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ArticleLogJpaRepository extends JpaRepository<ArticleLog, Long> {
    Optional<ArticleLog> findByArticle(Article article);
    void deleteAllByArticle(Article article);
}
