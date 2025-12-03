package com.colmena.demo.bancos.domain.repository;

import com.colmena.demo.bancos.domain.model.aggregates.Banco;

import java.util.List;
import java.util.Optional;

public interface BancoRepository {


    Banco save(Banco banco);

    Optional<Banco> findById(Long id);

    List<Banco> findAll();
}
