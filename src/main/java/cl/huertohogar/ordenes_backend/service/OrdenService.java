package cl.huertohogar.ordenes_backend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.huertohogar.ordenes_backend.client.ProductoFeignClient;
import cl.huertohogar.ordenes_backend.client.UsuarioFeignClient;
import cl.huertohogar.ordenes_backend.dto.DetalleOrdenDTO;
import cl.huertohogar.ordenes_backend.dto.OrdenResponseDTO;
import cl.huertohogar.ordenes_backend.dto.ProductoDTO;
import cl.huertohogar.ordenes_backend.dto.UsuarioDTO;
import cl.huertohogar.ordenes_backend.exception.OrdenNotFoundException;
import cl.huertohogar.ordenes_backend.exception.UsuarioServiceException;
import cl.huertohogar.ordenes_backend.model.Orden;
import cl.huertohogar.ordenes_backend.repository.OrdenRepository;
import feign.FeignException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class OrdenService {

    @Autowired
    private OrdenRepository ordenRepository;

    @Autowired
    private UsuarioFeignClient usuarioFeignClient;

    @Autowired
    private ProductoFeignClient productoFeignClient;


    @Autowired
    private HttpServletRequest request;

    public OrdenResponseDTO obtenerOrdenPorId(Integer idOrden) {
        try {
            // Obtenemos la orden
            Orden orden = ordenRepository.findById(idOrden)
                    .orElseThrow(() -> new OrdenNotFoundException("Orden no encontrada con ID: " + idOrden));

            // Obtener token desde el header Authorization
            String token = request.getHeader("Authorization");
            if (token == null || token.isEmpty()) {
                throw new UsuarioServiceException("Token no encontrado en la solicitud");
            }

            // Obtenemos el usuario asociado a la orden con el token
            UsuarioDTO usuario = usuarioFeignClient.obtenerUsuarioPorId(orden.getIdUsuario(), token);

            // Enriquecer detalles con productos
            List<DetalleOrdenDTO> detallesEnriquecidos = orden.getDetalleOrden().stream()
                    .map(detalle -> {
                        ProductoDTO producto = productoFeignClient.obtenerProductoPorId(detalle.getIdProducto());

                        DetalleOrdenDTO detalleDTO = new DetalleOrdenDTO();
                        detalleDTO.setIdDetalle(detalle.getIdDetalleOrden());
                        detalleDTO.setProducto(producto);
                        detalleDTO.setCantidad(detalle.getCantidad());
                        detalleDTO.setPrecioUnitario(detalle.getPrecioUnitario());
                        return detalleDTO;
                    })
                    .toList();

            // Mapeo respuesta
            var ordenResponse = new OrdenResponseDTO();
            ordenResponse.setIdOrden(orden.getIdOrden());
            ordenResponse.setIdUsuario(orden.getIdUsuario());
            ordenResponse.setUsuario(usuario);
            ordenResponse.setFechaOrden(orden.getFechaOrden());
            ordenResponse.setEstado(orden.getEstado());
            ordenResponse.setTotalOrden(orden.getTotalOrden());
            ordenResponse.setDireccionEnvio(orden.getDireccionEnvio());
            ordenResponse.setDetalles(detallesEnriquecidos);
            return ordenResponse;

        } catch (OrdenNotFoundException e) {
            throw e;
        } catch (FeignException.NotFound e) {
            throw new UsuarioServiceException("Usuario no encontrado en el microservicio de usuarios", e);
        } catch (FeignException.ServiceUnavailable e) {
            throw new UsuarioServiceException("Servicio de usuarios no disponible", e);
        } catch (FeignException.Unauthorized e) {
            throw new UsuarioServiceException("Token invalido o expirado", e);
        } catch (FeignException e) {
            throw new UsuarioServiceException("Error al comunicarse con el microservicio de usuarios" + e.getMessage(),
                    e);
        }

    }
}
