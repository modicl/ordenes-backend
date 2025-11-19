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

import cl.huertohogar.ordenes_backend.dto.OrdenResponseDTO;
import cl.huertohogar.ordenes_backend.model.Orden;
import cl.huertohogar.ordenes_backend.service.OrdenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Órdenes", description = "API para gestión de órdenes de compra")
@RestController
@RequestMapping("/api/v1/ordenes")
@CrossOrigin(origins = {
        "http://localhost:5173",
        "https://huertohogar.nyc3.cdn.digitaloceanspaces.com",
        "http://huertohogar-frontend.s3-website-us-east-1.amazonaws.com"
}, allowedHeaders = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE,
        RequestMethod.OPTIONS })
public class OrdenController {
    @Autowired
    private OrdenService ordenService;

    @Operation(summary = "Obtener todas las órdenes", description = "Retorna una lista completa de todas las órdenes registradas en el sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de órdenes obtenida exitosamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = OrdenResponseDTO.class)))
    })
    @GetMapping
    public ResponseEntity<List<OrdenResponseDTO>> obtenerOrdenes() {
        var ordenes = ordenService.obtenerOrdenes();
        return ResponseEntity.ok(ordenes);
    }

    @Operation(summary = "Obtener mis órdenes", description = "Retorna las órdenes del usuario autenticado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Órdenes del usuario obtenidas exitosamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = OrdenResponseDTO.class))),
            @ApiResponse(responseCode = "401", description = "No autorizado - Token JWT inválido", content = @Content)
    })
    @GetMapping("/mis-ordenes")
    public ResponseEntity<List<OrdenResponseDTO>> obtenerMisOrdenes() {
        var ordenes = ordenService.obtenerMisOrdenes();
        return ResponseEntity.ok(ordenes);
    }

    @Operation(summary = "Obtener orden por ID", description = "Retorna una orden específica según su identificador")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Orden encontrada", content = @Content(mediaType = "application/json", schema = @Schema(implementation = OrdenResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Orden no encontrada", content = @Content)
    })
    @GetMapping("/{idOrden}")
    public ResponseEntity<OrdenResponseDTO> obtenerOrdenPorId(
            @Parameter(description = "ID de la orden a buscar", example = "1") @PathVariable Integer idOrden) {
        var ordenResponse = ordenService.obtenerOrdenPorId(idOrden);
        return ResponseEntity.ok(ordenResponse);
    }

    @Operation(summary = "Crear nueva orden", description = "Registra una nueva orden de compra en el sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Orden creada exitosamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Orden.class))),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos", content = @Content)
    })
    @PostMapping
    public ResponseEntity<Orden> crearOrden(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Datos de la orden a crear", required = true, content = @Content(schema = @Schema(implementation = Orden.class))) @RequestBody Orden orden) {
        Orden ordenCreada = ordenService.crearOrden(orden);
        return ResponseEntity.status(HttpStatus.CREATED).body(ordenCreada);
    }

    @Operation(summary = "Actualizar orden", description = "Actualiza los datos de una orden existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Orden actualizada exitosamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Orden.class))),
            @ApiResponse(responseCode = "404", description = "Orden no encontrada", content = @Content),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos", content = @Content)
    })
    @PutMapping("/{idOrden}")
    public ResponseEntity<Orden> actualizarOrden(
            @Parameter(description = "ID de la orden a actualizar", example = "1") @PathVariable Integer idOrden,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Datos actualizados de la orden", required = true, content = @Content(schema = @Schema(implementation = Orden.class))) @RequestBody Orden orden) {
        orden.setIdOrden(idOrden);
        Orden ordenActualizada = ordenService.actualizarOrden(orden);
        return ResponseEntity.ok(ordenActualizada);
    }

    @Operation(summary = "Eliminar orden", description = "Elimina una orden del sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Orden eliminada exitosamente", content = @Content),
            @ApiResponse(responseCode = "404", description = "Orden no encontrada", content = @Content)
    })
    @DeleteMapping("/{idOrden}")
    public ResponseEntity<Void> eliminarOrden(
            @Parameter(description = "ID de la orden a eliminar", example = "1") @PathVariable Integer idOrden) {
        ordenService.eliminarOrden(idOrden);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Operation(summary = "Actualizar parcialmente orden", description = "Actualiza campos específicos de una orden (Solo ADMIN)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Orden actualizada exitosamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Orden.class))),
            @ApiResponse(responseCode = "404", description = "Orden no encontrada", content = @Content),
            @ApiResponse(responseCode = "403", description = "No autorizado", content = @Content)
    })
    @org.springframework.web.bind.annotation.PatchMapping("/{idOrden}")
    public ResponseEntity<Orden> patchOrden(
            @Parameter(description = "ID de la orden a actualizar", example = "1") @PathVariable Integer idOrden,
            @RequestBody java.util.Map<String, Object> updates) {
        Orden ordenActualizada = ordenService.patchOrden(idOrden, updates);
        return ResponseEntity.ok(ordenActualizada);
    }
}
