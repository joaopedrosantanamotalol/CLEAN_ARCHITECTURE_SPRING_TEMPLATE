package com.cleantemplate.base.application.usecases;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.cleantemplate.base.application.dto.LoginDTO;
import com.cleantemplate.base.domain.entities.Usuario;
import com.cleantemplate.base.domain.gateways.UsuarioGateway;
import com.cleantemplate.base.domain.services.TokenService;

@Service
public class LoginUseCase {
    private final UsuarioGateway gateway;
    private final TokenService service;
    private final BCryptPasswordEncoder passwordEncoder;

    public LoginUseCase(
        UsuarioGateway gateway,
        TokenService service,
        BCryptPasswordEncoder passwordEncoder
    ) {
        this.gateway = gateway;
        this.service = service;
        this.passwordEncoder = passwordEncoder;
    }

    public String executar(LoginDTO dto){
        Usuario usuario = gateway.buscarUsuarioPorEmail(dto.email());

            
    if(usuario == null){
        throw new RuntimeException("usuario não achado");
    }

    boolean senhaValida =
   passwordEncoder.matches(dto.senha(), usuario.getSenha());

     if(!senhaValida){
        throw new RuntimeException("Senha invalida");
    }
        return service.gerarToken(usuario.getEmail());
    }


}
