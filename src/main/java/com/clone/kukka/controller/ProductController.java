package com.clone.kukka.controller;

import com.clone.kukka.entity.Comment;
import com.clone.kukka.entity.Product;
import com.clone.kukka.repository.ProductRepository;
import com.clone.kukka.service.CommentService;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequiredArgsConstructor
public class ProductController {

    private final ProductRepository productRepository;

    @GetMapping("/api/products")
    public List<Product> getProducts() {
        return productRepository.findAll();
    }

//    Detail에 Product(1개) 뿌려줄 때, 해당하는 Comment들도 같이 뿌려주기
//    @GetMapping("/api/products/{productId}")
//    public Product getProductDetail(@PathVariable Long productId) {
//        return productRepository.findById(productId).orElseThrow(
//                () -> new NullPointerException("해당하는 상품이 존재하지 않습니다.")
//        );
//    }

//    위에 합치기
//    @ResponseBody
//    @GetMapping("/api/products/{productId}")
//    public List<Comment> readComment(@PathVariable Long productId) {
//        return commentRepository.findByProductIdOrderByModifiedAtDesc(productId);
//    }
}
