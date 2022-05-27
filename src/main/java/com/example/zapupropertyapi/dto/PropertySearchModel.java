package com.example.zapupropertyapi.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Pageable;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@Builder
public class PropertySearchModel implements Serializable {
    private List<Short> categories;
    private List<Short> cities;
    private String pathUrl;
    private Pageable paging;
    @JsonIgnore
    private String cacheKey;
}
