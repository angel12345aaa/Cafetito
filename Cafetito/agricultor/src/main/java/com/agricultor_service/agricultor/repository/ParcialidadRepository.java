package com.agricultor_service.agricultor.repository;

import com.agricultor_service.agricultor.model.Parcialidad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParcialidadRepository extends JpaRepository<Parcialidad, Long> {
    List<Parcialidad> findByPesaje_IdPesaje(Long idPesaje);
}