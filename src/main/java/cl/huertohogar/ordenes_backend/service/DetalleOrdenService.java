package cl.huertohogar.ordenes_backend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import cl.huertohogar.ordenes_backend.exception.DetalleOrdenNotFoundException;
import cl.huertohogar.ordenes_backend.model.DetalleOrden;
import cl.huertohogar.ordenes_backend.repository.DetalleOrdenRepository;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class DetalleOrdenService {
    
    @Autowired
    private DetalleOrdenRepository detalleOrdenRepository;

    public List<DetalleOrden> obtenerDetalles() {
        try {
            return detalleOrdenRepository.findAll();
        } catch (DataAccessException e) {
            throw new RuntimeException("Error al obtener la lista de detalles de órdenes", e);
        }
    }

    public DetalleOrden obtenerDetallePorId(Integer id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("El ID del detalle debe ser un número positivo válido");
        }
        
        return detalleOrdenRepository.findById(id)
                .orElseThrow(() -> new DetalleOrdenNotFoundException(
                    "Detalle de orden no encontrado con ID: " + id));
    }

    public List<DetalleOrden> obtenerDetallesPorOrden(Integer idOrden) {
        if (idOrden == null || idOrden <= 0) {
            throw new IllegalArgumentException("El ID de la orden es incorrecto");
        }

        List<DetalleOrden> detalles = detalleOrdenRepository.findByIdOrden(idOrden);

        if(detalles.isEmpty()){
            throw new DetalleOrdenNotFoundException("No se encontraron detalles para la orden con ID: " + idOrden);
        }
        
        return detalles;
    }


    public DetalleOrden crearDetalle(DetalleOrden detalle) {
        try {
            if (detalle == null) {
                throw new IllegalArgumentException("El detalle de orden no puede ser nulo");
            }
            
            if (detalle.getIdProducto() == null || detalle.getIdProducto() <= 0) {
                throw new IllegalArgumentException("El ID del producto es requerido y debe ser válido");
            }
            
            if (detalle.getCantidad() == null || detalle.getCantidad() <= 0) {
                throw new IllegalArgumentException("La cantidad debe ser mayor a 0");
            }
            
            if (detalle.getPrecioUnitario() == null || detalle.getPrecioUnitario() < 0) {
                throw new IllegalArgumentException("El precio unitario debe ser mayor o igual a 0");
            }
            
            return detalleOrdenRepository.save(detalle);
            
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (DataAccessException e) {
            throw new RuntimeException("Error al crear el detalle de orden en la base de datos", e);
        }
    }

    public DetalleOrden actualizarDetalle(DetalleOrden detalle) {
        try {
            if (detalle == null) {
                throw new IllegalArgumentException("El detalle de orden no puede ser nulo");
            }
            
            if (detalle.getIdDetalleOrden() == null || detalle.getIdDetalleOrden() <= 0) {
                throw new IllegalArgumentException("El ID del detalle es requerido para actualizar");
            }
            
            // Verificar que el detalle exista
            detalleOrdenRepository.findById(detalle.getIdDetalleOrden())
                    .orElseThrow(() -> new DetalleOrdenNotFoundException(
                        "No se puede actualizar. Detalle de orden no encontrado con ID: " + detalle.getIdDetalleOrden()));
            
            if (detalle.getCantidad() != null && detalle.getCantidad() <= 0) {
                throw new IllegalArgumentException("La cantidad debe ser mayor a 0");
            }
            
            if (detalle.getPrecioUnitario() != null && detalle.getPrecioUnitario() < 0) {
                throw new IllegalArgumentException("El precio unitario debe ser mayor o igual a 0");
            }
            
            return detalleOrdenRepository.save(detalle);
            
        } catch (IllegalArgumentException | DetalleOrdenNotFoundException e) {
            throw e;
        } catch (DataAccessException e) {
            throw new RuntimeException("Error al actualizar el detalle de orden en la base de datos", e);
        }
    }

    public void eliminarDetalle(Integer id) {
        try {
            if (id == null || id <= 0) {
                throw new IllegalArgumentException("El ID del detalle debe ser un número positivo válido");
            }
            
            // Verificar que el detalle exista antes de eliminar
            detalleOrdenRepository.findById(id)
                    .orElseThrow(() -> new DetalleOrdenNotFoundException(
                        "No se puede eliminar. Detalle de orden no encontrado con ID: " + id));
            
            detalleOrdenRepository.deleteById(id);
            
        } catch (IllegalArgumentException | DetalleOrdenNotFoundException e) {
            throw e;
        } catch (DataAccessException e) {
            throw new RuntimeException("Error al eliminar el detalle de orden de la base de datos", e);
        }
    }
}
