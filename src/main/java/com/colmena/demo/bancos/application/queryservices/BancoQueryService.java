package com.colmena.demo.bancos.application.queryservices;

import com.colmena.demo.bancos.domain.model.aggregates.Banco;
import com.colmena.demo.bancos.domain.model.queries.GetAllBancosQuery;
import com.colmena.demo.bancos.domain.model.queries.GetBancoByIdQuery;
import com.colmena.demo.bancos.domain.repository.BancoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BancoQueryService {

    private final BancoRepository bancoRepository;

    public BancoQueryService(BancoRepository bancoRepository) {
        this.bancoRepository = bancoRepository;
    }

    public List<Banco> handle(GetAllBancosQuery query) {
        return bancoRepository.findAll();
    }

    public Optional<Banco> handle(GetBancoByIdQuery query) {
        return bancoRepository.findById(query.bancoId());
    }
}
