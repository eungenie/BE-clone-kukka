package com.clone.kukka.service;

import com.clone.kukka.dto.CommentRequestDto;
import com.clone.kukka.entity.Comment;
import com.clone.kukka.entity.Product;
import com.clone.kukka.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final ProductService productService;
    /*
    서비스에서 다룰 내용
    - 수정
     */

//    public CommentService(CommentRepository commentRepository) {
//        this.commentRepository = commentRepository;
//    }

    public Comment saveComment(Long id, CommentRequestDto requestDto) {

        Comment comment = new Comment(requestDto, productService);
        commentRepository.save(comment);
        return comment;
    }

//    // 댓글 생성 기능
//    public Comment createComment(CommentRequestDto requestDto) {
//        Comment comment = new Comment(requestDto, memoService, userService);
//        commentRepository.save(comment);
//        return comment;
//    }

//    public Long saveComment(Product product, CommentRequestDto requestDto) {
//        productId = product.getId();
//        Comment comment = commentRepository.save(productId, requestDto);
////        commentRepository.save(requestDto);
//        return comment;
//    }
}
