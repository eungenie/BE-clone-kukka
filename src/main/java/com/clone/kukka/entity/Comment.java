package com.clone.kukka.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter // get 함수를 일괄적 생
@NoArgsConstructor // 기본 생성자를 만들어줍니다.
@Entity // DB 테이블 역할을 합니다.
//@Table 이게 꼭 있어야하는지 찾아보
public class Comment extends Timestamped{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    //Product의 id와 연결 필요
    @Column(name="product_id", nullable = false)
    private String productId;

    @Column(nullable = false)
    private String content;
}
