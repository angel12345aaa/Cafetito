package com.agricultor_service.agricultor.repository;

import com.agricultor_service.agricultor.model.Parcialidad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ParcialidadRepository extends JpaRepository<Parcialidad, Long> {

    List<Parcialidad> findByPesaje_IdPesaje(Long idPesaje);

    @Query("SELECT COALESCE(SUM(p.pesoActual), 0) FROM Parcialidad p WHERE p.pesaje.idPesaje = :idPesaje")
    Double sumarPesoPorPesaje(Long idPesaje);
}