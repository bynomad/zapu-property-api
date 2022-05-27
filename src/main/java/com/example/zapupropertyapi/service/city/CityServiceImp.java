package com.example.zapupropertyapi.service.city;

import com.example.zapupropertyapi.model.City;
import com.example.zapupropertyapi.repository.city.CityRepository;
import com.example.zapupropertyapi.service.cache.RedisCacheService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CityServiceImp implements CityService {

    public static final String CITY_CACHE_NAME = "city";
    public static String CITY_CACHE_NAME_ALL = "cities";
    private final CityRepository cityRepository;
    private final RedisCacheService redisCacheService;

    public CityServiceImp(CityRepository cityRepository, RedisCacheService redisCacheService) {
        this.cityRepository = cityRepository;
        this.redisCacheService = redisCacheService;
    }

    @Override
    public City createCity(City city) {
        City createdCity = cityRepository.save(city);
        putCityToCacheAndMergeAllCitiesCache(createdCity);
        return createdCity;
    }

    @Override
    @Cacheable(value = CITY_CACHE_NAME, key = "#root.target.CITY_CACHE_NAME_ALL")
    public List<City> getAllCities() {
        return cityRepository.findAll();
    }

    @Override
    @Cacheable(value = CITY_CACHE_NAME, key = "#id")
    public Optional<City> getCityById(short id) {
        return cityRepository.findCityById(id);
    }

    private void putCityToCacheAndMergeAllCitiesCache(City city) {
        redisCacheService.putValueToCache(CITY_CACHE_NAME, city.getId(), city);
        if (redisCacheService.isKeyAvailable(CITY_CACHE_NAME, CITY_CACHE_NAME_ALL)) {
            redisCacheService.putValueToCache(CITY_CACHE_NAME, CITY_CACHE_NAME_ALL, city);
        }
    }
}
