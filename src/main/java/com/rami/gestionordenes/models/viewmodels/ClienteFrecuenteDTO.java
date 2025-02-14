package com.rami.gestionordenes.models.viewmodels;

import com.rami.gestionordenes.models.Usuario;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ClienteFrecuenteDTO {
    private Usuario usuario;
    private int cantidadOrdenes;
}
