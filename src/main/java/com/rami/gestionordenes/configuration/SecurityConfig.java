package com.rami.gestionordenes.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.http.SessionCreationPolicy;
@Configuration
public class SecurityConfig {


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Desactivar CSRF
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll() // PERMITIR TODAS LAS RUTAS
                )
                .formLogin(form -> form.disable()) // Deshabilitar login por formulario
                .logout(logout -> logout.disable()); // Deshabilitar logout

        return http.build();
    }
}
