package com.clone.kukka.repository;

import com.clone.kukka.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface ProductRepository extends JpaRepository<Product, Long> {
}
