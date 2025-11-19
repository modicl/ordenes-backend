package cl.huertohogar.ordenes_backend.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * Configuración de OpenAPI/Swagger para el microservicio de Órdenes.
 * 
 * <p>Esta clase configura la documentación interactiva de la API utilizando Swagger UI,
 * permitiendo a los desarrolladores explorar y probar los endpoints del microservicio.</p>
 * 
 * <p>La documentación incluye:</p>
 * <ul>
 *   <li>Información general del microservicio y su propósito</li>
 *   <li>Esquema de autenticación JWT Bearer</li>
 *   <li>Servidores de producción y desarrollo</li>
 *   <li>Detalles de contacto y licencia</li>
 * </ul>
 * 
 * <p>Acceso a Swagger UI: <code>/swagger-ui.html</code></p>
 * <p>Acceso a OpenAPI JSON: <code>/v3/api-docs</code></p>
 * 
 * @author Huerto Hogar Development Team
 * @version 1.0
 * @since 2025-01-18
 */
@Configuration
public class OpenApiConfig {

    @Value("${server.port:8082}")
    private String serverPort;

    /**
     * Configura la especificación OpenAPI del microservicio.
     * 
     * <p>Define la metadata del API, incluyendo título, descripción, versión,
     * servidores disponibles y esquemas de seguridad.</p>
     * 
     * @return objeto OpenAPI configurado con toda la información del microservicio
     */
    @Bean
    public OpenAPI ordenesOpenAPI() {
        // Definir el nombre del esquema de seguridad
        final String securitySchemeName = "bearerAuth";
        
        return new OpenAPI()
                .info(new Info()
                        .title("API de Gestión de Órdenes - Huerto Hogar")
                        .description("""
                                API RESTful para la gestión integral de órdenes de compra en el sistema Huerto Hogar.
                                
                                **Funcionalidades Principales:**
                                - Creación y gestión de órdenes de compra
                                - Administración de detalles de orden (items, cantidades, precios)
                                - Cálculo automático de totales y subtotales
                                - Integración con microservicios de Productos y Usuarios mediante Feign Client
                                - Validación de stock y disponibilidad de productos
                                - Historial completo de órdenes por usuario
                                
                                **Arquitectura:**
                                - Microservicio independiente basado en Spring Boot 3.x
                                - Base de datos PostgreSQL para persistencia
                                - Comunicación inter-microservicios con OpenFeign
                                - Autenticación JWT para seguridad
                                
                                **Seguridad:**
                                - Todos los endpoints requieren autenticación JWT válida
                                - Header requerido: `Authorization: Bearer <token>`
                                - Los tokens JWT son emitidos por el microservicio de Autenticación
                                
                                **Modelo de Datos:**
                                - `Orden`: Representa una orden de compra con información del usuario y fecha
                                - `DetalleOrden`: Items individuales de cada orden con producto, cantidad y precio
                                
                                **Códigos de Estado HTTP:**
                                - `200 OK`: Operación exitosa
                                - `201 Created`: Recurso creado exitosamente
                                - `400 Bad Request`: Datos de entrada inválidos
                                - `401 Unauthorized`: Token JWT inválido o ausente
                                - `404 Not Found`: Recurso no encontrado
                                - `500 Internal Server Error`: Error del servidor
                                
                                Para más información, consulte la documentación del proyecto en GitHub.
                                """)
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Equipo de Desarrollo Huerto Hogar")
                                .email("dev@huertohogar.cl")
                                .url("https://github.com/huertohogar"))
                        .license(new License()
                                .name("MIT License")
                                .url("https://opensource.org/licenses/MIT")))
                .servers(List.of(
                        new Server()
                                .url("https://hh-ordenes-backend-barnt.ondigitalocean.app")
                                .description("Servidor de Producción (Digital Ocean)"),
                        new Server()
                                .url("http://localhost:" + serverPort)
                                .description("Servidor Local de Desarrollo")))
                .addSecurityItem(new SecurityRequirement()
                        .addList(securitySchemeName))
                .components(new io.swagger.v3.oas.models.Components()
                        .addSecuritySchemes(securitySchemeName,
                                new SecurityScheme()
                                        .name(securitySchemeName)
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")
                                        .description("""
                                                Autenticación mediante JSON Web Token (JWT).
                                                
                                                **Cómo obtener un token:**
                                                1. Autentícate en el microservicio de Usuarios
                                                2. Copia el token JWT recibido
                                                3. En Swagger UI, haz clic en el botón "Authorize" 🔓
                                                4. Ingresa el token sin el prefijo "Bearer"
                                                5. Prueba los endpoints autenticado
                                                
                                                **Formato del Header:**
                                                ```
                                                Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
                                                ```
                                                
                                                El token JWT contiene información del usuario autenticado
                                                y expira después de 24 horas (configurable).
                                                """)));
    }
}
