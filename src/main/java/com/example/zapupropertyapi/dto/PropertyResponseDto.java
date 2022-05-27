package com.example.zapupropertyapi.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@Builder
public class PropertyResponseDto implements Serializable {

    private int page;
    private int pageSize;
    private int totalPage;
    private long count;
    private List<PropertySearchResponseDto> result;
    private String rootUrl;

}
