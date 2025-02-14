package com.rami.gestionordenes.services;

import com.rami.gestionordenes.models.Producto;
import com.rami.gestionordenes.models.viewmodels.ProductoMasVendidoDTO;
import com.rami.gestionordenes.repositories.DetalleOrdenRepository;
import com.rami.gestionordenes.repositories.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductoService {
    @Autowired
    private final ProductoRepository productoRepository;
    @Autowired
    private DetalleOrdenRepository detalleOrdenRepository;

    public ProductoService(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    public List<Producto> listarTodos() {
        return productoRepository.findAll();
    }

    public Optional<Producto> buscarPorId(Long id) {
        return productoRepository.findById(id);
    }

    public Producto guardar(Producto producto) {
        return productoRepository.save(producto);
    }

    public Producto actualizar(Long id, Producto productoActualizado) {
        return productoRepository.findById(id).map(producto -> {
            producto.setNombre(productoActualizado.getNombre());
            producto.setDescripcion(productoActualizado.getDescripcion());
            producto.setPrecio(productoActualizado.getPrecio());
            producto.setCategoria(productoActualizado.getCategoria());
            producto.setActivo(productoActualizado.getActivo());
            return productoRepository.save(producto);
        }).orElseThrow(() -> new RuntimeException("Producto no encontrado"));
    }

    public void eliminar(Long id) {
        productoRepository.deleteById(id);
    }

    public Optional<Producto> buscarPorNombre(String nombre) {
        return productoRepository.findByNombre(nombre);
    }

    public List<Producto> listarPorRangoDePrecios(Double precioMin, Double precioMax) {
        return productoRepository.findByPrecioBetween(precioMin, precioMax);
    }

    public List<Producto> listarPorCategoria(String categoria) {
        return productoRepository.findByCategoriaIgnoreCase(categoria);
    }

    public List<ProductoMasVendidoDTO> obtenerProductosMasVendidos() {
        List<Object[]> resultados = detalleOrdenRepository.findTopSellingProducts();

        return resultados.stream().map(obj -> new ProductoMasVendidoDTO(
                (Producto) obj[0],
                ((Number) obj[1]).intValue()
        )).collect(Collectors.toList());
    }
}
