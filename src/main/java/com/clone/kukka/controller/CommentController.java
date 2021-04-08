package com.clone.kukka.controller;

import com.clone.kukka.dto.CommentRequestDto;
import com.clone.kukka.entity.Comment;
import com.clone.kukka.repository.CommentRepository;
import com.clone.kukka.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;
    private final CommentRepository commentRepository;

    // 댓글 작성
    @ResponseBody
    @PostMapping("/api/products/{productId}")
    public Comment createComment(@PathVariable Long productId, @RequestBody CommentRequestDto requestDto) {
        requestDto.setProductId(productId);
        return commentService.createComment(requestDto);
    }

    //상품별 댓글 조회
    @ResponseBody
    @GetMapping("/api/products/{productId}")
    public List<Comment> readComment(@PathVariable Long productId) {
        return commentRepository.findByProductIdOrderByCreatedAtDesc(productId);
    }

    //댓글 삭제
    @ResponseBody
    @DeleteMapping("/api/comments/{commentId}")
    public Long deleteComment(@PathVariable Long commentId) {
        commentRepository.deleteById(commentId);
        return commentId;
    }
}