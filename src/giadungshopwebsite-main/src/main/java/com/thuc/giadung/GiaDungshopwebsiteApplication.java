package com.thuc.giadung;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition()
public class GiaDungshopwebsiteApplication {

    public static void main(String[] args) {
        SpringApplication.run(GiaDungshopwebsiteApplication.class, args);
    }


}
