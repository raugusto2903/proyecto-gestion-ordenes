package com.rami.gestionordenes.services;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class DescuentoService {
    private LocalDateTime inicioDescuento;
    private LocalDateTime finDescuento;

    /**
     * Configura el rango de fechas para aplicar el descuento.
     */
    public void setRangoDescuento(LocalDateTime inicio, LocalDateTime fin) {
        this.inicioDescuento = inicio;
        this.finDescuento = fin;
    }

    /**
     * Verifica si la fecha de una orden está dentro del rango de descuento.
     */
    public boolean aplicarDescuento(LocalDateTime fechaCreacion) {
        if (inicioDescuento == null || finDescuento == null) {
            return false; // Si no se ha configurado un rango, no aplica descuento
        }

        return (fechaCreacion.isAfter(inicioDescuento) || fechaCreacion.isEqual(inicioDescuento)) &&
                (fechaCreacion.isBefore(finDescuento) || fechaCreacion.isEqual(finDescuento));
    }
}
