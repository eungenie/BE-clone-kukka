package com.clone.kukka.controller;

import com.clone.kukka.dto.CommentRequestDto;
import com.clone.kukka.entity.Comment;
import com.clone.kukka.repository.CommentRepository;
import com.clone.kukka.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class CommentController {

    private final CommentRepository commentRepository;
    private final CommentService commentService;

    @GetMapping("/api/products/{id}")
    public List<Comment> readComment(@PathVariable Long id){
        return commentRepository.findAllByProductId(id);

    }

    @PostMapping("/api/products/{id}")
    public Comment createComment(@PathVariable Long id, @RequestBody CommentRequestDto requestDto) {
//        Comment comment = new Comment(requestDto);
        return commentService.saveComment(id, requestDto);

    }

//    // 댓글을 작성하는 기능입니다.
//    @PostMapping("/post/read/{memoId}/comment")
//    public Comment createComment(@PathVariable Long memoId, @RequestBody CommentRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
//        requestDto.setMemoId(memoId);
//        requestDto.setUserId(userDetails.getUser().getId());
//        Comment comment = commentService.createComment(requestDto);
//        return comment;
//    }

//    @PutMapping("/api/products/{id}")
//    public Long updateProduct(@PathVariable Long id, @RequestBody ProductMypriceRequestDto requestDto) {
//        return productService.update(id, requestDto);
//    }
}
