package com.example.productos.service;

import com.example.productos.model.Producto;
import com.example.productos.repository.ProductoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.*;

public class ProductoServiceTest {

    private ProductoService productoService;
    private ProductoRepository productoRepository;

    @BeforeEach
    void setUp() {
        productoRepository = mock(ProductoRepository.class);
        productoService = new ProductoService(productoRepository);
        System.out.println("🔧 Setup completo para los tests");
    }

    @Test
    void crearProducto_debeRetornarProductoGuardadoConId() {
        System.out.println("\n🔍 Iniciando test: crearProducto_debeRetornarProductoGuardadoConId");

        Producto productoSinId = new Producto();
        productoSinId.setNombre("Mouse");
        productoSinId.setPrecio(49.99);

        Producto productoConId = new Producto();
        productoConId.setId("123abc");
        productoConId.setNombre("Mouse");
        productoConId.setPrecio(49.99);

        when(productoRepository.save(productoSinId)).thenReturn(Mono.just(productoConId));

        StepVerifier.create(productoService.crearProducto(productoSinId))
                .expectNextMatches(p -> {
                    boolean result = p.getId() != null &&
                            p.getNombre().equals("Mouse") &&
                            p.getPrecio() == 49.99;
                    if (!result) {
                        System.err.println("❌ El test falló: los datos del producto no son los esperados.");
                        System.err.println("   ID: " + p.getId());
                        System.err.println("   Nombre: " + p.getNombre());
                        System.err.println("   Precio: " + p.getPrecio());
                    } else {
                        System.out.println("✅ Producto guardado correctamente:");
                        System.out.println("   ID: " + p.getId());
                        System.out.println("   Nombre: " + p.getNombre());
                        System.out.println("   Precio: " + p.getPrecio());
                    }
                    return result;
                })
                .verifyComplete();

        verify(productoRepository, times(1)).save(productoSinId);
        System.out.println("✅ Test crearProducto_debeRetornarProductoGuardadoConId pasó correctamente 🎉");
    }

    @Test
    void actualizarProducto_debeRetornarProductoActualizado() {
        System.out.println("\n🔍 Iniciando test: actualizarProducto_debeRetornarProductoActualizado");

        String productoId = "123";
        Producto productoExistente = new Producto();
        productoExistente.setId(productoId);
        productoExistente.setNombre("Teclado");
        productoExistente.setPrecio(30.00);

        Producto productoActualizado = new Producto();
        productoActualizado.setNombre("Teclado Mecánico");
        productoActualizado.setPrecio(60.00);

        Producto resultadoEsperado = new Producto();
        resultadoEsperado.setId(productoId);
        resultadoEsperado.setNombre("Teclado Mecánico");
        resultadoEsperado.setPrecio(60.00);

        when(productoRepository.findById(productoId)).thenReturn(Mono.just(productoExistente));
        when(productoRepository.save(any(Producto.class))).thenReturn(Mono.just(resultadoEsperado));

        StepVerifier.create(productoService.actualizarProducto(productoId, productoActualizado))
                .expectNextMatches(p -> {
                    boolean result = p.getNombre().equals("Teclado Mecánico") &&
                            p.getPrecio() == 60.00;
                    if (!result) {
                        System.err.println("❌ El test falló: producto no se actualizó como se esperaba.");
                        System.err.println("   Nombre: " + p.getNombre());
                        System.err.println("   Precio: " + p.getPrecio());
                    } else {
                        System.out.println("✅ Producto actualizado correctamente:");
                        System.out.println("   ID: " + p.getId());
                        System.out.println("   Nombre: " + p.getNombre());
                        System.out.println("   Precio: " + p.getPrecio());
                    }
                    return result;
                })
                .verifyComplete();

        verify(productoRepository).findById(productoId);
        verify(productoRepository).save(any(Producto.class));
        System.out.println("✅ Test actualizarProducto_debeRetornarProductoActualizado pasó correctamente 🎉");
    }
}

