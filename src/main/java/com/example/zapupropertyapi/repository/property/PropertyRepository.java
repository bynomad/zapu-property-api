package com.example.zapupropertyapi.repository.property;

import com.example.zapupropertyapi.model.Property;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface PropertyRepository extends MongoRepository<Property, Integer> {
    Page<Property> findByCategoryIdEquals(short categoryId, Pageable pageable);

    Property findPropertyById(Long propertyId);

    Page<Property> findByCategoryIdIn(List<Short> categories, Pageable pageable);

    Page<Property> findByCityIdIn(List<Short> cities, Pageable pageable);

    Page<Property> findByCategoryIdInAndCityIdIn(List<Short> categories, List<Short> cities, Pageable pageable);

    Page<Property> findByCategoryNameEqualsIgnoreCase(String categoryName, Pageable pageable);

    Page<Property> findByCategoryNameEqualsIgnoreCaseAndCityNameEqualsIgnoreCase(String categoryName, String cityName, Pageable pageable);
}
