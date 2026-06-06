package com.cleantemplate.base.presentation.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.cleantemplate.base.application.dto.AtualizarUsuarioDTO;
import com.cleantemplate.base.application.dto.CriarUsuarioDTO;
import com.cleantemplate.base.application.usecases.AtualizarUsuarioUseCase;
import com.cleantemplate.base.application.usecases.CriarUsuarioUseCase;
import com.cleantemplate.base.application.usecases.DeletarUsuarioUseCase;
import com.cleantemplate.base.application.usecases.ListarUsuarioUseCase;
import com.cleantemplate.base.domain.entities.Usuario;
import com.cleantemplate.base.infrastructure.persistence.mappers.UsuarioMapper;
import com.cleantemplate.base.presentation.response.UsuarioResponse;

import jakarta.validation.Valid;

@RestController()
@RequestMapping("/usuarios")
public class UsuarioController {
    private final CriarUsuarioUseCase criarUseCase;
    private final ListarUsuarioUseCase listarUsuarioUseCase;
    private final AtualizarUsuarioUseCase atualizarUsuarioUseCase;
    private final DeletarUsuarioUseCase deletarUsuarioUseCase;
    private final UsuarioMapper mapper;

    public UsuarioController(CriarUsuarioUseCase criarUseCase, ListarUsuarioUseCase listarUsuarioUseCase,
            AtualizarUsuarioUseCase atualizarUsuarioUseCase, DeletarUsuarioUseCase deletarUsuarioUseCase,
            UsuarioMapper mapper) {

        this.criarUseCase = criarUseCase;
        this.listarUsuarioUseCase = listarUsuarioUseCase;
        this.atualizarUsuarioUseCase = atualizarUsuarioUseCase;
        this.deletarUsuarioUseCase = deletarUsuarioUseCase;
        this.mapper = mapper;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UsuarioResponse criar(   
        @Valid @RequestBody CriarUsuarioDTO dto
    ) {

        Usuario criado = criarUseCase.execute(
            mapper.toDomain(dto)
        );

        return mapper.toResponse(criado);
    }

    @GetMapping()
    public List<UsuarioResponse> listar(){
        return listarUsuarioUseCase.executar()
        .stream()
        .map(mapper::toResponse)
        .toList();

    }

    @PutMapping("/{id}")
    public UsuarioResponse atualizar(
        @PathVariable Long id,
        @Valid @RequestBody AtualizarUsuarioDTO dto
    ) {

        Usuario usuario = atualizarUsuarioUseCase.executar(id, dto);

        return mapper.toResponse(usuario);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(@PathVariable Long id) {
        deletarUsuarioUseCase.executar(id);
    }

    @GetMapping("/teste")
    public List<UsuarioResponse> teste() {

        return listarUsuarioUseCase.executar()
                .stream()
                .map(mapper::toResponse)
                .toList();
    }
}
