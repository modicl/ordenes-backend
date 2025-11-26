package cl.huertohogar.ordenes_backend.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import cl.huertohogar.ordenes_backend.dto.ActualizacionStockRequestDTO;
import cl.huertohogar.ordenes_backend.dto.ActualizacionStockResponseDTO;
import cl.huertohogar.ordenes_backend.dto.ProductoDTO;

@FeignClient(name = "productos-service", url = "${PRODUCTOS_SERVICE_URL:https://hh-productos-backend-xcijd.ondigitalocean.app}")
public interface ProductoFeignClient {

    @GetMapping("/api/v1/productos/{idProducto}")
    ProductoDTO obtenerProductoPorId(
        @PathVariable("idProducto") Integer idProducto
    );

        @PostMapping("/api/v1/productos/stock/actualizar")
    ResponseEntity<ActualizacionStockResponseDTO> actualizarStock(
        @RequestBody ActualizacionStockRequestDTO request,
        @RequestHeader("Authorization") String token
    );
    
    // No existe
    @PostMapping("/api/v1/productos/stock/revertir")
    ResponseEntity<ActualizacionStockResponseDTO> revertirStock(
        @RequestBody ActualizacionStockRequestDTO request,
        @RequestHeader("Authorization") String token
    );
}
