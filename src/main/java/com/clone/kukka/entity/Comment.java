package com.clone.kukka.entity;

import com.clone.kukka.dto.CommentRequestDto;
import com.clone.kukka.repository.ProductRepository;
import com.clone.kukka.service.ProductService;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter // get 함수를 일괄적 생
@NoArgsConstructor // 기본 생성자를 만들어줍니다.
@Entity // DB 테이블 역할을 합니다.
//@Table 이게 꼭 있어야하는지 찾아보
public class Comment extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name="USERNAME", nullable = false)
    private String username;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "PRODUCT_ID", nullable = false)
    private Product product;

    @Column(name = "content", nullable = false)
    private String content;

    public Comment(CommentRequestDto requestDto, ProductService productService) {
        this.username = requestDto.getUsername();
        this.product = productService.findById(requestDto.getProductId());
        this.content = requestDto.getContent();
    }
}
