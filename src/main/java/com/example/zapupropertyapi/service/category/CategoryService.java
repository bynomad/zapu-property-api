package com.example.zapupropertyapi.service.category;

import com.example.zapupropertyapi.model.Category;

import java.util.List;

public interface CategoryService {
    List<Category> getAllCategories();

    Category createCategory(Category category);

    Category findCategoryById(short id);
}
