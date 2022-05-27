package com.example.zapupropertyapi.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Document(collection = "city")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Getter
@Setter
public class City implements Serializable {
    @Id
    @EqualsAndHashCode.Include
    private Short id;

    @NotBlank(message = "City name is mandatory")
    @Indexed(unique = true)
    private String name;
}
