package com.Beneficio.Beneficio.repository;

import com.Beneficio.Beneficio.model.HistorialCuenta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HistorialCuentaRepository extends JpaRepository<HistorialCuenta, Long> {
    List<HistorialCuenta> findByCuenta_IdCuenta(Long idCuenta);
    List<HistorialCuenta> findByIdAgricultor(Long idAgricultor);
}