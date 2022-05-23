package com.example.zapupropertyapi.controller;

import dto.DenemeDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api")
public class BaseController {

    @GetMapping(value = "/index")
    public ResponseEntity<DenemeDto> index() {
        DenemeDto deneme = DenemeDto.builder().welcome("welcome").build();
        return ResponseEntity.ok(deneme);
    }
}
