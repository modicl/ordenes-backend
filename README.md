# 🛒 Microservicio de Órdenes - Huerto Hogar

API RESTful para la gestión de órdenes de compra del sistema Huerto Hogar.

## 📋 Descripción

Microservicio encargado de gestionar las órdenes de compra y sus detalles. Se comunica con los microservicios de **Usuarios** y **Productos** mediante Feign Client.

## 🛠️ Tecnologías

- **Java 21**
- **Spring Boot 3.5.7**
- **Spring Data JPA**
- **Spring Security**
- **Spring Cloud OpenFeign**
- **PostgreSQL**
- **JWT (JSON Web Tokens)**
- **Swagger/OpenAPI 3.0**
- **Lombok**
- **Docker**

## 🚀 Instalación

### Requisitos Previos

- Java 21+
- Maven 3.9+
- PostgreSQL
- Docker (opcional)

### Variables de Entorno

Crear archivo `.env` en la raíz del proyecto:

```env
# Base de datos
DB_HOST=localhost
DB_PORT=5432
DB_NAME=ordenes_db
DB_USERNAME=tu_usuario
DB_PASSWORD=tu_password

# JWT
JWT_SECRET=tu_secret_super_seguro_aqui
JWT_EXPIRATION=86400000

# URLs de Microservicios
USUARIOS_SERVICE_URL=https://hh-usuario-backend-efp2p.ondigitalocean.app
PRODUCTOS_SERVICE_URL=https://hh-productos-backend-xcijd.ondigitalocean.app
```

### Ejecutar Localmente

```bash
# Clonar repositorio
git clone https://github.com/modicl/ordenes-backend.git
cd ordenes-backend

# Compilar
mvn clean install -DskipTests

# Ejecutar
mvn spring-boot:run
```

### Ejecutar con Docker

```bash
# Construir imagen
docker build -t ordenes-backend .

# Ejecutar contenedor
docker run -p 8082:8082 --env-file .env ordenes-backend
```

## 📡 Endpoints

### Órdenes (`/api/v1/ordenes`)

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET | `/api/v1/ordenes` | Obtener todas las órdenes |
| GET | `/api/v1/ordenes/{id}` | Obtener orden por ID |
| GET | `/api/v1/ordenes/mis-ordenes` | Obtener órdenes del usuario autenticado |
| POST | `/api/v1/ordenes` | Crear nueva orden |
| PUT | `/api/v1/ordenes/{id}` | Actualizar orden |
| PATCH | `/api/v1/ordenes/{id}` | Actualizar parcialmente (solo ADMIN) |
| DELETE | `/api/v1/ordenes/{id}` | Eliminar orden |

### Detalles de Orden (`/api/v1/detalles-ordenes`)

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET | `/api/v1/detalles-ordenes` | Obtener todos los detalles |
| GET | `/api/v1/detalles-ordenes/{id}` | Obtener detalle por ID |
| GET | `/api/v1/detalles-ordenes/orden/{idOrden}` | Obtener detalles de una orden |
| POST | `/api/v1/detalles-ordenes` | Crear detalle |
| PUT | `/api/v1/detalles-ordenes/{id}` | Actualizar detalle |
| DELETE | `/api/v1/detalles-ordenes/{id}` | Eliminar detalle |

## 📝 Ejemplos de Uso

### Crear Orden

```bash
curl -X POST http://localhost:8082/api/v1/ordenes \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer <token>" \
  -d '{
    "idUsuario": 1,
    "fechaOrden": "2025-11-30T10:00:00",
    "estado": "PENDIENTE",
    "totalOrden": 25000,
    "direccionEnvio": "Calle 123, Santiago",
    "detalleOrden": [
      {
        "idProducto": 1,
        "cantidad": 2,
        "precioUnitario": 5000
      },
      {
        "idProducto": 3,
        "cantidad": 3,
        "precioUnitario": 5000
      }
    ]
  }'
```

### Obtener Mis Órdenes

```bash
curl -X GET http://localhost:8082/api/v1/ordenes/mis-ordenes \
  -H "Authorization: Bearer <token>"
```

## 📚 Documentación Swagger

- **Swagger UI:** http://localhost:8082/swagger-ui.html
- **OpenAPI JSON:** http://localhost:8082/v3/api-docs

### Producción

- **Swagger UI:** https://hh-ordenes-backend-barnt.ondigitalocean.app/swagger-ui.html

## 🗂️ Estructura del Proyecto

```
src/main/java/cl/huertohogar/ordenes_backend/
├── OrdenesBackendApplication.java
├── client/
│   ├── ProductoFeignClient.java
│   └── UsuarioFeignClient.java
├── config/
│   ├── OpenApiConfig.java
│   ├── SecurityConfig.java
│   └── WebConfig.java
├── controller/
│   ├── DetalleOrdenController.java
│   └── OrdenController.java
├── dto/
│   ├── ActualizacionStockRequestDTO.java
│   ├── ActualizacionStockResponseDTO.java
│   ├── DetalleOrdenDTO.java
│   ├── DetalleOrdenSimpleDTO.java
│   ├── ItemOrdenDTO.java
│   ├── OrdenResponseDTO.java
│   ├── ProductoDTO.java
│   └── UsuarioDTO.java
├── exception/
│   ├── DetalleOrdenNotFoundException.java
│   ├── ErrorResponse.java
│   ├── GlobalExceptionHandler.java
│   ├── OrdenNotFoundException.java
│   ├── ProductoServiceException.java
│   └── UsuarioServiceException.java
├── model/
│   ├── DetalleOrden.java
│   └── Orden.java
├── repository/
│   ├── DetalleOrdenRepository.java
│   └── OrdenRepository.java
├── service/
│   ├── DetalleOrdenService.java
│   └── OrdenService.java
└── util/
    └── JwtUtil.java
```

## 🔗 Comunicación con Microservicios

| Microservicio | URL Producción | Propósito |
|---------------|----------------|-----------|
| Usuarios | https://hh-usuario-backend-efp2p.ondigitalocean.app | Obtener datos de usuario |
| Productos | https://hh-productos-backend-xcijd.ondigitalocean.app | Obtener productos y actualizar stock |

## 🔐 Autenticación

- Autenticación mediante **JWT Bearer Token**
- Header requerido: `Authorization: Bearer <token>`
- Tokens emitidos por el microservicio de Usuarios

## 🚢 Despliegue

### Digital Ocean App Platform

1. Conectar repositorio de GitHub
2. Configurar variables de entorno
3. Deploy automático en cada push a `main`

**URL Producción:** https://hh-ordenes-backend-barnt.ondigitalocean.app

## 📊 Base de Datos

### Tabla: `ordenes`

| Campo | Tipo | Descripción |
|-------|------|-------------|
| id_orden | SERIAL | PK |
| id_usuario | INTEGER | FK usuario |
| fecha_orden | TIMESTAMP | Fecha de creación |
| estado | VARCHAR | PENDIENTE, ENVIADO, ENTREGADO, CANCELADO |
| total_orden | INTEGER | Monto total |
| direccion_envio | VARCHAR | Dirección de entrega |

### Tabla: `detalle_orden`

| Campo | Tipo | Descripción |
|-------|------|-------------|
| id_detalle_orden | SERIAL | PK |
| id_orden | INTEGER | FK orden |
| id_producto | INTEGER | ID producto (microservicio) |
| cantidad | INTEGER | Cantidad |
| precio_unitario | INTEGER | Precio por unidad |

## 👥 Autor

- **Equipo Huerto Hogar** - Duoc UC 2025

## 📄 Licencia

MIT License
