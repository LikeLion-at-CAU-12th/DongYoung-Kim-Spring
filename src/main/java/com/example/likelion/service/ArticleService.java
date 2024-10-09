package com.example.likelion.service;

import com.example.likelion.dto.request.ArticleCreateRequestDto;
import com.example.likelion.dto.request.ArticleUpdateRequestDto;
import com.example.likelion.dto.response.ArticleResponseDto;
import com.example.likelion.entity.*;
import com.example.likelion.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class ArticleService {
    @Autowired
    private MemberJpaRepository memberRepository;
    @Autowired
    private ArticleJpaRepository articleRepository;
    @Autowired
    private CategoryArticleJpaRepository categoryArticleRepository;
    @Autowired
    private ArticleLogJpaRepository articleLogRepository;
    @Autowired
    private CategoryJpaRepository categoryRepository;

    @Transactional
    public Long createArticle(ArticleCreateRequestDto requestDto) {
        Member member = memberRepository.findById(requestDto.getMemberId())
                .orElseThrow(() -> new RuntimeException("해당 ID를 가진 회원이 존재하지 않습니다."));
        Article article = Article.builder()
                .title(requestDto.getTitle())
                .content(requestDto.getContent())
                .member(member)
                .comments(new ArrayList<>())
                .build();
        articleRepository.save(article);

        ArticleLog articleLog = ArticleLog.builder()
                .title(requestDto.getTitle())
                .content(requestDto.getContent())
                .article(article)
                .build();
        articleLogRepository.save(articleLog);

        List<Long> categoryIds = requestDto.getCategoryIds();
        if (categoryIds != null && !categoryIds.isEmpty()) {
            for (Long categoryId : categoryIds) {
                Category category = categoryRepository.findById(categoryId)
                        .orElseThrow(() -> new RuntimeException("해당 ID를 가진 카테고리가 없습니다."));

                CategoryArticle categoryArticle = CategoryArticle.builder()
                        .category(category)
                        .article(article)
                        .build();

                categoryArticleRepository.save(categoryArticle);
            }
        }
        return article.getId();
    }

    public List<ArticleResponseDto> findArticlesByMemberId(Long memberId) {
        List<Article> articles = articleRepository.findByMemberId(memberId);
        return articles.stream()
                .map(article -> new ArticleResponseDto(article.getId(), article.getTitle(), article.getContent()))
                .toList();
    }

    @Transactional
    public ArticleResponseDto updateArticle(Long articleId, ArticleUpdateRequestDto requestDto) {
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new RuntimeException("해당 ID를 가진 글이 없습니다."));

        if (requestDto.getTitle() != null) {
            article.updateTitle(requestDto.getTitle());
        }
        if (requestDto.getContent() != null) {
            article.updateContent(requestDto.getContent());
        }
        if (requestDto.getCategoryIds() != null) {
            categoryArticleRepository.deleteAllByArticle(article);

            List<Long> categoryIds = requestDto.getCategoryIds();
            if (!categoryIds.isEmpty()) {
                for (Long categoryId : categoryIds) {
                    Category category = categoryRepository.findById(categoryId)
                            .orElseThrow(() -> new RuntimeException("해당 ID를 가진 카테고리가 없습니다."));

                    CategoryArticle categoryArticle = CategoryArticle.builder()
                            .category(category)
                            .article(article)
                            .build();

                    categoryArticleRepository.save(categoryArticle);
                }
            }
        }
        articleRepository.save(article);

        ArticleLog articleLog = ArticleLog.builder()
                .title(article.getTitle())
                .content(article.getContent())
                .article(article)
                .build();
        articleLogRepository.save(articleLog);

        return new ArticleResponseDto(article.getId(), article.getTitle(), article.getContent());
    }

    @Transactional
    public void deleteArticle(Long articleId) {
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new RuntimeException("해당 ID를 가진 글이 없습니다."));

        articleRepository.deleteById(article.getId());
    }
}
