package com.example.likelion.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CommentCreateRequestDto {
    private Long memberId;
    private Long articleId;
    private String content;
}
