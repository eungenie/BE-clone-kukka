package com.clone.kukka.controller;

import com.clone.kukka.entity.Product;
import com.clone.kukka.repository.ProductRepository;
import com.clone.kukka.service.CommentService;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

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
}
