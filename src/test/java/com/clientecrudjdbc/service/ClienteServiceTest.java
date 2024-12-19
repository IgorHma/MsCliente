package com.clientecrudjdbc.service;

import com.clientecrudjdbc.exeception.BadRequestException;
import com.clientecrudjdbc.exeception.NotFoundException;
import com.clientecrudjdbc.model.dto.ClienteDto;
import com.clientecrudjdbc.model.entity.Cliente;
import com.clientecrudjdbc.repository.interfaces.ClienteRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

@SpringBootTest
public class ClienteServiceTest {

    @Mock
    private ClienteRepository clienteRepository;

    @InjectMocks
    private ClienteService clienteService;

    @Test
    void testCreate() {
        ClienteDto clienteDto = new ClienteDto("name", "email@email.com", "password");
        Cliente newCliente = clienteDto.toEntity();
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(clienteDto.getPassword());
        newCliente.setPassword(encodedPassword);

        when(clienteRepository.existsByEmail(anyString())).thenReturn(false);
        when(clienteRepository.save(any(Cliente.class))).thenReturn(newCliente);

        Cliente returnedCliente = clienteService.create(clienteDto);
        assertEquals(newCliente, returnedCliente);
    }

    @Test
    void testCreate_EmailAlreadyExists() {
        ClienteDto clienteDto = new ClienteDto("name", "email@email.com", "password");
        when(clienteRepository.existsByEmail("email@email.com")).thenReturn(true);
        assertThrows(BadRequestException.class, () -> clienteService.create(clienteDto));
        verify(clienteRepository, times(1)).existsByEmail("email@email.com");
        verify(clienteRepository, never()).save(any());
    }

    @Test
    void findById() {
        Cliente cliente = new Cliente(1L, "name", "email@email.com", "password");
        when(clienteRepository.findOne(cliente.getId())).thenReturn(Optional.of(cliente));
        Cliente returnedCliente = clienteService.findById(cliente.getId());
        assertEquals(cliente, returnedCliente);
        verify(clienteRepository, times(1)).findOne(cliente.getId());
    }

    @Test
    void findById_whenClienteNotFound_throwNotFoundException() {
        Long id = 1L;
        when(clienteRepository.findOne(id)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> clienteService.findById(id));
    }

    @Test
    void findAll() {
        List<Cliente> clientes = Arrays.asList(new Cliente(1L, "name", "email@email.com", "password"));
        when(clienteRepository.findAll()).thenReturn(clientes);
        List<Cliente> returnedClientes = clienteService.findAll();
        assertEquals(clientes, returnedClientes);
        verify(clienteRepository, times(1)).findAll();
    }

    @Test
    void delete() {
        Long id = 1L;
        clienteService.delete(id);
        verify(clienteRepository, times(1)).delete(id);
    }

    @Test
    void update() {
        Long id = 1L;
        ClienteDto clienteDto = new ClienteDto("name", "email@email.com", "password");
        Cliente cliente = new Cliente(id, "name", "email@email.com", "password");
        when(clienteRepository.findOne(id)).thenReturn(Optional.of(cliente));
        when(clienteRepository.update(cliente)).thenReturn(cliente);
        Cliente returnedCliente = clienteService.update(id, clienteDto);
        assertEquals(cliente, returnedCliente);
        verify(clienteRepository, times(1)).findOne(id);
        verify(clienteRepository, times(1)).update(cliente);
    }

    @Test
    void update_whenEmailIsAlreadyUsed_throwBadRequestException() {
        Long id = 1L;
        ClienteDto clienteDto = new ClienteDto("name", "email@email.com", "password");
        Cliente cliente = new Cliente(id, "name", "email@email.com", "password");
        when(clienteRepository.findOne(id)).thenReturn(Optional.of(cliente));
        when(clienteRepository.existsByEmailWithNotThisId(anyString(), anyLong())).thenReturn(true);
        assertThrows(BadRequestException.class, () -> clienteService.update(id, clienteDto));
    }
}
