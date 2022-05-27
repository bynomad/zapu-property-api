package com.example.zapupropertyapi.repository.city;

import com.example.zapupropertyapi.model.City;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CityRepository extends MongoRepository<City, Integer> {
    Optional<City> findCityById(short id);
}
