package com.clone.kukka.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "product")
@Getter
@Setter
@NoArgsConstructor
public class Product {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "main_image", length = 500, nullable = false)
    private String mainImage;

    @Column(name = "summary", length = 200, nullable = false)
    private String summary;

    @Column(name = "flower", length = 200, nullable = false)
    private String flower;

    @Column(name = "per", length = 100, nullable = false)
    private String per;

    @Column(name = "old_price", length = 100, nullable = false)
    private String oldPrice;

    @Column(name = "price", length = 100, nullable = false)
    private String price;

    @Column(name = "notice", length = 300, nullable = false)
    private String notice;

    @Column(name = "image_detail", length = 500, nullable = false)
    private String imageDetail;

    @Column(name = "title_detail", length = 500, nullable = false)
    private String titleDetail;

    @Column(name = "contents_detail", length = 1000, nullable = false)
    private String contentsDetail;

}
