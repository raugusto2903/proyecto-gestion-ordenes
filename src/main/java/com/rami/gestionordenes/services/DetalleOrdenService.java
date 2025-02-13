package com.rami.gestionordenes.services;

import com.rami.gestionordenes.models.DetalleOrden;
import com.rami.gestionordenes.repositories.DetalleOrdenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DetalleOrdenService {
    @Autowired
    private DetalleOrdenRepository detalleOrdenRepository;

    // Obtener todos los detalles de órdenes
    public List<DetalleOrden> obtenerTodos() {
        return detalleOrdenRepository.findAll();
    }

    // Obtener detalles de una orden específica
    public List<DetalleOrden> obtenerPorOrdenId(Long ordenId) {
        return detalleOrdenRepository.findByOrdenId(ordenId);
    }

    // Obtener un detalle específico por ID
    public Optional<DetalleOrden> obtenerPorId(Long id) {
        return detalleOrdenRepository.findById(id);
    }

    // Guardar o actualizar un detalle de orden
    public DetalleOrden guardarDetalle(DetalleOrden detalleOrden) {
        return detalleOrdenRepository.save(detalleOrden);
    }

    // Eliminar un detalle de orden
    public void eliminarDetalle(Long id) {
        detalleOrdenRepository.deleteById(id);
    }
}
