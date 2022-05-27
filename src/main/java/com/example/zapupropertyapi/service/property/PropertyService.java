package com.example.zapupropertyapi.service.property;

import com.example.zapupropertyapi.dto.PropertySearchModel;
import com.example.zapupropertyapi.model.Property;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PropertyService {
    Property findPropertyById(Long propertyId);

    Page<Property> getProperties(Pageable pageable);

    Property createProperty(Property property);

    Property updateProperty(Property property);

    Page<Property> findByPropertySearchModel(PropertySearchModel propertySearchModel);
}
