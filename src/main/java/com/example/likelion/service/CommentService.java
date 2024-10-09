package com.example.likelion.service;

import com.example.likelion.dto.request.CommentCreateRequestDto;
import com.example.likelion.dto.request.CommentUpdateRequestDto;
import com.example.likelion.dto.response.ArticleResponseDto;
import com.example.likelion.dto.response.CommentResponseDto;
import com.example.likelion.entity.Article;
import com.example.likelion.entity.Comment;
import com.example.likelion.entity.Member;
import com.example.likelion.repository.ArticleJpaRepository;
import com.example.likelion.repository.CommentJpaRepository;
import com.example.likelion.repository.MemberJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CommentService {
    @Autowired
    private CommentJpaRepository commentRepository;
    @Autowired
    private MemberJpaRepository memberRepository;
    @Autowired
    private ArticleJpaRepository articleRepository;

    @Transactional
    public Long createComment(CommentCreateRequestDto requestDto) {
        Member member = memberRepository.findById(requestDto.getMemberId())
                .orElseThrow(() -> new RuntimeException("해당 ID를 가진 회원이 존재하지 않습니다."));
        Article article = articleRepository.findById(requestDto.getArticleId())
                .orElseThrow(() -> new RuntimeException("해당 ID를 가진 글이 존재하지 않습니다."));

        Comment comment = Comment.builder()
                .content(requestDto.getContent())
                .article(article)
                .member(member)
                .build();
        commentRepository.save(comment);
        return comment.getId();
    }

    public List<CommentResponseDto> getCommentsByArticleId(Long articleId) {
        List<Comment> comments = commentRepository.findByArticleId(articleId);
        return comments.stream()
                .map(comment -> new CommentResponseDto(comment.getId(), comment.getMember().getId(), comment.getContent()))
                .toList();
    }

    @Transactional
    public CommentResponseDto updateComment(Long commentId, CommentUpdateRequestDto requestDto) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("해당 ID를 가진 댓글이 없습니다."));

        if (requestDto.getContent() != null) {
            comment.updateContent(requestDto.getContent());
        }
        commentRepository.save(comment);

        return new CommentResponseDto(comment.getId(), comment.getMember().getId(), comment.getContent());
    }

    @Transactional
    public void deleteComment(Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("해당 ID를 가진 댓글이 없습니다."));

        commentRepository.delete(comment);
    }
}
