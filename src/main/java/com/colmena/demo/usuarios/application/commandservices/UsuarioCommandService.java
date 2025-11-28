package com.colmena.demo.usuarios.application.commandservices;

import com.colmena.demo.usuarios.domain.model.aggregates.Usuario;
import com.colmena.demo.usuarios.domain.model.commands.RegisterUsuarioCommand;
import com.colmena.demo.usuarios.domain.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

@Service
public class UsuarioCommandService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioCommandService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public Long handle(RegisterUsuarioCommand command) {

        if (usuarioRepository.existsByUsernameOrEmail(command.username(), command.email())) {
            throw new IllegalArgumentException("Ya existe un usuario con ese username o email");
        }

        // Aquí tú podrías hashear el password si quisieras.
        Usuario usuario = new Usuario(
                command.username(),
                command.email(),
                command.password()
        );

        Usuario saved = usuarioRepository.save(usuario);
        return saved.getId();
    }
}
