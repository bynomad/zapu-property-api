package com.example.zapupropertyapi.service.property;

import com.example.zapupropertyapi.dto.PropertySearchModel;
import com.example.zapupropertyapi.model.Category;
import com.example.zapupropertyapi.model.City;
import com.example.zapupropertyapi.model.Property;
import com.example.zapupropertyapi.repository.property.PropertyRepository;
import com.example.zapupropertyapi.service.cache.RedisCacheService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static com.example.zapupropertyapi.service.property.PropertyServiceImp.PROPERTY_CACHE_NAME;
import static com.example.zapupropertyapi.service.property.PropertyServiceImp.PROPERTY_SEARCH_CACHE_NAME;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PropertyServiceImpTest {
    @InjectMocks
    private PropertyServiceImp propertyService;

    @Mock
    private PropertyRepository propertyRepository;
    @Mock
    private RedisCacheService redisCacheService;

    private Property property;

    @BeforeEach
    public void init() {
        property = new Property();
        property.setId(Long.valueOf("1"));
        property.setPrice(BigDecimal.TEN);
        property.setTitle("Property Title");
        property.setCurrency("TL");

        Category category = new Category();
        category.setId(Short.valueOf("1"));
        category.setName("Konut");

        City city = new City();
        city.setId(Short.valueOf("1"));
        city.setName("Ankara");

        property.setCategory(category);
        property.setCity(city);
    }

    @Test
    public void shouldCreateProperty() {
        when(propertyRepository.save(property)).thenReturn(property);

        propertyService.createProperty(property);

        verify(propertyRepository).save(property);
        verify(redisCacheService).putValueToCache(PROPERTY_CACHE_NAME, property.getId(), property);
        verify(redisCacheService).deleteRelatedSearchCacheKeys(PROPERTY_SEARCH_CACHE_NAME, property);
    }

    @Test
    public void shouldUpdateProperty() {
        when(propertyRepository.save(property)).thenReturn(property);

        propertyService.updateProperty(property);

        verify(propertyRepository).save(property);
        verify(redisCacheService).putValueToCache(PROPERTY_CACHE_NAME, property.getId(), property);
        verify(redisCacheService).deleteRelatedSearchCacheKeys(PROPERTY_SEARCH_CACHE_NAME, property);
    }

    @Test
    public void shouldGetPropertyById() {
        propertyService.findPropertyById(property.getId());

        verify(propertyRepository).findPropertyById(property.getId());
    }

    @Test
    public void shouldGetAllProperties() {
        Pageable pageable = Pageable.unpaged();
        propertyService.getProperties(pageable);

        verify(propertyRepository).findAll(pageable);
    }

    @Test
    public void shouldFindByUserFriendlyUrlAndFindByCategoryNameAndCityName() {
        List<Short> categories = Arrays.asList(property.getCategory().getId());
        List<Short> cities = Arrays.asList(property.getCity().getId());

        PropertySearchModel propertySearchModel = PropertySearchModel.builder()
                .categories(categories)
                .cities(cities)
                .pathUrl("/konut/ankara")
                .paging(PageRequest.of(0, 10))
                .build();

        propertyService.findByPropertySearchModel(propertySearchModel);

        verify(propertyRepository).findByCategoryNameEqualsIgnoreCaseAndCityNameEqualsIgnoreCase("konut", "ankara", propertySearchModel.getPaging());

    }

    @Test
    public void shouldFindByUserFriendlyUrlAndFindByCategoryName() {
        List<Short> categories = Arrays.asList(property.getCategory().getId());
        List<Short> cities = Arrays.asList(property.getCity().getId());

        PropertySearchModel propertySearchModel = PropertySearchModel.builder()
                .categories(categories)
                .cities(cities)
                .pathUrl("/konut")
                .paging(PageRequest.of(0, 10))
                .build();

        propertyService.findByPropertySearchModel(propertySearchModel);

        verify(propertyRepository).findByCategoryNameEqualsIgnoreCase("konut", propertySearchModel.getPaging());

    }

    @Test
    public void shouldFindByParameterizedUrlAndFindByCategoryId() {
        List<Short> categories = Arrays.asList(property.getCategory().getId());
        List<Short> cities = null;

        PropertySearchModel propertySearchModel = PropertySearchModel.builder()
                .categories(categories)
                .cities(cities)
                .paging(PageRequest.of(0, 10))
                .build();

        propertyService.findByPropertySearchModel(propertySearchModel);

        verify(propertyRepository).findByCategoryIdIn(categories, propertySearchModel.getPaging());

    }

    @Test
    public void shouldFindByParameterizedUrlAndFindByCityId() {
        List<Short> categories = null;
        List<Short> cities = Arrays.asList(property.getCity().getId());

        PropertySearchModel propertySearchModel = PropertySearchModel.builder()
                .categories(categories)
                .cities(cities)
                .paging(PageRequest.of(0, 10))
                .build();

        propertyService.findByPropertySearchModel(propertySearchModel);

        verify(propertyRepository).findByCityIdIn(cities, propertySearchModel.getPaging());

    }

    @Test
    public void shouldFindByParameterizedUrlAndFindByCategoryIdAndCityId() {
        List<Short> categories = Arrays.asList(property.getCategory().getId());
        List<Short> cities = Arrays.asList(property.getCity().getId());

        PropertySearchModel propertySearchModel = PropertySearchModel.builder()
                .categories(categories)
                .cities(cities)
                .paging(PageRequest.of(0, 10))
                .build();

        propertyService.findByPropertySearchModel(propertySearchModel);

        verify(propertyRepository).findByCategoryIdInAndCityIdIn(categories, cities, propertySearchModel.getPaging());

    }
}