package com.cleantemplate.base.application.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CriarUsuarioDTO(

 @NotBlank
    String nome,

    @NotBlank
    @Email
    String email,

    @NotBlank
    @Size(min = 6)
    String senha

) {
}
