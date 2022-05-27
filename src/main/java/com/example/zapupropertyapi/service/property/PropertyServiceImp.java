package com.example.zapupropertyapi.service.property;

import com.example.zapupropertyapi.dto.PropertySearchModel;
import com.example.zapupropertyapi.model.Property;
import com.example.zapupropertyapi.repository.property.PropertyRepository;
import com.example.zapupropertyapi.service.cache.RedisCacheService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class PropertyServiceImp implements PropertyService {
    public static final String PROPERTY_CACHE_NAME = "property";
    public static final String PROPERTY_SEARCH_CACHE_NAME = "propertySearch";
    private final PropertyRepository propertyRepository;
    private final RedisCacheService redisCacheService;

    public PropertyServiceImp(PropertyRepository propertyRepository, RedisCacheService redisCacheService) {
        this.propertyRepository = propertyRepository;
        this.redisCacheService = redisCacheService;
    }

    @Override
    public Property createProperty(Property property) {
        return saveProperty(property);
    }

    @Override
    @Cacheable(value = PROPERTY_CACHE_NAME, key = "#propertyId")
    public Property findPropertyById(Long propertyId) {
        return propertyRepository.findPropertyById(propertyId);
    }

    @Override
    public Page<Property> getProperties(Pageable pageable) {
        return propertyRepository.findAll(pageable);
    }

    @Override
    public Property updateProperty(Property property) {
        return saveProperty(property);
    }

    @Override
    @Cacheable(value = PROPERTY_SEARCH_CACHE_NAME, key = "#propertySearchModel.cacheKey")
    public Page<Property> findByPropertySearchModel(PropertySearchModel propertySearchModel) {
        if (isUrlSeoFriendly(propertySearchModel)) {
            List<String> paths = Stream.of(propertySearchModel.getPathUrl().substring(1).split("/")).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(paths) && paths.size() <= 2) {
                String categoryName = paths.get(0);
                String cityName = paths.size() == 2 ? paths.get(1) : null;

                if (cityName == null) {
                    return propertyRepository.findByCategoryNameEqualsIgnoreCase(categoryName, propertySearchModel.getPaging());
                } else {
                    return propertyRepository.findByCategoryNameEqualsIgnoreCaseAndCityNameEqualsIgnoreCase(categoryName, cityName, propertySearchModel.getPaging());
                }
            }
        } else {
            List<Short> categories = propertySearchModel.getCategories();
            List<Short> cities = propertySearchModel.getCities();
            if (!CollectionUtils.isEmpty(categories) && CollectionUtils.isEmpty(cities)) {
                return propertyRepository.findByCategoryIdIn(categories, propertySearchModel.getPaging());
            } else if (CollectionUtils.isEmpty(categories) && !CollectionUtils.isEmpty(cities)) {
                return propertyRepository.findByCityIdIn(cities, propertySearchModel.getPaging());
            } else if (!CollectionUtils.isEmpty(categories) && !CollectionUtils.isEmpty(cities)) {
                return propertyRepository.findByCategoryIdInAndCityIdIn(categories, cities, propertySearchModel.getPaging());
            }
        }

        return null;
    }

    private Property saveProperty(Property property) {
        Property savedProperty = propertyRepository.save(property);
        redisCacheService.putValueToCache(PROPERTY_CACHE_NAME, savedProperty.getId(), savedProperty);
        redisCacheService.deleteRelatedSearchCacheKeys(PROPERTY_SEARCH_CACHE_NAME, savedProperty);

        return savedProperty;
    }

    private boolean isUrlSeoFriendly(PropertySearchModel propertySearchModel) {
        return propertySearchModel.getPathUrl() != null;
    }
}
