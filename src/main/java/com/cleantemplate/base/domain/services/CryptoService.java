package com.cleantemplate.base.domain.services;

import org.springframework.stereotype.Service;

@Service
public interface CryptoService{
    String encrypt(String text) throws Exception;
    String decrypt(String text) throws Exception;
}