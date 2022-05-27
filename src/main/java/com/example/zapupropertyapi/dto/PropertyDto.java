package com.example.zapupropertyapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
public class PropertyDto implements Serializable {

    private Long id;

    @JsonProperty("category")
    private Short categoryId;
    private String title;
    @JsonProperty("city")
    private short cityId;
    private BigDecimal price;
    private String currency;
}
