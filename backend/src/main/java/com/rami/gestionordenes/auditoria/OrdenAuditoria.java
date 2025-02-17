package com.rami.gestionordenes.auditoria;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "ordenes_auditoria")
public class OrdenAuditoria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long ordenId;
    private String accion; // CREATE, UPDATE, DELETE
    private Double total;
    private String estado;
    private Date fechaEvento;
    private String usuario;
}
