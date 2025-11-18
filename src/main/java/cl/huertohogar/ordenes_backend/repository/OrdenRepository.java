package cl.huertohogar.ordenes_backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import cl.huertohogar.ordenes_backend.model.Orden;

public interface OrdenRepository extends JpaRepository<Orden, Integer> {
    List<Orden> findByIdUsuario(Integer idUsuario);
}
