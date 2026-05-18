package com.agricultor_service.agricultor.repository;

import com.agricultor_service.agricultor.model.DetalleCatalogo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DetalleCatalogoRepository extends JpaRepository<DetalleCatalogo, Long> {
    java.util.List<DetalleCatalogo> findByCatalogo_IdCatalogo(Long idCatalogo);
}