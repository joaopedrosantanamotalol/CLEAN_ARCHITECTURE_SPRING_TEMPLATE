package com.cleantemplate.base.infrastructure.persistence.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.cleantemplate.base.application.dto.AtualizarUsuarioDTO;
import com.cleantemplate.base.application.dto.CriarUsuarioDTO;
import com.cleantemplate.base.domain.entities.Usuario;
import com.cleantemplate.base.infrastructure.persistence.entities.UsuarioEntity;
import com.cleantemplate.base.presentation.response.UsuarioResponse;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    UsuarioEntity toEntity(Usuario usuario);

    Usuario toDomain(UsuarioEntity entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "role", ignore = true)
    @Mapping(target = "dataCriacao", ignore = true)
    Usuario toDomain(CriarUsuarioDTO dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "dataCriacao", ignore = true)
    @Mapping(target = "senha", ignore = true)
    @Mapping(target = "role", ignore = true)
    void updateUsuarioFromDto(
        AtualizarUsuarioDTO dto,
        @MappingTarget Usuario usuario
    );

    UsuarioResponse toResponse(Usuario usuario);
}