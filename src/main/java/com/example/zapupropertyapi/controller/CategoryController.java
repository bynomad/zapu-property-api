package com.example.zapupropertyapi.controller;

import com.example.zapupropertyapi.model.Category;
import com.example.zapupropertyapi.service.category.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping(value = "categories")
    public ResponseEntity<List<Category>> getAllCategories() {
        return ResponseEntity.ok(categoryService.getAllCategories());
    }

    @PostMapping(value = "createCategory")
    public ResponseEntity<Category> createCategory(@RequestBody Category category) {
        return ResponseEntity.ok().body(categoryService.createCategory(category));
    }

    // for amazon aws purpose
    @GetMapping(value = "/index")
    public ResponseEntity<String> index() {
        String deneme = "Welcome to Zapu";
        return ResponseEntity.ok(deneme);
    }
}
