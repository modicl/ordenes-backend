package cl.huertohogar.ordenes_backend.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import cl.huertohogar.ordenes_backend.dto.ProductoDTO;

@FeignClient(name = "productos-service", url = "https://hh-productos-backend-xcijd.ondigitalocean.app/")
public interface ProductoFeignClient {
    
    @GetMapping("api/v1/productos/{idProducto}")
    public ProductoDTO obtenerProductoPorId(@PathVariable Integer idProducto);


}
