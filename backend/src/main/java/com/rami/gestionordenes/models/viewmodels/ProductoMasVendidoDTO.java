package com.rami.gestionordenes.models.viewmodels;

import com.rami.gestionordenes.models.Producto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ProductoMasVendidoDTO {
    private Producto producto;
    private int cantidadVendida;
}
