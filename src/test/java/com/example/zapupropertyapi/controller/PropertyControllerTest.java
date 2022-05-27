package com.example.zapupropertyapi.controller;

import com.example.zapupropertyapi.converter.PropertyConverter;
import com.example.zapupropertyapi.dto.PropertyDto;
import com.example.zapupropertyapi.model.Property;
import com.example.zapupropertyapi.service.property.PropertyService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class PropertyControllerTest {
    @InjectMocks
    private PropertyController propertyController;

    @Mock
    private PropertyService propertyService;

    @Mock
    private PropertyConverter propertyConverter;

    @Test
    public void shouldUpdateProperty() throws Exception {
        PropertyDto propertyDto = PropertyDto.builder().id(Long.parseLong("1")).build();
        Property property = new Property();
        property.setId(Long.parseLong("2"));

        when(propertyService.findPropertyById(propertyDto.getId())).thenReturn(property);
        when(propertyConverter.convertToEntity(propertyDto)).thenReturn(property);
        when(propertyService.updateProperty(propertyConverter.convertToEntity(propertyDto))).thenReturn(property);

        ResponseEntity<PropertyDto> response = propertyController.createProperty(propertyDto);

        ArgumentCaptor<Property> propertyArgumentCaptor = ArgumentCaptor.forClass(Property.class);
        verify(propertyService).updateProperty(propertyArgumentCaptor.capture());
        verify(propertyConverter).convertToDto(propertyArgumentCaptor.getValue());

        assertEquals(response.getStatusCode(), HttpStatus.OK);
    }

    @Test
    public void shouldCreateProperty() throws Exception {
        PropertyDto propertyDto = PropertyDto.builder().build();
        Property property = new Property();
        property.setId(Long.parseLong("2"));

        when(propertyConverter.convertToEntity(propertyDto)).thenReturn(property);

        ResponseEntity<PropertyDto> response = propertyController.createProperty(propertyDto);

        ArgumentCaptor<Property> propertyArgumentCaptor = ArgumentCaptor.forClass(Property.class);
        verify(propertyService).createProperty(propertyArgumentCaptor.capture());
        verify(propertyConverter).convertToDto(propertyArgumentCaptor.getValue());

        assertEquals(response.getStatusCode(), HttpStatus.OK);
    }

    @Test
    public void shouldGetGetAllProperties() {
        Pageable paging = PageRequest.of(0, 100);
        ResponseEntity<Page<Property>> response = propertyController.getAllProperties();

        verify(propertyService).getProperties(paging);
    }
}