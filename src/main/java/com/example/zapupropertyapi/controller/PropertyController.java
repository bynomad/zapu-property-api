package com.example.zapupropertyapi.controller;

import com.example.zapupropertyapi.converter.PropertyConverter;
import com.example.zapupropertyapi.dto.PropertyDto;
import com.example.zapupropertyapi.model.Property;
import com.example.zapupropertyapi.service.property.PropertyService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api")
public class PropertyController {
    private final PropertyService propertyService;
    private final PropertyConverter propertyConverter;

    public PropertyController(PropertyService propertyService, PropertyConverter propertyConverter) {
        this.propertyService = propertyService;
        this.propertyConverter = propertyConverter;
    }

    @PostMapping(value = "property")
    public ResponseEntity<PropertyDto> createProperty(@RequestBody PropertyDto propertyDto) throws Exception {
        Property property;
        if (propertyDto.getId() != null) {
            property = updateProperty(propertyDto);
        } else {
            property = propertyConverter.convertToEntity(propertyDto);
            propertyService.createProperty(property);
        }

        PropertyDto dto = propertyConverter.convertToDto(property);
        return ResponseEntity.ok().body(dto);
    }

    @GetMapping(value = "properties")
    public ResponseEntity<Page<Property>> getAllProperties() {
        Pageable paging = PageRequest.of(0, 100);
        return ResponseEntity.ok().body(propertyService.getProperties(paging));
    }

    private Property updateProperty(PropertyDto propertyDto) throws Exception {
        Property property = propertyService.findPropertyById(propertyDto.getId());
        if (property != null) {
            property = propertyService.updateProperty(propertyConverter.convertToEntity(propertyDto));
        } else {
            // todo hata fırlat böyle bir id li property yok diyerekten
        }

        return property;
    }

}
