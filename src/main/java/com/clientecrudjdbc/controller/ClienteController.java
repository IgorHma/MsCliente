package com.clientecrudjdbc.controller;

import com.clientecrudjdbc.model.dto.ClienteDto;
import com.clientecrudjdbc.model.entity.Cliente;
import com.clientecrudjdbc.service.ClienteService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/cliente")
@Tag(name = "Cliente Controller")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @PostMapping("/post")
    public Cliente create(@Valid @RequestBody ClienteDto ClienteDto) {
        return clienteService.create(ClienteDto);
    }

    @GetMapping("/getId/{id}")
    public Cliente findById(@PathVariable Long id) {
        return clienteService.findById(id);
    }

    @GetMapping("/getAll")
    public List<Cliente> findAll() {
        return clienteService.findAll();
    }

    @DeleteMapping("/del/{id}")
    public void delete(@PathVariable Long id) {
        clienteService.delete(id);
    }

    @PutMapping("/put/{id}")
    public Cliente update(@PathVariable Long id, @Valid @RequestBody ClienteDto ClienteDto) {
        return clienteService.update(id, ClienteDto);
    }
}
