package com.clone.kukka.controller;

import com.clone.kukka.dto.CommentRequestDto;
import com.clone.kukka.entity.Comment;
import com.clone.kukka.jwt.JwtTokenProvider;
import com.clone.kukka.repository.CommentRepository;
import com.clone.kukka.service.CommentService;
import com.clone.kukka.service.ProductService;
import com.clone.kukka.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;
    private final CommentRepository commentRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final ProductService productService;
    private final UserService userService;

//    @GetMapping("/api/products/{id}")
//    public List<Comment> readComment(@PathVariable Long id){
//        return commentRepository.findAllByProductId(id);
//    }

    @PostMapping("/api/products/{productId}")
    public Comment createComment(@PathVariable Long productId, @RequestBody CommentRequestDto requestDto, String token, JwtTokenProvider jwtTokenProvider) {
        String user = jwtTokenProvider.getUserPk(token);
        Long product = productId;
        String content = requestDto.getContent();
        Comment comment = new Comment(user, product, content, productService, userService);
        return commentRepository.save(comment);
    }
}