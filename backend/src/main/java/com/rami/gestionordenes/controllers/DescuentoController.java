package com.rami.gestionordenes.controllers;

import com.rami.gestionordenes.models.viewmodels.DescuentoDto;
import com.rami.gestionordenes.services.DescuentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/descuento")
public class DescuentoController {
    @Autowired
    private DescuentoService descuentoService;

    @PostMapping("/configurar")
    public ResponseEntity<String> configurarDescuento(@RequestBody DescuentoDto descuentoDto) {
        descuentoService.setRangoDescuento(descuentoDto.getInicio(), descuentoDto.getFin());
        return ResponseEntity.ok("✅ Rango de descuento configurado: " + descuentoDto.getInicio() + " - " + descuentoDto.getFin());
    }
}
