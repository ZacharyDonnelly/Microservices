package com.zachdonnelly.authorization.auth.util;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import org.springframework.security.oauth2.jwt.Jwt;

import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.Date;

public class TokenGenerator {

    public static void main(String[] args) throws NoSuchAlgorithmException {
        // Generate a key pair
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();

        // Build the JWT
        JwtBuilder jwtBuilder = Jwts.builder()
                .setIssuer("https://zachdonnelly.com")
                .setAudience("https://zachdonnelly.com")
                .setId("123")
                .setSubject("user@example.com")
                .setExpiration(Date.from(Instant.now().plusSeconds(3600)))
                .setIssuedAt(new Date())
                .signWith(keyPair.getPrivate());

          String jwt = jwtBuilder.compact();
//          System.out.println(jwt.);
    }
}
