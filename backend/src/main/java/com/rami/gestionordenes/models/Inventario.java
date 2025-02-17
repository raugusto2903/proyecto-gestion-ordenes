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

    public Inventario(Long idInventario, Producto producto, Integer cantidadDisponible, Integer stockMinimo, Integer stockMaximo, Date ultimaActualizacion) {
        this.idInventario = idInventario;
        this.producto = producto;
        this.cantidadDisponible = cantidadDisponible;
        this.stockMinimo = stockMinimo;
        this.stockMaximo = stockMaximo;
        this.ultimaActualizacion = ultimaActualizacion;
    }

    public Inventario() {
    }
}
