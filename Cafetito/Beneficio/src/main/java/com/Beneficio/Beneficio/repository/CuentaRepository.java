package com.Beneficio.Beneficio.repository;

import com.Beneficio.Beneficio.model.Cuenta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CuentaRepository extends JpaRepository<Cuenta, Long> {

    List<Cuenta> findByIdAgricultor(Long idAgricultor);

    List<Cuenta> findByEstado(String estado);
}