package com.example.zapupropertyapi.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.math.BigDecimal;

@Document(collection = "property")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Getter
@Setter
public class Property implements Serializable {

    @Transient
    public static final String SEQUENCE_NAME = "property_sequence";

    @Id
    @EqualsAndHashCode.Include
    private Long id;

    private Category category;
    private String title;
    private City city;
    private BigDecimal price;
    private String currency;
}
