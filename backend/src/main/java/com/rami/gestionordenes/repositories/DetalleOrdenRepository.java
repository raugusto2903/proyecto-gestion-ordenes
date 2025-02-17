package com.rami.gestionordenes.repositories;

import com.rami.gestionordenes.models.DetalleOrden;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DetalleOrdenRepository extends JpaRepository<DetalleOrden, Long> {
    List<DetalleOrden> findByOrdenId(Long ordenId);
    // Consulta para obtener los productos más vendidos
    @Query("SELECT d.producto, SUM(d.cantidad) as totalVentas " +
            "FROM DetalleOrden d GROUP BY d.producto ORDER BY totalVentas DESC")
    List<Object[]> findTopSellingProducts();
}
