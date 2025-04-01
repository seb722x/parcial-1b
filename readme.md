# 🧪 Pruebas Automatizadas - Proyecto Parcial 1B

Este proyecto incluye pruebas de integración automatizadas que validan que los endpoints del API funcionen correctamente. Las pruebas están escritas con `WebTestClient` y simulan el uso real del sistema.

## ✅ ¿Para qué sirven las pruebas?

Las pruebas permiten verificar que el flujo completo del sistema funcione como se espera. Validan lo siguiente:

1. Que se pueda crear un producto.
2. Que se pueda obtener ese producto por su ID.
3. Que se pueda eliminar el producto.
4. Que después de eliminarlo, ya no esté disponible (retorna 404).

Esto ayuda a detectar errores automáticamente antes de hacer despliegues.

## ▶️ ¿Cómo correr las pruebas?

### Opción 1: Desde IntelliJ IDEA
1. Abre el archivo `ProductoIntegrationTest.java`.
2. Haz clic derecho sobre la clase y selecciona:  
   **Run 'ProductoIntegrationTest'**

### Opción 2: Desde la terminal
En la raíz del proyecto ejecuta:

#### En Windows PowerShell:
```bash
.\mvnw.cmd test
