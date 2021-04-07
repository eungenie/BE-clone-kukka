package com.clone.kukka.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CommentRequestDto {

    private String userId;
    private String content;
    private Long productId;

}
