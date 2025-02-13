package com.rami.gestionordenes.repositories;

import com.rami.gestionordenes.models.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductoRepository extends JpaRepository<Producto, Long> {
    Optional<Producto> findByNombre(String nombre);
    List<Producto> findByPrecioBetween(Double precioMin, Double precioMax);
    List<Producto> findByCategoriaIgnoreCase(String categoria);
}
