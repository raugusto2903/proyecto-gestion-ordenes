package com.rami.gestionordenes;

import com.rami.gestionordenes.models.Producto;
import com.rami.gestionordenes.repositories.ProductoRepository;
import com.rami.gestionordenes.services.ProductoService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.mockito.Mockito.times;

public class ProductoServiceTest {
    @Mock
    private ProductoRepository productoRepository;

    @InjectMocks
    private ProductoService productoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void buscarPorId() {
        Producto producto = new Producto();
        producto.setIdProducto(1L);
        producto.setNombre("Laptop");

        Mockito.when(productoRepository.findById(1L)).thenReturn(Optional.of(producto));

        Optional<Producto> resultado = productoService.buscarPorId(1L);

        Assertions.assertEquals("Laptop", resultado.get().getNombre());
        Mockito.verify(productoRepository, times(1)).findById(1L);
    }
}
