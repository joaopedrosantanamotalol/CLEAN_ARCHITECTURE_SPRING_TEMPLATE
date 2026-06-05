package com.cleantemplate.base.infrastructure.Security;

import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import com.cleantemplate.base.domain.services.CryptoService;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestControllerAdvice
public class EncryptionResponseAdvice implements ResponseBodyAdvice<Object> {

    private final CryptoService cryptoService;
    private final ObjectMapper objectMapper;

    public EncryptionResponseAdvice(
            CryptoService cryptoService,
            ObjectMapper objectMapper) {

        this.cryptoService = cryptoService;
        this.objectMapper = objectMapper;
    }

    @Override
    public boolean supports(
            MethodParameter returnType,
            Class<? extends HttpMessageConverter<?>> converterType) {

        return !returnType.getContainingClass()
                .getSimpleName()
                .equals("BasicErrorController")
            &&
            !returnType.getParameterType()
                .equals(EncryptedResponse.class);
    }
    @Override
    public Object beforeBodyWrite(
            Object body,
            MethodParameter returnType,
            MediaType selectedContentType,
            Class<? extends HttpMessageConverter<?>> selectedConverterType,
            ServerHttpRequest request,
            ServerHttpResponse response) {

        try {

            String path = request.getURI().getPath();

            // Ignorar algumas rotas
            if (path.startsWith("/swagger-ui")
                    || path.startsWith("/v3/api-docs")
                    || path.startsWith("/actuator")
                    || path.startsWith("/error")
                    || path.startsWith("/usuarios/teste")) {

                return body;
            }

            // Não criptografar respostas vazias
            if (body == null) {
                return null;
            }
            if (body instanceof EncryptedResponse) {
            return body;
        }
            String json = objectMapper.writeValueAsString(body);

            String encrypted = cryptoService.encrypt(json);

            return new EncryptedResponse(encrypted);

        } catch (Exception e) {

            throw new RuntimeException("Erro ao criptografar resposta", e);

        }
    }
}