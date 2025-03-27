package com.example.productos.controller;

import com.example.productos.model.Producto;
import com.example.productos.service.ProductoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

@SpringBootTest(classes = com.example.parcial1a.Parcial1AApplication.class)
@AutoConfigureWebTestClient
public class ProductoControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private ProductoService productoService;

    private Producto producto;

    @BeforeEach
    void setUp() {
        producto = new Producto();
        producto.setId("test123");
        producto.setNombre("Monitor");
        producto.setPrecio(199.99);
        System.out.println("🔧 Setup completo para test de integración");
    }

    @Test
    void crearProducto_endpointDebeRetornarProductoCreado() {
        System.out.println("\n🔍 Iniciando prueba de integración: crearProducto_endpointDebeRetornarProductoCreado");

        Mockito.when(productoService.crearProducto(Mockito.any(Producto.class)))
                .thenReturn(Mono.just(producto));

        webTestClient.post()
                .uri("/api/v1/productos")
                .bodyValue(producto)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Producto.class)
                .value(res -> {
                    System.out.println("✅ Producto recibido desde el endpoint:");
                    System.out.println("   ID: " + res.getId());
                    System.out.println("   Nombre: " + res.getNombre());
                    System.out.println("   Precio: " + res.getPrecio());

                    try {
                        assert res.getId().equals("test123");
                        assert res.getNombre().equals("Monitor");
                        assert res.getPrecio() == 199.99;
                        System.out.println("🎯 Test pasó correctamente ✅");
                    } catch (AssertionError e) {
                        System.err.println("❌ Falló una validación del producto: " + e.getMessage());
                        throw e; // para que el test falle visiblemente
                    }
                });
    }
}
