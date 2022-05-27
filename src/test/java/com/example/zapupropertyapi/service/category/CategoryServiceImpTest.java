package com.example.zapupropertyapi.service.category;

import com.example.zapupropertyapi.model.Category;
import com.example.zapupropertyapi.repository.category.CategoryRepository;
import com.example.zapupropertyapi.service.cache.RedisCacheService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.example.zapupropertyapi.service.category.CategoryServiceImp.CATEGORY_CACHE_NAME;
import static com.example.zapupropertyapi.service.category.CategoryServiceImp.CATEGORY_CACHE_NAME_ALL;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CategoryServiceImpTest {
    @InjectMocks
    private CategoryServiceImp service;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private RedisCacheService redisCacheService;

    private Category category;

    @BeforeEach
    public void init() {
        category = new Category();
        category.setId(Short.valueOf("1"));
        category.setName("Konut");
    }

    @Test
    public void shouldCreateCategory() {
        when(categoryRepository.save(category)).thenReturn(category);
        when(redisCacheService.isKeyAvailable(CATEGORY_CACHE_NAME, CATEGORY_CACHE_NAME_ALL)).thenReturn(true);

        service.createCategory(category);

        verify(categoryRepository).save(category);
        verify(redisCacheService).putValueToCache(CATEGORY_CACHE_NAME, category.getId(), category);
        verify(redisCacheService).putValueToCache(CATEGORY_CACHE_NAME, CATEGORY_CACHE_NAME_ALL, category);
    }

    @Test
    public void shouldGetCategoryById() {
        service.findCategoryById(category.getId());

        verify(categoryRepository).findCategoryById(category.getId());
    }

    @Test
    public void shouldGetAllCategories() {
        service.getAllCategories();

        verify(categoryRepository).findAll();
    }
}