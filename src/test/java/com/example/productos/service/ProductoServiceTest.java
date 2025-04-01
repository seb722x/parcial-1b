package com.example.productos.service;

import com.example.productos.model.Producto;
import com.example.productos.repository.ProductoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class ProductoServiceTest {

    @Mock
    private ProductoRepository productoRepository;

    @InjectMocks
    private ProductoService productoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        System.out.println("\n🔧 Setup completo para pruebas unitarias con Mockito\n");
    }

    @Test
    void listarProductos_debeRetornarLista() {
        System.out.println("▶️ Iniciando prueba: listarProductos_debeRetornarLista");

        Producto producto1 = Producto.builder().id("1").nombre("Teclado").precio(50.0).build();
        Producto producto2 = Producto.builder().id("2").nombre("Mouse").precio(30.0).build();

        when(productoRepository.findAll()).thenReturn(Flux.just(producto1, producto2));

        StepVerifier.create(productoService.listarProductos())
                .assertNext(p -> {
                    System.out.println("🟢 Producto recibido: " + p);
                    assertEquals("1", p.getId());
                    assertEquals("Teclado", p.getNombre());
                    assertEquals(50.0, p.getPrecio());
                })
                .assertNext(p -> {
                    System.out.println("🟢 Producto recibido: " + p);
                    assertEquals("2", p.getId());
                    assertEquals("Mouse", p.getNombre());
                    assertEquals(30.0, p.getPrecio());
                })
                .verifyComplete();

        verify(productoRepository, times(1)).findAll();

        System.out.println("✅ Prueba finalizada: listarProductos_debeRetornarLista\n");
    }

    @Test
    void obtenerProductoPorId_debeRetornarProducto() {
        System.out.println("▶️ Iniciando prueba: obtenerProductoPorId_debeRetornarProducto");

        Producto producto = Producto.builder().id("123").nombre("Pantalla").precio(200.0).build();

        when(productoRepository.findById("123")).thenReturn(Mono.just(producto));

        StepVerifier.create(productoService.obtenerProductoPorId("123"))
                .assertNext(p -> {
                    System.out.println("🟢 Producto encontrado: " + p);
                    assertEquals("123", p.getId());
                    assertEquals("Pantalla", p.getNombre());
                    assertEquals(200.0, p.getPrecio());
                })
                .verifyComplete();

        verify(productoRepository).findById("123");

        System.out.println("✅ Prueba finalizada: obtenerProductoPorId_debeRetornarProducto\n");
    }

    @Test
    void crearProducto_debeGuardarYRetornarProducto() {
        System.out.println("▶️ Iniciando prueba: crearProducto_debeGuardarYRetornarProducto");

        Producto producto = Producto.builder().id("321").nombre("Auriculares").precio(75.0).build();

        when(productoRepository.save(producto)).thenReturn(Mono.just(producto));

        StepVerifier.create(productoService.crearProducto(producto))
                .assertNext(p -> {
                    System.out.println("🟢 Producto guardado: " + p);
                    assertEquals("321", p.getId());
                    assertEquals("Auriculares", p.getNombre());
                    assertEquals(75.0, p.getPrecio());
                })
                .verifyComplete();

        verify(productoRepository).save(producto);

        System.out.println("✅ Prueba finalizada: crearProducto_debeGuardarYRetornarProducto\n");
    }

    @Test
    void actualizarProducto_debeModificarYRetornarActualizado() {
        System.out.println("▶️ Iniciando prueba: actualizarProducto_debeModificarYRetornarActualizado");

        String id = "999";
        Producto original = Producto.builder().id(id).nombre("Tablet").precio(300.0).build();
        Producto actualizado = Producto.builder().id(id).nombre("Tablet Pro").precio(450.0).build();

        when(productoRepository.findById(id)).thenReturn(Mono.just(original));
        when(productoRepository.save(any(Producto.class))).thenReturn(Mono.just(actualizado));

        StepVerifier.create(productoService.actualizarProducto(id, actualizado))
                .assertNext(p -> {
                    System.out.println("🟢 Producto actualizado: " + p);
                    assertEquals("Tablet Pro", p.getNombre());
                    assertEquals(450.0, p.getPrecio());
                })
                .verifyComplete();

        verify(productoRepository).findById(id);
        verify(productoRepository).save(any(Producto.class));

        System.out.println("✅ Prueba finalizada: actualizarProducto_debeModificarYRetornarActualizado\n");
    }

    @Test
    void eliminarProducto_debeEliminarYCompletar() {
        System.out.println("▶️ Iniciando prueba: eliminarProducto_debeEliminarYCompletar");

        String id = "777";

        when(productoRepository.deleteById(id)).thenReturn(Mono.empty());

        StepVerifier.create(productoService.eliminarProducto(id))
                .verifyComplete();

        verify(productoRepository).deleteById(id);

        System.out.println("🟢 Producto con ID eliminado: " + id);
        System.out.println("✅ Prueba finalizada: eliminarProducto_debeEliminarYCompletar\n");
    }
}


