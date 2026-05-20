package com.cleantemplate.base.application.usecases;

import org.springframework.stereotype.Service;

import com.cleantemplate.base.domain.gateways.UsuarioGateway;

@Service
public class DeletarUsuarioUseCase {
    private final UsuarioGateway gateway;

    public DeletarUsuarioUseCase(UsuarioGateway gateway) {
        this.gateway = gateway;
    }

    public void executar(Long id){
        gateway.deletar(id);
    }

}
