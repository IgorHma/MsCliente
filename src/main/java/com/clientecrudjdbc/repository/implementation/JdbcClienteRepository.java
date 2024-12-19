package com.clientecrudjdbc.repository.implementation;

import com.clientecrudjdbc.model.entity.Cliente;
import com.clientecrudjdbc.repository.interfaces.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

@Repository
public class JdbcClienteRepository implements ClienteRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Cliente save(Cliente cliente) {
        String sql = "INSERT INTO cliente (name, email, password) VALUES (?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, cliente.getName());
            ps.setString(2, cliente.getEmail());
            ps.setString(3, cliente.getPassword());
            return ps;
        }, keyHolder);
        cliente.setId(keyHolder.getKey().longValue());
        return cliente;
    }

    @Override
    public Optional<Cliente> findOne(Long id) {
        String sql = "SELECT * FROM cliente WHERE id = ?";
        List<Cliente> clientes = jdbcTemplate.query(sql, new ClienteRowMapper(), id);
        if(clientes.isEmpty())
            return Optional.empty();
        else
            return Optional.of(clientes.get(0));
    }


    @Override
    public List<Cliente> findAll() {
        String sql = "SELECT * FROM cliente";
        return jdbcTemplate.query(sql, new ClienteRowMapper());
    }

    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM cliente WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public Cliente update(Cliente cliente) {
        String sql = "UPDATE cliente SET name = ?, email = ?, password = ? WHERE id = ?";
        jdbcTemplate.update(sql, cliente.getName(), cliente.getEmail(), cliente.getPassword(), cliente.getId());
        return cliente;
    }

    @Override
    public boolean existsByEmail(String email) {
        String sql = "SELECT COUNT(*) FROM cliente WHERE email = ?";
        Integer count = jdbcTemplate.query(sql, (ResultSet rs) -> {
            if (rs.next()) {
                return rs.getInt(1);
            }
            return 0;
        }, new Object[] { email } );
        return count > 0;
    }


    private final class ClienteRowMapper implements RowMapper<Cliente> {
        @Override
        public Cliente mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Cliente(rs.getLong("id"), rs.getString("name"), rs.getString("email"), rs.getString("password"));
        }
    }


    @Override
    public boolean existsByEmailWithNotThisId(String email, Long id) {
        String sql = "SELECT COUNT(*) FROM cliente WHERE email = ? and id != ?";
        Integer count = jdbcTemplate.query(sql, (ResultSet rs) -> {
            if (rs.next()) {
                return rs.getInt(1);
            }
            return 0;
        }, new Object[] { email, id } );
        return count > 0;
    }
}
