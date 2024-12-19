package com.clientecrudjdbc;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info=@Info(title="Cliente Crud Jdbc", version = "v1"))
public class ClienteCrudJdbcAplication {
    public static void main(String[] args) {
        SpringApplication.run(ClienteCrudJdbcAplication.class, args);
    }
}
