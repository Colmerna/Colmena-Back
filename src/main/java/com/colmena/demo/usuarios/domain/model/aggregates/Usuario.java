package com.colmena.demo.usuarios.domain.model.aggregates;

import com.colmena.demo.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "usuarios")
@NoArgsConstructor
public class Usuario extends AuditableAbstractAggregateRoot<Usuario> {

    @Column(nullable = false, unique = true, length = 80)
    private String username;

    @Column(nullable = false, unique = true, length = 120)
    private String email;

    @Column(name = "password", nullable = false, length = 255)
    private String password;

    public Usuario(String username, String email, String password) {

        if (username == null || username.isBlank())
            throw new IllegalArgumentException("El username no puede ser vacío");

        if (email == null || email.isBlank())
            throw new IllegalArgumentException("El email no puede ser vacío");

        if (password == null || password.isBlank())
            throw new IllegalArgumentException("El password no puede ser vacío");

        this.username = username;
        this.email = email;
        this.password = password;
    }
}
