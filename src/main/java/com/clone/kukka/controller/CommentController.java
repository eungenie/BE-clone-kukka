package com.clone.kukka.controller;

import com.clone.kukka.dto.CommentRequestDto;
import com.clone.kukka.entity.Comment;
import com.clone.kukka.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @ResponseBody
    @PostMapping("/api/products/{productId}")
    public Comment createComment(@PathVariable Long productId, @RequestBody CommentRequestDto requestDto) {
        requestDto.setProductId(productId);
        return commentService.createComment(requestDto);
    }
}