package cl.huertohogar.ordenes_backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cl.huertohogar.ordenes_backend.dto.DetalleOrdenSimpleDTO;
import cl.huertohogar.ordenes_backend.exception.DetalleOrdenNotFoundException;
import cl.huertohogar.ordenes_backend.model.DetalleOrden;
import cl.huertohogar.ordenes_backend.service.DetalleOrdenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Detalles de Orden", description = "API para gestión de detalles de órdenes (items)")
@RestController
@RequestMapping("/api/v1/detalles-ordenes")
@CrossOrigin(
    origins = {
        "http://localhost:5173",
        "https://huertohogar.nyc3.cdn.digitaloceanspaces.com",
        "http://huertohogar-frontend.s3-website-us-east-1.amazonaws.com"
    },
    allowedHeaders = "*",
    methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.OPTIONS}
)
public class DetalleOrdenController {

    @Autowired
    private DetalleOrdenService detalleOrdenService;

    @Operation(summary = "Obtener todos los detalles", description = "Retorna la lista completa de detalles de todas las órdenes")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de detalles obtenida exitosamente",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = DetalleOrden.class))),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content)
    })
    @GetMapping
    public ResponseEntity<List<DetalleOrden>> obtenerTodosLosDetalles() {
        try {
            List<DetalleOrden> detalles = detalleOrdenService.obtenerDetalles();
            return ResponseEntity.ok(detalles);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Operation(summary = "Obtener detalle por ID", description = "Retorna un detalle de orden específico según su identificador")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Detalle encontrado",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = DetalleOrden.class))),
        @ApiResponse(responseCode = "404", description = "Detalle no encontrado", content = @Content),
        @ApiResponse(responseCode = "400", description = "ID inválido", content = @Content),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<DetalleOrden> obtenerDetallePorId(
            @Parameter(description = "ID del detalle de orden", example = "1") @PathVariable Integer id) {
        try {
            DetalleOrden detalle = detalleOrdenService.obtenerDetallePorId(id);
            return ResponseEntity.ok(detalle);
        } catch (DetalleOrdenNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Operation(summary = "Obtener detalles por orden", description = "Retorna todos los detalles (items) de una orden específica")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Detalles de la orden obtenidos exitosamente",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = DetalleOrdenSimpleDTO.class))),
        @ApiResponse(responseCode = "404", description = "Orden no encontrada", content = @Content),
        @ApiResponse(responseCode = "400", description = "ID de orden inválido", content = @Content),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content)
    })
    @GetMapping("/orden/{idOrden}")
    public ResponseEntity<List<DetalleOrdenSimpleDTO>> obtenerDetallesPorOrden(
            @Parameter(description = "ID de la orden", example = "1") @PathVariable Integer idOrden) {
        try {
            List<DetalleOrdenSimpleDTO> detalles = detalleOrdenService.obtenerDetallesPorOrdenDTO(idOrden);
            return ResponseEntity.ok(detalles);
        } catch (DetalleOrdenNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Operation(summary = "Crear detalle de orden", description = "Registra un nuevo item/detalle en una orden")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Detalle creado exitosamente",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = DetalleOrden.class))),
        @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos", content = @Content),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content)
    })
    @PostMapping
    public ResponseEntity<DetalleOrden> crearDetalle(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Datos del detalle a crear", required = true,
                content = @Content(schema = @Schema(implementation = DetalleOrden.class)))
            @RequestBody DetalleOrden detalle) {
        try {
            DetalleOrden detalleCreado = detalleOrdenService.crearDetalle(detalle);
            return ResponseEntity.status(HttpStatus.CREATED).body(detalleCreado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Operation(summary = "Actualizar detalle de orden", description = "Actualiza los datos de un detalle/item existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Detalle actualizado exitosamente",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = DetalleOrden.class))),
        @ApiResponse(responseCode = "404", description = "Detalle no encontrado", content = @Content),
        @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos", content = @Content),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<DetalleOrden> actualizarDetalle(
            @Parameter(description = "ID del detalle a actualizar", example = "1") @PathVariable Integer id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Datos actualizados del detalle", required = true,
                content = @Content(schema = @Schema(implementation = DetalleOrden.class)))
            @RequestBody DetalleOrden detalle) {
        try {
            detalle.setIdDetalleOrden(id);
            DetalleOrden detalleActualizado = detalleOrdenService.actualizarDetalle(detalle);
            return ResponseEntity.ok(detalleActualizado);
        } catch (DetalleOrdenNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Operation(summary = "Eliminar detalle de orden", description = "Elimina un item/detalle de una orden")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Detalle eliminado exitosamente", content = @Content),
        @ApiResponse(responseCode = "404", description = "Detalle no encontrado", content = @Content),
        @ApiResponse(responseCode = "400", description = "ID inválido", content = @Content),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarDetalle(
            @Parameter(description = "ID del detalle a eliminar", example = "1") @PathVariable Integer id) {
        try {
            detalleOrdenService.eliminarDetalle(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (DetalleOrdenNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
