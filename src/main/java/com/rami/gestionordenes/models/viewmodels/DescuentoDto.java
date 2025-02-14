package com.rami.gestionordenes.models.viewmodels;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class DescuentoDto {
    private LocalDateTime inicio;
    private LocalDateTime fin;
}
