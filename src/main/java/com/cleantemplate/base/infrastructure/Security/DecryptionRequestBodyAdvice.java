package com.cleantemplate.base.infrastructure.Security;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;

import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdviceAdapter;

import com.cleantemplate.base.domain.services.CryptoService;
import com.fasterxml.jackson.databind.ObjectMapper;

@ControllerAdvice
public class DecryptionRequestBodyAdvice extends RequestBodyAdviceAdapter {

    private final CryptoService cryptoService;
    private final ObjectMapper objectMapper;

    public DecryptionRequestBodyAdvice(
            CryptoService cryptoService,
            ObjectMapper objectMapper) {

        this.cryptoService = cryptoService;
        this.objectMapper = objectMapper;
    }

    @Override
    public boolean supports(
            MethodParameter methodParameter,
            Type targetType,
            Class<? extends HttpMessageConverter<?>> converterType) {

        return true;
    }

   @Override
    public HttpInputMessage beforeBodyRead(
            HttpInputMessage inputMessage,
            MethodParameter parameter,
            Type targetType,
            Class<? extends HttpMessageConverter<?>> converterType)
            throws IOException {

        String body = new String(inputMessage.getBody().readAllBytes());

        System.out.println(body);

        EncryptedRequest encryptedRequest =
                objectMapper.readValue(body, EncryptedRequest.class);

        // Se não houver campo "data", trata como JSON normal
        if (encryptedRequest.getData() == null) {

            return new HttpInputMessage() {

                @Override
                public InputStream getBody() {
                    return new java.io.ByteArrayInputStream(body.getBytes());
                }

                @Override
                public HttpHeaders getHeaders() {
                    return inputMessage.getHeaders();
                }
            };
        }

        try {

            String decryptedJson =
                    cryptoService.decrypt(encryptedRequest.getData());

            return new HttpInputMessage() {

                @Override
                public InputStream getBody() {
                    return new java.io.ByteArrayInputStream(
                            decryptedJson.getBytes());
                }

                @Override
                public HttpHeaders getHeaders() {
                    return inputMessage.getHeaders();
                }
            };

        } catch (Exception e) {

            throw new RuntimeException(e);

        }
    }
}