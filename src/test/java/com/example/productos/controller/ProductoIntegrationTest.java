package com.example.productos;

import com.example.parcial1a.Parcial1AApplication;
import com.example.productos.model.Producto;
import com.example.productos.repository.ProductoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = Parcial1AApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
public class ProductoIntegrationTest {

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private ProductoRepository productoRepository;

    @BeforeEach
    void limpiarBaseDeDatos() {
        productoRepository.deleteAll().block();
        System.out.println("\n🧹 Base de datos limpiada antes de cada test\n");
    }

    @Test
    void flujoCompleto_crearObtenerEliminarProducto() {
        System.out.println("▶️ Iniciando prueba de integración completa");

        // Paso 1: Crear producto
        Producto producto = Producto.builder()
                .nombre("Monitor LG")
                .precio(900.0)
                .build();

        Producto productoCreado = webTestClient.post()
                .uri("/api/v1/productos")
                .body(Mono.just(producto), Producto.class)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(Producto.class)
                .returnResult()
                .getResponseBody();

        assert productoCreado != null;
        assertEquals("Monitor LG", productoCreado.getNombre());
        System.out.println("✅ Producto creado: " + productoCreado);

        // Paso 2: Obtener producto por ID
        Producto productoObtenido = webTestClient.get()
                .uri("/api/v1/productos/{id}", productoCreado.getId())
                .exchange()
                .expectStatus().isOk()
                .expectBody(Producto.class)
                .returnResult()
                .getResponseBody();

        assert productoObtenido != null;
        assertEquals(productoCreado.getId(), productoObtenido.getId());
        System.out.println("📦 Producto obtenido: " + productoObtenido);

        // Paso 3: Eliminar producto
        webTestClient.delete()
                .uri("/api/v1/productos/{id}", productoCreado.getId())
                .exchange()
                .expectStatus().isNoContent();

        System.out.println("🗑️ Producto eliminado con éxito");

        // Paso 4: Confirmar que ya no existe
        webTestClient.get()
                .uri("/api/v1/productos/{id}", productoCreado.getId())
                .exchange()
                .expectStatus().isNotFound();

        System.out.println("✅ Confirmación: Producto ya no existe");
        System.out.println("🏁 Prueba de integración finalizada\n");
    }
}

