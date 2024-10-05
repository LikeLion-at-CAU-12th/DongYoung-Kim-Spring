package com.example.likelion.repository;

import com.example.likelion.entity.Article;
import com.example.likelion.entity.CategoryArticle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryArticleJpaRepository extends JpaRepository<CategoryArticle, Long> {
    List<CategoryArticle> findByArticle(Article article);
    void deleteAllByArticle(Article article);
}
