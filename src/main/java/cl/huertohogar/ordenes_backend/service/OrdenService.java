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
import cl.huertohogar.ordenes_backend.model.DetalleOrden;
import cl.huertohogar.ordenes_backend.model.Orden;
import cl.huertohogar.ordenes_backend.repository.OrdenRepository;
import cl.huertohogar.ordenes_backend.util.JwtUtil;
import feign.FeignException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class OrdenService {

    @Autowired
    private OrdenRepository ordenRepository;

    @Autowired
    private DetalleOrdenService detalleOrdenService;

    @Autowired
    private UsuarioFeignClient usuarioFeignClient;

    @Autowired
    private ProductoFeignClient productoFeignClient;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private JwtUtil jwtUtil;

    // Obtiene todas las ordenes enriquecidas
    public List<OrdenResponseDTO> obtenerOrdenes() {
        try {
            List<Orden> ordenes = ordenRepository.findAll();

            // Transformamdos a OrdenResponseDTO para agregar detalles
            List<OrdenResponseDTO> ordenesResponse = ordenes.stream()
                    .map(orden -> {
                        List<DetalleOrden> detalles = detalleOrdenService.obtenerDetallesPorOrden(orden.getIdOrden());

                        // Enriquecer detalles con productos
                        List<DetalleOrdenDTO> detallesEnriquecidos = detalles.stream()
                                .map(detalle -> {
                                    ProductoDTO producto = productoFeignClient
                                            .obtenerProductoPorId(detalle.getIdProducto());
                                    DetalleOrdenDTO detalleDTO = new DetalleOrdenDTO();
                                    detalleDTO.setIdDetalle(detalle.getIdDetalleOrden());
                                    detalleDTO.setProducto(producto);
                                    detalleDTO.setCantidad(detalle.getCantidad());
                                    detalleDTO.setPrecioUnitario(detalle.getPrecioUnitario());
                                    return detalleDTO;
                                })
                                .toList();

                        OrdenResponseDTO ordenResponse = new OrdenResponseDTO();
                        ordenResponse.setIdOrden(orden.getIdOrden());
                        ordenResponse.setIdUsuario(orden.getIdUsuario());
                        ordenResponse.setFechaOrden(orden.getFechaOrden());
                        ordenResponse.setEstado(orden.getEstado());
                        ordenResponse.setTotalOrden(orden.getTotalOrden());
                        ordenResponse.setDireccionEnvio(orden.getDireccionEnvio());
                        ordenResponse.setDetalles(detallesEnriquecidos);
                        return ordenResponse;
                    })
                    .toList();
            return ordenesResponse;

        } catch (Exception e) {
            throw new RuntimeException("Error al obtener las ordenes", e);
        }

    }

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

            // Obtenemos el usuario asociado a la orden
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

    public List<OrdenResponseDTO> obtenerMisOrdenes() {
        try {
            // Obtener token desde el header Authorization
            String token = request.getHeader("Authorization");
            if (token == null || token.isEmpty()) {
                throw new UsuarioServiceException("Token no encontrado en la solicitud");
            }

            // Decodificar token y extraer idUsuario
            Integer idUsuarioDelToken = jwtUtil.obtenerIdUsuarioDelToken(token);

            // Obtener todas las órdenes del usuario autenticado
            List<Orden> ordenes = ordenRepository.findByIdUsuario(idUsuarioDelToken);

            if (ordenes.isEmpty()) {
                throw new OrdenNotFoundException(
                        "No se encontraron órdenes para el usuario con ID: " + idUsuarioDelToken);
            }

            // Obtener usuario una sola vez
            UsuarioDTO usuario = usuarioFeignClient.obtenerUsuarioPorId(idUsuarioDelToken, token);

            // Transformar a OrdenResponseDTO con detalles enriquecidos
            List<OrdenResponseDTO> ordenesResponse = ordenes.stream()
                    .map(orden -> {
                        // Enriquecer detalles con productos
                        List<DetalleOrdenDTO> detallesEnriquecidos = orden.getDetalleOrden().stream()
                                .map(detalle -> {
                                    ProductoDTO producto = productoFeignClient
                                            .obtenerProductoPorId(detalle.getIdProducto());

                                    DetalleOrdenDTO detalleDTO = new DetalleOrdenDTO();
                                    detalleDTO.setIdDetalle(detalle.getIdDetalleOrden());
                                    detalleDTO.setProducto(producto);
                                    detalleDTO.setCantidad(detalle.getCantidad());
                                    detalleDTO.setPrecioUnitario(detalle.getPrecioUnitario());
                                    return detalleDTO;
                                })
                                .toList();

                        // Mapear respuesta
                        OrdenResponseDTO ordenResponse = new OrdenResponseDTO();
                        ordenResponse.setIdOrden(orden.getIdOrden());
                        ordenResponse.setIdUsuario(orden.getIdUsuario());
                        ordenResponse.setUsuario(usuario);
                        ordenResponse.setFechaOrden(orden.getFechaOrden());
                        ordenResponse.setEstado(orden.getEstado());
                        ordenResponse.setTotalOrden(orden.getTotalOrden());
                        ordenResponse.setDireccionEnvio(orden.getDireccionEnvio());
                        ordenResponse.setDetalles(detallesEnriquecidos);
                        return ordenResponse;
                    })
                    .toList();

            return ordenesResponse;

        } catch (OrdenNotFoundException e) {
            throw e;
        } catch (FeignException.NotFound e) {
            System.err.println("🔍 URL llamada: " + e.request().url());
            System.err.println("🔍 Status: " + e.status());
            System.err.println("🔍 Mensaje: " + e.getMessage());
            throw new UsuarioServiceException("Recurso no encontrado en el microservicio: " + e.getMessage(), e);
        } catch (FeignException.ServiceUnavailable e) {
            throw new UsuarioServiceException("Servicio no disponible", e);
        } catch (FeignException.Unauthorized e) {
            throw new UsuarioServiceException("Token inválido o expirado", e);
        } catch (FeignException e) {
            throw new UsuarioServiceException("Error al comunicarse con los microservicios: " + e.getMessage(), e);
        }
    }

    public Orden crearOrden(Orden orden) {
        try {
            if (orden == null) {
                throw new IllegalArgumentException("La orden no puede ser nula");
            }

            if (orden.getIdUsuario() == null || orden.getIdUsuario() <= 0) {
                throw new IllegalArgumentException("El ID del usuario es requerido y debe ser válido");
            }

            if (orden.getFechaOrden() == null) {
                throw new IllegalArgumentException("La fecha de la orden es requerida");
            }

            if (orden.getTotalOrden() == null || orden.getTotalOrden() < 0) {
                throw new IllegalArgumentException("El total de la orden debe ser mayor o igual a 0");
            }

            // Establecer la relación bidireccional ANTES de guardar (en español : dejamos
            // la id de la orden en id_orden de detalleOrden)
            if (orden.getDetalleOrden() != null && !orden.getDetalleOrden().isEmpty()) {
                for (DetalleOrden detalle : orden.getDetalleOrden()) {
                    detalle.setOrden(orden); // Esto establece la FK id_orden
                }
            }

            return ordenRepository.save(orden);

        } catch (IllegalArgumentException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Error al crear la orden en la base de datos", e);
        }
    }

    public Orden actualizarOrden(Orden orden) {
        try {
            if (orden == null) {
                throw new IllegalArgumentException("La orden no puede ser nula");
            }

            if (orden.getIdOrden() == null || orden.getIdOrden() <= 0) {
                throw new IllegalArgumentException("El ID de la orden es requerido para actualizar");
            }

            // Verificar que la orden exista
            ordenRepository.findById(orden.getIdOrden())
                    .orElseThrow(() -> new OrdenNotFoundException(
                            "No se puede actualizar. Orden no encontrada con ID: " + orden.getIdOrden()));

            if (orden.getTotalOrden() != null && orden.getTotalOrden() < 0) {
                throw new IllegalArgumentException("El total de la orden debe ser mayor o igual a 0");
            }

            return ordenRepository.save(orden);

        } catch (IllegalArgumentException | OrdenNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Error al actualizar la orden en la base de datos", e);
        }
    }

    public void eliminarOrden(Integer idOrden) {
        try {
            if (idOrden == null || idOrden <= 0) {
                throw new IllegalArgumentException("El ID de la orden debe ser un número positivo válido");
            }

            // Verificar que la orden exista antes de eliminar
            ordenRepository.findById(idOrden)
                    .orElseThrow(() -> new OrdenNotFoundException(
                            "No se puede eliminar. Orden no encontrada con ID: " + idOrden));

            ordenRepository.deleteById(idOrden);

        } catch (IllegalArgumentException | OrdenNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Error al eliminar la orden de la base de datos", e);
        }
    }
}
