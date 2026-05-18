package com.agricultor_service.agricultor.repository;

import com.agricultor_service.agricultor.model.Modelo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ModeloRepository extends JpaRepository<Modelo, Long> {}