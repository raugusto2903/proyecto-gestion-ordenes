package com.rami.gestionordenes.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class UsuarioController {

    @GetMapping("/hola")
    public String healthCheck(){
        return "Hola mundo desde springBoot";
    }
}
