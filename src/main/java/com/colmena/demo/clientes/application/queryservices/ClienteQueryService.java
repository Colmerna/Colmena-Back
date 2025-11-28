package com.colmena.demo.clientes.application.queryservices;

import com.colmena.demo.clientes.domain.model.aggregates.Cliente;
import com.colmena.demo.clientes.domain.model.queries.GetAllClientesQuery;
import com.colmena.demo.clientes.domain.model.queries.GetClienteByIdQuery;
import com.colmena.demo.clientes.domain.repository.ClienteRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteQueryService {

    private final ClienteRepository clienteRepository;

    public ClienteQueryService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public Optional<Cliente> handle(GetClienteByIdQuery query) {
        return clienteRepository.findById(query.clienteId());
    }

    public List<Cliente> handle(GetAllClientesQuery query) {
        return clienteRepository.findAll();
    }
}
