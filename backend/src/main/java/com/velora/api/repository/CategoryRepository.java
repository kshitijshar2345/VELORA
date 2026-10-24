package com.velora.api.repository;
import com.velora.api.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
public interface CategoryRepository extends JpaRepository<Category, Long> { Optional<Category> findBySlug(String slug); }
