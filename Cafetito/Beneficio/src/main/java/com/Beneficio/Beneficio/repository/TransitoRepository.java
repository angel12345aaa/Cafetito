package com.Beneficio.Beneficio.repository;

import com.Beneficio.Beneficio.model.Transito;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransitoRepository extends JpaRepository<Transito, Long> {
    List<Transito> findByCuenta_IdCuenta(Long idCuenta);
}