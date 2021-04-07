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

    @Column(name = "mainImage", length = 500)
    private String mainImage;

    @Column(name = "summary", length = 200)
    private String summary;

    @Column(name = "flower", length = 200)
    private String flower;

    @Column(name = "per", length = 100)
    private String per;

    @Column(name = "oldPrice", length = 100)
    private String oldPrice;

    @Column(name = "price", length = 100)
    private String price;

    @Column(name = "notice", length = 300)
    private String notice;

    @Column(name = "imageDetail", length = 500)
    private String imageDetail;

    @Column(name = "titleDetail", length = 500)
    private String titleDetail;

    @Column(name = "contentsDetail", length = 1000)
    private String contentsDetail;

}
