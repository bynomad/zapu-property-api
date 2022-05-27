package com.example.zapupropertyapi.controller;

import com.example.zapupropertyapi.model.City;
import com.example.zapupropertyapi.service.city.CityService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CityControllerTest {
    @InjectMocks
    private CityController cityController;

    @Mock
    private CityService cityService;
    City city;

    @BeforeEach
    public void init() {
        city = new City();
        city.setId(Short.valueOf("1"));
        city.setName("Ankara");
    }

    @Test
    public void shouldGetAllCities() {
        ResponseEntity<List<City>> response = cityController.getAllCities();
        verify(cityService).getAllCities();

        assertEquals(response.getStatusCode(), HttpStatus.OK);
    }

    @Test
    public void shouldCreateCity() {
        ResponseEntity<City> response = cityController.createCity(city);
        verify(cityService).createCity(city);

        assertEquals(response.getStatusCode(), HttpStatus.OK);
    }
}