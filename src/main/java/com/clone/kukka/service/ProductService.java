package com.clone.kukka.service;

import com.clone.kukka.entity.Product;
import com.clone.kukka.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    // Comment
    public Product findById(Long id) {
        return productRepository.findById(id).orElseThrow(
                () -> new RuntimeException("USER_ID가 존재하지 않습니다."));
    }
}
