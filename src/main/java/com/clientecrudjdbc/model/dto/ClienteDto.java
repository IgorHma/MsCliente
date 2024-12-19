package com.clientecrudjdbc.model.dto;

import com.clientecrudjdbc.model.entity.Cliente;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
public class ClienteDto {
    @NotBlank
    @Size(min = 2, max = 100)
    private String name;

    @Email
    @NotBlank
    @Size(min = 5, max = 100)
    private String email;

    @NotBlank
    @Size(min = 8, max = 100)
    private String password;

    public Cliente toEntity() {
        return new Cliente(null, name, email, password);
    }
}
