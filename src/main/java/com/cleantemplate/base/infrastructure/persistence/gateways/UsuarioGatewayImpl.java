package com.cleantemplate.base.infrastructure.persistence.gateways;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.cleantemplate.base.application.dto.AtualizarUsuarioDTO;
import com.cleantemplate.base.domain.entities.Usuario;
import com.cleantemplate.base.domain.gateways.UsuarioGateway;
import com.cleantemplate.base.infrastructure.persistence.entities.UsuarioEntity;
import com.cleantemplate.base.infrastructure.persistence.mappers.UsuarioMapper;
import com.cleantemplate.base.infrastructure.persistence.repositories.UsuarioRepository;

@Repository
public class UsuarioGatewayImpl implements UsuarioGateway {
    private final UsuarioRepository repository;
    private final UsuarioMapper mapper;
    
    public UsuarioGatewayImpl(UsuarioRepository repository, UsuarioMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Usuario salvar(Usuario usuario) {

    UsuarioEntity entity = mapper.toEntity(usuario);

    UsuarioEntity saved = repository.save(entity);

    return mapper.toDomain(saved);
    }   

    @Override
    public Boolean existeEmail(String email) {
        return repository.existsByEmail(email);
    }

    @Override
    public Usuario buscarUsuarioPorEmail(String email) {

        return repository.findByEmail(email)
                .map(mapper::toDomain)
                .orElse(null);
    }

    @Override
    public List<Usuario> listarUsuarios(){
    return repository.findAll()
        .stream()
        .map(mapper::toDomain)
        .toList();
    }

    @Override
    public Usuario atualizar(Long id, AtualizarUsuarioDTO usuario){
        UsuarioEntity entity = repository.findById(id)
        .orElseThrow(() -> new RuntimeException("não achado"));

        entity.setNome(usuario.nome());
        entity.setEmail(usuario.email());

        UsuarioEntity atualizado = repository.save(entity);

        return mapper.toDomain(atualizado);

    }

    @Override
    public void deletar(Long id){
        repository.deleteById(id);
    }

}
