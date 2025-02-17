package com.rami.gestionordenes.repositories;

import com.rami.gestionordenes.models.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {
    Optional<Producto> findByNombre(String nombre);

    List<Producto> findByActivoTrue();
    List<Producto> findByNombreContaining(String nombre);
    List<Producto> findByPrecioBetween(Double precioMin, Double precioMax);
    List<Producto> findByCategoriaIgnoreCase(String categoria);
    @Query("SELECT p FROM Producto p LEFT JOIN Inventario i ON p.idProducto = i.producto.idProducto WHERE i.idInventario IS NULL")
    List<Producto> findProductosSinInventario();
}
