package com.example.likelion.repository;

import com.example.likelion.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentJpaRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByArticleId(Long articleId);
}
