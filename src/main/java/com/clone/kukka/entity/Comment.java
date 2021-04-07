package com.clone.kukka.entity;

import com.clone.kukka.dto.CommentRequestDto;
import com.clone.kukka.dto.UserDto;
import com.clone.kukka.jwt.JwtTokenProvider;
import com.clone.kukka.service.ProductService;
import com.clone.kukka.service.UserService;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import javax.persistence.*;

@Getter // get 함수를 일괄적 생
@NoArgsConstructor // 기본 생성자를 만들어줍니다.
@Entity // DB 테이블 역할을 합니다.
//@Table 이게 꼭 있어야하는지 찾아보
public class Comment extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

//    @ManyToOne(fetch = FetchType.EAGER)
//    @JoinColumn(name = "USER_NAME")
//    private User user;
    @Column(name="USERNAME")
    private String username;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "PRODUCT_ID")
    private Product product;

    @Column(name = "content", nullable = false)
    private String content;

    public Comment(CommentRequestDto requestDto, ProductService productService, UserDetails userDetails) {
        this.username = userDetails.getUsername();
        this.product = productService.findById(requestDto.getProductId());
        this.content = requestDto.getContent();
    }

//    public Comment(String user, Long product, String content, ProductService productService, UserService userService) {
//        this.user = userService.findByUserId(user);
//        this.product = productService.findById(product);
//        this.content = content;
//    }
}
