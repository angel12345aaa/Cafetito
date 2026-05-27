package com.Beneficio.Beneficio.repository;

import com.Beneficio.Beneficio.model.ParcialidadBeneficio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParcialidadBeneficioRepository extends JpaRepository<ParcialidadBeneficio, Long> {

    List<ParcialidadBeneficio> findByCuenta_IdCuenta(Long idCuenta);

    boolean existsByIdParcialidadAgricultor(Long idParcialidadAgricultor);

    List<ParcialidadBeneficio> findByEstado(String estado);

    List<ParcialidadBeneficio> findByBoletaTrue();

    List<ParcialidadBeneficio> findByEstadoAndCuenta_EstadoIn(
            String estado,
            List<String> estadosCuenta
    );
}