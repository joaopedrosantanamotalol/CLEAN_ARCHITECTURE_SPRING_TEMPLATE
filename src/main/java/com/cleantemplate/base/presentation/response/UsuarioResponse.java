package com.cleantemplate.base.presentation.response;

import java.time.LocalDateTime;

import com.cleantemplate.base.domain.entities.RoleEnum;

public record UsuarioResponse(
    Long id,
    String nome,
    String email,
    RoleEnum role,
    LocalDateTime dataCriacao

) {
}