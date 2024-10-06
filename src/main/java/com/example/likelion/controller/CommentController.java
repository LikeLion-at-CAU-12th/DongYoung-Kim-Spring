package com.example.likelion.controller;

import com.example.likelion.dto.request.CommentCreateRequestDto;
import com.example.likelion.dto.request.CommentUpdateRequestDto;
import com.example.likelion.dto.response.CommentResponseDto;
import com.example.likelion.entity.Comment;
import com.example.likelion.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping
    public ResponseEntity<Long> createComment(@RequestBody CommentCreateRequestDto requestDto) {
        Long commentId = commentService.createComment(requestDto);
        return ResponseEntity.ok(commentId);
    }

    @GetMapping("/article/{articleId}")
    public ResponseEntity<List<CommentResponseDto>> getCommentsByArticleId(@PathVariable Long articleId) {
        List<CommentResponseDto> comments = commentService.getCommentsByArticleId(articleId);
        if (comments.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(comments);
    }

    @PatchMapping("/{commentId}")
    public ResponseEntity<CommentResponseDto> updateComment(@PathVariable Long commentId, @RequestBody CommentUpdateRequestDto requestDto) {
        CommentResponseDto comment = commentService.updateComment(commentId, requestDto);
        return ResponseEntity.ok(comment);
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long commentId) {
        commentService.deleteComment(commentId);
        return ResponseEntity.ok().build();
    }
}
