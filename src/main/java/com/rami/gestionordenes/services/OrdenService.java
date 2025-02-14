package com.rami.gestionordenes.services;

import com.rami.gestionordenes.models.DetalleOrden;
import com.rami.gestionordenes.models.Orden;
import com.rami.gestionordenes.models.Producto;
import com.rami.gestionordenes.models.Usuario;
import com.rami.gestionordenes.repositories.DetalleOrdenRepository;
import com.rami.gestionordenes.repositories.OrdenRepository;
import com.rami.gestionordenes.repositories.ProductoRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class OrdenService {
    private static final Logger logger = LoggerFactory.getLogger(OrdenService.class);
    @Autowired
    private OrdenRepository ordenRepository;

    @Autowired
    private DetalleOrdenRepository detalleOrdenRepository;

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private DescuentoService descuentoService;
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
        LocalDateTime fechaCreacion = LocalDateTime.now(); // Captura la fecha actual

        // Guardar primero la Orden sin los detalles
        Orden nuevaOrden = new Orden();
        nuevaOrden.setUsuario(orden.getUsuario());  // Se asigna el usuario
        if (descuentoService.aplicarDescuento(fechaCreacion)) {
            double totalOriginal = orden.getTotal();
            double descuento = totalOriginal * 0.10;
            nuevaOrden.setTotal(totalOriginal - descuento); // Aplica el descuento del 10%
            logger.info("🔹 Se aplicó un 10% de descuento a la orden. Total original: {}, Nuevo total: {}",
                    totalOriginal, orden.getTotal());
        } else {
            logger.info("✅ No se aplicó descuento, fuera del rango de tiempo.");
            nuevaOrden.setTotal(orden.getTotal());
        }
             // Se asigna el total
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

    /**
     * Selecciona órdenes pendientes aleatoriamente y les aplica descuentos según las reglas.
     */
    public void seleccionarOrdenesAleatoriasYAplicarDescuento() {
        List<Orden> ordenesPendientes = ordenRepository.findByEstado("Pendiente");

        if (ordenesPendientes.isEmpty()) {
            logger.info("❌ No hay órdenes en estado 'Pendiente' para procesar.");
            return;
        }

        // 🔥 Barajar la lista para elegir aleatoriamente
        Collections.shuffle(ordenesPendientes, new Random());

        // 🔥 Obtener el Top 5 de clientes frecuentes
        List<Usuario> clientesFrecuentes = ordenRepository.findTop5ClientesFrecuentes();

        // 🔥 Aplicar descuentos a las órdenes seleccionadas
        for (Orden orden : ordenesPendientes) {
            LocalDateTime localDateTime = orden.getFechaCreacion().toInstant()
                    .atZone(ZoneId.systemDefault()) // Usa la zona horaria del sistema
                    .toLocalDateTime();
            if (descuentoService.aplicarDescuento(localDateTime)) {
                double descuentoBase = 0.50; // 🔥 50% de descuento por promoción
                if (clientesFrecuentes.contains(orden.getUsuario())) {
                    descuentoBase += 0.05; // 🔥 5% extra si es cliente frecuente (Total 55%)
                }

                double totalOriginal = orden.getTotal();
                double descuento = totalOriginal * descuentoBase;
                orden.setTotal(totalOriginal - descuento);

                logger.info("🎯 Orden ID={} seleccionada para descuento. Descuento aplicado: {}%",
                        orden.getId(), descuentoBase * 100);

                ordenRepository.save(orden);
            }
        }
    }

}
