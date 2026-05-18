package com.Beneficio.Beneficio.repository;

import com.Beneficio.Beneficio.model.DetalleCatalogo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DetalleCatalogoRepository extends JpaRepository<DetalleCatalogo, Long> {
    List<DetalleCatalogo> findByCatalogo_IdCatalogoOrderByOrdenAsc(Long idCatalogo);
    List<DetalleCatalogo> findByCatalogo_NombreCatalogoOrderByOrdenAsc(String nombreCatalogo);
    Optional<DetalleCatalogo> findByCatalogo_NombreCatalogoAndCodigo(String nombreCatalogo, String codigo);
}