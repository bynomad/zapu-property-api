package com.example.zapupropertyapi.service.city;

import com.example.zapupropertyapi.model.City;

import java.util.List;
import java.util.Optional;

public interface CityService {
    City createCity(City city);

    List<City> getAllCities();

    Optional<City> getCityById(short id);
}
