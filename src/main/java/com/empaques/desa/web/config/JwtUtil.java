package com.empaques.desa.web.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.concurrent.TimeUnit;

@Component
public class JwtUtil {

    private final Algorithm algorithm;

    public JwtUtil(@Value("${app.jwt.secret}") String secret) {
        this.algorithm = Algorithm.HMAC256(secret);
    }

    public String createToken(String username) {
        return JWT.create()
                .withSubject(username)
                .withIssuer("empaques-desa")
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(120)))
                .sign(algorithm);
    }

    public boolean isValid(String jwt) {
        try {
            JWT.require(algorithm).build().verify(jwt);
            return true;
        } catch (JWTVerificationException e) {
            return false;
        }
    }

    public String getUsername(String jwt) {
        return JWT.require(algorithm).build().verify(jwt).getSubject();
    }
}
