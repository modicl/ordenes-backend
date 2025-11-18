package cl.huertohogar.ordenes_backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.huertohogar.ordenes_backend.dto.OrdenResponseDTO;
import cl.huertohogar.ordenes_backend.model.Orden;
import cl.huertohogar.ordenes_backend.service.OrdenService;

@RestController
@RequestMapping("/api/v1/ordenes")
public class OrdenController {
    @Autowired
    private OrdenService ordenService;

    @GetMapping
    public ResponseEntity<List<OrdenResponseDTO>> obtenerOrdenes() {
        var ordenes = ordenService.obtenerOrdenes();
        return ResponseEntity.ok(ordenes);
    }

    @GetMapping("/mis-ordenes")
    public ResponseEntity<List<OrdenResponseDTO>> obtenerMisOrdenes() {
        var ordenes = ordenService.obtenerMisOrdenes();
        return ResponseEntity.ok(ordenes);
    }

    @GetMapping("/{idOrden}")
    public ResponseEntity<OrdenResponseDTO> obtenerOrdenPorId(@PathVariable Integer idOrden) {
        var ordenResponse = ordenService.obtenerOrdenPorId(idOrden);
        return ResponseEntity.ok(ordenResponse);
    }

    @PostMapping
    public ResponseEntity<Orden> crearOrden(@RequestBody Orden orden) {
        Orden ordenCreada = ordenService.crearOrden(orden);
        return ResponseEntity.status(HttpStatus.CREATED).body(ordenCreada);
    }

    @PutMapping("/{idOrden}")
    public ResponseEntity<Orden> actualizarOrden(
            @PathVariable Integer idOrden,
            @RequestBody Orden orden) {
        orden.setIdOrden(idOrden);
        Orden ordenActualizada = ordenService.actualizarOrden(orden);
        return ResponseEntity.ok(ordenActualizada);
    }

    @DeleteMapping("/{idOrden}")
    public ResponseEntity<Void> eliminarOrden(@PathVariable Integer idOrden) {
        ordenService.eliminarOrden(idOrden);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}

