package com.rami.gestionordenes.controllers;

import com.rami.gestionordenes.models.DetalleOrden;
import com.rami.gestionordenes.services.DetalleOrdenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/detalle-orden")
public class DetalleOrdenController {
    @Autowired
    private DetalleOrdenService detalleOrdenService;

    // Obtener todos los detalles de órdenes
    @GetMapping
    public List<DetalleOrden> obtenerTodos() {
        return detalleOrdenService.obtenerTodos();
    }

    // Obtener detalles de una orden específica
    @GetMapping("/orden/{ordenId}")
    public List<DetalleOrden> obtenerPorOrdenId(@PathVariable Long ordenId) {
        return detalleOrdenService.obtenerPorOrdenId(ordenId);
    }

    // Obtener un detalle específico por ID
    @GetMapping("/{id}")
    public ResponseEntity<DetalleOrden> obtenerPorId(@PathVariable Long id) {
        Optional<DetalleOrden> detalleOrden = detalleOrdenService.obtenerPorId(id);
        return detalleOrden.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Crear o actualizar un detalle de orden
    @PostMapping
    public DetalleOrden guardarDetalle(@RequestBody DetalleOrden detalleOrden) {
        return detalleOrdenService.guardarDetalle(detalleOrden);
    }

    // Eliminar un detalle de orden
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarDetalle(@PathVariable Long id) {
        if (detalleOrdenService.obtenerPorId(id).isPresent()) {
            detalleOrdenService.eliminarDetalle(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
