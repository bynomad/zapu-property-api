package com.example.zapupropertyapi.controller;

import com.example.zapupropertyapi.model.Category;
import com.example.zapupropertyapi.service.category.CategoryService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CategoryControllerTest {
    @InjectMocks
    private CategoryController categoryController;

    @Mock
    private CategoryService categoryService;

    @Test
    public void shouldGetAllCategories(){
        ResponseEntity<List<Category>> response = categoryController.getAllCategories();
        verify(categoryService).getAllCategories();

        assertEquals(response.getStatusCode(), HttpStatus.OK);
    }
}