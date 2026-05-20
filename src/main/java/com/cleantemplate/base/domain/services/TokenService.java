package com.cleantemplate.base.domain.services;

import org.springframework.stereotype.Service;

@Service
public interface TokenService {
    String gerarToken(String email);
}
