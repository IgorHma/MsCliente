package com.clientecrudjdbc.controller;

import com.clientecrudjdbc.model.dto.ClienteDto;
import com.clientecrudjdbc.model.entity.Cliente;
import com.clientecrudjdbc.service.ClienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/cliente")
@Tag(name = "Cliente", description = "API para gerenciamento de clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @PostMapping("/post")
    @Operation(summary = "Criar um novo cliente", description = "Adiciona um novo cliente ao sistema.")
    @ResponseStatus(HttpStatus.CREATED)
    public Cliente create(@Valid @RequestBody ClienteDto ClienteDto) {
        return clienteService.create(ClienteDto);
    }

    @GetMapping("/getId/{id}")
    @Operation(summary = "Buscar cliente por ID", description = "Retorna um cliente específico pelo ID informado.")
    public Cliente findById(@PathVariable Long id) {
        return clienteService.findById(id);
    }

    @GetMapping("/getAll")
    @Operation(summary = "Listar todos os clientes", description = "Retorna uma lista de todos os clientes cadastrados.")
    public List<Cliente> findAll() {
        return clienteService.findAll();
    }

    @DeleteMapping("/del/{id}")
    @Operation(summary = "Deletar um cliente", description = "Remove um cliente do sistema pelo ID.")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        clienteService.delete(id);
    }

    @PutMapping("/put/{id}")
    @Operation(summary = "Atualizar um cliente", description = "Atualiza os dados de um cliente existente pelo ID.")
    public Cliente update(@PathVariable Long id, @Valid @RequestBody ClienteDto ClienteDto) { return clienteService.update(id, ClienteDto); }

    @GetMapping("/count")
    @Operation(summary = "Contar clientes", description = "Retorna a quantidade total de clientes cadastrados no sistema.")
    public Long contarClientes() { return clienteService.contarClientes(); }

    @GetMapping("/search")
    @Operation(summary = "Buscar clientes por nome", description = "Busca clientes pelo nome informado.")
    public List<Cliente> buscarPorNome(@RequestParam String name) {
        return clienteService.buscarPorNome(name);
    }
}
