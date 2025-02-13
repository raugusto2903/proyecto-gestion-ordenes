package com.rami.gestionordenes.services;

import com.rami.gestionordenes.models.DetalleOrden;
import com.rami.gestionordenes.models.Orden;
import com.rami.gestionordenes.models.Producto;
import com.rami.gestionordenes.repositories.DetalleOrdenRepository;
import com.rami.gestionordenes.repositories.OrdenRepository;
import com.rami.gestionordenes.repositories.ProductoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrdenService {
    @Autowired
    private OrdenRepository ordenRepository;

    @Autowired
    private DetalleOrdenRepository detalleOrdenRepository;

    @Autowired
    private ProductoRepository productoRepository;
    // Listar todas las órdenes
    public List<Orden> obtenerTodas() {
        return ordenRepository.findAll();
    }

    // Obtener una orden por ID
    public Optional<Orden> obtenerPorId(Long id) {
        return ordenRepository.findById(id);
    }

    // Crear o actualizar una orden
    @Transactional
    public Orden guardarOrden(Orden orden) {
        // Guardar primero la Orden sin los detalles
        Orden nuevaOrden = new Orden();
        nuevaOrden.setUsuario(orden.getUsuario());  // Se asigna el usuario
        nuevaOrden.setTotal(orden.getTotal());      // Se asigna el total
        nuevaOrden.setEstado(orden.getEstado());    // Se asigna el estado
        nuevaOrden.setDetalles(null); // La lista de detalles se guarda después
        Orden ordenGuardada = ordenRepository.save(nuevaOrden);

        // Verificar si la orden traía detalles
        if (orden.getDetalles() != null && !orden.getDetalles().isEmpty()) {
            for (DetalleOrden detalle : orden.getDetalles()) {
                // Asignar la orden guardada al detalle
                detalle.setOrden(ordenGuardada);

                // Verificar si el producto existe en la BD
                Producto producto = productoRepository.findById(detalle.getProducto().getIdProducto())
                        .orElseThrow(() -> new RuntimeException("Producto con ID " + detalle.getProducto().getIdProducto() + " no encontrado"));

                detalle.setProducto(producto);
                detalleOrdenRepository.save(detalle); // Guardar cada detalle individualmente
            }
        }

        return ordenGuardada;
    }

    // Eliminar una orden
    public void eliminarOrden(Long id) {
        ordenRepository.deleteById(id);
    }
}
