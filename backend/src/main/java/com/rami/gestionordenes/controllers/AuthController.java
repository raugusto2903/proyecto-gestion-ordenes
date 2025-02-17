package com.rami.gestionordenes.controllers;

import com.rami.gestionordenes.models.LoginRequest;
import com.rami.gestionordenes.models.Usuario;
import com.rami.gestionordenes.repositories.UsuarioRepository;
import com.rami.gestionordenes.services.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private JwtService jwtService;
    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody LoginRequest loginRequest) {
        Optional<Usuario> usuario = usuarioRepository.findByEmail(loginRequest.getUsername());

        if (usuario.isPresent() && usuario.get().getPassword().equals(loginRequest.getPassword())) {
            String token = jwtService.generateToken(loginRequest.getUsername());

            Map<String, String> response = new HashMap<>();
            response.put("message", loginRequest.getUsername());
            response.put("token", token);
            return ResponseEntity.ok(response);
        }

        return ResponseEntity.status(401).body(Map.of("error", "Credenciales incorrectas"));
    }
}
