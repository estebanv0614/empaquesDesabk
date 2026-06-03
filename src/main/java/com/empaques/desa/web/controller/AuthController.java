package com.empaques.desa.web.controller;

import com.empaques.desa.domain.dto.LoginDto;

import com.empaques.desa.domain.exception.UserInactiveException;
import com.empaques.desa.web.config.JwtUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginDto loginDto) {
        try {
            UsernamePasswordAuthenticationToken login =
                    new UsernamePasswordAuthenticationToken(
                            loginDto.getUsername(),
                            loginDto.getPassword()
                    );
            authenticationManager.authenticate(login);

            String jwt = jwtUtil.createToken(
                    loginDto.getUsername()
            );
            return ResponseEntity.ok()
                    .header(HttpHeaders.AUTHORIZATION, jwt)
                    .body(Map.of(
                            "timestamp", LocalDateTime.now(),
                            "status", 200,
                            "message", "Login correcto",
                            "token", jwt
                    ));
        }catch (BadCredentialsException ex) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of(
                            "timestamp", LocalDateTime.now(),
                            "status", 401,
                            "error", "UNAUTHORIZED",
                            "message", "Credenciales inválidas"
                    ));
        }catch (DisabledException ex) {
            throw new UserInactiveException();
        }
    }
}
