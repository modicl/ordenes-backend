package cl.huertohogar.ordenes_backend.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import cl.huertohogar.ordenes_backend.dto.UsuarioDTO;

// Este cliente lo usamos para relacionarlo con Orden

@FeignClient(name = "usuarios-service" , url = "https://hh-usuario-backend-efp2p.ondigitalocean.app")
public interface UsuarioFeignClient {
    
    @GetMapping("api/v1/usuarios/{idUsuario}")
    UsuarioDTO obtenerUsuarioPorId(
        @PathVariable Integer idUsuario,
        @RequestHeader("Authorization") String token
    );

}
