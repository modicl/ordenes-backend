package cl.huertohogar.ordenes_backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import cl.huertohogar.ordenes_backend.model.DetalleOrden;

public interface DetalleOrdenRepository extends JpaRepository<DetalleOrden, Integer>{
    
    // Query para buscar por id de orden
    @Query("SELECT d FROM DetalleOrden d WHERE d.orden.idOrden = :id")
    List<DetalleOrden> findByIdOrden(Integer id);

}
