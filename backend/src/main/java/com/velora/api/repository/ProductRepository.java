package com.velora.api.repository;

import com.velora.api.entity.Product;
import java.util.*;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
  Optional<Product> findBySlug(String slug);

  List<Product> findByActiveTrueOrderByCreatedAtDesc();

  List<Product> findByFeaturedTrueAndActiveTrue();
}
