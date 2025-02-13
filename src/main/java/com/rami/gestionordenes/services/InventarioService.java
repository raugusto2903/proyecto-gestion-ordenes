package com.rami.gestionordenes.services;

import com.rami.gestionordenes.models.Inventario;
import com.rami.gestionordenes.models.Producto;
import com.rami.gestionordenes.repositories.InventarioRepository;
import com.rami.gestionordenes.repositories.ProductoRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class InventarioService {

    private final InventarioRepository inventarioRepository;
    private  final ProductoRepository productoRepository;



    public InventarioService(InventarioRepository inventarioRepository, ProductoRepository productoRepository) {
        this.inventarioRepository = inventarioRepository;
        this.productoRepository = productoRepository;
    }

    public List<Inventario> obtenerTodos() {
        return inventarioRepository.findAll();
    }

    public Optional<Inventario> obtenerPorId(Long id) {
        return inventarioRepository.findById(id);
    }

    public Inventario guardarInventario(Inventario inventario) {
        Producto producto = productoRepository.findById(inventario.getProducto().getIdProducto())
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con ID: " + inventario.getProducto().getIdProducto()));
        inventario.setUltimaActualizacion(new Date());
        return inventarioRepository.save(inventario);
    }

    public Inventario actualizarInventario(Long id, Inventario inventarioDetalles) {
        return inventarioRepository.findById(id).map(inventario -> {
            inventario.setProducto(inventarioDetalles.getProducto());
            inventario.setCantidadDisponible(inventarioDetalles.getCantidadDisponible());
            inventario.setStockMinimo(inventarioDetalles.getStockMinimo());
            inventario.setStockMaximo(inventarioDetalles.getStockMaximo());
            return inventarioRepository.save(inventario);
        }).orElseThrow(() -> new RuntimeException("Inventario no encontrado con ID: " + id));
    }

    public void eliminarInventario(Long id) {
        inventarioRepository.deleteById(id);
    }
}
