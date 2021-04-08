package com.clone.kukka.service;

import com.clone.kukka.dto.CommentRequestDto;
import com.clone.kukka.entity.Comment;
import com.clone.kukka.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    //    private final UserDetailsImpl userDetailsImpl;
    //IoC에서 관리하는 의존성이 아니기때문에 별도의 Bean으로 만들거나 해야한다.
    private final ProductService productService;

    public Comment createComment(CommentRequestDto requestDto) {
        Comment comment = new Comment(requestDto, productService);
        commentRepository.save(comment);
        return comment;
    }
}
