package com.rami.gestionordenes.controllers;

import com.rami.gestionordenes.models.LoginRequest;
import com.rami.gestionordenes.models.Usuario;
import com.rami.gestionordenes.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {
        Optional<Usuario> usuario = usuarioRepository.findByUsername(loginRequest.getUsername());

        if (usuario.isPresent() && usuario.get().getPassword().equals(loginRequest.getPassword())) {
            return ResponseEntity.ok("Autenticación exitosa");
        }

        return ResponseEntity.status(401).body("Credenciales incorrectas");
    }
}
