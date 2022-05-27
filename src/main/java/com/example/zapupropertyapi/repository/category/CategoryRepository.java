package com.example.zapupropertyapi.repository.category;

import com.example.zapupropertyapi.model.Category;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends MongoRepository<Category, Integer> {
    Category findCategoryById(short id);
}
