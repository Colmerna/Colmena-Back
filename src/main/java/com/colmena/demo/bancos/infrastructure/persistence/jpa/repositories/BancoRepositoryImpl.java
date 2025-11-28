package com.colmena.demo.bancos.infrastructure.persistence.jpa.repositories;

import com.colmena.demo.bancos.domain.model.aggregates.Banco;
import com.colmena.demo.bancos.domain.repository.BancoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class BancoRepositoryImpl implements BancoRepository {

    private final BancoJpaRepository jpaRepository;

    public BancoRepositoryImpl(BancoJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Banco save(Banco banco) {          // 👈 IMPLEMENTA ESTO
        return jpaRepository.save(banco);
    }

    @Override
    public Optional<Banco> findById(Long id) {
        return jpaRepository.findById(id);
    }

    @Override
    public List<Banco> findAll() {
        return jpaRepository.findAll();
    }
}
