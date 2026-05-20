package com.cleantemplate.base.application.usecases;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cleantemplate.base.domain.entities.Usuario;
import com.cleantemplate.base.domain.gateways.UsuarioGateway;

@Service
public class ListarUsuarioUseCase {
   private final UsuarioGateway usuarioGateway;

    public ListarUsuarioUseCase(UsuarioGateway usuarioGateway) {
        this.usuarioGateway = usuarioGateway;
    }

    public List<Usuario> executar() {
        return usuarioGateway.listarUsuarios();
    }

}
