package com.cleantemplate.base.infrastructure.Security;

import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.stereotype.Service;

import com.cleantemplate.base.domain.services.CryptoService;

@Service
public class AESCryptoService implements CryptoService {

    private final SecretKey secretKey =
            new SecretKeySpec(
                    "1234567890123456".getBytes(),
                    "AES"
            );

    @Override
    public String encrypt(String text) throws Exception {

        Cipher cipher = Cipher.getInstance("AES");

        cipher.init(Cipher.ENCRYPT_MODE, secretKey);

        byte[] encryptedBytes = cipher.doFinal(text.getBytes());

        return Base64.getEncoder()
                .encodeToString(encryptedBytes);
    }

    @Override
    public String decrypt(String encryptedText) throws Exception {

        Cipher cipher = Cipher.getInstance("AES");

        cipher.init(Cipher.DECRYPT_MODE, secretKey);

        byte[] bytes = Base64.getDecoder()
                .decode(encryptedText);

        return new String(cipher.doFinal(bytes));
    }
}