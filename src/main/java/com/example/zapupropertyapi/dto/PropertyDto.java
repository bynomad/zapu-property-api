package com.example.zapupropertyapi.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PropertyDto implements Serializable {

    private Long id;

    @JsonProperty("category")
    private Short categoryId;
    private String title;
    @JsonIgnore
    private short cityId;
    @JsonProperty("city")
    private String cityName;
    private BigDecimal price;
    private String currency;
}
