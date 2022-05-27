package com.example.zapupropertyapi.service.city;

import com.example.zapupropertyapi.model.City;
import com.example.zapupropertyapi.repository.city.CityRepository;
import com.example.zapupropertyapi.service.cache.RedisCacheService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.example.zapupropertyapi.service.city.CityServiceImp.CITY_CACHE_NAME;
import static com.example.zapupropertyapi.service.city.CityServiceImp.CITY_CACHE_NAME_ALL;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CityServiceImpTest {
    @InjectMocks
    private CityServiceImp service;

    @Mock
    private CityRepository cityRepository;

    @Mock
    private RedisCacheService redisCacheService;

    private City city;

    @BeforeEach
    public void init() {
        city = new City();
        city.setId(Short.valueOf("1"));
        city.setName("Ankara");
    }

    @Test
    public void shouldCreateCategory() {
        when(cityRepository.save(city)).thenReturn(city);
        when(redisCacheService.isKeyAvailable(CITY_CACHE_NAME, CITY_CACHE_NAME_ALL)).thenReturn(true);

        service.createCity(city);

        verify(cityRepository).save(city);
        verify(redisCacheService).putValueToCache(CITY_CACHE_NAME, city.getId(), city);
        verify(redisCacheService).putValueToCache(CITY_CACHE_NAME, CITY_CACHE_NAME_ALL, city);
    }

    @Test
    public void shouldGetCategoryById() {
        service.getCityById(city.getId());

        verify(cityRepository).findCityById(city.getId());
    }

    @Test
    public void shouldGetAllCategories() {
        service.getAllCities();

        verify(cityRepository).findAll();
    }
}