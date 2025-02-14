package com.rami.gestionordenes.repositories;

import com.rami.gestionordenes.models.Orden;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrdenRepository extends JpaRepository<Orden, Long> {

    // Consulta para obtener los clientes con más órdenes
    @Query("SELECT o.usuario, COUNT(o) as totalOrdenes " +
            "FROM Orden o GROUP BY o.usuario ORDER BY totalOrdenes DESC")
    List<Object[]> findTopFrequentCustomers();

}
