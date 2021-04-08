package com.clone.kukka.repository;

import com.clone.kukka.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    //상품별 댓글 찾아서 불러오기
    List<Comment> findByProductIdOrderByModifiedAtDesc(Long productId);

    //특정 댓글 삭제
    void deleteById(Long Id);
}
