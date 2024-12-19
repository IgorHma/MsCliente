package com.clientecrudjdbc.repository.interfaces;

import com.clientecrudjdbc.model.entity.Cliente;

import java.util.List;
import java.util.Optional;

public interface ClienteRepository {
    Cliente save(Cliente user);
    Optional<Cliente> findOne(Long id);
    List<Cliente> findAll();
    void delete(Long id);
    Cliente update(Cliente user);
    boolean existsByEmail(String email);
    boolean existsByEmailWithNotThisId(String email, Long id);
}
