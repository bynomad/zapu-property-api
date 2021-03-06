package com.example.zapupropertyapi.controller;

import com.example.zapupropertyapi.model.Category;
import com.example.zapupropertyapi.service.category.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CategoryControllerTest {
    @InjectMocks
    private CategoryController categoryController;

    @Mock
    private CategoryService categoryService;

    Category category;

    @BeforeEach
    public void init() {
        category = new Category();
        category.setId(Short.valueOf("1"));
        category.setName("konut");
    }

    @Test
    public void shouldGetAllCategories() {
        ResponseEntity<List<Category>> response = categoryController.getAllCategories();
        verify(categoryService).getAllCategories();

        assertEquals(response.getStatusCode(), HttpStatus.OK);
    }

    @Test
    public void shouldCreateCategory() {
        ResponseEntity<Category> response = categoryController.createCategory(category);
        verify(categoryService).createCategory(category);

        assertEquals(response.getStatusCode(), HttpStatus.OK);
    }
}