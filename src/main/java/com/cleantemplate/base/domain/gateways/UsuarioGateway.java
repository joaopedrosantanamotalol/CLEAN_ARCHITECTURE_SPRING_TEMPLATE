package com.cleantemplate.base.domain.gateways;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.cleantemplate.base.application.dto.AtualizarUsuarioDTO;
import com.cleantemplate.base.domain.entities.Usuario;

@Repository
public interface UsuarioGateway {
    Usuario salvar(Usuario usuario);

    Boolean existeEmail(String email);

    List<Usuario> listarUsuarios();

    Usuario atualizar(Long id, AtualizarUsuarioDTO usuario);

    void deletar(Long id);

    Usuario buscarUsuarioPorEmail(String email);

}
