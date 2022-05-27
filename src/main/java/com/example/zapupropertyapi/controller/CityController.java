package com.example.zapupropertyapi.controller;

import com.example.zapupropertyapi.model.City;
import com.example.zapupropertyapi.service.city.CityService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api")
public class CityController {

    private final CityService cityService;

    public CityController(CityService cityService) {
        this.cityService = cityService;
    }


    @GetMapping(value = "cities")
    public ResponseEntity<List<City>> getAllCities() {
        return ResponseEntity.ok().body(cityService.getAllCities());
    }

    @PostMapping(value = "createCity")
    public ResponseEntity<City> createCity(@Valid @RequestBody City city) {
        return ResponseEntity.ok().body(cityService.createCity(city));
    }
}
