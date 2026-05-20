package com.cleantemplate.base.infrastructure.Security;

import java.security.Key;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.cleantemplate.base.domain.services.TokenService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService implements TokenService {

    @Value("${jwt.secret}")
    private String secret;

    private Key getKey() {
        return Keys.hmacShaKeyFor(secret.getBytes());
    }

    public String gerarToken(String email) {

        return Jwts.builder()
                .subject(email)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 86400000))
                .signWith(getKey())
                .compact();
    }

    public String extrairEmail(String token) {

        Claims claims = Jwts.parser()
                .verifyWith((SecretKey) getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();

        return claims.getSubject();
    }

    public boolean tokenValido(String token) {

        try {
            extrairEmail(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
