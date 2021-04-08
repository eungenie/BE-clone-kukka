package com.clone.kukka.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CommentRequestDto {

    private String username;
    private String content;
    private Long productId;
    private int rate;

}
