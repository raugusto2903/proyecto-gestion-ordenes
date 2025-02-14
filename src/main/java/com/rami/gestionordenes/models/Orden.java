package com.rami.gestionordenes.models;

import com.rami.gestionordenes.auditoria.OrdenAuditListener;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.Date;
import java.util.List;
@Getter
@Setter
@Entity
@Table(name = "ordenes")
@EntityListeners(OrdenAuditListener.class)
public class Orden {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario; // Relación con Usuario

    @OneToMany(mappedBy = "orden", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<DetalleOrden> detalles;
    @Column(nullable = false)
    private Double total;

    @Column(nullable = false)
    private String estado; // Ejemplo: "Pendiente", "Enviado", "Entregado", "Cancelado"


    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCreacion;

    @PrePersist
    public void prePersist() {
        this.fechaCreacion = new Date();
    }
}
