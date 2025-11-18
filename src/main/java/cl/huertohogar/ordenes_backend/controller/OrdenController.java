package cl.huertohogar.ordenes_backend.controller;

import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.huertohogar.ordenes_backend.dto.OrdenResponseDTO;
import cl.huertohogar.ordenes_backend.service.OrdenService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/api/v1/ordenes")
public class OrdenController {
    @Autowired
    private OrdenService ordenService;

    @GetMapping("/{idOrden}")
    public ResponseEntity<OrdenResponseDTO> obtenerOrdenPorId(@PathVariable Integer idOrden){
        var ordenResponse = ordenService.obtenerOrdenPorId(idOrden);
        return ResponseEntity.ok(ordenResponse);
    }
    
}
