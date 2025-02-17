package com.rami.gestionordenes.controllers;

import com.rami.gestionordenes.auditoria.OrdenAuditoria;
import com.rami.gestionordenes.repositories.OrdenAuditoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/auditoria")
public class OrdenAuditoriaController {
    @Autowired
    private OrdenAuditoriaRepository ordenAuditoriaRepository;

    // Obtener historial de auditoría
    @GetMapping("/ordenes")
    public ResponseEntity<List<OrdenAuditoria>> obtenerAuditoriaOrdenes() {
        return ResponseEntity.ok(ordenAuditoriaRepository.findAll());
    }
}
