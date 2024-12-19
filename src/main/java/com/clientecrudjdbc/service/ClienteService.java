package com.clientecrudjdbc.service;

import com.clientecrudjdbc.exeception.BadRequestException;
import com.clientecrudjdbc.exeception.NotFoundException;
import com.clientecrudjdbc.model.dto.ClienteDto;
import com.clientecrudjdbc.model.entity.Cliente;
import com.clientecrudjdbc.repository.interfaces.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    protected ClienteRepository clienteRepository;

    public Cliente create(ClienteDto cliente) {
        Cliente newCliente = cliente.toEntity();
        validateEmail(cliente.getEmail());
        String encodedPassword = encryptPassword(cliente.getPassword());
        newCliente.setPassword(encodedPassword);
        return clienteRepository.save(newCliente);
    }


    private void validateEmail(String email) {
        if (clienteRepository.existsByEmail(email)) {
            throw new BadRequestException("Email already is used");
        }
    }

    public Cliente findById(Long id) {
        Optional<Cliente> clienteFounded = clienteRepository.findOne(id);
        if (clienteFounded.isEmpty()) {
            throw new NotFoundException("cliente");
        } return clienteFounded.get();
    }

    public List<Cliente> findAll() {
        return clienteRepository.findAll();
    }

    public void delete(Long id) {
        clienteRepository.delete(id);
    }

    public Cliente update(Long id, ClienteDto clienteDto) {
        Cliente cliente = findById(id);
        validateEmailByDiferentId(clienteDto.getEmail(), id);
        cliente.setEmail(clienteDto.getEmail());
        cliente.setName(clienteDto.getName());
        cliente.setPassword(encryptPassword(clienteDto.getPassword()));
        return clienteRepository.update(cliente);
    }

    private void validateEmailByDiferentId(String email, Long id) {
        if (clienteRepository.existsByEmailWithNotThisId(email, id)) {
            throw new BadRequestException("Email already is used");
        }
    }


    private String encryptPassword(String password) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(password);
        return encodedPassword;
    }
}
