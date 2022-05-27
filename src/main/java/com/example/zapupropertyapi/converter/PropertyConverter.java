package com.example.zapupropertyapi.converter;

import com.example.zapupropertyapi.dto.PropertyDto;
import com.example.zapupropertyapi.dto.PropertyResponseDto;
import com.example.zapupropertyapi.dto.PropertySearchResponseDto;
import com.example.zapupropertyapi.model.Category;
import com.example.zapupropertyapi.model.City;
import com.example.zapupropertyapi.model.Property;
import com.example.zapupropertyapi.service.category.CategoryService;
import com.example.zapupropertyapi.service.city.CityService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class PropertyConverter {

    private final ModelMapper modelMapper;
    private final CityService cityService;
    private final CategoryService categoryService;

    public PropertyConverter(ModelMapper modelMapper, CityService cityService, CategoryService categoryService) {
        this.modelMapper = modelMapper;
        this.cityService = cityService;
        this.categoryService = categoryService;
    }

    public PropertyDto convertToDto(Property property) {
        PropertyDto propertyDto = modelMapper.map(property, PropertyDto.class);
        propertyDto.setCityName(property.getCity().getName());
        propertyDto.setCategoryId(property.getCategory().getId());
        return propertyDto;
    }

    public PropertyResponseDto convertToPropertySearchResponseDto(Page<Property> properties, String rootUrl) {

        PropertyResponseDto responseDto = PropertyResponseDto.builder()
                .rootUrl(rootUrl)
                .page(properties.getPageable().getPageNumber())
                .pageSize(properties.getPageable().getPageSize())
                .count(properties.getTotalElements())
                .totalPage(properties.getTotalPages())
                .build();

        List<PropertySearchResponseDto> result = new ArrayList<>();
        properties.getContent().forEach(property -> result.add(modelMapper.map(property, PropertySearchResponseDto.class)));

        responseDto.setResult(result);

        return responseDto;
    }

    public Property convertToEntity(PropertyDto propertyDto) throws Exception {
        Property property = modelMapper.map(propertyDto, Property.class);

        Optional<City> city = cityService.getCityById(propertyDto.getCityId());
        if (!city.isPresent()) {
            throw new Exception("city not found");
        }
        Category category = categoryService.findCategoryById(propertyDto.getCategoryId());
        property.setCity(city.get());
        property.setCategory(category);

        return property;
    }
}
