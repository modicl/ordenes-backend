package cl.huertohogar.ordenes_backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import cl.huertohogar.ordenes_backend.model.DetalleOrden;

public interface DetalleOrdenRepository extends JpaRepository<DetalleOrden, Integer>{
    
    // Query para buscar por id de orden
    @Query(value = "SELECT * FROM detalle_ordenes WHERE id_orden = ?1",nativeQuery = true)
    List<DetalleOrden> findByOrden_IdOrden(Integer id);

}
