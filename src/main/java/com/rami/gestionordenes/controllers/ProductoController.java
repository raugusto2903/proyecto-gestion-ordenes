package com.rami.gestionordenes.controllers;

import com.rami.gestionordenes.models.Producto;
import com.rami.gestionordenes.models.viewmodels.ProductoMasVendidoDTO;
import com.rami.gestionordenes.services.ProductoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {
    private final ProductoService productoService;

    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    @GetMapping
    public List<Producto> obtenerTodos() {
        return productoService.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Producto> obtenerPorId(@PathVariable Long id) {
        Optional<Producto> producto = productoService.buscarPorId(id);
        return producto.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Producto> crear(@RequestBody Producto producto) {
        Producto nuevoProducto = productoService.guardar(producto);
        return ResponseEntity.ok(nuevoProducto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Producto> actualizar(@PathVariable Long id, @RequestBody Producto producto) {
        try {
            Producto actualizado = productoService.actualizar(id, producto);
            return ResponseEntity.ok(actualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        productoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/nombre")
    public ResponseEntity<Producto> obtenerPorNombre(@RequestParam String nombre) {
        Optional<Producto> producto = productoService.buscarPorNombre(nombre);
        return producto.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/rango-precio")
    public List<Producto> obtenerPorRangoDePrecios(@RequestParam Double precioMin, @RequestParam Double precioMax) {
        return productoService.listarPorRangoDePrecios(precioMin, precioMax);
    }

    @GetMapping("/categoria")
    public List<Producto> obtenerPorCategoria(@RequestParam String categoria) {
        return productoService.listarPorCategoria(categoria);
    }

    // Obtener los productos más vendidos
    @GetMapping("/mas-vendidos")
    public ResponseEntity<List<ProductoMasVendidoDTO>> obtenerProductosMasVendidos() {
        List<ProductoMasVendidoDTO> productosMasVendidos = productoService.obtenerProductosMasVendidos();
        return ResponseEntity.ok(productosMasVendidos);
    }

}
