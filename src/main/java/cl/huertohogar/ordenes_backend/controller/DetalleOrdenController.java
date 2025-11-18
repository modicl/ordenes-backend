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

    @GetMapping
    public ResponseEntity<List<DetalleOrden>> obtenerTodosLosDetalles() {
        try {
            List<DetalleOrden> detalles = detalleOrdenService.obtenerDetalles();
            return ResponseEntity.ok(detalles);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<DetalleOrden> obtenerDetallePorId(@PathVariable Integer id) {
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

    @GetMapping("/orden/{idOrden}")
    public ResponseEntity<List<DetalleOrdenSimpleDTO>> obtenerDetallesPorOrden(@PathVariable Integer idOrden) {
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

    @PostMapping
    public ResponseEntity<DetalleOrden> crearDetalle(@RequestBody DetalleOrden detalle) {
        try {
            DetalleOrden detalleCreado = detalleOrdenService.crearDetalle(detalle);
            return ResponseEntity.status(HttpStatus.CREATED).body(detalleCreado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<DetalleOrden> actualizarDetalle(
            @PathVariable Integer id,
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

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarDetalle(@PathVariable Integer id) {
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
