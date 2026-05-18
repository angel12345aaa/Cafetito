package com.agricultor_service.agricultor.repository;

import com.agricultor_service.agricultor.model.Licencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LicenciaRepository extends JpaRepository<Licencia, Long> {}