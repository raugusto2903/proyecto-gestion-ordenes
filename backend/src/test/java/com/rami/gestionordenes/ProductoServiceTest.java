package com.rami.gestionordenes;

import com.rami.gestionordenes.models.Producto;
import com.rami.gestionordenes.models.viewmodels.ProductoMasVendidoDTO;
import com.rami.gestionordenes.repositories.DetalleOrdenRepository;
import com.rami.gestionordenes.repositories.ProductoRepository;
import com.rami.gestionordenes.services.ProductoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductoServiceTest {

    @Mock
    private ProductoRepository productoRepository;

    @Mock
    private DetalleOrdenRepository detalleOrdenRepository;

    @InjectMocks
    private ProductoService productoService;

    private Producto producto1;
    private Producto producto2;

    @BeforeEach
    void setUp() {
        producto1 = new Producto(1L, "Laptop", "Laptop de gama alta", 1500.0, "Electrónica", true);
        producto2 = new Producto(2L, "Teléfono", "Smartphone con buena cámara", 800.0, "Electrónica", false);
    }

    @Test
    void testListarTodos() {
        when(productoRepository.findAll()).thenReturn(Arrays.asList(producto1, producto2));

        List<Producto> productos = productoService.listarTodos();

        assertNotNull(productos);
        assertEquals(2, productos.size());
        verify(productoRepository, times(1)).findAll();
    }

    @Test
    void testBuscarPorId() {
        when(productoRepository.findById(1L)).thenReturn(Optional.of(producto1));

        Optional<Producto> producto = productoService.buscarPorId(1L);

        assertTrue(producto.isPresent());
        assertEquals("Laptop", producto.get().getNombre());
        verify(productoRepository, times(1)).findById(1L);
    }

    @Test
    void testGuardarProducto() {
        when(productoRepository.save(producto1)).thenReturn(producto1);

        Producto productoGuardado = productoService.guardar(producto1);

        assertNotNull(productoGuardado);
        assertEquals("Laptop", productoGuardado.getNombre());
        verify(productoRepository, times(1)).save(producto1);
    }

    @Test
    void testActualizarProducto() {
        Producto productoActualizado = new Producto(1L, "Laptop Gamer", "Laptop con GPU potente", 2000.0, "Electrónica", true);

        when(productoRepository.findById(1L)).thenReturn(Optional.of(producto1));
        when(productoRepository.save(any(Producto.class))).thenReturn(productoActualizado);

        Producto resultado = productoService.actualizar(1L, productoActualizado);

        assertNotNull(resultado);
        assertEquals("Laptop Gamer", resultado.getNombre());
        assertEquals(2000.0, resultado.getPrecio());
        verify(productoRepository, times(1)).save(any(Producto.class));
    }

    @Test
    void testActualizarProductoNoExistente() {
        when(productoRepository.findById(3L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            productoService.actualizar(3L, producto1);
        });

        assertEquals("Producto no encontrado", exception.getMessage());
        verify(productoRepository, times(1)).findById(3L);
    }

    @Test
    void testEliminarProducto() {
        doNothing().when(productoRepository).deleteById(1L);

        productoService.eliminar(1L);

        verify(productoRepository, times(1)).deleteById(1L);
    }

    @Test
    void testBuscarPorNombre() {
        when(productoRepository.findByNombreContaining("Laptop")).thenReturn(Arrays.asList(producto1));

        List<Producto> productos = productoService.buscarPorNombre("Laptop");

        assertNotNull(productos);
        assertEquals(1, productos.size());
        assertEquals("Laptop", productos.get(0).getNombre());
        verify(productoRepository, times(1)).findByNombreContaining("Laptop");
    }

    @Test
    void testListarPorRangoDePrecios() {
        when(productoRepository.findByPrecioBetween(500.0, 2000.0)).thenReturn(Arrays.asList(producto1, producto2));

        List<Producto> productos = productoService.listarPorRangoDePrecios(500.0, 2000.0);

        assertNotNull(productos);
        assertEquals(2, productos.size());
        verify(productoRepository, times(1)).findByPrecioBetween(500.0, 2000.0);
    }

    @Test
    void testListarPorCategoria() {
        when(productoRepository.findByCategoriaIgnoreCase("Electrónica")).thenReturn(Arrays.asList(producto1, producto2));

        List<Producto> productos = productoService.listarPorCategoria("Electrónica");

        assertNotNull(productos);
        assertEquals(2, productos.size());
        verify(productoRepository, times(1)).findByCategoriaIgnoreCase("Electrónica");
    }

    @Test
    void testObtenerTodosLosProductos() {
        when(productoRepository.findByActivoTrue()).thenReturn(Arrays.asList(producto1));

        List<Producto> productosActivos = productoService.obtenerTodosLosProductos();

        assertNotNull(productosActivos);
        assertEquals(1, productosActivos.size());
        assertEquals("Laptop", productosActivos.get(0).getNombre());
        verify(productoRepository, times(1)).findByActivoTrue();
    }

    @Test
    void testObtenerProductosSinInventario() {
        when(productoRepository.findProductosSinInventario()).thenReturn(Arrays.asList(producto2));

        List<Producto> productosSinInventario = productoService.obtenerProductosSinInventario();

        assertNotNull(productosSinInventario);
        assertEquals(1, productosSinInventario.size());
        assertEquals("Teléfono", productosSinInventario.get(0).getNombre());
        verify(productoRepository, times(1)).findProductosSinInventario();
    }
}

