package com.rami.gestionordenes.controllers;

import com.rami.gestionordenes.models.Orden;
import com.rami.gestionordenes.services.OrdenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/ordenes")
public class OrdenController {
    @Autowired
    private OrdenService ordenService;

    // Obtener todas las órdenes
    @GetMapping
    public List<Orden> obtenerTodas() {
        return ordenService.obtenerTodas();
    }

    // Obtener una orden por ID
    @GetMapping("/{id}")
    public ResponseEntity<Orden> obtenerPorId(@PathVariable Long id) {
        Optional<Orden> orden = ordenService.obtenerPorId(id);
        return orden.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Crear o actualizar una orden
    @PostMapping
    public Orden guardarOrden(@RequestBody Orden orden) {
        return ordenService.guardarOrden(orden);
    }

    // Eliminar una orden
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarOrden(@PathVariable Long id) {
        if (ordenService.obtenerPorId(id).isPresent()) {
            ordenService.eliminarOrden(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
