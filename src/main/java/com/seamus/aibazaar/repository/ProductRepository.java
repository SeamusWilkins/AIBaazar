package com.seamus.aibazaar.repository;

import com.seamus.aibazaar.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {}
