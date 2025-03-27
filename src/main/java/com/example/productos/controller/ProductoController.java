package com.example.productos.controller;

import com.example.productos.model.Producto;
import com.example.productos.service.ProductoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/productos")
@RequiredArgsConstructor
public class ProductoController {

    private final ProductoService productoService;

    @GetMapping("/test")
    public Mono<String> test() {
        return Mono.just("¡Funciona 2!");
    }


    @GetMapping
    public Flux<Producto> listar() {
        return productoService.listarProductos();
    }

    @GetMapping("/{id}")
    public Mono<Producto> obtener(@PathVariable String id) {
        return productoService.obtenerProductoPorId(id);
    }

    @PostMapping
    public Mono<Producto> crear(@RequestBody Producto producto) {
        return productoService.crearProducto(producto);
    }

    @PutMapping("/{id}")
    public Mono<Producto> actualizar(@PathVariable String id, @RequestBody Producto producto) {
        return productoService.actualizarProducto(id, producto);
    }

    @DeleteMapping("/{id}")
    public Mono<Void> eliminar(@PathVariable String id) {
        return productoService.eliminarProducto(id);
    }
}
