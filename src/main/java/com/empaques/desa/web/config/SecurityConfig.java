package com.empaques.desa.web.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfigurationSource;


@Configuration
public class SecurityConfig {

    private final JwtFilter jwtFilter;
    private final CorsConfigurationSource corsConfigurationSource;

    @Autowired
    public SecurityConfig(JwtFilter jwtFilter, CorsConfigurationSource corsConfigurationSource) {
        this.jwtFilter = jwtFilter;
        this.corsConfigurationSource = corsConfigurationSource;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configurationSource(corsConfigurationSource))
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/**").permitAll()
                        .requestMatchers("/error").permitAll()
                        // PERSONS
                        .requestMatchers(HttpMethod.GET, "/persons/**").authenticated()
                        .requestMatchers(HttpMethod.POST, "/persons/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/persons/**").hasRole("ADMIN")
                        // USERS
                        .requestMatchers(HttpMethod.GET, "/users/**").hasAnyRole("ADMIN", "USER")
                        .requestMatchers(HttpMethod.POST, "/users/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/users/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/users/**").hasRole("ADMIN")
                        // EMPLOYEE
                        .requestMatchers(HttpMethod.GET, "/employee/**").hasAnyRole("ADMIN", "USER")
                        .requestMatchers(HttpMethod.POST, "/employee/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/employee/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/employee/**").permitAll()
                        // CLIENTS
                        .requestMatchers(HttpMethod.GET, "/clients/**").hasAnyRole("ADMIN", "USER")
                        .requestMatchers(HttpMethod.POST, "/clients/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/clients/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/clients/**").permitAll()
                        // MATERIALS
                        .requestMatchers(HttpMethod.GET, "/materials/**").hasAnyRole("ADMIN", "USER")
                        .requestMatchers(HttpMethod.POST, "/materials/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/materials/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/materials/**").permitAll()
                        //BOLSAS
                        .requestMatchers(HttpMethod.GET, "/bolsas/**").hasAnyRole("ADMIN", "USER")
                        .requestMatchers(HttpMethod.POST, "/bolsas/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/bolsas/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/bolsas/**").hasRole("ADMIN")
                        //RECETE-BOLSA
                        .requestMatchers(HttpMethod.GET, "/recetas-bolsa/**").hasAnyRole("ADMIN", "USER")
                        .requestMatchers(HttpMethod.POST, "/recetas-bolsa/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/recetas-bolsa/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/recetas-bolsa/**").hasRole("ADMIN")
                        //ORDEN-PRODUCCION
                        .requestMatchers(HttpMethod.GET, "/ordenes-produccion/**").hasAnyRole("ADMIN", "USER")
                        .requestMatchers(HttpMethod.POST, "/ordenes-produccion/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/ordenes-produccion/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/ordenes-produccion/**").hasRole("ADMIN")
                        //MOVIMIENTO-INVENTARIO
                        .requestMatchers(HttpMethod.GET, "/movimientos-inventario/**").hasAnyRole("ADMIN", "USER")
                        .requestMatchers(HttpMethod.POST, "/movimientos-inventario/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/movimientos-inventario/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/movimientos-inventario/**").hasRole("ADMIN")
                        //MOVIMIENTO-BOLSA
                        .requestMatchers(HttpMethod.GET, "/movimientos-bolsa/**").hasAnyRole("ADMIN", "USER")
                        .requestMatchers(HttpMethod.POST, "/movimientos-bolsa/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/movimientos-bolsa/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/movimientos-bolsa/**").hasRole("ADMIN")
                        //DOCUMENTO-COMERCIAL
                        .requestMatchers(HttpMethod.GET, "/documentos-comerciales/**").hasAnyRole("ADMIN", "USER")
                        .requestMatchers(HttpMethod.POST, "/documentos-comerciales/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/documentos-comerciales/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/documentos-comerciales/**").hasRole("ADMIN")
                        //DETALLE-DOCUMENTO
                        .requestMatchers(HttpMethod.GET, "/detalle-documentos/**").hasAnyRole("ADMIN", "USER")
                        .requestMatchers(HttpMethod.POST, "/detalle-documentos/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/detalle-documentos/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/detalle-documentos/**").hasRole("ADMIN")
                        //SOLICITUD-COTIZACION (formulario público)
                        .requestMatchers(HttpMethod.POST, "/solicitudes-cotizacion").permitAll()
                        .requestMatchers(HttpMethod.GET, "/solicitudes-cotizacion/**").hasAnyRole("ADMIN", "USER")
                        .requestMatchers(HttpMethod.PATCH, "/solicitudes-cotizacion/**").hasAnyRole("ADMIN", "USER")
                        .requestMatchers(HttpMethod.DELETE, "/solicitudes-cotizacion/**").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
