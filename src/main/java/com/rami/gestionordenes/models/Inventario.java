package com.rami.gestionordenes.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
@Getter
@Setter
@Entity
@Table(name = "inventarios")
public class Inventario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idInventario;

    @OneToOne
    @JoinColumn(name = "producto_id", nullable = false)
    private Producto producto; // Relación con Producto

    @Column(nullable = false)
    private Integer cantidadDisponible;

    @Column(nullable = false)
    private Integer stockMinimo;

    @Column(nullable = false)
    private Integer stockMaximo;

    @Column(nullable = false)
    private Date ultimaActualizacion;

    @PreUpdate
    public void actualizarFecha() {
        this.ultimaActualizacion = new Date();
    }
}
