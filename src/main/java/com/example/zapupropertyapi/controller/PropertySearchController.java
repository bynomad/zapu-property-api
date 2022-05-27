package com.example.zapupropertyapi.controller;

import com.example.zapupropertyapi.converter.PropertyConverter;
import com.example.zapupropertyapi.dto.PropertyDto;
import com.example.zapupropertyapi.dto.PropertyResponseDto;
import com.example.zapupropertyapi.dto.PropertySearchModel;
import com.example.zapupropertyapi.model.Property;
import com.example.zapupropertyapi.service.property.PropertyService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class PropertySearchController {

    private final PropertyService propertyService;

    private final PropertyConverter propertyConverter;

    public PropertySearchController(PropertyService propertyService, PropertyConverter propertyConverter) {
        this.propertyService = propertyService;
        this.propertyConverter = propertyConverter;
    }


    @GetMapping(value = {
            "arama",
            "{category:[a-zA-Z]+}",
            "{category:[a-zA-Z]+}/{city:[a-zA-Z]+}",
            "{category:[a-zA-Z]+}/{city:[a-zA-Z]+}/{district:[a-zA-Z]+}",
            "{category:[a-zA-Z]+}/{city:[a-zA-Z]+}/{district:[a-zA-Z]+}/{neigh:[a-zA-Z]+}"
    })
    public ResponseEntity<PropertyResponseDto> searchProperites(@RequestParam MultiValueMap<String, String> allParams,
                                                                @RequestParam(defaultValue = "0") int page,
                                                                @RequestParam(defaultValue = "10") int size, HttpServletRequest request) {
        String friendlyUrl = CollectionUtils.isEmpty(allParams) ? request.getServletPath() : null;
        String rootUrl = friendlyUrl != null ? request.getRequestURL().toString() : request.getRequestURL().append("?").append(request.getQueryString()).toString();

        PropertySearchModel propertySearchModel = prepareSearchDto(allParams, request, page, size);
        Page<Property> properties = propertyService.findByPropertySearchModel(propertySearchModel);

        PropertyResponseDto responseDto = propertyConverter.convertToPropertySearchResponseDto(properties, rootUrl);

        return ResponseEntity.ok().body(responseDto);
    }

    @GetMapping(value = "detay/{id:[\\d]+}")
    public ResponseEntity<PropertyDto> getPropertyById(@PathVariable long id) {
        Property property = propertyService.findPropertyById(id);
        PropertyDto dto = propertyConverter.convertToDto(property);
        return ResponseEntity.ok().body(dto);
    }

    private PropertySearchModel prepareSearchDto(MultiValueMap<String, String> allParams, HttpServletRequest request, int page, int size) {
        String friendlyUrl = CollectionUtils.isEmpty(allParams) ? request.getServletPath() : null;

        List<Short> categories = !CollectionUtils.isEmpty(allParams.get("category")) ? allParams.get("category").stream().map(Short::parseShort).collect(Collectors.toList()) : null;
        List<Short> cities = !CollectionUtils.isEmpty(allParams.get("city")) ? allParams.get("city").stream().map(Short::parseShort).collect(Collectors.toList()) : null;

        PropertySearchModel propertySearchModel = PropertySearchModel.builder()
                .categories(categories)
                .cities(cities)
                .pathUrl(friendlyUrl)
                .paging(PageRequest.of(page, size))
                .cacheKey(friendlyUrl != null ? request.getServletPath() : request.getQueryString())
                .build();
        return propertySearchModel;
    }

}
