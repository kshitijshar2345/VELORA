package com.velora.api.controller;

import com.velora.api.entity.Category;
import com.velora.api.repository.CategoryRepository;
import java.util.List;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
  private final CategoryRepository r;

  public CategoryController(CategoryRepository r) {
    this.r = r;
  }

  @GetMapping
  public List<Category> all() {
    return r.findAll();
  }
}
