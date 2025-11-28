package com.colmena.demo.bancos.application.commandservices;

import com.colmena.demo.bancos.domain.model.aggregates.Banco;
import com.colmena.demo.bancos.domain.model.commands.CreateBancosCommand;
import com.colmena.demo.bancos.domain.model.commands.UpdateTipoCambioBancoCommand;
import com.colmena.demo.bancos.domain.model.queries.GetBancoByIdQuery;
import com.colmena.demo.bancos.domain.repository.BancoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BancoCommandService {

    private final BancoRepository bancoRepository;

    public BancoCommandService(BancoRepository bancoRepository) {
        this.bancoRepository = bancoRepository;
    }

    @Transactional
    public Long handle(CreateBancosCommand command) {   // 👈 sin "s"

        Banco banco = new Banco(
                command.nombre(),
                command.ruc(),
                command.telefono(),
                command.sitioWeb(),
                command.monedaBase(),
                command.usaTasasNominales(),
                command.capitalizacionDefault(),
                command.tipoCambioCompra(),
                command.tipoCambioVenta(),
                command.estado()
        );

        return bancoRepository.save(banco).getId();    // 👈 usamos bancoRepository
    }

    @Transactional
    public void handle(UpdateTipoCambioBancoCommand command) {

        Banco banco = bancoRepository.findById(command.bancoId())
                .orElseThrow(() -> new IllegalArgumentException("Banco no encontrado"));

        banco.actualizarTipoCambio(command.tipoCambioCompra(), command.tipoCambioVenta());

        bancoRepository.save(banco); // opcional si estás en contexto persistente, pero está bien
    }

}
