package com.example.zapupropertyapi.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
public class PropertySearchResponseDto implements Serializable {

    private Long id;
    private String title;
    private BigDecimal price;
    private String currency;
}
