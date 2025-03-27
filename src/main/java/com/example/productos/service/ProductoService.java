package com.example.productos.service;

import com.example.productos.model.Producto;
import com.example.productos.repository.ProductoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ProductoService {

    private final ProductoRepository productoRepository;

    public Flux<Producto> listarProductos() {
        return productoRepository.findAll();
    }

    public Mono<Producto> obtenerProductoPorId(String id) {
        return productoRepository.findById(id);
    }

    public Mono<Producto> crearProducto(Producto producto) {
        return productoRepository.save(producto);
    }

    public Mono<Producto> actualizarProducto(String id, Producto nuevoProducto) {
        return productoRepository.findById(id)
                .flatMap(productoExistente -> {
                    productoExistente.setNombre(nuevoProducto.getNombre());
                    productoExistente.setPrecio(nuevoProducto.getPrecio());
                    return productoRepository.save(productoExistente);
                });
    }

    public Mono<Void> eliminarProducto(String id) {
        return productoRepository.deleteById(id);
    }
}
