package com.clientecrudjdbc.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
@AllArgsConstructor
public class Cliente {
    @Id
    private Long id;
    private String name;
    private String email;
    private String password;
}
