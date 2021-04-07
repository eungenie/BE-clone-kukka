package com.clone.kukka.service;

import com.clone.kukka.dto.CommentRequestDto;
import com.clone.kukka.entity.Comment;
import com.clone.kukka.entity.Product;
import com.clone.kukka.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserService userService;
    private final ProductService productService;

    public Comment createComment(CommentRequestDto requestDto) {
        Comment comment = new Comment(requestDto, productService, userService);
        commentRepository.save(comment);
        return comment;
    }

    public List<Comment> getComments(Long productId) {
        return commentRepository.findByProductId(productId);
    }
}
