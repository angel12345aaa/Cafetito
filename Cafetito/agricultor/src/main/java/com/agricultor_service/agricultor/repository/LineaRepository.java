package com.agricultor_service.agricultor.repository;

import com.agricultor_service.agricultor.model.Linea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LineaRepository extends JpaRepository<Linea, Long> {}