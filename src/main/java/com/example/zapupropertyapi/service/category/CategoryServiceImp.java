package com.example.zapupropertyapi.service.category;

import com.example.zapupropertyapi.model.Category;
import com.example.zapupropertyapi.repository.category.CategoryRepository;
import com.example.zapupropertyapi.service.cache.RedisCacheService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImp implements CategoryService {
    public static final String CATEGORY_CACHE_NAME = "category";
    public static String CATEGORY_CACHE_NAME_ALL = "categories";
    private final CategoryRepository categoryRepository;
    private final RedisCacheService redisCacheService;

    public CategoryServiceImp(CategoryRepository categoryRepository, RedisCacheService redisCacheService) {
        this.categoryRepository = categoryRepository;
        this.redisCacheService = redisCacheService;
    }

    @Override
    public Category createCategory(Category category) {
        Category createdCategory = categoryRepository.save(category);
        putCategoryToCacheAndMergeAllCategoriesCache(createdCategory);
        return createdCategory;
    }

    @Override
    @Cacheable(value = CATEGORY_CACHE_NAME, key = "#id")
    public Category findCategoryById(short id) {
        return categoryRepository.findCategoryById(id);
    }

    @Override
    @Cacheable(value = CATEGORY_CACHE_NAME, key = "#root.target.CATEGORY_CACHE_NAME_ALL")
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    private void putCategoryToCacheAndMergeAllCategoriesCache(Category category) {
        redisCacheService.putValueToCache(CATEGORY_CACHE_NAME, category.getId(), category);
        if (redisCacheService.isKeyAvailable(CATEGORY_CACHE_NAME, CATEGORY_CACHE_NAME_ALL)) {
            redisCacheService.putValueToCache(CATEGORY_CACHE_NAME, CATEGORY_CACHE_NAME_ALL, category);
        }
    }
}
