# 📚 Documentación Swagger - Microservicio de Órdenes

## 🎯 ¿Qué se agregó?

Se ha implementado **documentación completa de Swagger/OpenAPI** para el microservicio de órdenes, permitiendo explorar y probar todos los endpoints de forma interactiva.

---

## 📦 Cambios Realizados

### 1. **Dependencia Maven** (`pom.xml`)
```xml
<dependency>
    <groupId>org.springdoc</groupId>
    <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
    <version>2.7.0</version>
</dependency>
```

### 2. **Configuración OpenAPI** (`OpenApiConfig.java`)
- ✅ Metadata completa del API (título, descripción, versión)
- ✅ Esquema de seguridad JWT Bearer
- ✅ Servidores configurados (producción en Digital Ocean + desarrollo local)
- ✅ Información de contacto y licencia
- ✅ Instrucciones detalladas de uso

### 3. **Documentación de Código**
Aunque los controladores y modelos ya existen, Swagger los documentará automáticamente. Para mejorar la documentación, puedes agregar anotaciones como:

```java
@Operation(summary = "Crear nueva orden", description = "Crea una orden de compra para un usuario")
@ApiResponses(value = {
    @ApiResponse(responseCode = "201", description = "Orden creada exitosamente"),
    @ApiResponse(responseCode = "400", description = "Datos inválidos"),
    @ApiResponse(responseCode = "401", description = "No autorizado")
})
```

---

## 🚀 Cómo Acceder a Swagger

### **Desarrollo Local**
1. Inicia la aplicación:
   ```bash
   mvn spring-boot:run
   ```

2. Abre tu navegador en:
   - **Swagger UI:** http://localhost:8082/swagger-ui.html
   - **OpenAPI JSON:** http://localhost:8082/v3/api-docs

### **Producción (Digital Ocean)**
Una vez desplegado, accede en:
- **Swagger UI:** https://hh-ordenes-backend-barnt.ondigitalocean.app/swagger-ui.html
- **OpenAPI JSON:** https://hh-ordenes-backend-barnt.ondigitalocean.app/v3/api-docs

---

## 🔐 Autenticación en Swagger UI

### Paso 1: Obtener Token JWT
Autentica en tu microservicio de usuarios para obtener un token JWT.

### Paso 2: Autorizar en Swagger
1. Abre Swagger UI
2. Haz clic en el botón **"Authorize"** 🔓 (arriba a la derecha)
3. Ingresa tu token JWT **sin el prefijo "Bearer"**
   ```
   eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
   ```
4. Haz clic en **"Authorize"**
5. Cierra el modal

### Paso 3: Probar Endpoints
Ahora todos los endpoints incluirán automáticamente el header:
```
Authorization: Bearer <tu-token>
```

---

## 📋 Endpoints Documentados

Swagger documentará automáticamente todos tus endpoints existentes:

### **Órdenes** (`/api/ordenes`)
- `GET /api/ordenes` - Listar todas las órdenes
- `GET /api/ordenes/{id}` - Obtener orden por ID
- `GET /api/ordenes/usuario/{usuarioId}` - Obtener órdenes de un usuario
- `POST /api/ordenes` - Crear nueva orden
- `PUT /api/ordenes/{id}` - Actualizar orden
- `DELETE /api/ordenes/{id}` - Eliminar orden

### **Detalles de Orden** (`/api/detalles-orden`)
- `GET /api/detalles-orden` - Listar todos los detalles
- `GET /api/detalles-orden/{id}` - Obtener detalle por ID
- `GET /api/detalles-orden/orden/{ordenId}` - Obtener detalles de una orden
- `POST /api/detalles-orden` - Crear nuevo detalle
- `PUT /api/detalles-orden/{id}` - Actualizar detalle
- `DELETE /api/detalles-orden/{id}` - Eliminar detalle

---

## 🎨 Características de Swagger UI

### ✨ Exploración Interactiva
- Visualiza todos los endpoints disponibles
- Revisa modelos de datos (DTOs, entidades)
- Consulta códigos de respuesta HTTP
- Examina ejemplos de request/response

### 🧪 Pruebas en Vivo
- Ejecuta requests directamente desde el navegador
- Modifica parámetros y cuerpos de petición
- Visualiza respuestas en tiempo real
- Debugging simplificado

### 📖 Documentación Automática
- Genera documentación a partir del código
- Siempre actualizada con los cambios
- Formato estándar OpenAPI 3.0
- Exportable como JSON/YAML

---

## 🔧 Configuración Adicional (Opcional)

### Personalizar Descripciones de Endpoints

Edita tus controladores para agregar anotaciones:

```java
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Órdenes", description = "API para gestión de órdenes de compra")
@RestController
@RequestMapping("/api/ordenes")
public class OrdenController {
    
    @Operation(
        summary = "Crear nueva orden",
        description = "Crea una orden de compra asociada a un usuario específico"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Orden creada exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
        @ApiResponse(responseCode = "401", description = "Token JWT inválido o ausente")
    })
    @PostMapping
    public ResponseEntity<Orden> crearOrden(@RequestBody Orden orden) {
        // ...
    }
}
```

### Documentar DTOs y Modelos

```java
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Representación de una orden de compra")
public class Orden {
    
    @Schema(description = "ID único de la orden", example = "1")
    private Long id;
    
    @Schema(description = "ID del usuario que realizó la orden", example = "123")
    private Long usuarioId;
    
    @Schema(description = "Fecha de creación de la orden", example = "2025-01-18T10:30:00")
    private LocalDateTime fechaOrden;
    
    @Schema(description = "Monto total de la orden", example = "49.99")
    private BigDecimal total;
}
```

---

## 📊 Ventajas de Swagger

✅ **Documentación siempre actualizada** - Se genera automáticamente del código  
✅ **Testing integrado** - Prueba endpoints sin Postman  
✅ **Estándar de la industria** - OpenAPI 3.0 es el estándar global  
✅ **Fácil integración** - Compatible con otros microservicios  
✅ **Colaboración mejorada** - Frontend/Backend pueden trabajar en paralelo  
✅ **Generación de clientes** - Crea clientes automáticamente en otros lenguajes  

---

## 🐛 Resolución de Problemas

### Swagger UI no carga
- Verifica que la aplicación esté corriendo
- Confirma el puerto correcto (8082)
- Revisa logs de Spring Boot para errores

### Endpoints no aparecen
- Asegúrate de que los controladores tengan `@RestController`
- Verifica que los mappings sean correctos (`@GetMapping`, etc.)
- Recompila y reinicia la aplicación

### Token JWT no funciona
- Verifica que el token sea válido
- Asegúrate de **no incluir** el prefijo "Bearer" al autorizar
- Confirma que tu SecurityConfig permita Swagger:
  ```java
  .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()
  ```

---

## 📚 Recursos Adicionales

- [Documentación SpringDoc](https://springdoc.org/)
- [Especificación OpenAPI 3.0](https://swagger.io/specification/)
- [Guía de Anotaciones Swagger](https://github.com/swagger-api/swagger-core/wiki/Swagger-2.X---Annotations)

---

## ✅ Estado Actual

- [x] Dependencia SpringDoc agregada
- [x] Configuración OpenAPI creada
- [x] Proyecto compilando exitosamente
- [x] Swagger UI accesible en desarrollo
- [ ] Documentación de endpoints con anotaciones (opcional)
- [ ] Deploy a producción con Swagger habilitado

---

**¡Swagger está listo para usar! 🎉**

Inicia tu aplicación y visita http://localhost:8082/swagger-ui.html para comenzar a explorar la API.
