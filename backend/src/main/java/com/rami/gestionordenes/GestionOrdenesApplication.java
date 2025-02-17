package com.rami.gestionordenes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan({
        "com.rami.gestionordenes.models",
        "com.rami.gestionordenes.auditoria"
})
@EnableJpaRepositories({
        "com.rami.gestionordenes.repositories"
})
@ComponentScan({
        "com.rami.gestionordenes.services",
        "com.rami.gestionordenes.controllers",
        "com.rami.gestionordenes.configuration",
        "com.rami.gestionordenes.repositories"
})
public class GestionOrdenesApplication {

    public static void main(String[] args) {
        SpringApplication.run(GestionOrdenesApplication.class, args);
    }

}
